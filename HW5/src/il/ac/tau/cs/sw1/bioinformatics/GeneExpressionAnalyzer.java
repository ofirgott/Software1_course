package il.ac.tau.cs.sw1.bioinformatics;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.apache.commons.math3.stat.inference.TestUtils;

/**
 * 
 * Gene Expression Analyzer
 * 
 * Command line arguments: args[0] - GeoDatasetName: Gene expression dataset
 * name (expects a corresponding input file in SOFT format to exist in the local
 * directory). args[1] - LabelType: Specifies type of sample labels. Only labels
 * of this type will be parsed from the input file. args[2] - Label1: Label of
 * the first sample subset. args[3] - Label2: Label of the second sample subset
 * args[4] - Alpha: T-test confidence level : only genes with pValue below this
 * threshold will be printed to output file
 * 
 * Execution example: GeneExpressionAnalyzer GDS4085
 * "estrogen receptor-negative" "estrogen receptor-positive" 0.01
 * 
 * @author software1-2015
 *
 */

public class GeneExpressionAnalyzer {

	public static void main(String args[]) throws IOException {

		// Reads the dataset from a SOFT input file
		String inputSoftFileName = args[0] + ".soft";
		String labelType = args[1];
		GeneExpressionDataset geneExpressionDataset = parseGeneExpressionFile(
				inputSoftFileName, labelType);
		System.out.printf("Gene expression dataset loaded from file %s. %n",
				inputSoftFileName);
		System.out.printf("Dataset contains %d samples and %d gene probes.%n",
				geneExpressionDataset.samplesNumber,
				geneExpressionDataset.genesNumber);
		System.out
				.printf("Dataset contains %d label subsets of type [%s]: %s%n%n",
						geneExpressionDataset.labelSubsetNumber,
						labelType,
						new HashSet<String>(Arrays
								.asList(geneExpressionDataset.labels)));

		// Removes low variance genes which are usually not informative. Keeping
		// only the top 50% variable genes.
		float varianceFractionThreshold = 0.5f;
		int numberOfGenesToKeep = 50;
		float varianceThreshold = applyVarianceFilterOnDataset(
				geneExpressionDataset, numberOfGenesToKeep);
		System.out
				.printf("Applying variance filter on dataset: Kept %d top variable genes having variance of at least %.2f.%n",
						geneExpressionDataset.genesNumber, varianceThreshold);

		// Identifies differentially expressed genes between two sample groups
		// and writes the results to a text file
		String label1 = args[2];
		String label2 = args[3];
		double alpha = Double.parseDouble(args[4]);
		String diffGenesFileName = args[0] + "-DiffGenes.txt";
		int numOfDiffGenes = writeTopDifferentiallyExpressedGenesToFile(
				diffGenesFileName, geneExpressionDataset, alpha, label1, label2);
		System.out
				.printf("%d differentially expressed genes identified using alpha of %.5f when comparing the two sample groups [%s] and [%s].%n",
						numOfDiffGenes, alpha, label1, label2);
		System.out.printf("Results saved to file %s.%n", diffGenesFileName);
	}

	/**
	 * 
	 * parseGeneExpressionFile - parses the given SOFT file
	 * 
	 * @param filename
	 *            A gene expression file in SOFT format
	 * @param labelType
	 *            Only labels of this type will be included in the analysis
	 * @return a GeneExpressionDataset object storing all data parsed from the
	 *         input file
	 * @throws IOException
	 */
	/**
	 * @param filename
	 * @param labelType
	 * @return
	 * @throws IOException
	 */
	public static GeneExpressionDataset parseGeneExpressionFile(
			String filename, String labelType) throws IOException {

		GeneExpressionDataset dataset = new GeneExpressionDataset();
		File fromFile = new File(filename);
		BufferedReader bufferedReader = new BufferedReader(new FileReader(
				fromFile));
		String tmp_subset_type = "";
		String line;
		String[] tmp_samples_id = new String[0];
		boolean requested_subset = false;
		int row_cnt = 0;
		ArrayList<String> tmp_subsets_names = new ArrayList<String>();
		ArrayList<String[]> tmp_subsets_samples_ids = new ArrayList<String[]>();
		// (use ArrayList because we don't know how many subsets we have in
		// selected label type)

		while ((line = bufferedReader.readLine()) != null) {
			requested_subset = false;

			if (line.startsWith("!dataset_title")) // datasetTitle
				dataset.datasetTitle = line.substring(line.indexOf("=") + 2,
						line.length()); // start copy 2 char after '='

			if (line.startsWith("!dataset_sample_count")) { // samplesNumber
				dataset.samplesNumber = Integer.parseInt(line.substring(
						line.indexOf("=") + 2, line.length()));
				dataset.sampleIds = new String[dataset.samplesNumber];
			}

			if (line.startsWith("!dataset_feature_count")) { // genesNumber
				dataset.genesNumber = Integer.parseInt(line.substring(
						line.indexOf("=") + 2, line.length()));
				dataset.geneIds = new String[dataset.genesNumber];
				dataset.geneSymbols = new String[dataset.genesNumber];
			}

			// labels -
			if (line.startsWith("^SUBSET")) { // start block of new subset
												// properties
				String tmp_subset_label = "";
				row_cnt = 0;

				while (row_cnt < 4) // row_cnt for counting 4 rows of new subset
									// properties
				{
					line = bufferedReader.readLine();
					row_cnt++;
					requested_subset = false;
					if (line.startsWith("!subset_description")) // subset label
						tmp_subset_label = line.substring(
								line.indexOf("=") + 2, line.length());

					if (line.startsWith("!subset_sample_id")) { // list of
																// subset
																// samples ids
						tmp_samples_id = (line.substring(line.indexOf("=") + 2,
								line.length())).split(",");
					}

					if (line.startsWith("!subset_type")) {
						// type of subset - if != labelType we'll break, else we
						// add data to labels String array

						tmp_subset_type = line.substring(line.indexOf("=") + 2,
								line.length());

						if (tmp_subset_type.equals(labelType)) // if not
																// requested
																// type -
																// break
							requested_subset = true;

					}

				}

				if (requested_subset) {
					tmp_subsets_names.add(tmp_subset_label); // insert to labels
																// names array
					tmp_subsets_samples_ids.add(tmp_samples_id.clone()); // insert
																			// to
																			// labels
																			// ids
					// array
				}
			}
			// /////////////////// start of table //////////////////////
			if (line.startsWith("!dataset_table_begin")) {

				// read headers
				line = bufferedReader.readLine();
				String[] SampIds = line.split("\t");
				dataset.sampleIds = Arrays.copyOfRange(SampIds, 2,
						SampIds.length); // first 2 string are headers

				// define matrix dimensions
				dataset.dataMatrix = new float[dataset.genesNumber][dataset.samplesNumber];

				// parsing tables rows
				int i = 0;

				while ((!(line = bufferedReader.readLine())
						.startsWith("!dataset_table_end"))) { // while we didn't
					// reach to the
					// table's end
					String[] splt = line.split("\t");
					dataset.geneIds[i] = splt[0];
					dataset.geneSymbols[i] = splt[1];

					// iterate over numbers in row
					for (int j = 0; j < dataset.samplesNumber; j++)
						dataset.dataMatrix[i][j] = Float
								.parseFloat(splt[j + 2]);
					i++;
				}

			}
		}

		// build labels array
		dataset = Build_Labels_Array(dataset, tmp_subsets_names,
				tmp_subsets_samples_ids);

		bufferedReader.close();
		return dataset;
	}

	/**
	 * 
	 * Build_Labels_Array
	 * 
	 * Updated labels array to requested samples. for unrequested samples assign
	 * null
	 * 
	 * @param dataset
	 * @param tmp_subsets_names
	 * @param tmp_subsets_samples_ids
	 * @return
	 * @return
	 */
	public static GeneExpressionDataset Build_Labels_Array(
			GeneExpressionDataset dataset, ArrayList<String> tmp_subsets_names,
			ArrayList<String[]> tmp_subsets_samples_ids) {

		dataset.labels = new String[dataset.samplesNumber];
		for (int i = 0; i < dataset.samplesNumber; i++) {
			if (contain(tmp_subsets_samples_ids, dataset.sampleIds[i]) == -1)
				dataset.labels[i] = null;
			else {
				dataset.labels[i] = tmp_subsets_names.get(contain(
						tmp_subsets_samples_ids, dataset.sampleIds[i]));
				// put in labels[i] name of subset for every requested sample
			}

		}
		dataset.labelSubsetNumber = tmp_subsets_names.size();
		return dataset;
	}

	/**
	 * 
	 * contain
	 * 
	 * search String in ArrayList<String[]> 2d array return first row index if
	 * exists, else -1
	 * 
	 * @param array
	 * @param string
	 * @return row index
	 */
	private static int contain(ArrayList<String[]> array, String string) {

		for (int i = 0; i < array.size(); i++) {
			for (int j = 0; j < array.get(i).length; j++) {
				if (array.get(i)[j].equals(string)) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * applyVarianceFilterOnDataset - Filters out low variability genes from the
	 * given dataset object
	 * 
	 * 
	 * @param geneExpressionDataset
	 *            - The dataset object to be processed.
	 * @param numberOfGenesToKeep
	 *            - number of genes to keep in the dataset object based on
	 *            variance filtering
	 * @return varianceThreshold - a float indicating the variance threshold
	 *         value. Only genes having this variance value or higher are kept
	 *         in the filtered dataset.
	 */
	public static float applyVarianceFilterOnDataset(
			GeneExpressionDataset geneExpressionDataset, int numberOfGenesToKeep) {

		// Creates variance values array
		Float[] variences = new Float[geneExpressionDataset.genesNumber];

		for (int i = 0; i < geneExpressionDataset.dataMatrix.length; i++)
			// for each gene
			variences[i] = var(geneExpressionDataset.dataMatrix[i]);

		// Creates sorted copy of array
		Float[] variences_copy = Arrays.copyOf(variences, variences.length);
		Arrays.sort(variences_copy);

		float varianceThreshold = variences_copy[variences_copy.length
				- numberOfGenesToKeep - 1];

		// build Arraylist with indexes of top numberOfGenesToKeep genes with
		// variance above varianceThreshold
		// we will built new array for value with variance = varianceThreshold
		// to use it later
		ArrayList<Integer> above_varianceThreshold_indexes = new ArrayList<Integer>();
		ArrayList<Integer> equals_varianceThreshold_indexes = new ArrayList<Integer>();
		int counter = 0;
		int i = 0;

		while (i <= variences.length - 1 && counter <= numberOfGenesToKeep) {

			if (variences[i] > varianceThreshold) { // first we search only
													// values with variances >
													// varianceThreshold
				above_varianceThreshold_indexes.add(i);
				counter++;
			}

			if (variences[i] == varianceThreshold)
				equals_varianceThreshold_indexes.add(i);

			i++;
		}

		// if we have less than numberOfGenesToKeep, search for indexes with
		// value = varianceThreshold to fill it
		if (counter < numberOfGenesToKeep) {
			for (int j = 0; j < numberOfGenesToKeep - counter; j++)
				above_varianceThreshold_indexes
						.add(equals_varianceThreshold_indexes.get(j));
		}
		// now we suppose to have numberOfGenesToKeep values in
		// above_varianceThreshold_indexes

		// build the new matrix according to the new chosen indexes
		geneExpressionDataset.dataMatrix = BuildUpdatedMatrix(
				above_varianceThreshold_indexes, geneExpressionDataset);

		// update other geneExpressionDataset values
		geneExpressionDataset.genesNumber = geneExpressionDataset.dataMatrix.length; // number
																						// of
																						// dataset
																						// gene
																						// probes
		geneExpressionDataset.geneIds = UpdateArrayWithNewIndexes(
				geneExpressionDataset.geneIds, above_varianceThreshold_indexes); // gene
																					// probe
																					// ids
		geneExpressionDataset.geneSymbols = UpdateArrayWithNewIndexes(
				geneExpressionDataset.geneSymbols,
				above_varianceThreshold_indexes); // gene symbols

		return varianceThreshold;
	}

	private static String[] UpdateArrayWithNewIndexes(String[] orig_array,
			ArrayList<Integer> above_varianceThreshold_indexes) {
		String[] outputArray = new String[above_varianceThreshold_indexes
				.size()];

		for (int i = 0; i < above_varianceThreshold_indexes.size(); i++) {
			outputArray[i] = orig_array[above_varianceThreshold_indexes.get(i)];
		}

		return outputArray;
	}

	/**
	 * BuildUpdatedMatrix - building new DataMatrix with the relevant indexes
	 * 
	 * @param above_varianceThreshold_indexes
	 * @param geneExpressionDataset
	 * @return NewDataMatrix
	 */
	private static float[][] BuildUpdatedMatrix(
			ArrayList<Integer> above_varianceThreshold_indexes,
			GeneExpressionDataset geneExpressionDataset) {

		// define new matrix dimensions
		float[][] NewDataMatrix = new float[above_varianceThreshold_indexes
				.size()][geneExpressionDataset.samplesNumber];

		int counter = 0;
		for (int i = 0; i < above_varianceThreshold_indexes.size(); i++) {
			NewDataMatrix[counter] = geneExpressionDataset.dataMatrix[above_varianceThreshold_indexes
					.get(i)];
			counter++;
		}
		return NewDataMatrix;
	}

	// compute variance value for float array
	private static float var(float[] array) {
		float sum = 0;
		float mean = mean(array);
		for (float num : array)
			sum += Math.pow((num - mean), 2);
		return (sum / array.length);
	}

	// computer mean value for float array
	private static float mean(float[] array) {
		float sum = 0;
		for (float num : array)
			sum += num;
		return (sum / array.length);
	}

	/**
	 * 
	 * getDataEntriesForLabel
	 * 
	 * Returns the entries in the 'data' array for which the corresponding
	 * entries in the 'labels' array equals 'label'
	 * 
	 * @param data
	 * @param labels
	 * @param label
	 * @return output
	 */
	public static double[] getDataEntriesForLabel(float[] data,
			String[] labels, String label) {

		double[] output = new double[data.length];
		int counter = 0;

		for (int i = 0; i < labels.length; i++) {
			if (labels[i].equals(label)) {
				output[counter] = data[i];
				counter++;
			}
		}

		// output array is not full
		output = Arrays.copyOf(output, counter);

		return output;

	}

	/**
	 * 
	 * writeTopDifferentiallyExpressedGenesToFile Writes a sorted list of
	 * differentially expressed probes to a file.
	 * 
	 * @param outputFilename
	 * @param geneExpressionDataset
	 * @param alpha
	 * @param label1
	 * @param label2
	 * @return numOfDiffGenes The number of differentially expressed genes
	 *         detected, having p-value lower than alpha
	 * @throws IOException
	 */
	public static int writeTopDifferentiallyExpressedGenesToFile(
			String outputFilename, GeneExpressionDataset geneExpressionDataset,
			double alpha, String label1, String label2) throws IOException {

		int numOfDiffGenes = 0;

		// /////calcTtest for every gene in geneExpressionDataset

		double[] All_Genes_P_value = new double[geneExpressionDataset.genesNumber];
		double tmp_calc_test = 0.0;
		for (int i = 0; i < geneExpressionDataset.genesNumber; i++) {
			tmp_calc_test = calcTtest(geneExpressionDataset, i, label1, label2);
			All_Genes_P_value[i] = tmp_calc_test;
			if (tmp_calc_test < alpha)
				numOfDiffGenes++;
		}

		double[] sorted_Genes_P_value = Arrays.copyOf(All_Genes_P_value,
				All_Genes_P_value.length); // creates sorted copy of array

		// Creates indexes array of sorted_All_Genes_P_value_under_alpha
		int[] indexesOfsorted_All_Genes_P_value = new int[sorted_Genes_P_value.length];
		BubbleSort_DoubleArray_with_IndexesArray(sorted_Genes_P_value,
				indexesOfsorted_All_Genes_P_value);

		// ///////print genes to file///////////////
		File toFile = new File(outputFilename);
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
				toFile));

		String line;
		int k = 0;
		while (k < numOfDiffGenes) { // print only with p-value < alpha(first
										// numOfDiffGenes values in array

			line = (String.format("%d\t", k + 1)
					+ String.format(
							"%f",
							All_Genes_P_value[indexesOfsorted_All_Genes_P_value[k]])
					+ "\t"
					+ geneExpressionDataset.geneIds[indexesOfsorted_All_Genes_P_value[k]]
					+ "\t" + geneExpressionDataset.geneSymbols[indexesOfsorted_All_Genes_P_value[k]]);

			// print to file in each word - counter, P-value, geneID, geneSymbol
			bufferedWriter.write(line + "\n");
			k++;
		}
		bufferedWriter.close();
		return numOfDiffGenes;

	}

	private static void BubbleSort_DoubleArray_with_IndexesArray(
			double[] sorted_Genes_P_value,
			int[] indexesOfsorted_All_Genes_P_value) throws IOException {

		for (int i = 0; i < indexesOfsorted_All_Genes_P_value.length; i++) {
			indexesOfsorted_All_Genes_P_value[i] = i;
		}

		boolean changed = false;
		do {
			changed = false;
			for (int j = 0; j < sorted_Genes_P_value.length - 1; j++) {
				if (sorted_Genes_P_value[j] > sorted_Genes_P_value[j + 1]) { // swap
					swapInDoubleArray(sorted_Genes_P_value, j, j + 1); // swap
																		// values
					swapInintArray(indexesOfsorted_All_Genes_P_value, j, j + 1); // swap
																					// indexes
					changed = true;
				}
			}
		} while (changed);

	}

	private static void swapInintArray(int[] intarray, int j1, int j2) {
		int tmp = intarray[j1];
		intarray[j1] = intarray[j2];
		intarray[j2] = tmp;

	}

	private static void swapInDoubleArray(double[] doublearray, int j1, int j2) {
		double tmp = doublearray[j1];
		doublearray[j1] = doublearray[j2];
		doublearray[j2] = tmp;

	}

	/**
	 * calcTtest - returns a pValue for the t-Test
	 * 
	 * Returns the p-value, associated with a two-sample, two-tailed t-test
	 * comparing the means of the input arrays
	 * 
	 * //http://commons.apache.org/proper/commons-math/apidocs/org/apache/
	 * commons/math3/stat/inference/TTest.html#tTest(double[], double[])
	 * 
	 * @param geneExpressionDataset
	 * @param geneIndex
	 * @param label1
	 * @param label2
	 * @return
	 */
	public static double calcTtest(GeneExpressionDataset geneExpressionDataset,
			int geneIndex, String label1, String label2) {
		double[] sample1 = getDataEntriesForLabel(
				geneExpressionDataset.dataMatrix[geneIndex],
				geneExpressionDataset.labels, label1);
		double[] sample2 = getDataEntriesForLabel(
				geneExpressionDataset.dataMatrix[geneIndex],
				geneExpressionDataset.labels, label2);	
		return TestUtils.tTest(sample1, sample2);
	}
}

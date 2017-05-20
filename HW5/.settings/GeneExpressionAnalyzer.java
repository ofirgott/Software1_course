package il.ac.tau.cs.sw1.bioinformatics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

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
		int numberOfGenesToKeep = (int) Math
				.floor(geneExpressionDataset.genesNumber
						* varianceFractionThreshold);
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
	public static GeneExpressionDataset parseGeneExpressionFile(
			String filename, String labelType) throws IOException {

		GeneExpressionDataset dataset = new GeneExpressionDataset();

		File fromFile = new File(filename);
		BufferedReader bufferedReader = new BufferedReader(new FileReader(
				fromFile));
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			if (line.startsWith("!dataset_title")) // datasetTitle
				dataset.datasetTitle = line.substring(line.indexOf("=") + 2,
						line.length()); // start copy 2 char after '='
			if (line.startsWith("!dataset_sample_count")) // samplesNumber
				dataset.samplesNumber = Integer.parseInt(line.substring(
						line.indexOf("=") + 2, line.length()));
			if (line.startsWith("!dataset_feature_count")) // genesNumber
				dataset.genesNumber = Integer.parseInt(line.substring(
						line.indexOf("=") + 2, line.length()));
			// labels -
			int subset_index=0;
			String tmp_subset_name="";
			if (line.startsWith("^SUBSET")){	//start block of new subset properties
				
				while
					
			}
			
		}

		// TODO ----------------------------------------------------------------
		bufferedReader.close();
		return dataset;
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

		// TODO ----------------------------------------------------------
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
	 * @return
	 */
	public static double[] getDataEntriesForLabel(float[] data,
			String[] labels, String label) {

		// TODO ----------------------------------------------------------

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

		// TODO ----------------------------------------------------------

		return numOfDiffGenes;
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

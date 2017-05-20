package sw1.filesimilarity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileIndex {
	private static final String ERROR = "[ERROR] ";
	private Map<File, Map<String, Integer>> files_indexes = new HashMap<>(); // index
																				// for
																				// each
																				// file
	private Map<File, Map<File, Double>> cosineSimilarity = new HashMap<>(); // (memoization)
																				// for
																				// each
																				// file
																				// cosineSimilarity
																				// with
																				// each
																				// other
																				// files.

	/**
	 * Given a path to a folder, reads all the files in it and indexes them
	 */
	public void index(String folderPath) {
		// first, clear the previous contents of the index
		clearPreviousIndex();
		File folder = new File(folderPath);
		File[] listFiles = folder.listFiles();
		for (File file : listFiles) {
			// for every file in the folder
			if (file.isFile()) {
				String path = file.getAbsolutePath();
				System.out.println("Indexing " + path);
				try {
					// add to the index if read is successful
					addFileToIndex(file);
				} catch (IOException e) {
					System.out.println(ERROR + "failed to read from " + path);
				}
			}
		}
	}

	/**
	 * Adds the input file to the index
	 */
	public void addFileToIndex(File file) throws IOException {
		List<String> tokens = FileUtils.readAllTokens(file);
		String path = file.getAbsolutePath();
		Integer temp_value;
		Map<String, Integer> temp_index = new HashMap<>();

		if (tokens.isEmpty()) {
			System.out.println(ERROR + "ignoring empty file " + path);
			return;
		}
		// else
		for (String token : tokens) {
			if (temp_index.containsKey(token)) {
				temp_value = temp_index.get(token);
				temp_index.put(token, temp_value + 1);
			} else
				temp_index.put(token, 1);
		}
		files_indexes.put(file, temp_index);
	}

	/**
	 * Called at the beginning of index() in order to clear the fields from
	 * previously indexed files. After calling it the index contains no files.
	 */
	public void clearPreviousIndex() {
		files_indexes = new HashMap<>();
		cosineSimilarity = new HashMap<>();

	}

	/**
	 * Given indexed input files, compute their cosine similarity based on their
	 * indexed tokens
	 */
	public double getCosineSimilarity(File file1, File file2) {
		if (!verifyFile(file1) || !verifyFile(file2)) {
			return Double.NaN;
		}
		if (cosineSimilarity.containsKey(file1)
				&& cosineSimilarity.get(file1).containsKey(file2)) // if we
																	// already
																	// calculate
																	// it before
			return (cosineSimilarity.get(file1).get(file2));
		else { // we have to calculate it
			Map<String, Integer> file1_Tokens = files_indexes.get(file1);
			Map<String, Integer> file2_Tokens = files_indexes.get(file2);
			int aw;
			int bw;
			double aw_2 = 0; // sum for aw^2
			double bw_2 = 0; // sum for bw^2
			int sum_up = 0; // sum for up side in fraction
			double output;

			for (String token : file1_Tokens.keySet()) {// for each token in
														// file1

				aw = file1_Tokens.get(token);
				aw_2 += Math.pow(aw, 2);

				if (file2_Tokens.containsKey(token)) {// file2 have also the
														// same token
					bw = file2_Tokens.get(token);
					sum_up += (aw * bw);
				}

			}
			for (String token : file2_Tokens.keySet()) { // iterate over file2
															// tokens to
															// calculate bw_2
				bw = file2_Tokens.get(token);
				bw_2 += Math.pow(bw, 2);
			}
			output = sum_up / ((Math.sqrt(aw_2) * Math.sqrt(bw_2)));
			// update cosineSimilarity for memoization
			Map<File, Double> temp_cosineSimilarity = new HashMap<>();
			temp_cosineSimilarity.put(file2, output);
			cosineSimilarity.put(file1, temp_cosineSimilarity); // update for
																// file1 key
			temp_cosineSimilarity.put(file1, output);
			cosineSimilarity.put(file2, temp_cosineSimilarity); // update for
																// file2 key
			return output;
		}
	}

	/**
	 * Given an indexed input file, returns a list of the other indexed files,
	 * ordered from the highest to lowest cosine similarity to the input file.
	 * The output list should not contain the input file itself.
	 */
	public List<File> getFilesBySimilarity(File file) {
		if (!verifyFile(file)) {
			return null;
		}
		List<File> files = new ArrayList<File>();
		for (File key_file : files_indexes.keySet()) { // create files list
			if (!key_file.equals(file))
				files.add(key_file);
		}
		Collections.sort(files, new FileComparator(file,this));
		Collections.reverse(files);
		return files;
	}

	/**
	 * returns true iff the input file is currently indexed. Otherwise, prints
	 * an error message.
	 */
	public boolean verifyFile(File file) {
		String path = file.getAbsolutePath();
		if (files_indexes.containsKey(file)) // file found
			return true;
		else {
			System.out.println(ERROR + "file '" + path
					+ "' doesn't exists in index ");
			return false;
		}
	}

	/**
	 * @return the number of files currently indexed.
	 */
	public int getNumIndexedFiles() {
		return files_indexes.size();
	}

}

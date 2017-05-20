package sw1.filesimilarity;

import java.io.File;
import java.util.List;

public class FileSimilarityTest {
	private static final String FOLDER = "simfiles";

	public static void main(String[] args) {
		FileIndex fileIndex = new FileIndex();
		fileIndex.index(FOLDER);
		System.out.println("Indexed " + fileIndex.getNumIndexedFiles() + " files.");

		File file1 = new File(FOLDER + File.separator + "file1.txt");
		List<File> list = fileIndex.getFilesBySimilarity(file1);
		System.out
				.println("similarity to " + file1.getAbsolutePath());
		for (File file2 : list) {
			System.out.printf("%s: %.3f%n", file2.getAbsolutePath(),
					fileIndex.getCosineSimilarity(file1, file2));
		}
	}
}

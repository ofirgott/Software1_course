package sw1.filesimilarity;

import java.io.File;
import java.util.Comparator;

public class FileComparator implements Comparator<File> {
	
	private FileIndex index;
	private File file;
	
	public FileComparator (File file, FileIndex index) {
		this.file = file;
		this.index = index;
	}
	
	@Override
	public int compare(File file1, File file2) {
		return (Double.compare(index.getCosineSimilarity(file, file1), index.getCosineSimilarity(file, file2)));
	}

}

package codingstyle;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.*;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class TextAnalyzer {

	private static Map<String, Integer> map;
	public static StringBuffer str;

	/**
	 * readFile(String fileName) will read the input file, and will map (to the
	 * variable map) for each token - how many times it appeared in the file
	 */

	private static void readFile(String fileName) throws IOException {
		Scanner scanner = new Scanner(new File(fileName));
		String token;
		Integer tokenCount;
		while (scanner.hasNext() == true) {
			token = scanner.next();
			tokenCount = map.get(token);
			if (tokenCount == null) {
				map.put(token, 1);
			} else
				map.put(token, tokenCount + 1);
		}
		scanner.close();
	}

	/**
	 * getOutputFieName() gets the name of the new file to be created (!= null)
	 */

	private static String getOutputFieName(Scanner scanner) {
		System.out.println("Please enter output file name:");
		String fileName = scanner.nextLine();
		while (fileName.isEmpty()) {
			System.out
					.println("Invalid file name ! Please re-enter output file name:\n");
			fileName = scanner.nextLine();
		}
		return (fileName);
	}

	/**
	 * createFile1() write the first requested output file: contains for each
	 * token in the input file the number of his appearances
	 */

	private static void createFile1(Scanner scanner) throws IOException {
		for (Entry<String, Integer> pair : map.entrySet()) {
			str.append(String.format("%s\t%d%n", pair.getKey(), pair.getValue()));
		}
		writeFile(scanner, str.toString());
	}

	/**
	 * createFile2() will write the second output file: contains all of the
	 * tokens in the input file, in lexicographic order
	 * 
	 * @throws IOException
	 */

	private static void createFile2(Scanner scanner) throws IOException {
		List<String> list = new ArrayList<String>(map.keySet());
		Collections.sort(list);
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			str.append(list.get(i) + "\r\n");
		}
		writeFile(scanner, str.toString());
	}

	/**
	 * writeFile() - write text to new output file
	 * 
	 * @throws IOException
	 */
	private static void writeFile(Scanner scanner, String content)
			throws IOException {
		String fileName = getOutputFieName(scanner);
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
				fileName));
		bufferedWriter.write(content);
		bufferedWriter.close();
	}

	public static void main(String[] args) throws IOException {
		if (args.length == 0) {
			System.out.println("Please specify input file as program argument");
			return;
		} else {
			map = new HashMap<String, Integer>();
			str = new StringBuffer();

			readFile(args[0]);

			Scanner scanner = new Scanner(System.in);
			createFile1(scanner);
			scanner = new Scanner(System.in);
			createFile2(scanner);

			scanner.close();
		}
	}

}

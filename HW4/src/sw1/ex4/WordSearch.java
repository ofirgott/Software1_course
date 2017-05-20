package sw1.ex4;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class WordSearch {
	// ex1
	public static String[] scanVocabulary(Scanner scanner) {
		String[] output = new String[3000];
		int counter = 0;
		String temp = "";
		while (scanner.hasNext() && counter < output.length) {
			temp = scanner.next().toLowerCase();
			if (IsWordLegal(temp) == true
					&& Arrays.asList(output).contains(temp) == false) {
				output[counter] = temp;
				counter++;
			}
		}
		if (NumOfArrayNonNullValues(output) == output.length) {
			Arrays.sort(output);
			return output;
		} else { // array has less than 3000 unique words
			String[] smaller_output = Arrays.copyOf(output,
					NumOfArrayNonNullValues(output));
			Arrays.sort(smaller_output);
			return smaller_output;
		}
	}

	public static boolean IsWordLegal(String word) {
		if (word.length() == 0)
			return false;
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) > 'z' || word.charAt(i) < 'a')
				return false;
		}
		return true;
	}

	public static int NumOfArrayNonNullValues(String[] array) {
		/**
		 * @return Counts non-Null value in String array
		 */
		int counter = 0;
		for (int i = 0; i < array.length; i++)
			if (array[i] != null)
				counter++;
		return counter;
	}

	// ex2
	public static void printWords(String[] vocabulary, String firstLetter,
			String secondLetter, String thirdLetter) {
		int counter = 0;
		boolean secondLetterOK = false, thirdLetterOK = false;
		char CharSecond = secondLetter.charAt(0), CharThird = thirdLetter
				.charAt(0); // Convert to primitive char to work with CharAt
		for (String string : vocabulary) {
			secondLetterOK = false;
			thirdLetterOK = false;
			if (string.length() >= 3 && string.startsWith(firstLetter)
					&& string.contains(secondLetter)
					&& string.contains(thirdLetter)) {
				if (CharSecond != CharThird) {
					for (int i = 1; i < string.length(); i++) { // check from
																// second
																// char
						if (string.charAt(i) == CharSecond)
							secondLetterOK = true;

						else {
							if (string.charAt(i) == CharThird
									&& secondLetterOK == true) {
								thirdLetterOK = true;
								break;
							}
						}
					}
				} else { // char2==char3, so we check if string has 2 times of
							// it (exclude first char)
					StringBuilder temp = new StringBuilder();
					temp.append(string);
					temp.deleteCharAt(0); // delete first char that we already
											// checked
					if (temp.lastIndexOf(secondLetter) != temp
							.indexOf(secondLetter)) {
						secondLetterOK = true;
						thirdLetterOK = true;
					}
				}
				if (thirdLetterOK == true && secondLetterOK == true) {
					counter++;
					System.out.println(string);
				}
			}
		}
		System.out.format("found %d words%n", counter);
	}

	// ex3
	private static String[] IsLegal(String[] splitted) {
		StringBuilder sb = new StringBuilder("");
		StringBuilder az_err = new StringBuilder("");
		StringBuilder non_single_chars = new StringBuilder("");
		boolean OK = true;
		String[] output = new String[2];
		for (String string : splitted) {
			if (string.length() > 1)
				non_single_chars.append(string + ",");
			for (int i = 0; i < string.length(); i++) {
				if (string.charAt(i) < 'a' || string.charAt(i) > 'z') {
					az_err.append(string + ",");
					break;
				}
			}
		}
		if (az_err.length() != 0) { // checking a-z
			az_err.setLength(az_err.length() - 1); // delete 2
													// last
													// chars
													// (", ")

			OK = false;
			sb.append("[WARNING] Expecting only letters between a-z but got: "
					+ az_err + "\n");
		}

		if (splitted.length != 3) { // checking length
			OK = false;
			sb.append("[WARNING] Expecting 3 letters but got: "
					+ splitted.length + "\n");

		}
		if (non_single_chars.length() != 0) { // checking non-single chars
			non_single_chars.setLength(non_single_chars.length() - 1);
			OK = false;
			sb.append("[WARNING] Expected a single letter but got: "
					+ non_single_chars + "\n");
		}
		if (OK)
			output[0] = "TRUE";
		else {
			output[0] = "FALSE";
			output[1] = sb.toString();
		}
		return output;
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 1)
			throw new Exception("[Error] "
					+ "One argument was expected but got: " + args.length);
		if (args.length == 0)
			throw new Exception("[Error] " + "You didn't enter filename!");
		String FILE_NAME = args[0];
		Scanner scanner = new Scanner(new File(FILE_NAME));
		String[] Vocabulary = scanVocabulary(scanner);
		scanner.close();
		System.out.format("Read %d words from %s%n", Vocabulary.length,
				FILE_NAME);
		Scanner s = new Scanner(System.in);
		System.out.println("Enter 3 letters or \"exit\"");
		while (s.hasNextLine() && s.hasNext("exit") == false) {
			String[] values = s.nextLine().split(" ");
			if (IsLegal(values)[0].equals("FALSE"))
				System.out.println(IsLegal(values)[1]); // IsLegal will put
														// error messages in the
														// 2nd value of returned
														// array
			else
				// no error
				printWords(Vocabulary, values[0], values[1], values[2]);

			System.out.println("Enter 3 letters or \"exit\"");
		}
		s.close();
	}

}
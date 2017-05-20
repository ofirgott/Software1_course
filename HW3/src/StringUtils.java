import java.util.Arrays;

public class StringUtils {

	// ex6
	public static boolean isPalindrome(String str) {
		int i = 0, j = str.length() - 1;
		while (i < j) {
			if (str.charAt(i) != str.charAt(j)) {
				return false;
			}
			i++;
			j--;
		}
		return true;
	}

	// ex7
	public static String sortStringWords(String str) {
		String[] parsed = str.split(" ");

		String output = "";
		Arrays.sort(parsed);
		for (int i = 0; i < parsed.length - 1; i++) {
			output = output + parsed[i] + " ";
		}
		output = output + parsed[parsed.length - 1];
		return output;
	}

	// ex8
	public static boolean isStringArraySorted(String[] strs, int n) {
		String[] copy_n = CopyFirstnChars(strs, n); // copying n chars of every
													// string in the array
		ChangeArrayStringsToLowerCase(copy_n);
		String[] sorted_copy_n = Arrays.copyOf(copy_n, copy_n.length);
		Arrays.sort(sorted_copy_n);
		if (StringsArraysEquals(copy_n, sorted_copy_n) == true)
			return true;
		else
			return false;
	}

	private static void ChangeArrayStringsToLowerCase(String[] copy_n) {
		for (int i = 0; i < copy_n.length; i++) {
			copy_n[i] = copy_n[i].toLowerCase();
		}

	}

	private static String[] CopyFirstnChars(String[] strs, int n) {
		String[] output = new String[strs.length];
		for (int i = 0; i < strs.length; i++) {
			output[i] = strs[i].substring(0, n);
		}
		return output;
	}

	public static boolean StringsArraysEquals(String[] copy, String[] sorted) {
		for (int i = 0; i < sorted.length; i++) {
			if (copy[i].equals(sorted[i]) == false) {
				return false;
			}
		}
		return true;
	}

	// ex9
	public static int[] stringHistogram(String a) {
		int[] output = new int[26];
		String a2 = a.toLowerCase();
		for (int i = 0; i < a2.length(); i++) {
			output[a2.charAt(i) - 97]++;
		}
		return output;
	}

	// ex10
	public static boolean areAnagrams(String a, String b) {
		String a2 = a.replaceAll("\\s", ""); // delete spaces
		String b2 = b.replaceAll("\\s", "");
		int[] his_a2 = stringHistogram(a2);
		int[] his_b2 = stringHistogram(b2);
		if (Arrays.toString(his_a2).equals(Arrays.toString(his_b2))) {
			return true;
		} else {
			return false;
		}
	}

}
import java.util.Arrays;

public class ArrayUtils {

	// ex1
	public static int[] reverseArray(int[] array) {
		int[] newArray = new int[array.length];
		for (int i = 0; i < array.length; i++) {
			newArray[array.length - i - 1] = array[i];
		}
		return newArray;
	}

	// ex2
	public static String[] fillStringArray(String[] strs, int[] repeats) {
		int sum = 0, index = 0;
		for (int num : repeats) {
			sum += num;
		}
		String[] out_strs = new String[sum];

		for (int i = 0; i < strs.length; i++) {
			for (int j = 0; j < repeats[i]; j++) {
				out_strs[index] = strs[i];
				index++;
			}

		}
		return out_strs;
	}

	// ex3
	public static int[][] transposeMatrix(int[][] matrix) {
		int m = MatrixDim(matrix)[0]; // rows
		int n = MatrixDim(matrix)[1]; // Columns
		int[][] matrix_T = BuildEmptyTransposeMat(matrix, m, n);
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				matrix_T[j][i] = matrix[i][j];
			}
		}
		return matrix_T;

	}

	public static int[] MatrixDim(int[][] matrix) {
		return new int[] { matrix.length, matrix[0].length };
	}

	public static int[][] BuildEmptyTransposeMat(int[][] matrix, int m, int n) {
		int[][] matrix_T = new int[n][m];
		return matrix_T;
	}

	// ex4
	public static int[][] splitArrayByNum(int[] input, int number) {
		if (ContainsOnlyDelimiters(input, number) == true) {
			return (new int[1][1]);
		}
		int[][] indexes = IndexofSplitedArrays(input, number);
		int[][] output = new int[indexes.length][];

		for (int i = 0; i < indexes.length; i++) {
			output[i] = Arrays.copyOfRange(input, indexes[i][0],
					indexes[i][1] + 1);
		}
		return output;
	}

	public static int NumOfsplitedArrays(int[] input, int number) {
		int i = 0, sum = 0;
		boolean legal = false;
		while (i < input.length - 1) {
			if (input[i] != number) { // regular number
				legal = true;

			} else { // delimiter
				if (legal == true && input[i + 1] != number) { // check if real
					// delimiter
					sum++;
					legal = false;
				}

			}

			i++;
		}
		sum++; // for the last sub-array (we don't really have to check the last
				// number)
		return sum;
	}

	public static int[][] IndexofSplitedArrays(int[] input, int number) {
		// returns array of indexes pairs - start and end for every sub-array
		int[][] indexes = new int[NumOfsplitedArrays(input, number)][2];
		int i = 0, start = 0, end = 0, counter = 0, curr_len = 0;
		boolean legal = false;
		while (i < input.length) {
			if (input[i] == number && legal == true) {
				end = i - 1;
				indexes[counter][0] = start;
				indexes[counter][1] = end;
				counter++;
				legal = false;
				curr_len = 0;
			} else {
				if (input[i] == number && legal == false) {
					legal = false;

				} else { // non-delimiter number
					if (curr_len == 0) { // start of new sub-string
						start = i;
					}
					legal = true;
					curr_len++;

				}
			}
			i++;
		}
		if (legal == true) { // end of array with no delimiter
			indexes[counter][0] = start;
			indexes[counter][1] = i - 1;
		}

		return indexes;

	}

	public static boolean ContainsOnlyDelimiters(int[] input, int number) {
		for (int i = 0; i < input.length; i++) {
			if (input[i] != number) {
				return false;
			}
		}
		return true;
	}

	// ex5
	public static double matrixMean(int[][] m) {
		int counter = 0;
		double sum = 0.0;
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[i].length; j++) {
				sum = sum + m[i][j];
				counter++;
			}
		}
		return sum / counter;
	}

}
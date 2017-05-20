import java.util.Arrays;

public class Assignment02Q05 {
	public static void main(String[] args) {
		String[][] output = new String[args.length][];
		for (int i = 0; i < args.length; i++) {
			if (i == args.length - 1) {
				output[i] = new String[args[0].length()];
			} else {
				output[i] = new String[args[i + 1].length()];
			}
			for (int j = 0; j < output[i].length; j++) {
				output[i][j] = args[i];
			}
		}

		for (String[] strings : output) {
			System.out.println(Arrays.toString(strings));
		}
	}

}

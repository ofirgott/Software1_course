import java.util.Arrays;

public class Assignment02Q04 {
	public static void main(String[] args) {
		Integer[] nums = new Integer[args.length];
		for (int i = 0; i < args.length; i++) {
			nums[i] = Integer.parseInt(args[i]);
		}

		for (int i = 0; i < nums.length; i++) {
			for (int j = 0; j < nums.length; j++) {

				if (Arrays.asList(nums).indexOf(nums[i] + nums[j]) != -1) {
					System.out.println(nums[i] + "+" + nums[j] + "="
							+ (nums[i] + nums[j]));
				}
			}
		}
	}
}

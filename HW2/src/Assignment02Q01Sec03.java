import java.util.Arrays;

public class Assignment02Q01Sec03 {
	public static void main(String[] args) {
		long[] nums = new long[args.length];
		for (int i = 0; i < nums.length; i++) {
			nums[i] = Math.round(Double.parseDouble(args[i]));
		}
		Arrays.sort(nums);
		System.out.println(nums[0]);
	}

}

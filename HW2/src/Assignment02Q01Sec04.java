public class Assignment02Q01Sec04 {
	public static void main(String[] args) {
		int[] nums = new int[args.length];
		for (int i = 0; i < args.length; i++) {
			nums[i] = Integer.parseInt(args[i]);
		}
		for (int n : nums) {
			System.out.println(n % 11);
		}
	}

}

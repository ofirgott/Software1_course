public class Assignment02Q01Sec05 {
	public static void main(String[] args) {
		int[] nums = new int[args.length];
		for (int i = 0; i < args.length; i++) {
			nums[i] = Integer.parseInt(args[i]);
		}
		int first = nums[0], check = 0;
		for (int i = 1; i < nums.length; i++) {
			if (first == nums[i]) {
				check = 1;
				break;
			}
		}
		if (check == 1) {
			System.out.println("yes");
		} else {
			System.out.println("no");
		}

	}
}

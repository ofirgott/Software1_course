public class Assignment02Q03 {
	public static void main(String[] args) {
		String out = "";
		int counter = 0;
		for (int i = 0; i < args.length; i++) {
			for (int j = 0; j < args[i].length(); j++) {
				counter = 1;
				for (int z = 0; z < args.length; z++) {
					if ((i != z) && (args[z].indexOf(args[i].charAt(j)) != -1)) {
						counter++;
					}
				}
				out = out + args[i].charAt(j) + "=" + counter + " ";
			}
		}

		System.out.println(out.substring(0, out.length() - 1));
	}
}

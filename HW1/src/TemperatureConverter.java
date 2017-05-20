public class TemperatureConverter {
	
	public static void main (String[] args) {
		int c = Integer.parseInt(args[0]);
		double fr = c * 9.0/5 + 32;
		System.out.println(c + "C => " + fr + "F");
	}
}

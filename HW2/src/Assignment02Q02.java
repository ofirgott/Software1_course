public class Assignment02Q02 {
	public static void main(String[] args) {
		float weight = Float.parseFloat(args[0]);
		float height = Float.parseFloat(args[1]);
		float BMI = weight / (height * height);
		if (BMI < 18.5) {
			System.out.println("Underweight");
		}
		if (18.5 <= BMI && BMI <= 25) {
			System.out.println("Normal weight");
		}
		if (25 < BMI && BMI <= 30) {
			System.out.println("Overweight");
		}
		if (30 < BMI) {
			System.out.println("Obesity");
		}

	}
}

// check git commit
public class Assignment02Q02 {
	public static void main (double[] args){
		double weight = args[0];
		double heigt = args[1];
		double BMI = weight / (heigt*heigt);
		if (BMI<18.5)
			System.out.println("Underweight");
		else if ((BMI>=18.5) && (BMI<=25)) 
				System.out.println("Normal weight");
		else if (BMI<30)
			System.out.println("Overweight");
		else //BMI>=30
			System.out.println("Obesity");				
	}

}



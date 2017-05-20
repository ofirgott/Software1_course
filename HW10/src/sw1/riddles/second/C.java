package sw1.riddles.second;

import java.util.Random;

public class C {

	public static void main(String[] args) {
		String input = args[0]; 						//input is first arg
		B b = new B();									//create b instance
		Random random = new Random();
		boolean randomBool = random.nextBoolean();		//random bool
		A a = b.getA(randomBool);
		
		if (randomBool) {								//random bool is true
			if (!input.equals(a.foo(input))) {			//if input != a.foo(input)
				return;
			}
		} else {
			if (!(input+input).equals(a.foo(input))) {	//if input+input != input
				return;
			}
		}
		System.out.println("success!!!!!!");
	}
}

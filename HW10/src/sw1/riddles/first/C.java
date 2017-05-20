package sw1.riddles.first;

public class C {
	private static int secret = 0;

	public int foo() {
		return ++secret;
	}

	public static void main(String[] args) {
		C c = new C();
		for (int i = 0; i < args.length; i++) {
			c.foo();
		}
		A a = new B();
		if (a.foo() == secret) {
			System.out.println("success!");
		
		} else System.out.println("bad");
	}
}

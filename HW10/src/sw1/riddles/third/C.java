package sw1.riddles.third;

public class C extends B {

	public int foo = 100;

	public C(String s) {
		super(s);
	}

	@Override
	public void barbar() {
		foo++;
	}

	public static void main(String[] args) {
		String input = args[0];
		StringBuilder builder = new StringBuilder(input);
		C c = new C(input);
		B b = c;
		A a = b;

		for (int i = 0; i < args.length; i++) {
			if (!builder.toString().equals(a.bar())) {
				 System.out.println("noooo " + builder.toString() + "," + a.bar() + ":" + "");
				return;
				
			}
			//else System.out.println("noooo " + builder.toString() + "," + a.bar());
			builder.append(input);
			c.foo++;
		}

		if (c.foo - 99 == b.foo.length() / input.length()) { //needed : b.foo.length() = input.length(), c.foo =100
			System.out.println("noooo " + c.foo + "," + b.foo.length() + ",");
			System.out.println("success!");
			
		
		}else System.out.println("fuckk");
	}
}

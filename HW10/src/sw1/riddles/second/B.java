package sw1.riddles.second;

public class B extends A {

	@Override
	public String foo(String s) {
		return s + s;
	}

	protected A getA(boolean randomBool) {
		if (randomBool) {
			A a = new A();
			return a;
		} else {
			B b = new B();
			return b;
		}
	}
}

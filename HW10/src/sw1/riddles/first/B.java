package sw1.riddles.first;

public class B extends A{
	protected int foo() {
		C c = new C();
		return c.foo();
	}
}

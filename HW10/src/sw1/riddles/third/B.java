package sw1.riddles.third;

public class B implements A {

	private String input;
	public String foo;

	public B(String s) {
		this.input = s;
		this.foo = s;
	}

	@Override
	public String bar() {
		foo += input;
		return foo.substring(input.length());
	}

	public void barbar() {
		return;
	}
}

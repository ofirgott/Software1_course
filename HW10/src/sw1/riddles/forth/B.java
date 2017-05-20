package sw1.riddles.forth;

public class B extends A {

	public String s1;
	public String s2;

	public B(String s1, String s2) {
		super(s1, s2);
	}
	
	public B(){
        super(null,null);
    }

	@Override
	public int hashCode() {
		return 1;
	}

	@Override
	public boolean equals(Object obj) {
		return true;
	}
	
	

}

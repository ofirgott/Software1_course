package sw1.riddles.forth;

import java.util.HashSet;
import java.util.Set;

public class C extends B {
	public static void main(String[] args) {
		Set<A> set = new HashSet<A>();
		int size = 0;
		for (int i = 0; i < args.length - 1; i+=2) {
			A a = new B(args[i], args[i + 1]);
			A a2 = new B(args[i + 1], args[i]);
			set.add(a);
			size = set.size();
			set.add(a2);
			if (set.size() != size) {
				return;
			}
		}
		System.out.println("successsssssssmotharmam!");
	}
}

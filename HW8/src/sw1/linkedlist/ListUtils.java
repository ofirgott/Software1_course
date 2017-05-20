package sw1.linkedlist;

public class ListUtils {

	public static LinkedListNode findTwoThirdsNode(LinkedListNode head) {

		LinkedListNode output;
		LinkedListNode curr;
		int counter; // nodes counter

		// check if 2n/3 solution is even or odd
		// number - if list has 2 nodes - 1, 3/4
		// nodes - 2, 5 nodes 3 etc..

		if (head.getNext() == null) // if have only one node, return it
			return head;

		curr = head.getNext();
		output = head;
		counter = 2;
		while (curr != null) {
			if (counter % 3 != 0 && curr.getNext() != null)
				output = output.getNext();
			counter++;
			curr = curr.getNext();
		}
		return output;
	}

	public static LinkedListNode shiftList(LinkedListNode head, int n) {
		LinkedListNode curr;
		LinkedListNode new_head;

		if (n == 0 || head.getNext() == null)
			return head;
		curr = head;

		for (int j = 1; j < n; j++) { // n <= list.length
			curr = curr.getNext();
		}
		new_head = curr.getNext();

		if (curr.getNext() == null) // n==list.length
			return head;
		while (curr.getNext() != null)
			curr = curr.getNext();
		curr.setNext(head);

		for (int i = 0; i < n; i++)
			curr = curr.getNext();
		curr.setNext(null);

		return new_head;
	}

	public static LinkedListNode mergeLists(LinkedListNode head1,
			LinkedListNode head2) {
		LinkedListNode temp;
		LinkedListNode output_head;
		LinkedListNode curr1 = head1;
		LinkedListNode curr2 = head2;
		boolean head1_is_bigger = false;	//check with which list to begin
		
		if (head1.getValue() >= head2.getValue()){
			output_head = head1; // head1 is the new head
			curr1 = curr1.getNext();
			temp = output_head;
			temp.setNext(head2);	//head2 is second
			curr2 = curr2.getNext();
			head1_is_bigger = true;
		}
			
		else {
			output_head = head2; // head2 is the new head
			curr2 = curr2.getNext();
			temp = output_head;
			temp.setNext(head1);	//head2 is second
			curr1 = curr1.getNext();
		}
		
		while (curr1 != null && curr2 != null ){
			temp = temp.getNext();
			if (head1_is_bigger){
				temp.setNext(curr1);
				curr1 = curr1.getNext();
				temp = temp.getNext();
				temp.setNext(curr2);
				curr2 = curr2.getNext();
			}
			else{ //head2 is bigger
				temp.setNext(curr2);
				curr2 = curr2.getNext();
				temp = temp.getNext();
				temp.setNext(curr1);
				curr1 = curr1.getNext();
			}
		}
		while (curr1 != null){
			temp = temp.getNext();
			temp.setNext(curr1);
			curr1 = curr1.getNext();
		}
		
		while (curr2 != null){
			temp = temp.getNext();
			temp.setNext(curr2);
			curr2 = curr2.getNext();
		}
		
		return output_head;
	}

	public static void main(String[] args) {
		LinkedListNode a = new LinkedListNode(1);
		LinkedListNode b = new LinkedListNode(2);
		LinkedListNode c = new LinkedListNode(3);
		LinkedListNode d = new LinkedListNode(4);
		LinkedListNode e = new LinkedListNode(5);
		LinkedListNode f = new LinkedListNode(6);
		LinkedListNode g = new LinkedListNode(7);
		LinkedListNode h = new LinkedListNode(8);
		LinkedListNode i = new LinkedListNode(9);
		// LinkedListNode j = new LinkedListNode(10);
		// LinkedListNode k = new LinkedListNode(11);
		// LinkedListNode m = new LinkedListNode(12);
		a.setNext(b);
		b.setNext(c);
		c.setNext(d);
		d.setNext(e);
		e.setNext(f);
		f.setNext(g);
		g.setNext(h);
		h.setNext(i);
		// i.setNext(j);
		// j.setNext(k);
		// k.setNext(m);

		// System.out.println(findTwoThirdsNode(a).getValue());
		LinkedListNode n = shiftList(a, 9);
		while (n != null) {
			System.out.println(n.getValue());
			n = n.getNext();
		}

	}
}

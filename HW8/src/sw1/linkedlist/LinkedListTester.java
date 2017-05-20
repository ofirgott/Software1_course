package sw1.linkedlist;

import java.util.Random;

public class LinkedListTester {
	public static void main (String[] args) {
		
		Random rand = new Random();
		
		// findTwoThirds
		System.out.println("\n##### findTwoThirdsNode test: #####");
		int n = rand.nextInt(10)+1;
		LinkedListNode[] LLNarray = generateList(n);
		System.out.print("first linked list:\n");
		printList(LLNarray);
		int index = Math.floorDiv(2*n, 3);
		if (n==1)
			index = 1;
		System.out.println("floored 2/3 index: " + index);
		System.out.println("expected value: " + LLNarray[index-1].getValue());
		System.out.println("findTwoThirdsNode result: " + ListUtils.findTwoThirdsNode(LLNarray[0]).getValue());
		
		// shiftList
		System.out.println("\n##### shiftlist test: #####");
		System.out.println("second linked list:");
		int m = rand.nextInt(10)+1;
		LinkedListNode[] LLNarray3 = generateList(m);
		printList(LLNarray3);
		int a = rand.nextInt(m);
		System.out.println("number of shifts: " + a);
		LinkedListNode shiftedHead = ListUtils.shiftList(LLNarray3[0], a);
		System.out.println("shifted list:");
		printByHead(shiftedHead);
		// mergeLists
		System.out.println("\n##### mergeLists test: #####");
		System.out.println("first list: ");
		printList(LLNarray);
		System.out.println("second list: ");
		int k = rand.nextInt(10)+1;
		LinkedListNode[] LLNarray2 = generateList(k);
		printList(LLNarray2);
		LinkedListNode newHead = ListUtils.mergeLists(LLNarray[0], LLNarray2[0]);
		System.out.println("\nmerged list:");
		printByHead(newHead);
		System.out.println("\nDONE");
		
	}
	
	private static void printList(LinkedListNode[] LLNarray){
		int len = LLNarray.length; 
		System.out.print("[");
		for (int i=0; i<len-1; i++){
			System.out.print(LLNarray[i].getValue() + ", ");
		}
		System.out.print(LLNarray[len-1].getValue() + "]");
		System.out.println("\n***number of items: " + len+"***");
	}
	
	private static LinkedListNode[] generateList(int n){
		LinkedListNode[] LLNarray = new LinkedListNode[n];
		Random rand = new Random();
		for (int i=0; i<n; i++){
			int m = rand.nextInt(51);
			LLNarray[i] = new LinkedListNode(m);
		}
		for (int i=0; i<n-1; i++){
			LLNarray[i].setNext(LLNarray[i+1]);
		}
		LLNarray[n-1].setNext(null);
		return LLNarray;
		
	}
	private static void printByHead (LinkedListNode head){
		System.out.print("[" + head.getValue());
		LinkedListNode node = head.getNext();
		int cnt = 1;
		if (node != null){
			System.out.print(", ");
			while (node.getNext()!=null){
				System.out.print(node.getValue() + ", ");
				node = node.getNext();
				cnt++;
			}
			System.out.print(node.getValue() + "]");
			cnt++;
		}
		else
			System.out.print("]");
		
		System.out.println("\nnumber of items: " + cnt);
		
	}

}

import crypto.attack.Differential;
import crypto.attack.bfGOST28147;
import crypto.math.BooleanFunctionNormalForm;

public class Driver5 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		int i = Integer.valueOf(args[0]);
		System.out.println("Result of " + i);
		new BooleanFunctionNormalForm(new bfGOST28147(i), System.out);
		System.out.println("order " + 4);
		new Differential(new bfGOST28147(i), 4);
		long end = System.currentTimeMillis();
		System.out.println("Total running time is " + (end - start) + " ms!");
	}
}

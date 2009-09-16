import crypto.attack.differential.higher.AttackSpecial;
import crypto.attack.differential.higher.CAST5;

public class Driver4 {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		new AttackSpecial(new CAST5());
		long end = System.currentTimeMillis();
		System.out.println("Total running time is " + (end - start) + " ms!");
	}

}

package crypto.attack.test;

public class AttackTestDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// new BooleanFunctionNormalForm(new CAST5(0, 1), System.out);
		// new BooleanFunctionNormalForm(new DES(0, 1), System.out);
		//new Differential(new DES(0, 1), 12);
		// for (int j = 0; j < 8; ++j)
		// for (int i = 1; i <= 8; ++i) {
		// System.out.println(j + " " + i);
		// new BooleanFunctionNormalForm(new CAST5(j, i), System.out);
		// }
		// new Differential(new CAST5(0, 1), 3);
		
		CAST5 c = new CAST5(0, 1);
		for(int i = 0; i < 255; ++i)
			System.out.println(c.c(i));
	}
}

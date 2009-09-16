/**
 * 
 */
package crypto.attack;

import crypto.util.FunctionType;

/**
 * @author Isaac
 * 
 */
public class BooleanFunction implements FunctionType {

	private int[] arrayTable = null;
	private int key = 0;

	/**
	 * 
	 */
	public BooleanFunction(int _key) {
		// TODO Auto-generated constructor stub
		key = _key;
		arrayTable = new int[16];
		int[] tmp = new int[4];
		for (int i = 0; i < 16; ++i) {
			int t = 0, j = i ^ key;
			while (j > 0) {
				tmp[t++] = j % 2;
				j = j >> 1;
			}
			for (j = t; j < 4; ++j)
				tmp[j] = 0;
			arrayTable[i] = (tmp[0] * tmp[1] * tmp[2])
					^ (tmp[0] * tmp[1] * tmp[3]) ^ (tmp[1] * tmp[2] * tmp[3]);
			// System.out.print(i + " = ");
			// for (j = 3; j >= 0; --j)
			// System.out.print(tmp[j]);
			// System.out.print(" " + tmp[0] * tmp[1] * tmp[2]);
			// System.out.print(" " + tmp[0] * tmp[1] * tmp[3]);
			// System.out.print(" " + tmp[1] * tmp[2] * tmp[3]);
			// System.out.println(" " + arrayTable[i]);
		}
	}

	public BooleanFunction() {
		arrayTable = new int[16];
		int[] tmp = new int[4];
		for (int i = 0; i < 16; ++i) {
			int t = 0, j = i;
			while (j > 0) {
				tmp[t++] = j % 2;
				j = j >> 1;
			}
			for (j = t; j < 4; ++j)
				tmp[j] = 0;
			arrayTable[i] = (tmp[0] * tmp[1] * tmp[2])
					^ (tmp[0] * tmp[1] * tmp[3]) ^ (tmp[1] * tmp[2] * tmp[3]);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see crypto.attack.functionType#calc(int)
	 */
	public int calc(int input) {
		// TODO Auto-generated method stub
		return arrayTable[input];
	}

	public int getN() {
		return 4;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BooleanFunction f = new BooleanFunction();
		for (int i = 0; i < 16; ++i)
			System.out.println(crypto.common.BasicMethod.toBitString(i, 4)
					+ " " + f.calc(i));
		// int[] tmp = new int[4];
		// for (int i = 0; i < 16; ++i) {
		// int t = 0, j = i;
		// while (j > 0) {
		// tmp[t++] = j % 2;
		// j = j >> 1;
		// }
		// for (j = t; j < 4; ++j)
		// tmp[j] = 0;
		// for (j = 3; j >= 0; --j)
		// System.out.print(tmp[j]);
		// System.out.println(" " + f.calc(i));
		// }
		//
		// System.out.println("0 ^ 0 = " + (0 ^ 0));
		// System.out.println("0 ^ 1 = " + (0 ^ 1));
		// System.out.println("1 ^ 0 = " + (1 ^ 0));
		// System.out.println("1 ^ 1 = " + (1 ^ 1));
	}
}

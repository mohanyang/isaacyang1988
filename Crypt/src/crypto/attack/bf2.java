/**
 * 
 */
package crypto.attack;

import crypto.util.FunctionType;

/**
 * @author Isaac
 * 
 */
public class bf2 implements FunctionType {

	private int[] arrayTable = null;
	private static int n = 5;

	public bf2() {
		arrayTable = new int[1 << n];
		int[] tmp = new int[n];
		for (int i = 0; i < (1 << n); ++i) {
			int t = 0, j = i;
			while (j > 0) {
				tmp[t++] = j & 1;
				j >>= 1;
			}
			for (j = t; j < 4; ++j)
				tmp[j] = 0;
			arrayTable[i] = (tmp[0] * tmp[1] * tmp[2])
					^ (tmp[0] * tmp[1] * tmp[3]) ^ (tmp[0] * tmp[2] * tmp[3])
					^ (tmp[1] * tmp[2] * tmp[3]) ^ (tmp[1] * tmp[2] * tmp[4]);
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
		return n;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		bf2 f = new bf2();
		for (int i = 0; i < (1 << n); ++i)
			System.out.println(crypto.common.BasicMethod.toBitString(i, n)
					+ " " + f.calc(i));
	}
}

/**
 * 
 */
package crypto.attack;

import crypto.util.FunctionType;

/**
 * @author Isaac
 * 
 */
public class bf5_y3 implements FunctionType {

	private int[] arrayTable = null;
	private static int n = 8;

	public bf5_y3() {
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

			arrayTable[i] = (tmp[4] * tmp[3] * tmp[2] * tmp[0])
					^ (tmp[4] * tmp[3] * tmp[2] * tmp[1])
					^ (tmp[5] * tmp[3] * tmp[2] * tmp[1])
					^ (tmp[5] * tmp[4] * tmp[2] * tmp[1])
					^ (tmp[5] * tmp[4] * tmp[3] * tmp[1])
					^ (tmp[5] * tmp[4] * tmp[3] * tmp[2])
					^ (tmp[6] * tmp[4] * tmp[2] * tmp[0])
					^ (tmp[6] * tmp[4] * tmp[2] * tmp[1])
					^ (tmp[6] * tmp[4] * tmp[3] * tmp[1])
					^ (tmp[6] * tmp[4] * tmp[3] * tmp[2])
					^ (tmp[6] * tmp[5] * tmp[3] * tmp[1])
					^ (tmp[6] * tmp[5] * tmp[3] * tmp[2])
					^ (tmp[6] * tmp[5] * tmp[4] * tmp[1])
					^ (tmp[6] * tmp[5] * tmp[4] * tmp[3])
					^ (tmp[7] * tmp[4] * tmp[3] * tmp[0])
					^ (tmp[7] * tmp[4] * tmp[3] * tmp[1])
					^ (tmp[7] * tmp[5] * tmp[2] * tmp[0])
					^ (tmp[7] * tmp[5] * tmp[2] * tmp[1])
					^ (tmp[7] * tmp[5] * tmp[4] * tmp[0])
					^ (tmp[7] * tmp[5] * tmp[4] * tmp[1])
					^ (tmp[7] * tmp[5] * tmp[4] * tmp[2])
					^ (tmp[7] * tmp[6] * tmp[2] * tmp[0])
					^ (tmp[7] * tmp[6] * tmp[3] * tmp[0])
					^ (tmp[7] * tmp[6] * tmp[4] * tmp[0])
					^ (tmp[7] * tmp[6] * tmp[4] * tmp[1])
					^ (tmp[7] * tmp[6] * tmp[4] * tmp[2])
					^ (tmp[7] * tmp[6] * tmp[5] * tmp[0])
					^ (tmp[7] * tmp[6] * tmp[5] * tmp[1])
					^ (tmp[7] * tmp[6] * tmp[5] * tmp[2])
					^ (tmp[7] * tmp[6] * tmp[5] * tmp[4])
					^ (tmp[4] * tmp[3] * tmp[1]) ^ (tmp[5] * tmp[2] * tmp[1])
					^ (tmp[5] * tmp[3] * tmp[2]) ^ (tmp[5] * tmp[4] * tmp[0])
					^ (tmp[5] * tmp[4] * tmp[1]) ^ (tmp[6] * tmp[2] * tmp[1])
					^ (tmp[6] * tmp[3] * tmp[0]) ^ (tmp[6] * tmp[3] * tmp[1])
					^ (tmp[6] * tmp[4] * tmp[0]) ^ (tmp[6] * tmp[4] * tmp[2])
					^ (tmp[6] * tmp[4] * tmp[3]) ^ (tmp[6] * tmp[5] * tmp[1])
					^ (tmp[6] * tmp[5] * tmp[2]) ^ (tmp[6] * tmp[5] * tmp[3])
					^ (tmp[6] * tmp[5] * tmp[4]) ^ (tmp[7] * tmp[2] * tmp[0])
					^ (tmp[7] * tmp[2] * tmp[1]) ^ (tmp[7] * tmp[3] * tmp[0])
					^ (tmp[7] * tmp[3] * tmp[1]) ^ (tmp[7] * tmp[4] * tmp[0])
					^ (tmp[7] * tmp[4] * tmp[2]) ^ (tmp[7] * tmp[5] * tmp[1])
					^ (tmp[7] * tmp[6] * tmp[2]) ^ (tmp[7] * tmp[6] * tmp[4])
					^ (tmp[7] * tmp[6] * tmp[5]) ^ (tmp[1] * tmp[0])
					^ (tmp[2] * tmp[0]) ^ (tmp[2] * tmp[1]) ^ (tmp[3] * tmp[2])
					^ (tmp[4] * tmp[0]) ^ (tmp[4] * tmp[1]) ^ (tmp[4] * tmp[3])
					^ (tmp[5] * tmp[2]) ^ (tmp[5] * tmp[4]) ^ (tmp[6] * tmp[1])
					^ (tmp[6] * tmp[2]) ^ (tmp[6] * tmp[3]) ^ (tmp[6] * tmp[4])
					^ (tmp[7] * tmp[2]) ^ (tmp[7] * tmp[3]) ^ (tmp[7] * tmp[4])
					^ (tmp[7] * tmp[6]) ^ (tmp[2]) ^ (tmp[3]) ^ (tmp[5])
					^ (tmp[6]) ^ (tmp[7]);
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
		bf5_y3 f = new bf5_y3();
		for (int i = 0; i < (1 << n); ++i)
			System.out.println(crypto.common.BasicMethod.toBitString(i, n)
					+ " " + f.calc(i));
	}
}

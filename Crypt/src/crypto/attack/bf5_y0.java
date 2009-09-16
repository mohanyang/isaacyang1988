/**
 * 
 */
package crypto.attack;

import crypto.util.FunctionType;

/**
 * @author Isaac
 * 
 */
public class bf5_y0 implements FunctionType {

	private int[] arrayTable = null;
	private static int n = 8;

	public bf5_y0() {
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

			/*
			 * x4x3x2x1 + x5x4x2x1 + x5x4x3x1 + x6x3x2x0 + x6x4x3x0 + x6x4x3x1 +
			 * x6x5x2x1 + x6x5x3x0 + x6x5x3x1 + x7x3x2x0 + x7x3x2x1 + x7x4x2x1 +
			 * x7x4x3x1 + x7x4x3x2 + x7x5x2x0 + x7x5x3x2 + x7x5x4x3 + x7x6x2x1 +
			 * x7x6x4x1 + x7x6x4x2 + x7x6x5x2 + x7x6x5x3 + x7x6x5x4 + x4x2x0 +
			 * x4x2x1 + x5x2x1 + x5x4x0 + x5x4x2 + x5x4x3 + x6x2x1 + x6x3x1 +
			 * x6x4x0 + x6x4x2 + x6x5x0 + x7x2x0 + x7x2x1 + x7x3x1 + x7x3x2 +
			 * x7x5x0 + x7x5x1 + x7x5x4 + x7x6x0 + x7x6x1 + x7x6x4 + x7x6x5 +
			 * x1x0 + x4x1 + x5x2 + x5x3 + x7x0 + x7x1 + x7x3 + x7x4 + x7x5 + x3 +
			 * x4 + x5 + x6 + x7:
			 */
			arrayTable[i] = (tmp[4] * tmp[3] * tmp[2] * tmp[1])
					^ (tmp[5] * tmp[4] * tmp[2] * tmp[1])
					^ (tmp[5] * tmp[4] * tmp[3] * tmp[1])
					^ (tmp[6] * tmp[3] * tmp[2] * tmp[0])
					^ (tmp[6] * tmp[4] * tmp[3] * tmp[0])
					^ (tmp[6] * tmp[4] * tmp[3] * tmp[1])
					^ (tmp[6] * tmp[5] * tmp[2] * tmp[1])
					^ (tmp[6] * tmp[5] * tmp[3] * tmp[0])
					^ (tmp[6] * tmp[5] * tmp[3] * tmp[1])
					^ (tmp[7] * tmp[3] * tmp[2] * tmp[0])
					^ (tmp[7] * tmp[3] * tmp[2] * tmp[1])
					^ (tmp[7] * tmp[4] * tmp[2] * tmp[1])
					^ (tmp[7] * tmp[4] * tmp[3] * tmp[1])
					^ (tmp[7] * tmp[4] * tmp[3] * tmp[2])
					^ (tmp[7] * tmp[5] * tmp[2] * tmp[0])
					^ (tmp[7] * tmp[5] * tmp[3] * tmp[2])
					^ (tmp[7] * tmp[5] * tmp[4] * tmp[3])
					^ (tmp[7] * tmp[6] * tmp[2] * tmp[1])
					^ (tmp[7] * tmp[6] * tmp[4] * tmp[1])
					^ (tmp[7] * tmp[6] * tmp[4] * tmp[2])
					^ (tmp[7] * tmp[6] * tmp[5] * tmp[2])
					^ (tmp[7] * tmp[6] * tmp[5] * tmp[3])
					^ (tmp[7] * tmp[6] * tmp[5] * tmp[4])
					^ (tmp[4] * tmp[2] * tmp[0]) ^ (tmp[4] * tmp[2] * tmp[1])
					^ (tmp[5] * tmp[2] * tmp[1]) ^ (tmp[5] * tmp[4] * tmp[0])
					^ (tmp[5] * tmp[4] * tmp[2]) ^ (tmp[5] * tmp[4] * tmp[3])
					^ (tmp[6] * tmp[2] * tmp[1]) ^ (tmp[6] * tmp[3] * tmp[1])
					^ (tmp[6] * tmp[4] * tmp[0]) ^ (tmp[6] * tmp[4] * tmp[2])
					^ (tmp[6] * tmp[5] * tmp[0]) ^ (tmp[7] * tmp[2] * tmp[0])
					^ (tmp[7] * tmp[2] * tmp[1]) ^ (tmp[7] * tmp[3] * tmp[1])
					^ (tmp[7] * tmp[3] * tmp[2]) ^ (tmp[7] * tmp[5] * tmp[0])
					^ (tmp[7] * tmp[5] * tmp[1]) ^ (tmp[7] * tmp[5] * tmp[4])
					^ (tmp[7] * tmp[6] * tmp[0]) ^ (tmp[7] * tmp[6] * tmp[1])
					^ (tmp[7] * tmp[6] * tmp[4]) ^ (tmp[7] * tmp[6] * tmp[5])
					^ (tmp[1] * tmp[0]) ^ (tmp[4] * tmp[1]) ^ (tmp[5] * tmp[2])
					^ (tmp[5] * tmp[3]) ^ (tmp[7] * tmp[0]) ^ (tmp[7] * tmp[1])
					^ (tmp[7] * tmp[3]) ^ (tmp[7] * tmp[4]) ^ (tmp[7] * tmp[5])
					^ (tmp[3]) ^ (tmp[4]) ^ (tmp[5]) ^ (tmp[6]) ^ (tmp[7]);
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
		bf5_y0 f = new bf5_y0();
		for (int i = 0; i < (1 << n); ++i)
			System.out.println(crypto.common.BasicMethod.toBitString(i, n)
					+ " " + f.calc(i));
	}
}

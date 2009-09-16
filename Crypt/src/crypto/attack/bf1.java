/**
 * 
 */
package crypto.attack;

import crypto.util.FunctionType;

/**
 * @author Isaac
 * 
 */
public class bf1 implements FunctionType {

	private int[] arrayTable = null;

	public bf1() {
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
					^ (tmp[0] * tmp[1] * tmp[3]) ^ (tmp[0] * tmp[2] * tmp[3])
					^ (tmp[1] * tmp[2] * tmp[3]);
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
		bf1 f = new bf1();
		for (int i = 0; i < 16; ++i)
			System.out.println(crypto.common.BasicMethod.toBitString(i, 4)
					+ " " + f.calc(i));
	}
}

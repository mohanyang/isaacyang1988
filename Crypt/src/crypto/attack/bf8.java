/**
 * 
 */
package crypto.attack;

import crypto.util.FunctionType;

/**
 * @author Isaac
 * 
 */
public class bf8 implements FunctionType {

	private int[] arrayTable = null;

	public bf8() {
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
			arrayTable[i] = (tmp[2] * tmp[3]) ^ tmp[1] ^ tmp[0] ^ 1;
		}
	}

	public int calc(int input) {
		return arrayTable[input];
	}

	public int getN() {
		return 4;
	}

	public static void main(String[] args) {
		bf8 f = new bf8();
		for (int i = 0; i < 16; ++i)
			System.out.println(crypto.common.BasicMethod.toBitString(i, 4)
					+ " " + f.calc(i));
	}
}

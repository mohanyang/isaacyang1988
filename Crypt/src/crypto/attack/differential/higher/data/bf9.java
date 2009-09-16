/**
 * 
 */
package crypto.attack.differential.higher.data;

import crypto.util.FunctionType;

/**
 * @author Isaac
 * 
 */
public class bf9 implements FunctionType {

	private int[] arrayTable = { 11, 12, 6, 1, 11, 4, 14, 1, 11, 2, 8, 1, 10,
			11, 1, 0 };

	public bf9() {
	}

	public int calc(int input) {
		return arrayTable[arrayTable[input]];
		// return arrayTable[input];
	}

	public int getN() {
		return 4;
	}

	public static void main(String[] args) {
		bf9 f = new bf9();
		for (int i = 0; i < 16; ++i)
			System.out.println(crypto.common.BasicMethod.toBitString(i, 4)
					+ " " + f.calc(i));
	}
}

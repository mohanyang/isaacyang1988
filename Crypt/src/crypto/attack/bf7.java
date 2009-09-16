package crypto.attack;

import crypto.util.FunctionType;

public class bf7 implements FunctionType {

	private static final int[] t = { 0x3, 0xF, 0xE, 0x0, 0x5, 0x4, 0xB, 0xC,
			0xD, 0xA, 0x9, 0x6, 0x7, 0x8, 0x2, 0x1 };

	// private static final int[] t = { 3, 8, 15, 1, 10, 6, 5, 11, 14, 13, 4, 2,
	// 7, 0, 9, 12 };

	public int calc(int input) {
		return t[input];
	}

	public int getN() {
		return 4;
	}
}

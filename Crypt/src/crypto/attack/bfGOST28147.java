package crypto.attack;

import crypto.util.FunctionType;

public class bfGOST28147 implements FunctionType {

	private int s = 0;

	public bfGOST28147(int s) {
		this.s = s;
	}

	public int calc(int input) {
		switch (s) {
		case 1:
			return S[input];
		case 2:
			return ESbox_Test[input];
		case 3:
			return ESbox_A[input];
		case 4:
			return ESbox_B[input];
		case 5:
			return ESbox_C[input];
		case 6:
			return ESbox_D[input];
		case 7:
			return DSbox_Test[input];
		case 8:
			return DSbox_A[input];
		}
		return 0;
	}

	public int getN() {
		return 7;
	}

	private byte S[] = { 0x4, 0xA, 0x9, 0x2, 0xD, 0x8, 0x0, 0xE, 0x6, 0xB, 0x1,
			0xC, 0x7, 0xF, 0x5, 0x3, 0xE, 0xB, 0x4, 0xC, 0x6, 0xD, 0xF, 0xA,
			0x2, 0x3, 0x8, 0x1, 0x0, 0x7, 0x5, 0x9, 0x5, 0x8, 0x1, 0xD, 0xA,
			0x3, 0x4, 0x2, 0xE, 0xF, 0xC, 0x7, 0x6, 0x0, 0x9, 0xB, 0x7, 0xD,
			0xA, 0x1, 0x0, 0x8, 0x9, 0xF, 0xE, 0x4, 0x6, 0xC, 0xB, 0x2, 0x5,
			0x3, 0x6, 0xC, 0x7, 0x1, 0x5, 0xF, 0xD, 0x8, 0x4, 0xA, 0x9, 0xE,
			0x0, 0x3, 0xB, 0x2, 0x4, 0xB, 0xA, 0x0, 0x7, 0x2, 0x1, 0xD, 0x3,
			0x6, 0x8, 0x5, 0x9, 0xC, 0xF, 0xE, 0xD, 0xB, 0x4, 0x1, 0x3, 0xF,
			0x5, 0x9, 0x0, 0xA, 0xE, 0x7, 0x6, 0x8, 0x2, 0xC, 0x1, 0xF, 0xD,
			0x0, 0x5, 0x7, 0xA, 0x4, 0x9, 0x2, 0x3, 0xE, 0x6, 0xB, 0x8, 0xC };

	/*
	 * class content S-box parameters for encrypting getting from, see:
	 * http://www.ietf.org/internet-drafts/draft-popov-cryptopro-cpalgs-01.txt
	 * http://www.ietf.org/internet-drafts/draft-popov-cryptopro-cpalgs-02.txt
	 */
	private static byte[] ESbox_Test = { 0x4, 0x2, 0xF, 0x5, 0x9, 0x1, 0x0,
			0x8, 0xE, 0x3, 0xB, 0xC, 0xD, 0x7, 0xA, 0x6, 0xC, 0x9, 0xF, 0xE,
			0x8, 0x1, 0x3, 0xA, 0x2, 0x7, 0x4, 0xD, 0x6, 0x0, 0xB, 0x5, 0xD,
			0x8, 0xE, 0xC, 0x7, 0x3, 0x9, 0xA, 0x1, 0x5, 0x2, 0x4, 0x6, 0xF,
			0x0, 0xB, 0xE, 0x9, 0xB, 0x2, 0x5, 0xF, 0x7, 0x1, 0x0, 0xD, 0xC,
			0x6, 0xA, 0x4, 0x3, 0x8, 0x3, 0xE, 0x5, 0x9, 0x6, 0x8, 0x0, 0xD,
			0xA, 0xB, 0x7, 0xC, 0x2, 0x1, 0xF, 0x4, 0x8, 0xF, 0x6, 0xB, 0x1,
			0x9, 0xC, 0x5, 0xD, 0x3, 0x7, 0xA, 0x0, 0xE, 0x2, 0x4, 0x9, 0xB,
			0xC, 0x0, 0x3, 0x6, 0x7, 0x5, 0x4, 0x8, 0xE, 0xF, 0x1, 0xA, 0x2,
			0xD, 0xC, 0x6, 0x5, 0x2, 0xB, 0x0, 0x9, 0xD, 0x3, 0xE, 0x7, 0xA,
			0xF, 0x4, 0x1, 0x8 };

	private static byte[] ESbox_A = { 0x9, 0x6, 0x3, 0x2, 0x8, 0xB, 0x1, 0x7,
			0xA, 0x4, 0xE, 0xF, 0xC, 0x0, 0xD, 0x5, 0x3, 0x7, 0xE, 0x9, 0x8,
			0xA, 0xF, 0x0, 0x5, 0x2, 0x6, 0xC, 0xB, 0x4, 0xD, 0x1, 0xE, 0x4,
			0x6, 0x2, 0xB, 0x3, 0xD, 0x8, 0xC, 0xF, 0x5, 0xA, 0x0, 0x7, 0x1,
			0x9, 0xE, 0x7, 0xA, 0xC, 0xD, 0x1, 0x3, 0x9, 0x0, 0x2, 0xB, 0x4,
			0xF, 0x8, 0x5, 0x6, 0xB, 0x5, 0x1, 0x9, 0x8, 0xD, 0xF, 0x0, 0xE,
			0x4, 0x2, 0x3, 0xC, 0x7, 0xA, 0x6, 0x3, 0xA, 0xD, 0xC, 0x1, 0x2,
			0x0, 0xB, 0x7, 0x5, 0x9, 0x4, 0x8, 0xF, 0xE, 0x6, 0x1, 0xD, 0x2,
			0x9, 0x7, 0xA, 0x6, 0x0, 0x8, 0xC, 0x4, 0x5, 0xF, 0x3, 0xB, 0xE,
			0xB, 0xA, 0xF, 0x5, 0x0, 0xC, 0xE, 0x8, 0x6, 0x2, 0x3, 0x9, 0x1,
			0x7, 0xD, 0x4 };

	private static byte[] ESbox_B = { 0x8, 0x4, 0xB, 0x1, 0x3, 0x5, 0x0, 0x9,
			0x2, 0xE, 0xA, 0xC, 0xD, 0x6, 0x7, 0xF, 0x0, 0x1, 0x2, 0xA, 0x4,
			0xD, 0x5, 0xC, 0x9, 0x7, 0x3, 0xF, 0xB, 0x8, 0x6, 0xE, 0xE, 0xC,
			0x0, 0xA, 0x9, 0x2, 0xD, 0xB, 0x7, 0x5, 0x8, 0xF, 0x3, 0x6, 0x1,
			0x4, 0x7, 0x5, 0x0, 0xD, 0xB, 0x6, 0x1, 0x2, 0x3, 0xA, 0xC, 0xF,
			0x4, 0xE, 0x9, 0x8, 0x2, 0x7, 0xC, 0xF, 0x9, 0x5, 0xA, 0xB, 0x1,
			0x4, 0x0, 0xD, 0x6, 0x8, 0xE, 0x3, 0x8, 0x3, 0x2, 0x6, 0x4, 0xD,
			0xE, 0xB, 0xC, 0x1, 0x7, 0xF, 0xA, 0x0, 0x9, 0x5, 0x5, 0x2, 0xA,
			0xB, 0x9, 0x1, 0xC, 0x3, 0x7, 0x4, 0xD, 0x0, 0x6, 0xF, 0x8, 0xE,
			0x0, 0x4, 0xB, 0xE, 0x8, 0x3, 0x7, 0x1, 0xA, 0x2, 0x9, 0x6, 0xF,
			0xD, 0x5, 0xC };

	private static byte[] ESbox_C = { 0x1, 0xB, 0xC, 0x2, 0x9, 0xD, 0x0, 0xF,
			0x4, 0x5, 0x8, 0xE, 0xA, 0x7, 0x6, 0x3, 0x0, 0x1, 0x7, 0xD, 0xB,
			0x4, 0x5, 0x2, 0x8, 0xE, 0xF, 0xC, 0x9, 0xA, 0x6, 0x3, 0x8, 0x2,
			0x5, 0x0, 0x4, 0x9, 0xF, 0xA, 0x3, 0x7, 0xC, 0xD, 0x6, 0xE, 0x1,
			0xB, 0x3, 0x6, 0x0, 0x1, 0x5, 0xD, 0xA, 0x8, 0xB, 0x2, 0x9, 0x7,
			0xE, 0xF, 0xC, 0x4, 0x8, 0xD, 0xB, 0x0, 0x4, 0x5, 0x1, 0x2, 0x9,
			0x3, 0xC, 0xE, 0x6, 0xF, 0xA, 0x7, 0xC, 0x9, 0xB, 0x1, 0x8, 0xE,
			0x2, 0x4, 0x7, 0x3, 0x6, 0x5, 0xA, 0x0, 0xF, 0xD, 0xA, 0x9, 0x6,
			0x8, 0xD, 0xE, 0x2, 0x0, 0xF, 0x3, 0x5, 0xB, 0x4, 0x1, 0xC, 0x7,
			0x7, 0x4, 0x0, 0x5, 0xA, 0x2, 0xF, 0xE, 0xC, 0x6, 0x1, 0xB, 0xD,
			0x9, 0x3, 0x8 };

	private static byte[] ESbox_D = { 0xF, 0xC, 0x2, 0xA, 0x6, 0x4, 0x5, 0x0,
			0x7, 0x9, 0xE, 0xD, 0x1, 0xB, 0x8, 0x3, 0xB, 0x6, 0x3, 0x4, 0xC,
			0xF, 0xE, 0x2, 0x7, 0xD, 0x8, 0x0, 0x5, 0xA, 0x9, 0x1, 0x1, 0xC,
			0xB, 0x0, 0xF, 0xE, 0x6, 0x5, 0xA, 0xD, 0x4, 0x8, 0x9, 0x3, 0x7,
			0x2, 0x1, 0x5, 0xE, 0xC, 0xA, 0x7, 0x0, 0xD, 0x6, 0x2, 0xB, 0x4,
			0x9, 0x3, 0xF, 0x8, 0x0, 0xC, 0x8, 0x9, 0xD, 0x2, 0xA, 0xB, 0x7,
			0x3, 0x6, 0x5, 0x4, 0xE, 0xF, 0x1, 0x8, 0x0, 0xF, 0x3, 0x2, 0x5,
			0xE, 0xB, 0x1, 0xA, 0x4, 0x7, 0xC, 0x9, 0xD, 0x6, 0x3, 0x0, 0x6,
			0xF, 0x1, 0xE, 0x9, 0x2, 0xD, 0x8, 0xC, 0x4, 0xB, 0xA, 0x5, 0x7,
			0x1, 0xA, 0x6, 0x8, 0xF, 0xB, 0x0, 0x4, 0xC, 0x3, 0x5, 0x9, 0x7,
			0xD, 0x2, 0xE };

	// S-box for digest
	private static byte DSbox_Test[] = { 0x4, 0xA, 0x9, 0x2, 0xD, 0x8, 0x0,
			0xE, 0x6, 0xB, 0x1, 0xC, 0x7, 0xF, 0x5, 0x3, 0xE, 0xB, 0x4, 0xC,
			0x6, 0xD, 0xF, 0xA, 0x2, 0x3, 0x8, 0x1, 0x0, 0x7, 0x5, 0x9, 0x5,
			0x8, 0x1, 0xD, 0xA, 0x3, 0x4, 0x2, 0xE, 0xF, 0xC, 0x7, 0x6, 0x0,
			0x9, 0xB, 0x7, 0xD, 0xA, 0x1, 0x0, 0x8, 0x9, 0xF, 0xE, 0x4, 0x6,
			0xC, 0xB, 0x2, 0x5, 0x3, 0x6, 0xC, 0x7, 0x1, 0x5, 0xF, 0xD, 0x8,
			0x4, 0xA, 0x9, 0xE, 0x0, 0x3, 0xB, 0x2, 0x4, 0xB, 0xA, 0x0, 0x7,
			0x2, 0x1, 0xD, 0x3, 0x6, 0x8, 0x5, 0x9, 0xC, 0xF, 0xE, 0xD, 0xB,
			0x4, 0x1, 0x3, 0xF, 0x5, 0x9, 0x0, 0xA, 0xE, 0x7, 0x6, 0x8, 0x2,
			0xC, 0x1, 0xF, 0xD, 0x0, 0x5, 0x7, 0xA, 0x4, 0x9, 0x2, 0x3, 0xE,
			0x6, 0xB, 0x8, 0xC };

	private static byte DSbox_A[] = { 0xA, 0x4, 0x5, 0x6, 0x8, 0x1, 0x3, 0x7,
			0xD, 0xC, 0xE, 0x0, 0x9, 0x2, 0xB, 0xF, 0x5, 0xF, 0x4, 0x0, 0x2,
			0xD, 0xB, 0x9, 0x1, 0x7, 0x6, 0x3, 0xC, 0xE, 0xA, 0x8, 0x7, 0xF,
			0xC, 0xE, 0x9, 0x4, 0x1, 0x0, 0x3, 0xB, 0x5, 0x2, 0x6, 0xA, 0x8,
			0xD, 0x4, 0xA, 0x7, 0xC, 0x0, 0xF, 0x2, 0x8, 0xE, 0x1, 0x6, 0x5,
			0xD, 0xB, 0x9, 0x3, 0x7, 0x6, 0x4, 0xB, 0x9, 0xC, 0x2, 0xA, 0x1,
			0x8, 0x0, 0xE, 0xF, 0xD, 0x3, 0x5, 0x7, 0x6, 0x2, 0x4, 0xD, 0x9,
			0xF, 0x0, 0xA, 0x1, 0x5, 0xB, 0x8, 0xE, 0xC, 0x3, 0xD, 0xE, 0x4,
			0x1, 0x7, 0x0, 0x5, 0xA, 0x3, 0xC, 0x8, 0xF, 0x6, 0x2, 0x9, 0xB,
			0x1, 0x3, 0xA, 0x9, 0x5, 0xB, 0x4, 0xF, 0x8, 0x6, 0x7, 0xE, 0xD,
			0x0, 0x2, 0xC };
}
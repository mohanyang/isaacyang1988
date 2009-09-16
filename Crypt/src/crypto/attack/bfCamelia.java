package crypto.attack;

import crypto.util.FunctionType;

public class bfCamelia implements FunctionType {

	private int s = 0;

	public int calc(int input) {
		switch (s) {
		case 1:
			return SBOX1[input];
		case 2:
			return SBOX2[input];
		case 3:
			return SBOX3[input];
		case 4:
			return SBOX4[input];
		}
		return 0;
	}

	public int getN() {
		return 8;
	}

	public bfCamelia(int s) {
		this.s = s;
		for (int x = 0; x != 256; x++) {
			SBOX2[x] = lRot8(SBOX1[x], 1);
			SBOX3[x] = lRot8(SBOX1[x], 7);
			SBOX4[x] = SBOX1[lRot8((byte) x, 1) & 0xff];
		}
	}

	private final byte[] SBOX1 = { (byte) 112, (byte) 130, (byte) 44,
			(byte) 236, (byte) 179, (byte) 39, (byte) 192, (byte) 229,
			(byte) 228, (byte) 133, (byte) 87, (byte) 53, (byte) 234,
			(byte) 12, (byte) 174, (byte) 65, (byte) 35, (byte) 239,
			(byte) 107, (byte) 147, (byte) 69, (byte) 25, (byte) 165,
			(byte) 33, (byte) 237, (byte) 14, (byte) 79, (byte) 78, (byte) 29,
			(byte) 101, (byte) 146, (byte) 189, (byte) 134, (byte) 184,
			(byte) 175, (byte) 143, (byte) 124, (byte) 235, (byte) 31,
			(byte) 206, (byte) 62, (byte) 48, (byte) 220, (byte) 95, (byte) 94,
			(byte) 197, (byte) 11, (byte) 26, (byte) 166, (byte) 225,
			(byte) 57, (byte) 202, (byte) 213, (byte) 71, (byte) 93, (byte) 61,
			(byte) 217, (byte) 1, (byte) 90, (byte) 214, (byte) 81, (byte) 86,
			(byte) 108, (byte) 77, (byte) 139, (byte) 13, (byte) 154,
			(byte) 102, (byte) 251, (byte) 204, (byte) 176, (byte) 45,
			(byte) 116, (byte) 18, (byte) 43, (byte) 32, (byte) 240,
			(byte) 177, (byte) 132, (byte) 153, (byte) 223, (byte) 76,
			(byte) 203, (byte) 194, (byte) 52, (byte) 126, (byte) 118,
			(byte) 5, (byte) 109, (byte) 183, (byte) 169, (byte) 49,
			(byte) 209, (byte) 23, (byte) 4, (byte) 215, (byte) 20, (byte) 88,
			(byte) 58, (byte) 97, (byte) 222, (byte) 27, (byte) 17, (byte) 28,
			(byte) 50, (byte) 15, (byte) 156, (byte) 22, (byte) 83, (byte) 24,
			(byte) 242, (byte) 34, (byte) 254, (byte) 68, (byte) 207,
			(byte) 178, (byte) 195, (byte) 181, (byte) 122, (byte) 145,
			(byte) 36, (byte) 8, (byte) 232, (byte) 168, (byte) 96, (byte) 252,
			(byte) 105, (byte) 80, (byte) 170, (byte) 208, (byte) 160,
			(byte) 125, (byte) 161, (byte) 137, (byte) 98, (byte) 151,
			(byte) 84, (byte) 91, (byte) 30, (byte) 149, (byte) 224,
			(byte) 255, (byte) 100, (byte) 210, (byte) 16, (byte) 196,
			(byte) 0, (byte) 72, (byte) 163, (byte) 247, (byte) 117,
			(byte) 219, (byte) 138, (byte) 3, (byte) 230, (byte) 218, (byte) 9,
			(byte) 63, (byte) 221, (byte) 148, (byte) 135, (byte) 92,
			(byte) 131, (byte) 2, (byte) 205, (byte) 74, (byte) 144, (byte) 51,
			(byte) 115, (byte) 103, (byte) 246, (byte) 243, (byte) 157,
			(byte) 127, (byte) 191, (byte) 226, (byte) 82, (byte) 155,
			(byte) 216, (byte) 38, (byte) 200, (byte) 55, (byte) 198,
			(byte) 59, (byte) 129, (byte) 150, (byte) 111, (byte) 75,
			(byte) 19, (byte) 190, (byte) 99, (byte) 46, (byte) 233,
			(byte) 121, (byte) 167, (byte) 140, (byte) 159, (byte) 110,
			(byte) 188, (byte) 142, (byte) 41, (byte) 245, (byte) 249,
			(byte) 182, (byte) 47, (byte) 253, (byte) 180, (byte) 89,
			(byte) 120, (byte) 152, (byte) 6, (byte) 106, (byte) 231,
			(byte) 70, (byte) 113, (byte) 186, (byte) 212, (byte) 37,
			(byte) 171, (byte) 66, (byte) 136, (byte) 162, (byte) 141,
			(byte) 250, (byte) 114, (byte) 7, (byte) 185, (byte) 85,
			(byte) 248, (byte) 238, (byte) 172, (byte) 10, (byte) 54,
			(byte) 73, (byte) 42, (byte) 104, (byte) 60, (byte) 56, (byte) 241,
			(byte) 164, (byte) 64, (byte) 40, (byte) 211, (byte) 123,
			(byte) 187, (byte) 201, (byte) 67, (byte) 193, (byte) 21,
			(byte) 227, (byte) 173, (byte) 244, (byte) 119, (byte) 199,
			(byte) 128, (byte) 158 };

	private final byte[] SBOX2 = new byte[256];
	private final byte[] SBOX3 = new byte[256];
	private final byte[] SBOX4 = new byte[256];

	private byte lRot8(byte value, int rotation) {
		return (byte) ((value << rotation) | ((value & 0xff) >>> (8 - rotation)));
	}
}

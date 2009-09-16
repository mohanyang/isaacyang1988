package crypto.attack;

import crypto.util.FunctionType;

public class bfAES implements FunctionType {

	public bfAES() {
	}

	public int calc(int input) {
		return S[input];
	}

	public int getN() {
		return 8;
	}

	private static final byte[] S = { (byte) 99, (byte) 124, (byte) 119,
			(byte) 123, (byte) 242, (byte) 107, (byte) 111, (byte) 197,
			(byte) 48, (byte) 1, (byte) 103, (byte) 43, (byte) 254, (byte) 215,
			(byte) 171, (byte) 118, (byte) 202, (byte) 130, (byte) 201,
			(byte) 125, (byte) 250, (byte) 89, (byte) 71, (byte) 240,
			(byte) 173, (byte) 212, (byte) 162, (byte) 175, (byte) 156,
			(byte) 164, (byte) 114, (byte) 192, (byte) 183, (byte) 253,
			(byte) 147, (byte) 38, (byte) 54, (byte) 63, (byte) 247,
			(byte) 204, (byte) 52, (byte) 165, (byte) 229, (byte) 241,
			(byte) 113, (byte) 216, (byte) 49, (byte) 21, (byte) 4, (byte) 199,
			(byte) 35, (byte) 195, (byte) 24, (byte) 150, (byte) 5, (byte) 154,
			(byte) 7, (byte) 18, (byte) 128, (byte) 226, (byte) 235, (byte) 39,
			(byte) 178, (byte) 117, (byte) 9, (byte) 131, (byte) 44, (byte) 26,
			(byte) 27, (byte) 110, (byte) 90, (byte) 160, (byte) 82, (byte) 59,
			(byte) 214, (byte) 179, (byte) 41, (byte) 227, (byte) 47,
			(byte) 132, (byte) 83, (byte) 209, (byte) 0, (byte) 237, (byte) 32,
			(byte) 252, (byte) 177, (byte) 91, (byte) 106, (byte) 203,
			(byte) 190, (byte) 57, (byte) 74, (byte) 76, (byte) 88, (byte) 207,
			(byte) 208, (byte) 239, (byte) 170, (byte) 251, (byte) 67,
			(byte) 77, (byte) 51, (byte) 133, (byte) 69, (byte) 249, (byte) 2,
			(byte) 127, (byte) 80, (byte) 60, (byte) 159, (byte) 168,
			(byte) 81, (byte) 163, (byte) 64, (byte) 143, (byte) 146,
			(byte) 157, (byte) 56, (byte) 245, (byte) 188, (byte) 182,
			(byte) 218, (byte) 33, (byte) 16, (byte) 255, (byte) 243,
			(byte) 210, (byte) 205, (byte) 12, (byte) 19, (byte) 236,
			(byte) 95, (byte) 151, (byte) 68, (byte) 23, (byte) 196,
			(byte) 167, (byte) 126, (byte) 61, (byte) 100, (byte) 93,
			(byte) 25, (byte) 115, (byte) 96, (byte) 129, (byte) 79,
			(byte) 220, (byte) 34, (byte) 42, (byte) 144, (byte) 136,
			(byte) 70, (byte) 238, (byte) 184, (byte) 20, (byte) 222,
			(byte) 94, (byte) 11, (byte) 219, (byte) 224, (byte) 50, (byte) 58,
			(byte) 10, (byte) 73, (byte) 6, (byte) 36, (byte) 92, (byte) 194,
			(byte) 211, (byte) 172, (byte) 98, (byte) 145, (byte) 149,
			(byte) 228, (byte) 121, (byte) 231, (byte) 200, (byte) 55,
			(byte) 109, (byte) 141, (byte) 213, (byte) 78, (byte) 169,
			(byte) 108, (byte) 86, (byte) 244, (byte) 234, (byte) 101,
			(byte) 122, (byte) 174, (byte) 8, (byte) 186, (byte) 120,
			(byte) 37, (byte) 46, (byte) 28, (byte) 166, (byte) 180,
			(byte) 198, (byte) 232, (byte) 221, (byte) 116, (byte) 31,
			(byte) 75, (byte) 189, (byte) 139, (byte) 138, (byte) 112,
			(byte) 62, (byte) 181, (byte) 102, (byte) 72, (byte) 3, (byte) 246,
			(byte) 14, (byte) 97, (byte) 53, (byte) 87, (byte) 185, (byte) 134,
			(byte) 193, (byte) 29, (byte) 158, (byte) 225, (byte) 248,
			(byte) 152, (byte) 17, (byte) 105, (byte) 217, (byte) 142,
			(byte) 148, (byte) 155, (byte) 30, (byte) 135, (byte) 233,
			(byte) 206, (byte) 85, (byte) 40, (byte) 223, (byte) 140,
			(byte) 161, (byte) 137, (byte) 13, (byte) 191, (byte) 230,
			(byte) 66, (byte) 104, (byte) 65, (byte) 153, (byte) 45, (byte) 15,
			(byte) 176, (byte) 84, (byte) 187, (byte) 22, };

	public static void main(String[] args) {
		System.out.println(8);
		for (int i = 0; i < 256; ++i)
			System.out.print((S[i] & 0xff) + " ");
	}
}

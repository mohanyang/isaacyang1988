package crypto.attack.test;

import java.util.Arrays;

public class Trivium {
	public int[] key = null;
	public int[] iv = null;
	public int[] s = null;
	private int[] t = null;

	public Trivium() {
		key = new int[80];
		iv = new int[80];
		s = new int[288];
		t = new int[3];
	}

	public void setKey(byte[] newKey) {
		for (int i = 0; i != 10; ++i)
			for (int j = 0; j != 8; ++j)
				key[i * 8 + 7 - j] = getIthBit(newKey[i] & 0xff, j);
	}

	public void setIV(byte[] newIV) {
		for (int i = 0; i != 10; ++i)
			for (int j = 0; j != 8; ++j)
				iv[i * 8 + 7 - j] = getIthBit(newIV[i] & 0xff, j);
	}

	public int process() {
		return round();
	}

	public void init() {
		Arrays.fill(s, (byte) 0);
		int i = 0;
		for (i = 0; i != 80; ++i)
			s[i] = key[i];
		for (i = 93; i != 173; ++i)
			s[i] = iv[i - 93];
		for (i = 285; i != 288; ++i)
			s[i] = 1;
		// for (i = 0; i != 288; ++i)
		// System.out.print(s[i]);
		// System.out.println();
		for (i = 0; i != 672; ++i)
			round();
		// for (i = 0; i != 1152; ++i)
		// System.out.print(round());
		// System.out.println();
	}

	private int round() {
		int i = 0, z = 0;
		t[0] = s[65] ^ s[92];
		t[1] = s[161] ^ s[176];
		t[2] = s[242] ^ s[287];
		z = t[0] ^ t[1] ^ t[2];

		t[0] ^= (s[90] & s[91]) ^ s[170];
		t[1] ^= (s[174] & s[175]) ^ s[263];
		t[2] ^= (s[285] & s[286]) ^ s[68];

		for (i = 92; i != 0; --i)
			s[i] = s[i - 1];
		s[0] = t[2];

		for (i = 176; i != 93; --i)
			s[i] = s[i - 1];
		s[93] = t[0];

		for (i = 287; i != 177; --i)
			s[i] = s[i - 1];
		s[177] = t[1];

		// for (i = 0; i != 288; ++i)
		// System.out.print(s[i]);
		// System.out.println();

		return z;
	}

	private int getIthBit(int k, int i) {
		int ret = (int) ((k >> i) & 1);
		return ret;
	}
}

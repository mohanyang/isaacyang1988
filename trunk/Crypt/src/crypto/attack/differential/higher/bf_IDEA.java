package crypto.attack.differential.higher;

import org.bouncycastle.util.encoders.Hex;

import crypto.util.FunctionType;

public class bf_IDEA implements FunctionType {

	private byte[] t1 = null, t2 = null;
	private int b = 0, s = 0;

	public bf_IDEA(int b) {
		this.b = b & 0x7;
		this.s = b >> 3;
		t1 = new byte[8];
		t2 = new byte[8];
	}

	public int calc(int input) {
		Bits32ToBytes(input, t1, 0);
		ideaFunc(t1, 0, t2, 0);
		return getIthBit(t2[s] & 0xff, b);
	}

	private int getIthBit(int k, int i) {
		int ret = (int) ((k >> i) & 1);
		if (ret < 0)
			ret = 1;
		return ret;
	}

	public int getN() {
		return 16;
	}

	private int[] workingKey = expandKey(Hex
			.decode("00112233445566778899AABBCCDDEEFF"));

	private static final int MASK = 0xffff;
	private static final int BASE = 0x10001;

	private int bytesToWord(byte[] in, int inOff) {
		return ((in[inOff] << 8) & 0xff00) + (in[inOff + 1] & 0xff);
	}

	private void wordToBytes(int word, byte[] out, int outOff) {
		out[outOff] = (byte) (word >>> 8);
		out[outOff + 1] = (byte) word;
	}

	private int mul(int x, int y) {
		if (x == 0) {
			x = (BASE - y);
		} else if (y == 0) {
			x = (BASE - x);
		} else {
			int p = x * y;

			y = p & MASK;
			x = p >>> 16;
			x = y - x + ((y < x) ? 1 : 0);
		}

		return x & MASK;
	}

	private void ideaFunc(byte[] in, int inOff, byte[] out, int outOff) {
		int x0, x1, x2, x3, t0, t1;
		int keyOff = 0;

		x0 = bytesToWord(in, inOff);
		x1 = bytesToWord(in, inOff + 2);
		x2 = bytesToWord(in, inOff + 4);
		x3 = bytesToWord(in, inOff + 6);

		for (int round = 0; round < 8; round++) {
			x0 = mul(x0, workingKey[keyOff++]);
			x1 += workingKey[keyOff++];
			x1 &= MASK;
			x2 += workingKey[keyOff++];
			x2 &= MASK;
			x3 = mul(x3, workingKey[keyOff++]);

			t0 = x1;
			t1 = x2;
			x2 ^= x0;
			x1 ^= x3;

			x2 = mul(x2, workingKey[keyOff++]);
			x1 += x2;
			x1 &= MASK;

			x1 = mul(x1, workingKey[keyOff++]);
			x2 += x1;
			x2 &= MASK;

			x0 ^= x1;
			x3 ^= x2;
			x1 ^= t1;
			x2 ^= t0;
		}

		wordToBytes(mul(x0, workingKey[keyOff++]), out, outOff);
		wordToBytes(x2 + workingKey[keyOff++], out, outOff + 2); /* NB: Order */
		wordToBytes(x1 + workingKey[keyOff++], out, outOff + 4);
		wordToBytes(mul(x3, workingKey[keyOff]), out, outOff + 6);
	}

	private int[] expandKey(byte[] uKey) {
		int[] key = new int[52];

		if (uKey.length < 16) {
			byte[] tmp = new byte[16];

			System.arraycopy(uKey, 0, tmp, tmp.length - uKey.length,
					uKey.length);

			uKey = tmp;
		}

		for (int i = 0; i < 8; i++) {
			key[i] = bytesToWord(uKey, i * 2);
		}

		for (int i = 8; i < 52; i++) {
			if ((i & 7) < 6) {
				key[i] = ((key[i - 7] & 127) << 9 | key[i - 6] >> 7) & MASK;
			} else if ((i & 7) == 6) {
				key[i] = ((key[i - 7] & 127) << 9 | key[i - 14] >> 7) & MASK;
			} else {
				key[i] = ((key[i - 15] & 127) << 9 | key[i - 14] >> 7) & MASK;
			}
		}

		return key;
	}

	protected final void Bits32ToBytes(int in, byte[] b, int offset) {
		b[offset + 3] = (byte) in;
		b[offset + 2] = (byte) (in >>> 8);
		b[offset + 1] = (byte) (in >>> 16);
		b[offset] = (byte) (in >>> 24);
	}
}

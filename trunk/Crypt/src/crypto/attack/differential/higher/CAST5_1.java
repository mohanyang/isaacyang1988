package crypto.attack.differential.higher;

import org.bouncycastle.util.encoders.Hex;

import crypto.util.HigherOrderFunctionType;

public class CAST5_1 implements HigherOrderFunctionType {

	private static int r = 4;
	private byte[] t1 = null, t2 = null;
	private byte[] key = Hex.decode("0123456712345678234567893456789A");

	public CAST5_1() {
		initSBOX();
		setState(1);
		t1 = new byte[16];
		t2 = new byte[16];
		Bits32ToBytes(0, t1, 4);
	}

	public int getR(int input) {
		Bits32ToBytes(input, t1, 0);
		encryptBlock(t1, 0, t2, 0);
		return BytesTo32bits(t2, 0);
	}

	public int getKeyLength() {
		return 8;
	}

	public int getKeyOrder() {
		return 2;
	}

	public int getL(int input) {
		Bits32ToBytes(input, t1, 0);
		encryptBlock(t1, 0, t2, 0);
		return BytesTo32bits(t2, 4);
	}

	public int getOrder() {
		return 8;
	}

	public int getPreRound(int input) {
		--_rounds;
		Bits32ToBytes(input, t1, 0);
		encryptBlock(t1, 0, t2, 0);
		++_rounds;
		return BytesTo32bits(t2, 4);
	}

	public int getRound() {
		return _rounds;
	}

	public int getPartion() {
		return 1;
	}

	public int roundFun(int input, long key) {
		return F(input, (int) key, (int) key & 0x1f);
	}

	public void setState(int s) {
		if (s == 0) {
			_workingKey = Hex.decode("00000000000000000000000000000000");
			setKey(_workingKey);
		} else {
			_workingKey = key;
			setKey(_workingKey);
		}
	}

	public String getKey() {
		return crypto.common.BasicMethod.toBitString(_Km[_rounds], 8);
	}

	protected int _Kr[] = new int[17]; // the rotating round key
	protected int _Km[] = new int[17]; // the masking round key

	private byte[] _workingKey = null;
	private int _rounds = r;

	protected void setKey(byte[] key) {
		int x[] = new int[16];
		for (int i = 0; i < key.length; i++) {
			x[i] = key[i] & 0xff;
			_Km[i + 1] = x[i];// S[x[i]];
		}
		for (int i = 1; i <= 16; ++i)
			_Kr[i] = _Km[i] & 0x1f;
	}

	protected void encryptBlock(byte[] src, int srcIndex, byte[] dst,
			int dstIndex) {
		int result[] = new int[2];

		int L0 = BytesTo32bits(src, srcIndex);
		int R0 = BytesTo32bits(src, srcIndex + 4);

		CAST_Encipher(L0, R0, result);

		Bits32ToBytes(result[0], dst, dstIndex);
		Bits32ToBytes(result[1], dst, dstIndex + 4);
	}

	protected final int F(int D, int Kmi, int Kri) {
		return S[(int) (Kmi ^ D)];
	}

	protected final void CAST_Encipher(int L0, int R0, int result[]) {
		int Lp = L0;
		int Rp = R0;
		int Li = L0, Ri = R0;
		for (int i = 1; i <= _rounds; i++) {
			Lp = Li;
			Rp = Ri;
			Li = Rp;
			Ri = Lp ^ F(Rp, _Km[i], _Kr[i]);
		}
		result[0] = Ri;
		result[1] = Li;
	}

	protected final void Bits32ToInts(int in, int[] b, int offset) {
		b[offset + 3] = (in & 0xff);
		b[offset + 2] = ((in >>> 8) & 0xff);
		b[offset + 1] = ((in >>> 16) & 0xff);
		b[offset] = ((in >>> 24) & 0xff);
	}

	protected final int IntsTo32bits(int[] b, int i) {
		return ((b[i] & 0xff) << 24) | ((b[i + 1] & 0xff) << 16)
				| ((b[i + 2] & 0xff) << 8) | ((b[i + 3] & 0xff));
	}

	protected final void Bits32ToBytes(int in, byte[] b, int offset) {
		b[offset + 3] = (byte) in;
		b[offset + 2] = (byte) (in >>> 8);
		b[offset + 1] = (byte) (in >>> 16);
		b[offset] = (byte) (in >>> 24);
	}

	protected final int BytesTo32bits(byte[] b, int i) {
		return ((b[i] & 0xff) << 24) | ((b[i + 1] & 0xff) << 16)
				| ((b[i + 2] & 0xff) << 8) | ((b[i + 3] & 0xff));
	}

	private int[] S = null;

	private void initSBOX() {
		S = new int[256];
		int[] tmp = new int[8];
		for (int i = 0; i < 256; ++i) {
			int t = 0, j = i;
			while (j > 0) {
				tmp[t++] = j & 1;
				j >>= 1;
			}
			for (j = t; j < 8; ++j)
				tmp[j] = 0;
			S[i] = 0;
			for (int k = 0; k < 8; ++k) {
				t = 0;
				for (int p = 0; p < 8; ++p)
					t ^= tmp[k] * tmp[p];
				S[i] |= (t << k);
			}
			// for (int p = 0; p < 8; ++p)
			// for (int q = p + 1; q < 8; ++q)
			// S[i] ^= tmp[p] * tmp[q];
			// S[i] = S[i] * 255;
		}
	}
	// public static void main(String[] args) {
	// CAST5_1 c = new CAST5_1();
	// for (int i = 0; i < 255; ++i)
	// System.out
	// .println(crypto.common.BasicMethod.toBitString(i, 8)
	// + " "
	// + crypto.common.BasicMethod.toBitString(c.getR(i),
	// 8));
	// }
}

package crypto.attack.differential.higher;

import org.bouncycastle.util.encoders.Hex;

import crypto.util.HigherOrderFunctionType;

public class CAST5Mini implements HigherOrderFunctionType {

	private static int r = 2;
	private byte[] t1 = null, t2 = null;
	private byte[] key = Hex.decode("0123456712345678234567893456789A");

	public CAST5Mini() {
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
		return 4;
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
			_Km[i + 1] = S[x[i]];
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

	protected final static int[] S = { 0xd4, 0x0b, 0x2f, 0x7a, 0x2f, 0xd3,
			0x40, 0x49, 0x27, 0xb5, 0x90, 0x75, 0xe0, 0xd2, 0x1d, 0x8e, 0x6f,
			0x59, 0xc8, 0xe2, 0xd3, 0x56, 0x1a, 0x2d, 0xd6, 0x19, 0x87, 0x2f,
			0xe1, 0x6b, 0x3a, 0xd0, 0xc8, 0x2f, 0x2f, 0xde, 0xac, 0xd8, 0xb7,
			0xa7, 0xef, 0x59, 0xed, 0x1f, 0xd0, 0x2e, 0x54, 0x35, 0x7f, 0xa2,
			0x10, 0x2d, 0x90, 0x9f, 0x11, 0x5d, 0x40, 0x3f, 0x2e, 0x65, 0xad,
			0xae, 0x6d, 0x50, 0xf2, 0x38, 0xd7, 0x72, 0x2f, 0x19, 0x54, 0xfe,
			0x8a, 0xdd, 0x45, 0x5d, 0xd5, 0x93, 0xe0, 0xb3, 0x5f, 0xb5, 0xdf,
			0x6a, 0xd5, 0x91, 0x5e, 0x67, 0x91, 0xeb, 0x8c, 0x0f, 0x27, 0x6b,
			0xaf, 0x91, 0x60, 0x0d, 0x26, 0xc9, 0x2d, 0x14, 0x3c, 0x79, 0xab,
			0x82, 0x82, 0xa6, 0x2e, 0xe6, 0x50, 0xc2, 0x05, 0xc8, 0xd6, 0xc9,
			0xcf, 0xd5, 0xf6, 0x11, 0x13, 0xd0, 0x86, 0x9e, 0x41, 0x95, 0x04,
			0x8d, 0xd0, 0x3c, 0xdf, 0x8e, 0x2b, 0x41, 0x75, 0xf5, 0xab, 0x26,
			0xd7, 0x82, 0x65, 0xf3, 0x94, 0x24, 0x3f, 0xe2, 0x02, 0xac, 0xb3,
			0x98, 0xb2, 0x6c, 0xde, 0x28, 0x69, 0x0f, 0xf6, 0x1e, 0x9b, 0xcc,
			0xf0, 0xad, 0x05, 0x91, 0xe6, 0xd4, 0xcc, 0x6d, 0x6c, 0x78, 0xc9,
			0xde, 0xda, 0x64, 0xd8, 0x96, 0xc3, 0xfb, 0x35, 0x6a, 0xbc, 0x4a,
			0x10, 0x0a, 0x2f, 0x53, 0x2e, 0x79, 0x8f, 0x8d, 0x96, 0x9d, 0x09,
			0xf0, 0xd8, 0x79, 0xd4, 0x74, 0x9e, 0xbd, 0xf9, 0xb1, 0xfd, 0x55,
			0xaf, 0x4d, 0x96, 0xe6, 0xf0, 0xf1, 0xea, 0xdb, 0x4a, 0xa0, 0x09,
			0xd9, 0x66, 0x25, 0x9d, 0xd0, 0xcf, 0xe1, 0xf1, 0x72, 0xa7, 0x70,
			0xf3, 0x79, 0x98, 0xe7, 0xb8, 0x4c, 0xd7, 0x5c, 0x59, 0x98, 0xdb,
			0x53, 0x23, 0x9e, 0x46, 0x6e, 0x0c, 0x71, 0x1c, 0xff, 0x08, 0xb9,
			0x83, 0x43, 0x79, 0x7d, 0x9c, 0x49, 0x00, 0xbf };

	// public static void main(String[] args) {
	// CAST5Mini c = new CAST5Mini();
	// for (int i = 0; i < 255; ++i)
	// System.out
	// .println(crypto.common.BasicMethod.toBitString(i, 8)
	// + " "
	// + crypto.common.BasicMethod.toBitString(c.getR(i),
	// 8));
	// }
}

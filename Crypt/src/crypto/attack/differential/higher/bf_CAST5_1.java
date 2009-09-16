package crypto.attack.differential.higher;

import crypto.util.FunctionType;

public class bf_CAST5_1 implements FunctionType {

	private int s = 0;

	public bf_CAST5_1(int _s) {
		s = _s;
		initSBOX();
	}

	public int calc(int input) {
		// TODO Auto-generated method stub
		return getIthBit(S[input], s);
	}

	public int getN() {
		// TODO Auto-generated method stub
		return 8;
	}

	private int getIthBit(int k, int i) {
		int ret = (int) ((k >> i) & 1);
		if (ret < 0)
			ret = 1;
		return ret;
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
			// System.out.println(S[i] + ",");
		}
	}

	public static void main(String[] args) {
		new bf_CAST5_1(0);
	}
}

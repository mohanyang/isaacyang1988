package crypto.attack.test;

import java.util.Arrays;

import org.bouncycastle.util.encoders.Hex;

public class TriviumFunction {

	Trivium t = new Trivium();
	int[] list = null;
	int d = 0, upper = 0;

	public TriviumFunction(int d) {
		this.d = d;
		upper = 1 << d;
		t.setKey(Hex.decode("80000000000000000000"));
		t.setIV(Hex.decode("00000000000000000000"));
		t.init();
	}

	public void setList(int[] newList) {
		list = newList;
	}

	public int calc(byte[] key) {
		Arrays.fill(t.iv, 0);
		t.setKey(key);
		int i, j, tmp, ret = 0;
		for (i = 0; i != upper; ++i) {
			tmp = i;
			for (j = 0; j != d; ++j) {
				if ((tmp & 1) == 1)
					t.iv[list[j]] = 1;
				else
					t.iv[list[j]] = 0;
				tmp >>= 1;
			}
			t.init();
			ret ^= t.process();
		}
		return ret;
	}

	public int c1(byte[] key) {
		Arrays.fill(t.iv, 0);
		t.setKey(key);
		for (int i = 0; i != d; ++i)
			t.iv[list[i]] = 1;
		t.init();
		return t.process();
	}
}

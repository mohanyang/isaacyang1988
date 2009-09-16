package crypto.common;

public class BasicTest {

	private static int[] L = { 1, 254, 255 };
	private static int n = 8;
	private static int[] m = null, p = null;

	private static void init() {
		m = new int[32];
		p = new int[32];
		int all = 0;
		for (int i = 0; i < 31; ++i) {
			m[i] = (1 << i);
			all = all | m[i];
		}
		for (int i = 0; i < 31; ++i) {
			p[i] = 0;
			for (int j = 0; j < i; ++j)
				p[i] = p[i] | m[j];
			p[i] = all - p[i];
		}
	}

	private static boolean twoN(int k) {
		return (k & (k - 1)) == 0 ? true : false;
	}

	private static boolean independent() {
		int[] a = L.clone();
		int d = L.length, t, s, j;

		System.out.println();
		for (int i = 0; i < a.length; ++i)
			System.out.println(crypto.common.BasicMethod.toBitString(L[i], n));

		for (int i = 0; i < n; ++i) {
			s = n - i - 1;
			for (t = 0; t < d && !((a[t] & m[s]) != 0 && twoN(a[t] & p[s])); ++t) {
			}
			System.out.println(t);
			if (t < d) {
				for (j = 0; j < t; ++j)
					if ((a[j] & m[s]) != 0 && ((a[j] & p[s + 1]) == 0)) {
						a[j] = a[j] ^ a[t];
						if (a[j] == 0)
							return false;
					}
				for (j = t + 1; j < d; ++j)
					if ((a[j] & m[s]) != 0 && ((a[j] & p[s + 1]) == 0)) {
						a[j] = a[j] ^ a[t];
						if (a[j] == 0)
							return false;
					}
			}

			for (int tt = 0; tt < a.length; ++tt)
				System.out.println(crypto.common.BasicMethod.toBitString(a[tt],
						n));
		}
		return true;
	}

	private static byte getIthBit(long k, int i) {
		int ret = (int) (Long.rotateRight(k, i) & 1);
		if (ret < 0)
			ret = 1;
		return (byte) ret;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// byte[] read = BasicMethod.readBinFile("1.exe");
		// System.out.println("finished");
		// BasicMethod.writeBinFile("2.exe", read);
		// System.out.println("finished");
		// System.out.println(BasicMethod.toBitString(10, 6));
		// init();
		// independent();
		// for (int i = 0; i < 256; ++i)
		// System.out.println(BasicMethod.toBitString(i, 32));
		long a = Long.valueOf("1234567890123");
		System.out.println(Long.bitCount(a));
		System.out.println(BasicMethod.toBitString(a, 64));
		for (int i = 0; i < 64; ++i)
			System.out.println(i + " " + getIthBit(a, i));
	}
}

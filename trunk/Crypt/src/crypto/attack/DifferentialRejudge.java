package crypto.attack;

import crypto.util.FunctionType;

public class DifferentialRejudge {

	private FunctionType fun = null;
	private int[] L = null;// { 1, 10 };
	// 0001
	// 1010
	private int[] a = null;
	private int[] m = null, p = null;
	private int n = 0;
	private int w = 0;

	public DifferentialRejudge(FunctionType _fun) {
		fun = _fun;
		n = fun.getN();
		init();
		try {
			int k = 3;
			L = new int[k];
			L[0] = Integer.valueOf("110111", 2);
			L[1] = Integer.valueOf("111101", 2);
			L[2] = Integer.valueOf("111111", 2);
			for (int i = 0; i < k; ++i)
				System.out.println(L[i]);
			constructAFromL();
			System.out.println("independent " + independent());
			System.out.println("perform " + perform());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void init() {
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

	private void constructAFromL() {
		a = new int[n];
		int[] t = L.clone();
		for (int i = 0; i < n; ++i) {
			StringBuffer tmp = new StringBuffer();
			for (int j = 0; j < t.length; ++j) {
				tmp.append(t[j] & 1);
				t[j] >>= 1;
			}
			a[i] = Integer.valueOf(tmp.toString(), 2);
			System.out.println(crypto.common.BasicMethod.toBitString(a[i], 6));
		}
	}

	private int toBitValue(int k) {
		int ret = 0;
		while (k > 0) {
			ret = ret ^ (k & 1);
			k >>= 1;
		}
		return ret;
	}

	private boolean twoN(int k) {
		return (k & (k - 1)) == 0 ? true : false;
	}

	private boolean independent() {
		int[] a = L.clone();
		int d = L.length, t, s, j;

		for (int i = 0; i < d / 2; ++i) {
			t = a[i];
			a[i] = a[d - 1 - i];
			a[d - 1 - i] = t;
		}
		for (int i = 0; i < n; ++i) {
			s = n - i - 1;
			System.out.println("round " + i);
			for (t = 0; t < d && !((a[t] & m[s]) != 0 && twoN(a[t] & p[s])); ++t) {
				System.out.println(crypto.common.BasicMethod.toBitString(a[t],
						n));
			}
			System.out.println(i + " " + t);
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
		}
		for (int i = 0; i < d; ++i)
			System.out.println(crypto.common.BasicMethod.toBitString(a[i], n));
		return true;
	}

	private boolean perform() {
		int sigma = 0, t = 0, v = 0;
		for (w = 0; w < (1 << n); ++w) {
			sigma = 0;
			for (int i = 0; i < (1 << L.length); ++i) {
				t = 0;
				for (int j = 0; j < n; ++j) {
					t = t | (toBitValue(i & a[j]) << j);
					System.out.print(toBitValue(i & a[j]) + " ");
				}
				System.out.println((t ^ w) + " - " + fun.calc(t ^ w) + " ");
				sigma ^= fun.calc(t ^ w);
			}
			System.out.println();
			System.out.println(w + " " + sigma);
			if (w == 0) {
				v = sigma;
			} else {
				if (v != sigma)
					return false;
			}
		}
		return true;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// for (int i = 1; i <= 8; ++i) {
		// System.out.println(i);
		// new DifferentialRejudge(new bfCAST5(i, 0));
		// }
		// new DifferentialRejudge(new bfCAST5(1, 0));
		// new DifferentialRejudge(new CAST5(0, 3));
		new DifferentialRejudge(new bfDES(4));
		// for (int j = 1; j <= 16; ++j) {
		// System.out.println(i + " " + j);
		// new DifferentialRejudge(new bfCAST5(i, j));
		// }
	}
}

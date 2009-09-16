package crypto.math;

import java.io.PrintWriter;

import crypto.util.FunctionType;

public class Differential {

	private FunctionType fun = null;
	private int[] L = null;
	private int[] a = null;
	private int[] m = null, p = null;
	private int n = 0;
	private int w = 0;
	private int total = 0;
	private PrintWriter Log = null;

	public Differential(FunctionType _fun, int _k, PrintWriter log) {
		fun = _fun;
		n = fun.getN();
		Log = log;
		init();
		int k = _k;
		L = new int[k];
		make(k, 1 << n);
		Log.print("total solution " + total);
		Log.flush();
		Log.close();
		System.out.println("total solution " + total);
	}

	private void make(int k, int range) {
		if (k == 0) {
			if (independent()) {
				constructAFromL();
				if (perform()) {
					++total;
					for (int i = 0; i < L.length; ++i)
						Log.print(crypto.common.BasicMethod
								.toBitString(L[i], n)
								+ " ");
					Log.println("");
				}
			}
		} else {
			for (int i = range - 1; i > 0; --i) {
				L[k - 1] = i;
				make(k - 1, i);
			}
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
			for (t = 0; t < d && !((a[t] & m[s]) != 0 && twoN(a[t] & p[s])); ++t)
				;
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
				}
				sigma ^= fun.calc(t ^ w);
			}
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
		new Differential(new crypto.attack.bfDES(1), 4, new PrintWriter(
				System.out));
	}
}

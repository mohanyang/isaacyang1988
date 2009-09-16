package crypto.math;

import java.io.PrintWriter;

import crypto.util.FunctionType;

public class ANF {
	int n = 0;
	int upper = 0;
	FunctionType f = null;
	boolean v[] = null, t[] = null, u[] = null;

	public ANF(FunctionType f, PrintWriter out) {
		this.f = f;
		n = f.getN();
		upper = 1 << (n - 1);
		v = new boolean[1 << n];
		t = new boolean[upper];
		u = new boolean[upper];
		calc();
		show(out);
	}

	void calc() {
		for (int i = 0; i != (1 << n); ++i)
			v[i] = f.calc(i) == 0 ? false : true;
		for (int i = 0; i != n; ++i) {
			for (int j = 0; j != upper; ++j) {
				t[j] = v[2 * j];
				u[j] = ((v[2 * j] ? 1 : 0) ^ (v[2 * j + 1] ? 1 : 0)) == 1 ? true
						: false;
			}
			for (int j = 0; j < upper; ++j)
				v[j] = t[j];
			for (int j = 0; j < upper; ++j)
				v[j + upper] = u[j];
		}
	}

	void show(PrintWriter out) {
		int total = 0, maxOrder = 0, tmp = 0;
		for (int i = (1 << n) - 1; i >= 0; --i)
			if (v[i]) {
				if (total > 0)
					out.print(" + ");
				String bin = crypto.common.BasicMethod.toBitString(i, n);
				StringBuffer ret = new StringBuffer();
				tmp = 0;
				for (int j = 0; j < bin.length(); ++j)
					if (bin.charAt(j) == '1') {
						ret.append("x" + (n - j - 1));
						++tmp;
					}

				// ret.append("x" + j);
				if (i == 0)
					ret.append("1");
				out.print(ret.toString());
				if (tmp > maxOrder)
					maxOrder = tmp;
				++total;
			}
		if (total == 0)
			out.print(0);
		out.println();
		this.maxOrder = maxOrder;
		out.println("The order of the polynomial is " + maxOrder);
		out.println("The number of mini-item is " + total);
	}

	private int maxOrder = 0;

	public int getMaxOrder() {
		return maxOrder;
	}
}

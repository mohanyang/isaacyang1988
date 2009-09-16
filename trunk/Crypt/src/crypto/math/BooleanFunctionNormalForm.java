package crypto.math;

import crypto.util.FunctionType;

import java.io.PrintStream;

public class BooleanFunctionNormalForm {

	private int n = 0;
	private int upper = 1;
	private byte[][] a = null;
	private int[] b = null;
	private int[] c = null;
	private FunctionType fun = null;

	public BooleanFunctionNormalForm(FunctionType _fun, PrintStream out) {
		fun = _fun;
		n = fun.getN();
		upper = (1 << n);
		makeMatrix();
		c = new crypto.math.Gauss(a, b, upper).getSolution();
		show(out);
	}

	private void makeMatrix() {
		a = new byte[upper][];
		b = new int[upper];
		int[] tmp = new int[n];
		int t, j, k;
		byte s;
		for (int i = 0; i < upper; ++i) {
			a[i] = new byte[upper];
			b[i] = fun.calc(i);
			t = 0;
			k = i;
			while (k > 0) {
				tmp[t++] = k & 1;
				k >>= 1;
			}
			for (j = t; j < n; ++j)
				tmp[j] = 0;
			for (j = 0; j < upper; ++j) {
				s = 1;
				t = 0;
				k = j;
				while (k > 0) {
					if ((k & 1) == 1) {
						s &= tmp[t];
					}
					t++;
					k >>= 1;
				}
				a[i][j] = s;
			}
		}
	}

	private void show(PrintStream out) {
		int total = 0, maxOrder = 0, tmp = 0;
		for (int i = upper - 1; i >= 0; --i)
			if (c[i] != 0) {
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
		out.println("The order of the polynomial is " + maxOrder);
		out.println("The number of miniitem is " + total);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// new BooleanFunctionNormalForm(new crypto.attack.bfCAST5(1, 21));
		// new BooleanFunctionNormalForm(new crypto.attack.bfDES(1, 2),
		// System.out);
		new BooleanFunctionNormalForm(new crypto.attack.bfRND(), System.out);
		// try {
		// PrintStream out = new PrintStream("d://5.txt");
		// // for (int i = 3; i < 8; i += 4)
		// // for (int j = 0; j < 8; ++j) {
		// // out.println(i + " " + j);
		// // new BooleanFunctionNormalForm(
		// // new crypto.attack.differential.higher.bf_CAST5Mini(
		// // i, j), out);
		// // }
		// // for (int i = 0; i < 8; ++i) {
		// // out.println(i);
		// // new BooleanFunctionNormalForm(new crypto.attack.bfDES(i), out);
		// // }
		//			
		// // new BooleanFunctionNormalForm(
		// // new crypto.attack.differential.higher.bf_CAST5Bit(), System.out);
		// // for (int i = 0; i < 8; ++i)
		// // new BooleanFunctionNormalForm(
		// // new crypto.attack.differential.higher.bf_CAST5_1(i),
		// // System.out);
		// // for (int i = 0; i < 8; ++i)
		// // for (int j = 0; j < 8; ++j)
		// // new BooleanFunctionNormalForm(
		// // new crypto.attack.differential.higher.bf_CAST5_2(i, j),
		// // System.out);
		// } catch (Exception e) {
		// }
	}
}

package crypto.attack.differential.higher.data;

import java.io.FileOutputStream;
import java.io.PrintStream;

import crypto.util.FunctionType;
import crypto.attack.*;

public class distribution {
	private FunctionType fun = null;
	private PrintStream out = null;
	private int n = 0;
	private int upper = 0;
	private int upperd = 0;
	private int total = 10000;
	java.util.Random rnd = null;

	public distribution(FunctionType fun, int d, PrintStream out) {
		this.fun = fun;
		this.out = out;
		n = fun.getN();
		rnd = new java.util.Random();

		upper = 0;
		for (int i = 0; i < n; ++i)
			upper |= (1 << i);
		upperd = (1 << d) - 1;

		calc();
	}

	private void calc() {
		int sigma = 0, v = 0, w = 0;
		byte[] container = new byte[4];
		for (int k = 0; k < total; ++k) {
			rnd.nextBytes(container);
			w = BytesTo32bits(container, 0) & upper;
			sigma = 0;
			for (int i = 0; i <= upperd; ++i) {
				sigma ^= fun.calc(i ^ w);
			}
			if (sigma == 0)
				++v;
		}
		out.println("ratio of 0 to 1 is " + v + " : " + (total - v) + " = "
				+ ((double) v / (double) total));
	}

	private int BytesTo32bits(byte[] b, int i) {
		return ((b[i] & 0xff) << 24) | ((b[i + 1] & 0xff) << 16)
				| ((b[i + 2] & 0xff) << 8) | ((b[i + 3] & 0xff));
	}

	public static void main(String[] args) {
		try {
			PrintStream out = new PrintStream(new FileOutputStream(
					"d://123.txt"));
			for (int j = 1; j <= 1000; ++j) {
				System.out.println("Test point " + j);
				out.println("Test point " + j);
				bfRND bf = new bfRND();
				// new crypto.math.BooleanFunctionNormalForm(bf, out);
				// out.flush();
				for (int i = 1; i <= bf.getN(); ++i) {
					new distribution(bf, i, out);
					out.flush();
				}
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

/**
 * 
 */
package crypto.attack.differential.higher;

import java.io.PrintStream;

import crypto.util.FunctionType;

/**
 * @author Isaac
 * 
 */
public class OrderTest {

	private PrintStream out = null;
	private FunctionType fun = null;
	private int n = 32, d = 15, total = 40, upper = 0, upperd = 0;
	java.util.Random rnd = null;

	public OrderTest(FunctionType _fun, PrintStream _out) {
		fun = _fun;
		out = _out;
		n = fun.getN();
		construct();
		rnd = new java.util.Random();
		out.println(perform());
	}

	private void construct() {
		upper = 0;
		for (int i = 0; i < n; ++i)
			upper |= (1 << i);
		for (int i = 0; i < d; ++i)
			upperd |= (1 << i);
	}

	private boolean perform() {
		int sigma = 0, v = 0, w = 0;
		byte[] container = new byte[4];
		for (int k = 0; k < total; ++k) {
			rnd.nextBytes(container);
			w = BytesTo32bits(container, 0);
			// Math.abs(rnd.nextInt() & upper);
			sigma = 0;
			for (int i = 0; i != upperd; ++i) {
				sigma ^= fun.calc(i ^ w);
				// if ((i & 0xffffff) == 0)
				// out.println(crypto.common.BasicMethod.toBitString(i, 32));
			}
			sigma ^= fun.calc(upperd ^ w);
			out.println("Value of "
					+ crypto.common.BasicMethod.toBitString(w, n) + " is "
					+ sigma);
			if (k == 0) {
				v = sigma;
			} else {
				if (v != sigma)
					return false;
			}
		}
		return true;
	}

	private int BytesTo32bits(byte[] b, int i) {
		// return ((b[i] & 0xff) << 24) | ((b[i + 1] & 0xff) << 16)
		// | ((b[i + 2] & 0xff) << 8) | ((b[i + 3] & 0xff));
		return ((b[i] & 0xff) << 8) | (b[i + 1] & 0xff);
	}

	public static void main(String[] args) {
		// new crypto.attack.differential.higher.OrderTest(
		// new crypto.attack.differential.higher.bf_CAST5(Integer
		// .valueOf(args[0]), Integer.valueOf(args[1])),
		// System.out);
		// for (int i = 0; i < 8; ++i)
		// for (int j = 0; j < 8; ++j)
		// new OrderTest(new bf_CAST5Mini(i, j), System.out);
		new OrderTest(new bf_IDEA(1), System.out);
	}
}

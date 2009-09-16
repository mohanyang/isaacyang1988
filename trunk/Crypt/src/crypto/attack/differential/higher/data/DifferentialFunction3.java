package crypto.attack.differential.higher.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;

import crypto.common.BasicMethod;
import crypto.common.Lib;
import crypto.math.ANF;
import crypto.util.FunctionType;

public class DifferentialFunction3 implements FunctionType {
	private int b = 0, n = 0, width = 0, upper = 0, max = 0, order = 0;
	private int[] point = null;
	private int[] a = null;

	public DifferentialFunction3() {
		init();
		int tmp = -1;
		for (int i = 0; i != upper; ++i)
			if (a[i] > tmp)
				tmp = a[i];
		for (width = 1; (1 << width) < tmp; ++width)
			;
	}

	public DifferentialFunction3(FunctionType f) {
		n = f.getN();
		upper = 1 << n;
		max = upper - 1;
		a = new int[upper];
		for (int i = 0; i != upper; ++i)
			a[i] = f.calc(i);
		int tmp = -1;
		for (int i = 0; i != upper; ++i)
			if (a[i] > tmp)
				tmp = a[i];
		for (width = 1; (1 << width) < tmp; ++width)
			;
	}

	private void init() {
		n = 4;
		upper = 1 << n;
		max = upper - 1;
		a = new int[upper];
		for (int i = 0; i != upper; ++i)
			a[i] = s3[i];
	}

	public void setBit(int b) {
		this.b = b;
	}

	public int getWidth() {
		return width;
	}

	public void setOrder(int order, int[] point) {
		this.order = order;
		this.point = point;
	}

	private int value(int input) {
		int ret = a[input & max];
		ret = (ret & (1 << b)) >> b;
		return ret;
	}

	@Override
	public int calc(int input) {
		int ret = 0, bound = 1 << order, delta;
		for (int i = 0; i != bound; ++i) {
			String tmp = BasicMethod.toBitString(i, order);
			delta = 0;
			for (int j = 0; j != order; ++j)
				if (tmp.charAt(j) == '1') {
					delta ^= point[j];
				}
			ret ^= value(input ^ delta);
		}
		return ret;
	}

	@Override
	public int getN() {
		return n;
	}

	public static void main(String[] args) {
		try {
			Lib.seedRandom(new Date().getTime());
			DifferentialFunction3 df = new DifferentialFunction3(new bf9());
			PrintWriter log = new PrintWriter(new File("log.txt"));
			// df.setOrder(1, new int[] { 23, 44, 59 });
			// df.setOrder(1, new int[] { 12, 38, 42 });
			// df.setOrder(1, new int[] { 22, 37, 51 });
			// System.out.println(new ANF(df, log).getMaxOrder());
			for (int bit = 0; bit < df.getWidth(); ++bit) {
				df.setBit(bit);
				df.setOrder(0, new int[] {});
				int bound = new ANF(df, log).getMaxOrder();
				System.out.println(bound);
				int count = 0;
				for (int i = 1; i != (1 << df.getN()); ++i) {
					df.setOrder(1, new int[] { i });
					int order = new ANF(df, log).getMaxOrder();
					if (order <= bound - 2)
						System.out.println(i + " " + order);
					// if (order < 3) {
					// ++count;
					// System.out.println(i);
					// }
				}
				System.err.println(count);
			}
			log.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static int[] s = { 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0,
			1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0,
			1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0,
			0, 1, 1, 1 };
	private static int[] s1 = { 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0,
			0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0,
			1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0,
			0, 0, 1, 0 };
	private static int[] s2 = { 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0,
			0, 1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0,
			1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 1 };
	private static int[] s3 = { 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0 };
}

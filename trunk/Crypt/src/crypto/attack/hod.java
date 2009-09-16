package crypto.attack;

import crypto.util.FunctionType;

public class hod {

	private FunctionType fun = null;
	private int[] y = null;
	private int[] L = { 1, 10 };
	// 0001
	// 1010
	private int n = 0;
	private int[] a = { 1, 0, 1, 2 };
	private int d = L.length;
	private int lk = 4;
	private int w = 1;
	private int xr = 9;

	public hod(FunctionType _fun, int _n) {
		fun = _fun;
		n = _n;
		initArrayY();
		perform();
	}

	public void initArrayY() {
		y = new int[1 << d];
		int t;
		for (int i = 0; i < (1 << d); ++i) {
			t = 0;
			for (int j = 0; j < 2 * n; ++j) {
				t = t | (toBitValue(i & a[j]) << (2 * n - 1 - j));
				System.out.println((toBitValue(i & a[j]) << (2 * n - 1 - j))
						+ " " + t);
			}

			y[i] = fun.calc(xr + t ^ w);
			// System.out.println(i + " " + (xr + t ^ w) + " " + y[i]);
		}
	}

	private int toBitValue(int k) {
		int ret = 0;
		while (k > 0) {
			ret = ret ^ (k % 2);
			k = k >> 1;
		}
		return ret;
	}

	private void perform() {
		for (int k = 0; k < (1 << lk); ++k) {
			int sigma = 0, yy;
			for (int i = 0; i < (1 << d); ++i) {
				yy = y[i];// sigma =
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new hod(new BooleanFunction(9), 2);
	}

}

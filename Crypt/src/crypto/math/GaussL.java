package crypto.math;

public class GaussL {

	private double[][] a = null;
	private double[] b = null;
	private double[] x = null;
	private int n = 0;

	public GaussL(byte[][] _a, int[] _b, int _n, int _m) {
		n = _n;
		a = new double[n][];
		for (int i = 0; i < n; ++i) {
			a[i] = new double[n];
			for (int j = 0; j < n; ++j) {
				a[i][j] = 0;
				for (int k = 0; k < _m; ++k)
					a[i][j] += _a[k][i] * _a[k][j];
			}
		}
		b = new double[n];
		for (int i = 0; i < n; ++i) {
			b[i] = 0;
			for (int j = 0; j < _m; ++j)
				b[i] += _a[j][i] * _b[j];
		}
		perform();
	}

	public double[] getSolution() {
		return x;
	}

	private void swap(int x, int y) {
		for (int i = 0; i < n; ++i) {
			double t = a[x][i];
			a[x][i] = a[y][i];
			a[y][i] = t;
		}
	}

	private void perform() {
		int k;
		double c = 0;
		for (int i = 0; i < n; ++i) {
			for (k = i; k < n && a[k][i] == 0; ++k)
				;
			if (k < n) {
				if (k != i)
					swap(i, k);
				for (int j = k + 1; j < n; ++j)
					if (a[j][i] != 0) {
						c = a[j][i] / a[i][i];
						b[j] -= b[i] * c;
						for (int t = i; t < n; ++t)
							a[j][t] -= a[i][t] * c;
					}
			}
		}
		x = new double[n];
		for (int i = n - 1; i >= 0; --i) {
			System.out.println(a[i][i]);
			if (a[i][i] != 0) {
				c = b[i];
				for (int j = n - 1; j > i; --j)
					c -= a[i][j] * x[j];
				x[i] = c / a[i][i];
			} else if (b[i] == 0) {
				x[i] = 0;
			} else {
				System.err.println("Error!");
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		byte[][] a = new byte[][] { { 1, 0, 1, 1 }, { 1, 1, 1, 1 },
				{ 0, 1, 1, 1 }, { 1, 1, 0, 1 } };
		int[] b = new int[] { 0, 0, 0, 1 };
		double x[] = new GaussL(a, b, 4, 4).getSolution();
		for (int i = 0; i < 4; ++i)
			System.out.println(x[i]);
	}

}

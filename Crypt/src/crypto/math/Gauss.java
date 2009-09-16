package crypto.math;

public class Gauss {

	private byte[][] a = null;
	private int[] b = null;
	private int[] x = null;
	private int n = 0;

	public Gauss(byte[][] _a, int[] _b, int _n) {
		a = _a;
		b = _b;
		n = _n;
		perform();
	}

	public int[] getSolution() {
		return x;
	}

	private void swap(int x, int y) {
		for (int i = 0; i < n; ++i) {
			byte t = a[x][i];
			a[x][i] = a[y][i];
			a[y][i] = t;
		}
	}

	private void perform() {
		int k;
		for (int i = 0; i < n; ++i) {
			for (k = i; k < n && a[k][i] == 0; ++k)
				;
			if (k < n) {
				if (k != i)
					swap(i, k);
				for (int j = k + 1; j < n; ++j)
					if (a[j][i] != 0) {
						b[j] ^= b[i];
						for (int t = i; t < n; ++t)
							a[j][t] ^= a[i][t];
					}
			}
		}
		x = new int[n];
		for (int i = n - 1; i >= 0; --i) {
			if (a[i][i] != 0) {
				k = b[i];
				for (int j = n - 1; j > i; --j)
					k ^= (a[i][j] & x[j]);
				x[i] = k;
			} else {
				x[i] = 0;
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
		int x[] = new Gauss(a, b, 4).getSolution();
		for (int i = 0; i < 4; ++i)
			System.out.println(x[i]);
	}

}

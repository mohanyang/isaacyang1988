package crypto.math;

public class Jacobi {

	private int[][] a = null;
	private int[] b = null;
	private int[] x = null;
	private int n = 0;

	public Jacobi(int[][] _a, int[] _b, int _n) {
		a = _a;
		b = _b;
		n = _n;
		perform();
	}

	public int[] getSolution() {
		return x;
	}

	private void perform() {
		boolean flag = true;
		x = new int[n];
		int[] y = new int[n];
		for (int i = 0; i < n; ++i)
			x[i] = 1;
		while (flag) {
			for (int i = 0; i < n; ++i)
				y[i] = x[i];

			for (int i = 0; i < n; ++i)
				if (a[i][i] != 0) {
					x[i] = b[i];
					for (int j = 0; j < i; ++j)
						x[i] ^= (a[i][j] & x[j]);
					for (int j = i + 1; j < n; ++j)
						x[i] ^= (a[i][j] & y[j]);
					// for (int j = 0; j < i; ++j)
					// x[i] ^= (a[i][j] & y[j]);
					// for (int j = i + 1; j < n; ++j)
					// x[i] ^= (a[i][j] & y[j]);
				}
			flag = !judge();
		}
	}

	private boolean judge() {
		int tmp;
		for (int i = 0; i < n; ++i) {
			tmp = b[i];
			for (int j = 0; j < n; ++j)
				tmp ^= (a[i][j] & x[j]);
			if (tmp != 0)
				return false;
		}
		return true;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[][] a = new int[][] { { 1, 0, 1, 1 }, { 1, 1, 1, 1 },
				{ 0, 1, 1, 1 }, { 1, 1, 0, 1 } };
		int[] b = new int[] { 0, 0, 0, 1 };
		int x[] = new Jacobi(a, b, 4).getSolution();
		for (int i = 0; i < 4; ++i)
			System.out.println(x[i]);
	}
}

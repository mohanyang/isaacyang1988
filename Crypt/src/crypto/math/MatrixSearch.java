package crypto.math;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MatrixSearch {

	private byte[][] a = null;
	private int[] b = null;
	private int[] x = null, y = null;
	private int n = 0, m = 0;
	private boolean find = false;
	private Map<Integer, List<Integer>> ListMap = null;

	public MatrixSearch(byte[][] _a, int[] _b, int _n, int _m,
			Map<Integer, List<Integer>> _ListMap) {
		a = _a;
		b = _b;
		n = _n;
		m = _m;
		ListMap = _ListMap;
		x = new int[n];
		perform(0);
	}

	public int[] getSolution() {
		return y;
	}

	private void perform(int k) {
		if (find)
			return;
		if (k == n) {
			if (judge()) {
				find = true;
				y = x.clone();
			}
		} else {
			for (int i = 0; i < 2; ++i) {
				x[k] = i;
				perform(k + 1);
			}
		}
	}

	private boolean judge() {
		int t = 0, tt = 1;
		for (int i = 0; i < n; ++i) {
			t = 0;
			for (int j = 0; j < m; ++j)
				if (a[i][j] != 0) {
					List<Integer> l = ListMap.get(j);
					tt = 1;
					for (Iterator<Integer> itr = l.iterator(); itr.hasNext();)
						tt &= x[itr.next()];
					t ^= tt;
				}
			if (t != b[i])
				return false;
		}
		return true;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		byte[][] a = new byte[][] { { 1, 0, 1, 1 }, { 1, 1, 1, 1 },
				{ 0, 1, 1, 1 }, { 1, 1, 0, 1 } };
		int[] b = new int[] { 0, 0, 0, 1 };
		Map<Integer, List<Integer>> ListMap = new HashMap<Integer, List<Integer>>();
		List<Integer> l = null;
		for (int i = 0; i < 4; ++i) {
			l = new LinkedList<Integer>();
			l.add(i);
			ListMap.put(i, l);
		}
		int x[] = new MatrixSearch(a, b, 4, 4, ListMap).getSolution();
		for (int i = 0; i < 4; ++i)
			System.out.println(x[i]);
	}

}

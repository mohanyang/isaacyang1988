package crypto.math;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import crypto.util.FunctionType;

public class ExplicitBooleanNormalForm {
	private int ni = 0, no = 0, m = 0, p1 = 0, p2 = 0, p3 = 0;
	private int upper = 1;
	private boolean[][] a = null;
	private Set<Integer>[] b = null;
	private String[] map = null;
	private FunctionType fun = null;

	public ExplicitBooleanNormalForm(FunctionType _fun, PrintStream out) {
		fun = _fun;
		ni = fun.getN();
		no = 4;
		m = 1 + ni + no + ni * no + ni * (ni + 1) / 2 + no * (no + 1) / 2;
		p1 = 1 + ni + no;
		p2 = 1 + ni + no + ni * no;
		p3 = 1 + ni + no + ni * no + ni * (ni + 1) / 2;
		upper = 1 << ni;
		makeMatrix();
		makeList();
		show(out);
	}

	private void makeMatrix() {
		int t = 0;
		String s = null;
		a = new boolean[m][];
		for (int i = 0; i < m; ++i)
			a[i] = new boolean[upper];
		for (int i = 0; i < upper; ++i)
			a[0][i] = true;
		for (int i = 0; i < upper; ++i) {
			t = fun.calc(i);
			s = toBitString(i, ni);
			for (int j = 0; j < ni; ++j)
				a[j + 1][i] = (s.charAt(j) == '1');
			s = toBitString(t, no);
			for (int j = 0; j < ni; ++j)
				a[j + 1 + ni][i] = (s.charAt(j) == '1');
			for (int j = 0; j < ni; ++j)
				for (int k = 0; k < no; ++k)
					a[p1 + j * no + k][i] = a[j + 1][i] & a[k + ni + 1][i];
			t = 0;
			for (int j = 0; j < ni; ++j)
				for (int k = j; k < ni; ++k)
					a[p2 + t++][i] = a[j + 1][i] & a[k + 1][i];
			t = 0;
			for (int j = 0; j < no; ++j)
				for (int k = j; k < no; ++k)
					a[p3 + t++][i] = a[j + ni + 1][i] & a[k + ni + 1][i];
		}
		map = new String[m];
		map[0] = "1";
		for (int i = 0; i < ni; ++i)
			map[i + 1] = "x" + i;
		for (int i = 0; i < no; ++i)
			map[i + 1 + ni] = "y" + i;
		for (int i = 0; i < ni; ++i)
			for (int j = 0; j < no; ++j)
				map[p1 + i * no + j] = "x" + i + "y" + j;
		t = 0;
		for (int i = 0; i < ni; ++i)
			for (int j = i; j < ni; ++j)
				map[p2 + t++] = "x" + i + "x" + j;
		t = 0;
		for (int i = 0; i < no; ++i)
			for (int j = i; j < no; ++j)
				map[p3 + t++] = "y" + i + "y" + j;
	}

	private void makeList() {
		int k = 0;
		b = new Set[m];
		for (int i = 0; i < m; ++i) {
			b[i] = new HashSet<Integer>();
			b[i].add(i);
		}
		for (int i = 0; i < upper; ++i) {
			for (k = 0; k < m; ++k)
				if (judge(i, k)) {
					for (int j = 0; j < k; ++j)
						if (a[j][i])
							reduce(j, k);
					for (int j = k + 1; j < m; ++j)
						if (a[j][i])
							reduce(j, k);
					break;
				}
		}
	}

	private void show(PrintStream out) {
		for (int i = 0; i < m; ++i)
			if (allZero(i) && b[i].size() > 1) {
				for (Iterator<Integer> itr = b[i].iterator(); itr.hasNext();)
					out.print(map[itr.next()] + " ");
				out.println();
			}
		for (int i = 0; i < m; ++i) {
			for (int j = 0; j < upper; ++j)
				out.print((a[i][j] ? 1 : 0) + " ");
			out.println();
		}
	}

	private boolean judge(int r, int k) {
		for (int i = 0; i < r; ++i)
			if (a[k][i])
				return false;
		return a[k][r];
	}

	private void reduce(int r, int s) {
		for (Iterator<Integer> itr = b[s].iterator(); itr.hasNext();) {
			int t = itr.next();
			if (b[r].contains(t)) {
				b[r].remove(t);
			} else {
				b[r].add(t);
			}
		}
		for (int i = 0; i < upper; ++i)
			a[r][i] = a[r][i] ^ a[s][i];
	}

	private boolean allZero(int k) {
		for (int i = 0; i < upper; ++i)
			if (a[k][i])
				return false;
		return true;
	}

	public String toBitString(int k, int n) {
		StringBuffer bf = new StringBuffer(Integer.toBinaryString(k));
		for (int i = bf.length(); i < n; ++i)
			bf.insert(0, "0");
		return bf.reverse().toString();
	}

	public static void main(String[] args) {
		// new ExplicitBooleanNormalForm(new crypto.attack.bf6(), System.out);
		try {
			// new ExplicitBooleanNormalForm(new crypto.attack.bfCAST5(1, 0),
			// new PrintStream("output.txt"));
			new ExplicitBooleanNormalForm(new crypto.attack.bf7(),
					new PrintStream("output.txt"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

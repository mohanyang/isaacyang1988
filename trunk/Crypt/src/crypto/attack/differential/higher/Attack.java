/**
 * 
 */
package crypto.attack.differential.higher;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import crypto.util.HigherOrderFunctionType;

/**
 * @author Isaac
 * 
 */
public class Attack {

	private HigherOrderFunctionType fun = null;
	private int d = 0, m = 0, M = 0, M1 = 0, kd = 0, partion = 4, n = 32;
	private int[] L = null;
	private int[] l = null;
	private byte[][] a = null;
	private int[] c = null;
	private int[] x = null;
	private int[] F = null;
	private int[] A = null;
	private long[] e = null;
	private int sigma = 0;
	private long[] b = null;
	private Map<Integer, List<Integer>> ListMap = new HashMap<Integer, List<Integer>>();
	private Map<Long, Integer> IndexMap = new HashMap<Long, Integer>();
	private PrintStream out = null;

	/**
	 * 
	 */
	public Attack(HigherOrderFunctionType _fun) {
		// TODO Auto-generated constructor stub
		try {
			out = new PrintStream("output.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fun = _fun;
		d = fun.getOrder();
		m = fun.getKeyLength();
		kd = fun.getKeyOrder();
		partion = fun.getPartion();
		generateDeg();
		init();
		make(d, n);
		for (int i = 0; i < M1; ++i) {
			for (int j = 0; j < M; ++j)
				out.print(a[i][j] + " ");
			out.println(c[i]);
		}
		System.out.println("Construct finished!");
		x = new crypto.math.GaussM(a, c, M).getSolution();
		// x = new crypto.math.MatrixSearch(a, c, n, M, ListMap).getSolution();
		// for (int i = 0; i < M1; ++i) {
		// int tx = 0;
		// for (int j = 0; j < M; ++j)
		// tx ^= a[i][j] * x[j];
		// if (tx != c[i])
		// System.err.println(i + " " + "Error!");
		// }

		show(System.out);
		StringBuffer bf = new StringBuffer(fun.getKey());
		System.out.println("\n" + bf.reverse().toString());
		out.flush();
		out.close();

		// for (int i = 0; i < M1; ++i) {
		// int tt = 0;
		// for (int j = 0; j < M; ++j)
		// tt ^= a[i][j] & x[j];
		// if (tt != c[i])
		// System.err.println(i);
		// }
		// int[] xx = new int[33];
		// int tk = fun.getKey();
		// for (int i = 31; i >= 0; --i) {
		// xx[i] = tk & 1;
		// tk >>= 1;
		// }

		// for (int i = 0; i < M; ++i) {
		// // System.out.println("Index " + i);
		// List<Integer> l = ListMap.get(i);
		// int tt = 1;
		// // System.out.print("Size " + l.size() + ": ");
		// for (int j = 0; j < l.size(); ++j) {
		// // System.out.print("k" + l.get(j) + " ");
		// tt *= x[l.get(j)];
		// }
		// // System.out.println();
		// // for (int j = 0; j < l.size(); ++j) {
		// // System.out.print(xx[l.get(j)] + " ");
		// // }
		// // System.out.println();
		// System.out.println(x[i] == tt);
		// // x[i] = tt;
		// }
		// for (int i = 0; i < M1; ++i) {
		// int tt = 0;
		// for (int j = 0; j < M; ++j)
		// tt ^= x[j] * a[i][j];
		// System.out.println(tt == c[i]);
		// }
	}

	private void generateDeg() {
		M = 0;
		for (int i = 1; i < kd; ++i) {
			M += CMN(m / partion, i) * partion;
		}
		M1 = ((int) Math.ceil((double) M / n)) * n;
		// M1 = n;
	}

	private int[] tmp = new int[33];
	private int total = 0;

	private void init() {
		a = new byte[M1][];
		for (int i = 0; i < M1; ++i)
			a[i] = new byte[M];
		c = new int[M1];
		F = new int[M + 1];
		A = new int[M];

		b = new long[64];
		for (int i = 0; i < 64; ++i)
			b[i] = Long.rotateLeft((long) 1, i);

		e = new long[M + 1];
		total = 0;
		for (int i = 1; i < kd; ++i)
			for (int j = 0; j < partion; ++j)
				constructArrayE(1, m / partion, i, i, m / partion * j);
		e[M] = 0;
		ListMap.put(M, null);
		IndexMap.put((long) 0, M);

		L = new int[d];
		total = 0;
	}

	private void constructArrayE(int m, int mm, int k, int kk, int offSet) {
		if (k == 0) {
			long tt = 0;
			for (int i = 1; i <= kk; ++i)
				tt |= b[tmp[i] + offSet - 1];
			ListMap.put(total, constructList(offSet, kk));
			IndexMap.put(tt, total);
			e[total++] = tt;
		} else {
			for (int i = m; i <= mm; ++i) {
				tmp[k] = i;
				constructArrayE(i + 1, mm, k - 1, kk, offSet);
			}
		}
	}

	private List<Integer> constructList(int offSet, int k) {
		List<Integer> ret = new LinkedList<Integer>();
		for (int i = k; i >= 1; --i)
			ret.add(tmp[i] + offSet - 1);
		return ret;
	}

	private void make(int k, int range) {
		if (total == M1)
			return;
		if (k == 0) {
			constructL();
			constructC();
			constructA();
			if (total == M1)
				return;
		} else {
			for (int i = range - 1; i > 0; --i) {
				L[k - 1] = (int) b[i];
				make(k - 1, i);
			}
		}
	}

	private void constructL() {
		l = new int[n];
		int[] t = L.clone();
		for (int i = 0; i < n; ++i) {
			StringBuffer tmp = new StringBuffer();
			for (int j = 0; j < t.length; ++j) {
				tmp.append(t[j] & 1);
				t[j] = t[j] >> 1;
			}
			l[i] = Integer.valueOf(tmp.toString(), 2);
		}
	}

	private void constructC() {
		fun.setState(1);
		int s = 0, t = 0;
		for (int i = 0; i < (1 << d); ++i) {
			t = 0;
			for (int j = 0; j < n; ++j) {
				t = t | (toBitValue(i & l[j]) << (n - 1 - j));
			}
			s ^= fun.getR(t);
		}
		fun.setState(0);
		for (int i = 0; i < (1 << d); ++i) {
			t = 0;
			for (int j = 0; j < n; ++j) {
				t = t | (toBitValue(i & l[j]) << (n - 1 - j));
			}
			s ^= fun.getPreRound(t);
		}
		fun.setState(1);
		sigma = s;
	}

	private void constructA() {
		int t = 0, Cra = 0;
		for (int i = 0; i <= M; ++i)
			F[i] = 0;
		for (int i = 0; i < (1 << d); ++i) {
			t = 0;
			for (int j = 0; j < n; ++j) {
				t = t | (toBitValue(i & l[j]) << (n - 1 - j));
			}
			Cra = fun.getL(t);
			for (int j = 0; j <= M; ++j) {
				F[j] ^= (fun.roundFun(Cra, e[j]));
			}
		}
		sigma ^= F[M];
		for (int i = 0; i < M; ++i) {
			A[i] = makeAFromList(ListMap.get(i));
		}
		for (int i = 0; i < n; ++i)
			for (int j = 0; j < M; ++j)
				a[i + total][j] = getIthBit(A[j], i);
		for (int i = 0; i < n; ++i) {
			c[i + total] = getIthBit(sigma, i);
		}
		total += n;
	}

	private int makeAFromList(List<Integer> l) {
		int len = l.size(), ret = 0, k, count;
		long tmp;
		for (int i = 0; i < (1 << len); ++i) {
			k = i;
			tmp = 0;
			count = 0;
			while (k > 0) {
				if ((k & 1) == 1)
					tmp |= b[l.get(count)];
				++count;
				k >>= 1;
			}
			ret ^= F[IndexMap.get(tmp)];
		}
		return ret;
	}

	private void show(PrintStream out) {
		for (int i = 0; i < m; ++i)
			out.print(x[i]);
	}

	private int toBitValue(long k) {
		return Long.bitCount(k) & 1;
	}

	private byte getIthBit(int k, int i) {
		int ret = (int) ((k >> i) & 1);
		if (ret < 0)
			ret = 1;
		return (byte) ret;
	}

	private int CMN(int m, int n) {
		long ret = 1;
		for (int i = 0; i < n; ++i)
			ret = ret * (m - i) / (i + 1);
		return (int) ret;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long start = System.currentTimeMillis();
		new Attack(new CAST5());
		// new Attack(new Daemon());
		// new Attack(new DES());
		// new DES().getKey();
		long end = System.currentTimeMillis();
		System.out.println("Total running time is " + (end - start) + " ms!");
	}
}

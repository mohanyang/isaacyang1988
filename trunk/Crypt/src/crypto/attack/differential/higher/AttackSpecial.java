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
public class AttackSpecial {

	private HigherOrderFunctionType fun = null;
	private int m = 0, M = 0, M1 = 0, kd = 0, partion = 4, n = 32;
	private int upper = 0;
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
	public AttackSpecial(HigherOrderFunctionType _fun) {
		try {
			out = new PrintStream("special_output.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		fun = _fun;
		m = fun.getKeyLength();
		kd = fun.getKeyOrder();
		partion = fun.getPartion();
		generateDeg();
		init();
		constructC();
		constructA();
		for (int i = 0; i < M1; ++i) {
			for (int j = 0; j < M; ++j)
				out.print(a[i][j] + " ");
			out.println(c[i]);
		}
		System.out.println("Construct finished!");
		x = new crypto.math.MatrixSearch(a, c, n, M, ListMap).getSolution();

		show(System.out);
		StringBuffer bf = new StringBuffer(fun.getKey());
		System.out.println("\n" + bf.reverse().toString());
		out.flush();
		out.close();
	}

	private void generateDeg() {
		M = 0;
		for (int i = 1; i < kd; ++i) {
			M += CMN(m / partion, i) * partion;
		}
		M1 = n;
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

		upper = 0;
		for (int i = 0; i < n; ++i)
			upper |= (1 << i);
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

	private void constructC() {
		fun.setState(1);
		int s = 0;
		for (int i = 0; i != upper; ++i) {
			s ^= fun.getR(i);
		}
		s ^= fun.getR(upper);
		fun.setState(0);
		for (int i = 0; i != upper; ++i) {
			s ^= fun.getPreRound(i);
		}
		s ^= fun.getPreRound(upper);
		fun.setState(1);
		sigma = s;
	}

	private void constructA() {
		int Cra = 0;
		for (int i = 0; i <= M; ++i)
			F[i] = 0;
		for (int i = 0; i != upper; ++i) {
			Cra = fun.getL(i);
			for (int j = 0; j <= M; ++j) {
				F[j] ^= (fun.roundFun(Cra, e[j]));
			}
		}
		Cra = fun.getL(upper);
		for (int j = 0; j <= M; ++j) {
			F[j] ^= (fun.roundFun(Cra, e[j]));
		}
		sigma ^= F[M];
		for (int i = 0; i < M; ++i) {
			A[i] = makeAFromList(ListMap.get(i));
		}
		for (int i = 0; i < n; ++i)
			for (int j = 0; j < M; ++j)
				a[i][j] = getIthBit(A[j], i);
		for (int i = 0; i < n; ++i) {
			c[i] = getIthBit(sigma, i);
		}
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
		long start = System.currentTimeMillis();
		new AttackSpecial(new CAST5());
		long end = System.currentTimeMillis();
		System.out.println("Total running time is " + (end - start) + " ms!");
	}
}

/**
 * 
 */
package crypto.attack;

import crypto.util.FunctionType;

/**
 * @author Isaac
 * 
 */
public class bf4 implements FunctionType {

	private int[] arrayTable = null;
	private static int n = 8;

	private int pow(int d, int k, int mod) {
		if (k == 0)
			return 1;
		int ans = pow(d, k >> 1, mod);
		// ans = (ans * ans) & (mod - 1);
		ans = (ans * ans) % mod;
		if ((k & 1) == 1)
			// ans = (ans * d) & (mod - 1);
			ans = (ans * d) % mod;
		return ans;
	}

	public bf4() {
		arrayTable = new int[1 << n];
		// int[] tmp = new int[n];
		for (int i = 0; i < (1 << n); ++i) {
			// arrayTable[i] = (i * i * i + 5 * i * i + 4 * i + 10)
			// & ((1 << n) - 1);
			// arrayTable[i] = (pow(3, i, 1 << n) + pow(5, i, 1 << n) + i * i *
			// i)
			// & ((1 << n) - 1);
			// arrayTable[i] = pow(i, (1 << n) - 1, (1 << n) + 1);
			arrayTable[i] = pow(3, i, (1 << n) + 1);
			// arrayTable[i] = pow(3, i, (1 << n) + 1) % 256;
			// int t = 0, j = i;
			// while (j > 0) {
			// tmp[t++] = j & 1;
			// j >>= 1;
			// }
			// for (j = t; j < 4; ++j)
			// tmp[j] = 0;
			// arrayTable[i] = (tmp[0] * tmp[1] * tmp[2])
			// ^ (tmp[0] * tmp[1] * tmp[3]) ^ (tmp[0] * tmp[2] * tmp[3])
			// ^ (tmp[1] * tmp[2] * tmp[3]) ^ (tmp[1] * tmp[2] * tmp[4])
			// ^ (tmp[0] * tmp[1] * tmp[4]) ^ (tmp[0] * tmp[1] * tmp[5])
			// ^ (tmp[0] * tmp[1] * tmp[6]) ^ (tmp[0] * tmp[1] * tmp[7])
			// ^ (tmp[1] * tmp[2] * tmp[6]) ^ (tmp[1] * tmp[2] * tmp[7]);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see crypto.attack.functionType#calc(int)
	 */
	public int calc(int input) {
		// TODO Auto-generated method stub
		return arrayTable[input];
	}

	public int getN() {
		return n;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		bf4 f = new bf4();
		for (int i = 0; i < (1 << n); ++i)
			System.out.println(crypto.common.BasicMethod.toBitString(i, n)
					+ " " + f.calc(i));
	}
}

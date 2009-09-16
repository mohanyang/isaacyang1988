package crypto.math;

import crypto.attack.differential.higher.data.DifferentialFunction;
import crypto.util.FunctionType;

public class InverseWalshTransform implements FunctionType {
	FunctionType bf = null;
	int n = 0, upper = 0;

	public InverseWalshTransform(FunctionType bf) {
		this.bf = bf;
		n = bf.getN();
		upper = 1 << n;
	}

	@Override
	public int calc(int input) {
		int ret = 0, tmpa, tmpb;
		for (int i = 0; i != upper; ++i) {
			tmpa = bf.calc(i);
			tmpb = (toBitValue(input & i) == 0 ? 1 : -1);
			ret += tmpa * tmpb;
		}
		return ret >> n;
	}

	private int toBitValue(int k) {
		int ret = 0;
		while (k > 0) {
			ret = ret ^ (k & 1);
			k >>= 1;
		}
		return ret;
	}

	@Override
	public int getN() {
		return n;
	}

	public static void main(String[] args) {
		DifferentialFunction df = new DifferentialFunction();
		df.setBit(0);
		df.setOrder(0, new int[] {});
		WalshTransform wt = new WalshTransform(df);
		InverseWalshTransform iwt = new InverseWalshTransform(wt);
		for (int i = 0; i != (1 << wt.getN()); ++i)
			System.out.println(i + " " + (df.calc(i) == iwt.calc(i)));
	}
}

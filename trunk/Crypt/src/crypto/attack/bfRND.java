package crypto.attack;

import crypto.util.FunctionType;

public class bfRND implements FunctionType {

	java.util.Random rnd = new java.util.Random();

	int s[] = null;
	int n = 16;
	int upper = 1 << n;

	public bfRND() {
		s = new int[upper];
		for (int i = 0; i < upper; ++i)
			s[i] = rnd.nextInt(2);
	}

	public int calc(int input) {
		return s[input];
	}

	public int getN() {
		return n;
	}
}

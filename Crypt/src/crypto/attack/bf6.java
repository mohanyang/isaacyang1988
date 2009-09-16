package crypto.attack;

import crypto.util.FunctionType;

public class bf6 implements FunctionType {

	private static final int[] t = { 7, 6, 0, 4, 2, 5, 1, 3 };

	public int calc(int input) {
		return t[input];
	}

	public int getN() {
		return 3;
	}

}

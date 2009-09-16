package crypto.attack;

import crypto.util.FunctionType;

public class bfADD implements FunctionType {

	private int s = 0;

	public bfADD(int s) {
		this.s = s;
	}

	public int calc(int input) {
		return (input + s) & 0xff;
	}

	public int getN() {
		return 8;
	}

}

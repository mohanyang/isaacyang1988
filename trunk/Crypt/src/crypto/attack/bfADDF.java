package crypto.attack;

import crypto.util.FunctionType;

public class bfADDF implements FunctionType {

	public bfADDF() {
	}

	public int calc(int input) {
		int a = (input >> 4) & 0xf;
		int b = input & 0xf;
		return (a + b) & 0xf;
	}

	public int getN() {
		return 8;
	}
}

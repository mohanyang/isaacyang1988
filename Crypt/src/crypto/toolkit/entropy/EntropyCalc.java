package crypto.toolkit.entropy;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.PrintWriter;

import crypto.common.BasicMethod;
import crypto.toolkit.IToolkit;

public class EntropyCalc implements IToolkit {

	String fileName = null;
	int[] count = null;
	int total;

	public EntropyCalc() {
	}

	@Override
	public String getName() {
		return "Entropy Calc";
	}

	@Override
	public void init(String[] args) throws Exception {
		fileName = BasicMethod.getParam(args, "/in:", 4);
		if (fileName == null)
			throw new Exception("Not enought parameter!");
		count = new int[256];
		total = 0;
	}

	@Override
	public void perform(PrintWriter log) throws Exception {
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(
				fileName));
		Integer tmp;
		while (true) {
			tmp = in.read();
			if (tmp == -1)
				break;
			++count[tmp & 0xff];
			total++;
		}
		in.close();
		double ans = 0, p = 0;
		int c = 0;
		for (int i = 0; i < 256; ++i) {
			if (count[i] != 0) {
				++c;
				p = (double) count[i] / total;
				ans += Math.log(p) / Math.log(2) * p;
			}
		}
		log.println("This file contains " + total + " characters in total.");
		log
				.println("This file contains "
						+ c
						+ " different characters compared to the 256 characters of the byte representation.");
		log.println("The entropy of this file is " + -ans + ".");
	}
}

package crypto.toolkit.ANF;

import java.io.PrintWriter;

import crypto.common.BasicMethod;
import crypto.math.ANF;
import crypto.toolkit.IToolkit;

public class ANFCalc implements IToolkit {
	BooleanFunctionFromFile bf = null;

	public ANFCalc() {
	}

	@Override
	public String getName() {
		return "ANF Calc";
	}

	@Override
	public void init(String[] args) throws Exception {
		String fileName = BasicMethod.getParam(args, "/in:", 4);
		if (fileName == null)
			throw new Exception("Not enought parameter!");
		bf = new BooleanFunctionFromFile(fileName);
	}

	@Override
	public void perform(PrintWriter log) throws Exception {
		for (int i = 0; i < bf.getWidth(); ++i) {
			log.println(i + "-bit ANF of the input");
			bf.setBit(i);
			new ANF(bf, log);
		}
	}
}
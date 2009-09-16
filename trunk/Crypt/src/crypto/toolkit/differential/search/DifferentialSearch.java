package crypto.toolkit.differential.search;

import java.io.PrintWriter;

import crypto.common.BasicMethod;
import crypto.math.Differential;
import crypto.toolkit.IToolkit;
import crypto.toolkit.differential.BooleanFunctionFromFile;

public class DifferentialSearch implements IToolkit {

	BooleanFunctionFromFile bf = null;
	int order = 0;

	public DifferentialSearch() {
	}

	@Override
	public String getName() {
		return "Differential Calc";
	}

	@Override
	public void init(String[] args) throws Exception {
		String tmp = BasicMethod.getParam(args, "/in:", 4);
		if (tmp == null)
			throw new Exception("File name missing!");
		bf = new BooleanFunctionFromFile(tmp);
		tmp = BasicMethod.getParam(args, "/order:", 7);
		if (tmp == null)
			throw new Exception("Test order missing!");
		order = Integer.parseInt(tmp);
	}

	@Override
	public void perform(PrintWriter log) throws Exception {
		new Differential(bf, order, log);
	}

}

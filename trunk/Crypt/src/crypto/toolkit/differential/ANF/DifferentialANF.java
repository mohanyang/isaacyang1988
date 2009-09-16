package crypto.toolkit.differential.ANF;

import java.io.PrintWriter;

import crypto.common.BasicMethod;
import crypto.math.ANF;
import crypto.toolkit.IToolkit;

public class DifferentialANF implements IToolkit {
	DifferentialFunction df = null;

	public DifferentialANF() {
	}

	@Override
	public String getName() {
		return "Differential ANF";
	}

	@Override
	public void init(String[] args) throws Exception {
		String tmp = BasicMethod.getParam(args, "/in:", 4);
		if (tmp == null)
			throw new Exception("File name missing!");
		df = new DifferentialFunction(tmp);
	}

	@Override
	public void perform(PrintWriter log) throws Exception {
		for (int i = 0; i < df.getWidth(); ++i) {
			log.println(i + "-bit ANF of the " + df.getOrder()
					+ "-order differential");
			df.setBit(i);
			new ANF(df, log);
		}
	}

	public static void main(String[] args) {
		try {
			PrintWriter log = new PrintWriter(System.out);
			DifferentialANF d = new DifferentialANF();
			d.init(new String[] { "/in:bf3.txt" });
			d.perform(log);
			log.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

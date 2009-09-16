package crypto.toolkit.differential.down;

import java.io.File;
import java.io.PrintWriter;

import crypto.attack.differential.higher.data.DifferentialFunction;
import crypto.common.BasicMethod;
import crypto.math.ANF;
import crypto.toolkit.IToolkit;

public class DifferentialDown implements IToolkit {
	DifferentialFunction df = null;

	public DifferentialDown() {
	}

	@Override
	public String getName() {
		return "Differential Down Search";
	}

	@Override
	public void init(String[] args) throws Exception {
		String tmp = BasicMethod.getParam(args, "/in:", 4);
		if (tmp == null)
			throw new Exception("File name missing!");
		FunctionFromFile bf = new FunctionFromFile(tmp);
		df = new DifferentialFunction(bf);
	}

	@Override
	public void perform(PrintWriter log) throws Exception {
		File bufferFile = new File("./config/buffer");
		PrintWriter bufferWriter = new PrintWriter(bufferFile);
		for (int bit = 0; bit < df.getWidth(); ++bit) {
			log.print(bit + "-bit search result ");
			df.setBit(bit);
			df.setOrder(0, new int[] {});
			int bound = new ANF(df, bufferWriter).getMaxOrder();
			log.println("order = " + bound);
			for (int i = 1; i != (1 << df.getN()); ++i) {
				df.setOrder(1, new int[] { i });
				int order = new ANF(df, bufferWriter).getMaxOrder();
				if (order < bound - 1)
					log.println(BasicMethod.toBitString(i, df.getN()) + " "
							+ order);
			}
		}
		bufferWriter.flush();
		bufferWriter.close();
		bufferFile.delete();
	}

	public static void main(String[] args) {
		DifferentialDown dd = new DifferentialDown();
		try {
			dd.init(new String[] { "/in:bf1.txt" });
			PrintWriter log = new PrintWriter(System.out);
			dd.perform(log);
			log.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

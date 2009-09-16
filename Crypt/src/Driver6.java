import java.io.PrintStream;

import crypto.attack.differential.higher.*;

public class Driver6 {

	public static void main(String[] args) {
		try {
			PrintStream out = new PrintStream("report.txt");
			for (int i = 0; i < 64; ++i) {
				long start = System.currentTimeMillis();
				out.println("Result of " + i);
				// new OrderTest(new bf_DES(i), out);
				// new OrderTest(new bf_CAST5_Standard(i), out);
				new OrderTest(new bf_IDEA(i), out);
				// new OrderTest(new bf_AES(i), out);
				long end = System.currentTimeMillis();
				out.println("Total running time is " + (end - start) + "ms!");
				out.flush();
			}
			out.close();
		} catch (Exception e) {
		}
	}
}

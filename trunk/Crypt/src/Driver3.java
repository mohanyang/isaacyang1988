import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Driver3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// for (int i = 1; i <= 8; ++i) {
		// System.out.println(i);
		// new BooleanFunctionNormalForm(new crypto.attack.bfDES(i, 0),
		// System.out);
		// }

		// for (int j = 0; j < 8; ++j)
		// for (int i = 1; i <= 8; ++i) {
		// System.out.println(j + " " + i);
		// new BooleanFunctionNormalForm(new CAST5(j, i), System.out);
		// }

		// for (int j = 0; j < 8; ++j)
		// for (int i = 1; i <= 8; ++i) {
		// System.out.println(j + " " + i);
		// new BooleanFunctionNormalForm(new DES(j, i), System.out);
		// }
		// for (int i = 1; i <= 8; ++i)
		// for (int j = 1; j <= 32; ++j) {
		// System.out.println(i + " " + j);
		// new BooleanFunctionNormalForm(new crypto.attack.bfDES(i, j),
		// System.out);
		// System.out.println();
		// }
		try {
			PrintStream out = new PrintStream("report.txt");
			for (int j = 0; j < 8; ++j)
				for (int i = 0; i < 8; ++i) {
					System.out.println(j + " " + i);
					out.println(j + " " + i);
					new crypto.attack.differential.higher.OrderTest(
							new crypto.attack.differential.higher.bf_CAST5(j, i),
							out);
				}
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

import crypto.attack.Differential;

public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// new Differential(new crypto.attack.bf5_y3(),
		// Integer.valueOf(args[0]));
		// new crypto.attack.ExtractPattern();

		// new Differential(new crypto.attack.bfDES(Integer.valueOf(args[1]),
		// Integer.valueOf(args[2])), Integer.valueOf(args[0]));
		new Differential(new crypto.attack.test.CAST5(Integer.valueOf(args[1]),
				Integer.valueOf(args[2])), Integer.valueOf(args[0]));
		// new Differential(new crypto.attack.bfCAST5(1, 0), Integer
		// .valueOf(args[0]));
	}
}

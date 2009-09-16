package crypto.execution.cipher;

import java.io.FileWriter;
import java.io.PrintWriter;

import crypto.common.Config;
import crypto.common.Lib;

public class CipherExecutionDriver extends crypto.util.DriverUtil {
	private ICipherExecution exe = null;

	public int getTotal() {
		return exe.getTotal();
	}

	public int getCur() {
		return exe.getCur();
	}

	public static String[] algorithms = null;

	public int getAlgorithmID(String name) {
		for (int i = 0; i < algorithms.length; ++i)
			if (algorithms[i].equals(name))
				return i;
		return -1;
	}

	public String getParam(String[] args, String prefix, int offSet) {
		String ret = null;
		for (int i = 0; i < args.length; ++i)
			if (args[i].startsWith(prefix)) {
				ret = args[i];
				ret = ret.substring(ret.indexOf(prefix) + offSet);
				return ret;
			}
		return ret;
	}

	Object[] params = null;
	private boolean encrypt;
	private String infile, outfile, keyfile, ivfile;
	private int k, mode;

	public CipherExecutionDriver(String[] args) throws Exception {
		encrypt = true;
		infile = null;
		outfile = null;
		keyfile = "key.txt";
		ivfile = null;
		k = 10;
		mode = 0;
		String tmp = null;

		tmp = getParam(args, "/algo:", 6);
		if (tmp != null) {
			k = getAlgorithmID(tmp);
		}
		if (k == -1)
			throw new Exception("Unknown cipher algorithm!");
		tmp = getParam(args, "/in:", 4);
		if (tmp != null) {
			infile = tmp;
		}
		tmp = getParam(args, "/out:", 5);
		if (tmp != null) {
			outfile = tmp;
		}
		tmp = getParam(args, "/key:", 5);
		if (tmp != null) {
			keyfile = tmp;
		}
		tmp = getParam(args, "/iv:", 4);
		if (tmp != null) {
			ivfile = tmp;
		}
		try {
			tmp = getParam(args, "/enc:", 5);
			if (tmp == null) {
				encrypt = true;
			} else {
				if (Integer.valueOf(tmp) == 1) {
					encrypt = false;
				} else {
					encrypt = true;
				}
			}
			tmp = getParam(args, "/mode:", 6);
			if (tmp == null) {
				mode = 0;
			} else {
				mode = Integer.valueOf(tmp);
			}
		} catch (Exception e) {
		}
		params = new Object[7];
		params[0] = algorithms[k];
		params[1] = infile;
		params[2] = outfile;
		params[3] = keyfile;
		params[4] = ivfile;
		params[5] = encrypt;
		params[6] = mode;
	}

	public void run() {
		try {
			final PrintWriter Log = new PrintWriter(
					new FileWriter("report.txt"));
			try {
				Config.load("./config/cipher.ini");
				exe = (ICipherExecution) Lib.constructObject(Config
						.getString(algorithms[k]));
				exe.init(params);

				System.out.println("Initialization finished!");
				Log.println("Initialization finished!");
				if (exe instanceof CipherExecution) {
					Thread executeProcess = new Thread() {
						public void run() {
							try {
								exe.perform(Log);
								Thread.sleep(10);
							} catch (Exception e) {
								System.out.println(exe.getName()
										+ " execution failed!");
								System.out.println(e.toString());
								Log.println(exe.getName()
										+ " execution failed!");
								Log.println(e.toString());
								Log.close();
							}
						}
					};
					executeProcess.start();
				} else {
					exe.perform(Log);
					Log.flush();
					Log.close();
				}
			} catch (Exception e) {
				System.out.println(exe.getName() + " execution failed!");
				System.out.println(e.toString());
				Log.println(exe.getName() + " execution failed!");
				Log.println(e.toString());
				Log.close();
				System.exit(1);
			}
		} catch (Exception e) {
		}
	}
}
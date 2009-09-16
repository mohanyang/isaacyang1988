package crypto.execution.digest;

import java.io.FileWriter;
import java.io.PrintWriter;

import crypto.common.Config;
import crypto.common.Lib;
import crypto.execution.IExecution;

public class DigestExecutionDriver {
	private IExecution exe = null;
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
	private String infile, outfile;
	private int k;

	public DigestExecutionDriver(String[] args) throws Exception {
		infile = null;
		outfile = null;
		k = 10;
		String tmp = null;

		tmp = getParam(args, "/algo:", 6);
		if (tmp != null) {
			k = getAlgorithmID(tmp);
		}
		tmp = getParam(args, "/in:", 4);
		if (tmp != null) {
			infile = tmp;
		}
		tmp = getParam(args, "/out:", 5);
		if (tmp != null) {
			outfile = tmp;
		}
		params = new Object[3];
		params[0] = algorithms[k];
		params[1] = infile;
		params[2] = outfile;
	}

	public void run() {
		try {
			PrintWriter Log = new PrintWriter(new FileWriter("report.txt"));
			try {
				Config.load("./config/digest.ini");
				exe = (IExecution) Lib.constructObject(Config
						.getString(algorithms[k]));
				exe.init(params);
				if (exe == null)
					throw new Exception("Do not support algorithm "
							+ algorithms[k]);
				System.out.println("Initialization finished!");
				Log.println("Initialization finished!");
				exe.perform(null);
				Log.println(exe.getName() + " execution finished!");
				Log.println("Digest of input file " + infile);
				Log.println(crypto.common.BasicMethod.readFile(outfile));
				Log.flush();
				Log.close();
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

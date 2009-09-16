package crypto.toolkit;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import crypto.common.BasicMethod;
import crypto.common.Config;
import crypto.common.Lib;

public class ToolkitDriver extends crypto.util.DriverUtil {

	public static String[] algorithms = null;
	private IToolkit exe = null;
	int k = 0;
	String[] args = null;

	public int getAlgorithmID(String name) {
		for (int i = 0; i < algorithms.length; ++i)
			if (algorithms[i].equals(name))
				return i;
		return -1;
	}

	public ToolkitDriver(String[] args) throws Exception {
		this.args = args;
		String tmp = BasicMethod.getParam(args, "/toolkit:", 9);
		if (tmp != null) {
			k = getAlgorithmID(tmp);
		}
		if (k == -1)
			throw new Exception("Unknown toolkit!");
	}

	@Override
	public int getCur() {
		return 0;
	}

	@Override
	public int getTotal() {
		return 1;
	}

	@Override
	public void run() {
		try {
			final PrintWriter Log = new PrintWriter(
					new FileWriter("report.txt"));
			try {
				Config.load("./config/toolkit.ini");
				exe = (IToolkit) Lib.constructObject(Config
						.getString(algorithms[k]));
				exe.init(args);
				if (exe == null)
					throw new Exception("Do not support toolkit "
							+ algorithms[k]);

				System.out.println("Initialization finished!");
				Log.println("Initialization finished!");

				exe.perform(Log);
				System.out.println(exe.getName() + " finished!");
				Log.flush();
				Log.close();
			} catch (Exception e) {
				System.out.println(exe.getName() + " execution failed!");
				System.out.println(e.toString());
				Log.println(exe.getName() + " execution failed!");
				Log.println(e.toString());
				Log.flush();
				Log.close();
				System.exit(1);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

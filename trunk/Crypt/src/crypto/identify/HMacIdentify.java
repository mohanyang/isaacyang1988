package crypto.identify;

import java.io.PrintWriter;

import crypto.common.Config;
import crypto.common.Lib;
import crypto.execution.IExecution;

public class HMacIdentify implements IIdentify {
	private boolean isSucceed = false;
	private String name = null;

	public HMacIdentify(String name, String infile, String outfile,
			String keyfile, PrintWriter log) {
		try {
			this.name = name;
			Object[] params = new Object[4];
			params[0] = name;
			params[1] = infile;
			params[2] = tmpFile;
			params[3] = keyfile;
			crypto.common.BasicMethod.writeFile(tmpFile, "flushout");
			Config.load("./config/HMac.ini");
			IExecution ie = (IExecution) Lib.constructObject(Config
					.getString(name));
			ie.init(params);
			ie.perform(log);
			isSucceed = crypto.common.BasicMethod.fileCompare(outfile, tmpFile);
		} catch (Exception e) {
			isSucceed = false;
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isSucceed() {
		return isSucceed;
	}
}

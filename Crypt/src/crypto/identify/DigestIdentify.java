package crypto.identify;

import java.io.PrintWriter;

import crypto.common.BasicMethod;
import crypto.common.Config;
import crypto.common.Lib;
import crypto.execution.IExecution;

public class DigestIdentify implements IIdentify {
	private boolean isSucceed = false;
	private String name = null;

	public DigestIdentify(String name, String infile, String outfile,
			PrintWriter log) {
		try {
			this.name = name;
			Object[] params = new Object[3];
			params[0] = name;
			params[1] = infile;
			params[2] = tmpFile;
			BasicMethod.writeFile(tmpFile, "flushout");
			Config.load("./config/digest.ini");
			IExecution ie = (IExecution) Lib.constructObject(Config
					.getString(name));
			ie.init(params);
			ie.perform(log);
			isSucceed = BasicMethod.fileCompare(outfile, tmpFile);
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

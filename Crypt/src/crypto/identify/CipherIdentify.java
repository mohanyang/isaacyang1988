package crypto.identify;

import java.io.PrintWriter;

import crypto.common.Config;
import crypto.common.Lib;
import crypto.execution.cipher.ICipherExecution;

public class CipherIdentify implements IIdentify {
	private boolean isSucceed = false;
	private String name = null;

	public CipherIdentify(String name, String infile, String outfile,
			String keyfile, PrintWriter log) {
		try {
			this.name = name;
			Object[] params = new Object[7];
			params[0] = name;
			params[1] = infile;
			params[2] = tmpFile;
			params[3] = keyfile;
			params[4] = null;
			params[5] = true;
			params[6] = 0;
			crypto.common.BasicMethod.writeFile(tmpFile, "flushout");
			Config.load("./config/cipher.ini");
			ICipherExecution ice = (ICipherExecution) Lib
					.constructObject(Config.getString(name));
			ice.init(params);
			ice.perform(log);
			System.out.println(Thread.activeCount());
			Thread.sleep(100);
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

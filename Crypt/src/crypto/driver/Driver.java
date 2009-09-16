package crypto.driver;

import java.util.Date;

import crypto.common.Config;
import crypto.common.Lib;
import crypto.execution.HMac.HMacExecutionDriver;
import crypto.execution.cipher.CipherExecutionDriver;
import crypto.execution.digest.DigestExecutionDriver;
import crypto.toolkit.ToolkitDriver;
import crypto.ui.ArgManager;

public class Driver {

	public static void main(String[] args) {
		try {
			Lib.seedRandom(new Date().getTime());
			Config.load("./config/system.ini");
			ArgManager.init();
			int mode = Config.getInteger("Mode");
			CipherExecutionDriver.algorithms = crypto.common.BasicMethod
					.loadAlgorithm("./config/cipher.ini");
			DigestExecutionDriver.algorithms = crypto.common.BasicMethod
					.loadAlgorithm("./config/digest.ini");
			HMacExecutionDriver.algorithms = crypto.common.BasicMethod
					.loadAlgorithm("./config/HMac.ini");
			ToolkitDriver.algorithms = crypto.common.BasicMethod
					.loadAlgorithm("./config/toolkit.ini");
			if (mode == 0) {
				new crypto.driver.CommandDriver(args);
			} else {
				new crypto.ui.FunctionSelection();
			}
		} catch (Exception e) {
		}
	}
}

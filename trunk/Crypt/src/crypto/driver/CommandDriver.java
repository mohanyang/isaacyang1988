package crypto.driver;

public class CommandDriver {

	public static String getParam(String[] args, String prefix, int offSet) {
		String ret = null;
		for (int i = 0; i < args.length; ++i)
			if (args[i].startsWith(prefix)) {
				ret = args[i];
				ret = ret.substring(ret.indexOf(prefix) + offSet);
				return ret;
			}
		return ret;
	}

	public CommandDriver(String[] args) {
		try {
			String type = "Cipher";
			String tmp = getParam(args, "/oper:", 6);
			if (tmp != null) {
				type = tmp;
			}
			if (type.equals("Identify")) {
				new crypto.identify.IdentifyDriver(args).run();
			} else if (type.equals("Cipher")) {
				new crypto.execution.cipher.CipherExecutionDriver(args).run();
			} else if (type.equals("Digest")) {
				if (getParam(args, "/key:", 5) == null) {
					new crypto.execution.digest.DigestExecutionDriver(args)
							.run();
				} else {
					new crypto.execution.HMac.HMacExecutionDriver(args).run();
				}
			} else if (type.equals("Test")) {
			}
		} catch (Exception e) {
		}
	}

	public static void main(String[] args) {
		new CommandDriver(args);
	}
}

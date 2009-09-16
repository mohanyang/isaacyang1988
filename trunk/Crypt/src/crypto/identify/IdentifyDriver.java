package crypto.identify;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import crypto.common.BasicMethod;
import crypto.execution.HMac.HMacExecutionDriver;
import crypto.execution.cipher.CipherExecutionDriver;
import crypto.execution.digest.DigestExecutionDriver;

public class IdentifyDriver extends crypto.util.DriverUtil {
	private int current = 0;
	private int total = 0;
	private File logFileName = null;
	private PrintWriter logFile = null;

	private static String infile = "./config/in.tmp",
			outfile = "./config/out.tmp";

	public String getParam(String[] args, String prefix, int offSet) {
		String ret = null;
		for (int i = 0; i < args.length; ++i)
			if (args[i].startsWith(prefix)) {
				ret = args[i];
				ret = ret.substring(ret.indexOf(prefix) + offSet);
			}
		return ret;
	}

	private String inFile = "in.txt", outFile = "out.txt", keyFile = "key.txt";

	public IdentifyDriver(String[] args) throws Exception {
		total = CipherExecutionDriver.algorithms.length
				+ DigestExecutionDriver.algorithms.length
				+ HMacExecutionDriver.algorithms.length;
		String tmp = null;
		tmp = getParam(args, "/in:", 4);
		if (tmp != null) {
			inFile = tmp;
		}
		tmp = getParam(args, "/out:", 5);
		if (tmp != null) {
			outFile = tmp;
		}
		tmp = getParam(args, "/key:", 5);
		if (tmp != null) {
			keyFile = tmp;
		}
		try {
			logFileName = new File("./config/log.txt");
			logFile = new PrintWriter(logFileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			boolean flag = false;
			PrintWriter Log = new PrintWriter(new FileWriter("report.txt"));
			BasicMethod.hexToBinFile(inFile, infile);
			BasicMethod.hexToBinFile(outFile, outfile);
			for (int i = 0; i < CipherExecutionDriver.algorithms.length; ++i) {
				CipherIdentify ci = new CipherIdentify(
						CipherExecutionDriver.algorithms[i], infile, outfile,
						keyFile, logFile);
				if (ci.isSucceed()) {
					flag = true;
					System.out.println(ci.getName() + " cipher");
					Log.println(ci.getName() + " cipher");
				}
				++current;
			}
			for (int i = 0; i < DigestExecutionDriver.algorithms.length; ++i) {
				DigestIdentify di = new DigestIdentify(
						DigestExecutionDriver.algorithms[i], inFile, outFile,
						logFile);
				if (di.isSucceed()) {
					flag = true;
					System.out.println(di.getName() + " digest");
					Log.println(di.getName() + " digest");
				}
				++current;
			}
			for (int i = 0; i < HMacExecutionDriver.algorithms.length; ++i) {
				HMacIdentify hi = new HMacIdentify(
						HMacExecutionDriver.algorithms[i], inFile, outFile,
						keyFile, logFile);
				if (hi.isSucceed()) {
					flag = true;
					System.out.println(hi.getName() + " HMac");
					Log.println(hi.getName() + " HMac");
				}
				++current;
			}
			new File(infile).delete();
			new File(outfile).delete();
			new File(IIdentify.tmpFile).delete();
			logFile.close();
			logFileName.delete();
			if (!flag) {
				System.out
						.println("This package can not recognize this algorithm!");
				Log.println("This package can not recognize this algorithm!");
			}
			Log.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getCur() {
		return current;
	}

	@Override
	public int getTotal() {
		return total;
	}
}

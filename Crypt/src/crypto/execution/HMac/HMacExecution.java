package crypto.execution.HMac;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Hex;

import crypto.common.Config;
import crypto.execution.IExecution;

public class HMacExecution implements IExecution {
	private Digest digest;
	private String input;
	private String key;
	private OutputStream out = null;

	public HMacExecution() {
	}

	@Override
	public void init(Object[] params) throws Exception {
		String name, infile, outfile, keyfile;
		try {
			name = (String) params[0];
			infile = (String) params[1];
			outfile = (String) params[2];
			keyfile = (String) params[3];
		} catch (Exception e) {
			throw new Exception("Illegal parameters!");
		}
		Config.load("./config/engine.ini");
		digest = (Digest) crypto.common.Lib.constructObject(Config
				.getString(name));
		if (digest == null)
			throw new Exception("Do not support algorithm " + name);
		input = crypto.common.BasicMethod.readFile(infile);
		out = new FileOutputStream(outfile);
		key = crypto.common.BasicMethod.readFile(keyfile);
	}

	public String getName() {
		return digest.getAlgorithmName() + " Digest";
	}

	public void perform(PrintWriter log) throws Exception {
		HMac hmac = new HMac(digest);
		byte[] resBuf = new byte[hmac.getMacSize()];
		byte[] m = input.getBytes();
		if (input.startsWith("0x")) {
			m = Hex.decode(input.substring(2));
		}
		hmac.init(new KeyParameter(Hex.decode(key)));
		hmac.update(m, 0, m.length);
		hmac.doFinal(resBuf, 0);

		try {
			Hex.encode(resBuf, out);
			out.flush();
			out.close();
		} catch (Exception e) {
		}
	}
}

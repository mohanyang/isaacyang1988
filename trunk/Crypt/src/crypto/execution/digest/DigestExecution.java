package crypto.execution.digest;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.util.encoders.Hex;

import crypto.common.Config;
import crypto.execution.IExecution;

public class DigestExecution implements IExecution {
	private Digest digest;
	private String input;
	private OutputStream out = null;

	public DigestExecution() {
	}

	public void init(Object[] params) throws Exception {
		String name, infile, outfile;
		try {
			name = (String) params[0];
			infile = (String) params[1];
			outfile = (String) params[2];
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
	}

	public String getName() {
		return digest.getAlgorithmName() + " Digest";
	}

	public void perform(PrintWriter log) throws Exception {
		byte[] resBuf = new byte[digest.getDigestSize()];
		byte[] m = toByteArray(input);
		digest.update(m, 0, m.length);
		digest.doFinal(resBuf, 0);

		try {
			Hex.encode(resBuf, out);
			out.flush();
			out.close();
		} catch (Exception e) {
		}
	}

	private byte[] toByteArray(String input) {
		byte[] bytes = new byte[input.length()];
		for (int i = 0; i != bytes.length; i++) {
			bytes[i] = (byte) input.charAt(i);
		}
		return bytes;
	}
}

package crypto.execution.cipher;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.x509.X509CertificateStructure;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.params.RSAKeyParameters;

public class RSAExecution implements ICipherExecution {

	private RSAEngine engine = new RSAEngine();
	private RSAKeyParameters param;
	private int cur, total;

	private String outfile = null;
	private byte[] data = null;
	private BigInteger mod = null;
	private BigInteger pubExp = null;

	public RSAExecution() {
	}

	@Override
	public void init(Object[] params) throws Exception {
		String infile, outfile, keyfile;
		try {
			infile = (String) params[1];
			outfile = (String) params[2];
			keyfile = (String) params[3];
		} catch (Exception e) {
			throw new Exception("Illegal parameters!");
		}
		try {
			BufferedInputStream in = new BufferedInputStream(
					new FileInputStream(keyfile));
			ASN1InputStream aIn;
			aIn = new ASN1InputStream(in);
			ASN1Sequence seq = (ASN1Sequence) aIn.readObject();
			X509CertificateStructure obj = new X509CertificateStructure(seq);
			String s = obj.getSubjectPublicKeyInfo().getPublicKey().toString();
			s = s.substring(1, s.length() - 1);
			pubExp = new BigInteger(s.substring(s.indexOf(",") + 2), 10);
			mod = new BigInteger(s.substring(0, s.indexOf(",")), 10);
			param = new RSAKeyParameters(false, mod, pubExp);
			in.close();
		} catch (IOException ioe) {
			throw new Exception("Decryption key file not found, "
					+ "or not valid [" + keyfile + "]");
		}
		data = crypto.common.BasicMethod.readBinFile(infile);
		this.outfile = outfile;
	}

	public void perform(PrintWriter log) throws Exception {
		long start = System.currentTimeMillis();
		engine.init(true, param);
		// System.out.println(new String(Hex.encode(data)));
		data = engine.processBlock(data, 0, data.length);
		crypto.common.BasicMethod.writeBinFile(outfile, data);
		// System.out.println(new String(Hex.encode(data)));
		long end = System.currentTimeMillis();
		System.out.println("Execution time£º" + (end - start)
				+ "ms\nRSA execution finished!");
		log.println("Execution time£º" + (end - start)
				+ "ms\nRSA execution finished!");
	}

	public int getCur() {
		return cur;
	}

	public String getName() {
		return "RSA";
	}

	public int getTotal() {
		return total;
	}

	public void performDecrypt() {
	}

	public void performEncrypt() {
	}
}

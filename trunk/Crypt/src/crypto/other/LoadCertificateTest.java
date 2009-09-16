package crypto.other;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigInteger;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.x509.X509CertificateStructure;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.util.ExecutionAsymmetric;
import org.bouncycastle.util.encoders.Hex;

import crypto.common.BasicMethod;

public class LoadCertificateTest {
	static private BufferedInputStream in = null;

	public void test() throws Exception {
		String infile = "D://Document//Document//ACM°à//Crypt//EC2007//certificate-Lai.cer";
		// String infile = "D://Document//Document//ACM°à//Crypt//EC2007//d.cer";
		String edgeInput = "ff6f77206973207468652074696d6520666f7220616c6c20676f6f64206d656e";
		byte[] data = Hex.decode(edgeInput);
		try {
			in = new BufferedInputStream(new FileInputStream(infile));
		} catch (FileNotFoundException fnf) {
			BasicMethod.error("Input file not found [" + infile + "]");
			System.exit(1);
		}

		ASN1InputStream aIn;
		aIn = new ASN1InputStream(in);
		ASN1Sequence seq = (ASN1Sequence) aIn.readObject();
		X509CertificateStructure obj = new X509CertificateStructure(seq);

		System.out.println(obj.getIssuer());
		System.out.println(obj.getVersion());
		System.out.println(obj.getSerialNumber());
		System.out.println(obj.getSignatureAlgorithm().getObjectId());
		System.out.println(obj.getSubjectPublicKeyInfo().getAlgorithmId()
				.getObjectId());
		System.out.println(obj.getSubjectPublicKeyInfo().getPublicKey());
		System.out.println(obj.getSubjectPublicKeyInfo().getPublicKeyData());
		System.out.println(obj.getSubjectPublicKeyInfo().getPublicKeyData()
				.getBytes().length);
		System.out.println(obj.getSubject());

		String s = obj.getSubjectPublicKeyInfo().getPublicKey().toString();
		s = s.substring(1, s.length() - 1);
		BigInteger pubExp = new BigInteger(s.substring(s.indexOf(",") + 2), 10);
		System.out.println(pubExp);
		BigInteger mod = new BigInteger(s.substring(0, s.indexOf(",")), 10);
		System.out.println(mod.bitLength());

		AsymmetricBlockCipher eng = new RSAEngine();
		RSAKeyParameters pubParameters = new RSAKeyParameters(false, mod,
				pubExp);
		ExecutionAsymmetric rsa = new RSAExecution();
		rsa.init(eng, pubParameters, true);
		System.out.println(new String(Hex.encode(data)));
		System.out.println(new String(Hex.encode(rsa.executeProcess(data))));
	}

	static public void main(String[] args) {
		try {
			new LoadCertificateTest().test();
		} catch (Exception e) {
			BasicMethod.error("Final error " + e.toString());
		}
	}
}

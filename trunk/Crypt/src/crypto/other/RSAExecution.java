package crypto.other;

import org.bouncycastle.util.ExecutionAsymmetric;
import org.bouncycastle.crypto.engines.*;
import org.bouncycastle.crypto.params.*;

public class RSAExecution extends ExecutionAsymmetric {

	public String getName() {
		return "RSA alrorithm";
	}

	public byte[] performEncrypt(byte[] data) throws Exception {
		if(eng instanceof RSAEngine)
		{
			eng = (RSAEngine)eng;
			param = (RSAKeyParameters)param;
		}
		else if(eng instanceof ElGamalEngine)
		{
			eng = (ElGamalEngine)eng;
			param = (ElGamalPublicKeyParameters)param;
		}else return data;
		eng.init(true, param);
		return eng.processBlock(data, 0, data.length);
	}

	public byte[] performDecrypt(byte[] data) throws Exception {
		if(eng instanceof RSAEngine)
		{
			eng = (RSAEngine)eng;
			param = (RSAPrivateCrtKeyParameters)param;
		}
		else if(eng instanceof ElGamalEngine)
		{
			eng = (ElGamalEngine)eng;
			param = (ElGamalPrivateKeyParameters)param;
		}else return data;
		eng.init(false, param);
		return eng.processBlock(data, 0, data.length);		
	}
}

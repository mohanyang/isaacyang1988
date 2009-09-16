package org.bouncycastle.util;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;

public abstract class Execution {
	private BlockCipher eng;
	private CipherParameters param;
	private boolean isEncrypt;
	
	public abstract String getName();
	public abstract byte[] performEncrypt(byte[] data);
	public abstract byte[] performDecrypt(byte[] data);
	
	public byte[] executeProcess(byte[] data)
	{
		if(isEncrypt) return performEncrypt(data);
		else return performDecrypt(data);
	}
	public void init(BlockCipher _eng, CipherParameters _param, boolean _isEncrypt)
	{
		setEngine(_eng);
		setParam(_param);
		setIsEncrypt(_isEncrypt);
	}
	public void setEngine(BlockCipher _eng)
	{
		eng = _eng;
	}
	public BlockCipher getEngine()
	{
		return eng;
	}
	public CipherParameters getParam()
	{
		return param;
	}
	public void setParam(CipherParameters _param)
	{
		param = _param;
	}
	public boolean getIsEncrypt()
	{
		return isEncrypt;
	}
	public void setIsEncrypt(boolean _isEncrypt)
	{
		isEncrypt = _isEncrypt;
	}
}

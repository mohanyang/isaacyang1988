package org.bouncycastle.util;

import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;

import crypto.common.BasicMethod;

public abstract class ExecutionAsymmetric {
	protected AsymmetricBlockCipher eng;
	protected CipherParameters param;
	protected boolean isEncrypt;
	
	public abstract String getName();
	public abstract byte[] performEncrypt(byte[] data) throws Exception;
	public abstract byte[] performDecrypt(byte[] data) throws Exception;
	
	public byte[] executeProcess(byte[] data)
	{
		try{
			if(isEncrypt) return performEncrypt(data);
			else return performDecrypt(data);	
		}
		catch(Exception e)
		{
			BasicMethod.error(e.toString());
			return data;
		}
	}
	public void init(AsymmetricBlockCipher _eng, CipherParameters _param, boolean _isEncrypt)
	{
		setEngine(_eng);
		setParam(_param);
		setIsEncrypt(_isEncrypt);
	}
	public void setEngine(AsymmetricBlockCipher _eng)
	{
		eng = _eng;
	}
	public AsymmetricBlockCipher getEngine()
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

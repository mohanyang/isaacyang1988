/**
 * 
 */
package org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;

/**
 * @author Isaac
 * @literal implements ECB mode on top of a simple cipher.
 */
public class ECBBlockCipher implements BlockCipher {
	private int blockSize;
	private BlockCipher cipher = null;
	private boolean encrypting;

	/**
	 * 
	 */
	public ECBBlockCipher(BlockCipher cipher) {
		// TODO Auto-generated constructor stub
		this.cipher = cipher;
		this.blockSize = cipher.getBlockSize();
	}

	/**
	 * return the underlying block cipher that we are wrapping.
	 * 
	 * @return the underlying block cipher that we are wrapping.
	 */
	public BlockCipher getUnderlyingCipher() {
		return cipher;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bouncycastle.crypto.BlockCipher#getAlgorithmName()
	 */
	public String getAlgorithmName() {
		// TODO Auto-generated method stub
		return cipher.getAlgorithmName() + "/ECB";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bouncycastle.crypto.BlockCipher#getBlockSize()
	 */
	public int getBlockSize() {
		// TODO Auto-generated method stub
		return cipher.getBlockSize();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bouncycastle.crypto.BlockCipher#init(boolean,
	 *      org.bouncycastle.crypto.CipherParameters)
	 */
	public void init(boolean forEncryption, CipherParameters params)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		encrypting = forEncryption;
		reset();
		cipher.init(encrypting, params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bouncycastle.crypto.BlockCipher#processBlock(byte[], int,
	 *      byte[], int)
	 */
	public int processBlock(byte[] in, int inOff, byte[] out, int outOff)
			throws DataLengthException, IllegalStateException {
		// TODO Auto-generated method stub
		return (encrypting) ? encryptBlock(in, inOff, out, outOff)
				: decryptBlock(in, inOff, out, outOff);
	}

	private int encryptBlock(byte[] in, int inOff, byte[] out, int outOff)
			throws DataLengthException, IllegalStateException {
		if ((inOff + blockSize) > in.length) {
			throw new DataLengthException("input buffer too short");
		}
		return cipher.processBlock(in, inOff, out, outOff);
	}

	private int decryptBlock(byte[] in, int inOff, byte[] out, int outOff)
			throws DataLengthException, IllegalStateException {
		if ((inOff + blockSize) > in.length) {
			throw new DataLengthException("input buffer too short");
		}
		return cipher.processBlock(in, inOff, out, outOff);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bouncycastle.crypto.BlockCipher#reset()
	 */
	public void reset() {
		// TODO Auto-generated method stub
		cipher.reset();
	}

}

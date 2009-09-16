package crypto.execution.cipher;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.modes.CFBBlockCipher;
import org.bouncycastle.crypto.modes.ECBBlockCipher;
import org.bouncycastle.crypto.modes.OFBBlockCipher;
import org.bouncycastle.crypto.modes.SICBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.encoders.Hex;

import crypto.common.Config;

public class CipherExecution implements ICipherExecution {
	public static String[] mode = { "ECB", "CBC", "CFB1", "CFB8", "CFB64",
			"OFB1", "OFB8", "OFB64", "CTR" };
	protected PaddedBufferedBlockCipher cipher;
	private CipherParameters param;
	private boolean isEncrypt;
	protected int inBlockSize;
	protected int outBlockSize;
	private int cur, total;

	private String name = null;
	private byte[] key = null;
	private byte[] iv = null;

	// The input stream of bytes to be processed for encryption
	private BufferedInputStream in = null;

	// The output stream of bytes to be procssed
	private BufferedOutputStream out = null;

	public CipherExecution() {
	}

	public void init(Object[] params) throws Exception {
		if (params.length < 6)
			throw new Exception("Parameter too short!");
		String infile, outfile, keyfile, ivfile;
		boolean encrypt;
		int mode;
		try {
			name = (String) params[0];
			infile = (String) params[1];
			outfile = (String) params[2];
			keyfile = (String) params[3];
			if (params[4] != null)
				ivfile = (String) params[4];
			else
				ivfile = null;
			encrypt = params[5].toString().equals("true") ? true : false;
			mode = Integer.parseInt(params[6].toString());
		} catch (Exception e) {
			throw new Exception("Illegal parameters!");
		}

		Config.load("./config/engine.ini");
		BlockCipher beng = (BlockCipher) crypto.common.Lib
				.constructObject(Config.getString(name));
		if (beng == null)
			throw new Exception("Unknown engine!");

		try {
			BufferedInputStream keystream = new BufferedInputStream(
					new FileInputStream(keyfile));
			int len = keystream.available();
			byte[] keyhex = new byte[len];
			keystream.read(keyhex, 0, len);
			key = Hex.decode(keyhex);
		} catch (IOException ioe) {
			throw new Exception("Decryption key file not found, "
					+ "or not valid [" + keyfile + "]");
		}
		if (ivfile != null) {
			try {
				BufferedInputStream ivstream = new BufferedInputStream(
						new FileInputStream(ivfile));
				int len = ivstream.available();
				byte[] ivhex = new byte[len];
				ivstream.read(ivhex, 0, len);
				iv = Hex.decode(ivhex);
			} catch (IOException ioe) {
				throw new Exception("Decryption IV file not found, "
						+ "or not valid [" + ivfile + "]");
			}
		}

		if (ivfile == null) {
			init(beng, new KeyParameter(key), encrypt, infile, outfile, mode);
		} else {
			init(beng, new ParametersWithIV(new KeyParameter(key), iv),
					encrypt, infile, outfile, mode);
		}
		inBlockSize = 64;
		outBlockSize = cipher.getOutputSize(inBlockSize);
	}

	public String getName() {
		return name;
	}

	public int getCur() {
		return cur;
	}

	public int getTotal() {
		return total;
	}

	public void performEncrypt() {
		cipher.init(true, this.getParam());
		byte[] inblock = new byte[inBlockSize];
		byte[] outblock = new byte[outBlockSize];

		/*
		 * now, read the file, and output the chunks
		 */
		try {
			int inL;
			int outL;
			while ((inL = in.read(inblock, 0, inBlockSize)) > 0) {
				outL = cipher.processBytes(inblock, 0, inL, outblock, 0);
				/*
				 * Before we write anything out, we need to make sure that we've
				 * got something to write out.
				 */
				if (outL > 0) {
					// System.out.println(1 + " outL " + outL);
					out.write(outblock, 0, outL);
					cur += inBlockSize;
				}
			}

			try {
				/*
				 * Now, process the bytes that are still buffered within the
				 * cipher.
				 */
				outL = cipher.doFinal(outblock, 0);
				if (outL > 0) {
					// System.out.println(2 + " outL " + outL);
					out.write(outblock, 0, outL);
					cur = total;
				}
			} catch (CryptoException ce) {

			}
		} catch (IOException ioeread) {
			ioeread.printStackTrace();
		}
	}

	public void performDecrypt() {
		cipher.init(false, this.getParam());
		byte[] inblock = new byte[outBlockSize];
		byte[] outblock = new byte[inBlockSize * 2];

		/*
		 * now, read the file, and output the chunks
		 */
		try {
			int inL;
			int outL;
			while ((inL = in.read(inblock, 0, outBlockSize)) > 0) {
				outL = cipher.processBytes(inblock, 0, inL, outblock, 0);
				/*
				 * Before we write anything out, we need to make sure that we've
				 * got something to write out.
				 */
				if (outL > 0) {
					out.write(outblock, 0, outL);
					cur += outBlockSize;
				}
			}

			try {
				/*
				 * Now, process the bytes that are still buffered within the
				 * cipher.
				 */
				outL = cipher.doFinal(outblock, 0);
				if (outL > 0) {
					out.write(outblock, 0, outL);
					cur = total;
				}
			} catch (CryptoException ce) {

			}
		} catch (IOException ioeread) {
			ioeread.printStackTrace();
		}
	}

	private long start, end;

	public void perform(PrintWriter log) throws Exception {
		start = System.currentTimeMillis();
		end = System.currentTimeMillis();
		final PrintWriter Log = log;
		final String exeName = name;
		Thread executeProcess = new Thread() {
			public void run() {
				try {
					if (isEncrypt) {
						performEncrypt();
					} else {
						performDecrypt();
					}

					in.close();
					out.flush();
					out.close();

					end = System.currentTimeMillis();
					System.out.println("Execution time£º" + getExecutionTime()
							+ "ms\n" + exeName + " execution finished!");
					Log.println("Execution time£º" + getExecutionTime() + "ms\n"
							+ exeName + " execution finished!");
					Log.close();
					Thread.sleep(10);
				} catch (Exception e) {
					System.out.println(exeName + " execution failed!");
					System.out.println(e.toString());
					Log.println(exeName + " execution failed!");
					Log.println(e.toString());
					Log.close();
				}
			}
		};
		executeProcess.start();
	}

	public void flush() {
		try {
			in.close();
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public long getExecutionTime() {
		return end - start;
	}

	public void init(BlockCipher _eng, CipherParameters _param,
			boolean _isEncrypt, String infile, String outfile, int mode)
			throws Exception {
		PaddedBufferedBlockCipher eng = null;
		switch (mode) {
		case 0:
			eng = new PaddedBufferedBlockCipher(new ECBBlockCipher(_eng));
			break;
		case 1:
			eng = new PaddedBufferedBlockCipher(new CBCBlockCipher(_eng));
			break;
		case 2:
			eng = new PaddedBufferedBlockCipher(new CFBBlockCipher(_eng, 1));
			break;
		case 3:
			eng = new PaddedBufferedBlockCipher(new CFBBlockCipher(_eng, 8));
			break;
		case 4:
			eng = new PaddedBufferedBlockCipher(new CFBBlockCipher(_eng, 64));
			break;
		case 5:
			eng = new PaddedBufferedBlockCipher(new OFBBlockCipher(_eng, 1));
			break;
		case 6:
			eng = new PaddedBufferedBlockCipher(new OFBBlockCipher(_eng, 8));
			break;
		case 7:
			eng = new PaddedBufferedBlockCipher(new OFBBlockCipher(_eng, 64));
			break;
		case 8:
			eng = new PaddedBufferedBlockCipher(new SICBlockCipher(_eng));
			break;
		default:
			eng = new PaddedBufferedBlockCipher(new ECBBlockCipher(_eng));
			break;
		}
		setEngine(eng);
		setParam(_param);
		setIsEncrypt(_isEncrypt);

		in = new BufferedInputStream(new FileInputStream(infile));
		java.io.File fin = new java.io.File(infile);
		total = Integer.valueOf(String.valueOf(fin.length()));
		cur = 0;
		out = new BufferedOutputStream(new FileOutputStream(outfile));
	}

	public void setEngine(PaddedBufferedBlockCipher _eng) {
		cipher = _eng;
	}

	public PaddedBufferedBlockCipher getEngine() {
		return cipher;
	}

	public CipherParameters getParam() {
		return param;
	}

	public void setParam(CipherParameters _param) {
		param = _param;
	}

	public boolean getIsEncrypt() {
		return isEncrypt;
	}

	public void setIsEncrypt(boolean _isEncrypt) {
		isEncrypt = _isEncrypt;
	}
}

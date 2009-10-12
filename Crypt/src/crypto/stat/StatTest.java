package crypto.stat;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;

import crypto.common.BasicMethod;
import crypto.common.Lib;
import crypto.execution.cipher.CipherExecution;
import crypto.prng.MWCGenerator;
import crypto.prng.ThreadedSeedGenerator;
import crypto.toolkit.IToolkit;

public class StatTest implements IToolkit {
	int testName, algoName;
	boolean autoGen, reserveGen;
	int keyLength;
	String keyFile = null;

	public static String[] algorithms = { "AES", "Blowfish", "Camellia",
			"CAST5", "CAST6", "DES", "GOST28147", "IDEA", "MISTY1", "Noekeon",
			"RC2", "RC6", "Rijndael", "SEED", "Serpent", "Skipjack", "TDEA",
			"TEA", "Twofish", "XTEA", "All" };

	public int getAlgorithmID(String name) {
		for (int i = 0; i < algorithms.length; ++i)
			if (algorithms[i].equals(name))
				return i;
		return -1;
	}

	public StatTest() {
	}

	@Override
	public String getName() {
		return "Statistical Test";
	}

	@Override
	public void init(String[] args) throws Exception {
		String tmp = null;
		tmp = BasicMethod.getParam(args, "/test:", 6);
		if (tmp == null)
			throw new Exception("Test name missing!");
		testName = Integer.valueOf(tmp);
		tmp = BasicMethod.getParam(args, "/algo:", 6);
		if (tmp == null)
			throw new Exception("Algorithm missing!");
		algoName = getAlgorithmID(tmp);
		if (algoName == -1)
			throw new Exception("Unknown cipher algorithm!");
		tmp = BasicMethod.getParam(args, "/auto:", 6);
		if (tmp == null)
			throw new Exception("Key generation type missing!");
		autoGen = Boolean.parseBoolean(tmp);
		if (autoGen) {
			tmp = BasicMethod.getParam(args, "/reserve:", 9);
			if (tmp == null)
				throw new Exception("Key reserve type missing!");
			reserveGen = Boolean.parseBoolean(tmp);
			tmp = BasicMethod.getParam(args, "/kenlen:", 8);
			if (tmp == null)
				throw new Exception("Key length missing!");
			keyLength = Integer.parseInt(tmp.split(" ")[0]);
		} else {
			tmp = BasicMethod.getParam(args, "/key:", 5);
			if (tmp == null)
				throw new Exception("Key file missing!");
			keyFile = tmp;
		}
	}

	@Override
	public void perform(PrintWriter log) throws Exception {
		String rootPath = System.getProperty("user.dir");
		String rndPath = rootPath + "\\config\\rnd.dat";
		String outPath = rootPath + "\\config\\out.dat";
		String keyPath = rootPath + "\\config\\key.txt";
		String[] cmd = new String[] { "cmd.exe", "/C",
				rootPath + "\\lib\\assess.exe", outPath,
				String.valueOf(testName + 1) };
		try {
			int len = 11468816;
			byte[] buf = new byte[len];

			MWCGenerator mgen = new MWCGenerator();
			mgen.addSeedMaterial(new ThreadedSeedGenerator().generateSeed(16,
					false));
			mgen.nextBytes(buf);
			BufferedOutputStream rndOut = new BufferedOutputStream(
					new FileOutputStream(rndPath));
			rndOut.write(buf);
			rndOut.close();

			if (autoGen) {
				byte[] keys = new byte[keyLength / 8];
				mgen.nextBytes(keys);
				PrintWriter keyOut = new PrintWriter(new File(keyPath));
				for (int i = 0; i < keys.length; ++i) {
					keyOut.write(Lib.toHexString(keys[i] & 0xff, 2));
				}
				keyOut.close();
			} else {
				keyPath = keyFile;
			}

			boolean[] flag = new boolean[algorithms.length - 1];
			Arrays.fill(flag, false);

			if (algoName == algorithms.length - 1) {
				Arrays.fill(flag, true);
			} else {
				flag[algoName] = true;
			}
			for (int i = 0; i < flag.length; ++i)
				if (flag[i]) {
					log.println("test result of " + algorithms[i]);
					try {
						CipherExecution exe = new CipherExecution();
						exe.init(new Object[] { algorithms[i], rndPath,
								outPath, keyPath, null, true, 0 });
						exe.performEncrypt();
						exe.flush();

						Process process = Runtime.getRuntime().exec(cmd);
						BufferedReader br = new BufferedReader(
								new InputStreamReader(process.getInputStream()));
						while (true) {
							String s = br.readLine();
							if (s == null)
								break;
							log.println(s);
						}
						br.close();
					} catch (Exception e) {
						log.println("test of " + algorithms[i] + " failed");
						e.printStackTrace();
					}
				}
			if (autoGen && !reserveGen)
				new File(keyPath).delete();
			String reportFile = "finalAnalysisReport";
			log.println(BasicMethod.readFile(reportFile));
			new File(reportFile).delete();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

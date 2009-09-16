package crypto.stat;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import crypto.prng.MWCGenerator;
import crypto.prng.ThreadedSeedGenerator;

public class TestCMD {

	public static void main(String[] args) {
		String rootPath = System.getProperty("user.dir");
		String rndPath = rootPath + "\\rnd.dat";
		String[] cmd = new String[] { "cmd.exe", "/C",
				rootPath + "\\lib\\diehard.exe", rndPath, "16" };
		try {
			int len = 11468800;
			byte[] buf = new byte[len];

			MWCGenerator mgen = new MWCGenerator();
			mgen.addSeedMaterial(new ThreadedSeedGenerator().generateSeed(16,
					false));
			mgen.nextBytes(buf);
			BufferedOutputStream rndOut = new BufferedOutputStream(
					new FileOutputStream(rndPath));
			rndOut.write(buf);
			rndOut.close();

			Process process = Runtime.getRuntime().exec(cmd);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			PrintWriter Log = new PrintWriter(new FileWriter("report.txt"));
			while (true) {
				String s = br.readLine();
				if (s == null)
					break;
				Log.println(s);
			}
			br.close();
			Log.close();

			new File(rndPath).delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

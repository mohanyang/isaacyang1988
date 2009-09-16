/**
 * 
 */
package crypto.test;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

import junit.framework.TestCase;
import crypto.prng.MWCGenerator;
import crypto.prng.ReversedWindowGenerator;
import crypto.prng.ThreadedSeedGenerator;
import crypto.prng.VMPCRandomGenerator;

/**
 * @author v-moyang
 * 
 */
public class PRNGTest extends TestCase {

	private String byte2Str(byte b) {
		String ret = Integer.toHexString(b & 0xff).toUpperCase();
		if (ret.length() == 1)
			ret = "0" + ret;
		return ret;
	}

	public void testReversedWindowGenerator() {
		ReversedWindowGenerator gen = new ReversedWindowGenerator(
				new VMPCRandomGenerator(), 32);
		gen.addSeedMaterial(new ThreadedSeedGenerator().generateSeed(1024,
				false));
		byte[] buf = new byte[1024];
		gen.nextBytes(buf);
		for (int i = 0; i < buf.length; ++i)
			System.out.print(byte2Str(buf[i]));
		System.out.println();

		MWCGenerator mgen = new MWCGenerator();
		mgen.addSeedMaterial(new ThreadedSeedGenerator().generateSeed(1024,
				false));
		mgen.nextBytes(buf);
		for (int i = 0; i < buf.length; ++i)
			System.out.print(byte2Str(buf[i]));
		System.out.println();
	}

	public void tesMWCGenerator() {
		try {
			System.out.println("gen 1mb bytes");
			BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream("mthr4.32"));
			byte[] buf = new byte[1024];
			MWCGenerator mgen = new MWCGenerator();
			mgen.addSeedMaterial(new byte[] { 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0,
					3, 0, 0, 0, 4 });
			for (int i = 0; i < 1024; ++i) {
				mgen.nextBytes(buf);
				out.write(buf);
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package crypto.toolkit.prng;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;

import crypto.common.BasicMethod;
import crypto.common.Lib;
import crypto.toolkit.IToolkit;

public class BasicPRNG implements IToolkit {

	String fileName = null;
	int length = 0, style = 0;

	public BasicPRNG() {
	}

	@Override
	public String getName() {
		return "Basic PRNG";
	}

	@Override
	public void init(String[] args) throws Exception {
		fileName = BasicMethod.getParam(args, "/out:", 5);
		if (fileName == null)
			throw new Exception("File name missing!");
		String tmp = BasicMethod.getParam(args, "/length:", 8);
		if (tmp == null)
			throw new Exception("Output length missing!");
		length = Integer.valueOf(tmp);
		tmp = BasicMethod.getParam(args, "/style:", 7);
		if (tmp == null)
			throw new Exception("Output style missing!");
		style = Integer.valueOf(tmp);
	}

	@Override
	public void perform(PrintWriter log) throws Exception {
		PrintWriter out = null;
		BufferedOutputStream bout = null;
		switch (style) {
		case 0:
			out = new PrintWriter(new FileWriter(fileName));
			for (int i = 0; i < length; ++i) {
				out.print(Lib.random(2));
			}
			out.close();
			break;
		case 1:
			out = new PrintWriter(new FileWriter(fileName));
			for (int i = 0; i < length; ++i) {
				out.print(Integer.toHexString(Lib.random(256)));
			}
			out.close();
			break;
		case 2:
			bout = new BufferedOutputStream(new FileOutputStream(fileName));
			for (int i = 0; i < length; ++i) {
				bout.write(Lib.random(256));
			}
			bout.close();
			break;
		}
		log.println("Random number successfully generated!");
	}

	public static void main(String[] args) {
		BasicPRNG b = new BasicPRNG();
		try {
			b
					.init(new String[] { "/out:rnd.txt", "/length:10000",
							"/style:2" });
			b.perform(new PrintWriter(System.out));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

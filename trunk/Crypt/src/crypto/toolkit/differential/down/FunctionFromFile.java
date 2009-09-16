package crypto.toolkit.differential.down;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.StreamTokenizer;

import crypto.util.FunctionType;

public class FunctionFromFile implements FunctionType {

	private int[] a = null;
	private int n = 0, upper = 0, max = 0;

	public FunctionFromFile(String fileName) throws Exception {
		try {
			File file = new File(fileName);
			Reader reader = new FileReader(file);
			StreamTokenizer s = new StreamTokenizer(reader);

			s.resetSyntax();
			s.eolIsSignificant(false);
			s.parseNumbers();
			s.whitespaceChars(0x00, 0x20);
			s.wordChars(0x21, 0xFF);
			s.commentChar('#');
			s.quoteChar('"');
			s.nextToken();
			if (s.ttype == StreamTokenizer.TT_NUMBER)
				n = (int) s.nval;
			else
				throw new Exception("Array size missing!");
			upper = 1 << n;
			max = upper - 1;
			a = new int[upper];
			int tmp = -1;
			for (int i = 0; i != upper; ++i) {
				s.nextToken();
				if (s.ttype == StreamTokenizer.TT_NUMBER)
					a[i] = (int) s.nval;
				else
					throw new Exception("Not enough values!");
				if (a[i] > tmp)
					tmp = a[i];
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int calc(int input) {
		return a[input & max];
	}

	@Override
	public int getN() {
		return n;
	}

}

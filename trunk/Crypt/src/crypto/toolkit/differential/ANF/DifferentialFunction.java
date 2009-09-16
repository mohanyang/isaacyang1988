package crypto.toolkit.differential.ANF;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.StreamTokenizer;

import crypto.common.BasicMethod;
import crypto.util.FunctionType;

public class DifferentialFunction implements FunctionType {
	private int[] a = null;
	private int b = 0, n = 0, width = 0, upper = 0, max = 0, order = 0;
	private int[] point = null;

	public DifferentialFunction(String fileName) throws Exception {
		try {
			File file = new File(fileName);
			Reader reader = new FileReader(file);
			StreamTokenizer s = new StreamTokenizer(reader);

			s.resetSyntax();
			s.eolIsSignificant(false);
			s.whitespaceChars(0x00, 0x20);
			s.wordChars(0x21, 0xFF);
			s.commentChar('#');
			s.quoteChar('"');
			s.nextToken();
			if (s.ttype == StreamTokenizer.TT_WORD)
				n = Integer.parseInt(s.sval);
			else
				throw new Exception("Array size missing!");
			upper = 1 << n;
			max = upper - 1;
			a = new int[upper];
			int tmp = -1;
			for (int i = 0; i != upper; ++i) {
				s.nextToken();
				if (s.ttype == StreamTokenizer.TT_WORD)
					a[i] = Integer.parseInt(s.sval);
				else
					throw new Exception("Not enough values!");
				if (a[i] > tmp)
					tmp = a[i];
			}
			for (width = 1; (1 << width) < tmp; ++width)
				;
			s.nextToken();
			if (s.ttype == StreamTokenizer.TT_WORD)
				order = Integer.parseInt(s.sval);
			else
				throw new Exception("Differential order missing!");
			point = new int[order];
			for (int i = 0; i != order; ++i) {
				s.nextToken();
				if (s.ttype == StreamTokenizer.TT_WORD)
					point[i] = Integer.parseInt(s.sval, 2);
				else
					throw new Exception("Differential point missing!");
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setBit(int b) {
		this.b = b;
	}

	public int getWidth() {
		return width;
	}

	public int getOrder() {
		return order;
	}

	private int value(int input) {
		int ret = a[input & max];
		ret = (ret & (1 << b)) >> b;
		return ret;
	}

	@Override
	public int calc(int input) {
		int ret = 0, bound = 1 << order, delta;
		for (int i = 0; i != bound; ++i) {
			String tmp = BasicMethod.toBitString(i, order);
			delta = 0;
			for (int j = 0; j != order; ++j)
				if (tmp.charAt(j) == '1') {
					delta ^= point[j];
				}
			ret ^= value(input ^ delta);
		}
		return ret;
	}

	@Override
	public int getN() {
		return n;
	}
}

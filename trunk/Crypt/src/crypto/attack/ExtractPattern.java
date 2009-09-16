package crypto.attack;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;

public class ExtractPattern {
	private Set<String> p1 = null, p2 = null;

	private Set<String> loadSet(String file) {
		Set<String> ret = null;
		try {
			ret = new HashSet<String>();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new BufferedInputStream(new FileInputStream(file))));
			String rv = null;
			while ((rv = br.readLine()) != null) {
				ret.add(rv);
			}
			br.close();
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public ExtractPattern() {
		PrintWriter out = null;
		try {
			p1 = loadSet("report_y0.txt");
			p2 = loadSet("report_y1.txt");
			p1.retainAll(p2);

			out = new PrintWriter(new FileWriter("report.txt"));
			for (Iterator<String> itr = p1.iterator(); itr.hasNext();)
				out.println(itr.next());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

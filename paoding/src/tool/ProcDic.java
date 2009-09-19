package tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class ProcDic {

	public static void main(String[] args) {
		try {
			InputStream in = new FileInputStream(new File(
					".\\dic\\cedict_ts.u8"));
			OutputStream out = new FileOutputStream(new File(
					".\\dic\\cedict.u8"));
			BufferedReader re = new BufferedReader(new InputStreamReader(in,
					"UTF-8"));
			BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(out,
					"UTF-8"));
			String line = null;
			while ((line = re.readLine()) != null) {
				if (!line.startsWith("#")) {
					String[] s = line.split(" ");
					wr.write(s[1]);
					wr.newLine();
				}
			}
			re.close();
			wr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

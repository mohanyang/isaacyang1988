package tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashSet;

public class MergeDict {
	static HashSet<String> set = new HashSet<String>();

	public static void loadFile(String name) {
		try {
			BufferedReader re = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(name)), "UTF-8"));
			String line = null;
			while ((line = re.readLine()) != null) {
				line = line.trim();
				if (!line.equals(""))
					set.add(line.trim());
			}
			re.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			loadFile(".\\dic\\dict.u8");
			loadFile(".\\dic\\t-base.dic");
			loadFile(".\\dic\\sogou-dic.dic");

			String[] arr = new String[set.size()];
			int i = 0;
			for (String s : set)
				arr[i++] = s;
			Arrays.sort(arr);

			BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(new File(".\\dic\\dict.u8")), "UTF-8"));
			for (String s : arr) {
				wr.write(s);
				wr.newLine();
			}
			wr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

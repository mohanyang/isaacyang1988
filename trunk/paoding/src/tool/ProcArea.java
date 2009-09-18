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

public class ProcArea {

	public static void main(String[] args) {
		try {
			BufferedReader re = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(".\\dic\\area_all.txt")),
					"UTF-8"));
			HashSet<String> set = new HashSet<String>();
			String line = null, subStr;
			int pos = 0, pos1 = 0;
			while ((line = re.readLine()) != null) {
				line = line.trim();
				set.add(line);
				pos = line.lastIndexOf('(');
				if (pos != -1) {
					subStr = line.substring(0, pos);
					set.add(subStr);
				} else {
					pos1 = line.indexOf('·');
					if (pos1 != -1) {
						String[] sub = line.split("·");
						StringBuilder buf = new StringBuilder();
						for (int i = 0; i < sub.length; ++i) {
							set.add(sub[i].trim());
							buf.append(sub[i].trim());
						}
						set.add(buf.toString());
					}
				}
			}
			re.close();

			String[] arr = new String[set.size()];
			int i = 0;
			for (String s : set)
				arr[i++] = s;
			Arrays.sort(arr);

			BufferedWriter wr = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(new File(
							".\\dic\\area.txt")), "UTF-8"));
			for (String s : arr) {
				if (!s.equals("")) {
					wr.write(s);
					wr.newLine();
				}
			}
			wr.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

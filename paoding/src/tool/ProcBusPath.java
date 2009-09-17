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

public class ProcBusPath {

	public static void main(String[] args) {
		try {
			BufferedReader re = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File(
							".\\dic\\buspath.txt")), "GBK"));
			HashSet<String> set = new HashSet<String>();
			String line = null;
			while ((line = re.readLine()) != null) {
				String[] tmp = line.trim().split("　");
				line = tmp[tmp.length - 1];
				tmp = line.split("→");
				for (int i = 0; i < tmp.length; ++i) {
					int pos = tmp[i].trim().indexOf('.');
					set.add(tmp[i].trim().substring(pos + 1));
					// System.out.println(tmp[i].trim().substring(pos + 1));
				}
			}
			re.close();

			String[] arr = new String[set.size()];
			int i = 0;
			for (String s : set)
				arr[i++] = s;
			Arrays.sort(arr);

			BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(new File(".\\dic\\bus.txt")), "UTF-8"));
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

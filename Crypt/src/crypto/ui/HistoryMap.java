package crypto.ui;

import java.util.HashMap;
import java.util.Iterator;

public class HistoryMap {
	private static final String fileName = "./config/system.ini";

	public static void load() {
		String[] tmp = crypto.common.BasicMethod.readFile(fileName).split("\n");
		map = new HashMap<String, String>();
		for (int i = 0; i < tmp.length; ++i) {
			String[] tt = tmp[i].split("=");
			if (tt.length == 2)
				map.put(tt[0].trim(), tt[1].trim());
		}
	}

	public static void update() {
		StringBuffer buf = new StringBuffer();
		for (Iterator<String> itr = map.keySet().iterator(); itr.hasNext();) {
			String key = itr.next();
			buf.append(key + " = " + get(key) + "\n");
		}
		crypto.common.BasicMethod.writeFile(fileName, buf.toString());
	}

	public static void put(String key, String value) {
		map.put(key, value);
		update();
	}

	public static String get(String key) {
		return map.get(key);
	}

	private static HashMap<String, String> map;
}

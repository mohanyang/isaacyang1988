package crypto.ui;

import java.util.HashMap;
import java.util.Iterator;

import crypto.common.Lib;

public final class ArgManager {

	private ArgManager() {

	}

	public static void init() {
		Lib.assertTrue(loaded == false);
		config = new HashMap<String, String>();
	}

	public static void put(String key, String value) {
		config.put(key, value);
	}

	public static String get(String key) {
		return config.get(key);
	}

	public static void clear() {
		config.clear();
	}

	public static String displayArg() {
		StringBuffer buf = new StringBuffer();
		for (Iterator<String> itr = config.keySet().iterator(); itr.hasNext();) {
			String key = itr.next();
			buf.append(key + " : " + get(key) + "\n");
		}
		return buf.toString();
	}

	public static String[] makeArg() {
		String[] ret = new String[config.size()];
		int i = 0;
		for (Iterator<String> itr = config.keySet().iterator(); itr.hasNext();) {
			String key = itr.next();
			ret[i++] = "/" + key + ":" + get(key);
		}
		return ret;
	}

	private static boolean loaded = false;
	private static HashMap<String, String> config;
}

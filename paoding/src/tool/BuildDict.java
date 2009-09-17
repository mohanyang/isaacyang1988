package tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BuildDict {

	class Node {
		HashMap<Character, Node> child = null;
		char val;
		boolean isStop;

		Node(char v) {
			child = new HashMap<Character, Node>();
			isStop = false;
			this.val = v;
		}
	}

	public void buildDict() {
		System.out.println("building dictionary...");
		if (root == null) {
			root = new Node(' ');
			for (String s : list) {
				insert(s);
			}
			System.out.println("dictionary build successful.");
		}
	}

	public void insert(String s) {
		Node cur = root, next = null;
		int l = s.length();
		for (int i = 0; i < l; ++i) {
			char ch = s.charAt(i);
			next = cur.child.get(ch);
			if (next == null) {
				next = new Node(ch);
				cur.child.put(ch, next);
				cur = next;
			} else {
				cur = next;
			}
			if (/* i > 0 && */i == l - 1)
				cur.isStop = true;
		}
	}

	public boolean searchDict(String s) {
		Node cur = root, next = null;
		int l = s.length();
		for (int i = 0; i < l; ++i) {
			char ch = s.charAt(i);
			next = cur.child.get(ch);
			if (next == null)
				return false;
			cur = next;
		}
		return cur.isStop;
	}

	public void splitWord(String str) {
		Queue<String> strQueue = new LinkedList<String>();
		Queue<Integer> posQueue = new LinkedList<Integer>();
		boolean[] startFromPos = new boolean[str.length()];
		Arrays.fill(startFromPos, false);

		Node cur = null, next = null;
		String s = null;
		int pos = 0;
		char[] buf = new char[str.length()];

		strQueue.offer(str);
		posQueue.offer(0);

		while (!strQueue.isEmpty()) {
			cur = root;
			s = strQueue.poll();
			pos = posQueue.poll();

			if (!startFromPos[pos]) {
				startFromPos[pos] = true;
				int l = s.length();
				for (int i = 0; i < l; ++i) {
					char ch = s.charAt(i);
					next = cur.child.get(ch);
					if (next == null)
						break;
					buf[i] = ch;
					cur = next;
					if (cur.isStop) {
						if (i + 1 != l && !startFromPos[i + 1 + pos]) {
							strQueue.offer(s.substring(i + 1));
							posQueue.offer(i + 1 + pos);
						}
						System.out.println(String.copyValueOf(buf, 0, i + 1));
					}
				}
			}
		}
	}

	static List<String> list = new LinkedList<String>();
	static Node root = null;

	public static void main(String[] args) {
		try {
			BufferedReader re = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(".\\dic\\dict.u8")), "UTF-8"));
			String line = null;
			while ((line = re.readLine()) != null) {
				list.add(line.trim());
			}
			re.close();

			BuildDict dict = new BuildDict();
			dict.buildDict();
			System.out.println(dict.searchDict("中国"));
			System.out.println(dict.searchDict("家国"));
			System.out.println(dict.searchDict("国家"));
			long start = System.currentTimeMillis();
			dict.splitWord("中华人民共和国");
			System.out.println((System.currentTimeMillis() - start) + "ms");
			start = System.currentTimeMillis();
			dict.splitWord("很好的人民");
			System.out.println((System.currentTimeMillis() - start) + "ms");
			start = System.currentTimeMillis();
			dict.splitWord("知春路旁小区");
			System.out.println((System.currentTimeMillis() - start) + "ms");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
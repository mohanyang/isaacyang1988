package tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class BuildDict {

	static final char nil = ' ', bus = 'b', zone = 'z', house = 'h';

	class Node {
		HashMap<Character, Node> child = null;
		char val, tag;
		boolean isStop;

		Node(char v) {
			child = new HashMap<Character, Node>();
			isStop = false;
			val = v;
			tag = nil;
		}
	}

	class Token {
		String str;
		char tag;

		Token(String s, char t) {
			str = s;
			tag = t;
		}
	}

	public void buildWordDict() throws Exception {
		System.out.println("building word dictionary...");
		if (root == null) {
			root = new Node(' ');
		}
		BufferedReader re = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File(".\\dic\\dict.u8")), "UTF-8"));
		String line = null;
		while ((line = re.readLine()) != null) {
			insert(line.trim(), nil);
		}
		re.close();
	}

	public void buildBusDict() throws Exception {
		System.out.println("building bus dictionary...");
		if (root == null) {
			root = new Node(' ');
		}
		BufferedReader re = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File(".\\dic\\bus.txt")), "UTF-8"));
		String line = null;
		while ((line = re.readLine()) != null) {
			insert(line.trim(), bus);
		}
		re.close();
	}

	public void buildZoneDict() throws Exception {
		System.out.println("building zone dictionary...");
		if (root == null) {
			root = new Node(' ');
		}
		BufferedReader re = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File(".\\dic\\zone.txt")), "UTF-8"));
		String line = null;
		while ((line = re.readLine()) != null) {
			insert(line.trim(), zone);
		}
		re.close();
	}

	public void buildHouseDict() throws Exception {
		System.out.println("building house dictionary...");
		if (root == null) {
			root = new Node(' ');
		}
		BufferedReader re = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File(".\\dic\\house.txt")), "UTF-8"));
		String line = null;
		while ((line = re.readLine()) != null) {
			insert(line.trim(), house);
		}
		re.close();
	}

	public void insert(String s, char tag) {
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
			if (i == l - 1) {
				cur.isStop = true;
				cur.tag = tag;
			}
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

	public LinkedList<Token> simpleSplitWord(String str) {
		Queue<String> strQueue = new LinkedList<String>();
		Queue<Integer> posQueue = new LinkedList<Integer>();
		boolean[] startFromPos = new boolean[str.length()];
		Arrays.fill(startFromPos, false);

		Node cur = null, next = null;
		String s = null;
		int pos = 0, lastPos = -1, totalLen = str.length();
		char tag = nil;
		char[] buf = new char[totalLen];
		LinkedList<Token> match = new LinkedList<Token>();

		strQueue.offer(str);
		posQueue.offer(0);

		while (!strQueue.isEmpty()) {
			cur = root;
			s = strQueue.poll();
			pos = posQueue.poll();

			if (!startFromPos[pos]) {
				startFromPos[pos] = true;
				lastPos = -1;
				int l = s.length();
				for (int i = 0; i < l; ++i) {
					char ch = s.charAt(i);
					next = cur.child.get(ch);
					if (next == null)
						break;
					buf[i] = ch;
					cur = next;
					if (cur.isStop) {
						if (i + 1 == l || !startFromPos[i + 1 + pos]) {
							lastPos = i + 1;
							tag = cur.tag;
						}
					}
				}
				if (lastPos == -1) {
					match.add(new Token(s, nil));
				} else {
					match.add(new Token(s.substring(0, lastPos), tag));
					if (lastPos + pos < totalLen) {
						strQueue.offer(s.substring(lastPos));
						posQueue.offer(lastPos + pos);
					}
				}
			}
		}
		return match;
	}

	public LinkedList<String> complexSplitWord(String str) {
		Queue<String> strQueue = new LinkedList<String>();
		Queue<Integer> posQueue = new LinkedList<Integer>();
		boolean[] startFromPos = new boolean[str.length()];
		Arrays.fill(startFromPos, false);

		Node cur = null, next = null;
		String s = null;
		int pos = 0, lastPos = -1, totalLen = str.length();
		char[] buf = new char[totalLen];
		LinkedList<String> match = new LinkedList<String>();

		strQueue.offer(str);
		posQueue.offer(0);

		while (!strQueue.isEmpty()) {
			cur = root;
			s = strQueue.poll();
			pos = posQueue.poll();

			if (!startFromPos[pos]) {
				startFromPos[pos] = true;
				lastPos = -1;
				int l = s.length();
				for (int i = 0; i < l; ++i) {
					char ch = s.charAt(i);
					next = cur.child.get(ch);
					if (next == null)
						break;
					buf[i] = ch;
					cur = next;
					if (cur.isStop) {
						if (i + 1 == l || !startFromPos[i + 1 + pos]) {
							lastPos = i + 1;
						}
					}
				}
				if (lastPos == -1) {
					match.add(s);
				} else {
					match.add(s.substring(0, lastPos));
					if (lastPos + pos < totalLen) {
						strQueue.offer(s.substring(lastPos));
						posQueue.offer(lastPos + pos);
					}
				}
			}
		}
		return match;
	}

	static Node root = null;

	public static void main(String[] args) {
		try {
			BuildDict dict = new BuildDict();
			dict.buildWordDict();
			dict.buildBusDict();
			dict.buildZoneDict();
			dict.buildHouseDict();

			System.out.println(dict.searchDict("中国"));
			System.out.println(dict.searchDict("家国"));
			System.out.println(dict.searchDict("国家"));
			long start = System.currentTimeMillis();
			for (Token t : dict.simpleSplitWord("中华人民共和国"))
				System.out.println(t.str + " " + t.tag);
			System.out.println((System.currentTimeMillis() - start) + "ms");
			start = System.currentTimeMillis();
			for (Token t : dict.simpleSplitWord("勇敢的人民"))
				System.out.println(t.str + " " + t.tag);
			System.out.println((System.currentTimeMillis() - start) + "ms");
			start = System.currentTimeMillis();
			for (Token t : dict.simpleSplitWord("某大学博士的立委候选人"))
				System.out.println(t.str + " " + t.tag);
			System.out.println((System.currentTimeMillis() - start) + "ms");
			start = System.currentTimeMillis();
			for (Token t : dict.simpleSplitWord("知春路旁的小区"))
				System.out.println(t.str + " " + t.tag);
			System.out.println((System.currentTimeMillis() - start) + "ms");
			start = System.currentTimeMillis();
			for (Token t : dict.simpleSplitWord("知春路附近的小区两房一厅"))
				System.out.println(t.str + " " + t.tag);
			System.out.println((System.currentTimeMillis() - start) + "ms");
			start = System.currentTimeMillis();
			for (Token t : dict.simpleSplitWord("海淀区知春路小区两房两厅"))
				System.out.println(t.str + " " + t.tag);
			System.out.println((System.currentTimeMillis() - start) + "ms");
			start = System.currentTimeMillis();
			for (Token t : dict.simpleSplitWord("学院路豪华装修三居"))
				System.out.println(t.str + " " + t.tag);
			System.out.println((System.currentTimeMillis() - start) + "ms");
			start = System.currentTimeMillis();
			for (Token t : dict.simpleSplitWord("五道口学院路逸成东苑两居"))
				System.out.println(t.str + " " + t.tag);
			System.out.println((System.currentTimeMillis() - start) + "ms");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

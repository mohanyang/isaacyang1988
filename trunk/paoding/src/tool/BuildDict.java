package tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class BuildDict {

	static final char nil = ' ', bus = 'b', zone = 'z', house = 'h',
			area = 'a';

	class Node {
		Node father = null;
		HashMap<Character, Node> child = null;
		char val, tag;
		boolean isStop;
		short depth = 0;

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

	protected BuildDict() {
		dicRoot = new Node(nil);
		areaRoot = new Node(nil);
	}

	public void buildWordDict(Node root) throws Exception {
		System.out.println("building word dictionary...");
		BufferedReader re = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File(".\\dic\\dict.u8")), "UTF-8"));
		String line = null;
		while ((line = re.readLine()) != null) {
			insert(root, line.trim(), nil);
		}
		re.close();
	}

	public void buildBusDict(Node root) throws Exception {
		System.out.println("building bus dictionary...");
		BufferedReader re = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File(".\\dic\\bus.txt")), "UTF-8"));
		String line = null;
		while ((line = re.readLine()) != null) {
			insert(root, line.trim(), bus);
		}
		re.close();
	}

	public void buildZoneDict(Node root) throws Exception {
		System.out.println("building zone dictionary...");
		BufferedReader re = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File(".\\dic\\zone.txt")), "UTF-8"));
		String line = null;
		while ((line = re.readLine()) != null) {
			insert(root, line.trim(), zone);
		}
		re.close();
	}

	public void buildHouseDict(Node root) throws Exception {
		System.out.println("building house dictionary...");
		BufferedReader re = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File(".\\dic\\house.txt")), "UTF-8"));
		String line = null;
		while ((line = re.readLine()) != null) {
			insert(root, line.trim(), house);
		}
		re.close();
	}

	public void buildAreaDict(Node root) throws Exception {
		System.out.println("building area dictionary...");
		BufferedReader re = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File(".\\dic\\area.txt")), "UTF-8"));
		String line = null;
		while ((line = re.readLine()) != null) {
			insert(root, line.trim(), area);
		}
		re.close();
	}

	public void insert(Node root, String s, char tag) {
		Node cur = root, next = null;
		int l = s.length();
		for (int i = 0; i < l; ++i) {
			char ch = s.charAt(i);
			next = cur.child.get(ch);
			if (next == null) {
				next = new Node(ch);
				next.father = cur;
				next.depth = (short) (i + 1);
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

	public boolean searchDict(Node root, String s) {
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

	public LinkedList<Token> simpleSplitWord(Node root, String str) {
		Node cur = null, next = null;
		String s = str;
		int pos = 0, lastPos = -1, totalLen = str.length();
		char tag = nil;
		LinkedList<Token> match = new LinkedList<Token>();

		while (pos < totalLen) {
			cur = root;
			lastPos = -1;
			int l = s.length();
			for (int i = 0; i < l; ++i) {
				char ch = s.charAt(i);
				next = cur.child.get(ch);
				if (next == null)
					break;
				cur = next;
				if (cur.isStop) {
					lastPos = i + 1;
					tag = cur.tag;
				}
			}
			if (lastPos == -1) {
				match.add(new Token(s, nil));
				pos = l + pos;
			} else {
				match.add(new Token(s.substring(0, lastPos), tag));
				if (lastPos < l) {
					s = s.substring(lastPos);
				}
				pos = lastPos + pos;
			}
		}
		return match;
	}

	String makeStr(Node n) {
		Node cur = n;
		StringBuilder buf = new StringBuilder();
		while (cur.father != null) {
			buf.insert(0, cur.val);
			cur = cur.father;
		}
		return buf.toString();
	}

	public LinkedList<Token> simpleMatchWord(Node root, String str,
			int faultThreshold) {
		Queue<Node> nodeQueue = new LinkedList<Node>();
		Queue<Integer> posQueue = new LinkedList<Integer>();
		Queue<Integer> faultQueue = new LinkedList<Integer>();

		Node curNode = null, nextNode = null, lastNode = null;
		String s = str;
		int curPos, curFault, tmpFault, totalLength = s.length(), lastPos = 0;
		HashSet<Node> nodeMatch = new HashSet<Node>();
		LinkedList<Token> match = new LinkedList<Token>();

		nodeQueue.offer(root);
		posQueue.offer(0);
		faultQueue.offer(0);

		while (!nodeQueue.isEmpty()) {
			curNode = nodeQueue.poll();
			curPos = posQueue.poll();
			curFault = faultQueue.poll();
			tmpFault = curFault;
			lastNode = null;
			for (int i = curPos; i < totalLength; ++i) {
				char ch = s.charAt(i);
				nextNode = curNode.child.get(ch);
				if (nextNode == null) {
					if (curFault < faultThreshold) {
						nodeQueue.offer(curNode);
						posQueue.offer(i + 1);
						faultQueue.offer(curFault + 1);
						for (Node node : curNode.child.values()) {
							nodeQueue.offer(node);
							posQueue.offer(i);
							faultQueue.offer(curFault + 1);
						}
						break;
					} else
						break;
				} else {
					if (i > 0 && tmpFault < faultThreshold) {
						for (Node node : curNode.child.values()) {
							nodeQueue.offer(node);
							posQueue.offer(i + 1);
							faultQueue.offer(tmpFault + 1);
						}
						tmpFault = tmpFault + 1;
					}
				}
				curNode = nextNode;
				if (curNode.isStop) {
					lastNode = curNode;
					lastPos = i + 1;
				}
			}
			if (lastNode == null) {
				if (curFault + totalLength - curNode.depth <= faultThreshold)
					nodeMatch.add(curNode);
			} else {
				if (curFault + totalLength - lastPos <= faultThreshold)
					nodeMatch.add(lastNode);
			}
		}
		for (Node node : nodeMatch) {
			if (node.tag == area)
				match.add(new Token(makeStr(node), node.tag));
		}
		return match;
	}

	public LinkedList<String> complexSplitWord(Node root, String str) {
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

	static Node dicRoot = null, areaRoot = null;

	public static void main(String[] args) {
		try {
			BuildDict dict = new BuildDict();
			dict.buildWordDict(dicRoot);
			dict.buildBusDict(dicRoot);
			dict.buildZoneDict(dicRoot);
			dict.buildHouseDict(dicRoot);
			dict.buildAreaDict(dicRoot);

			dict.buildAreaDict(areaRoot);

			System.out.println(dict.searchDict(dicRoot, "中国"));
			System.out.println(dict.searchDict(dicRoot, "家国"));
			System.out.println(dict.searchDict(dicRoot, "国家"));
			long start = System.currentTimeMillis();
			for (Token t : dict.simpleSplitWord(dicRoot, "中华人民共和国"))
				System.out.println(t.str + " " + t.tag);
			System.out.println((System.currentTimeMillis() - start) + "ms");
			start = System.currentTimeMillis();
			for (Token t : dict.simpleSplitWord(dicRoot, "勇敢的人民"))
				System.out.println(t.str + " " + t.tag);
			System.out.println((System.currentTimeMillis() - start) + "ms");
			start = System.currentTimeMillis();
			for (Token t : dict.simpleSplitWord(dicRoot, "某大学博士的立委候选人"))
				System.out.println(t.str + " " + t.tag);
			System.out.println((System.currentTimeMillis() - start) + "ms");
			start = System.currentTimeMillis();
			for (Token t : dict.simpleSplitWord(dicRoot, "知春路旁的小区"))
				System.out.println(t.str + " " + t.tag);
			System.out.println((System.currentTimeMillis() - start) + "ms");
			start = System.currentTimeMillis();
			for (Token t : dict.simpleSplitWord(dicRoot, "知春路附近的小区两房一厅"))
				System.out.println(t.str + " " + t.tag);
			System.out.println((System.currentTimeMillis() - start) + "ms");
			start = System.currentTimeMillis();
			for (Token t : dict.simpleSplitWord(dicRoot, "海淀区知春路小区两房两厅"))
				System.out.println(t.str + " " + t.tag);
			System.out.println((System.currentTimeMillis() - start) + "ms");
			start = System.currentTimeMillis();
			for (Token t : dict.simpleSplitWord(dicRoot, "学院路豪华装修三居"))
				System.out.println(t.str + " " + t.tag);
			System.out.println((System.currentTimeMillis() - start) + "ms");
			start = System.currentTimeMillis();
			for (Token t : dict.simpleSplitWord(dicRoot, "五道口学院路逸成东苑两居"))
				System.out.println(t.str + " " + t.tag);
			System.out.println((System.currentTimeMillis() - start) + "ms");
			start = System.currentTimeMillis();
			for (Token t : dict.simpleSplitWord(dicRoot, "紫金公寓"))
				System.out.println(t.str + " " + t.tag);
			System.out.println((System.currentTimeMillis() - start) + "ms");
			start = System.currentTimeMillis();
			for (Token t : dict.simpleSplitWord(dicRoot, "都市华庭1期"))
				System.out.println(t.str + " " + t.tag);
			System.out.println((System.currentTimeMillis() - start) + "ms");
			start = System.currentTimeMillis();
			for (Token t : dict.simpleSplitWord(dicRoot, "都会华庭2期"))
				System.out.println(t.str + " " + t.tag);
			System.out.println((System.currentTimeMillis() - start) + "ms");
			start = System.currentTimeMillis();
			for (Token t : dict.simpleSplitWord(dicRoot, "都会华庭 二期"))
				System.out.println(t.str + " " + t.tag);
			System.out.println((System.currentTimeMillis() - start) + "ms");
			start = System.currentTimeMillis();
			for (Token t : dict.simpleSplitWord(dicRoot, "珠江奥古斯塔城邦"))
				System.out.println(t.str + " " + t.tag);
			System.out.println((System.currentTimeMillis() - start) + "ms");

			start = System.currentTimeMillis();
			for (Token t : dict.simpleMatchWord(areaRoot, "紫金公寓", 2))
				System.out.println(t.str + " " + t.tag);
			System.out.println((System.currentTimeMillis() - start) + "ms");

			start = System.currentTimeMillis();
			for (Token t : dict.simpleMatchWord(areaRoot, "都市华庭", 2))
				System.out.println(t.str + " " + t.tag);
			System.out.println((System.currentTimeMillis() - start) + "ms");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

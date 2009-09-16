package crypto.attack.test;

import java.util.Arrays;
import java.util.LinkedList;

import crypto.common.Lib;

public class CubeAttack {

	TriviumFunction tf = null;
	int d = 0, total = 0;
	boolean[] bucket = new boolean[80];
	byte[] key = new byte[10];
	byte[] key1 = new byte[10];
	LinkedList<int[]> queueList = new LinkedList<int[]>();

	public CubeAttack() {
		Lib.seedRandom(0);
		d = 12;
		tf = new TriviumFunction(d);
	}

	public void run() {
		generateCube();
	}

	void generateCube() {
		int count = 0, tmp = 0;
		int[] list = new int[] { 3, 14, 16, 18, 20, 23, 32, 46, 56, 57, 65, 73 };

		for (count = 0; count != d; ++count)
			System.out.print(list[count] + " ");
		System.out.println();

		tf.setList(list);
		count = 0;
		tmp = -1;
		int t0, t1, t2, t3;
		Arrays.fill(key, (byte) 0);
		t0 = tf.calc(key);

		// while (count != 100) {
		// for (int i = 0; i < 10; ++i)
		// key[i] = (byte) Lib.random(256);
		// t1 = tf.calc(key);
		//
		// for (int i = 0; i < 10; ++i)
		// key1[i] = key[i];
		// // key[0] ^=(byte)111;
		// for (int i = 0; i < 10; ++i)
		// key[i] = (byte) Lib.random(256);
		// t2 = tf.calc(key);
		//
		// for (int i = 0; i < 10; ++i)
		// key[i] ^= key1[i];
		// t3 = tf.calc(key);
		//
		// System.out.println(t0 + " " + t1 + " " + t2 + " " + t3);
		//
		// if ((t1 ^ t2) != (t0 ^ t3))
		// return;
		// // if (t1 == 0 && t2 == 0)
		// // return;
		// // t1 ^= t2;
		// // if (tmp == -1)
		// // tmp = t1;
		// // else if (tmp != t1)
		// // return;
		// // ++count;
		// System.out.println(count);
		// }
		//
		// for (Iterator<int[]> itr = queueList.iterator(); itr.hasNext();) {
		// int[] q = itr.next();
		// if (isEqual(q, list))
		// return;
		// }
		// queueList.add(list);

		System.out.println("new IV set");
		for (count = 0; count != d; ++count)
			System.out.print(list[count] + " ");
		System.out.println();

		if (t0 == 1)
			System.out.print("1 ");
		for (count = 0; count != 80; ++count) {
			Arrays.fill(key, (byte) 0);
			key[count >> 3] |= 1 << (7 - (count & 7));
			if ((t0 ^ tf.calc(key)) == 1)
				System.out.print("x" + count + " ");
			// System.out.print((t0 ^ tf.calc(key)) + " ");
		}
		System.out.println();
		System.out.println(++total);
	}

	boolean isEqual(int[] q, int[] p) {
		for (int i = 0; i != d; ++i)
			if (q[i] != p[i])
				return false;
		return true;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new CubeAttack().run();
	}
}
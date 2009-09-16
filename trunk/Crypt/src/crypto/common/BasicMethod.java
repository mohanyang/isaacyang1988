package crypto.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.bouncycastle.util.encoders.Hex;

public class BasicMethod {
	public static synchronized String readFile(String file) {
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb = sb.append(line).append("\n");
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString().trim();
	}

	public static synchronized String readToHtmlFile(String file) {
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			String line = null;
			sb.append("<html><font color=red>");
			while ((line = br.readLine()) != null) {
				sb = sb.append(line).append("<\br>");
			}
			sb.append("</font></html>");
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString().trim();
	}

	public static synchronized String readFile(String file, int n) {
		if (n == 0) {
			return readFile(file);
		}
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb = sb.append(line).append("\n");
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString().trim();
	}

	public static synchronized byte[] readBinFile(String file) {
		BufferedInputStream in = null;
		byte[] ret = null;
		try {
			in = new BufferedInputStream(new FileInputStream(file));
			ret = new byte[in.available()];
			in.read(ret, 0, ret.length);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static synchronized boolean fileCompare(String f1, String f2) {
		byte[] b1 = readBinFile(f1), b2 = readBinFile(f2);
		if (b1.length == 0)
			return false;
		for (int i = 0; i < b1.length; ++i)
			if (b1[i] != b2[i])
				return false;
		return true;
	}

	public static synchronized void writeFile(String file, String text) {
		try {
			PrintWriter out = new PrintWriter(new FileWriter(file));
			out.print(text);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static synchronized void writeBinFile(String file, byte[] text) {
		try {
			BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream(file));
			out.write(text);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static synchronized void hexToBinFile(String srcFile, String dstFile) {
		try {
			BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream(dstFile));
			String src = readFile(srcFile);
			out.write(Hex.decode(src));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static synchronized String[] loadAlgorithm(String file) {
		Vector<String> buf = new Vector<String>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			String line = null;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (line.indexOf('=') >= 0) {
					line = line.substring(0, line.indexOf('=')).trim();
					buf.add(line);
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] ret = new String[buf.size()];
		int i = 0;
		for (Iterator<String> itr = buf.iterator(); itr.hasNext();)
			ret[i++] = itr.next();
		return ret;
	}

	public static synchronized String getErrorLogName() {
		Date nowTime = new Date();
		String result = "D://Error log//";
		result += (nowTime.getYear() + 1900);
		String temp = String.valueOf(nowTime.getMonth() + 1);
		if (temp.length() == 1)
			temp = "0" + temp;
		result += temp;
		temp = String.valueOf(nowTime.getDate());
		if (temp.length() == 1)
			temp = "0" + temp;
		result += temp;
		temp = String.valueOf(nowTime.getHours());
		if (temp.length() == 1)
			temp = "0" + temp;
		result += temp;
		temp = String.valueOf(nowTime.getMinutes());
		if (temp.length() == 1)
			temp = "0" + temp;
		result += temp;
		temp = String.valueOf(nowTime.getSeconds());
		if (temp.length() == 1)
			temp = "0" + temp;
		result += temp;
		temp = String.valueOf(nowTime.getTime() % 1000);
		if (temp.length() == 1)
			temp = "00" + temp;
		if (temp.length() == 2)
			temp = "0" + temp;
		result += temp;
		result += "error.log";
		return result;
	}

	public static String getParam(String[] args, String prefix, int offSet) {
		String ret = null;
		for (int i = 0; i < args.length; ++i)
			if (args[i].startsWith(prefix)) {
				ret = args[i];
				ret = ret.substring(ret.indexOf(prefix) + offSet);
				return ret;
			}
		return ret;
	}

	public static synchronized void error(String message) {
		try {
			PrintWriter errorLog = new PrintWriter(new FileWriter(BasicMethod
					.getErrorLogName()));
			errorLog.println(message);
			errorLog.close();
		} catch (IOException e) {
			System.err.println(e.toString());
		}
	}

	public static synchronized String toBitString(int k, int n) {
		StringBuffer bf = new StringBuffer(Integer.toBinaryString(k));
		for (int i = bf.length(); i < n; ++i)
			bf.insert(0, "0");
		return bf.toString();
	}

	public static synchronized String toBitString(long k, int n) {
		StringBuffer bf = new StringBuffer(Long.toBinaryString(k));
		for (int i = bf.length(); i < n; ++i)
			bf.insert(0, "0");
		return bf.toString();
	}

	public static synchronized String toBlankedBitString(int k, int n) {
		StringBuffer ret = new StringBuffer();
		int t = 0;
		while (k > 0) {
			ret.append(" " + (k & 1));
			k >>= 1;
			++t;
		}
		for (int i = t; i < n; ++i)
			ret.append(" 0");
		return ret.reverse().toString();
	}

	public static synchronized void Bits32ToInts(int in, int[] b, int offset) {
		b[offset + 3] = (in & 0xff);
		b[offset + 2] = ((in >>> 8) & 0xff);
		b[offset + 1] = ((in >>> 16) & 0xff);
		b[offset] = ((in >>> 24) & 0xff);
	}

	public static synchronized int IntsTo32bits(int[] b, int i) {
		int rv = 0;

		rv = ((b[i] & 0xff) << 24) | ((b[i + 1] & 0xff) << 16)
				| ((b[i + 2] & 0xff) << 8) | ((b[i + 3] & 0xff));

		return rv;
	}

	public static synchronized void Bits32ToBytes(int in, byte[] b, int offset) {
		b[offset + 3] = (byte) in;
		b[offset + 2] = (byte) (in >>> 8);
		b[offset + 1] = (byte) (in >>> 16);
		b[offset] = (byte) (in >>> 24);
	}

	public static synchronized int BytesTo32bits(byte[] b, int i) {
		return ((b[i] & 0xff) << 24) | ((b[i + 1] & 0xff) << 16)
				| ((b[i + 2] & 0xff) << 8) | ((b[i + 3] & 0xff));
	}

}

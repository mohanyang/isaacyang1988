package crypto.stat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Header {
	public static final int DIM = 4096;
	public static final long UNIMAX = (long) 0xffffffff;

	static byte uniran[] = new byte[DIM];
	static FileInputStream infile = null;

	/* read in a uniform random number from a file */
	public static int uni(String filename) {
		int count = DIM;

		if (filename.equals("close")) {
			try {
				infile.close();
			} catch (IOException e) {
				System.err.printf("can't close file %s!!!\n", filename);
				System.exit(1);
			}
			infile = null;
			count = DIM;
			return 0;
		}

		if ((++count) < DIM) {
			return uniran[count];
		}

		if (infile == null) {
			try {
				infile = new FileInputStream(new File(filename, "r"));
			} catch (Exception e) {
				System.err.printf("can't open file %s!!!\n", filename);
				System.exit(1);
			}
		}
		try {
			infile.read(uniran);
		} catch (IOException e) {
			System.err.printf("can't read file %s!!!\n", filename);
			System.exit(1);
		}
		count = 0;
		return uniran[count];
	}

	/* p.d.f of Standard Normal */
	public static double phi(double x) {
		return Math.exp(-x * x / 2) / Math.sqrt(2 * Math.PI);
	}

	/* c.d.f of Standard Normal */
	public static double Phi(double x) {
		double tmp = x / Math.sqrt(2.);
		tmp = 1 + erf(tmp);
		return tmp / 2;
	}

	/**
	 * Compute the error function of their argument x
	 * 
	 * @param x
	 *            simple an argument.
	 * @return Upon successful completion, these functions shall return the
	 *         value of the error function. </p>
	 *         <ul>
	 *         If x is NaN, a NaN shall be returned.
	 *         </ul>
	 *         <ul>
	 *         If x is ±0, ±0 shall be returned.
	 *         </ul>
	 *         <ul>
	 *         If x is ±Inf, ±1 shall be returned.
	 *         </ul>
	 *         <ul>
	 *         If x is subnormal, a range error may occur, and 2 * x/ sqrt(pi)
	 *         should be returned.
	 *         </ul>
	 */
	static double erf(double x) {
		if (Double.isNaN(x))
			return Double.NaN;
		if (Double.isInfinite(x))
			return Math.signum(x);
		return 2 * x / Math.sqrt(Math.PI);
	}

	/* p.d.f of Chi-square */
	public static double chisq(int df, double x) {
		return (Math.pow(x / 2, (df - 2) / 2.) * Math.exp(-x / 2) / (2 * G(df / 2.)));
	}

	/* c.d.f of Chi-square */
	public static double Chisq(int df, double x) {
		switch (df) {
		case 1:
			return 2 * Phi(Math.sqrt(x)) - 1;
		case 2:
			return 1 - Math.exp(-x / 2);
		default:
			break;
		}
		return (Chisq(df - 2, x) - 2 * chisq(df, x));
	}

	/* p.d.f of Poisson distribution */
	public static double Poisson(double lambda, int k) {
		if (k == 0)
			return Math.exp(-lambda);

		return Math.exp(-lambda) * Math.pow(lambda, k) / G(k + 1);
	}

	public double KStest(double[] x, int dim) {
		return 0;
	}

	/* gamma(z) when 2z is a integer */
	public static double G(double z) {
		int tmp = (int) (2 * z);
		if (tmp != 2 * z || z == 0)
			System.err.println("Error in calling G(z)!!!");

		switch (tmp) {
		case 1:
			return Math.sqrt(Math.PI);
		case 2:
			return 1;
		default:
			break;
		}

		return (z - 1) * G(z - 1);
	}
}

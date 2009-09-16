/**
 * 
 */
package crypto.util;

/**
 * @author Isaac
 * 
 */
public interface HigherOrderFunctionType {
	public int roundFun(int input, long key);

	public int getRound();

	public int getOrder();

	public int getKeyOrder();

	public int getKeyLength();

	public int getL(int input);

	public int getR(int input);

	public int getPreRound(int input);

	public void setState(int s);

	public String getKey();

	public int getPartion();
}

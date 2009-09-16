package crypto.identify;

public interface IIdentify {
	public static final String tmpFile = "./config/crypto.tmp";

	public boolean isSucceed();

	public String getName();
}

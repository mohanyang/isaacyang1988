package crypto.toolkit;

import java.io.PrintWriter;

public interface IToolkit {
	public void init(String[] args) throws Exception;

	public String getName();

	public void perform(PrintWriter log) throws Exception;
}

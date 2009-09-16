package crypto.execution;

import java.io.PrintWriter;

public interface IExecution {
	public void init(Object[] params) throws Exception;

	public String getName();

	public void perform(PrintWriter log) throws Exception;
}

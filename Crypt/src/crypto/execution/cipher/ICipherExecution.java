package crypto.execution.cipher;

import crypto.execution.IExecution;

public interface ICipherExecution extends IExecution {
	public int getTotal();

	public int getCur();

	public void performEncrypt();

	public void performDecrypt();
}

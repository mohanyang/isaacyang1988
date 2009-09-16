package crypto.errormsg;

import java.io.PrintWriter;
import java.io.FileWriter;

public class ErrorMsg {
	private ErrorList errorList = new ErrorList("", null);
	private boolean anyErrors;

	public ErrorMsg() {
		anyErrors = false;
	}

	public void error(String function, String msg) {
		anyErrors = true;
		errorList = new ErrorList("Error in " + function + "\n" + msg,
				errorList);
		System.out.println("Error in " + function + "\n" + msg);
	}

	public void finish() {
		if (anyErrors) {
			try {
				PrintWriter Log = new PrintWriter(new FileWriter(
						"D://Error log//report.txt"));
				// PrintWriter Log = new PrintWriter(new FileWriter(
				// crypto.common.BasicMethod.getErrorLogName()));
				for (ErrorList err = errorList; err != null; err = err.tail)
					Log.println(err.head);
				Log.close();
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
	}
}

class ErrorList {
	String head;
	ErrorList tail;

	ErrorList(String h, ErrorList t) {
		head = h;
		tail = t;
	}
}
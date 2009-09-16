package crypto.ui.test;

import javax.swing.*;
import java.awt.*;

// Example using defaultButton and JRootPane.setDefaultButton()
public class DefaultButtonExample {
	public static void main(String[] args) {
		// Create some buttons
		JButton ok = new JButton("OK");
		JButton cancel = new JButton("Cancel");
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(ok);
		buttonPanel.add(cancel);
		JLabel msg = new JLabel("Is this OK?", JLabel.CENTER);
		// Create a frame, get its root pane, and set the "ok" button as the
		// default. This button will be "pressed" if we hit <ENTER> while the
		// frame has focus.
		JFrame f = new JFrame("111");
		f.addWindowListener(new BasicWindowMonitor());
		JRootPane root = f.getRootPane();
		root.setDefaultButton(ok);
		// Layout and Display
		Container content = f.getContentPane();
		content.setLayout(new BorderLayout());
		content.add(msg, BorderLayout.CENTER);
		content.add(buttonPanel, BorderLayout.SOUTH);
		f.setSize(200, 100);
		f.setVisible(true);
	}
}

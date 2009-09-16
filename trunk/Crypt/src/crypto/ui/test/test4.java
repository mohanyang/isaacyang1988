package crypto.ui.test;

import java.awt.*;
import java.awt.event.*;

import javax.swing.UIManager;

public class test4 extends Frame implements ActionListener {
	Button cutButton, copyButton, pasteButton;

	public test4() {
		super("Toolbar Example (AWT)");
		try {
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
		}
		setSize(450, 250);
		addWindowListener(new BasicWindowMonitor());
		Panel toolbar = new Panel();
		toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));
		cutButton = new Button("Cut");
		cutButton.addActionListener(this);
		toolbar.add(cutButton);
		copyButton = new Button("Copy");
		copyButton.addActionListener(this);
		toolbar.add(copyButton);
		pasteButton = new Button("Paste");
		pasteButton.addActionListener(this);
		toolbar.add(pasteButton);
		// the new "preferred" BorderLayout add call
		add(toolbar, BorderLayout.NORTH);
	}

	public void actionPerformed(ActionEvent ae) {
		System.out.println(ae.getActionCommand());
	}

	public static void main(String args[]) {
		test4 tf1 = new test4();
		tf1.setVisible(true);
	}
}

package crypto.ui.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class test1 {

	private static String labelPrefix = "Number of button clicks: ";
	private static int numClicks = 0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
		}

		JFrame frame = new JFrame("SwingApplication");

		final JLabel label = new JLabel(labelPrefix + "0    ");

		JButton button = new JButton("I'm a Swing button!");
		button.setMnemonic('i');
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numClicks++;
				label.setText(labelPrefix + numClicks);
			}
		});
		label.setLabelFor(button);

		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new GridLayout(2, 1));
		contentPane.add(button);
		contentPane.add(label);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}

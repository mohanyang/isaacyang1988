package crypto.ui.test;

import javax.swing.*;
import java.awt.*;

// Shows how displayedMnemonic and labelFor properties work together
public class MnemonicLabels {
	public static void main(String[] args) {
		// Create labels and text fields
		JLabel lastName = new JLabel("Last Name", JLabel.RIGHT);
		JLabel middleName = new JLabel("Middle Name", JLabel.RIGHT);
		JLabel firstName = new JLabel("First Name", JLabel.RIGHT);
		JTextField lastField = new JTextField(10);
		JTextField middleField = new JTextField(10);
		JTextField firstField = new JTextField(10);
		// Add displayedMnemonic and labelFor property values
		lastName.setDisplayedMnemonic('L');
		middleName.setDisplayedMnemonic('M');
		firstName.setDisplayedMnemonic('F');
		lastName.setLabelFor(lastField);
		middleName.setLabelFor(middleField);
		firstName.setLabelFor(firstField);
		// Layout and Display
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(3, 2, 5, 5));
		p.add(lastName);
		p.add(lastField);
		p.add(middleName);
		p.add(middleField);
		p.add(firstName);
		p.add(firstField);
		JFrame f = new JFrame();
		f.addWindowListener(new BasicWindowMonitor());
		f.setContentPane(p);
		f.pack();
		f.setVisible(true);
	}
}
package crypto.ui.toolkit;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import crypto.ui.ArgManager;

public class UIANFCalc extends BasicToolkit {
	static public final long serialVersionUID = 181;

	public UIANFCalc() {
		super();
	}

	public Component subConstruct() {
		final JLabel inLabel;
		final JTextField inTextField;
		final JButton inButton;
		inLabel = new JLabel("Input file");
		inLabel.setDisplayedMnemonic('I');
		inTextField = new JTextField();
		Font font = new Font("Serif", Font.BOLD, 16);
		inTextField.setFont(font);
		if (ArgManager.get("in") != null) {
			inTextField.setText(ArgManager.get("in"));
		}
		inTextField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				ArgManager.put("in", inTextField.getText());
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				ArgManager.put("in", inTextField.getText());
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				ArgManager.put("in", inTextField.getText());
			}

		});
		inLabel.setLabelFor(inTextField);
		inButton = new JButton("Open input file...");
		inButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser chooser = new JFileChooser();
				int option = chooser.showOpenDialog(parent);
				if (option == JFileChooser.APPROVE_OPTION) {
					if (chooser.getSelectedFile() != null) {
						inTextField.setText(chooser.getSelectedFile()
								.getAbsolutePath());
						ArgManager.put("in", inTextField.getText());
					}
				}
			}
		});
		JPanel mainPanel = new JPanel();
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		mainPanel.setLayout(gridbag);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;

		c.insets = new Insets(2, 2, 3, 3);
		c.gridwidth = 1;
		c.weightx = 1.0;
		gridbag.setConstraints(inLabel, c);
		mainPanel.add(inLabel);
		c.weightx = 8.0;
		gridbag.setConstraints(inTextField, c);
		mainPanel.add(inTextField);
		c.weightx = 4.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(inButton, c);
		mainPanel.add(inButton);
		return mainPanel;
	}
}

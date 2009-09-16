package crypto.ui.toolkit;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import crypto.ui.ArgManager;

public class UIBasicPRNG extends BasicToolkit {
	static public final long serialVersionUID = 182;

	private static String[] styleList = { "0/1", "Hex", "Binary" };

	@Override
	public Component subConstruct() {
		JLabel lengthLabel, outLabel;
		final JTextField lengthTextField, outTextField;
		JButton outButton;
		final JComboBox styleComboBox;

		lengthLabel = new JLabel("Output length");
		outLabel = new JLabel("Output file");

		lengthTextField = new JTextField();
		outTextField = new JTextField();
		Font font = new Font("Serif", Font.BOLD, 16);
		lengthTextField.setFont(font);
		outTextField.setFont(font);

		lengthTextField.getDocument().addDocumentListener(
				new DocumentListener() {
					@Override
					public void changedUpdate(DocumentEvent arg0) {
						ArgManager.put("length", lengthTextField.getText());
					}

					@Override
					public void insertUpdate(DocumentEvent arg0) {
						ArgManager.put("length", lengthTextField.getText());
					}

					@Override
					public void removeUpdate(DocumentEvent arg0) {
						ArgManager.put("length", lengthTextField.getText());
					}

				});
		outTextField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				ArgManager.put("out", outTextField.getText());
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				ArgManager.put("out", outTextField.getText());
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				ArgManager.put("out", outTextField.getText());
			}

		});

		if (ArgManager.get("length") != null) {
			lengthTextField.setText(ArgManager.get("length"));
		}
		if (ArgManager.get("out") != null) {
			outTextField.setText(ArgManager.get("out"));
		}
		lengthLabel.setLabelFor(lengthTextField);
		outLabel.setLabelFor(outTextField);

		outButton = new JButton("Open output file...");
		outButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser chooser = new JFileChooser();
				int option = chooser.showOpenDialog(parent);
				if (option == JFileChooser.APPROVE_OPTION) {
					if (chooser.getSelectedFile() != null) {
						outTextField.setText(chooser.getSelectedFile()
								.getAbsolutePath());
						ArgManager.put("out", outTextField.getText());
					}
				}
			}
		});

		styleComboBox = new JComboBox(styleList);
		styleComboBox.setSelectedIndex(0);
		styleComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if ("comboBoxChanged".equals(event.getActionCommand())) {
					ArgManager.put("style", String.valueOf(styleComboBox
							.getSelectedIndex()));
				}
			}
		});
		if (ArgManager.get("style") == null) {
			ArgManager.put("style", "0");
		} else {
			styleComboBox.setSelectedIndex(Integer.parseInt(ArgManager
					.get("style")));
		}
		JPanel mainPanel = new JPanel();
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		mainPanel.setLayout(gridbag);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;

		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(styleComboBox, c);
		mainPanel.add(styleComboBox);
		c.insets = new Insets(2, 2, 3, 3);

		c.gridwidth = 1;
		c.weightx = 1.0;
		gridbag.setConstraints(lengthLabel, c);
		mainPanel.add(lengthLabel);
		c.weightx = 8.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(lengthTextField, c);
		mainPanel.add(lengthTextField);

		c.gridwidth = 1;
		c.weightx = 1.0;
		gridbag.setConstraints(outLabel, c);
		mainPanel.add(outLabel);
		c.weightx = 8.0;
		gridbag.setConstraints(outTextField, c);
		mainPanel.add(outTextField);
		c.weightx = 4.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(outButton, c);
		mainPanel.add(outButton);
		return mainPanel;
	}
}

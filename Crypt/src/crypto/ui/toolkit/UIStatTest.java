package crypto.ui.toolkit;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import crypto.stat.StatTest;
import crypto.ui.ArgManager;

public class UIStatTest extends BasicToolkit {
	static public final long serialVersionUID = 188;
	static final String[] testName = { "Birthday Spacings",
			"Overlapping Permutations", "Ranks of 31x31 and 32x32 matrices",
			"Ranks of 6x8 Matrices", "Monkey Tests on 20-bit Words",
			"Monkey Tests OPSO,OQSO,DNA", "Count the 1`s in a Stream of Bytes",
			"Count the 1`s in Specific Bytes", "Parking Lot Test",
			"Minimum Distance Test", "Random Spheres Test", "The Sqeeze Test",
			"Overlapping Sums Test", "Runs Test", "The Craps Test",
			"All of the above" };
	static final String[] keyLength = { "64 bit", "128 bit", "256 bit" };

	public UIStatTest() {
		super();
	}

	Boolean autoGen = true;
	Boolean reserveGen = true;

	@Override
	public Component subConstruct() {
		final JPanel algoPanel, testPanel, keyPanel;
		final JComboBox algoComboBox, testComboBox, keyLengthComboBox;
		final JLabel keyLabel;
		final JTextField keyTextField;
		final JButton keyButton;
		final JCheckBox selectKeyCheck, reserveKeyCheck;

		algoPanel = new JPanel();
		algoPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createTitledBorder("Select algorithm"), BorderFactory
				.createEmptyBorder(5, 5, 5, 5)));
		algoComboBox = new JComboBox(StatTest.algorithms);
		algoComboBox.setSelectedIndex(0);
		algoComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if ("comboBoxChanged".equals(event.getActionCommand())) {
					ArgManager.put("algo", algoComboBox.getSelectedItem()
							.toString());
				}
			}
		});
		if (ArgManager.get("algo") == null) {
			ArgManager.put("algo", StatTest.algorithms[0]);
		} else {
			algoComboBox.setSelectedItem(ArgManager.get("algo"));
		}
		algoPanel.setLayout(new BorderLayout());
		algoPanel.add(algoComboBox, BorderLayout.CENTER);

		testPanel = new JPanel();
		testPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createTitledBorder("Select test name"), BorderFactory
				.createEmptyBorder(5, 5, 5, 5)));
		testComboBox = new JComboBox(testName);
		testComboBox.setSelectedIndex(0);
		testComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if ("comboBoxChanged".equals(event.getActionCommand())) {
					ArgManager.put("test", String.valueOf(testComboBox
							.getSelectedIndex()));
				}
			}
		});
		if (ArgManager.get("test") == null) {
			ArgManager.put("test", "0");
		} else {
			testComboBox.setSelectedIndex(Integer.parseInt(ArgManager
					.get("test")));
		}
		testPanel.setLayout(new BorderLayout());
		testPanel.add(testComboBox, BorderLayout.CENTER);

		if (ArgManager.get("auto") == null) {
			ArgManager.put("auto", "true");
			autoGen = true;
			ArgManager.put("reserve", "true");
			reserveGen = true;
		} else {
			autoGen = Boolean.getBoolean(ArgManager.get("auto"));
			reserveGen = Boolean.getBoolean(ArgManager.get("reserve"));
		}

		keyPanel = new JPanel();
		keyPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createTitledBorder("Select key attribute"), BorderFactory
				.createEmptyBorder(5, 5, 5, 5)));

		keyLengthComboBox = new JComboBox(keyLength);
		keyLengthComboBox.setSelectedIndex(0);
		keyLengthComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if ("comboBoxChanged".equals(event.getActionCommand())) {
					ArgManager.put("kenlen", keyLengthComboBox
							.getSelectedItem().toString());
				}
			}
		});
		if (ArgManager.get("kenlen") == null) {
			ArgManager.put("kenlen", keyLength[0]);
		} else {
			algoComboBox.setSelectedItem(ArgManager.get("keylen"));
		}

		selectKeyCheck = new JCheckBox();
		selectKeyCheck.setText("random(click)/manually select(unclick)");
		selectKeyCheck.setSelected(autoGen);

		reserveKeyCheck = new JCheckBox();
		reserveKeyCheck.setText("reserve generated key");
		reserveKeyCheck.setSelected(reserveGen);

		reserveKeyCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				reserveGen = reserveGen ? false : true;
				ArgManager.put("reserve", reserveGen.toString());
			}
		});

		keyLabel = new JLabel("Key file");
		keyLabel.setDisplayedMnemonic('K');
		keyTextField = new JTextField();
		Font font = new Font("Serif", Font.BOLD, 16);
		keyTextField.setFont(font);
		if (ArgManager.get("key") != null) {
			keyTextField.setText(ArgManager.get("key"));
		}
		keyTextField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				ArgManager.put("key", keyTextField.getText());
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				ArgManager.put("key", keyTextField.getText());
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				ArgManager.put("key", keyTextField.getText());
			}
		});
		keyLabel.setLabelFor(keyTextField);
		keyButton = new JButton("Open key file...");
		keyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser chooser = new JFileChooser();
				int option = chooser.showOpenDialog(parent);
				if (option == JFileChooser.APPROVE_OPTION) {
					if (chooser.getSelectedFile() != null) {
						keyTextField.setText(chooser.getSelectedFile()
								.getAbsolutePath());
						ArgManager.put("key", keyTextField.getText());
					}
				}
			}
		});

		selectKeyCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				autoGen = autoGen ? false : true;
				if (autoGen) {
					keyLengthComboBox.setEnabled(true);
					reserveKeyCheck.setEnabled(true);
					keyLabel.setEnabled(false);
					keyTextField.setEnabled(false);
					keyButton.setEnabled(false);
				} else {
					keyLengthComboBox.setEnabled(false);
					reserveKeyCheck.setEnabled(false);
					keyLabel.setEnabled(true);
					keyTextField.setEnabled(true);
					keyButton.setEnabled(true);
				}
			}
		});

		keyPanel.setLayout(new GridLayout(2, 3));
		keyPanel.add(selectKeyCheck);
		keyPanel.add(reserveKeyCheck);
		keyPanel.add(keyLengthComboBox);
		keyPanel.add(keyLabel);
		keyPanel.add(keyTextField);
		keyPanel.add(keyButton);

		if (autoGen) {
			keyLengthComboBox.setEnabled(true);
			reserveKeyCheck.setEnabled(true);
			keyLabel.setEnabled(false);
			keyTextField.setEnabled(false);
			keyButton.setEnabled(false);
		} else {
			keyLengthComboBox.setEnabled(false);
			reserveKeyCheck.setEnabled(false);
			keyLabel.setEnabled(true);
			keyTextField.setEnabled(true);
			keyButton.setEnabled(true);
		}

		JPanel mainPanel = new JPanel();
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		mainPanel.setLayout(gridbag);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(algoPanel, c);
		mainPanel.add(algoPanel);
		gridbag.setConstraints(testPanel, c);
		mainPanel.add(testPanel);
		gridbag.setConstraints(keyPanel, c);
		mainPanel.add(keyPanel);

		return mainPanel;
	}

}

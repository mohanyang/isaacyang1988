package crypto.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class FileSelection extends JFrame {
	static public final long serialVersionUID = 4;

	static String item[] = { "oper", "in", "out", "key" };
	JFrame parent;
	JLabel inLabel, outLabel, keyLabel, ivLabel, statusLabel;
	JTextField inTextField, outTextField, keyTextField, ivTextField;
	JButton inButton, outButton, keyButton, ivButton;
	JButton backButton, nextButton, cancelButton;
	JPanel mainPanel, subPanel;
	String inFile, outFile, keyFile, ivFile, oper;
	String[] config = null;

	private String getDir(String dir) {
		return dir.substring(0, dir.lastIndexOf("\\"));
	}

	public FileSelection() {
		super("Crypto wizard - File Selection");
		HistoryMap.load();
		inFile = HistoryMap.get("InputDir");
		outFile = HistoryMap.get("OutputDir");
		keyFile = HistoryMap.get("KeyDir");
		ivFile = HistoryMap.get("IVDir");
		try {
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
		}
		oper = ArgManager.get("oper");
		parent = this;
		inLabel = new JLabel("Input file");
		outLabel = new JLabel("Output file");
		keyLabel = new JLabel("Key file");
		ivLabel = new JLabel("IV file");
		statusLabel = new JLabel("status");
		statusLabel.setBorder(BorderFactory.createLineBorder(Color
				.decode("#B8CFE5")));
		statusLabel.setToolTipText("current status");
		inLabel.setDisplayedMnemonic('I');
		outLabel.setDisplayedMnemonic('O');
		keyLabel.setDisplayedMnemonic('K');
		ivLabel.setDisplayedMnemonic('V');
		inTextField = new JTextField();
		outTextField = new JTextField();
		keyTextField = new JTextField();
		ivTextField = new JTextField();
		Font font = new Font("Serif", Font.BOLD, 16);
		inTextField.setFont(font);
		outTextField.setFont(font);
		keyTextField.setFont(font);
		ivTextField.setFont(font);
		if (ArgManager.get("in") != null) {
			inTextField.setText(ArgManager.get("in"));
		}
		if (ArgManager.get("out") != null) {
			outTextField.setText(ArgManager.get("out"));
		}
		if (ArgManager.get("key") != null) {
			keyTextField.setText(ArgManager.get("key"));
		}
		if (ArgManager.get("iv") != null) {
			ivTextField.setText(ArgManager.get("iv"));
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
		ivTextField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				ArgManager.put("iv", ivTextField.getText());
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				ArgManager.put("iv", ivTextField.getText());
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				ArgManager.put("iv", ivTextField.getText());
			}

		});

		inLabel.setLabelFor(inTextField);
		outLabel.setLabelFor(outTextField);
		keyLabel.setLabelFor(keyTextField);
		ivLabel.setLabelFor(ivTextField);

		inButton = new JButton("Open input file...");
		outButton = new JButton("Open output file...");
		keyButton = new JButton("Open key file...");
		ivButton = new JButton("Open iv file...");
		backButton = new JButton("Prev");
		backButton.setDisplayedMnemonicIndex(0);
		nextButton = new JButton();
		if (oper.equals("Identify")) {
			nextButton.setText("Ok");
		} else {
			nextButton.setText("Next");
		}
		nextButton.setDisplayedMnemonicIndex(0);
		cancelButton = new JButton("Cancel");
		cancelButton.setDisplayedMnemonicIndex(0);

		inButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser chooser = new JFileChooser(inFile);
				int option = chooser.showOpenDialog(parent);
				if (option == JFileChooser.APPROVE_OPTION) {
					statusLabel.setText("You chose "
							+ ((chooser.getSelectedFile() != null) ? chooser
									.getSelectedFile().getName() : "nothing")
							+ " as input file.");
					if (chooser.getSelectedFile() != null) {
						inTextField.setText(chooser.getSelectedFile()
								.getAbsolutePath());
						ArgManager.put("in", inTextField.getText());
						HistoryMap.put("InputDir", getDir(chooser
								.getSelectedFile().getAbsolutePath()));
					}
				} else {
					statusLabel.setText("You canceled.");
				}
			}
		});

		outButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser chooser = new JFileChooser(outFile);
				int option = chooser.showOpenDialog(parent);
				if (option == JFileChooser.APPROVE_OPTION) {
					statusLabel.setText("You chose "
							+ ((chooser.getSelectedFile() != null) ? chooser
									.getSelectedFile().getName() : "nothing")
							+ " as output file.");
					if (chooser.getSelectedFile() != null) {
						outTextField.setText(chooser.getSelectedFile()
								.getAbsolutePath());
						ArgManager.put("out", outTextField.getText());
						HistoryMap.put("OutputDir", getDir(chooser
								.getSelectedFile().getAbsolutePath()));
					}
				} else {
					statusLabel.setText("You canceled.");
				}
			}
		});

		keyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser chooser = new JFileChooser(keyFile);
				int option = chooser.showOpenDialog(parent);
				if (option == JFileChooser.APPROVE_OPTION) {
					statusLabel.setText("You chose "
							+ ((chooser.getSelectedFile() != null) ? chooser
									.getSelectedFile().getName() : "nothing")
							+ " as key file.");
					if (chooser.getSelectedFile() != null) {
						keyTextField.setText(chooser.getSelectedFile()
								.getAbsolutePath());
						ArgManager.put("key", keyTextField.getText());
						HistoryMap.put("KeyDir", getDir(chooser
								.getSelectedFile().getAbsolutePath()));
					}
				} else {
					statusLabel.setText("You canceled.");
				}
			}
		});

		ivButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser chooser = new JFileChooser(ivFile);
				int option = chooser.showOpenDialog(parent);
				if (option == JFileChooser.APPROVE_OPTION) {
					statusLabel.setText("You chose "
							+ ((chooser.getSelectedFile() != null) ? chooser
									.getSelectedFile().getName() : "nothing")
							+ " as iv file.");
					if (chooser.getSelectedFile() != null) {
						ivTextField.setText(chooser.getSelectedFile()
								.getAbsolutePath());
						ArgManager.put("iv", ivTextField.getText());
						HistoryMap.put("IVDir", getDir(chooser
								.getSelectedFile().getAbsolutePath()));
					}
				} else {
					statusLabel.setText("You canceled.");
				}
			}
		});

		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				parent.dispose();
				new FunctionSelection();
			}
		});

		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (!inTextField.getText().equals("")) {
					ArgManager.put("in", inTextField.getText());
				}
				if (!outTextField.getText().equals("")) {
					ArgManager.put("out", outTextField.getText());
				}
				if (!keyTextField.getText().equals("")) {
					ArgManager.put("key", keyTextField.getText());
				}
				if (!ivTextField.getText().equals("")) {
					ArgManager.put("iv", ivTextField.getText());
				}
				if (oper.equals("Identify")) {
					int n = JOptionPane.showConfirmDialog(parent,
							"This is your parameter :\n"
									+ ArgManager.displayArg()
									+ "Are you sure to procede?", "Crypto",
							JOptionPane.YES_NO_CANCEL_OPTION);
					if (n == JOptionPane.OK_OPTION) {
						try {
							parent.dispose();
							new ProgressViewer(
									new crypto.identify.IdentifyDriver(
											ArgManager.makeArg()));
						} catch (Exception e) {
							e.printStackTrace();
							JOptionPane
									.showMessageDialog(parent,
											"Error occured while testing!",
											"Crypto error",
											JOptionPane.WARNING_MESSAGE);
							parent.dispose();
							System.exit(1);
						}
					}
				} else {
					parent.dispose();
					new AlgorithmSelection();
				}

			}
		});

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int n = JOptionPane.showConfirmDialog(parent,
						"Are you sure to cancel?", "Crypto",
						JOptionPane.YES_NO_CANCEL_OPTION);
				if (n == JOptionPane.OK_OPTION) {
					System.exit(0);
				}
			}
		});

		mainPanel = new JPanel();
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		mainPanel.setLayout(gridbag);
		mainPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createTitledBorder("Select files"), BorderFactory
				.createEmptyBorder(5, 5, 5, 5)));
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

		c.gridwidth = 1;
		c.weightx = 1.0;
		gridbag.setConstraints(keyLabel, c);
		mainPanel.add(keyLabel);
		c.weightx = 8.0;
		gridbag.setConstraints(keyTextField, c);
		mainPanel.add(keyTextField);
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(keyButton, c);
		mainPanel.add(keyButton);

		if (oper.equals("Cipher")) {
			c.gridwidth = 1;
			c.weightx = 1.0;
			gridbag.setConstraints(ivLabel, c);
			mainPanel.add(ivLabel);
			c.weightx = 8.0;
			gridbag.setConstraints(ivTextField, c);
			mainPanel.add(ivTextField);
			c.weightx = 4.0;
			c.gridwidth = GridBagConstraints.REMAINDER;
			gridbag.setConstraints(ivButton, c);
			mainPanel.add(ivButton);
		}

		subPanel = new JPanel();
		subPanel.setLayout(new GridLayout(1, 3));
		subPanel.add(backButton);
		subPanel.add(nextButton);
		subPanel.add(cancelButton);
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(40,
				this.getToolkit().getScreenSize().width / 2 - 300, 0, 0);
		gridbag.setConstraints(subPanel, c);
		mainPanel.add(subPanel);

		setLayout(new BorderLayout());
		add(mainPanel, BorderLayout.CENTER);
		add(statusLabel, BorderLayout.SOUTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		Toolkit theKit = this.getToolkit();
		Dimension wndSize = theKit.getScreenSize();
		setBounds(wndSize.width / 4, wndSize.height / 4, wndSize.width / 2,
				wndSize.height / 2);
		setVisible(true);
	}

	public static void main(String[] args) {
		new FileSelection();
	}

}

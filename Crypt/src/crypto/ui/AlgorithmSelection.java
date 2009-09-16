package crypto.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class AlgorithmSelection extends JFrame {
	static public final long serialVersionUID = 5;
	private static final int DEFAULT = 0, CIPHER = 1, DIGEST = 2, HMAC = 3;

	int mode;
	JFrame parent;

	JPanel algorithmPanel, modePanel, mainPanel, encPanel, subPanel;
	JComboBox algorithmChoices, modeChoices, encChoices;
	JLabel nameLabel;

	JButton backButton, nextButton, cancelButton;

	public AlgorithmSelection() {
		super("Crypto wizard - Algorithm Selection");
		parent = this;
		try {
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
		}
		String oper = ArgManager.get("oper");
		if (oper.equals("Cipher"))
			mode = CIPHER;
		else if (oper.equals("Digest")) {
			if (ArgManager.get("key") == null)
				mode = DIGEST;
			else
				mode = HMAC;
		} else
			mode = DEFAULT;
		algorithmPanel = new JPanel();
		modePanel = new JPanel();
		nameLabel = new JLabel("status");
		nameLabel.setBorder(BorderFactory.createLineBorder(Color
				.decode("#B8CFE5")));
		nameLabel.setToolTipText("current status");
		encPanel = new JPanel();
		subPanel = new JPanel();

		mainPanel = new JPanel();
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		mainPanel.setLayout(gridbag);
		mainPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createTitledBorder("Select detail"), BorderFactory
				.createEmptyBorder(5, 5, 5, 5)));
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;

		algorithmPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Select algorithm"),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		switch (mode) {
		case CIPHER:
			algorithmChoices = new JComboBox(
					crypto.execution.cipher.CipherExecutionDriver.algorithms);
			break;
		case DIGEST:
			algorithmChoices = new JComboBox(
					crypto.execution.digest.DigestExecutionDriver.algorithms);
			break;
		case HMAC:
			algorithmChoices = new JComboBox(
					crypto.execution.HMac.HMacExecutionDriver.algorithms);
			break;
		case DEFAULT:
			break;
		}
		algorithmChoices.setSelectedIndex(0);

		algorithmChoices.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if ("comboBoxChanged".equals(event.getActionCommand())) {
					ArgManager.put("algo", algorithmChoices.getSelectedItem()
							.toString());
					nameLabel.setText("Algorithm "
							+ algorithmChoices.getSelectedItem().toString()
							+ " selected.");
				}
			}
		});
		algorithmPanel.setLayout(new BorderLayout());
		algorithmPanel.add(algorithmChoices, BorderLayout.CENTER);

		if (mode == CIPHER) {
			encPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory
					.createTitledBorder("Select operation"), BorderFactory
					.createEmptyBorder(5, 5, 5, 5)));
			encChoices = new JComboBox(new String[] { "encrypt", "decrypt" });
			encChoices.setSelectedIndex(0);
			encChoices.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					if ("comboBoxChanged".equals(event.getActionCommand())) {
						ArgManager.put("enc", String.valueOf(encChoices
								.getSelectedIndex()));
						nameLabel.setText("Operation "
								+ encChoices.getSelectedItem().toString()
								+ " selected.");
					}
				}
			});
			encPanel.setLayout(new BorderLayout());
			encPanel.add(encChoices, BorderLayout.CENTER);

			modePanel.setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createTitledBorder("Select mode"),
					BorderFactory.createEmptyBorder(5, 5, 5, 5)));
			modeChoices = new JComboBox(
					crypto.execution.cipher.CipherExecution.mode);
			modeChoices.setSelectedIndex(0);
			modeChoices.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					if ("comboBoxChanged".equals(event.getActionCommand())) {
						ArgManager.put("mode", String.valueOf(modeChoices
								.getSelectedIndex()));
						nameLabel.setText("Mode "
								+ modeChoices.getSelectedItem().toString()
								+ " selected.");
					}
				}
			});
			modePanel.setLayout(new BorderLayout());
			modePanel.add(modeChoices, BorderLayout.CENTER);
		}

		if (ArgManager.get("algo") != null) {
			algorithmChoices.setSelectedItem(ArgManager.get("algo"));
		}
		if (mode == CIPHER) {
			if (ArgManager.get("mode") != null) {
				System.out.println(ArgManager.get("mode"));
				modeChoices.setSelectedIndex(Integer.valueOf(ArgManager
						.get("mode")));
			}
			if (ArgManager.get("enc") != null) {
				System.out.println(ArgManager.get("enc"));
				encChoices.setSelectedIndex(Integer.valueOf(ArgManager
						.get("enc")));
			}
		}

		backButton = new JButton("Prev");
		backButton.setDisplayedMnemonicIndex(0);
		nextButton = new JButton("Ok");
		nextButton.setDisplayedMnemonicIndex(0);
		cancelButton = new JButton("Cancel");
		cancelButton.setDisplayedMnemonicIndex(0);

		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				parent.dispose();
				new FileSelection();
			}
		});

		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int n = JOptionPane.showConfirmDialog(parent,
						"This is your parameter :\n" + ArgManager.displayArg()
								+ "Are you sure to procede?", "Crypto",
						JOptionPane.YES_NO_CANCEL_OPTION);
				if (n == JOptionPane.OK_OPTION) {
					try {
						parent.dispose();
						if (mode == CIPHER) {
							new ProgressViewer(
									new crypto.execution.cipher.CipherExecutionDriver(
											ArgManager.makeArg()));
						} else if (mode == DIGEST) {
							new crypto.execution.digest.DigestExecutionDriver(
									ArgManager.makeArg()).run();
							new ResultViewer();
						} else if (mode == HMAC) {
							new crypto.execution.HMac.HMacExecutionDriver(
									ArgManager.makeArg()).run();
							new ResultViewer();
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(parent,
								"Error occured while executing!",
								"Crypto error", JOptionPane.WARNING_MESSAGE);
						parent.dispose();
						System.exit(1);
					}
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

		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(algorithmPanel, c);
		mainPanel.add(algorithmPanel);
		if (mode == 1) {
			c.gridwidth = GridBagConstraints.REMAINDER;
			gridbag.setConstraints(modePanel, c);
			mainPanel.add(modePanel);
			c.gridwidth = GridBagConstraints.REMAINDER;
			gridbag.setConstraints(encPanel, c);
			mainPanel.add(encPanel);
		}

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
		add(nameLabel, BorderLayout.SOUTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		Toolkit theKit = this.getToolkit();
		Dimension wndSize = theKit.getScreenSize();
		setBounds(wndSize.width / 4, wndSize.height / 4, wndSize.width / 2,
				wndSize.height / 2);
		setVisible(true);
	}

	public static void main(String[] args) {
		new AlgorithmSelection();
	}

}

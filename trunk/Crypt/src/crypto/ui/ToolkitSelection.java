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

import crypto.common.Config;
import crypto.common.Lib;

public class ToolkitSelection extends JFrame {
	static public final long serialVersionUID = 18;
	JFrame parent;

	JPanel toolkitPanel, mainPanel, subPanel;
	JComboBox toolkitChoices;
	JLabel nameLabel;
	JButton backButton, nextButton, cancelButton;

	public ToolkitSelection() {
		super("Crypto wizard - Toolkit Selection");
		parent = this;
		try {
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
		}
		toolkitPanel = new JPanel();
		nameLabel = new JLabel("status");
		nameLabel.setBorder(BorderFactory.createLineBorder(Color
				.decode("#B8CFE5")));
		nameLabel.setToolTipText("current status");
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

		toolkitPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createTitledBorder("Select toolkit"), BorderFactory
				.createEmptyBorder(5, 5, 5, 5)));

		toolkitChoices = new JComboBox(crypto.toolkit.ToolkitDriver.algorithms);

		toolkitChoices.setSelectedIndex(0);

		toolkitChoices.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if ("comboBoxChanged".equals(event.getActionCommand())) {
					ArgManager.put("toolkit", toolkitChoices.getSelectedItem()
							.toString());
					nameLabel.setText("Toolkit "
							+ toolkitChoices.getSelectedItem().toString()
							+ " selected.");
				}
			}
		});
		if (ArgManager.get("toolkit") != null) {
			toolkitChoices.setSelectedItem(ArgManager.get("toolkit"));
		} else {
			ArgManager.put("toolkit", toolkitChoices.getSelectedItem()
					.toString());
		}

		toolkitPanel.setLayout(new BorderLayout());
		toolkitPanel.add(toolkitChoices, BorderLayout.CENTER);

		backButton = new JButton("Prev");
		backButton.setDisplayedMnemonicIndex(0);
		nextButton = new JButton("Next");
		nextButton.setDisplayedMnemonicIndex(0);
		cancelButton = new JButton("Cancel");
		cancelButton.setDisplayedMnemonicIndex(0);

		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				parent.dispose();
				new FunctionSelection();
			}
		});

		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				parent.dispose();
				Config.load("./config/toolkit.UI.ini");
				Lib
						.constructObject(Config.getString(ArgManager
								.get("toolkit")));
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
		gridbag.setConstraints(toolkitPanel, c);
		mainPanel.add(toolkitPanel);

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
}

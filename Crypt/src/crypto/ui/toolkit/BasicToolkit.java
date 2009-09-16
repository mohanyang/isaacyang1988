package crypto.ui.toolkit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import crypto.ui.ArgManager;
import crypto.ui.ToolkitSelection;
import crypto.ui.ResultViewer;

public abstract class BasicToolkit extends JFrame {
	static public final long serialVersionUID = 18;
	JFrame parent;

	JPanel toolkitPanel, mainPanel, subPanel;
	JLabel nameLabel;
	JButton backButton, nextButton, cancelButton;

	public BasicToolkit() {
		super("Crypto wizard - Toolkit Parameter Selection");
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
				.createTitledBorder("Select parameter"), BorderFactory
				.createEmptyBorder(5, 5, 5, 5)));

		toolkitPanel.setLayout(new BorderLayout());
		toolkitPanel.add(subConstruct(), BorderLayout.CENTER);
		setTitle("Crypto wizard - " + ArgManager.get("toolkit"));

		backButton = new JButton("Prev");
		backButton.setDisplayedMnemonicIndex(0);
		nextButton = new JButton("OK");
		nextButton.setDisplayedMnemonicIndex(0);
		cancelButton = new JButton("Cancel");
		cancelButton.setDisplayedMnemonicIndex(0);

		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				parent.dispose();
				new ToolkitSelection();
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
						new crypto.toolkit.ToolkitDriver(ArgManager.makeArg())
								.run();
						new ResultViewer();
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(parent,
								"Error occured while testing!", "Crypto error",
								JOptionPane.WARNING_MESSAGE);
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

	public abstract Component subConstruct();
}

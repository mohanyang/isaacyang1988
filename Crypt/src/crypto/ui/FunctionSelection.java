package crypto.ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class FunctionSelection extends JFrame {
	static public final long serialVersionUID = 3;

	JFrame parent;
	JButton identifyButton, cipherButton, digestButton, toolkitButton;
	JPanel mainPanel;

	public FunctionSelection() {
		super("Crypto wizard - Function Selection");
		parent = this;
		try {
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
		}
		identifyButton = new JButton("Identify");
		identifyButton
				.setToolTipText("find out the algorithm of a set of input");
		cipherButton = new JButton("Cipher");
		cipherButton
				.setToolTipText("perform your desired cipher algorithm on a set of input");
		digestButton = new JButton("Digest");
		digestButton
				.setToolTipText("get the digest of a string with your desired algorithm");
		toolkitButton = new JButton("Toolkit");
		toolkitButton.setToolTipText("Cryptography toolkit");

		identifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				ArgManager.clear();
				ArgManager.put("oper", "Identify");
				parent.dispose();
				new FileSelection();
			}
		});

		cipherButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				ArgManager.clear();
				ArgManager.put("oper", "Cipher");
				parent.dispose();
				new FileSelection();
			}
		});

		digestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				ArgManager.clear();
				ArgManager.put("oper", "Digest");
				parent.dispose();
				new FileSelection();
			}
		});

		toolkitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				ArgManager.clear();
				ArgManager.put("oper", "Toolkit");
				parent.dispose();
				new ToolkitSelection();
			}
		});
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2, 1, 5, 5));
		mainPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createTitledBorder("Select operation"), BorderFactory
				.createEmptyBorder(5, 5, 5, 5)));
		mainPanel.add(identifyButton);
		mainPanel.add(cipherButton);
		mainPanel.add(digestButton);
		mainPanel.add(toolkitButton);
		add(mainPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		Toolkit theKit = this.getToolkit();
		Dimension wndSize = theKit.getScreenSize();
		setBounds(wndSize.width / 4, wndSize.height / 4, wndSize.width / 2,
				wndSize.height / 2);
		setVisible(true);
	}

	public static void main(String[] args) {
		new FunctionSelection();
	}
}

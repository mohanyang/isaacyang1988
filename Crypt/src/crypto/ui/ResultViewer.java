package crypto.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class ResultViewer extends JFrame {
	static public final long serialVersionUID = 2;

	JFrame parent;
	JScrollPane scrollPanel;
	JTextArea output;
	JPanel subPanel;
	JButton backButton, finishButton;

	public ResultViewer() {
		super("Crypto execution result");
		parent = this;
		try {
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
		}
		output = new JTextArea();
		output.setAutoscrolls(true);
		output.setEditable(false);
		output.setLineWrap(true);
		output.setText(crypto.common.BasicMethod.readFile("report.txt"));
		output.setToolTipText("crypto execution result");
		output.setSize(400, 200);
		output.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createTitledBorder("Execution result"), BorderFactory
				.createEmptyBorder(5, 5, 5, 5)));

		scrollPanel = new JScrollPane(output);
		scrollPanel.setVisible(true);
		scrollPanel.setWheelScrollingEnabled(true);

		backButton = new JButton("Prev");
		backButton.setDisplayedMnemonicIndex(0);
		finishButton = new JButton("Finish");
		finishButton.setDisplayedMnemonicIndex(0);

		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				parent.dispose();
				String oper = ArgManager.get("oper");
				if (oper.equals("Identify")) {
					new FileSelection();
				} else if (oper.equals("Cipher")) {
					new AlgorithmSelection();
				} else if (oper.equals("Digest")) {
					new AlgorithmSelection();
				} else if (oper.equals("Toolkit")) {
					new ToolkitSelection();
				} else
					new AlgorithmSelection();
			}
		});

		finishButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int n = JOptionPane.showConfirmDialog(parent,
						"Are you sure to finish?", "Crypto",
						JOptionPane.YES_NO_CANCEL_OPTION);
				if (n == JOptionPane.OK_OPTION) {
					System.exit(0);
				}
			}
		});

		subPanel = new JPanel();
		subPanel.setLayout(new GridLayout(1, 2));
		subPanel.add(backButton);
		subPanel.add(finishButton);

		setLayout(new BorderLayout());
		add(scrollPanel, BorderLayout.CENTER);
		add(subPanel, BorderLayout.SOUTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		Toolkit theKit = this.getToolkit();
		Dimension wndSize = theKit.getScreenSize();
		setBounds(wndSize.width / 4, wndSize.height / 4, wndSize.width / 2,
				wndSize.height / 2);
		setVisible(true);
	}

	public static void main(String[] args) {
		new ResultViewer();
	}
}

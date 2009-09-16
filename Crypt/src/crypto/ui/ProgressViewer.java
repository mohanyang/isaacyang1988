package crypto.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;

import crypto.util.DriverUtil;

public class ProgressViewer extends JFrame {
	static public final long serialVersionUID = 1;
	JFrame parent;
	JProgressBar prog;
	JLabel progLabel;
	JPanel mainPanel;
	DriverUtil util;
	int total, cur, cycle;

	public ProgressViewer(DriverUtil _util) throws Exception {
		super("Crypto execution progress");
		parent = this;
		util = _util;
		try {
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
		}
		prog = new JProgressBar();
		prog.setToolTipText("crypto execution progress");
		prog.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createTitledBorder("Execution progress"), BorderFactory
				.createEmptyBorder(0, 0, 0, 0)));

		progLabel = new JLabel("progress");
		progLabel.setBorder(BorderFactory.createLineBorder(Color
				.decode("#B8CFE5")));
		progLabel.setHorizontalAlignment(JLabel.CENTER);

		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(prog, BorderLayout.CENTER);
		mainPanel.add(progLabel, BorderLayout.SOUTH);

		setLayout(new BorderLayout());
		add(mainPanel, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		Toolkit theKit = this.getToolkit();
		Dimension wndSize = theKit.getScreenSize();
		setBounds(wndSize.width / 4, wndSize.height / 5 * 2, wndSize.width / 2,
				wndSize.height / 5);
		setVisible(true);
		total = 1;
		cur = 0;
		cycle = 0;
		process();
	}

	private void process() {
		Thread run = new Thread() {
			public void run() {
				util.run();
				total = util.getTotal();
				do {
					try {
						++cycle;
						if (cycle == 10 && cur == 0) {
							throw new Exception();
						}
						cur = util.getCur();
						int tmp = Integer.valueOf(String
								.valueOf(Math
										.round(Double.valueOf(String
												.valueOf(cur))
												* 100.0
												/ Double.valueOf(String
														.valueOf(total)))));
						prog.setValue(tmp);
						progLabel.setText("progress " + tmp + "%");
						Thread.sleep(100);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(parent,
								"Error occured while executing!",
								"Crypto error", JOptionPane.WARNING_MESSAGE);
						parent.dispose();
						System.exit(1);
					}
				} while (cur != total);
				if (cur == total) {
					prog.setValue(100);
					JOptionPane.showMessageDialog(parent,
							"Crypto execution finished!",
							"Crypto execution successful",
							JOptionPane.WARNING_MESSAGE);
					parent.dispose();
					new ResultViewer();
				}
			}
		};
		run.start();
	}
}

package crypto.ui.test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class ActionExample extends JPanel {
	public JMenuBar menuBar;
	public JToolBar toolBar;

	public ActionExample() {
		super(true);
		// Create a menu bar and give it a bevel border
		menuBar = new JMenuBar();
		menuBar.setBorder(new BevelBorder(BevelBorder.RAISED));
		// Create a menu and add it to the menu bar
		JMenu menu = new JMenu("Menu");
		menuBar.add(menu);

		// Create a toolbar and give it an etched border
		toolBar = new JToolBar();
		toolBar.setBorder(new EtchedBorder());
		// Instantiate a sample action with the NAME property of
		// "Download" and the appropriate SMALL_ICON property
		SampleAction exampleAction = new SampleAction("Download",
				new ImageIcon("action.gif"));
		// Finally, add the sample action to the menu and the toolbar.
		menu.add(exampleAction);
		toolBar.add(exampleAction);
	}

	class SampleAction extends AbstractAction {
		// This is our sample action. It must have an actionPerformed() method,
		// which is called when the action should be invoked.
		public SampleAction(String text, Icon icon) {
			super(text, icon);
		}

		public void actionPerformed(ActionEvent e) {
			System.out.println("Action [" + e.getActionCommand()
					+ "]performed!");
		}
	}

	public static void main(String s[]) {
		ActionExample example = new ActionExample();
		JFrame frame = new JFrame("Action Example");
		frame.addWindowListener(new BasicWindowMonitor());
		frame.setJMenuBar(example.menuBar);
		frame.getContentPane().add(example.toolBar, BorderLayout.NORTH);
		frame.setSize(200, 200);
		frame.setVisible(true);
	}
}

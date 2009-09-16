package crypto.ui.test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class test5 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Test Frame");
		
		JButton button = new JButton("Press Me!"); // JButton extends JComponent
		button.setToolTipText("Go Ahead!");
		System.out.println(button.getToolTipText());
		
		frame.add(button);
		frame.setBounds(20, 20, 200, 200);
		frame.setVisible(true);
		Rectangle r = new Rectangle();
		r = frame.getBounds(r);
	}

}

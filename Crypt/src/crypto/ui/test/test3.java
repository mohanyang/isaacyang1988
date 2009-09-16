/**
 * 
 */
package crypto.ui.test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Isaac
 * 
 */
public class test3 extends JFrame {

	final static int NUM_IMAGES = 8;
	final static int START_INDEX = 3;

	JPanel selectPanel = null;
	JPanel displayPanel = null;
	JPanel mainPanel = null;
	JComboBox phaseChoices = null;
	JLabel nameLabel = null;

	/**
	 * @throws HeadlessException
	 */
	public test3() {
		super("LunarPhases");
		selectPanel = new JPanel();
		displayPanel = new JPanel();
		nameLabel = new JLabel("No file");
		displayPanel.add(nameLabel);

		// Create the main panel to contain the two subpanels.
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2, 1, 5, 5));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		// Add the select and display panels to the main panel.
		mainPanel.add(selectPanel);
		mainPanel.add(displayPanel);

		selectPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createTitledBorder("Select Phase"), BorderFactory
				.createEmptyBorder(5, 5, 5, 5)));

		phaseChoices = new JComboBox(
				crypto.execution.cipher.CipherExecutionDriver.algorithms);
		phaseChoices.setSelectedIndex(START_INDEX);
		phaseChoices.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if ("comboBoxChanged".equals(event.getActionCommand())) {
					// update the icon to display the new phase
					nameLabel
							.setText(phaseChoices.getSelectedItem().toString());
				}
			}
		});
		selectPanel.add(phaseChoices);
		this.add(mainPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		test3 tt = new test3();
	}

}

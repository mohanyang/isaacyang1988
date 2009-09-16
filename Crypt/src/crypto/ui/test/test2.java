package crypto.ui.test;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class test2 extends JFrame {

	JTextField tempCelsius = null;
	JLabel celsius = null;
	JLabel fahrenheitLabel = null;
	JButton convertTemp = null;

	public test2() {
		super("CelsiusConverter");
		tempCelsius = new JTextField(5);
		celsius = new JLabel("Celsius");
		fahrenheitLabel = new JLabel("Fahrenheit");
		convertTemp = new JButton("Convert...");
		convertTemp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// Parse degrees Celsius as a double and convert to Fahrenheit.
				int tempFahr = (int) ((Double
						.parseDouble(tempCelsius.getText())) * 1.8 + 32);
				// Set fahrenheitLabel to new value and font color based on
				// temperature.
				if (tempFahr <= 32) {
					fahrenheitLabel.setText("<html><font color=blue>"
							+ tempFahr + "&#176  Fahrenheit </font></html>");

				} else if (tempFahr <= 80) {
					fahrenheitLabel.setText("<html><font color=green>"
							+ tempFahr + "&#176  Fahrenheit </font></html>");
				} else {
					fahrenheitLabel.setText("<html><font color=red>" + tempFahr
							+ "&#176  Fahrenheit </font></html>");
				}

			}
		});
		this.setSize(400, 400);
		getRootPane().setDefaultButton(convertTemp);
		Container con = this.getContentPane();
		con.setLayout(new GridLayout(2, 2));
		con.add(tempCelsius);
		con.add(celsius);
		con.add(convertTemp);
		con.add(fahrenheitLabel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		test2 tt = new test2();
	}
}

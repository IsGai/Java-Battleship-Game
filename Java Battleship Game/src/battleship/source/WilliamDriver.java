package battleship.source;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class WilliamDriver implements ActionListener{
	static JFrame test = new JFrame("Battle Shets");
	public static void main(String[] args) {
		test.setSize(500,500);
		test.setVisible(true);
		test.getContentPane().setLayout(new GridLayout(1,1,0,0));
		
		TitleScreen ts = new TitleScreen();
		test.add(ts);
		
		
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.requestFocus();
		test.toFront();
		test.setVisible(true);
		test.setMinimumSize(new Dimension(450, 300));
		//test.setResizable(false);
	}

	public void actions(Object source, String command) {
	}
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getSource().toString());
	}
}

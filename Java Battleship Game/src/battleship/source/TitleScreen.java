package battleship.source;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class TitleScreen extends JPanel implements ActionListener{

	private JButton blankButton = new JButton("");
	private JButton playButton = new JButton("Play Button");
	private JButton optionsButton = new JButton("Options Button");
	private JButton creditsButton = new JButton("Credits Button");
	private JButton quitButton = new JButton("Quit Button");
	private JLabel battleshipLogo = new JLabel( new ImageIcon(getClass().getResource("Images\\BattleshipLogo.png")));
	
	public TitleScreen() {
		//titlescreen main values
		//this.setSize(500,500);
		this.setBackground(Color.black);
		//this.setVisible(true);
		this.setLayout(new GridBagLayout());
		
		//default GridBagConstraint values
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		
		//other gui componenets
		otherGUIComponents();
		//adding buttons
		c.insets = new Insets(0,0,0,0);
		c.weightx=0.9;
		c.gridy=0;
		this.add(battleshipLogo, c);
		c.weightx=1.0;
		
		c.insets = new Insets(1,25,25,25);
		c.gridy=1;
		this.add(playButton, c);
		
		c.insets = new Insets(0,50,25,50);
		c.gridy=2;
		this.add(optionsButton, c);
		
		c.gridy=3;
		this.add(creditsButton, c);
		
		c.gridy=4;
		c.insets = new Insets(0,100,25,100);
		this.add(quitButton, c);
		this.setMinimumSize(new Dimension(500,500));
		this.setPreferredSize(new Dimension(500,500));
		this.setMaximumSize(new Dimension(500,500));
	}
	public void otherGUIComponents() {
		playButton.addActionListener(this);
		optionsButton.addActionListener(this);
		creditsButton.addActionListener(this);
		quitButton.addActionListener(this);
	}
	public void actions(Object source, String command) {
		if(source == playButton) {
			
		}
		if(source == optionsButton) {
			
		}
		if(source == creditsButton) {
			System.out.println(command);
		}
		if(source == quitButton) {
			
		}
	}
	public void actionPerformed(ActionEvent e) {
		actions(e.getSource(), e.getActionCommand());
	}

}
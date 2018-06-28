/**
 * MAKE CONSTANTS FOR SOME VARIABLES
 * DEFINE SOME NUMBERS INTO VARIABLES FOR READIBILITY
 * 
 * THINGS TO DO
 * -make BattleSetupScreen
 * -make BattleScreen
 * -finish adding options to OptionsScreen
 * -make CreditScreen look fancy
 * 
 */
package battleship.source;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class TitleScreen extends JFrame implements ActionListener{
	public static void main(String[] args) {
		new TitleScreen();
	}
	private static final Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
	
	private JLabel battleshipLogo = new JLabel(new ImageIcon(getClass().getResource("Images\\BattleshipLogo.png")));
	private JButton playButton = new JButton("Play Button");
	private JButton optionsButton = new JButton("Options Button");
	private JButton creditsButton = new JButton("Credits Button");
	private JButton quitButton = new JButton("Quit Button");
	
	GridBagConstraints c = new GridBagConstraints();
	private TitleScreen() {
		//titlescreen main values
		this.setUndecorated(true);//removes JFRAME border
		this.setSize(500,500);
		this.setIconImage(new ImageIcon(getClass().getResource("Images\\BattleshipIcon.png")).getImage());
		this.setTitle("Battle Ship");
		this.getContentPane().setBackground(Color.black);
		
		//intialization
		otherGUIComponents();
		mainMenu();
		//size
		/*
		this.setMinimumSize(new Dimension(500,500));
		this.setPreferredSize(new Dimension(500,500));
		this.setMaximumSize(new Dimension(500,500));
		*/
		//center GUI
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation((screenDimension.width - this.getSize().width) /2, 
				(screenDimension.height - this.getSize().height) /2);
		this.setResizable(false);
	}
	private void otherGUIComponents() {
		playButton.addActionListener(this);
		optionsButton.addActionListener(this);
		creditsButton.addActionListener(this);
		quitButton.addActionListener(this);
	}
	private void actions(Object source, String command) {
		if(source == playButton) {
			playMenu();
		}
		if(source == optionsButton) {
			optionsMenu();
		}
		if(source == creditsButton) {
			creditsMenu();
		}
		if(source == quitButton) {
			this.dispose();
		}
	}
	public void mainMenu() {
		this.getContentPane().removeAll();
		this.repaint();
		this.getContentPane().setLayout(new GridBagLayout());
		
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0,0,0,0);
		c.weightx=0.9;
		c.gridy=0;
		Image t = ((ImageIcon)battleshipLogo.getIcon()).
				getImage().getScaledInstance(this.getWidth(),this.getHeight()/5, Image.SCALE_DEFAULT);
		battleshipLogo.setIcon(new ImageIcon(t));
		this.add(battleshipLogo, c);
		c.weightx=1.0;
		c.insets = new Insets((int)(this.getHeight()/500),(int)(this.getWidth()/20),(int)(this.getHeight()/20),(int)(this.getWidth()/20));
		c.gridy=1;
		this.add(playButton, c);
		
		c.insets = new Insets(0,(int)(this.getWidth()/10),(int)(this.getHeight()/20),(int)(this.getWidth()/10));
		c.gridy=2;
		this.add(optionsButton, c);
		c.gridy=3;
		this.add(creditsButton, c);
		c.gridy=4;
		c.insets = new Insets(0,(int)(this.getWidth()/5),(int)(this.getHeight()/20),(int)(this.getWidth()/5));
		this.add(quitButton, c);
		
		//button fonts
		Font buttonFont = new Font("Arial", Font.PLAIN, (int)(this.getHeight()/20));
		playButton.setFont(buttonFont);
		optionsButton.setFont(buttonFont);
		creditsButton.setFont(buttonFont);
		quitButton.setFont(buttonFont);
		this.validate();
	}
	public void changeBackground(Color newColor) {
		this.getContentPane().setBackground(newColor);
	}
	public void changeBackground(int r, int g, int b) {
		this.getContentPane().setBackground(new Color(r, g, b));
	}
	public void changeSize(Dimension newSize) {
		this.setSize(newSize);
		this.setLocation((screenDimension.width - newSize.width) /2, 
				(screenDimension.height - newSize.height) /2);
	}
	private void optionsMenu() {
		this.getContentPane().removeAll();
		this.repaint();
		this.getContentPane().setLayout(new BorderLayout());
		
		this.add(new OptionsScreen(this));
		
		this.validate();
	}
	private void creditsMenu() {
		this.getContentPane().removeAll();
		this.repaint();
		this.getContentPane().setLayout(new BorderLayout());
		
		this.add(new CreditsScreen(this));
		
		this.validate();
	}
	private void playMenu() {
		this.getContentPane().removeAll();
		this.repaint();
		this.getContentPane().setLayout(new BorderLayout());
		
		this.add(new BattleMap(this));
		
		this.validate();
	}
	public void playMenuStep2(JPanel shipSelectGrid) {
		this.getContentPane().removeAll();
		this.repaint();
		this.getContentPane().setLayout(new BorderLayout());
		this.add(shipSelectGrid);
		this.validate();
	}
	public void addScreen(Component screen) {
		this.getContentPane().removeAll();
		this.repaint();
		this.getContentPane().setLayout(new BorderLayout());
		
		this.add(screen);
		
		this.validate();
	}
	public void actionPerformed(ActionEvent e) {
		actions(e.getSource(), e.getActionCommand());
	}

}
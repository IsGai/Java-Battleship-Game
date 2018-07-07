package battleship.source;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class BattleScreen extends JPanel{
	//MenuBar
	private JPanel menuBarPanel = new JPanel();
	private JMenuBar menuBar = new JMenuBar();
	private JMenu file = new JMenu("Game");
	private JMenuItem saveAndReturn = new JMenuItem("Save and Return to Main Menu");
	private JMenuItem returnNoSave = new JMenuItem("Return to Main Menu");
	private JMenuItem saveAndQuit = new JMenuItem("Save and Quit");
	private JMenuItem quitNoSave = new JMenuItem("Quit");
	//playerScreen
	private JPanel playerScreen = new JPanel();
	private JPanel ptop = new JPanel(); 
	private JLabel plabel = new JLabel("PLAYER");
	private JPanel consolePanel = new JPanel();
	private JTextArea console = new JTextArea();
	private JScrollPane consolePane = new JScrollPane(console);
	//enemyScreen
	private JPanel enemyScreen = new JPanel();
	private JPanel etop = new JPanel();
	private JLabel elabel = new JLabel("ENEMY");
	private JPanel efire = new JPanel();
	private JLabel xlabel = new JLabel("X: ");
	private JTextField xcoord = new JTextField(10);
	private JLabel ylabel = new JLabel("Y: ");
	private JTextField ycoord = new JTextField(10);
	private JButton fireButton = new JButton("FIRE");
	//
	private TitleScreen ts;
	private BattleMap playerMap;
	private BattleMap enemyMap;
	public BattleScreen(TitleScreen ts, int rows, int columns) {
		this.ts = ts;
		playerMap = new BattleMap(ts, rows, columns);
		enemyMap = new BattleMap(ts, rows, columns);

		setBackground(null);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		adjustToSettings();
		setupMenuBar();
		playerScreenInitialize();
		enemyScreenIntialize();
		
		//initial GUI setup, later re-added with changeScreen()
		add(menuBarPanel);
		add(playerScreen);
	}
	private void setupMenuBar() {
		//components
		menuBarPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		menuBarPanel.add(menuBar);
		menuBar.add(file);
		file.add(saveAndReturn);
		file.add(returnNoSave);
		file.add(saveAndQuit);
		file.add(quitNoSave);
		//listeners
		saveAndReturn.addActionListener(new MenuBarActionListener());
		returnNoSave.addActionListener(new MenuBarActionListener());
		saveAndQuit.addActionListener(new MenuBarActionListener());
		quitNoSave.addActionListener(new MenuBarActionListener());
		//
		menuBarPanel.setBackground(null);
	}
	//change font colors to opposite to background color
	//adjust font sizes with current game height
	//set component backgrounds to null
	private void adjustToSettings(){
		playerScreen.setBackground(null);
		ptop.setBackground(null);
		plabel.setBackground(null);
		
		enemyScreen.setBackground(null);
		etop.setBackground(null);
		elabel.setBackground(null);
		efire.setBackground(null);
		
		plabel.setFont(ts.fontResize(25));
		plabel.setForeground(TitleScreen.fontLabelColor);
		elabel.setFont(ts.fontResize(25));
		elabel.setForeground(TitleScreen.fontLabelColor);
		
		console.setFont(ts.fontResize(45));//in playerScreen
		
		xlabel.setFont(ts.fontResize(25));
		xlabel.setForeground(TitleScreen.fontLabelColor);
		ylabel.setFont(ts.fontResize(25));
		ylabel.setForeground(TitleScreen.fontLabelColor);
	}
	public void playerScreenInitialize() {
		GridBagConstraints c = new GridBagConstraints();
		playerScreen.setLayout(new GridBagLayout());
		//add top
		ptop.add(plabel);
		//add console panel
		consolePanel.setBackground(null);
		consolePanel.setLayout(new BorderLayout());
		consolePane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		consolePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		//console.setEditable(false); //turned off for testing
		
		consolePanel.add(consolePane);
		
		
		//add to playerScreen
		c.gridwidth = 2;
		c.weightx = 0.95;
		c.weighty = 0.05;
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.PAGE_START;
		playerScreen.add(ptop, c); //top
		
		c.anchor = GridBagConstraints.CENTER; //reset anchor
		c.weightx = 0.8;
		c.weighty = 0.85;
		c.gridx = 0;
		c.gridy = 1;
		playerScreen.add(playerMap, c); //middle
		
		c.gridwidth = 1;
		c.weightx = 0.999;
		c.weighty = 0.1;
		c.gridx = 0;
		c.gridy = 2;
		playerScreen.add(consolePanel,c); //bottom
		
		c.anchor = GridBagConstraints.SOUTHEAST;
		c.weightx = 0.001;
		c.weighty = 0.1;
		c.gridx = 1;
		c.gridy = 2;
		
		JButton changeScreen = new JButton();
		playerScreen.add(changeScreen, c);
		
		//rezise image
		int newImageSize = 0;
		if(changeScreen.getPreferredSize().width > changeScreen.getPreferredSize().height)
			newImageSize = changeScreen.getPreferredSize().width;
		else 
			newImageSize = changeScreen.getPreferredSize().height;
		Image t = new ImageIcon(getClass().getResource("Images\\Enemy.png")).getImage()
				.getScaledInstance(newImageSize, newImageSize, Image.SCALE_DEFAULT);
		
		changeScreen.setIcon(new ImageIcon(t));
		changeScreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeScreen(enemyScreen);
			}
		});
	}
	public void enemyScreenIntialize(){
		GridBagConstraints c = new GridBagConstraints();
		enemyScreen.setLayout(new GridBagLayout());
		//add top
		etop.add(elabel);
		JPanel ex = new JPanel();
		ex.setBackground(null);
		ex.add(xlabel);
		ex.add(xcoord);
		JPanel ey = new JPanel();
		ey.setBackground(null);
		ey.add(ylabel);
		ey.add(ycoord);
		efire.setLayout(new GridLayout(1,3));
		efire.add(ex);
		efire.add(ey);
		efire.add(fireButton);

		c.gridwidth = 2;
		c.weightx = 1.0;
		c.weighty = 0.05;
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.PAGE_START;
		enemyScreen.add(etop, c);
		
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 1.0;
		c.weighty = 0.85;
		c.gridx = 0;
		c.gridy = 1;
		enemyScreen.add(enemyMap, c);
		
		c.gridwidth = 1;
		c.weightx = 0.999;
		c.weighty = 0.1;
		c.gridx = 0;
		c.gridy = 2;
		enemyScreen.add(efire,c);
		
		c.anchor = GridBagConstraints.SOUTHEAST;
		c.weightx = 0.001;
		c.weighty = 0.1;
		c.gridx = 1;
		c.gridy = 2;
		JButton changeScreen = new JButton();
		enemyScreen.add(changeScreen, c);
		
		//resize image
		int newImageSize = 0;
		if(changeScreen.getPreferredSize().width > changeScreen.getPreferredSize().height)
			newImageSize = changeScreen.getPreferredSize().width;
		else 
			newImageSize = changeScreen.getPreferredSize().height;
		Image t = new ImageIcon(getClass().getResource("Images\\Player.png")).getImage()
				.getScaledInstance(newImageSize, newImageSize, Image.SCALE_DEFAULT);
		
		changeScreen.setIcon(new ImageIcon(t));
		changeScreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeScreen(playerScreen);
			}
		});
	}
	private void changeScreen(JPanel screen) {
		removeAll();
		repaint();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));	

		add(menuBarPanel);
		add(screen);
		
		validate();
	}
	//display information to the console on player screen
	private void writeToConsole(String text) {
		console.append(text + "\n");
	}
	private class MenuBarActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == saveAndReturn) {
				//SAVE BATTLE DATA
				// add method to here
				JOptionPane.showMessageDialog(ts.getContentPane(),
						"Save function currently N/A", "Save and Quit", JOptionPane.PLAIN_MESSAGE);
				ts.mainMenu();
			}
			if(e.getSource() == returnNoSave) {
				int choice = JOptionPane.showConfirmDialog(ts.getContentPane(), "Are you sure?", "Return to Main Menu", JOptionPane.YES_NO_OPTION);
				if(choice==JOptionPane.YES_OPTION) ts.mainMenu();
			}
			if(e.getSource() == saveAndQuit) {
				//SAVE BATTLE DATA
				// add method to here
				JOptionPane.showMessageDialog(ts.getContentPane(),
						"Save function currently N/A", "Save and Quit", JOptionPane.PLAIN_MESSAGE);
				ts.dispose();
			}
			if(e.getSource() == quitNoSave) {
				int choice = JOptionPane.showConfirmDialog(ts.getContentPane(), "Are you sure?", "Quit", JOptionPane.YES_NO_OPTION);
				if(choice==JOptionPane.YES_OPTION) ts.dispose();
			}
		}
	}
}
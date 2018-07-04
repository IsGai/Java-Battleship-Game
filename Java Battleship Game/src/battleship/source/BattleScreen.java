<<<<<<< HEAD
package battleship.source;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFrame;

public class BattleScreen extends JPanel implements MouseListener{
	//playerScreen
	private JPanel playerScreen = new JPanel();
	private JPanel ptop = new JPanel(); 
	private JPanel pships= new JPanel();
	//private JPanel pgrid = new JPanel();
	private JPanel pfire = new JPanel();
	//enemyScreen
	private JPanel enemyScreen = new JPanel();
	private JPanel etop = new JPanel();
	private JPanel eships = new JPanel();
	//private JPanel egrid = new JPanel();
	private JPanel efire = new JPanel();
	JLabel xlabel = new JLabel("X: ");
	JTextField xcoord = new JTextField(10);
	JLabel ylabel = new JLabel("Y: ");
	JTextField ycoord = new JTextField(10);
	JButton fireButton = new JButton("FIRE");
	//
	private JPanel dock = new JPanel(new GridLayout(10,2));
	private TitleScreen ts;
	private BattleMap playerMap;
	private BattleMap enemyMap;
	public BattleScreen(TitleScreen ts, int rows, int columns) {
		this.ts = ts;
		playerMap = new BattleMap(ts, rows, columns);
		enemyMap = new BattleMap(ts, rows, columns);

		setBackground(null);
		setLayout(new BorderLayout());
		
		playerScreenInitialize();
		enemyScreenIntialize();
		addMouseListener(this);
		add(playerScreen);
	}
	private void changeScreen(JPanel screen) {
		removeAll();
		repaint();
		setLayout(new BorderLayout());	
		
		add(screen);
		
		validate();
	}
	public void playerScreenInitialize() {
		GridBagConstraints c = new GridBagConstraints();
		playerScreen.setLayout(new GridBagLayout());
		//add top
		ptop.add(new JLabel("PLAYER"));
		//add pfire coordinates
		pfire.add(xlabel);
		pfire.add(xcoord);
		pfire.add(ylabel);
		pfire.add(ycoord);
		pfire.add(fireButton);
		//add to playerScreen
		c.gridwidth = 2;
		c.weightx = 0.95;
		c.weighty = 0.05;
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.PAGE_START;
		playerScreen.add(ptop, c);
		c.anchor = GridBagConstraints.CENTER; //reset anchor
		c.weightx = 0.8;
		c.weighty = 0.85;
		c.gridx = 0;
		c.gridy = 1;
		playerScreen.add(playerMap, c);
		
		c.gridwidth = 1;
		c.weightx = 0.95;
		c.weighty = 0.1;
		c.gridx = 0;
		c.gridy = 2;
		playerScreen.add(pfire,c);
		
		c.anchor = GridBagConstraints.SOUTHEAST;
		c.weightx = 0.05;
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
		etop.add(new JLabel("ENEMY"));
		//add pfire coordinates
		JLabel xlabel = new JLabel("X: ");
		JTextField xcoord = new JTextField(10);
		JLabel ylabel = new JLabel("Y: ");
		JTextField ycoord = new JTextField(10);
		JButton fireButton = new JButton("FIRE");
		efire.add(xlabel);
		efire.add(xcoord);
		efire.add(ylabel);
		efire.add(ycoord);
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
		c.weightx = 0.95;
		c.weighty = 0.1;
		c.gridx = 0;
		c.gridy = 2;
		enemyScreen.add(efire,c);
		
		c.anchor = GridBagConstraints.SOUTHEAST;
		c.weightx = 0.05;
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
	public void mouseActions(Object source) {
		System.out.println(source);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		mouseActions(e.getLocationOnScreen());
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
=======
package battleship.source;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFrame;

public class BattleScreen extends JPanel {
	//playerScreen
	private JPanel playerScreen = new JPanel();
	private JPanel ptop = new JPanel(); 
	private JPanel pships= new JPanel();
	//private JPanel pgrid = new JPanel();
	private JPanel pfire = new JPanel();
	//enemyScreen
	private JPanel enemyScreen = new JPanel();
	private JPanel etop = new JPanel();
	private JPanel eships = new JPanel();
	//private JPanel egrid = new JPanel();
	private JPanel efire = new JPanel();
	//
	private JPanel dock = new JPanel(new GridLayout(10,2));
	private TitleScreen ts;
	private BattleMap playerMap;
	private BattleMap enemyMap;
	public BattleScreen(TitleScreen ts, int rows, int columns) {
		this.ts = ts;
		playerMap = new BattleMap(ts, rows, columns);
		enemyMap = new BattleMap(ts, rows, columns);
		setBackground(null);
		setLayout(new BorderLayout());
		
		playerScreenInitialize();
		enemyScreenIntialize();
		
		add(playerScreen);
	}
	private void changeScreen(JPanel screen) {
		removeAll();
		repaint();
		setLayout(new BorderLayout());	
		
		add(screen);
		
		validate();
	}
	public void playerScreenInitialize() {
		GridBagConstraints c = new GridBagConstraints();
		playerScreen.setLayout(new GridBagLayout());
		//add top
		ptop.add(new JLabel("PLAYER"));
		//add pfire coordinates
		JLabel xlabel = new JLabel("X: ");
		JTextField xcoord = new JTextField(10);
		JLabel ylabel = new JLabel("Y: ");
		JTextField ycoord = new JTextField(10);
		JButton fireButton = new JButton("FIRE");
		pfire.add(xlabel);
		pfire.add(xcoord);
		pfire.add(ylabel);
		pfire.add(ycoord);
		pfire.add(fireButton);

		c.gridwidth = 2;
		c.weightx = 0.95;
		c.weighty = 0.05;
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.PAGE_START;
		playerScreen.add(ptop, c);
		c.anchor = GridBagConstraints.CENTER; //reset anchor
		c.weightx = 0.8;
		c.weighty = 0.85;
		c.gridx = 0;
		c.gridy = 1;
		playerScreen.add(playerMap, c);
		
		c.gridwidth = 1;
		c.weightx = 0.95;
		c.weighty = 0.1;
		c.gridx = 0;
		c.gridy = 2;
		playerScreen.add(pfire,c);
		
		c.anchor = GridBagConstraints.SOUTHEAST;
		c.weightx = 0.05;
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
		etop.add(new JLabel("ENEMY"));
		//add pfire coordinates
		JLabel xlabel = new JLabel("X: ");
		JTextField xcoord = new JTextField(10);
		JLabel ylabel = new JLabel("Y: ");
		JTextField ycoord = new JTextField(10);
		JButton fireButton = new JButton("FIRE");
		efire.add(xlabel);
		efire.add(xcoord);
		efire.add(ylabel);
		efire.add(ycoord);
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
		c.weightx = 0.95;
		c.weighty = 0.1;
		c.gridx = 0;
		c.gridy = 2;
		enemyScreen.add(efire,c);
		
		c.anchor = GridBagConstraints.SOUTHEAST;
		c.weightx = 0.05;
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
>>>>>>> 7c855b6fb5d389cfb0b58b1a8f058c5438757c93
}
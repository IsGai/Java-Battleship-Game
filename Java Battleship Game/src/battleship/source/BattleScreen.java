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
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

public class BattleScreen extends JPanel {
	// MenuBar
	private JPanel menuBarPanel = new JPanel();
	private JMenuBar menuBar = new JMenuBar();
	private JMenu file = new JMenu("Game");
	private JMenuItem saveAndReturn = new JMenuItem("Save and Return to Main Menu");
	private JMenuItem returnNoSave = new JMenuItem("Return to Main Menu");
	private JMenuItem saveAndQuit = new JMenuItem("Save and Quit");
	private JMenuItem quitNoSave = new JMenuItem("Quit");
	// playerScreen
	private JPanel playerScreen = new JPanel();
	private JPanel ptop = new JPanel();
	private JLabel plabel = new JLabel("PLAYER");
	private JPanel consolePanel = new JPanel();
	private JTextArea console = new JTextArea();
	private JScrollPane consolePane = new JScrollPane(console);
	// enemyScreen
	private JPanel enemyScreen = new JPanel();
	private JPanel etop = new JPanel();
	private JLabel elabel = new JLabel("ENEMY");
	private JPanel efire = new JPanel();
	private JLabel xlabel = new JLabel("X: ");
	private JTextField xcoord = new JTextField(10);
	private JLabel ylabel = new JLabel("Y: ");
	private JTextField ycoord = new JTextField(10);
	private JButton fireButton = new JButton("FIRE");

	// player and enemy maps with buttons
	private JPanel playerMap;
	private JPanel enemyMap;
	private JButton[][] playerMapButtons; // all the player buttons(mapTiles)
	private JButton[][] enemyMapButtons; // all the enemy buttons(mapTiles)
	private boolean onEnemyScreen = false; // starts off on playerScreen

	// references
	private TitleScreen ts;
	private BattleData playerData;
	private BattleMechanics bm;

	// global game variables
	private ExecutorService threadManager;
	private int turnCounter = 0;
	
	private JScrollPane scrollPanePlayerMap;
	private JScrollPane scrollPaneEnemyMap;
	private boolean screenDimensionTooBig = false;

	public BattleScreen(TitleScreen ts, BattleMechanics bm, BattleData playerData, boolean saveLoaded,
			Dimension screenDimension, String consoleText, int turnCount) {
		this.ts = ts;
		this.bm = bm;
		this.playerData = playerData;
		// threadManager
		// runs playerThread(playerTurn), waits until done, then runs
		// enemyThread(enemyTurn)
		threadManager = Executors.newFixedThreadPool(1);
		setBackground(null);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// all for PLAYER and ENEMY map
		// playerMapButtons = new JButton[columns][rows];
		// enemyMapButtons = new JButton[columns][rows];
		createPlayerMap(playerData.getSizeX(), playerData.getSizeY());
		createEnemyMap(playerData.getSizeX(), playerData.getSizeY());
		
		adjustToSettings(screenDimension);
		setupMenuBar();
		playerScreenInitialize();
		enemyScreenIntialize();
		declareComponentsTo();
		if (saveLoaded)
			setMaps(consoleText, turnCount);

		// initial GUI setup, later re-added with changeScreen()
		add(menuBarPanel);
		add(playerScreen);
		consolePane.setPreferredSize(consolePane.getSize()); // stop console from resizing
		checkForWinner();// thread loop to check for ships destroyed(both enemy and player)
	}

	public void setMaps(String c, int t) {
		console.setText(c);
		turnCounter = t;
		for (int x = 0; x < playerData.getSizeX(); x++) {
			for (int y = 0; y < playerData.getSizeY(); y++) {
				if (playerData.getBattleHitStatus(x, y) == BattleData.tileHitStatus.HIT) {
					playerMapButtons[x][y].setBackground(Color.red);
					playerMapButtons[x][y].setEnabled(false);
				}
				if (playerData.getBattleHitStatus(x, y) == BattleData.tileHitStatus.MISS) {
					playerMapButtons[x][y].setBackground(null);
					playerMapButtons[x][y].setEnabled(false);
				}
			}
		}
		for (int x = 0; x < playerData.getSizeX(); x++) {
			for (int y = 0; y < playerData.getSizeY(); y++) {
				if (bm.getEnemyMapTileStatus(x, y) == BattleData.tileHitStatus.HIT) {
					enemyMapButtons[x][y].setBackground(Color.red);
					enemyMapButtons[x][y].setEnabled(false);
				}
				if (bm.getEnemyMapTileStatus(x, y) == BattleData.tileHitStatus.MISS) {
					enemyMapButtons[x][y].setBackground(null);
					enemyMapButtons[x][y].setEnabled(false);
				}
			}
		}
	}

	public void createPlayerMap(int columns, int rows) {
		playerMapButtons = new JButton[columns][rows];
		playerMap = new JPanel(new GridLayout(rows, columns));
		playerMap.setBackground(null);

		int counter = 0;
		int xCounter = 0;
		int yCounter = 0;
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
				if (counter % columns == 0)
					xCounter = 0;
				yCounter = counter / columns;
				playerMapButtons[x][y] = new JButton("(" + xCounter + ", " + yCounter + ")");
				xCounter++;
				counter++;
				playerMapButtons[x][y].addActionListener(new MapButtonListener());
				playerMap.add(playerMapButtons[x][y]);
			}
		}

		// set map to display where ships were placed
		setButtonsToShip(playerData.bs.getTailLocation().x, playerData.bs.getTailLocation().y,
				playerData.bs.getRotation(), playerData.bs.getSHIP_SIZE(), "BS");
		setButtonsToShip(playerData.sb.getBodyLocation().x, playerData.sb.getBodyLocation().y,
				playerData.sb.getRotation(), playerData.sb.getSHIP_SIZE(), "SB");
		setButtonsToShip(playerData.cr.getTailLocation().x, playerData.cr.getTailLocation().y,
				playerData.cr.getRotation(), playerData.cr.getSHIP_SIZE(), "CR");
	}

	// displays where the ships are at, on the playerMap
	public void setButtonsToShip(int x, int y, BattleData.shipDirection r, int size, String shipAbbreviation) {
		if (size != 0) {
			playerMapButtons[x][y].setText(shipAbbreviation);
			playerMapButtons[x][y].setBackground(Color.blue);
			if (r == BattleData.shipDirection.UP)
				setButtonsToShip(x, y - 1, r, size - 1, shipAbbreviation);
			if (r == BattleData.shipDirection.DOWN)
				setButtonsToShip(x, y + 1, r, size - 1, shipAbbreviation);
			if (r == BattleData.shipDirection.LEFT)
				setButtonsToShip(x - 1, y, r, size - 1, shipAbbreviation);
			if (r == BattleData.shipDirection.RIGHT)
				setButtonsToShip(x + 1, y, r, size - 1, shipAbbreviation);
		}
	}

	public void createEnemyMap(int columns, int rows) {
		enemyMapButtons = new JButton[columns][rows];
		enemyMap = new JPanel(new GridLayout(rows, columns));
		enemyMap.setBackground(null);

		int counter = 0;
		int xCounter = 0;
		int yCounter = 0;
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
				if (counter % columns == 0)
					xCounter = 0;
				yCounter = counter / columns;
				enemyMapButtons[x][y] = new JButton("(" + xCounter + ", " + yCounter + ")");
				xCounter++;
				counter++;
				enemyMapButtons[x][y].addActionListener(new MapButtonListener());
				// add my own button listener to the buttons
				enemyMap.add(enemyMapButtons[x][y]);
			}
		}
	}

	// change font colors to opposite to background color
	// adjust font sizes with current game height
	// set component backgrounds to null
	private void adjustToSettings(Dimension screenDimension) {
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

		console.setFont(ts.fontResize(45));// in playerScreen

		xlabel.setFont(ts.fontResize(25));
		xlabel.setForeground(TitleScreen.fontLabelColor);
		ylabel.setFont(ts.fontResize(25));
		ylabel.setForeground(TitleScreen.fontLabelColor);

		ts.changeSize(screenDimension);
		if(screenDimension.getWidth() > TitleScreen.SCREEN_DIMESNION.getWidth()) {
			scrollPanePlayerMap = new JScrollPane(playerMap);
			scrollPaneEnemyMap = new JScrollPane(playerMap);
			scrollPanePlayerMap.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPaneEnemyMap.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			screenDimensionTooBig = true;
		}
		if(screenDimension.getHeight() > TitleScreen.SCREEN_DIMESNION.getHeight()) {
			scrollPanePlayerMap = new JScrollPane(playerMap);
			scrollPaneEnemyMap = new JScrollPane(playerMap);
			scrollPanePlayerMap.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPaneEnemyMap.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			screenDimensionTooBig = true;
		}
	}

	// setup MenuBar
	private void setupMenuBar() {
		// components
		menuBarPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		menuBarPanel.add(menuBar);
		menuBar.add(file);
		file.add(saveAndReturn);
		file.add(returnNoSave);
		file.add(saveAndQuit);
		file.add(quitNoSave);
		// listeners
		saveAndReturn.addActionListener(new MenuBarActionListener());
		returnNoSave.addActionListener(new MenuBarActionListener());
		saveAndQuit.addActionListener(new MenuBarActionListener());
		quitNoSave.addActionListener(new MenuBarActionListener());
		// visual look
		menuBarPanel.setBackground(null);
	}

	// populates JPanel playerScreen
	public void playerScreenInitialize() {
		GridBagConstraints c = new GridBagConstraints();
		playerScreen.setLayout(new GridBagLayout());
		// add top
		ptop.add(plabel);
		// add console panel
		consolePanel.setBackground(null);
		consolePanel.setLayout(new BorderLayout());
		consolePane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		consolePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		// console.setEditable(false); //turned off for testing

		consolePanel.add(consolePane);

		// add to playerScreen
		c.gridwidth = 2;
		c.weightx = 0.95;
		c.weighty = 0.05;
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.PAGE_START;
		playerScreen.add(ptop, c); // top

		c.anchor = GridBagConstraints.CENTER; // reset anchor
		c.weightx = 0.8;
		c.weighty = 0.85;
		c.gridx = 0;
		c.gridy = 1;
		if(screenDimensionTooBig) {
			playerScreen.add(scrollPanePlayerMap, c);
		}else {
			playerScreen.add(playerMap, c); // middle
		}

		c.gridwidth = 1;
		c.weightx = 0.999;
		c.weighty = 0.1;
		c.gridx = 0;
		c.gridy = 2;
		playerScreen.add(consolePanel, c); // bottom

		c.anchor = GridBagConstraints.SOUTHEAST;
		c.weightx = 0.001;
		c.weighty = 0.1;
		c.gridx = 1;
		c.gridy = 2;

		JButton changeScreen = new JButton();
		playerScreen.add(changeScreen, c);

		// rezise image
		int newImageSize = 0;
		if (changeScreen.getPreferredSize().width > changeScreen.getPreferredSize().height)
			newImageSize = changeScreen.getPreferredSize().width;
		else
			newImageSize = changeScreen.getPreferredSize().height;
		Image t = new ImageIcon("src/battleship/source/Images/Enemy.png").getImage().getScaledInstance(newImageSize,
				newImageSize, Image.SCALE_DEFAULT);

		changeScreen.setIcon(new ImageIcon(t));
		changeScreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeScreen(enemyScreen);
				onEnemyScreen = true;
			}
		});
		Thread iwtw = new Thread() {
			public void run() {
				try {
					changeScreen(enemyScreen);
					onEnemyScreen = true;
					for (int x = 0; x < enemyMapButtons.length; x++) {
						for (int y = 0; y < enemyMapButtons[x].length; y++) {
							enemyMapButtons[x][y].setBackground(Color.yellow);
						}
						// hit enemy ship
						Thread.sleep(500);
						for (int y = 0; y < enemyMapButtons[x].length; y++) {
							BattleData.tileHitStatus playerShot = bm.playerShotCheck(x, y);
							if (playerShot == BattleData.tileHitStatus.HIT) {
								enemyMapButtons[x][y].setBackground(Color.red);
								enemyMapButtons[x][y].setEnabled(false);
								writeToConsole("Enemy ship hit!");
							}
							// miss enemy ship
							if (playerShot == BattleData.tileHitStatus.MISS) {
								enemyMapButtons[x][y].setBackground(null);
								enemyMapButtons[x][y].setEnabled(false);
							}
						}
					}
				} catch (Exception e) {

				}
			}
		};
		Thread te = new Thread() {
			public void run() {
				try {
					changeScreen(playerScreen);
					onEnemyScreen = false;
					for (int x = 0; x < playerMapButtons.length; x++) {
						for (int y = 0; y < playerMapButtons[x].length; y++) {
							playerMapButtons[x][y].setBackground(Color.yellow);
						}
						writeToConsole("ABANDON SHIP!");
						// hit enemy ship
						Thread.sleep(500);
						for (int y = 0; y < playerMapButtons[x].length; y++) {
							BattleData.tileHitStatus enemyShot = bm.enemyShotCheck(x, y);
							if (enemyShot == BattleData.tileHitStatus.HIT) {
								playerMapButtons[x][y].setBackground(Color.red);
								playerMapButtons[x][y].setEnabled(false);
								writeToConsole(playerData.getShipHitName(new Point(x, y)) + " was hit!");
							}
							// miss enemy ship
							if (enemyShot == BattleData.tileHitStatus.MISS) {
								playerMapButtons[x][y].setBackground(null);
								playerMapButtons[x][y].setEnabled(false);
							}
						}
					}
				} catch (Exception e) {

				}
			}
		};
		// secret
		addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				if (e.getX() > ts.getWidth() - 3 && e.getY() < 3) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						String text = JOptionPane.showInputDialog("");
						if (text.equalsIgnoreCase("IWANTTOWIN")) {
							iwtw.start();
						}
						if (text.equalsIgnoreCase("TOOEASY")) {
							te.start();
						}
					}
				}
			}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
	}

	// populates JPanel enemyScreen
	private void enemyScreenIntialize() {
		GridBagConstraints c = new GridBagConstraints();
		enemyScreen.setLayout(new GridBagLayout());
		// add top
		etop.add(elabel);
		JPanel ex = new JPanel();
		ex.setBackground(null);
		ex.add(xlabel);
		ex.add(xcoord);
		JPanel ey = new JPanel();
		ey.setBackground(null);
		ey.add(ylabel);
		ey.add(ycoord);
		efire.setLayout(new GridLayout(1, 3));
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
		if(screenDimensionTooBig) {
			enemyScreen.add(scrollPaneEnemyMap, c);
		}else {
			enemyScreen.add(enemyMap, c); // middle
		}

		c.gridwidth = 1;
		c.weightx = 0.999;
		c.weighty = 0.1;
		c.gridx = 0;
		c.gridy = 2;
		enemyScreen.add(efire, c);

		c.anchor = GridBagConstraints.SOUTHEAST;
		c.weightx = 0.001;
		c.weighty = 0.1;
		c.gridx = 1;
		c.gridy = 2;
		JButton changeScreen = new JButton();
		enemyScreen.add(changeScreen, c);

		// resize image for changeScreen button
		int newImageSize = 0;
		if (changeScreen.getPreferredSize().width > changeScreen.getPreferredSize().height)
			newImageSize = changeScreen.getPreferredSize().width;
		else
			newImageSize = changeScreen.getPreferredSize().height;
		Image t = new ImageIcon("src/battleship/source/Images/Player.png").getImage()
				.getScaledInstance(newImageSize, newImageSize, Image.SCALE_DEFAULT);

		changeScreen.setIcon(new ImageIcon(t));
		changeScreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeScreen(playerScreen);
				onEnemyScreen = false;
			}
		});
	}

	// listeners, etc.. declare components with other stuff
	private void declareComponentsTo() {
		fireButton.addActionListener(new MapButtonListener());
		// set textfields and console are to non-editable
		console.setEditable(false);
		xcoord.setEditable(false);
		ycoord.setEditable(false);
	}

	// changing between playerScreen and enemyScreen
	private void changeScreen(JPanel screen) {
		removeAll();
		repaint();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(menuBarPanel);
		add(screen);

		validate();
	}

	// clicking the fire button will initiate
	private void doPlayerTurn() {
		Thread playerTurn = new Thread() {
			public void run() {
				try {
					int x = Integer.parseInt(xcoord.getText());
					int y = Integer.parseInt(ycoord.getText());
					writeToConsole("---Player Turn---");
					writeToConsole("Missles fired at coordinates(" + x + ", " + y + ")");
					enemyMapButtons[x][y].setBackground(Color.yellow);

					Thread.sleep(2 * 1000);
					BattleData.tileHitStatus playerShot = bm.playerShotCheck(x, y);
					// hit enemy ship
					if (playerShot == BattleData.tileHitStatus.HIT) {
						enemyMapButtons[x][y].setBackground(Color.red);
						enemyMapButtons[x][y].setEnabled(false);
						writeToConsole("Enemy ship hit!");
					}
					// miss enemy ship
					if (playerShot == BattleData.tileHitStatus.MISS) {
						enemyMapButtons[x][y].setBackground(null);
						enemyMapButtons[x][y].setEnabled(false);
						writeToConsole("Missles hit nothing");
					}
					// reset textFields
					xcoord.setText("");
					ycoord.setText("");
				} catch (Exception g) {

				}
			}
		};
		threadManager.submit(playerTurn); // run the thread
	}

	// runs after playerTurn(thread) is over
	private void doEnemyTurn() {
		// get AI's shot
		Point enemyShot = bm.getEnemyPointToShoot();
		menuBar.setEnabled(false);
		fireButton.setText("Awaiting enemy");
		fireButton.setEnabled(false);
		int x = enemyShot.x;
		int y = enemyShot.y;

		// makes it seem like AI is thinking
		int sleepTime = new Random().nextInt(11) + 3;

		Thread enemyTurn = new Thread() {
			public void run() {
				try {
					Thread.sleep(sleepTime * 1000);
					writeToConsole("---Enemy Turn---");
					writeToConsole("Missles firing at coordinates(" + x + ", " + y + ")");
					fireButton.setText("!!!(" + x + ", " + y + ")!!!");
					playerMapButtons[x][y].setBackground(Color.yellow);

					Thread.sleep(2 * 1000);
					BattleData.tileHitStatus enemyShot = bm.enemyShotCheck(x, y);

					// player ship hit
					if (enemyShot == BattleData.tileHitStatus.HIT) {
						playerMapButtons[x][y].setBackground(Color.red);
						playerMapButtons[x][y].setEnabled(false);
						writeToConsole(playerData.getShipHitName(new Point(x, y)) + " ship was hit!");
						fireButton.setText(playerData.getShipHitName(new Point(x, y)) + "");
					}
					// enemy hit nothing
					if (enemyShot == BattleData.tileHitStatus.MISS) {
						playerMapButtons[x][y].setBackground(null);
						playerMapButtons[x][y].setEnabled(false);
						writeToConsole("Missle hit nothing");
					}
					// re-enable players turn
					menuBar.setEnabled(true);
					fireButton.setEnabled(true);
					fireButton.setText("Fire");
					turnCounter++;
				} catch (Exception e) {

				}
			}
		};
		threadManager.submit(enemyTurn);// only runs after player thread ends
	}

	// continuous running thread to check for winner
	private void checkForWinner() {
		Thread checkForWinner = new Thread() {
			public void run() {
				while (true) {// whiel battlescreen exist
					try {
						// check every half second
						// so it wont spam the processor
						Thread.sleep(500);
						if (bm.enemyShipsDestroyed()) { // player wins
							// show loserScreen
							threadManager.shutdown();
							ts.changeScreen(new EndScreen(ts, true));
							break;
						}
						if (bm.playerShipsDestroyed()) { // player lost
							/// show winscreen
							threadManager.shutdown();
							ts.changeScreen(new EndScreen(ts, false));
							break;
						}
					} catch (Exception e) {

					}
				}
			}
		};
		checkForWinner.start();
	}

	// display information to the console on player screen
	private void writeToConsole(String text) {
		console.append(turnCounter + ": " + text + "\n");
		console.setCaretPosition(console.getDocument().getLength());
	}

	private void save(String fileName, Dimension screenDimension, String consoleText, int turnCount) {
		bm.save(fileName, screenDimension, consoleText, turnCount);
	}

	private class MapButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//// ENDING PLAYER TURN////
			if (e.getSource() == fireButton) {
				// only if coordinates have been selected, ie: not blank
				if (!xcoord.getText().equals("") && !ycoord.getText().equals("")) {

					// do player turn
					doPlayerTurn();
					// do enemy turn
					doEnemyTurn();
				}
			}
			// WHEN CLICKING ON MAP TO SELECT COORDINATES FOR FIRING//
			if (onEnemyScreen && e.getSource() != fireButton) {
				String[] coords = e.getActionCommand().split(", ");
				coords[0] = coords[0].substring(1, coords[0].length());
				coords[1] = coords[1].substring(0, coords[1].length() - 1);
				xcoord.setText(coords[0]);
				ycoord.setText(coords[1]);
			}
		}
	}

	// for the menuBar on top of screen
	private class MenuBarActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Date currentTime = new Date();
			String fileName = currentTime.getMonth() + "-" + currentTime.getDay() + "-" + (currentTime.getYear() + 1900)
					+ "_" + (currentTime.getHours() % 12) + "-" + currentTime.getMinutes() + "-"
					+ currentTime.getSeconds();
			if (e.getSource() == saveAndReturn) {
				JOptionPane.showMessageDialog(ts.getContentPane(), "\"" + fileName + "\" saved", "Save and Quit",
						JOptionPane.PLAIN_MESSAGE);
				save(fileName, ts.getSize(), console.getText(), turnCounter);
				ts.mainMenu();
			}
			if (e.getSource() == returnNoSave) {
				int choice = JOptionPane.showConfirmDialog(ts.getContentPane(), "Are you sure?", "Return to Main Menu",
						JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION)
					ts.mainMenu();
			}
			if (e.getSource() == saveAndQuit) {
				JOptionPane.showMessageDialog(ts.getContentPane(), "\"" + fileName + "\" saved", "Save and Quit",
						JOptionPane.PLAIN_MESSAGE);
				save(fileName, ts.getSize(), console.getText(), turnCounter);
				bm.dispose();
				ts.dispose();
			}
			if (e.getSource() == quitNoSave) {
				int choice = JOptionPane.showConfirmDialog(ts.getContentPane(), "Are you sure?", "Quit",
						JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION)
					bm.dispose();
				ts.dispose();
			}
		}
	}
}

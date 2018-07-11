package battleship.source;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.*;

public class ShipSelect {
	public static enum shipType {
		BATTLESHIP, SPEEDBOAT, CARRIER;
	}

	private JPanel battleSetupScreen = new JPanel();
	private JTextField input1 = new JTextField("", 10);
	private JTextField input2 = new JTextField("", 10);
	private JButton enter = new JButton("Continue"); //

	private JPanel battlePlacementScreen = new JPanel();
	private BattleData.shipDirection storeDirection = BattleData.shipDirection.UP;
	private JButton direction = new JButton("Up");
	private shipType storeType = shipType.BATTLESHIP;
	private JButton shipChoice = new JButton("Battle Ship");
	private JButton next = new JButton("Continue");

	private JButton[][] shipButton;

	private TitleScreen ts;
	private int numForX = 0;//mapsize X
	private int numForY = 0;//mapsize Y

	//if ship was not placed
	boolean battleshipRemain = true;
	boolean speedboatRemain = true;
	boolean carrierRemain = true;

	BattleData playerData; // players data

	//only used when loading a save file
	BattleData enemyData;
	Dimension screenDimension;
	private String consoleText = "";
	private int turnCount = 0;

	public ShipSelect(TitleScreen ts) {
		this.ts = ts;
		playerPrompt(); // intialize battleSetupScreen
		ts.changeScreen(battleSetupScreen);// change to battleSetupScreen
	}

	// battleScreenSetup
	public void playerPrompt() {
		battleSetupScreen.setBackground(null);
		Dimension dimensionOfJFrame1 = ts.getContentPane().getSize();
		int bestY = (int) dimensionOfJFrame1.getHeight() / 10;
		int bestX = (int) dimensionOfJFrame1.getWidth() / 10;

		JLabel promptPart1 = new JLabel("What size game?");
		promptPart1.setForeground(Color.white);
		promptPart1.setSize(bestX, bestY);
		JLabel promptPart2 = new JLabel("X:");
		promptPart2.setForeground(Color.white);
		promptPart2.setSize(bestX, bestY);
		JLabel promptPart3 = new JLabel("Y:");
		promptPart3.setSize(bestX, bestY);
		promptPart3.setForeground(Color.white);

		input1.setSize(bestX, bestY);

		input2.setSize(bestX, bestY);

		battleSetupScreen.setLayout(new GridLayout(4, 1));

		JPanel promptStart = new JPanel(new FlowLayout());
		promptStart.setBackground(null);
		promptStart.add(promptPart1);

		JPanel promptX = new JPanel(new FlowLayout());
		promptX.setBackground(null);
		promptX.add(promptPart2);
		promptX.add(input1);

		JPanel promptY = new JPanel(new FlowLayout());
		promptY.setBackground(null);
		promptY.add(promptPart3);
		promptY.add(input2);

		JPanel promptEnd = new JPanel(new FlowLayout());
		JButton load = new JButton("Load");
		JButton back = new JButton("Back");
		promptEnd.setBackground(null);
		promptEnd.add(back);
		promptEnd.add(enter);
		promptEnd.add(load);

		battleSetupScreen.add(promptStart);
		battleSetupScreen.add(promptX);
		battleSetupScreen.add(promptY);
		battleSetupScreen.add(promptEnd);

		enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String stringForX = input1.getText();
				String stringforY = input2.getText();

				if (validSizes(stringForX, stringforY)) {
					numForX = Integer.parseInt(stringForX);
					numForY = Integer.parseInt(stringforY);
					setupMap(numForX, numForY);// setups playerData
					playerData = new BattleData(numForX, numForY);
					ts.changeScreen(battlePlacementScreen);
				} else {

				}
			}
		});
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfl = new JFileChooser();
				jfl.setCurrentDirectory(new File("src/battleship/source/Saves/"));
				jfl.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int option = jfl.showOpenDialog(battleSetupScreen);
				if(option == JFileChooser.APPROVE_OPTION) {
					String filePath = jfl.getSelectedFile().getPath();
					if(jfl.getSelectedFile().getName().substring(jfl.getSelectedFile().getName().lastIndexOf("."),
							jfl.getSelectedFile().getName().length()).equals(".dat")) {
						read(filePath);
						if(screenDimension.getHeight() > ts.getHeight()) {
							TitleScreen.output(battleSetupScreen, "Message", "Game will resize to loaded saves dimension.");
						}
						ts.changeScreen(new BattleScreen(ts, new BattleMechanics(playerData, enemyData), playerData,
								true, screenDimension, consoleText, turnCount)); 
					}
				}
			}
		});
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ts.mainMenu();
			}
		});
	}
	
	public void read(String filePath) {
		try {
			ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath));
			try {
				playerData = (BattleData)inputStream.readObject();
				enemyData = (BattleData)inputStream.readObject();
				screenDimension = (Dimension)inputStream.readObject();
				consoleText = inputStream.readUTF();
				turnCount = inputStream.readInt();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			inputStream.close();
		}catch(IOException e) {
			
		}
	}
	// should add some complication algorithm to determine max values based on
	// current game width and height
	// or should resize game width and height if values are past a certain value
	public boolean validSizes(String xtext, String ytext) {
		// if nothing was put in textfields, return false
		if (xtext == null || ytext == null || xtext.equals("") || ytext.equals("")) {
			TitleScreen.output(battleSetupScreen, "Message",
					"Minimum sizes must be 4x4! \nCannot be empty textfields!");
			return false;
		}
		// if another other than numbers were put in textfields, return false
		for (int x = 0; x < xtext.length(); x++) {
			if (!Character.isDigit(xtext.charAt(x))) {
				TitleScreen.output(battleSetupScreen, "Message", "Must be integer numbers!");
				return false;
			}
		}
		for (int x = 0; x < ytext.length(); x++) {
			if (!Character.isDigit(ytext.charAt(x))) {
				TitleScreen.output(battleSetupScreen, "Message", "Must be integer numbers!");
				return false;
			}
		}
		int xinput = Integer.parseInt(xtext);
		int yinput = Integer.parseInt(ytext);
		// minimum size to 4x4, so that battlship, carrier, and speedboat can all fit
		if (xinput < 4 || yinput < 4) {
			TitleScreen.output(battleSetupScreen, "Message", "Minimum sizes must be 4x4!");
			return false;
		}
		if((xinput <= 10 && yinput >= 11) || (xinput >= 11 && yinput <= 10)) {
			if(ts.getWidth()/xinput < 69) {
				TitleScreen.output(battleSetupScreen, "Message", "X should be atmost " + ts.getWidth()/69);
				return false;
			}
		}
		if(xinput >= 11 && yinput >= 10) {
			if(ts.getWidth()/xinput < 76) {
				TitleScreen.output(battleSetupScreen, "Message", "X should be atmost " + ts.getWidth()/79);
				return false;
			}
		}
		if((ts.getHeight()*.52)/yinput < 26) {
			TitleScreen.output(battleSetupScreen, "Message", "Y should be atmost " + ((ts.getHeight()*.52)/26));
			return false;
		}
		return true;// all is good, return true
	}

	// battlePlacementScreen
	private void setupMap(int sizex, int sizey) {
		battlePlacementScreen.setBackground(null);
		battlePlacementScreen.setLayout(new GridLayout(sizey + 1, sizex));
		shipButton = new JButton[sizex][sizey];

		int counter = 0;
		int xCounter = 0;
		int yCounter = 0;
		for (int y = 0; y < sizey; y++) {
			for (int x = 0; x < sizex; x++) {
				if(counter%sizex==0) xCounter = 0;
				yCounter = counter/sizex;
				shipButton[x][y] = new JButton("(" + xCounter + ", " + yCounter + ")");
				xCounter++;
				counter++;
				shipButton[x][y].addActionListener(new ButtonListener());
				battlePlacementScreen.add(shipButton[x][y]);
			}
		}
		battlePlacementScreen.add(direction);
		direction.addActionListener(new ButtonListener());
		battlePlacementScreen.add(shipChoice);
		shipChoice.addActionListener(new ButtonListener());
		
		battlePlacementScreen.add(next);
		
		next.setEnabled(false);
		for (int g = 3; g < sizex; g++) {// fills in space with extra blank buttons
			battlePlacementScreen.add(new JButton(""));
		}
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ts.changeScreen(new BattleScreen(ts, new BattleMechanics(playerData), playerData, false , ts.getSize(), "", 0)); 
			}
		});
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// Cycle chip direction
			if (e.getSource() == direction) { // the direction button
				String buttonDirection = direction.getText();
				if (buttonDirection.equals("Up")) {
					direction.setText("Down");
					storeDirection = BattleData.shipDirection.DOWN;
				} else if (buttonDirection.equals("Down")) {
					direction.setText("Left");
					storeDirection = BattleData.shipDirection.LEFT;
				} else if (buttonDirection.equals("Left")) {
					direction.setText("Right");
					storeDirection = BattleData.shipDirection.RIGHT;
				} else if (buttonDirection.equals("Right")) {
					direction.setText("Up");
					storeDirection = BattleData.shipDirection.UP;
				}
			}
			// Cycle ship type
			if (e.getSource() == shipChoice) {
				String buttonShip = shipChoice.getText();
				if (buttonShip.equals("Battle Ship")) {
					if (speedboatRemain) {
						shipChoice.setName("Speed Boat");
						shipChoice.setText("Speed Boat");
						storeType = shipType.SPEEDBOAT;
					}
					if (carrierRemain) {
						shipChoice.setName("Carrier");
						shipChoice.setText("Carrier");
						storeType = shipType.CARRIER;
					}
				} else if (buttonShip.equals("Carrier")) {
					if (battleshipRemain) {
						shipChoice.setName("Battle Ship");
						shipChoice.setText("Battle Ship");
						storeType = shipType.BATTLESHIP;
					}
					if (speedboatRemain) {
						shipChoice.setName("Speed Boat");
						shipChoice.setText("Speed Boat");
						storeType = shipType.SPEEDBOAT;
					}
				} else if (buttonShip.equals("Speed Boat")) {
					if (carrierRemain) {
						shipChoice.setName("Carrier");
						shipChoice.setText("Carrier");
						storeType = shipType.CARRIER;
					}
					if (battleshipRemain) {
						shipChoice.setName("Battle Ship");
						shipChoice.setText("Battle Ship");
						storeType = shipType.BATTLESHIP;
					}
				}
			}

			// shipPlacing
			if (e.getSource() != direction && e.getSource() != shipChoice) {
				// clicked another button on the map
				// now place ship on map

				// new coordinate system because old one was
				// only working with squared maps
				String[] coords = e.getActionCommand().split(", ");
				coords[0] = coords[0].substring(1, coords[0].length());
				coords[1] = coords[1].substring(0, coords[1].length() - 1);
				int xCoord = Integer.parseInt(coords[0]);
				int yCoord = Integer.parseInt(coords[1]);

				// battleship
				if (storeType == shipType.BATTLESHIP && battleshipRemain) {
					if (playerData.checkAvailable(storeDirection, xCoord, yCoord, 3)) { // hardcoded
						playerData.setBattleshipObject(storeDirection, new BattleShip(xCoord, yCoord, storeDirection),
								xCoord, yCoord);
						if (storeDirection == BattleData.shipDirection.UP) {
							for (int i = 0; i < 3; i++) {
								shipButton[xCoord][yCoord - i].setText("BS");
								shipButton[xCoord][yCoord - i].setEnabled(false);
							}
						}
						if (storeDirection == BattleData.shipDirection.DOWN) {
							for (int i = 0; i < 3; i++) {
								shipButton[xCoord][yCoord + i].setText("BS");
								shipButton[xCoord][yCoord + i].setEnabled(false);
							}
						}
						if (storeDirection == BattleData.shipDirection.RIGHT) {
							for (int i = 0; i < 3; i++) {
								shipButton[xCoord + i][yCoord].setText("BS");
								shipButton[xCoord + i][yCoord].setEnabled(false);
							}
						}
						if (storeDirection == BattleData.shipDirection.LEFT) {
							for (int i = 0; i < 3; i++) {
								shipButton[xCoord - i][yCoord].setText("BS");
								shipButton[xCoord - i][yCoord].setEnabled(false);
							}
						}
						battleshipRemain = false;
					}
				}
				// carrier
				if (storeType == shipType.CARRIER && carrierRemain) {
					if (playerData.checkAvailable(storeDirection, xCoord, yCoord, 4)) { // hardcoded size "4"

						playerData.setBattleshipObject(storeDirection, new Carrier(xCoord, yCoord, storeDirection),
								xCoord, yCoord);
						if (storeDirection == BattleData.shipDirection.UP) {
							for (int i = 0; i < 4; i++) {
								shipButton[xCoord][yCoord - i].setText("CR");
								shipButton[xCoord][yCoord - i].setEnabled(false);
							}
						}
						if (storeDirection == BattleData.shipDirection.DOWN) {
							for (int i = 0; i < 4; i++) {
								shipButton[xCoord][yCoord + i].setText("CR");
								shipButton[xCoord][yCoord + i].setEnabled(false);
							}
						}
						if (storeDirection == BattleData.shipDirection.RIGHT) {
							for (int i = 0; i < 4; i++) {
								shipButton[xCoord + i][yCoord].setText("CR");
								shipButton[xCoord + i][yCoord].setEnabled(false);
							}
						}
						if (storeDirection == BattleData.shipDirection.LEFT) {
							for (int i = 0; i < 4; i++) {
								shipButton[xCoord - i][yCoord].setText("CR");
								shipButton[xCoord - i][yCoord].setEnabled(false);
							}
						}
						carrierRemain = false;
					}
				}
				// speedBoat
				if (storeType == shipType.SPEEDBOAT && speedboatRemain) {
					if (playerData.checkAvailable(storeDirection, xCoord, yCoord, 2)) {// hardcoded
						playerData.setBattleshipObject(storeDirection, new SpeedBoat(xCoord, yCoord, storeDirection),
								xCoord, yCoord);
						if (storeDirection == BattleData.shipDirection.UP) {
							for (int i = 0; i < 2; i++) {
								shipButton[xCoord][yCoord - i].setText("SB");
								shipButton[xCoord][yCoord - i].setEnabled(false);
							}
						}
						if (storeDirection == BattleData.shipDirection.DOWN) {
							for (int i = 0; i < 2; i++) {
								shipButton[xCoord][yCoord + i].setText("SB");
								shipButton[xCoord][yCoord + i].setEnabled(false);
							}
						}
						if (storeDirection == BattleData.shipDirection.RIGHT) {
							for (int i = 0; i < 2; i++) {
								shipButton[xCoord + i][yCoord].setText("SB");
								shipButton[xCoord + i][yCoord].setEnabled(false);
							}
						}
						if (storeDirection == BattleData.shipDirection.LEFT) {
							for (int i = 0; i < 2; i++) {
								shipButton[xCoord - i][yCoord].setText("SB");
								shipButton[xCoord - i][yCoord].setEnabled(false);
							}
						}
						speedboatRemain = false;
					}
				}

				// switching shipChoice text when a boat has been placed
				if (!battleshipRemain && carrierRemain) {
					// switch to Carrier
					shipChoice.setName("Carrier");
					shipChoice.setText("Carrier");
					storeType = shipType.CARRIER;
				}
				if (!carrierRemain && speedboatRemain) {
					// switch to speedboat
					shipChoice.setName("Speed Boat");
					shipChoice.setText("Speed Boat");
					storeType = shipType.SPEEDBOAT;
				}
				if (!speedboatRemain && battleshipRemain) {
					// switch to battleship
					shipChoice.setName("Battle Ship");
					shipChoice.setText("Battle Ship");
					storeType = shipType.BATTLESHIP;
				}
			}

			if (!speedboatRemain && !battleshipRemain && !carrierRemain) {
				direction.setEnabled(false);
				shipChoice.setEnabled(false);
				next.setEnabled(true);
			}
		}

	}
}

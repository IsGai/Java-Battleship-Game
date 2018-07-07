
/*-resizing is broken
 * -Selection after the first part is not yet working 
 */

package battleship.source;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class BattleMap extends JPanel {
	public static enum shipType{
		BATTLESHIP, SPEEDBOAT, CARRIER; 
	}
	public static enum shipDirection{
	UP, DOWN, RIGHT, LEFT;
	}

	private JTextField input1 = new JTextField("", 10);
	private JTextField input2 = new JTextField("", 10);
	JButton enter = new JButton("Continue");
	shipDirection storeDirection = shipDirection.RIGHT;
	JButton direction = new JButton("Right");
	shipType storeType = shipType.BATTLESHIP;
	JButton shipChoice = new JButton("Battle Ship");
	JButton[][] shipButton;
	
	

	TitleScreen gameState = null;
	
	private int numForX = 0;
	private int numForY = 0;
	
	boolean battleshipRemain = true;
	boolean speedboatRemain = true;
	boolean carrierReamain = true;
	
	public BattleMap(TitleScreen ts) {
		gameState = ts;
		this.setBackground(null);
		connectListeners();
		playerPrompt(gameState);
		
	}
	/**
	 * William
	 */
	public BattleMap(TitleScreen ts, int rows, int columns) {
		this.setBackground(null);
		this.setLayout(new GridLayout(rows+1, columns));
		setupMap(columns, rows);
		sizeX = columns;
		sizeY = rows;
		BattleData player = new BattleData(sizeX, sizeY);
	}
	private void setupMap(int sizex, int sizey) {
		/* how the array would look like
		 * 	  Y
		 * X [0][1][2]
		 *   [1][1][1]
		 * 	 [2][2][2]
		 */
		shipButton = new JButton[sizex][sizey]; 
		
		int counter = 0;
		for(int y=0; y<sizey; y++) {
			for(int x=0; x<sizex; x++) {
				shipButton[x][y] = new JButton(counter++ + "");
				shipButton[x][y].addActionListener(new ButtonListener());
				this.add(shipButton[x][y]);
			}
		}
		
		this.add(horizontal);
		this.add(vertical);
		JButton selected = new JButton("Continue");
		this.add(selected);
		for(int g=3;g<sizex;g++) {//fills in space with extra buttons
			this.add(new JButton("X"));
		}
		
		//enemyAI goes here
	}
	/*
	 * End of WILLIAM
	 */
	
	public void playerPrompt(TitleScreen ts) {
		//Size not working must fix
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
		
		this.setLayout(new GridLayout(4,1));
		
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
		promptEnd.setBackground(null);
		promptEnd.add(enter);
		
		this.add(promptStart);
		this.add(promptX);
		this.add(promptY);
		this.add(promptEnd);
		
		
	}
	
	private void connectListeners() {
		enter.addActionListener(new ButtonListener());
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
		String nameOfCallingBtn = e.getActionCommand();
				if(nameOfCallingBtn == "Continue") {
					String stringForX = input1.getText();
					String stringforY = input2.getText();
					
		
					try {
						numForX = Integer.parseInt(stringForX);
						numForY = Integer.parseInt(stringforY);
					} catch (Exception ex) {
						
					}
					/*when continue button is clicked
					 * changes to BattleScreen, BattleScreen creates two BattleMaps
					 * one for the PLAYER and one for the ENEMY
					 * the JFRAME is now displaying BattleScreen, which holds BattleMaps
					 */
					gameState.changeScreen(new BattleScreen(gameState, numForY, numForX));
					//
					
				}
				//Cycle chip direction
				else if (nameOfCallingBtn == "Right") {
					JButton buttonToGetOut = (JButton) e.getSource();
					buttonToGetOut.setName("Down");
					storeDirection = shipDirection.DOWN;
				}else if (nameOfCallingBtn == "Down") {
					JButton buttonToGetOut = (JButton) e.getSource();
					buttonToGetOut.setName("Left");
					storeDirection = shipDirection.LEFT;
				}else if (nameOfCallingBtn == "Left") {
					JButton buttonToGetOut = (JButton) e.getSource();
					buttonToGetOut.setName("Up");
					storeDirection = shipDirection.UP;
				}else if (nameOfCallingBtn == "Up") {
					JButton buttonToGetOut = (JButton) e.getSource();
					buttonToGetOut.setName("Right");
					storeDirection = shipDirection.RIGHT;
				}
				//Cycle ship type
				else if (nameOfCallingBtn == "Battle Ship") {
					JButton buttonToGetOut = (JButton) e.getSource();
					if(carrierReamain){
						buttonToGetOut.setName("Carrier");
						storeType = shipType.CARRIER;
					}
					else if(speedboatRemain){
						buttonToGetOut.setName("Speed Boat");
						storeType = shipType.SPEEDBOAT;
					}else{
						buttonToGetOut.setEnabled(false);
					}
				}else if (nameOfCallingBtn == "Speed Boat") {
					JButton buttonToGetOut = (JButton) e.getSource();
					if(battleshipReamain){
						buttonToGetOut.setName("Battle Ship");
						storeType = shipType.BATTLESHIP;
					}
					else if(carrierRemain){
						buttonToGetOut.setName("Carrier");
						storeType = shipType.Carrier;
					}else{
						buttonToGetOut.setEnabled(false);
					}
				}else if (nameOfCallingBtn == "Carrier") {
					JButton buttonToGetOut = (JButton) e.getSource();
					if(speedboatReamain){
						buttonToGetOut.setName("Speed Boat");
						storeType = shipType.SPEEDBOAT;
					}
					else if(battleshipRemain){
						buttonToGetOut.setName("Battle Ship");
						storeType = shipType.BATTLESHIP;
					}else{
						buttonToGetOut.setEnabled(false);
					}
				}
				//For location selected
				else{
					int temp = Integer.parseInt(nameOfCallingBtn);
					int xCoord = (temp % sizeX) - 1;
					int yCoord = (temp / sizeX) - 1;
					if(storeType = shipType.CARRIER){
						if(player.checkAvailable(storeDirection, xCoord, yCoord)){
							player.setBattleshipObject(storeDirection, new Carrier(xCoord , yCoord, storeDirection));
							if(storeDirection == shipDirection.UP){
								for(i = 0; i < 4; i++){
									shipButton[xCoord][yCoord + i].setEnabled(false);
								}
							}
							if(storeDirection == shipDirection.DOWN){
								for(i = 0; i < 4; i++){
									shipButton[xCoord][yCoord - i].setEnabled(false);
								}
							}	
							if(storeDirection == shipDirection.RIGHT){
								for(i = 0; i < 4; i++){
									shipButton[xCoord + i][yCoord].setEnabled(false);
								}
							}	
							if(storeDirection == shipDirection.LEFT){
								for(i = 0; i < 4; i++){
									shipButton[xCoord - i][yCoord].setEnabled(false);
								}
							}	
						}
						else{
						return;	
						}		
					}
					if(storeType = shipType.BATTLESHIP){
						if(player.checkAvailable(storeDirection, xCoord, yCoord)){
							player.setBattleshipObject(storeDirection, new BattleShip(xCoord , yCoord, storeDirection));
							if(storeDirection == shipDirection.UP){
								for(i = 0; i < 3; i++){
									shipButton[xCoord][yCoord + i].setEnabled(false);
								}
							}
							if(storeDirection == shipDirection.DOWN){
								for(i = 0; i < 3; i++){
									shipButton[xCoord][yCoord - i].setEnabled(false);
								}
							}	
							if(storeDirection == shipDirection.RIGHT){
								for(i = 0; i < 3; i++){
									shipButton[xCoord + i][yCoord].setEnabled(false);
								}
							}	
							if(storeDirection == shipDirection.LEFT){
								for(i = 0; i < 3; i++){
									shipButton[xCoord - i][yCoord].setEnabled(false);
								}
							}	
						}
						else{
						return;	
						}		
					}
					if(storeType = shipType.SPEEDBOAT){
						if(player.checkAvailable(storeDirection, xCoord, yCoord)){
							player.setBattleshipObject(storeDirection, new SpeedBoat(xCoord , yCoord, storeDirection));
							if(storeDirection == shipDirection.UP){
								for(i = 0; i < 2; i++){
									shipButton[xCoord][yCoord + i].setEnabled(false);
								}
							}
							if(storeDirection == shipDirection.DOWN){
								for(i = 0; i < 2; i++){
									shipButton[xCoord][yCoord - i].setEnabled(false);
								}
							}	
							if(storeDirection == shipDirection.RIGHT){
								for(i = 0; i < 2; i++){
									shipButton[xCoord + i][yCoord].setEnabled(false);
								}
							}	
							if(storeDirection == shipDirection.LEFT){
								for(i = 0; i < 2; i++){
									shipButton[xCoord - i][yCoord].setEnabled(false);
								}
							}	
						}
						else{
						return;	
						}		
					}
				}	
				
		}

	}
}

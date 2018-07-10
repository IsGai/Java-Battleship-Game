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
	public static enum tileHitStatus {
		UNKOWN, MISS, HIT, DESTROYED; 
	}
	public static enum tileShipStatus{
		SHIP, EMPTY;
	}
	public static enum shipDirection{
		HORIZONTAL, VERTICAL;
	}

	private JTextField input1 = new JTextField("", 10);
	private JTextField input2 = new JTextField("", 10);
	JButton enter = new JButton("Continue");
	JButton horizontal = new JButton("Horizontal");
	JButton vertical = new JButton("Vertical");
	JButton[][] shipButton;

	TitleScreen gameState = null;
	
	private int numForX = 0;
	private int numForY = 0;
	
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
					
				}else {
					JButton buttonToGetOut = (JButton) e.getSource();
					buttonToGetOut.setEnabled(false);
					System.out.println(buttonToGetOut.getLocation());
				}
			

		}

	}
}

package battleship.source;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
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
	private int shipTokens = 0;
	private shipDirection select = shipDirection.HORIZONTAL; 
	
	
	private BattleTile[][] player;
	private BattleTile[][] enemy;
	public BattleMap(TitleScreen ts) {
		gameState = ts;
		this.setBackground(null);
		connectListeners();
		playerPrompt(gameState);
		
	}
	
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
	
	private void shipSelect() {
		shipTokens = (int) (numForX * numForY * .15);
		JPanel shipSelectGrid = new JPanel(new GridLayout(numForX, numForY + 1));
		player = new BattleTile[numForX][numForY];
		enemy = new BattleTile[numForX][numForY];
		shipButton = new JButton[numForX][numForY];
		for(int i = 0; i < numForX; i++) {
			for(int j = 0; j < numForY;) {
				shipButton[i][j] = new JButton();
				shipButton[i][j].setName("test");
				shipButton[i][j].addActionListener(new ButtonListener());
			}
		}
		
		tileShipStatus[][] trackSelection =
				new tileShipStatus[numForX][numForY];
		//enemyAI goes here
		while(shipTokens > 0) {
			for(int i = 0; i < numForX; i++) {
				for(int j = 0; j < numForY;) {
					if(trackSelection[i][j] == tileShipStatus.EMPTY) {
						 shipSelectGrid.add(shipButton[i][j]);
					}
				}
			}
		}

	}
	
	private void connectListeners() {
		enter.addActionListener(new ButtonListener());
	}

	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
		String nameOfCallingBtn = e.getActionCommand();

			String stringForX = input1.getText();
			String stringforY = input2.getText();
			

			try {
				numForX = Integer.parseInt(stringForX);
				numForY = Integer.parseInt(stringforY);
			} catch (Exception ex) {
				
			}
			gameState.playMenuStep2();
			shipSelect();

		}

	}
}

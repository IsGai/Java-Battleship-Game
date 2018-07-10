package battleship.source;

import java.awt.Dimension;
import java.awt.Point;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class BattleMechanics {
	public static enum tileShipStatus {
		SHIP, EMPTY;
	}

	private BattleData playerBD;
	private BattleData enemyBD;
	private BattleShipAi bsa;

	public BattleMechanics(BattleData playerBD, BattleData enemyBD) {
		this.playerBD = playerBD;
		this.enemyBD = enemyBD;
		// this.enemyBD = new BattleData(playerBD.getSizeX(), playerBD.getSizeY());
		bsa = new BattleShipAi(this.enemyBD, false); // dont generateShips
	}

	public BattleMechanics(BattleData playerBD) {
		this.playerBD = playerBD;
		this.enemyBD = new BattleData(playerBD.getSizeX(), playerBD.getSizeY());
		bsa = new BattleShipAi(enemyBD, true); // generateShips
	}

	public BattleData.tileHitStatus getEnemyMapTileStatus(int x, int y) {
		return enemyBD.getBattleHitStatus(x, y);
	}

	public BattleData.tileHitStatus playerShotCheck(int x, int y) {
		return shotCheck(enemyBD, x, y);
	}
	
	public BattleData.tileHitStatus enemyShotCheck(int x, int y) {
		return shotCheck(playerBD, x, y);
	}

	private BattleData.tileHitStatus shotCheck(BattleData dataToCheck, int x, int y) {
		BattleData.tileShipStatus check = dataToCheck.getBattleShipLocationStatus(x, y);
		if (check == BattleData.tileShipStatus.SHIP) {
			dataToCheck.setBattleHitStatus(BattleData.tileHitStatus.HIT, x, y);
			dataToCheck.determineAndSetShipBodyPartHit(new Point(x, y));
		} else {
			dataToCheck.setBattleHitStatus(BattleData.tileHitStatus.MISS, x, y);
		}
		return dataToCheck.getBattleHitStatus(x, y);
	}

	public Point getEnemyPointToShoot() {
		Point aiShot;
		do {
			aiShot = bsa.generateAiShot();
		} while (playerBD.getBattleHitStatus(aiShot.x, aiShot.y) != BattleData.tileHitStatus.UNKOWN); // if found an
																										// unkown
		return aiShot;
	}

	public boolean enemyShipsDestroyed() {
		return enemyBD.allDestroyed();
	}

	public boolean playerShipsDestroyed() {
		return playerBD.allDestroyed();
	}

	public void save(String fileName, Dimension screenDimension, String consoleText, int turnCount) {
		try {
			ObjectOutputStream outputStream = new ObjectOutputStream(
					new FileOutputStream("src\\battleship\\source\\Saves\\" + fileName + ".dat"));
			outputStream.writeObject(playerBD);
			outputStream.writeObject(enemyBD);
			outputStream.writeObject(screenDimension);
			outputStream.writeUTF(consoleText);
			outputStream.writeInt(turnCount);
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void dispose() {
		System.exit(0);
	}
}
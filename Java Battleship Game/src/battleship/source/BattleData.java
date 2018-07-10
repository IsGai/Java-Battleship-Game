package battleship.source;

import java.awt.Point;
import java.io.Serializable;

public class BattleData implements Serializable {

	// one for the player
	// one for the enemy(AI)
	public static enum tileHitStatus {
		UNKOWN, HIT, MISS;
	}

	public static enum tileShipStatus {
		SHIP, EMPTY;
	}

	public static enum shipDirection {
		UP, DOWN, LEFT, RIGHT;
	}

	// Array to know what graphic to display on battlescreen
	private tileHitStatus[][] battleHitStatus;

	// Array to know what tiles contain ships
	private tileShipStatus[][] battleShipLocationStatus;

	// Ships contained in the BattleData
	// one of each
	public Carrier cr = new Carrier();
	public BattleShip bs = new BattleShip();
	public SpeedBoat sb = new SpeedBoat();

	// size of BattleData, ie MapSize
	private int sizeX;
	private int sizeY;

	public BattleData(int sizeX, int sizeY) {
		battleHitStatus = new tileHitStatus[sizeX][sizeY];
		battleShipLocationStatus = new tileShipStatus[sizeX][sizeY];
		this.sizeX = sizeX;
		this.sizeY = sizeY;

		// define each map tile hit status
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				battleHitStatus[i][j] = tileHitStatus.UNKOWN;
			}
		}

		// define each map tile to have no ship
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				battleShipLocationStatus[i][j] = tileShipStatus.EMPTY;
			}
		}
	}

	// returns true if all ships have been destroyed
	public boolean allDestroyed() {
		if (bs.checkDestroyed() && sb.checkDestroyed() && cr.checkDestroyed()) {
			return true;
		}
		return false;
	}

	// used in BattleScreen during playersTurn
	// returns name of the ship that have been hit
	public String getShipHitName(Point shot) {
		String shipName = "";
		if (bs.getHeadLocation().equals(shot))
			shipName = "BattleShip";
		if (bs.getBodyLocation().equals(shot))
			shipName = "BattleShip";
		if (bs.getTailLocation().equals(shot))
			shipName = "BattleShip";

		if (sb.getHeadLocation().equals(shot))
			shipName = "SpeedBoat";
		if (sb.getBodyLocation().equals(shot))
			shipName = "SpeedBoat";

		if (cr.getHeadLocation() == shot)
			shipName = "Carrier";
		if (cr.getBodyLocation() == shot)
			shipName = "Carrier";
		if (cr.getBody2Location() == shot)
			shipName = "Carrier";
		if (cr.getTailLocation() == shot)
			shipName = "Carrier";

		return shipName;
	}

	// allows the ships to keep track of their hits
	public void determineAndSetShipBodyPartHit(Point shot) {
		// determine for the BattleShip
		if (bs.getHeadLocation().equals(shot))
			bs.setHeadHit(true);
		if (bs.getBodyLocation().equals(shot))
			bs.setBodyHit(true);
		if (bs.getTailLocation().equals(shot))
			bs.setTailHit(true);

		// determine for the SpeedBoat
		if (sb.getHeadLocation().equals(shot))
			sb.setHeadHit(true);
		if (sb.getBodyLocation().equals(shot))
			sb.setBodyHit(true);

		// determine for the Carrier
		if (cr.getHeadLocation().equals(shot))
			cr.setHeadHit(true);
		if (cr.getBodyLocation().equals(shot))
			cr.setBodyHit(true);
		if (cr.getBody2Location().equals(shot))
			cr.setBody2Hit(true);
		if (cr.getTailLocation().equals(shot))
			cr.setTailHit(true);
	}

	public tileHitStatus getBattleHitStatus(int xLocation, int yLocation) {
		return battleHitStatus[xLocation][yLocation];
	}

	public void setBattleHitStatus(tileHitStatus battleHitStatus, int xLocation, int yLocation) {
		this.battleHitStatus[xLocation][yLocation] = battleHitStatus;
	}

	public tileShipStatus getBattleShipLocationStatus(int xLocation, int yLocation) {// error
		return battleShipLocationStatus[xLocation][yLocation];
	}

	private void setBattleShipLocationStatus(tileShipStatus battleShipLocationStatus, int xLocation, int yLocation) {
		this.battleShipLocationStatus[xLocation][yLocation] = battleShipLocationStatus;
	}

	// make sure a ship wont be place out of map bounds
	// returns false if outOfBounds, else return true
	public boolean checkAvailable(shipDirection direction, int x, int y, int size) {
		if (direction == shipDirection.UP) {
			if ((y - size + 1) < 0) {
				return false;
			}
			for (int i = 0; i < size; i++) {
				if (getBattleShipLocationStatus(x, y - i) == tileShipStatus.SHIP) {
					return false;
				}
			}
			return true;
		}
		if (direction == shipDirection.DOWN) {
			if ((y + size) > sizeY) {
				return false;
			}
			for (int i = 0; i < size; i++) {
				if (getBattleShipLocationStatus(x, y + i) == tileShipStatus.SHIP) {
					return false;
				}
			}
			return true;
		}
		if (direction == shipDirection.LEFT) {
			if ((x - size + 1) < 0) {
				return false;
			}
			for (int i = 0; i < size; i++) {
				if (getBattleShipLocationStatus(x - i, y) == tileShipStatus.SHIP) {
					return false;
				}
			}
			return true;
		}
		if (direction == shipDirection.RIGHT) {
			if ((x + size) > sizeX) {
				return false;
			}
			for (int i = 0; i < size; i++) {
				if (getBattleShipLocationStatus(x + i, y) == tileShipStatus.SHIP) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	// assuming that checkAvailable() was called first
	// to verify that a ship can be placed
	public void setBattleshipObject(shipDirection direction, Ship battleShipObject, int xLocation, int yLocation) {
		battleShipObject.setLocation(new Point(xLocation, yLocation), direction); // create new ship
		if (battleShipObject.getClass() == bs.getClass())
			bs = (BattleShip) battleShipObject; // set blank ship to new ship
		if (battleShipObject.getClass() == sb.getClass())
			sb = (SpeedBoat) battleShipObject; // set blank ship to new ship
		if (battleShipObject.getClass() == cr.getClass())
			cr = (Carrier) battleShipObject; // set blank ship to new ship

		if (direction == shipDirection.UP) {
			for (int i = 0; i < battleShipObject.getSHIP_SIZE(); i++) {
				this.setBattleShipLocationStatus(tileShipStatus.SHIP, xLocation, yLocation - i);
			}
		}
		if (direction == shipDirection.DOWN) {
			for (int i = 0; i < battleShipObject.getSHIP_SIZE(); i++) {
				this.setBattleShipLocationStatus(tileShipStatus.SHIP, xLocation, yLocation + i);
			}
		}
		if (direction == shipDirection.RIGHT) {
			for (int i = 0; i < battleShipObject.getSHIP_SIZE(); i++) {
				this.setBattleShipLocationStatus(tileShipStatus.SHIP, xLocation + i, yLocation);
			}
		}
		if (direction == shipDirection.LEFT) {
			for (int i = 0; i < battleShipObject.getSHIP_SIZE(); i++) {
				this.setBattleShipLocationStatus(tileShipStatus.SHIP, xLocation - i, yLocation);
			}
		}
	}

	public int getSizeX() {
		return sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

}
package battleship.source;

import java.awt.*;

public class BattleShip extends Ship {
	private Point tailLocation;
	private boolean isTailHit;
	private final int SHIP_SIZE = 3;

	public BattleShip() {
		setLocation(new Point(0, 0), BattleData.shipDirection.UP);
		setHeadHit(false);
		setBodyHit(false);
		setTailHit(false);
	}

	public BattleShip(int x, int y, BattleData.shipDirection rotation) {
		setLocation(new Point(x, y), rotation);
		setHeadHit(false);
		setBodyHit(false);
		setTailHit(false);
	}

	public void setLocation(Point tailLocation, BattleData.shipDirection rotation) {
		setTailLocation(tailLocation);
		setRotation(rotation);
		if (rotation == BattleData.shipDirection.UP) {
			setBodyLocation(moveUp(tailLocation));
			setHeadLocation(moveUp(getBodyLocation()));
		}
		if (rotation == BattleData.shipDirection.DOWN) {
			setBodyLocation(moveDown(tailLocation));
			setHeadLocation(moveDown(getBodyLocation()));
		}
		if (rotation == BattleData.shipDirection.LEFT) {
			setBodyLocation(moveLeft(tailLocation));
			setHeadLocation(moveLeft(getBodyLocation()));
		}
		if (rotation == BattleData.shipDirection.RIGHT) {
			setBodyLocation(moveRight(tailLocation));
			setHeadLocation(moveRight(getBodyLocation()));
		}
	}

	public boolean checkDestroyed() {
		if (isHeadHit() == true && isBodyHit() == true && isTailHit() == true)
			return true;
		else
			return false;
	}

	public Point getTailLocation() {
		return tailLocation;
	}

	public void setTailLocation(Point tailLocation) {
		this.tailLocation = tailLocation;
	}

	public boolean isTailHit() {
		return isTailHit;
	}

	public void setTailHit(boolean isTailHit) {
		this.isTailHit = isTailHit;
	}

	public int getSHIP_SIZE() {
		return SHIP_SIZE;
	}

	public String toString() {
		return "BattleShip [getHeadLocation()=" + getHeadLocation() + ", getBodyLocation()=" + getBodyLocation()
				+ ", getTailLocation()=" + getTailLocation() + ", isTailHit()=" + isTailHit() + ", getRotation()="
				+ getRotation() + ", isHeadHit()=" + isHeadHit() + ", isBodyHit()=" + isBodyHit() + ", isDead()="
				+ checkDestroyed() + ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + "]";
	}
}

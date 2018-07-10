package battleship.source;

import java.awt.Point;

public class Carrier extends Ship {
	private Point body2Location;
	private Point tailLocation;
	private boolean isTailHit;
	private boolean isBody2Hit;
	private final int SHIP_SIZE = 4;

	public Carrier() {
		setLocation(new Point(0, 0), BattleData.shipDirection.UP);
		setHeadHit(false);
		setBodyHit(false);
		setTailHit(false);
		setBody2Hit(false);
	}

	public Carrier(int x, int y, BattleData.shipDirection rotation) {
		setLocation(new Point(x, y), rotation);
		setHeadHit(false);
		setBodyHit(false);
		setTailHit(false);
		setBody2Hit(false);
	}

	public void setLocation(Point tailLocation, BattleData.shipDirection rotation) {
		setTailLocation(tailLocation);
		setRotation(rotation);
		if (rotation == BattleData.shipDirection.UP) {
			setBody2Location(moveUp(tailLocation));
			setBodyLocation(moveUp(getBody2Location()));
			setHeadLocation(moveUp(getBodyLocation()));
		}
		if (rotation == BattleData.shipDirection.DOWN) {
			setBody2Location(moveDown(tailLocation));
			setBodyLocation(moveDown(getBody2Location()));
			setHeadLocation(moveDown(getBodyLocation()));
		}
		if (rotation == BattleData.shipDirection.LEFT) {
			setBody2Location(moveLeft(tailLocation));
			setBodyLocation(moveLeft(getBody2Location()));
			setHeadLocation(moveLeft(getBodyLocation()));
		}
		if (rotation == BattleData.shipDirection.RIGHT) {
			setBody2Location(moveRight(tailLocation));
			setBodyLocation(moveRight(getBody2Location()));
			setHeadLocation(moveRight(getBodyLocation()));
		}
	}

	public boolean checkDestroyed() {
		if (isHeadHit() && isBodyHit() && isBody2Hit() && isTailHit())
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

	public Point getBody2Location() {
		return body2Location;
	}

	public void setBody2Location(Point body2Location) {
		this.body2Location = body2Location;
	}

	public boolean isBody2Hit() {
		return isBody2Hit;
	}

	public void setBody2Hit(boolean isBody2Hit) {
		this.isBody2Hit = isBody2Hit;
	}

	public String toString() {
		return "Carrier [checkDestroyed()=" + checkDestroyed() + ", getTailLocation()=" + getTailLocation()
				+ ", isTailHit()=" + isTailHit() + ", getSHIP_SIZE()=" + getSHIP_SIZE() + ", getBody2Location()="
				+ getBody2Location() + ", isBody2Hit()=" + isBody2Hit() + ", getHeadLocation()=" + getHeadLocation()
				+ ", getBodyLocation()=" + getBodyLocation() + ", getRotation()=" + getRotation() + ", isHeadHit()="
				+ isHeadHit() + ", isBodyHit()=" + isBodyHit() + "]";
	}
}
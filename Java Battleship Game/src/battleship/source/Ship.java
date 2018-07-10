package battleship.source;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Ship extends JLabel {
	private Point headLocation;
	private Point bodyLocation;
	private BattleData.shipDirection rotation;
	private boolean isHeadHit;
	private boolean isBodyHit;
	private final int SHIP_SIZE = 2;

	public Ship() {
		setLocation(new Point(0, 0), BattleData.shipDirection.UP);
		setHeadHit(false);
		setBodyHit(false);
	}

	public Ship(int x, int y, BattleData.shipDirection rotation) {
		setLocation(new Point(x, y), rotation);
		setHeadHit(false);
		setBodyHit(false);
	}

	public void setLocation(Point bodyLocation, BattleData.shipDirection rotation) {
		setBodyLocation(bodyLocation);
		setRotation(rotation);
		if (rotation == BattleData.shipDirection.UP)
			setHeadLocation(moveUp(bodyLocation));
		if (rotation == BattleData.shipDirection.DOWN)
			setHeadLocation(moveDown(bodyLocation));
		if (rotation == BattleData.shipDirection.LEFT)
			setHeadLocation(moveLeft(bodyLocation));
		if (rotation == BattleData.shipDirection.RIGHT)
			setHeadLocation(moveRight(bodyLocation));
	}

	public Point moveUp(Point point) {
		Point newPoint = new Point(point);
		newPoint.y -= 1;
		return newPoint;
	}

	public Point moveDown(Point point) {
		Point newPoint = new Point(point);
		newPoint.y += 1;
		return newPoint;
	}

	public Point moveLeft(Point point) {
		Point newPoint = new Point(point);
		newPoint.x -= 1;
		return newPoint;
	}

	public Point moveRight(Point point) {
		Point newPoint = new Point(point);
		newPoint.x += 1;
		return newPoint;
	}

	public boolean checkDestroyed() {
		if (isHeadHit() == true && isBodyHit() == true)
			return true;
		else
			return false;
	}

	public Point getHeadLocation() {
		return headLocation;
	}

	public void setHeadLocation(Point headLocation) {
		this.headLocation = headLocation;
	}

	public Point getBodyLocation() {
		return bodyLocation;
	}

	public void setBodyLocation(Point bodyLocation) {
		this.bodyLocation = bodyLocation;
	}

	public BattleData.shipDirection getRotation() {
		return this.rotation;
	}

	public void setRotation(BattleData.shipDirection rotation) {
		this.rotation = rotation;
	}

	public boolean isHeadHit() {
		return isHeadHit;
	}

	public void setHeadHit(boolean isHeadHit) {
		this.isHeadHit = isHeadHit;
	}

	public boolean isBodyHit() {
		return isBodyHit;
	}

	public void setBodyHit(boolean isBodyHit) {
		this.isBodyHit = isBodyHit;
	}

	public int getSHIP_SIZE() {
		return SHIP_SIZE;
	}

	public String toString() {
		return "Ship [getHeadLocation()=" + getHeadLocation() + ", getBodyLocation()=" + getBodyLocation()
				+ ", getRotation()=" + getRotation() + ", isHeadHit()=" + isHeadHit() + ", isBodyHit()=" + isBodyHit()
				+ ", isDead()=" + checkDestroyed() + "]";
	}
}
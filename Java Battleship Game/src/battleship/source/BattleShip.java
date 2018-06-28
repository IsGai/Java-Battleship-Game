package battleship.source;

import java.awt.*;

import battleship.source.Ship.Rotated;
public class BattleShip extends Ship{
	private Point tailLocation;
	private boolean isTailHit;
	public BattleShip() {
		setLocation(new Point(0,0), Rotated.UP);
		setHeadHit(false);
		setBodyHit(false);
		setTailHit(false);
		setDead(false);
	}
	public BattleShip(int x, int y, Rotated rotation) {
		setLocation(new Point(x,y), rotation);
		setHeadHit(false);
		setBodyHit(false);
		setTailHit(false);
		setDead(false);
	}
	public void setLocation(Point headLocation, Rotated rotation) {
		setHeadLocation(headLocation);
		if(rotation==Rotated.UP) {
			setBodyLocation(moveDown(headLocation));
			setTailLocation(moveDown(getBodyLocation()));
		}
		if(rotation==Rotated.DOWN) {
			setBodyLocation(moveUp(headLocation));
			setTailLocation(moveUp(getBodyLocation()));
		}
		if(rotation==Rotated.LEFT) {
			setBodyLocation(moveRight(headLocation));
			setTailLocation(moveRight(getBodyLocation()));
		}
		if(rotation==Rotated.RIGHT) {
			setBodyLocation(moveLeft(headLocation));
			setTailLocation(moveLeft(getBodyLocation()));
		}
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
	public String toString() {
		return "BattleShip [getTailLocation()=" + getTailLocation() + ", isTailHit()=" + isTailHit()
				+ ", getHeadLocation()=" + getHeadLocation() + ", getBodyLocation()=" + getBodyLocation()
				+ ", getRotation()=" + getRotation() + ", isHeadHit()=" + isHeadHit() + ", isBodyHit()=" + isBodyHit()
				+ ", isDead()=" + isDead() + ", toString()=" + super.toString() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + "]";
	}
}

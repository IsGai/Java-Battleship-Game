package battleship.source;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Ship extends JLabel{
	private Point headLocation;
	private Point bodyLocation;
	private Rotated rotation;
	enum Rotated{UP, DOWN, LEFT, RIGHT};//the rotation determines which way the head is facing
	private boolean isHeadHit;
	private boolean isBodyHit;
	private boolean isDead;
	private ImageIcon shipImage = new ImageIcon(getClass().getResource("Images\\Ship.png"));
	public Ship() {
		setLocation(new Point(0,0), Rotated.UP);
		setHeadHit(false);
		setBodyHit(false);
		setDead(false);
		this.setIcon(shipImage);
	}
	public Ship(int x, int y, Rotated rotation) {
		setLocation(new Point(x,y), rotation);
		setHeadHit(false);
		setBodyHit(false);
		setDead(false);
	}
	public void setLocation(Point headLocation, Rotated rotation) {
		setHeadLocation(headLocation);
		setRotation(rotation);
		if(rotation==Rotated.UP)
			setBodyLocation(moveDown(headLocation));
		if(rotation==Rotated.DOWN)
			setBodyLocation(moveUp(headLocation));
		if(rotation==Rotated.LEFT)
			setBodyLocation(moveRight(headLocation));
		if(rotation==Rotated.RIGHT)
			setBodyLocation(moveLeft(headLocation));
	}
	public Point moveUp(Point point) {
		Point newPoint = new Point(point);
		newPoint.y-=1;
		return newPoint;
	}
	public Point moveDown(Point point) {
		Point newPoint = new Point(point);
		newPoint.y+=1;
		return newPoint;
	}
	public Point moveLeft(Point point) {
		Point newPoint = new Point(point);
		newPoint.x-=1;
		return newPoint;
	}
	public Point moveRight(Point point) {
		Point newPoint = new Point(point);
		newPoint.x+=1;
		return newPoint;
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
	public Rotated getRotation() {
		return this.rotation;
	}
	public void setRotation(Rotated rotation) {
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
	public boolean isDead() {
		return isDead;
	}
	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}
	public String toString() {
		return "Ship [getHeadLocation()=" + getHeadLocation() + ", getBodyLocation()=" + getBodyLocation()
				+ ", getRotation()=" + getRotation() + ", isHeadHit()=" + isHeadHit() + ", isBodyHit()=" + isBodyHit()
				+ ", isDead()=" + isDead() + "]";
	}
}
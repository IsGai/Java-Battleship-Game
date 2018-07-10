package battleship.source;

import java.awt.Point;
import java.util.Random;

public class BattleShipAi {
	private Point shot;
	private Point lastPoint;
	private int xSize;
	private int ySize;
	// private Point[] potentialPoint;
	private BattleData bd;

	public BattleShipAi(BattleData bd, boolean doShipSelect) {
		this.bd = bd;
		this.xSize = bd.getSizeX();
		this.ySize = bd.getSizeY();
		if (doShipSelect) {
			shipSelect(bd.bs);
			shipSelect(bd.sb);
			shipSelect(bd.cr);
		}
	}

	// EASY AI//
	public Point generateAiShot() {
		int xPoint = new Random().nextInt(xSize);
		int yPoint = new Random().nextInt(ySize);
		shot = new Point(xPoint, yPoint);
		return shot;
	}
	// END OF EASY AI//

	// Determines where to place (Ship ship)
	public void shipSelect(Ship ship) {
		Random r = new Random();
		BattleData.shipDirection shipRotation;
		int x, y;
		do {
			shipRotation = BattleData.shipDirection.values()[r.nextInt(4)];
			x = r.nextInt(xSize);
			y = r.nextInt(ySize);
		} while (!bd.checkAvailable(shipRotation, x, y, ship.getSHIP_SIZE()));
		bd.setBattleshipObject(shipRotation, ship, x, y);
	}

	
	//
	//
	//
	public Point generateAiShot(Point lastPointShot) {
		lastPoint = lastPointShot;
		int xPoint = new Random().nextInt(xSize + 1);
		int yPoint = new Random().nextInt(ySize + 1);
		shot = new Point(xPoint, yPoint);

		if (bd.getBattleHitStatus(xPoint, yPoint) == BattleData.tileHitStatus.UNKOWN) {
			// if(onHardMode)
			// lastPoint = shot;
			return shot;
		} else {
			return new Point(-1, -1);
		}
	}

	// use the lastPoint variable down here somewhere
	public Point guessNorth(Point hitPoint) {
		Point pointNorth = hitPoint;
		pointNorth.translate(0, -1);
		if (bd.getBattleHitStatus(pointNorth.x, pointNorth.y) == BattleData.tileHitStatus.UNKOWN) {
			return pointNorth;
		} else {
			return new Point(-1, -1);
		}
	}

	public Point guessSouth(Point hitPoint) {
		Point pointSouth = hitPoint;
		pointSouth.translate(0, 1);
		if (bd.getBattleHitStatus(pointSouth.x, pointSouth.y) == BattleData.tileHitStatus.UNKOWN) {
			return pointSouth;
		} else {
			return new Point(-1, -1);
		}
	}

	public Point guessEast(Point hitPoint) {
		Point pointEast = hitPoint;
		pointEast.translate(1, 0);
		if (bd.getBattleHitStatus(pointEast.x, pointEast.y) == BattleData.tileHitStatus.UNKOWN) {
			return pointEast;
		} else {
			return new Point(-1, -1);
		}
	}

	public Point guessWest(Point hitPoint) {
		Point pointWest = hitPoint;
		pointWest.translate(-1, 0);
		if (bd.getBattleHitStatus(pointWest.x, pointWest.y) == BattleData.tileHitStatus.UNKOWN) {
			return pointWest;
		} else {
			return new Point(-1, -1);
		}
	}

}
/*
 * if(lastPoint==HIT){guessNorth();if(guessNorth==HIT){return guessNorth;}else
 * if(guessNorth()==MISS){guessSouth();
 * 
 * if(guessSouth()==HIT){guessSouth();}
 * 
 * else if(guessSouth==MISS){
 * 
 * guessEast(){ if(guessEast == HIT){ guessEast(); else if (guessEast == MISS){
 * guessWest(); } if(guessWest == Hit){ guessWest(); }
 * 
 * } } }}}}
 */
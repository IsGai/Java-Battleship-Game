package battleship.source;

import javax.swing.JPanel;

public class BattleData extends JPanel {
	public static enum tileHitStatus {
		UNKOWN, MISS, HIT, DESTROYED; 
	}
	public static enum tileShipStatus{
		SHIP, EMPTY;
	}
	public static enum tileShipDirection{
		DOWN, UP, LEFT, RIGHT;
	}
	//Array to know what graphic to display on battlescreen
	private tileHitStatus[][] battleHitStatus;
	
	//Array to know what tiles contain ships
	private tileShipStatus[][] battleShipLocationStatus;
	
	//Array to contain ship objects 
	private Ship[][] battleShipObject;
	
	private int sizeX;
	private int sizeY;
		
	public void BattleMap(int sizeX, int sizeY){
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		for(int i = 0; i < sizeX; i++){
			for(int j = 0; j < sizeY; j++){
				battleHitStatus[i][j] = tileHitStatus.UNKOWN;   
			}
		}
		
		for(int i = 0; i < sizeX; i++){
			for(int j = 0; j < sizeY; j++){
				battleShipLocationStatus[i][j] = tileShipStatus.EMPTY;   
			}
		}
		
		for(int i = 0; i < sizeX; i++){
			for(int j = 0; j < sizeY; j++){
				battleShipObject[i][j] = null;   
			}
		}
	}

	public int getSizeX(){
		return sizeX;
	}
	
	public int getSizeY(){
		return sizeY;
	}
	
	public tileHitStatus getBattleHitStatus(int xLocation, int yLocation) {
		return battleHitStatus[xLocation][yLocation];
	}

	public void setBattleHitStatus(tileHitStatus battleHitStatus, int xLocation, int yLocation) {
		this.battleHitStatus[xLocation][yLocation] = battleHitStatus;
	}

	public tileShipStatus getBattleShipLocationStatus(int xLocation, int yLocation) {
		return battleShipLocationStatus[xLocation][yLocation];
	}

	//Private to prevent confusion with setbattleShipObject
	private void setBattleShipLocationStatus(tileShipStatus battleShipLocationStatus, int xLocation, int yLocation) {
		this.battleShipLocationStatus[xLocation][yLocation] = battleShipLocationStatus;
	}
	
	public Ship getbattleShipObject(int xLocation, int yLocation) {
		return battleShipObject[xLocation][yLocation];
	}

	public void setbattleShipObject(tileShipDirection direction, Ship battleShipObject, int xLocation, int yLocation) {
		if (direction == tileShipDirection.UP){
			for(int i = 0; i < battleShipObject.getSize; i++){
				this.battleShipObject[xLocation][yLocation + i] = battleShipObject;
				this.setBattleShipLocationStatus(tileShipStatus.SHIP, xLocation, yLocation + i);
			}
		}
		if (direction == tileShipDirection.DOWN){
			for(int i = 0; i < battleShipObject.getSize; i++){
				this.battleShipObject[xLocation][yLocation - i] = battleShipObject;
				this.setBattleShipLocationStatus(tileShipStatus.SHIP, xLocation, yLocation - i);
			}			
		}
		if (direction == tileShipDirection.RIGHT){
			for(int i = 0; i < battleShipObject.getSize; i++){
				this.battleShipObject[xLocation + i][yLocation] = battleShipObject;
				this.setBattleShipLocationStatus(tileShipStatus.SHIP, xLocation + i, yLocation);
			}
		}
		if (direction == tileShipDirection.LEFT){
			for(int i = 0; i < battleShipObject.getSize; i++){
				this.battleShipObject[xLocation - i][yLocation] = battleShipObject;
				this.setBattleShipLocationStatus(tileShipStatus.SHIP, xLocation - i, yLocation);
			}
		}
	}
	
	public boolean checkAvailability(int x, int y, int size, shipDirection direction){
		if(direction = shipDirection.UP){
			if ((y - size) < 0){
				return false;
			}
			for(int i = 0; i < size; i++){
				if(!getBattleShipLocationStatus(x , y + i) == tileShipStatus.EMPTY){
					return false;
				}	
			}
			return true;
		}	
		if(direction = shipDirection.DOWN){
			if ((y + size) > sizeY){
				return false;
			}
			for(int i = 0; i < size; i++){
				if(!getBattleShipLocationStatus(x , y - i) == tileShipStatus.EMPTY){
					return false;
				}	
			}
			return true;
		}	
		if(direction = shipDirection.RIGHT){
			if ((x + size) > sizeX){
				return false;
			}
			for(int i = 0; i < size; i++){
				if(!getBattleShipLocationStatus(x + i, y) == tileShipStatus.EMPTY){
					return false;
				}	
			}
			return true;
		}	
		if(direction = shipDirection.LEFT){
			if ((x - size) < 0){
				return false;
			}
			for(int i = 0; i < size; i++){
				if(!getBattleShipLocationStatus(x - i, y) == tileShipStatus.EMPTY){
					return false;
				}	
			}
			return true;
		}
	}
	
}
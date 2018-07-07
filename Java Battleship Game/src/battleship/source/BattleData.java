
public class BattleMap extends JPanel {
	public static enum tileHitStatus {
		UNKOWN, MISS, HIT, DESTROYED; 
	}
	public static enum tileShipStatus{
		SHIP, EMPTY;
	}
	//Array to know what graphic to display on battlescreen
	private tileHitStatus[][] battleHitStatus;
	
	//Array to know what tiles contain ships
	private tileShipStatus[][] battleShipLocationStatus;
	
	//Array to contain ship objects 
	private Ship[][] battleShipInfo;
		
	public void BattleMap(int sizeX, int sizeY){
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
				battleShipInfo[i][j] = null;   
			}
		}
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

	//Private to prevent confusion with setBattleShipInfo
	private void setBattleShipLocationStatus(tileShipStatus battleShipLocationStatus, int xLocation, int yLocation) {
		this.battleShipLocationStatus[xLocation][yLocation] = battleShipLocationStatus;
	}
	
	public Ship getBattleShipInfo(int xLocation, int yLocation) {
		return battleShipInfo[xLocation][yLocation];
	}

	public void setBattleShipInfo(direction direction, Ship battleShipInfo, int xLocation, int yLocation) {
		if (battleShipLocationStatus.getDirection == UP){
			for(int i = 0; i < battleShipLocationStatus.getSize; i++){
				this.battleShipInfo[xLocation][yLocation + i] = battleShipInfo;
				this.setBattleShipLocationStatus(tileShipStatus.SHIP, xLocation, yLocation + i);
			}
		}
		if (battleShipLocationStatus.getDirection == DOWN){
			for(int i = 0; i < battleShipLocationStatus.getSize; i++){
				this.battleShipInfo[xLocation][yLocation - i] = battleShipInfo;
				this.setBattleShipLocationStatus(tileShipStatus.SHIP, xLocation, yLocation - i);
			}			
		}
		if (battleShipLocationStatus.getDirection == RIGHT){
			for(int i = 0; i < battleShipLocationStatus.getSize; i++){
				this.battleShipInfo[xLocation + i][yLocation] = battleShipInfo;
				this.setBattleShipLocationStatus(tileShipStatus.SHIP, xLocation + i, yLocation);
			}
		}
		if (battleShipLocationStatus.getDirection == LEFT){
			for(int i = 0; i < battleShipLocationStatus.getSize; i++){
				this.battleShipInfo[xLocation - i][yLocation] = battleShipInfo;
				this.setBattleShipLocationStatus(tileShipStatus.SHIP, xLocation - i, yLocation);
			}
		}
	}

	
}
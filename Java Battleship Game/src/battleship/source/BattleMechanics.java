
public class BattleMechnanics extends JPanel {
	public static enum tileHitStatus {
		UNKOWN, MISS, HIT, DESTROYED; 
	}
	public static enum tileShipStatus{
		SHIP, EMPTY;
	}
	
	public void BattleMechnanics(BattleData playerBD, BattleData enemyBD){
		BattleView playerInstance = new BattleView(player BD, enemyBD);
		EnemyView enemyInstance = new EnemyView(playerBD, enemyBD);
		boolean gameStillGoing = true;
		while(gameStillGoing){
			//BattleScreen
			gameStillGoing = gameCheck(enemyBD);
			//enemyInstance.turn;
			gameStillGoing = gameCheck(playerBD);
		}
		
	}
	
	public void shotCheck(BattleData dataToCheck, int x, int y){
		tileShipStatus check =
			dataToCheck.getBattleShipLocationStatus(x, y);
		if (check == tileShipStatus.SHIP){
			dataToCheck.setBattleHitStatus(tileHitStatus.HIT, x, y);
		}else{
			dataToCheck.setBattleHitStatus(tileHitStatus.MISS, x, y);
		}
	}
	
	public void gameCheck(BattleData battleData){
		for (int i = 0; i < battleData.getSizeX(); i++){
			for(int i = 0; i < battleData.getSizeY(); i++){
				if(battleData.getBattleShipInfo(x, y) == null){
					continue;
				}
				else {
					if(battleData.getBattleShipInfo(x, y).destroyCheck()){
						battleData.setBattleHitStatus(tileHitStatus.DESTROYED);
					}
				}
			}
		}
	}
	
	
	public void gameOverScreen(){
		
	}
	
}
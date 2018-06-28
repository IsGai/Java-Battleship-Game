import java.awt.Point;
import java.util.Random;


public class BattleShipAi {
	static int mapSizeX = 3;
	static int mapSizeY = 3;
	private static Point shot;
	private static Point[] list = new Point[mapSizeX*mapSizeY];
	
	public static Point generateAiShot() {
		int xPoint = new Random().nextInt(mapSizeX+1);
		int yPoint = new Random().nextInt(mapSizeY+1);
		shot = new Point(xPoint, yPoint);
		
		boolean isInsideArray = false;
		
		for(int i = 0; i < mapSizeX*mapSizeY; i++) {
			if(shot.equals(list[i])) {
				isInsideArray = true;
			} 
		}
		
		for(int i = 0; i < mapSizeX*mapSizeY; i++) {
			if (list[i] == null) {
				
				if(isInsideArray != true) {
					list[i] = shot;
				}
			}
		}
		
		return shot;
			
	}

	public static void main(String[] args) {
		Point shoot = new Point();
		shoot = generateAiShot();
		System.out.println(shoot);
		
	}
	
}

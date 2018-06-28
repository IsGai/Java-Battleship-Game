package battleship.source;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import battleship.source.BattleMap.tileHitStatus;
import battleship.source.BattleMap.tileShipStatus;

public class BattleTile{
	static private ImageIcon iconUnkown = new ImageIcon("BattleshipIcon.png");
	static private ImageIcon iconHit = new ImageIcon("BattleshipIcon.png");
	static private ImageIcon iconDestroyed = new ImageIcon("BattleshipIcon.png");
	static private ImageIcon iconMiss = new ImageIcon("BattleshipIcon.png");
	private JButton tileButton;
	private tileShipStatus battleTileShipStatus;
	

	
	public BattleTile() {
		tileIconSet(tileHitStatus.UNKOWN);
		tileShipSet(tileShipStatus.EMPTY);
	}
	
	private void tileIconSet(tileHitStatus tileCheck) {
		if (tileCheck == tileHitStatus.UNKOWN) {
			tileButton = new JButton(iconUnkown);
		} else if (tileCheck == tileHitStatus.HIT) {
			tileButton = new JButton(iconHit);
		}else if (tileCheck == tileHitStatus.MISS) {
			tileButton = new JButton(iconMiss);
		}else if (tileCheck == tileHitStatus.DESTROYED) {
			tileButton = new JButton(iconDestroyed);
		}
	
	}
	
	private void tileShipSet(tileShipStatus tileCheck) {
		if (tileCheck == tileShipStatus.SHIP) {
			battleTileShipStatus = tileShipStatus.SHIP;
		} else if (tileCheck == tileShipStatus.EMPTY) {
			battleTileShipStatus = tileShipStatus.EMPTY;
		}
	}
	
	public tileShipStatus getTileShipStatus() {
		return this.battleTileShipStatus;
	}
	
	public void setTileShipStatus(tileShipStatus set) {
		this.battleTileShipStatus = set;
	}
}
	
	

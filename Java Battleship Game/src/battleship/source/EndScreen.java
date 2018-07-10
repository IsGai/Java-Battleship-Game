package battleship.source;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class EndScreen extends JPanel {
	private JButton button = new JButton();
	private TitleScreen ts;

	public EndScreen(TitleScreen ts, boolean playerWon) {
		this.ts = ts;
		setBackground(null);
		setLayout(new BorderLayout());
		button.setBackground(null);
		button.setFont(ts.fontResize(25));
		button.setForeground(TitleScreen.fontLabelColor);
		if (playerWon) {
			button.setText("Congratulations You Won!");
		}
		if (!playerWon) {
			button.setText("Congratulations You Lost!");
		}
		add(button);
		timer();
	}

	private void timer() {
		Thread t = new Thread() {
			public void run() {
				try {
					Thread.sleep(5000);
					int x = 10;
					do {
						Thread.sleep(1000);
						if (x == 8 || x == 7)
							button.setText("Created By: Huy Nguyen     " + x-- + "...");
						else if (x == 6 || x== 5)
							button.setText("Created By: Isaac Gaiovnik " + x-- + "...");
						else if (x == 4 || x == 3)
							button.setText("Created By: William Xiong  " + x-- + "...");
						else
							button.setText("Returning to Main Menu in  " + x-- + "...");
					} while (x >= 0);

					ts.mainMenu();
				} catch (Exception e) {

				}
			}
		};

		t.start();
	}
}

package battleship.source;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class CreditsScreen extends JPanel {
	private JLabel createdBy = new JLabel("Created by: ");
	private JLabel huyNguyen = new JLabel("Huy Nguyen");
	private JLabel isaacGaiovnik = new JLabel("Isaac Gaiovnik");
	private JLabel williamXiong = new JLabel("William Xiong");
	private JButton backButton = new JButton("<");
	public CreditsScreen(TitleScreen ts) {
		this.setLayout(new GridBagLayout());
		this.setBackground(null);

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.CENTER;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.gridy = 0;
		
		
		createdBy.setFont(new Font("Arial", 0, 25));
		createdBy.setForeground(Color.white);
		huyNguyen.setFont(new Font("Arial", 0, 60));
		huyNguyen.setForeground(Color.white);
		isaacGaiovnik.setFont(new Font("Arial", 0, 60));
		isaacGaiovnik.setForeground(Color.white);
		williamXiong.setFont(new Font("Arial", 0, 60));
		williamXiong.setForeground(Color.white);
		
		c.anchor = GridBagConstraints.NORTHWEST;
		this.add(backButton, c);

		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		this.add(createdBy, c);
		c.gridy = 2;
		c.weighty=0.3;
		this.add(huyNguyen, c);
		c.gridy = 3;
		this.add(isaacGaiovnik, c);
		c.gridy = 4;
		this.add(williamXiong, c);
		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ts.mainMenu();
			}
		});
	}

}

package battleship.source;
import javax.swing.*;
import java.awt.*;
public class CreditsScreen extends JPanel {
	private JLabel createdBy = new JLabel("Created by: ");
	private JLabel huyNguyen = new JLabel("Huy Nguyen");
	private JLabel isaacGaiovnik = new JLabel("Isaac Gaiovnik");
	private JLabel williamXiong = new JLabel("WilliamXiong");
	public CreditsScreen() {
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.black);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.CENTER;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.gridy = 0;
		createdBy.setFont(new Font("Arial", 0, 25));
		huyNguyen.setFont(new Font("Arial", 0, 60));
		isaacGaiovnik.setFont(new Font("Arial", 0, 60));
		williamXiong.setFont(new Font("Arial", 0, 60));
		this.add(createdBy, c);
		c.gridy = 1;
		c.weighty=0.3;
		this.add(huyNguyen, c);
		c.gridy = 2;
		this.add(isaacGaiovnik, c);
		c.gridy = 3;
		this.add(williamXiong, c);
	}
}

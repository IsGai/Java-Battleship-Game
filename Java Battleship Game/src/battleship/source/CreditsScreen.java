package battleship.source;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
public class CreditsScreen extends JPanel{
	private JLabel createdBy = new JLabel("Created by: ");
	private JLabel huyNguyen = new JLabel("Huy Nguyen");
	private JLabel isaacGaiovnik = new JLabel("Isaac Gaiovnik");
	private JLabel williamXiong = new JLabel("William Xiong");
	private JButton backButton = new JButton("<");
	
	private JPanel glassPane;
	private JPanel creditsPanel;
	private TitleScreen ts;
	public CreditsScreen(TitleScreen ts) {
		this.ts = ts;
		this.setBackground(null);
		this.setLayout(new BorderLayout());
		
		glassPane = (JPanel) ts.getGlassPane();
		
		creditsPanel = new JPanel(new GridBagLayout());
		creditsPanel.setBackground(null);
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.CENTER;
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.gridy = 0;
		
		
		createdBy.setFont(ts.fontResize(20));
		createdBy.setForeground(TitleScreen.fontLabelColor);
		huyNguyen.setFont(ts.fontResize(8));
		huyNguyen.setForeground(TitleScreen.fontLabelColor);
		isaacGaiovnik.setFont(ts.fontResize(8));
		isaacGaiovnik.setForeground(TitleScreen.fontLabelColor);
		williamXiong.setFont(ts.fontResize(8));
		williamXiong.setForeground(TitleScreen.fontLabelColor);
		
		c.anchor = GridBagConstraints.NORTHWEST;
		creditsPanel.add(backButton, c);

		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		creditsPanel.add(createdBy, c);
		c.gridy = 2;
		c.weighty=0.3;
		creditsPanel.add(huyNguyen, c);
		c.gridy = 3;
		creditsPanel.add(isaacGaiovnik, c);
		c.gridy = 4;
		creditsPanel.add(williamXiong, c);
		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeGlassPane();
				ts.mainMenu();
			}
		});
		
		add(creditsPanel);
		glassPaneSetup();
		Image icon = new ImageIcon("src/battleship/source/Images/CreditsScreenBackground.gif").getImage();
		JLabel matrixIcon = new JLabel();
		matrixIcon.setIcon(new ImageIcon(icon));
		add(matrixIcon);
	}
	private void glassPaneSetup() {
		glassPane.setVisible(true);
		glassPane.setBackground(null);
		glassPane.setLayout(new BoxLayout(glassPane, BoxLayout.Y_AXIS));
		glassPane.add(backButton);//re-add backbutton to top panel(glassPane)
		glassPane.add(creditsPanel);//re-add creditsScreen over the matrixs Gif
		creditsPanel.setOpaque(false);
	}
	private void removeGlassPane() {
		glassPane.setVisible(false);
		glassPane.removeAll();
		glassPane.validate();
	}
}

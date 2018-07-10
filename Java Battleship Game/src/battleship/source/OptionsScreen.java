package battleship.source;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
public class OptionsScreen extends JPanel implements ActionListener, ChangeListener{
	private JComboBox<String> resolution;
	private String[] sizes = {"1024x576", "1152x648", "1280x720", "1366x768", "1600x900", "1920x1080",
			"1280x800", "1440x900", "1680x1050", "1920x1200", "800x600", "700x550", "500x500"};

	private JSlider red = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
	private JSlider green = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
	private JSlider blue = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
	
	private TitleScreen ts;
	private Color tsBackground;
	
	private JLabel optionsLabel = new JLabel("Options");
	private Color optionsColor;
	private JButton backButton = new JButton("<");
	private JButton cancelButton = new JButton("Cancel");
	private JButton applyButton = new JButton("Apply");
	
	public OptionsScreen(TitleScreen ts) {
		this.ts = ts;
		this.tsBackground = ts.getContentPane().getBackground();
		this.setLayout(new GridBagLayout());
		this.setBackground(null);
		
		checkSizes();
		setGUI();
		setGUIListeners();
	}
	private void actions(Object source, String command) {
		if(source == red || source == green || source == blue) {
			ts.changeBackground(red.getValue(), green.getValue(), blue.getValue());
			optionsColor =  new Color(Math.abs(red.getValue()-255),
								Math.abs(green.getValue()-255),
								Math.abs(blue.getValue()-255));
			optionsLabel.setForeground(optionsColor);
			TitleScreen.fontLabelColor = optionsColor;
		}
		if(source == applyButton) {
			ts.changeSize(StringToDimension(resolution.getSelectedItem().toString()));
			ts.mainMenu();
		}
		if(source == cancelButton) {
			ts.changeBackground(tsBackground);//change background back to original
			ts.mainMenu();
		}
		if(source == backButton) {
			ts.changeBackground(tsBackground);//change background back to original
			ts.mainMenu();
		}
	}
	private void setGUI() {
		resolution = new JComboBox<String>(sizes);
		
		optionsLabel.setFont(ts.fontResize(10));
		optionsLabel.setBackground(null);
		red.setBackground(null);
		green.setBackground(null);
		blue.setBackground(null);

		
		GridBagConstraints c = new GridBagConstraints();
		//adding components
		//y0
		c.fill = GridBagConstraints.CENTER;
		c.weightx = 0.1;
		c.weighty = 0.1;
		c.gridy = 0;
		c.anchor = GridBagConstraints.NORTHWEST;
		this.add(backButton,c);
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.9;
		c.weighty = 1.0;
		c.ipadx = 100;
		c.gridx = 1;
		this.add(optionsLabel, c);
		//y1
		c.fill = GridBagConstraints.NONE;
		c.ipadx = 0;
		c.weightx = 1.0;
		c.gridx = 0;
		c.gridy = 1;
		this.add(red,c);
		red.setValue(tsBackground.getRed());
		c.gridx = 1;
		c.gridy = 1;
		this.add(resolution,c);
		//y2
		c.gridx = 0;
		c.gridy = 2;
		this.add(green,c);
		green.setValue(tsBackground.getGreen());
		c.gridx = 1;
		c.gridy = 2;
		//this.add(new JComboBox<String>(), c); //extra
		//y3
		c.gridx = 0;
		c.gridy = 3;
		this.add(blue,c);
		blue.setValue(tsBackground.getBlue());
		c.gridx = 1;
		c.gridy = 3;
		//this.add(new JComboBox<String>(), c); //extra
		
		//make "cancel" and "apply" buttons bigger and fatter
		c.gridx = 0;
		c.gridy = 4;
		c.ipady = 20;//taller
		c.ipadx = 100;//wider
		this.add(cancelButton, c);
		c.gridx = 1;
		this.add(applyButton, c);
		
		optionsColor =  new Color(Math.abs(red.getValue()-255),
				Math.abs(green.getValue()-255),
				Math.abs(blue.getValue()-255));
		optionsLabel.setForeground(optionsColor);
	}
	private void setGUIListeners(){
		resolution.addActionListener(this);
		backButton.addActionListener(this);
		cancelButton.addActionListener(this);
		applyButton.addActionListener(this);
		red.addChangeListener(this);
		green.addChangeListener(this);
		blue.addChangeListener(this);
	}
	/* this method makes is so that only the highest display setting
	 * will be displayed on in the resolution JComboBox
	 * anything highest will be disposed
	 * this method also sorts from highest to lowest
	 */ 
	private void checkSizes(){//need to clean up code
		Dimension screenMax = Toolkit.getDefaultToolkit().getScreenSize();
		
		Dimension[] dims = new Dimension[sizes.length];
		for(int x=0;x<sizes.length;x++)
			dims[x] = StringToDimension(sizes[x]);
		
		int i, min_idx, j;
		for (i = 0; i < dims.length-1; i++)
	    {
	        // Find the minimum element in unsorted array
	        min_idx = i;
	        for (j = i+1; j < dims.length; j++)
	        	if (dims[j].width > dims[min_idx].width) 
	        		min_idx = j;
	        // Swap the found minimum element with the first element
	        Dimension temp = dims[min_idx];
	        dims[min_idx] = dims[i];
	        dims[i] = temp;
	    }
		int dimsLength = dims.length;
		for(int x=0;x<dims.length;x++){
			if(dims[x].width >= screenMax.width || dims[x].height >= screenMax.height){
				dims[x] = null;
				dimsLength--;
			}
		}
		Dimension[] newDim = new Dimension[dimsLength+1];
		int dimCount = 1;
		newDim[0] = screenMax;
		for(int x=0;x<dims.length;x++){
			if(dims[x]!=null){
				newDim[dimCount] = dims[x];
				dimCount++;
			}
		}
		
		sizes = new String[dimCount];
		for(int x=0;x<sizes.length;x++)
			sizes[x] = DimensionToString(newDim[x]);
	}
	private String DimensionToString(Dimension dimension){
		return dimension.width + "x" + dimension.height;
	}
	private Dimension StringToDimension(String text){
		return new Dimension(Integer.parseInt(text.split("x")[0]), Integer.parseInt(text.split("x")[1]));
	}
	public void actionPerformed(ActionEvent e) {
		actions(e.getSource(), e.getActionCommand());
	}
	public void stateChanged(ChangeEvent e) {
		actions(e.getSource(), "");
	}
}

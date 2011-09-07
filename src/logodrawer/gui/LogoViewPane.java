package logodrawer.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class LogoViewPane extends JPanel {

	/////////////////////////////
	// Private Instance Variables
	
	private BufferedImage bi;

	private static final long serialVersionUID = -3161799680762273792L;

	///////////////////
	// Public Interface
	public void setBi(BufferedImage bi) {
		
		this.bi = bi;
		this.setPreferredSize( new Dimension(bi.getWidth(),bi.getHeight()));
		this.updateUI();
		
	}

	//////////////////
	// Private Methods
	
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage((Image) bi, 0, 0, null);

	}
	

	
	
	
}

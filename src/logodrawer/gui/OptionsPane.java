package logodrawer.gui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import logodrawer.LogoImageLayout;
import logodrawer.PositionValues;


public class OptionsPane extends JPanel {

	/////////////////////////////
	// Private Instance Variables
	LogoImageLayout layout;
	
	/////////////
	// Components
	JTextField txtLogoHeader;
	JTextField txtLogoHeight;
	JTextField txtRowHeight;
	JTextField txtRightSpacer;
	JTextField txtPosWidth;
	JTextField txtRulerColumn;
	JTextField txtPositionsPerLine;
	JTextField txtList;
	JTextField fontName;
	JTextField fontSize;
	
	
	
	//////////////
	// Constructor
	
	public OptionsPane() {
		super();
		this.layout = new LogoImageLayout();
		this.CreateGUI();
	}
	
	//////////////////
	// Private Methods
	
	private void CreateGUI() {
		this.setLayout(new FlowLayout());
		
		this.add(new JLabel("Logo Header"));
		txtLogoHeader = new JTextField("100",5);
		this.add(txtLogoHeader);
		
		
		
	}
	
	int 						logoHeight;
	int 						rowHeight;
	int 						rightSpacer;
	int							posWidth;
	int 						rulerColumn;
	int							positionsPerLine;
	List<PositionValues> 		list;
	Font						font;
	
}

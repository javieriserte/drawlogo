package logodrawer.gui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import logodrawer.LogoImageLayout;
import logodrawer.PositionValues;


public class OptionsPane extends JPanel {


	private static final long serialVersionUID = 5388133283039970241L;

	/////////////////////////////
	// Private Instance Variables
	LogoImageLayout layout;
	Font			selectedFont;
	
	/////////////
	// Components
	JTextField 		txtLogoHeader;
	JTextField 		txtLogoHeight;
	JTextField 		txtRowHeight;
	JTextField 		txtRightSpacer;
	JTextField 		txtPosWidth;
	JTextField 		txtRulerColumn;
	JTextField 		txtPositionsPerLine;
	JTextField 		txtList;
	
	JButton			btDraw;
	JButton			btExport;
	
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
		
		this.add(new JLabel("Logo Height"));
		txtLogoHeight = new JTextField("500",5);
		this.add(txtLogoHeight);

		this.add(new JLabel("Logo Row Height"));
		txtRowHeight = new JTextField("700",5);
		this.add(txtRowHeight);
		
		this.add(new JLabel("Right Spacer:"));
		txtRightSpacer = new JTextField("100",5);
		this.add(txtRightSpacer);
		
		this.add(new JLabel("Pos Width"));
		txtPosWidth = new JTextField("100",5);
		this.add(txtPosWidth);
		
		this.add(new JLabel("Ruler Column"));
		txtRulerColumn = new JTextField("100",5);
		this.add(txtRulerColumn);
		
		this.add(new JLabel("Positiones Per Line"));
		txtPositionsPerLine = new JTextField("20",5);
		this.add(txtPositionsPerLine);
		
		txtList = new JTextField("100",5);

		
		
		
		
		
		
		
		
		
		
		
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

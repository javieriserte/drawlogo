package logodrawer.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
	
	JPanel 			leftPane;
	JPanel			rightPane;
	
	JTextField 		txtLogoHeader;
	JTextField 		txtLogoHeight;
	JTextField 		txtRowHeight;
	JTextField 		txtRightSpacer;
	JTextField 		txtPosWidth;
	JTextField 		txtRulerColumn;
	JTextField 		txtPositionsPerLine;
	JTextField 		txtList;
	
	
	
	JButton			btDraw;
	JButton			btLoadFile;
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
		GridBagLayout layout = new GridBagLayout();
		
		
		layout.rowHeights = new int[]{0,25,25,25,0};
		layout.rowWeights = new double[]{0,1,1,1,0};
		layout.columnWidths = new int[]{0,40,0};
		layout.columnWeights = new double[]{0,1,0};
		
//		this.setLayout(layout);
		
		
		
//		this.setLayout(new BorderLayout());
		
		leftPane = new JPanel();
		leftPane.setLayout(layout);
		btDraw = new JButton("Draw Logo");
		
		btLoadFile = new JButton("Load a file");
		btExport = new JButton("Export JPG fle");
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		c.gridx = 1;
		c.gridy = 1;
		leftPane.add(btDraw, c);
		
		c.gridy = 2;
		leftPane.add(btLoadFile, c);

		c.gridy = 3;
		leftPane.add(btExport, c);

		this.add(leftPane);
		
		
//		this.add(new JLabel("Logo Header"));
//		txtLogoHeader = new JTextField("100",3);
//		this.add(txtLogoHeader);
//		
//		this.add(new JLabel("Logo Height"));
//		txtLogoHeight = new JTextField("500",3);
//		this.add(txtLogoHeight);
//
//		this.add(new JLabel("Logo Row Height"));
//		txtRowHeight = new JTextField("700",3);
//		this.add(txtRowHeight);
//		
//		this.add(new JLabel("Right Spacer:"));
//		txtRightSpacer = new JTextField("100",3);
//		this.add(txtRightSpacer);
//		
//		this.add(new JLabel("Pos Width"));
//		txtPosWidth = new JTextField("100",3);
//		this.add(txtPosWidth);
//		
//		this.add(new JLabel("Ruler Column"));
//		txtRulerColumn = new JTextField("100",3);
//		this.add(txtRulerColumn);
//		
//		this.add(new JLabel("Positiones Per Line"));
//		txtPositionsPerLine = new JTextField("20",3);
//		this.add(txtPositionsPerLine);
//		
//		txtList = new JTextField("100",5);
		
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

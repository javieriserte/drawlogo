package logodrawer.gui;

import java.awt.FileDialog;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.metal.MetalBorders.OptionDialogBorder;

import fastaIO.FastaFilter;

import logodrawer.LogoImageLayout;
import logodrawer.PositionValues;


public class OptionsPane extends JPanel {


	private static final long serialVersionUID = 5388133283039970241L;

	/////////////////////////////
	// Private Instance Variables
	LogoImageLayout layout;
	Font			selectedFont;
	LogoDrawerGui   parent;
	File			selectedFile;
	
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
	JButton			btFontSelect;
	JLabel			jlFileSelected;
	
	
	JButton			btDraw;
	JButton			btLoadFile;
	JButton			btExport;
	
	//////////////
	// Constructor
	
	public OptionsPane(LogoDrawerGui parent) {
		super();
		this.parent = parent;
		this.selectedFont =  new Font("Arial", Font.BOLD, 10);
		this.layout = new LogoImageLayout();
		this.CreateGUI();
	}
	
	///////////////////
	// Public Interface
	
	public Font getFont() {
		return this.selectedFont;
	}
	
	public File getFile() {
		return this.selectedFile;
	}
	
	public int getLogoHeaderSize() {
		return (int) Integer.parseInt(this.txtLogoHeader.getText());
	}

	public int getLogoHeightSize() {
		return (int) Integer.parseInt(this.txtLogoHeight.getText());
	}

	public int getRowHeightSize() {
		return (int) Integer.parseInt(this.txtRowHeight.getText());
	}

	public int getRightSpacerSize() {
		return (int) Integer.parseInt(this.txtRightSpacer.getText());
	}

	public int getPosWidthSize() {
		return (int) Integer.parseInt(this.txtPosWidth.getText());
	}

	public int getRulerColumnSize() {
		return (int) Integer.parseInt(this.txtRulerColumn.getText());
	}

	public int getPositionsPerLine() {
		return (int) Integer.parseInt(this.txtPositionsPerLine.getText());
	}
	
	//////////////////
	// Private Methods
	
	private void CreateGUI() {
		GridBagLayout layout = new GridBagLayout();
		
		layout.rowHeights = new int[]{20};
		layout.rowWeights = new double[]{1};
		layout.columnWidths = new int[]{150,300};
		layout.columnWeights = new double[]{0,1};
		this.setLayout(layout);
	
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;

		leftPane = this.createLeftPaneGUI();
		this.add(leftPane,c);
		
		c.gridx = 1;
		rightPane = this.createRightPaneGUI();
		this.add(rightPane,c);
		
	}
	
	private JPanel createLeftPaneGUI() {
		JPanel leftPane = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		
		layout.rowHeights = new int[]{25,25,25,0};
		layout.rowWeights = new double[]{0,0,0,1};
		layout.columnWidths = new int[]{40};
		layout.columnWeights = new double[]{1};
		
		leftPane.setLayout(layout);
		
		btDraw = new JButton("Draw Logo");
		btDraw .addActionListener(new buttonDrawLogoListener());
		btLoadFile = new JButton("Load a file");
		btLoadFile.addActionListener(new buttonSelectFile())   ;
		btExport = new JButton("Export JPG fle");
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		leftPane.add(btDraw, c);
		
		c.gridy = 1;
		leftPane.add(btLoadFile, c);

		c.gridy = 2;
		leftPane.add(btExport, c);

		
		return leftPane;
	}
	
	private JPanel createRightPaneGUI() {
		JPanel rightPane = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		
		layout.rowHeights = new int[]{25,25,25,25,0};
		layout.rowWeights = new double[]{0,0,0,0,1};
		layout.columnWidths = new int[]{50,25,50,25,50,25,0};
		layout.columnWeights = new double[]{0,0,0,0,0,0,1};

		rightPane.setLayout( layout);
		
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		c.fill = GridBagConstraints.BOTH;

		c.gridx = 0; c.gridy = 0;
		rightPane.add(new JLabel("Logo Header"),c);
		txtLogoHeader = new JTextField("10",3);
		c.gridx = 1; c.gridy = 0;
		rightPane.add(txtLogoHeader,c);
		
		c.gridx = 2; c.gridy = 0;
		rightPane.add(new JLabel("Logo Height"),c);
		txtLogoHeight = new JTextField("170",3);
		c.gridx = 3; c.gridy = 0;
		rightPane.add(txtLogoHeight,c);

		c.gridx = 4; c.gridy = 0;
		rightPane.add(new JLabel("Logo Row Height"),c);
		txtRowHeight = new JTextField("200",3);
		c.gridx = 5; c.gridy = 0;
		rightPane.add(txtRowHeight,c);
		
		c.gridx = 0; c.gridy = 1;
		rightPane.add(new JLabel("Right Spacer:"),c);
		txtRightSpacer = new JTextField("20",3);
		c.gridx = 1; c.gridy = 1;
		rightPane.add(txtRightSpacer,c);
		
		c.gridx = 2; c.gridy = 1;
		rightPane.add(new JLabel("Pos Width"),c);
		txtPosWidth = new JTextField("25",3);
		c.gridx = 3; c.gridy = 1;
		rightPane.add(txtPosWidth,c);
		
		c.gridx = 4; c.gridy = 1;
		rightPane.add(new JLabel("Ruler Column"),c);
		txtRulerColumn = new JTextField("30",3);
		c.gridx = 5; c.gridy = 1;
		rightPane.add(txtRulerColumn,c);
		
		c.gridx = 0; c.gridy = 2;
		rightPane.add(new JLabel("Positiones Per Line:"),c);
		txtPositionsPerLine = new JTextField("20",3);
		c.gridx = 1; c.gridy = 2;
		rightPane.add(txtPositionsPerLine,c);
		
		c.gridx = 2; c.gridy = 2;
		rightPane.add(new JLabel("Choose Font:"),c);
		btFontSelect = new JButton(this.getSelectedFont().getFontName());
		btFontSelect.addActionListener(new buttonFontChooserListener());
		c.gridx = 3; c.gridy = 2;
		rightPane.add(btFontSelect,c);
		
		c.gridx = 0; c.gridy = 3;
		rightPane.add(new JLabel("Loaded File:"),c);
		jlFileSelected = new JLabel("None");
		c.gridx = 1; c.gridy = 3;
		rightPane.add(jlFileSelected ,c);
		
		return rightPane;
	}

	private Font getSelectedFont() {
		return this.selectedFont;
	}
	
	//////////////////////////
	// Auxiliary Classes
	
	private class buttonFontChooserListener implements ActionListener{
		@Override public void actionPerformed(ActionEvent e) {
			Font sf = FontChooser.openFontChooser();
			if (sf!=null) {
				OptionsPane.this.selectedFont = sf;
				OptionsPane.this.btFontSelect.setText(sf.getFontName());
			}
		}
	}
	
	
	private class buttonDrawLogoListener implements ActionListener {

		@Override public void actionPerformed(ActionEvent e) {
			parent.drawLogo();
		}
	}
	
	private class buttonSelectFile implements ActionListener {
 
		@Override public void actionPerformed(ActionEvent e) {
			
			FileFilter fastaFilter = new FileFilter() {
				@Override public String getDescription() { return "Fasta files"; }
				@Override public boolean accept(File f) { return f.isDirectory() || (new FastaFilter()).accept(f); }
			}; 
				// Creates a filter to choose only fasta files.
			

			
//			JFileChooser iFile = new JFileChooser(new java.io.File( "." ));
			JFileChooser iFile = new JFileChooser();
			
			iFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
			iFile.setMultiSelectionEnabled(false);
			iFile.setDialogTitle("Select Fasta Alignment");
			iFile.setDialogType(JFileChooser.OPEN_DIALOG);
			iFile.setFileFilter(fastaFilter);
			iFile.showOpenDialog(OptionsPane.this);

			OptionsPane.this.selectedFile = iFile.getSelectedFile();

			OptionsPane.this.jlFileSelected.setText(OptionsPane.this.selectedFile.getName());

		}
		
	}
	
}

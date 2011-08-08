package logodrawer.gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicBorders;

import sun.font.FontFamily;

public class FontChooser extends javax.swing.JDialog {

	//////////////////////////
	// Private Class Variables
	private static String sampleText = "El murcielago hindú";
	
	/////////////////////////////
	// Private Instance Variables
	private Font currentFont;
	private FontContainer result;
	
	
	/////////////
	// Components
	
	public Font getCurrentFont() {
		return currentFont;
	}

	public FontContainer getResult() {
		return result;
	}

	private JComboBox jcbFontName;
	private JComboBox jcbFontStyle;
	private JSpinner  jsFontSize;
	private JTextArea jtpSampleText;
	private JButton   jbOK;
	private JButton   jbCancel;
	
	//////////////////
	// Main Executable
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame();
				FontContainer f = new FontChooser.FontContainer();
				FontChooser inst = new FontChooser(frame,f);
				inst.setVisible(true);
				inst.setModal(true);
				System.out.println(f.result);
			}
		});
		
	}
	
	//////////////
	// Constructor
	public FontChooser(JFrame frame, FontContainer result) {
		super(frame);
		this.result = result;
		this.createGUI();
		this.setModalityType(ModalityType.APPLICATION_MODAL);
	}
	
	
	//////////////////
	// Private Methods
	private void createGUI() {
		try 
		{
			FontModificationListener fl = new FontModificationListener();
			this.setLayout(null);
			this.setSize(300, 150);
			this.setResizable(false);
			this.setTitle("Font Chooser");
			
			jbOK = new JButton("OK");
			jbOK.setSize(80, 27);
			jbOK.setLocation(210, 5);
			jbOK.getInsets().set(0, 0, 0, 0);
			jbOK.addActionListener(new OKButtonPressed());
			
			jbCancel = new JButton("Cancel");
			jbCancel.setSize(80, 27);
			jbCancel.setLocation(210, 40);
			jbCancel.getInsets().set(0, 0, 0, 0);
			jbCancel.addActionListener(new CancelButtonPressed());
			
			jcbFontName = new JComboBox(this.getSystemFontNames());
			jcbFontName.setLocation(10, 5);
			jcbFontName.setSize(190, 27);
			jcbFontName.addActionListener(fl);
			
			jcbFontStyle = new JComboBox(new String[] {"Plain","Bold","Italic","Bold Italic"});
			jcbFontStyle.setLocation(10, 40);
			jcbFontStyle.setSize(90,27);
			jcbFontStyle.addActionListener(fl);
			
			jsFontSize = new JSpinner();
			SpinnerNumberModel model = new SpinnerNumberModel();
			model.setValue(10);
			model.setMinimum(1);
			model.setMaximum(72);
			model.setStepSize(1);
			jsFontSize.setModel(model);
			jsFontSize.setLocation(110, 40);
			jsFontSize.setSize(90,27);
			jsFontSize.addChangeListener(fl);
			
			
			this.currentFont = this.getFontFromSelectedValues();
			
			jtpSampleText = new JTextArea(FontChooser.sampleText);
			this.redrawSampleText();
			jtpSampleText.setLocation(10, 75);
			jtpSampleText.setSize(280,35);
			jtpSampleText.setBorder(BasicBorders.getTextFieldBorder());
			
			this.add(jtpSampleText);
			this.add(jsFontSize);
			this.add(jcbFontStyle);
			this.add(jbCancel);
			this.add(jbOK);
			this.add(jcbFontName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private Font getFontFromSelectedValues() {
		String fontname = (String) jcbFontName.getSelectedItem();
		int fontstyleIndex = jcbFontStyle.getSelectedIndex();
		int fontStyle=0;
		int fontSize = (Integer) jsFontSize.getValue();
		switch (fontstyleIndex) {
			case 0: fontStyle = Font.PLAIN;					break;
			case 1: fontStyle = Font.BOLD;					break;
			case 2: fontStyle = Font.ITALIC;				break;
			case 3: fontStyle = Font.BOLD + Font.ITALIC;	break;		
			default: fontStyle = Font.PLAIN;				break;
		}
		return new Font(fontname, fontStyle, fontSize);
	}

	private String[] getSystemFontNames() {
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    return ge.getAvailableFontFamilyNames(); // Get the fonts
	}
	
	private void redrawSampleText() {
		Font font = getFontFromSelectedValues();
		jtpSampleText.setFont(font);
	}
	
	// Auxiliary Classes And Listeners
	
	class FontModificationListener  implements ActionListener, ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			redrawSampleText();
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			redrawSampleText();
			
		}
		
	}
	
	class OKButtonPressed implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			FontChooser.this.getResult().result = FontChooser.this.getCurrentFont();
			FontChooser.this.dispose();
		}
		
	}
	
	
	class CancelButtonPressed implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			FontChooser.this.getResult().result = null;
			FontChooser.this.dispose();
		}
		
	}
	
	public static class FontContainer {
		Font result;
	}
	

}

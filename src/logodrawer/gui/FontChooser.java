package logodrawer.gui;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicBorders;

public class FontChooser extends javax.swing.JDialog {

	private static final long serialVersionUID = 3364026494786435323L;

	//////////////////////////
	// Private Class Variables
	private static String sampleText;
	
	/////////////////////////////
	// Private Instance Variables
	private Font currentFont;
	private FontContainer result; // FontContainer is an object to store the chosen font.
	
	
	/////////////
	// Components
	private JComboBox<String> jcbFontName;
	private JComboBox<String> jcbFontStyle;
	private JSpinner  jsFontSize;
	private JTextArea jtpSampleText;
	private JButton   jbOK;
	private JButton   jbCancel;
	
	//////////////////
	// Main Executable
	public static void 					main						(String[] args) {
//		SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				JFrame frame = new JFrame();
//				FontContainer f = new FontChooser.FontContainer();
//				FontChooser inst = new FontChooser(frame,f);
//				inst.setVisible(true);
//				inst.setModal(true);
//				System.out.println(f.result);
//			}
//		});
		
		System.out.println(FontChooser.openFontChooser());
	}
	
	///////////////////
	// Public Interface
	public static Font 					openFontChooser				() {
		return openFontChooser("El murcielago hind�");
	}
	public static Font 					openFontChooser				(String sampleText) {
		JFrame frame = new JFrame();
		FontContainer f = new FontChooser.FontContainer();
		FontChooser inst = new FontChooser(frame,f,sampleText);
		inst.setVisible(true);
		inst.setModal(true);
		frame.dispose();
		return f.result;
	}
	

//	public FontChooser(JFrame frame, FontContainer result) {
//		this(frame,result,"El murcielago hind�");
//	}
	
	//////////////
	// Constructor
	private 							FontChooser					(JFrame frame, FontContainer result, String sampleText) {
		super(frame);
		this.result = result;
		FontChooser.sampleText = sampleText;
		this.createGUI();
		this.setModalityType(ModalityType.APPLICATION_MODAL);
	}
	
	//////////////////
	// Private Methods
	private void 						createGUI					() {
		try 
		{
			FontModificationListener fl = new FontModificationListener();
			this.setLayout(null);
			this.setSize(310, 180);
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
			
			jcbFontName = new JComboBox<String>(this.getSystemFontNames());
			jcbFontName.setLocation(10, 5);
			jcbFontName.setSize(190, 27);
			jcbFontName.addActionListener(fl);
			
			jcbFontStyle = new JComboBox<String>(new String[] {"Plain","Bold","Italic","Bold Italic"});
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
			JScrollPane jspSampleText = new JScrollPane(jtpSampleText);
			jspSampleText.setLocation(10, 75);
			jspSampleText.setSize(280,65);
			jspSampleText.setBorder(BasicBorders.getTextFieldBorder());
			
			this.add(jspSampleText);
			this.add(jsFontSize);
			this.add(jcbFontStyle);
			this.add(jbCancel);
			this.add(jbOK);
			this.add(jcbFontName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	protected Font 						getCurrentFont				() {
		return currentFont;
	}

	protected FontContainer 			getResult					() {
		return result;
	}
	
	private Font 						getFontFromSelectedValues	() {
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

	private String[] 					getSystemFontNames			() {
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    return ge.getAvailableFontFamilyNames(); // Get the fonts
	}
	
	private void 						redrawSampleText			() {
		currentFont = getFontFromSelectedValues();
		jtpSampleText.setFont(currentFont);
	}
	
	//////////////////////////////////
	// Auxiliary Classes And Listeners
	class 					FontModificationListener  			implements ActionListener, ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			redrawSampleText();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			redrawSampleText();
		}
		
	}
	
	class 					OKButtonPressed 					implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			FontChooser.this.getResult().result = FontChooser.this.getCurrentFont();
			FontChooser.this.dispose();
		}
		
	}
	
	class 					CancelButtonPressed 				implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			FontChooser.this.getResult().result = null;
			FontChooser.this.dispose();
		}
		
	}
	
	public static class 	FontContainer {
		Font result;
	}
	

}

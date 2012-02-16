package logodrawer.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import fastaIO.FastaMultipleReader;
import fastaIO.Pair;

import logodrawer.DnaLogoDrawer;
import logodrawer.LogoDrawer;
import logodrawer.LogoImageLayout;
import logodrawer.ProteinLogoDrawer;

public class LogoDrawerGui extends javax.swing.JFrame{
	
	
	private static final long serialVersionUID = 7453285271001269584L;
	
	/////////////////////
	// Instance Variables

	private List<String> sequences=null;
	private LogoDrawer drawer= null;

	/////////////
	// Components
	private LogoViewPane topPane;
	private OptionsPane bottomPane;
	private JSplitPane jspMain;
	
	///////////////
	// Constructor
	
	public LogoDrawerGui() {
		this.createGUI();
		this.pack();
		jspMain.setDividerLocation((double)0.5);
	}

	//////////////////
	// Public Methods

	public void setSequences(List<String> sequences) {
		this.sequences = sequences;
	}
	
	
	//////////////////
	// Private Methods
	
	private void createGUI() {
		
		
		JScrollPane topSPane = new JScrollPane();
		JScrollPane bottomSPane = new JScrollPane();
		
		topPane = new LogoViewPane();
		topSPane.setViewportView(topPane);
		bottomPane = new OptionsPane(this);
		bottomSPane.setViewportView(bottomPane);
		
		
		jspMain = new JSplitPane(JSplitPane.VERTICAL_SPLIT,topSPane,bottomSPane);
		
		jspMain.setDividerSize(3);
		
		this.add(jspMain);
		
		this.setPreferredSize(new Dimension(700, 500));
		this.setSize(new Dimension(400, 300));
		
		jspMain.setDividerLocation(200);
		jspMain.setContinuousLayout(true);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}
	
	protected void drawLogo() {

		RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		renderingHints.add(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
		renderingHints.add(new RenderingHints(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY ));
		renderingHints.add(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON ));
		renderingHints.add(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));

		LogoImageLayout layout = new LogoImageLayout();		
		layout.setFont(bottomPane.getFont());
		layout.setLogoHeader(bottomPane.getLogoHeaderSize());
		layout.setLogoHeight(bottomPane.getLogoHeightSize());
		layout.setPositionsPerLine(bottomPane.getPositionsPerLine());
		layout.setPosWidth(bottomPane.getPosWidthSize());
		layout.setRenderingHints(renderingHints);
		layout.setRowHeight(bottomPane.getRowHeightSize());
		layout.setRulerColumn(bottomPane.getRulerColumnSize());

		topPane.setBi(this.drawer.drawLogo(this.sequences,layout));
		
		
	}
	
	public void selectLogoDrawer(MoleculeType type) {
		if (type == MoleculeType.DNA) {
			this.drawer = new DnaLogoDrawer();  
		} else {
			this.drawer = new ProteinLogoDrawer();
		}
	}
	
	//////////////////
	// Main Executable
	public static void 					main				(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override public void run() {
				LogoDrawerGui ldg = new LogoDrawerGui();
				ldg.setVisible(true);
			}
		});
	}


	
	
	
}

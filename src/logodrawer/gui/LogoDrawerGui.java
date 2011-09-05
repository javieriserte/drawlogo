package logodrawer.gui;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

public class LogoDrawerGui extends javax.swing.JFrame{
	
	
	private static final long serialVersionUID = 7453285271001269584L;

	/////////////
	// Components
	private LogoViewPane topPane;
	private JPanel bottomPane;
	private JSplitPane jspMain;
	
	///////////////
	// Constructor
	
	public LogoDrawerGui() {
		this.createGUI();
		this.pack();
		jspMain.setDividerLocation((double)0.5);
	}
	
	private void createGUI() {
		
		
		JScrollPane topSPane = new JScrollPane();
		JScrollPane bottomSPane = new JScrollPane();
		
		topPane = new LogoViewPane();
		topSPane.setViewportView(topPane);
		bottomPane = new OptionsPane();
		bottomSPane.setViewportView(bottomPane);
		
		
		jspMain = new JSplitPane(JSplitPane.VERTICAL_SPLIT,topSPane,bottomSPane);
		
//		jspMain.add(topSPane);
//		jspMain.add(bottomSPane);
		
		jspMain.setDividerSize(3);
		
		this.add(jspMain);
		
		this.setPreferredSize(new Dimension(400, 300));
		this.setSize(new Dimension(400, 300));
		
		jspMain.setDividerLocation(200);
		jspMain.setContinuousLayout(true);
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
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

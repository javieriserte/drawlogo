package logodrawer;

import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

public class DnaLogoDrawer extends LogoDrawer{
	
	public void drawLogo(List<String> sequences, LogoImageLayout layout) throws FileNotFoundException, IOException {
		
		LogoCalculator lc = new LogoCalculator();
		
		List<PositionValues> calculateValues = lc.calculateValues(4, sequences );
		System.out.println(calculateValues);
		
		BufferedImage createImage = this.createImage(calculateValues, new DnaColorStrategy(), 4,layout);
		
		this.exportJPG( new File("c:\\logo.jpg"),  createImage);
		
	}
	
	///////////////////
	// Executable Main
	
	public static void main(String[] arg) {
		DnaLogoDrawer dld = new DnaLogoDrawer();
		List<String> s = new Vector<String>();
		
		s.add("AAACTA");
		s.add("AAAATG");
		s.add("ACATTG");
		s.add("ACTGTG");
		s.add("ACTGTG");
		s.add("ACTGTG");
		s.add("ACTGTG");
		s.add("ACTGTG");
		s.add("ACTGTG");
		s.add("ACTGAG");
		
		
		LogoImageLayout layout = new LogoImageLayout();

		RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		renderingHints.add(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
		renderingHints.add(new RenderingHints(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY ));
		renderingHints.add(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON ));
		renderingHints.add(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));

		
		layout.setFont(new Font("Calibri", 0, 10));
		layout.setLogoHeader(8);
		layout.setLogoHeight(300);
		layout.setPositionsPerLine(20);
		layout.setPosWidth(45);
		layout.setRenderingHints(renderingHints);
		layout.setRowHeight(320);
		layout.setRulerColumn(15);
		
		
		
		try {
			dld.drawLogo(s,layout);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

package logodrawer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

public class DnaLogoDrawer extends LogoDrawer{
	
	public void drawLogo(List<String> sequences) throws FileNotFoundException, IOException {
		
		LogoCalculator lc = new LogoCalculator();
		
		List<PositionValues> calculateValues = lc.calculateValues(4, sequences );
		System.out.println(calculateValues);
		
		BufferedImage createImage = this.createImage(calculateValues, new DnaColorStrategy(), 4);
		
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
		
		try {
			dld.drawLogo(s);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

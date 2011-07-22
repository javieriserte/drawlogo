package logodrawer;

import java.util.List;
import java.util.Vector;

public class DnaLogoDrawer extends LogoDrawer{
	
	public void drawLogo(List<String> sequences) {
		
		LogoCalculator lc = new LogoCalculator();
		System.out.println(lc.calculateValues(4, sequences ));
		
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
		
		dld.drawLogo(s);
		
	}
}

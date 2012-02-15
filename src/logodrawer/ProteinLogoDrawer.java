package logodrawer;

public class ProteinLogoDrawer extends LogoDrawer {

	////////////////////
	// Public Interface
	
	@Override
	public int 									getAlphabetSize							() {
		return 20;
	}

	@Override
	public ColorStrategy 						getColorStrategy						() {
		return new ProteinColorStrategy();
	}



}

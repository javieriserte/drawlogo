package logodrawer;


public class DetectType {

	///////////////////
	// class variables
	
	@SuppressWarnings("unused")
	private static final String DNA_alphabet = "ACTG-"; 
	@SuppressWarnings("unused")
	private static final String degDNA_alphabet = "ACTGRYWSKMVDHBN-";
	@SuppressWarnings("unused")
	private static final String protein_alphabet = "QWERTYIPASDFGHKLCVNMX-";
	private static final String exclusive_protein_alphabet = "QEIPFLX";
	@SuppressWarnings("unused")
	private static final String excluded = "UOJZ";
	
	////////////////////
	// Public Interface
	
	public static boolean isProtein(String string) {
		boolean exclusiveProteinCharFound = false;
		int index=0;
		while (!exclusiveProteinCharFound && index<exclusive_protein_alphabet.length()) {
			char current = exclusive_protein_alphabet.charAt(index++);
			exclusiveProteinCharFound = (string.indexOf(current)>=0);
		}
		return exclusiveProteinCharFound;
	}
}

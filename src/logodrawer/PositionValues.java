package logodrawer;

public class PositionValues {
	private String listOfResidues;
	private double[] frequencies;
	private double observedEntropy;
	
	
	///////////////
	// Constructor
	
	public PositionValues() {
		this.setListOfResidues(null);
		this.setValues(null);
	}
	
	public PositionValues(String residues,double[] values) {
		this.setListOfResidues(residues);
		this.setValues(values);
	}
	
	////////////////////
	// Public interface
	
	public void sortValues() {
		StringBuilder newlistOfResidues = new StringBuilder();
		double[] newValues = new double[frequencies.length];

		for (int i=0;i<frequencies.length;i++) {
			int p = this.getMinPos(frequencies);
			newValues[i] = frequencies[p]; frequencies[p]=1;
			newlistOfResidues.append(listOfResidues.charAt(p));
		}
		this.setListOfResidues(newlistOfResidues.toString());
		this.setValues(newValues);
	}
	
	private int getMinPos(double[] Values) {
		double	min = 1;
		int mP  = 0;
		for (int i=0;i<Values.length;i++) {
			if ( Values[i]<min ) {
				min = Values[i];
				mP = i;
			}
		}
		return mP;
	}
	
	
	public String toString() {
		StringBuilder r = new StringBuilder();
			r.append('[');
			r.append('(');
			r.append(listOfResidues.charAt(0));
			r.append(',');
			r.append(frequencies[0]);
			r.append(')');
		for (int i=1; i<listOfResidues.length();i++) {
			r.append(',');

			r.append('(');
			r.append(listOfResidues.charAt(i));
			r.append(',');
			r.append(frequencies[i]);
			r.append(')');
		}
		r.append(']');
		return r.toString();
	}
	
	///////////////////////
	// Getters And Setters
	
	public String getListOfResidues() {
		return listOfResidues;
	}
	public void setListOfResidues(String listOfResidues) {
		this.listOfResidues = listOfResidues;
	}
	public double[] getValues() {
		return frequencies;
	}
	public void setValues(double[] values) {
		frequencies = values;
	}

	public double getObservedEntropy() {
		return this.observedEntropy;
	}
	public void setObservedEntropy(double so) {
		this.observedEntropy = so;
	}
	
	
}

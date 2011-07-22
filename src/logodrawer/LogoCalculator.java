package logodrawer;

import java.util.List;
import java.util.Vector;


public class LogoCalculator {

	public List<PositionValues> calculateValues(int elemInAlphabet, List<String> sequences) {
		int numberOfSequences = sequences.size();
		
		List<PositionValues> frequencies = calculateFrequencies(elemInAlphabet, sequences, numberOfSequences);
		
		for (int i=0; i<frequencies.size();i++) {
			double[] vs = frequencies.get(i).getValues();
			for (int j = 0; j < vs.length; j++) {
				vs[j] = vs[j] * Math.log(vs[j])/Math.log(2); 
			}
			frequencies.get(i).setValues(vs);
			frequencies.get(i).sortValues();  
		}
		return frequencies;
	}


	// Private Methods
	private List<PositionValues> calculateFrequencies(int elemInAlphabet, List<String> sequences, int numberOfSequences) {
		List<PositionValues> positionValues = new Vector<PositionValues>();
		
		// Note: gaps are ignored!
		
		for (int i = 0; i<sequences.get(0).length();i++) {
			// iterate over positions
			
			double[] freqsInSeq = new double[elemInAlphabet];
			for (int j=0;j<freqsInSeq.length;j++) freqsInSeq[j]=0;
			
			char[] alphabet = new char[elemInAlphabet];
			for (int j=0;j<alphabet.length;j++) alphabet[j]=' ';
			
			for (int j=0; j<numberOfSequences; j++ ) {
				// iterate over sequences

				char c = sequences.get(j).charAt(i);
				
				for (int k=0;k<alphabet.length;k++) {
					if (' ' == alphabet[k]) alphabet[k] = c; 
					if (c==alphabet[k]) {
							freqsInSeq[k]++;
							k = alphabet.length;
					}
				}
			}
			
			for (int j=0;j<freqsInSeq.length;j++) freqsInSeq[j]=freqsInSeq[j]/numberOfSequences;
				// Convert AbsoluteNumbers to frequencies
			
			StringBuilder r = new StringBuilder();
			List<Double> f = new Vector<Double>();			

			for (int j=0;j<freqsInSeq.length;j++) {
				// Eliminate values that do not appear in the sequences
				if (freqsInSeq[j]!=0) {
					r.append(alphabet[j]);
					f.add(freqsInSeq[j]);
				}
			}
			double[] fa = new double[f.size()];
			for (int j = 0; j < fa.length; j++) {fa[j] = (double) f.get(j);}
			PositionValues pv = new PositionValues(r.toString(), fa);
			positionValues.add(pv);
		}
		
		return positionValues; 
	}
	
}

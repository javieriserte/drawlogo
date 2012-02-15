package logodrawer;

import java.util.List;
import java.util.Vector;


public class LogoCalculator {

	///////////////////
	// Public Interface
	
	public List<PositionValues>                        calculateValues                 (int elemInAlphabet, List<String> sequences) {
		int numberOfSequences = sequences.size();
		
		List<PositionValues> frequencies = calculateFrequencies(elemInAlphabet, sequences, numberOfSequences);
		
		for (int i=0; i<frequencies.size();i++) {
			double[] vs = frequencies.get(i).getValues();
			double so = observedEntropyFromFrequencies(vs);
			
			frequencies.get(i).sortValues();
			frequencies.get(i).setObservedEntropy(so);
			
		}
		return frequencies;
	}

    //////////////////
	// Private Methods
	
	protected List<PositionValues>                     calculateFrequencies            (int numberOfElemInAlphabet, List<String> sequences, int numberOfSequences) {
		List<PositionValues> positionValues = new Vector<PositionValues>();
		
		// Note: gaps are ignored!
		
		int numberOfPositionsInSequence = sequences.get(0).length();
		
		for (int i = 0; i<numberOfPositionsInSequence;i++) {
			// iterate over positions
			
			int gapCount = 0;
			
			double[] freqsInSeq = new double[numberOfElemInAlphabet];

			char[] alphabet = new char[numberOfElemInAlphabet];
			
			for (int j=0;j<numberOfElemInAlphabet;j++) freqsInSeq[j]=0;
			
			for (int j=0;j<numberOfElemInAlphabet;j++) alphabet[j] = ' ';
			
			gapCount = absoluteCountOfChars(sequences, numberOfSequences, i, gapCount, freqsInSeq, alphabet);
			
			absoluteCountsToFrequencies(numberOfSequences, freqsInSeq, gapCount, false);
				// Convert AbsoluteNumbers to frequencies
			
			StringBuilder r = new StringBuilder();
			List<Double> f = new Vector<Double>();			

			removeUnusedCharacters(freqsInSeq, alphabet, r, f);
				// Eliminate values that do not appear in the sequences
			
			double[] fa = new double[f.size()];
			for (int j = 0; j < fa.length; j++) {fa[j] = (double) f.get(j);}
			PositionValues pv = new PositionValues(r.toString(), fa);
			positionValues.add(pv);
		}
		
		return positionValues; 
	}

	protected int absoluteCountOfChars(List<String> sequences,
			int numberOfSequences, int i, int gapCount, double[] freqsInSeq,
			char[] alphabet) {
		for (int j=0; j<numberOfSequences; j++ ) {
			// iterate over sequences

			char c = sequences.get(j).charAt(i);
			
			if (!(isGap(c))) {
				countCharInFrequencies(freqsInSeq, alphabet, c);
			} else {
				gapCount++;
			}
			
		}
		return gapCount;
	}

	protected void removeUnusedCharacters(double[] freqsInSeq, char[] alphabet,
			StringBuilder r, List<Double> f) {
		for (int j=0;j<freqsInSeq.length;j++) {

			if (freqsInSeq[j]!=0) {
				r.append(alphabet[j]);
				f.add(freqsInSeq[j]);
			}
		}
	}

	protected void absoluteCountsToFrequencies(int numberOfSequences,
			double[] freqsInSeq, int gapCount, boolean countGaps) {
		
		int gaps = 0;
		if (countGaps) gaps = gapCount;
		
		for (int j=0;j<freqsInSeq.length;j++) freqsInSeq[j]=freqsInSeq[j]/(numberOfSequences-gaps);

	}

	protected void countCharInFrequencies(double[] freqsInSeq, char[] alphabet, char c) {
		for (int k=0;k<alphabet.length;k++) {
			if (isSpace(alphabet[k])) alphabet[k] = c; 
			if (c==alphabet[k]) {
				freqsInSeq[k]++;
				k = alphabet.length;
			}
		}
	}

	protected double observedEntropyFromFrequencies(double[] vs) {
		double so = 0;
		for (int j = 0; j < vs.length; j++) {
			so = so + vs[j] * Math.log(vs[j])/Math.log(2); 
		}
		return so;
	}
	
	protected boolean isGap(char c) {
		return '-'==c;
	}
	
	protected boolean isSpace(char c) {
		return ' '==c;
	}
	
}

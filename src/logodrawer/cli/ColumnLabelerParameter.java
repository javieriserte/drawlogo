package logodrawer.cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import logodrawer.CustomColumnLabeler;
import logodrawer.NumericColumnLabeler;
import cmdGA.parameterType.ParameterType;

/**
 * Generates a column labeler from a command line pararmeter. 
 * @author javier
 *
 */
public class ColumnLabelerParameter extends ParameterType {

	protected static ColumnLabelerParameter singleton = new ColumnLabelerParameter();
	/**
	 * No instance variables are used, so there is no need of more than one instance.
	 * A 'singleton' pattern is implemented. 

	 * @return the only one instance BooleanParameter
	 */	
	public static ColumnLabelerParameter getParameter() {
		return (ColumnLabelerParameter) singleton;
	}

	
	@Override
	protected Object parse(String parameter) {
		
		if (parameter.length()>2) {
		
			if (parameter.toLowerCase().startsWith("n:")) {
				
				return new NumericColumnLabeler(Integer.valueOf(parameter.substring(2)));
				
			} else
			if (parameter.toLowerCase().startsWith("f:")) {
				
				File labelsFile = new File (parameter.substring(2));
				
				if (labelsFile.exists()) {
					
					BufferedReader br;
					
					try {
						
						br = new BufferedReader(new FileReader(labelsFile));
					
						String currentLine = null;
						
						List<String> labels = new ArrayList<String>();
						
						while ((currentLine=br.readLine())!=null) {
							
							labels.add(currentLine.trim());
							
						}
						
						br.close();
						
						return new CustomColumnLabeler(labels.toArray(new String[0]));
					
					} catch (IOException e) {
						
						e.printStackTrace();
						
					}
					
				}
				
			}
			
		}
		
		return new NumericColumnLabeler(1);
		
	}

}

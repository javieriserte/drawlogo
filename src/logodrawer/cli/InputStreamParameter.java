package logodrawer.cli;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import cmdGA.parameterType.ParameterType;

public class InputStreamParameter extends ParameterType {
	/**
	 * This Class represents a InputStreamParameter parameter in a command line created from an existing file.
	 * 
	 * @author Javier Iserte <jiserte@unq.edu.ar>
	 *
	 */
		protected static ParameterType singleton = new InputStreamParameter();
		/**
		 * No instance variables are used, so there is no need of more than one instance.
		 * A 'singleton' pattern is implemented. 

		 * @return the only one instance BooleanParameter
		 */	
		public static InputStreamParameter getParameter() {
			return (InputStreamParameter) singleton;
		}
		/**
		 * Parse method
		 * 
		 * @return A String
		 */
		protected Object parse(String parameter) {
			String st = parameter.trim();
			
			st = st.replaceAll("\"", "");
			st = st.replaceAll("\'", "");
			
			File file = new File(st);
			InputStream is;
			try {
				is = new FileInputStream(file);
				
			} catch (FileNotFoundException e) {
				System.err.println("Especified "+file.getName()+" not found");
				is = null;
			}
			return (Object) is;
		}
		


}


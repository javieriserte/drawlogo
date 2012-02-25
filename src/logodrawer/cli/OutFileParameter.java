package logodrawer.cli;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;

import cmdGA.parameterType.ParameterType;

public class OutFileParameter extends ParameterType {
	/**
	 * This Class represents a InputStreamParameter parameter in a command line created from an existing file.
	 * 
	 * @author Javier Iserte <jiserte@unq.edu.ar>
	 *
	 */
		protected static ParameterType singleton = new OutFileParameter();
		/**
		 * No instance variables are used, so there is no need of more than one instance.
		 * A 'singleton' pattern is implemented. 

		 * @return the only one instance BooleanParameter
		 */	
		public static OutFileParameter getParameter() {
			return (OutFileParameter) singleton;
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
			return (Object) file;
		}
		


}

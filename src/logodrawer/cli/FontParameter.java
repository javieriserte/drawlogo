package logodrawer.cli;

import java.awt.Font;
import cmdGA.parameterType.ParameterType;

public class FontParameter extends ParameterType {
	/**
	 * This Class represents a InputStreamParameter parameter in a command line created from an existing file.
	 * 
	 * @author Javier Iserte <jiserte@unq.edu.ar>
	 *
	 */
		protected static FontParameter singleton = new FontParameter();
		/**
		 * No instance variables are used, so there is no need of more than one instance.
		 * A 'singleton' pattern is implemented. 

		 * @return the only one instance BooleanParameter
		 */	
		public static FontParameter getParameter() {
			return (FontParameter) singleton;
		}
		/**
		 * Parse method
		 * 
		 * @return A String
		 */
		protected Object parse(String parameter) {
			String st = parameter.trim();
			
			if (st.matches("^\\[.+,.+\\]$")) {
				st = st.substring(1,st.length()-1);
				String[] ss = st.split(",",2);
				return (Object) new Font(ss[0].trim(),Integer.parseInt(ss[1].trim()),10);
			}
			
			return (Object) null;
		}
		


}

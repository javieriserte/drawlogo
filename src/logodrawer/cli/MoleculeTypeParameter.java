package logodrawer.cli;

import logodrawer.MoleculeType;
import cmdGA.parameterType.ParameterType;

public class MoleculeTypeParameter extends ParameterType {

	protected static ParameterType singleton = new MoleculeTypeParameter();
	/**
	 * No instance variables are used, so there is no need of more than one instance.
	 * A 'singleton' pattern is implemented. 

	 * @return the only one instance BooleanParameter
	 */	
	public static MoleculeTypeParameter getParameter() {
		return (MoleculeTypeParameter) singleton;
	}
	
	@Override
	protected Object parse(String parameter) {
		String st = parameter.trim().toUpperCase();
		
		if (st.matches("D|DNA")) {
			return (Object) MoleculeType.DNA; 
		} else if (st.matches("P|PROTEIN")) {
			return (Object) MoleculeType.Protein;
		} else {
			return null;
		}
	}

}

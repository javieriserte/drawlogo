package logodrawer;

/**
 * Creates labels for column given by the index.
 * The label is the string conversion of the numeric index plus an offset.
 * 
 * @author javier
 *
 */
public class NumericColumnLabeler extends ColumnLabeler {

	private int offset;
	
	public NumericColumnLabeler(int offset) {
		super();
		this.offset = offset;
	}

	@Override
	public String label(int columnIndex) {
		
		return String.valueOf(columnIndex+this.getOffset());
		
	}

	protected int getOffset() {
		return offset;
	}

	protected void setOffset(int offset) {
		this.offset = offset;
	}

}

package logodrawer;

/**
 * This class generates a label for a given column given by the column index
 * @author javier
 *
 */
public abstract class ColumnLabeler {

	/**
	 * Gets the label corresponding to the current column.
	 * @param columnIndex
	 * @return
	 */
	public abstract String label(int columnIndex);
	
}

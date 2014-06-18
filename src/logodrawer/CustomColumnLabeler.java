package logodrawer;

/**
 * 
 * 
 * @author javier
 *
 */
public class CustomColumnLabeler extends ColumnLabeler {

	private String[] labels;
	
	public CustomColumnLabeler(String[] labels) {
		super();
		this.labels = labels;
	}

	@Override
	public String label(int columnIndex) {
		
		if (columnIndex >= 0 && columnIndex < this.getLabels().length) {

			return this.getLabels()[columnIndex];
			
		}
		return "?";
		
	}

	protected String[] getLabels() {
		return labels;
	}

	protected void setLabels(String[] labels) {
		this.labels = labels;
	}

}

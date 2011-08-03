package logodrawer;

import java.awt.Font;
import java.awt.RenderingHints;
import java.util.List;

public class LogoImageLayout {

	////////////////////////////
	// Private Instance Variable
	
	int 						logoHeader;
	int 						logoHeight;
	int 						rowHeight;
	int 						rightSpacer;
	int							posWidth;
	int 						rulerColumn;
	int							positionsPerLine;
	List<PositionValues> 		list;
	Font						font;
	
	int 						imageWidth;
	int 						imageHeight;
	int 						rulerHeight; 
	int 						lines;
	int 						numberOfPositions;
	RenderingHints				renderingHints;

	///////////////////
	// Public Interface
	

	/**
	 * Calculates the positions of every element in the logo.
	 */
	public void 				doLayout					() {
		numberOfPositions = list.size();
		lines = 1 + (numberOfPositions-1) / positionsPerLine;
		imageWidth = (lines == 1 ? numberOfPositions:positionsPerLine)*posWidth+ rulerColumn+rightSpacer;
		imageHeight = (rowHeight + logoHeader) * lines;
		rulerHeight = rowHeight - logoHeight; 
		
		
	}

	//////////////////////
	// Getters And Setters
	
	public int 					getLogoHeader				() {
		return logoHeader;
	}

	public void 				setLogoHeader				(int logoHeader) {
		this.logoHeader = logoHeader;
	}

	public int 					getLogoHeight				() {
		return logoHeight;
	}

	public void 				setLogoHeight				(int logoHeight) {
		this.logoHeight = logoHeight;
	}

	public int 					getRowHeight				() {
		return rowHeight;
	}

	public void 				setRowHeight				(int rowHeight) {
		this.rowHeight = rowHeight;
	}

	public int 					getRightSpacer				() {
		return rightSpacer;
	}

	public void 				setRightSpacer				(int rightSpacer) {
		this.rightSpacer = rightSpacer;
	}

	public int 					getPosWidth					() {
		return posWidth;
	}

	public void 				setPosWidth					(int posWidth) {
		this.posWidth = posWidth;
	}

	public int 					getRulerColumn				() {
		return rulerColumn;
	}

	public void 				setRulerColumn				(int rulerColumn) {
		this.rulerColumn = rulerColumn;
	}

	public int 					getPositionsPerLine			() {
		return positionsPerLine;
	}

	public void 				setPositionsPerLine			(int positionsPerLine) {
		this.positionsPerLine = positionsPerLine;
	}

	public int 					getImageWidth				() {
		return imageWidth;
	}

	protected void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	protected void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	public int getRulerHeight() {
		return rulerHeight;
	}

	protected void setRulerHeight(int rulerHeight) {
		this.rulerHeight = rulerHeight;
	}

	public int getLines() {
		return lines;
	}

	protected void setLines(int lines) {
		this.lines = lines;
	}

	public int getNumberOfPositions() {
		return numberOfPositions;
	}

	protected void setNumberOfPositions(int numberOfPositions) {
		this.numberOfPositions = numberOfPositions;
	}

	public List<PositionValues> getList() {
		return list;
	}

	public void setList(List<PositionValues> list) {
		this.list = list;
	};
	
	public RenderingHints getRenderingHints() {
		return renderingHints;
	}

	public void setRenderingHints(RenderingHints renderingHints) {
		this.renderingHints = renderingHints;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

}

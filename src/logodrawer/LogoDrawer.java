package logodrawer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public abstract class LogoDrawer {

	public abstract int getAlphabetSize();
	public abstract ColorStrategy getColorStrategy();
	
	
	//////////////////////////
	// Public Class Interface
	
	public static LogoDrawer LogoDrawerFor(MoleculeType mt) {
		LogoDrawer result=null;
		switch (mt) {
		case DNA:
			result = new DnaLogoDrawer();
			break;

		case Protein:
			result = new ProteinLogoDrawer();
			break;
			
		default:
			
			break;
		}
		
		return result;
	}
	
	////////////////////
	// Public Interface
	public BufferedImage drawLogo(List<String> sequences, LogoImageLayout layout, boolean countGaps)  {
		
		LogoCalculator lc = new LogoCalculator();
		
		List<PositionValues> calculateValues = lc.calculateValues(this.getAlphabetSize(), sequences , countGaps);
		
		return this.createImage(calculateValues, this.getColorStrategy(),layout);
		
	}
	
	
	protected BufferedImage 	createImage							(List<PositionValues> list, ColorStrategy mycolor, LogoImageLayout layout ) {

		
		/////////////////////
		// Sets up the layout
		
		layout.setList(list);
		layout.doLayout();
		
		//////////////////////
		// Creates a new Image
		BufferedImage bi = new BufferedImage(layout.getImageWidth() , layout.getImageHeight() ,BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g = bi.createGraphics();
		
		g.setColor(Color.white);
		g.fillRect(0, 0, layout.getImageWidth(), layout.getImageHeight());
		g.setRenderingHints(layout.getRenderingHints());
		
		g.setFont(layout.getFont());
		double maxBits =  Math.log(this.getAlphabetSize())/Math.log(2);
		
		drawLogoChars(list, mycolor, layout.getLogoHeader(), layout.getLogoHeight(), layout.getRowHeight(),
				layout.getPosWidth(), layout.getRulerColumn(), layout.getPositionsPerLine(), g, maxBits);
		
		int labelValues[] = new int[list.size()];
		for (int i = 0; i < labelValues.length; i++) labelValues[i]=i; 

		int[] verticalLabels = new int[(int) maxBits + 1];
		for (int i = 0; i < verticalLabels.length; i++) {
			verticalLabels[i]=i;
		}
		
		for (int l = 0; l< layout.getLines();l++) {

			this.drawHorizotalRuler(layout.getRulerColumn(), (layout.getRowHeight()+ layout.getLogoHeader())*(l+1),layout.getRulerHeight(),labelValues,l*layout.getPositionsPerLine() ,Math.min((l+1)*layout.getPositionsPerLine(),layout.getNumberOfPositions())-1,layout.getPosWidth(),2,4,Color.BLACK,new Font("Verdana", Font.BOLD, 10),g,layout.getPositionsPerLine());
			this.drawVerticalRuler((layout.getRowHeight()+ layout.getLogoHeader())*l + (layout.getLogoHeight()+ layout.getLogoHeader()), (layout.getRowHeight()+ layout.getLogoHeader())*l + layout.getLogoHeader(),verticalLabels,maxBits,4,2,layout.getRulerColumn(), Color.black, new Font("Verdana",Font.BOLD, 10), g);
			
		}

		return bi;
	}

	protected void 				drawLogoChars						(List<PositionValues> list, ColorStrategy mycolor, int logoHeader, 
																	 int logoHeight, int rowHeight, int posWidth, int rulerColumn, 
																	 int positionsPerLine, Graphics2D g, double maxBits) {
		
		int left = 0 + rulerColumn;
			// sets the initial left position of the first logo in the Graphics2D
		int position = 0;
			// Starts in the first position
		
		for (PositionValues positionValues : list) {
			// iterates over each column of the alignment
			int right = ((position+1) % positionsPerLine ) * posWidth + rulerColumn;
				// calculates the right position of each logo
			int line = position / positionsPerLine;
				// calculates the line in which the current logo will be printed

			double bottom = 0 ;
				// calculates the bottom position of each logo

			double observedEntropy = positionValues.getObservedEntropy();
				// calculates the entropy of the current column in the alignment
			
			double rsec = maxBits + observedEntropy;
				// calculates the rsec value. That is like the height of the current column alignment 
				// in the logo image 
			
			for (int i=positionValues.getListOfResidues().length()-1; i>=0;i--) {
				// iterates over each character in the current column of the alignment
				
				double charHeight = (positionValues.getValues()[i]*logoHeight*rsec/maxBits);
					// calculates the height of the current character in the current column 
				
				double top = bottom + charHeight;
					// calculates the top location of the current character. 
				
				String c = String.valueOf(positionValues.getListOfResidues().charAt(i));
					// gets the current string to be printed

				
				if (!c.equals("I")) { // Corrects the 'I' width glitch 
					this.drawCharEx(left, logoHeight - (int) bottom + logoHeader + (line) * (logoHeader+rowHeight), TextDrawingLayout.Left,TextDrawingLayout.Right, charHeight, posWidth, mycolor.getColor(c.charAt(0)), c, g);
					// draws the current character
				} else {
					this.drawCharEx(left + posWidth*3/8, logoHeight - (int) bottom + logoHeader + (line) * (logoHeader+rowHeight), TextDrawingLayout.Left,TextDrawingLayout.Right, charHeight, posWidth/4, mycolor.getColor(c.charAt(0)), c, g);
					// draws the current character
				}
				
				bottom = top;
			}
			left = right;
				// set the left position of the next column of the alignment
			position++;
				// advances one position in the alignment
		}
	}
	
	/**
	 * Creates a Jpg file from a given Buffered Image.
	 * @param Outfile complete path of output jpg file. 
	 * @param bi bufferedImage to be converted into a Jpeg file.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void 				exportJPG							(File outfile, BufferedImage bi) throws FileNotFoundException, IOException {
		FileOutputStream out = new FileOutputStream(outfile);
			// Creates an Output Stream for the speficied file.
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			// Set the outout Stream to the Jpg codec.
		JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bi);
			// Creates the encoder for the current image
		param.setQuality(0.95f, false);
			// Sets the quality of the image.
		encoder.setJPEGEncodeParam(param);
			// 
		encoder.encode(bi);
	}
	
	/**
	 * Draws a String into a specified (x,y) position with the width and height given, with a particular color into a Graphics2D object.
	 * 
	 * If both Weight and Height are Zero, means that Weight and Height will be of the size of the Font of Graphics2D object.
	 * If one of them is Zero, then the size of the non-zero will be used and the other will be scaled according to the Graphics2D object   
	 * 
	 * @param x horizontal position of String
	 * @param y vertical position of the String
	 * @param horizatalLayout horizontal alignment of the text in relation to x value.  
	 * @param verticalLayout vertical alignment of the text in relation to y value.
	 * @param height string's height.  
	 * @param width string's width.
	 * @param color string's color
	 * @param c string text
	 * @param g graphics2D object in which text will be rendered
	 */
	protected void 				drawCharEx							(int x, int y, TextDrawingLayout horizatalLayout, 
			                                                         TextDrawingLayout verticalLayout,double height, 
			                                                         double width, Color color, String c, Graphics2D g) {
		Font font = g.getFont();
			// Get the font of the Graphics
		
		GeneralPath charPath = new GeneralPath(this.getTextShape(g, c, font));
			// Create a Shape (GeneralPath implements Shape interface) of the font.
	
		Rectangle2D r2d = charPath.getBounds2D();		
		double charPathWidth = r2d.getWidth(); 
		double charPathHeight = r2d.getHeight();
		double charPathLeft = r2d.getX();
		double charPathUp = r2d.getY();
			// gets the coordinates of the font object
		
		AffineTransform at = new AffineTransform();
			// Object to store transformations.

		double scaleX = 0;
		double scaleY = 0;
		if (width!=0 && height!=0) {
			scaleX = (double)width  / (double)charPathWidth  ;
			scaleY = (double)height / (double)charPathHeight ;
		} else 
		if (width!=0 && height == 0) {
			scaleX = (double)width  / (double)charPathWidth  ;
			scaleY = scaleX / (double)charPathHeight;
		} else 
		if (width==0 && height != 0) {
			scaleY = (double)height / (double)charPathHeight;
			scaleX = scaleY / (double)charPathWidth  ;
		} else 
		if (width==0 && height == 0) {
			scaleY = 1; height = charPathHeight;
			scaleX = 1; width = charPathWidth;
		}		
		
		at.scale(scaleX, scaleY);
			// Scale transform, so the charPath has the width and height needed.

		at.translate(-charPathLeft, -charPathUp);
			// Translate transform to (0,0) positions (left,upper)

		charPath = (GeneralPath) charPath.createTransformedShape(at);
			// Creates a new path with the transformations applied
		
		at.setToIdentity();
			// resets the object that stores transformations.
		
		double tx =0; double ty = 0;
		
		switch (horizatalLayout) {
			case Center: tx = x - charPath.getBounds2D().getWidth() /2; break;
			case Right:  tx = x - charPath.getBounds2D().getWidth()   ; break;
			case Left: 			
			default:     tx = x                                       ; break;
		} 
		
		switch (verticalLayout) {
			case Center: ty = y-height + charPath.getBounds2D().getHeight() /2  ; break;
			case Top:    ty = y;                                                ; break;
			case Bottom: 
			default:     ty = (y-height)                                        ; break;
		}
		
		at.translate(tx, ty);
			// Translate transform to the correct positions.

		charPath = (GeneralPath) charPath.createTransformedShape(at);
			// Creates a new path with the transformations applied
		
		g.setColor(color);
			// Set the color
		
		g.fill((Shape) charPath);
			// Draws the Shape of the font.
	}
	
	/**
	 * Gets the shape of a string with a specified Font.
	 * 
	 * @param g2d a Graphics2D Object.
	 * @param str String to be converted into a shape.
	 * @param font Font which glyphs are used to construct the Shape Object
	 * @return a Shape Object
	 */
	protected Shape 			getTextShape						(Graphics2D g2d, String str, Font font) {
	    FontRenderContext frc = g2d.getFontRenderContext();
	    	// Creates a FontRenderContext.
	    TextLayout tl = new TextLayout(str, font, frc);
	    	// Creates a text layout for the a given string, with a given font in a specified render context.
	    return tl.getOutline(null);
	    	// returns the path as a Shape
	}
	
	protected void 				drawHorizotalRuler					(int left, int bottom, int rulerHeight, int values[], int from, int to,
			                                                         int widthPosition , int lineWidht, int markHeight, 
			                                                         Color color, Font font, Graphics2D g, int positionsPerLine) {
		///////////////////
		// Store old values
		
		Color oldColor = g.getColor();
			// gets the original color
		Font oldFont = g.getFont();
			// gets the original font

		
		/////////////////////////////
		// Setting Drawing Parameters
		
		g.setColor(color);
			// sets the new color
		g.setStroke(new BasicStroke(lineWidht));
			// sets the new line width
		g.setFont(font);
			// sets the new font

		
		/////////////////////
		// Performing Drawing
		
		g.drawLine(left, bottom - rulerHeight, widthPosition*(to-from+1) + left, bottom - rulerHeight);
			// Draws the horizontal Line
	
		for (int i= from;i<=to; i++ ) {
			g.drawLine(left + widthPosition * (i-from) + widthPosition/2, bottom - rulerHeight, left + widthPosition * (i-from) + widthPosition/2, bottom - rulerHeight + markHeight);
				// Draws each vertical mark line
			String strValue = String.valueOf(values[i]);
			this.drawCharEx(left + widthPosition * (i-from) + widthPosition/2 , bottom - ( rulerHeight - markHeight)/2 , TextDrawingLayout.Center,TextDrawingLayout.Center,0,0,color,strValue,g);
				// Draws the value of each mark 
		}

		
		///////////////////////////
		// Restore Original Values
		
		g.setColor(oldColor);
			//restores the original color.
		g.setFont(oldFont);
			// restores the original font.
		
		
	}
	
	protected void 				drawVerticalRuler 					(int bottom ,int top, int values[], double maxValue, int markWidth, int lineWidht, int columnWidth, Color color, Font font, Graphics2D g ) {
		
		///////////////////
		// Store old values

		Color oldColor = g.getColor();
			// get the color of the Graphics2D object. 
		Font oldFont = g.getFont();
			// get the font of the Graphics2D object.		
		
		
		/////////////////////////////
		// Setting Drawing Parameters

		g.setColor(color);
			// sets the new color
		g.setStroke(new BasicStroke(lineWidht));
			// sets the line width
		g.setFont(font);
			// sets the new font

		
		/////////////////////
		// Performing Drawing

		g.drawLine(columnWidth, bottom, columnWidth, top);
			// draw the vertical axis.
		
		for (int i=0;i<values.length;i++) {

			int posY = (int) (bottom - (bottom-top) * values[i] / maxValue);			
			int posX = (columnWidth - markWidth)/2;
				// gets the coordinates used to print each label and mark

			g.drawLine(columnWidth, posY, columnWidth-markWidth, posY);
				// Draws each horizontal mark
		
			String strToPrint = String.valueOf(values[i]);
				// Gets the String to be printed
			
			this.drawCharEx(posX ,posY, TextDrawingLayout.Center,TextDrawingLayout.Center,0,0,color,strToPrint,g);
				// draws the string into a graphics2D
		}

		
		///////////////////////////
		// Restore Original Values
		
		g.setFont(oldFont);
			// restores the original font
		g.setColor(oldColor);
			// restores the original color
		
	}
	
	
	
}

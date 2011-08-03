package logodrawer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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

@SuppressWarnings("restriction")
public abstract class LogoDrawer {
	
	protected BufferedImage 	createImage							(List<PositionValues> list, ColorStrategy mycolor, int alphabetSize ) {
		
		int numberOfPositions = list.size();
		int logoHeader = 8;
		int logoHeight = 100;
		int rowHeight = 120;
		int rightSpacer = 10;
		int rulerHeight = rowHeight - logoHeight; 
		int posWidth = 25;
		int rulerColumn = 15;
		int positionsPerLine = 6;
		int lines = 1 + (numberOfPositions-1) / positionsPerLine;
		
		
		int imageWidth = (lines==1 ? numberOfPositions:positionsPerLine)*posWidth+ rulerColumn+rightSpacer;
		int imageHeight = (rowHeight + logoHeader) * lines;
		
		BufferedImage bi = new BufferedImage(imageWidth , imageHeight ,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bi.createGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, imageWidth, imageHeight);
		g.setFont(new Font("Verdana", 0, 10));
		
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		double maxBits =  Math.log(alphabetSize)/Math.log(2);

		
		int xleft = 0 + rulerColumn;
		int position = 0;
		for (PositionValues positionValues : list) {
			int xright = ((position+1) % positionsPerLine ) * posWidth + rulerColumn;
			int line = position / positionsPerLine;
//			int xright = xleft+posWidth;
			double ybottom = 0 ;
			double observedEntropy = 0;
			
			observedEntropy = positionValues.getObservedEntropy();
			
			double rsec = maxBits + observedEntropy;
			
			for (int i=positionValues.getListOfResidues().length()-1; i>=0;i--) {
				
				double h = (positionValues.getValues()[i]*logoHeight*rsec/maxBits);
				
				double ytop = ybottom + h;
				
				String c = String.valueOf(positionValues.getListOfResidues().charAt(i)); 

				g.setColor(Color.white);
//				
				
//				this.drawChar(xleft, logoHeight - (int) ybottom + logoHeader + (line) * (logoHeader+rowHeight), h, posWidth, mycolor.getColor(positionValues.getListOfResidues().charAt(i)), c, g);
				this.drawCharEx(xleft, logoHeight - (int) ybottom + logoHeader + (line) * (logoHeader+rowHeight), TextDrawingLayout.Left,TextDrawingLayout.Right, h, posWidth, mycolor.getColor(positionValues.getListOfResidues().charAt(i)), c, g);
				
				ybottom = ytop;
			}
			xleft = xright;
			position++;
		}
		
		int labelValues[] = new int[list.size()];
		for (int i = 0; i < labelValues.length; i++) labelValues[i]=i; 
		
		for (int l = 0; l< lines;l++) {
			
			this.drawHortizotalRuler(rulerColumn, (rowHeight+ logoHeader)*(l+1),rulerHeight,labelValues,l*positionsPerLine ,Math.min((l+1)*positionsPerLine,numberOfPositions)-1,posWidth,2,4,Color.BLACK,new Font("Verdana", Font.BOLD, 10),g,5,positionsPerLine);
			this.drawVerticalRuler((rowHeight+ logoHeader)*l + (logoHeight+ logoHeader), (rowHeight+ logoHeader)*l + logoHeader,new int[]{0,1,2},2,4,2,rulerColumn, Color.black, new Font("Verdana",Font.BOLD, 10), g);
			
		}

		
		return bi;
	}
	
	/**
	 * Creates a Jpg file from a given Buffered Image.
	 * @param Outfile complete path of output jpg file. 
	 * @param bi bufferedImage to be converted into a Jpeg file.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	protected void 				exportJPG							(File outfile, BufferedImage bi) throws FileNotFoundException, IOException {
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
	 * @param x left position of String
	 * @param y bottom position of the String
	 * @param height string's height
	 * @param width string's width
	 * @param color string's color
	 * @param c string text
	 * @param g graphics2D object in which text will be rendered
	 */
	protected void 				drawChar							(int x, int y, double height, double width, Color color, String c, Graphics2D g) {
		Font font = g.getFont();
			// Get the font of the Graphics
		
//		font = new Font("comic sans ms",Font.BOLD,10);
			// If you want another font
		
		GeneralPath charPath = new GeneralPath(this.getTextShape(g, c, font));

		Rectangle2D r2d = charPath.getBounds2D();		
		double charPathWidth = r2d.getWidth(); 
		double charPathHeight = r2d.getHeight();
		double charPathLeft = r2d.getX();
		double charPathUp = r2d.getY();
			// Create a Shape (GeneralPath implements Shape interface) of the font.
		
		AffineTransform at = new AffineTransform();
			// Object to store transformations.

		
		double scaleX = (double)width  / (double)charPathWidth  ;
		double scaleY = (double)height / (double)charPathHeight ;
		
		at.scale(scaleX, scaleY);
			// Scale transform, so the charPath has the width and height needed.

		at.translate(-charPathLeft, -charPathUp);
			// Translate transform to (0,0) positions (left,upper)

		charPath = (GeneralPath) charPath.createTransformedShape(at);
			// Creates a new path with the transformations applied
		
		at.setToIdentity();
			// resets the object that stores transformations.
		
		at.translate(x, y-height);
			// Translate transform to the correct positions.

		charPath = (GeneralPath) charPath.createTransformedShape(at);
			// Creates a new path with the transformations applied
		
		g.setColor(color);
			// Set the color
		
		g.fill((Shape) charPath);
			// Draws the Shape of the font.
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
		
//		font = new Font("comic sans ms",Font.BOLD,10);
			// If you want another font
		
		GeneralPath charPath = new GeneralPath(this.getTextShape(g, c, font));

		Rectangle2D r2d = charPath.getBounds2D();		
		double charPathWidth = r2d.getWidth(); 
		double charPathHeight = r2d.getHeight();
		double charPathLeft = r2d.getX();
		double charPathUp = r2d.getY();
			// Create a Shape (GeneralPath implements Shape interface) of the font.
		
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
	
	protected void 				drawHortizotalRuler					(int left, int bottom, int rulerHeight, int values[], int from, int to,
			                                                         int widthPosition , int lineWidht, int markHeight, 
			                                                         Color color, Font font, Graphics2D g, int bottomFontSpacer,
			                                                         int positionsPerLine) {
		
		Color oldColor = g.getColor();
		Font oldFont = g.getFont();
		
		g.setColor(color);
		g.setStroke(new BasicStroke(lineWidht));
		g.setFont(font);
		int fontHeight=0;		
		fontHeight= g.getFontMetrics().getHeight();
		
		g.drawLine(left, bottom - rulerHeight, widthPosition*(to-from+1) + left, bottom - rulerHeight);
			// Draws the horizontal Line
	
/*		while (fontHeight > rulerHeight - markHeight - bottomFontSpacer) {
			font = font.deriveFont((float)font.getSize()-1);
			g.setFont(font);
			fontHeight= g.getFontMetrics().getHeight();
		} // reduces the font size to fit into the ruler
		*/
		for (int i= from;i<=to; i++ ) {
			g.drawLine(left + widthPosition * (i-from) + widthPosition/2, bottom - rulerHeight, left + widthPosition * (i-from) + widthPosition/2, bottom - rulerHeight + markHeight);
				// Draws each vertical mark line
			String strValue = String.valueOf(values[i]);
//			int fontWidth = g.getFontMetrics().stringWidth(strValue);
//			g.drawString(strValue, left + widthPosition * (i-from) + widthPosition/2 - fontWidth/2, bottom - ( rulerHeight - markHeight - fontHeight)/2 - bottomFontSpacer);
			this.drawCharEx(left + widthPosition * (i-from) + widthPosition/2 , bottom - ( rulerHeight - markHeight)/2 , TextDrawingLayout.Center,TextDrawingLayout.Center,0,0,color,strValue,g);
				// Draws the value of each mark 
		}

		g.setColor(oldColor);
			//restores the original color.
		g.setFont(oldFont);
			// restores the original font.
		
		
	}
	
	protected void drawVerticalRuler (int bottom ,int top, int values[], double maxValue, int markWidth, int lineWidht, int columnWidth, Color color, Font font, Graphics2D g ) {
		
		Color oldColor = g.getColor();
		Font oldFont = g.getFont();
		
		g.setColor(color);
		g.setStroke(new BasicStroke(lineWidht));
		g.drawLine(columnWidth, bottom, columnWidth, top);
		
		g.setFont(font);
		
		for (int i=0;i<values.length;i++) {
			int posY = (int) (bottom - (bottom-top) * values[i] / maxValue);
			g.drawLine(columnWidth, posY, columnWidth-markWidth, posY);
			
			String strToPrint = String.valueOf(values[i]);
			int fontWidth = g.getFontMetrics().stringWidth(strToPrint);
			int fontHeight = g.getFontMetrics().getHeight();
			int pX = (columnWidth - markWidth)/2;
			this.drawCharEx(pX ,posY, TextDrawingLayout.Center,TextDrawingLayout.Center,0,0,color,strToPrint,g);
//			g.drawString(strToPrint,pX,posY);
		}
		g.setFont(oldFont);
		g.setColor(oldColor);
		
	}
	
}

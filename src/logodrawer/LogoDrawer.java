package logodrawer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
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
		int logoHeight = 500;
		int posWidth = 80;
		
		BufferedImage bi = new BufferedImage(numberOfPositions*posWidth,logoHeight,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bi.createGraphics();
		
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		

		double maxBits =  Math.log(alphabetSize)/Math.log(2);

		g.setColor(Color.white);
		g.fillRect(0, 0, numberOfPositions*posWidth, logoHeight);
		
		g.setFont(new Font("Verdana", 0, 10));
		
		
		int xleft = 0;
		for (PositionValues positionValues : list) {
			int xright = xleft+posWidth;
			double ybottom = 0;
			double observedEntropy = 0;
			
			observedEntropy = positionValues.getObservedEntropy();
			double rsec = maxBits + observedEntropy;
			for (int i=positionValues.getListOfResidues().length()-1; i>=0;i--) {
				double ytop = ybottom + (positionValues.getValues()[i]*logoHeight*rsec/maxBits);
				
				String c = String.valueOf(positionValues.getListOfResidues().charAt(i)); 

				int h = (int)(ytop-ybottom);
				
				g.setColor(Color.white);
				
				Font f = g.getFont();
				f = f.deriveFont(10f);
				g.setFont(f);
				this.drawChar(xleft, logoHeight-(int) ybottom, h, posWidth, mycolor.getColor(positionValues.getListOfResidues().charAt(i)), c, g);
				
				ybottom = ytop;
			}
			xleft = xright;
		}
		return bi;
	}
	
	protected void 				exportJPG							(File outfile, BufferedImage bi) throws FileNotFoundException, IOException {
		FileOutputStream out = new FileOutputStream(outfile);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bi);
		param.setQuality(1.0f, false);
		encoder.setJPEGEncodeParam(param);
		encoder.encode(bi);
	}
	
	protected void drawChar(int x, int y, double height, double width, Color color, String c, Graphics2D g) {
	
		if(c.equals("I") ) c= " I ";
		
		Font of = g.getFont();
				
		double advX = (int) g.getFontMetrics(of).stringWidth(c);
		double advY = (int) g.getFontMetrics(of).getAscent() * ((500f-175f)/500f);
//		double advY = (int) g.getFontMetrics(of).getAscent() ;
		
		AffineTransform at = new AffineTransform();
		
		at.scale((double)width / (double)advX, (double)height / (double)advY );
		
		Font f = of.deriveFont(at);
		
		g.setFont(f);
		g.setColor(color);
		g.drawString(c, x, y);
		
		g.setFont(of);
	
	}
}

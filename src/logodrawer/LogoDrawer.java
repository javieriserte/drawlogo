package logodrawer;

import java.awt.Graphics2D;
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

	
	@SuppressWarnings("unused")
	private BufferedImage createImage(List<PositionValues> list, ColorStrategy color, int alphabetSize ) {
		
		int numberOfPositions = list.size();
		BufferedImage bi = new BufferedImage(numberOfPositions*10,100,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bi.createGraphics();
		
		
		
		return null;
	}
	
	@SuppressWarnings("unused")
	private void 				exportJPG							(File outfile, BufferedImage bi) throws FileNotFoundException, IOException {
		FileOutputStream out = new FileOutputStream(outfile);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bi);
		param.setQuality(1.0f, false);
		encoder.setJPEGEncodeParam(param);
		encoder.encode(bi);
	}
}

package logodrawer.cli;

import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Vector;

import logodrawer.DetectType;
import logodrawer.LogoDrawer;
import logodrawer.LogoImageLayout;
import logodrawer.MoleculeType;

import cmdGA.NoOption;
import cmdGA.Parser;
import cmdGA.SingleOption;
import cmdGA.exceptions.IncorrectParameterTypeException;
import fastaIO.FastaMultipleReader;
import fastaIO.Pair;

public class LogoDrawerCli {

	public static void main(String[] args) {
		
		Parser parser = new Parser();
		
		SingleOption inputStream = new SingleOption(parser, System.in, "--infile", InputStreamParameter.getParameter());
		
		SingleOption outFile = new SingleOption(parser, null, "--outfile", OutFileParameter.getParameter());
		
		SingleOption font = new SingleOption(parser, new Font("Verdana",Font.PLAIN,10), "--font", FontParameter.getParameter());
		
		SingleOption molType = new SingleOption(parser, null, "--type", MoleculeTypeParameter.getParameter());
		
		NoOption countGaps = new NoOption(parser, false, "--NoCountGaps");
		
		
		try {
			parser.parseEx(args);
		} catch (IncorrectParameterTypeException e1) {
			System.out.println("An error ocurred while parsing;"+" arg\n");
		}
		
		
		List<String> s = new Vector<String>();
		
		if (outFile.getValue()!=null && inputStream.isPresent() && inputStream.getValue()!=null) {
		
			BufferedReader bf = new BufferedReader( new InputStreamReader((InputStream) inputStream.getValue()));
			
			FastaMultipleReader fmr = new FastaMultipleReader();
			
			List<Pair<String,String>> seqs = fmr.readBuffer(bf);

			for(int i=0;i<seqs.size();i++) {
				s.add(seqs.get(i).getSecond());				
			}
			
		}
		
		MoleculeType mt = (MoleculeType) molType.getValue();
		
		
		if (mt==null) {
		
			if (DetectType.isProtein(s.get(0))) {
				mt = MoleculeType.Protein;
			} else {
				mt = MoleculeType.DNA;
			}
		}
			
		LogoDrawer dld = LogoDrawer.LogoDrawerFor(mt);
		
		LogoImageLayout layout = new LogoImageLayout();

		RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		renderingHints.add(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
		renderingHints.add(new RenderingHints(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY ));
		renderingHints.add(new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON ));
		renderingHints.add(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));

		
		layout.setFont((Font) font.getValue());
		layout.setLogoHeader(8);
		layout.setLogoHeight(300);
		layout.setPositionsPerLine(20);
		layout.setPosWidth(45);
		layout.setRenderingHints(renderingHints);
		layout.setRowHeight(320);
		layout.setRulerColumn(15);

		BufferedImage createImage = dld.drawLogo(s,layout,!countGaps.getValue());
		
		try {
			dld.exportJPG( (File) outFile.getValue(), createImage);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
}

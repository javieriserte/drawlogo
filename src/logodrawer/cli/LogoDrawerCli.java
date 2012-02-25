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
import logodrawer.gui.LogoDrawerGui;

import cmdGA.NoOption;
import cmdGA.Parser;
import cmdGA.SingleOption;
import cmdGA.exceptions.IncorrectParameterTypeException;
import fastaIO.FastaMultipleReader;
import fastaIO.Pair;

public class LogoDrawerCli {

	public static void main(String[] args) {
		
		if (args.length==0) {
			LogoDrawerGui.main(null);
			return;
		}
		
		Parser parser = new Parser();
		
		SingleOption inputStream = new SingleOption(parser, System.in, "--infile", InputStreamParameter.getParameter());
		
		SingleOption outFile = new SingleOption(parser, null, "--outfile", OutFileParameter.getParameter());
		
		SingleOption font = new SingleOption(parser, new Font("Verdana",Font.PLAIN,10), "--font", FontParameter.getParameter());
		
		SingleOption molType = new SingleOption(parser, null, "--type", MoleculeTypeParameter.getParameter());
		
		NoOption countGaps = new NoOption(parser, false, "--NoCountGaps");
		
		NoOption help = new NoOption(parser,false, "--help");
		
		
		try {
			parser.parseEx(args);
		} catch (IncorrectParameterTypeException e1) {
			System.out.println("An error ocurred while parsing;"+" arg\n");
		}
		
		if (help.isPresent()) {

			System.out.println("LogoDrawer Help.\nLogoDrawer is a simple utility to draw Sequences Logos as decribes in :\nSchneider TD, Stephens RM. 1990. Sequence Logos: A New Way to Display Consensus Sequences. Nucleic Acids Res. 18:6097-6100 \n\nUsage: \n       java -jar logoDrawercli.jar\n         Goes to Graphic User Interface\n\n       java -jar logoDrawercli.jar options\n         Runs command line interpreter\n         \nOptions:\n       --infile filepath \n         Reads sequences from the specified filepath. Data is expected to be in fasta format.\n         if --infile is not present then the data is readed from standard input.\n\n       --outfile filepath \n         Specifies the destination jpg file with the image.\n\n       --type moltype\n         Specifies if sequences are from DNA or Proteins, moltype can be (without the quotes):\n         \"d\" or \"DNA\"         for DNA sequences.\n         \"p\" or \"Protein\"     for Protein Sequences.\n         If --type is not present, then the type is autodetected.\n       \n       --NoCountGaps\n         If --NoCountGaps options is present, then the gaps are ignored to estimate frequencies. This result in a higher logo in the positions with gaps.\n         By default gaps are taken into account to estimate frequencies.\n         \n       --font [font name, font style]\n         By default, verdana plain font is used to draw the logos.\n         The font name can be any font installed in your system.\n         The font style is a number that specifies the style:\n           Plain        : 0.\n           Bold         : 1.\n           Italics      : 2.\n           Bold+Italics : 3.\n         The \"[\" and \"]\" symbols must be present.\n        \n       --help\n         Shows this help.\n\nExample:\n       java -jar logoDrawerCli.jar --outfile image.jpg --infile Nuc.fas\n       cat nuc.fas | java -jar logoDrawerCli.jar --outfile image.jpg \n       java -jar logoDrawerCli.jar --outfile image.jpg --infile Nuc.fas --type protein\n       java -jar logoDrawerCli.jar --outfile image.jpg --infile Nuc.fas --font [times new roman,3]\n       java -jar logoDrawerCli.jar --outfile image.jpg --infile Nuc.fas --NoCountGaps\n");
			
			return;
		}
		
		
		List<String> s = new Vector<String>();
		
				
		if (outFile.getValue()!=null && inputStream.getValue()!=null) {
		
			BufferedReader bf = new BufferedReader( new InputStreamReader((InputStream) inputStream.getValue()));
			
			FastaMultipleReader fmr = new FastaMultipleReader();
			
			List<Pair<String,String>> seqs = fmr.readBuffer(bf);

			for(int i=0;i<seqs.size();i++) {
				s.add(seqs.get(i).getSecond());				
			}
			
			System.out.println("Found " + seqs.size() + " sequences in input data");
		
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
}

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

import logodrawer.ColumnLabeler;
import logodrawer.DetectType;
import logodrawer.LogoDrawer;
import logodrawer.LogoImageLayout;
import logodrawer.MoleculeType;
import logodrawer.NumericColumnLabeler;
import logodrawer.gui.LogoDrawerGui;
import cmdGA.MultipleOption;
import cmdGA.NoOption;
import cmdGA.Parser;
import cmdGA.SingleOption;
import cmdGA.exceptions.IncorrectParameterTypeException;
import cmdGA.parameterType.IntegerParameter;
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
		
		SingleOption labelOpt = new SingleOption(parser, new NumericColumnLabeler(1), "--labels", ColumnLabelerParameter.getParameter());
		
		NoOption countGaps = new NoOption(parser, "--NoCountGaps");
		
		NoOption help = new NoOption(parser, "--help");
		
		
		Integer[] deflayout = new Integer[]{8, 300, 20, 45, 320, 15};

		MultipleOption layoutOpt = new MultipleOption(parser, deflayout, "--layout", ',', IntegerParameter.getParameter());
		
		try {
			parser.parseEx(args);
		} catch (IncorrectParameterTypeException e1) {
			System.out.println("An error ocurred while parsing;"+" arg\n");
		}
		
		if (help.isPresent()) {

			System.out.println("LogoDrawer Help.\r\nLogoDrawer is a simple utility to draw Sequences Logos as decribes in :\r\nSchneider TD, Stephens RM. 1990. Sequence Logos: A New Way to Display Consensus Sequences. Nucleic Acids Res. 18:6097-6100 \r\n\r\nUsage: \r\n       java -jar logoDrawercli.jar\r\n         Goes to Graphic User Interface\r\n\r\n       java -jar logoDrawercli.jar options\r\n         Runs command line interpreter\r\n         \r\nOptions:\r\n       --infile filepath \r\n         Reads sequences from the specified filepath. Data is expected to be in fasta format.\r\n         if --infile is not present then the data is readed from standard input.\r\n\r\n       --outfile filepath \r\n         Specifies the destination jpg file with the image.\r\n\r\n       --type moltype\r\n         Specifies if sequences are from DNA or Proteins, moltype can be (without the quotes):\r\n         \"d\" or \"DNA\"         for DNA sequences.\r\n         \"p\" or \"Protein\"     for Protein Sequences.\r\n         If --type is not present, then the type is autodetected.\r\n       \r\n       --NoCountGaps\r\n         If --NoCountGaps options is present, then the gaps are ignored to estimate frequencies. This result in a higher logo in the positions with gaps.\r\n         By default gaps are taken into account to estimate frequencies.\r\n         \r\n       --font [font name, font style]\r\n         By default, verdana plain font is used to draw the logos.\r\n         The font name can be any font installed in your system.\r\n         The font style is a number that specifies the style:\r\n           Plain        : 0.\r\n           Bold         : 1.\r\n           Italics      : 2.\r\n           Bold+Italics : 3.\r\n         The \"[\" and \"]\" symbols must be present.\r\n\r\n       --layout LogoHeader, LogoHeight, PositionsPerLine, PosWidth, RowHeight, RulerColumn \r\n         Are integers that define the layout drawing of the Sequence Logo.\r\n         Where:\r\n           LogoHeader: is the number of pixels from the top of the row to the beginning of the Logo.\r\n           LogoHeight: is the number of pixels from of the Logo.\r\n           PositionsPerLine: is the number of positions of the sequence Logo that are shown in a single line.\r\n           PosWidth: is the number os pixels of the width of each character in the logo.\r\n           RowHeight: is the number of pixels of the entire row. It includes the Logo Header, the Sequence Logo itself and the horizontal Ruler.\r\n           RulerColumn: is the number of pixels from the left to the beginning of the Sequence Logo. This part is used to draw the Vertical Ruler.\r\n         The default values are 8, 300, 20, 45, 320, 15.\r\n\r\n       --help\r\n         Shows this help.\r\n\r\nExample:\r\n       java -jar logoDrawerCli.jar --outfile image.jpg --infile Nuc.fas\r\n       cat nuc.fas | java -jar logoDrawerCli.jar --outfile image.jpg \r\n       java -jar logoDrawerCli.jar --outfile image.jpg --infile Nuc.fas --type protein\r\n       java -jar logoDrawerCli.jar --outfile image.jpg --infile Nuc.fas --font [times new roman,3]\r\n       java -jar logoDrawerCli.jar --outfile image.jpg --infile Nuc.fas --NoCountGaps\r\n       java -jar logoDrawerCli.jar --outfile image.jpg --infile Nuc.fas --NoCountGaps -- layout 8, 300, 20, 45, 350, 45\r\n");
			
			return;
		}
		
		ColumnLabeler columnLabeler = (ColumnLabeler) labelOpt.getValue();
		
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
			Object[] layoutValues = (Object[]) layoutOpt.getValues()[0];
			if (layoutValues.length != 6) {
				System.out.println("Wrong number of arguments for layout");
			}
			layout.setLogoHeader((Integer) (layoutValues[0]));
			layout.setLogoHeight((Integer) layoutValues[1]);
			layout.setPositionsPerLine((Integer) layoutValues[2]);
			layout.setPosWidth((Integer) layoutValues[3]);
			layout.setRenderingHints(renderingHints);
			layout.setRowHeight((Integer) layoutValues[4]);
			layout.setRulerColumn((Integer) layoutValues[5]);
			
			BufferedImage createImage = dld.drawLogo(s,layout,!countGaps.getValue(), columnLabeler);
		
			try {
				dld.exportPNG( (File) outFile.getValue(), createImage);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
	}
}

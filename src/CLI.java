import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.google.gson.GsonBuilder;

public class CLI {
	private String[] args = null;
	private Options options = new Options();
	
	/**
	 * CLI(arg) Takes arg parameter to pass on to the parsing method and execute methods corresponding to its operation.
	 * @param args
	 */
	public CLI(String[] args) {
		this.args = args;
		options.addOption("help", false, "Display help");
		options.addOption("getblock", true, "Print block information");
	}
	
	/**
	 * parse() Parse any command sent and react correspondingly.
	 */
	public void parse() {
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = null;
		
		try {
			cmd = parser.parse(options, args);
			if(cmd.hasOption("help")) 
				help();
			if(cmd.hasOption("getblock"))
				getblock(cmd);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * getblock() Return human readable data of the block with given blockheight.
	 * @param cmd command issued
	 */
	public void getblock(CommandLine cmd) {
		for(int i = 0; i < Blockchain.blockchain.size(); i++)
			if(Blockchain.blockchain.get(i).blockHeight == Integer.parseInt(cmd.getOptionValue("getblock"))) {
				String block = new GsonBuilder().setPrettyPrinting().create().toJson(Blockchain.blockchain.get(i));
				System.out.println(block);
		}
	}

	/**
	 * help() return list of command that can be issued
	 */
	private void help() {
		HelpFormatter formatter = new HelpFormatter();
		
		formatter.printHelp("PingCoin", options);
		System.exit(0);
	}
}
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
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
		options.addOption("getnewaddress", false, "Generate a new address");
		options.addOption("displaymypublickey", true, "Display my public key");
		options.addOption("displayprvtkey",true,"Display my private key");
		options.addOption("displaynumoftxn", true, "Display number of transaction");
		options.addOption("txninlist",true,"Display transaction in list");
		options.addOption("txnoutlist",true,"Display transaction out list");
		options.addOption("displaytxn",true,"Show all transaction");
		options.addOption("maketransfer",true,"Transfer Coin");
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
			if(cmd.hasOption("getnewaddress"))
				getnewaddress();
			if(cmd.hasOption("displaymypublickey"))
				displaymyaddress();
			if(cmd.hasOption("displayprvtkey"))
				displayprvtkey();
			if(cmd.hasOption("displaynumoftxn"))
				displaynumoftxn();
			if(cmd.hasOption("txninlist"))
				txninlist();
			if(cmd.hasOption("txnoutlist"))
				txnoutlist();
			if(cmd.hasOption("displaytxn"))
				displaytxn();
			if(cmd.hasOption("maketransfer"))
				maketransfer();
				
		} catch (ParseException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	/**
	 * transfer pingcoin to another address
	 */
	private void maketransfer() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * display all transaction
	 */
	private void displaytxn() {
		// TODO Auto-generated method stub
	}

	/**
	 * display the transaction out list
	 */
	private void txnoutlist() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * display the transaction in list
	 */
	private void txninlist() {
	
	}

	/**
	 * display number of transaction made
	 */
	private void displaynumoftxn() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * display the user's private key
	 */
	private void displayprvtkey() {
	}

	/**
	 * display the current address of the machine 
	 */
	private void displaymyaddress() {
		PublicPrivateKeyPair ppkp = new PublicPrivateKeyPair();
		System.out.println(ppkp.publicKey);
	}
	/**
	 * getnewaddress() Return generated address derived from public key
	 * @throws NoSuchAlgorithmException
	 */
	private void getnewaddress() throws NoSuchAlgorithmException {
		Security.addProvider(new BouncyCastleProvider());
		PublicPrivateKeyPair PPP = new PublicPrivateKeyPair();
		System.out.println(PPP.generateAddress());
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

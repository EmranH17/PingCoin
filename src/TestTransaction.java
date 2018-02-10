import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;

public class TestTransaction {
	

	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static int difficulty = 5;
	public static PublicPrivateKeyPair walletA;
	public static PublicPrivateKeyPair walletB;
	
	//List all unspent transaction
	public static HashMap<String,TransactionOut> UTXOs = new HashMap<String,TransactionOut>(); //list of all unspent transactions. 
	
	public static void testTransaction() {
	//Setup Bouncey castle as a Security Provider
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider()); 
			//Create the new wallets
			walletA = new PublicPrivateKeyPair();
			walletB = new PublicPrivateKeyPair();
			//Test public and private keys
			System.out.println("Private and public keys:");
			System.out.println(HashFunctionUtility.getStringFromKey(walletA.privateKey));
			System.out.println(HashFunctionUtility.getStringFromKey(walletA.publicKey));
			//Create a test transaction from WalletA to walletB 
			Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
			transaction.signature = transaction.generateSignature(walletA.privateKey);
			//Verify the signature works and verify it from the public key
			System.out.println("Is signature verified");
			System.out.println(transaction.verifiySignature());
	}
}

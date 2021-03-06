import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.GsonBuilder;

public class TestClass {
	
	public static Blockchain blockchain = new Blockchain();
	public static Database database = new Database();
	
	public static int difficulty = 5;
	public static PublicPrivateKeyPair walletA;
	public static PublicPrivateKeyPair walletB;
	public static TestTransaction testTransaction;

	public static void main(String[] args) throws NoSuchAlgorithmException, NumberFormatException, IOException, InterruptedException {
		//add our blocks to the block chain ArrayList:
			blockchain.add( new Block("Genesis", "0") );
						
		//Print the whole BlockChain
			System.out.println("Print the Genesis block:");
			String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(Blockchain.blockchain);
			System.out.println(blockchainJson);
						
		//Supposed that we met a condition where we need to elect a block master from the BlockChain
		//Add the new block created by the elected Block
			System.out.println("\n\nA block master is elected and new block is generated and added to the blockchain");
			blockMasterElection elector = new blockMasterElection();
			blockchain.add( new Block( "New block generated by genesis block", elector.electBlock( blockchain ).blockHash ) );
				
		//Print the whole BlockChain again
			System.out.println("\n\nPrint the blockchain with the generated block added:");
			blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(Blockchain.blockchain);
			System.out.println(blockchainJson);
				
		//Testing: Save the entire blockchain to database file named blockchainDb.db on PingCoin root folder
		//Recommended to use http://sqlitebrowser.org/ to open the .db file
			
			System.out.println("\n\nWriting local blockchain to database file");
			for (int i = 0; i < blockchain.size(); i ++)
				database.addBlock(blockchain.get(i));
			
		
			System.out.println("\n\nReading stored blockchain:");
			database.readBlock();
				
		//testing markleTree
			List<String> tempTxList = new ArrayList<String>();
				tempTxList.add("TXIN:x,Value:50,TXOUT:y");
			    tempTxList.add("TXIN:y,Value:20,TXOUT:g");
			    tempTxList.add("TXIN:a,Value:10,TXOUT:b");
			    tempTxList.add("TXIN:t,Value:15,TXOUT:v");
			    tempTxList.add("TXIN:L,Value:67,TXOUT:o");
			    
			    markleTree merkleTrees = new markleTree(tempTxList);
			    merkleTrees.merkle_tree();
			    System.out.println("\n\nPrinting the merkle root of y, g, b, v, o:\nroot : " + merkleTrees.getRoot());
				    
		//Transaction Test
		    System.out.println("\n\nTesting transaction:");
			testTransaction = new TestTransaction();
			TestTransaction.testTransaction();
			
		//Address generation test
			PublicPrivateKeyPair PPP = new PublicPrivateKeyPair();
			System.out.println("\n\nAddress Generation Test:\nAddress: " + PPP.generateAddress());
			

		//Peer testing
			PeerNode pn = new PeerNode("127.0.0.1","50696");

		//serialization test
			Block block = new Block(null, null);
			block.serialize();
			block.deserialize();

	}
			
	public static Boolean isChainValid() {
    	Block currentBlock; 
    	Block previousBlock;
    	
    	//loop through block chain to check hashes
    	
    	for(int i=1; i < blockchain.size(); i++) {
    		currentBlock = blockchain.get(i);
    		previousBlock = blockchain.get(i-1);
    		//compare registered hash and calculated hash:
    		if(!currentBlock.blockHash.equals(currentBlock.calculateHash()) ){
    			System.out.println("Current Hashes not equal");			
    			return false;
    		}
    		//compare previous hash and registered previous hash
    		if(!previousBlock.blockHash.equals(currentBlock.previousHash) ) {
    			System.out.println("Previous Hashes not equal");
    			return false;
    		}
    	}
    	return true;
    }
}

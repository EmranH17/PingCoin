import java.util.ArrayList;
import java.util.Random;
import com.google.gson.GsonBuilder;

public class Main {
	
	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	static Block random;
	
	public static void main(String[] args) {
		
		//add our blocks to the block chain ArrayList:
				blockchain.add( new Block("Genesis", "0") );		
				
		//Print the whole blockchain
				String blockchainJson = new GsonBuilder()
						.setPrettyPrinting()
						.create()
						.toJson(blockchain);
				
				System.out.println(blockchainJson);
				
		//Supposed that we met a condition where we need to elect a block master
				electBlockMaster();
		
		//New block created from the elected Block
				blockchain.add(
						new Block("New block generated by block height: " + (int) random.blockHeight,blockchain
								.get( (int)random.blockHeight )
								.calculateHash() ) );
		
		//Print the whole blockchain again
				blockchainJson = new GsonBuilder()
						.setPrettyPrinting()
						.create()
						.toJson(blockchain);
				
				System.out.println("\n\n" + blockchainJson);
	}
	
	//Elect a block master randomly(unsecure)
	public static void electBlockMaster() {
		Random randomizer = new Random();
		random = blockchain.get( randomizer.nextInt( blockchain.size() ) );
		
		System.out.println("\n\nElected block height: " + random.blockHeight);
	}
	
	public static long getBlockHeight() {
		return blockchain.size() - 1;
	}
}

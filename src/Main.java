import java.util.ArrayList;
import com.google.gson.GsonBuilder;


public class Main {

	//Simple example of how to use BlockGenerator module
	
	public static ArrayList<Block> blockchain = new ArrayList<Block>(); 
	
	public static void main(String[] args) {
		
		//add our blocks to the block chain ArrayList:
				blockchain.add(new Block("Genesis", "0"));		
				blockchain.add(new Block("2nd block",blockchain.get(blockchain.size()-1).hash)); 
				blockchain.add(new Block("3rd block",blockchain.get(blockchain.size()-1).hash));
				
				String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);		
				System.out.println(blockchainJson);
	}

}

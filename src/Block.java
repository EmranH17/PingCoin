import java.util.Date;

public class Block {

	String previousHash;
	long blockHeight;
	private String data;
	private long timeStamp;
	
	

	//Block Constructor.
	public Block(String data,String previousHash) {
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.blockHeight = Main.getBlockHeight() + 1;		
	}
	
	//Return the hash of the current block
	public String calculateHash() {
		String calculatedhash = HashFunctionUtility.applySha256(previousHash + Long.toString(timeStamp) + data);
		return calculatedhash;
	}
	
	//Return the height of the current block
	public long getBlockHeight() {
		return blockHeight;
	}
}

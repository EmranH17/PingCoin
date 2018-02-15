import java.util.Date;

public class Block {
	
	public String blockHash;
	public long timeStamp;
	public int blockHeight;
	public String previousHash;
	public String data;

	//Block Constructor.
	public Block(String data,String previousHash) {
		this.timeStamp = new Date().getTime();
		this.blockHeight = Blockchain.blockchain.size();
		this.previousHash = previousHash;
		this.data = data;
		this.blockHash = HashFunctionUtility.applySha256(Long.toString(timeStamp) + blockHeight + this.previousHash + this.data);
	}
	
	//Return the height of the current block
	public long getBlockHeight() {
		return blockHeight;
	}
	
	//Return the raw String representation of the block, useful when saving the block or sending it to a peer. 
	public String getRawBlock()
    {
        String rawBlock = ""; 
        rawBlock = timeStamp + "," + blockHeight + "," + previousHash + "," + data;
        return rawBlock; 
    }
	
	public String calculateHash() {
		String calculatedhash = HashFunctionUtility.applySha256(Long.toString(timeStamp) + blockHeight + previousHash + data);
		return calculatedhash;
	}
}

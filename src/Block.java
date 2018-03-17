import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

//added java.io.Serializable
public class Block implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8755438464942566885L;
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
	
	public String calculateHash() {
		String calculatedhash = HashFunctionUtility.applySha256(Long.toString(timeStamp) + blockHeight + previousHash + data);
		return calculatedhash;
	}
	
	//serialization
	public void serialize(){
		Block block = new Block(null,null);
		block.timeStamp = timeStamp;
		block.blockHeight = blockHeight;
		try {
			FileOutputStream fileout = new FileOutputStream("serializedBlock.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileout);
			out.writeObject(block);
			out.close();
			fileout.close();
			System.out.println("\n\nserializedBlock.ser generated");
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
		
	//deserialization
	public void deserialize() {
		Block dblock = null;
		try {
			FileInputStream fileIn = new FileInputStream("serializedBlock.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			dblock = (Block) in.readObject();
			in.close();
			fileIn.close();
		}catch(IOException ex) {
			ex.printStackTrace();
			return;
		}catch(ClassNotFoundException ex) {
			System.out.println("\n\nBlock class not found");
			ex.printStackTrace();
			return;
		}
		timeStamp = dblock.timeStamp;
		blockHeight = dblock.blockHeight;
		System.out.println("\nDeserializing Block:");
		System.out.println("\nTime Stamp: "+timeStamp);
		System.out.println("Block Height: "+blockHeight);
	}
}

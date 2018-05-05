import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Date;

public class Block implements Serializable {

	static final long serialVersionUID = 1L; //For serialization
	Transaction transactions;
	Account generator;
	long blockTimestamp;
	long baseTarget;
	byte[] generationSignature;
	
	long initialBaseTarget;
	long maxBaseTarget;
	
	//Block Constructor.
	public Block() {
	}
	
	public void formBlock(Block prevBlock, Account gen, Transaction txn, boolean genesis) {
		this.blockTimestamp = new Date().getTime();
		
		if(genesis)
			this.initialBaseTarget = new Date().getTime();
		else
			this.initialBaseTarget = prevBlock.initialBaseTarget;
		
		this.transactions = txn;
		
		this.maxBaseTarget = this.initialBaseTarget * PCSystem.getSystemBalance();
		
		if(!((this.blockTimestamp - prevBlock.blockTimestamp) <= (prevBlock.baseTarget / 2) ))
			if(!((this.blockTimestamp - prevBlock.blockTimestamp) > prevBlock.baseTarget * 2))
				if(!((this.blockTimestamp - prevBlock.blockTimestamp) < 1))
					if(!((this.blockTimestamp - prevBlock.blockTimestamp) > maxBaseTarget))
						this.baseTarget = prevBlock.baseTarget * (this.blockTimestamp - prevBlock.blockTimestamp);
					else
						System.err.println("Error in calculating Block's baseTarget!\n"
								+ "Its more than maxBaseTarget");
				else
					System.err.println("Error in calculating Block's baseTarget!\n"
							+ "Its less than 1");
			else
				System.err.println("Error in calculating Block's baseTarget!\n"
						+ "Its more than 2 times of the previous Block's baseTarget");
		else
			System.err.println("Error in calculating Block's baseTarget!\n"
					+ "Its less then half of the previous Block's baseTarget");
	}
	
	/**
	 * This method will produce a signature from GenerationSignature of previous Block
	 * and the generator's public key.
	 * 
	 * @param prevBlock Previous Block.
	 * @param Account of the generator to sign the current block.
	 * @return byte[] containing signature data.
	 */
	public byte[] calculateGenerationSignature (Block prevBlock, Account account) {
		return HashFunctionUtility.applySha256(
			ByteArrayUtility.concateByteArray(
				prevBlock.getGenerationSignature(),
				account.publicKey
			)
		);
	}
	
	/**
	 * This method will return a deterministic "hit" integer value that depends on the willing account(forging)
	 * to generate a block when suitable condition is satisfied.
	 * 
	 * @param calculatedGS Calculated Generation Signature.
	 * @return number constructed from first 8 byte of calculatedGS.
	 */
	public int calculateHit(byte[] calculatedGS) {
		ByteBuffer bb = ByteBuffer.wrap(ByteArrayUtility.getFirstNByte(calculatedGS, 8));
		return bb.getInt();
	}
	
	public byte[] getGenerationSignature() {
		return generationSignature;
	}
}

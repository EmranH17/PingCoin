
/**
 * Simple Database class template.
 * addBlock
 * More TODO methods...
 */
public class Database {
	public Blockchain blockchain;
	
	public Database() {
		this.blockchain = new Blockchain();
	}

	/**
     * Attempts to add a block to the blockchain; passthrough to Blockchain.addBlock(block).
     * 
     * @param block Block to add
     */
	public void addBlock(Block block) {
		blockchain.addBlock(block);
	}
}

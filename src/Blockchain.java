import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Simple Block class template.
 * addBlock
 * writeBlockToFile
 * More TODO methods...
 */
public class Blockchain {

	//Empty Constructor
	public Blockchain(){
    }
	
	/** This method attempts to add a block to the blockchain.
	 * 
	 * @param block Block to add to the blockchain
	 * @return boolean Whether adding the block was unsuccessful. Most common source of returning false is a block that doesn't verify.
	 */
	public boolean addBlock(Block block) {
		return writeBlockToFile(block);
		/*TODO:
		 *  Verification
		 *  Read from File
		 */
	}
	
	/**
     * Writes a block to the blockchain file
     * 
     * @return boolean Whether write was successful
     */
	private boolean writeBlockToFile(Block block) {
		System.out.println("Writing a block to file...");
        try (FileWriter fileWriter = new FileWriter("blockchain.dta", true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        PrintWriter out = new PrintWriter(bufferedWriter))
        {
            out.println(block.getRawBlock());
        } catch (Exception e)
        {
            System.out.println("ERROR: UNABLE TO SAVE BLOCK TO DATABASE!");
            e.printStackTrace();
            return false;
        }
        return true;
	}
}

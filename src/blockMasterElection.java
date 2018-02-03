import java.security.SecureRandom;
import java.util.ArrayList;

public class blockMasterElection {
	Block electedBlock;
	SecureRandom randomizer = new SecureRandom();
	
	//Elect a block randomly from array of blocks given
	public Block electBlock(ArrayList<Block> activeBlock) {
		electedBlock = activeBlock.get( randomizer.nextInt( activeBlock.size() ) );
		return electedBlock;
	}
}
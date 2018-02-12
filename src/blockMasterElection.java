import java.security.SecureRandom;

public class blockMasterElection {
	Block electedBlock;
	SecureRandom randomizer = new SecureRandom();
	
	//Elect a block randomly from array of blocks given
	public Block electBlock(Blockchain blockchain) {
		electedBlock = blockchain.get( randomizer.nextInt( blockchain.size() ) );
		return electedBlock;
	}
}
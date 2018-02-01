import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestBlockGeneration {

	@Test
	public void test() {
		
		//We create a block and store the RawString of the code and check the calculated hash
		
		String genesisBlockRawString = BlockGenerator.compileBlock(
				20180201140138L, //Hardcoded timestamp
				0, //Hardcoded block number/index
				"c9a2ebe3c4a631b66d03095553db3370466d5fb8122ce1485992e83338864947", //SHA-256 Hash of "PingCoin"(previousHash)
				"b42c47ef6b54655f061cb40201eaf68e7c97abc6b8f12584526585347663bfd1"); //SHA-256 Hash of "Calvin"(signingAdress)
		
		Assertions.assertEquals(
				"{20180201140138:0:c9a2ebe3c4a631b66d03095553db3370466d5fb8122ce1485992e83338864947}," + //First section of Block's RawString
				"{91A9E13B53A65154EBFB9CD848A6690643A05B9E2631164BAF5CE02FB18F4D45},"+ //Calculated hash of the block(This is what we need to check)
				"{b42c47ef6b54655f061cb40201eaf68e7c97abc6b8f12584526585347663bfd1}", //Address that used to sign this block(Who generate and rightful owner of this block)
				genesisBlockRawString);
	}
}

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestBlockGeneration {

	@Test
	public void test() {
		String genesisBlockRawString = BlockGenerator.compileBlock(
				20180201140138L,
				0, 
				"c9a2ebe3c4a631b66d03095553db3370466d5fb8122ce1485992e83338864947", //Hash of "PingCoin"
				"b42c47ef6b54655f061cb40201eaf68e7c97abc6b8f12584526585347663bfd1"); //Hash of "Calvin"
		
		Assertions.assertEquals(
				"{20180201140138:0:c9a2ebe3c4a631b66d03095553db3370466d5fb8122ce1485992e83338864947}," + 
				"{91A9E13B53A65154EBFB9CD848A6690643A05B9E2631164BAF5CE02FB18F4D45},"+ 
				"{b42c47ef6b54655f061cb40201eaf68e7c97abc6b8f12584526585347663bfd1}", 
				genesisBlockRawString);
	}
}

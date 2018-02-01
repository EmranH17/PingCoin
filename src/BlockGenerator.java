import java.security.MessageDigest;

import javax.xml.bind.DatatypeConverter;

public class BlockGenerator {
	
	public static String compileBlock(long timestamp, int blockNum, String previousBlockHash, String signingAdress)
    {
		//Debugging purposes
        System.out.println("Creating block...");
        System.out.println("timestamp: " + timestamp);//Timestamp, the format need to be finalized.(Research on Epoch Time)
        System.out.println("blockNum: " + blockNum); //Block number/index
        System.out.println("previousBlockHash: " + previousBlockHash); //Calculated hash of the previous block
        System.out.println("signingAdress: " + signingAdress); //Address that used to sign this block(Who generate and rightful owner of this block)
        
        String block = "";
        block += "{" + timestamp + ":" + blockNum + ":" + previousBlockHash + "}"; //Concatenate the Strings
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-256"); 
            String blockHash =  DatatypeConverter.printHexBinary(md.digest(block.getBytes("UTF-8"))); //Magic part where the block is being hashed
            
            block += ",{" + blockHash + "},{" + signingAdress + "}"; //Concatenate the Strings
            System.out.println("Raw String of the Genesis block: " + block); //Debugging purposes
            return block;
        } catch (Exception e)
        {
            System.out.println("[CRITICAL ERROR] UNABLE TO GENERATE BLOCK");
            e.printStackTrace();
            return null;
        }
    }
}
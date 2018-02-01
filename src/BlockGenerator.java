import java.security.MessageDigest;

import javax.xml.bind.DatatypeConverter;

public class BlockGenerator {
	
	public static String compileBlock(long timestamp, int blockNum, String previousBlockHash, String signingAdress)
    {
        System.out.println("Creating block...");
        System.out.println("timestamp: " + timestamp);
        System.out.println("blockNum: " + blockNum);
        System.out.println("previousBlockHash: " + previousBlockHash);
        System.out.println("signingAdress: " + signingAdress);
        
        String block = "";
        block += "{" + timestamp + ":" + blockNum + ":" + previousBlockHash + "}";
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String blockHash =  DatatypeConverter.printHexBinary(md.digest(block.getBytes("UTF-8")));
            block += ",{" + blockHash + "},{" + signingAdress + "}";
            System.out.println("pre-block: " + block);
            return block;
        } catch (Exception e)
        {
            System.out.println("[CRITICAL ERROR] UNABLE TO GENERATE BLOCK");
            e.printStackTrace();
            return null;
        }
    }
}
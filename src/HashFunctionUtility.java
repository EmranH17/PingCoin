import java.security.Key;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

public class HashFunctionUtility {
	
		/**
		 * Apply SHA-256 hashing algorithm and return the produced hash of the ByteArray.
		 * @param byteArray ByteArray to be hashed by SHA-256 algorithm.
		 * @return SHA-256 hashed ByteArray.
		 */
		public static byte[] applySha256(byte[] byteArray){
			try {
				MessageDigest digest = MessageDigest.getInstance("SHA-256");
				return digest.digest(byteArray);
			}
			catch(Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		/**
		 * Convert ByteArray to Hexadecimal String
		 * @param bytes ByteArray to be converted.
		 * @return String in Hexadecimal form.
		 */
		public static String byteToHexString(byte[] bytes) {
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < bytes.length; i++) {
				String hex = Integer.toHexString(0xff & bytes[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		}
		
		/**
		 * Convert Hexadecimal String to ByteArray.
		 * @param s String to be converted.
		 * @return byte[] ByteArray.
		 */
		public static byte[] hexStringToByteArray(String s) {
			int len = s.length();
			byte[] data = new byte[len / 2];
			for (int i = 0; i < len; i += 2) {
				data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i+1), 16));
			}
			return data;
		}
		
		public static byte[] applyECDSASig(PrivateKey privateKey, String input) {
			Signature dsa;
			byte[] output = new byte[0];
			try {
				dsa = Signature.getInstance("ECDSA", "BC");
				dsa.initSign(privateKey);
				byte[] strByte = input.getBytes();
				dsa.update(strByte);
				byte[] realSig = dsa.sign();
				output = realSig;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			return output;
		}
		
		//Verifies a String signature 
		public static boolean verifyECDSASig(PublicKey publicKey, String data, byte[] signature) {
			try {
				Signature ecdsaVerify = Signature.getInstance("ECDSA", "BC");
				ecdsaVerify.initVerify(publicKey);
				ecdsaVerify.update(data.getBytes());
				return ecdsaVerify.verify(signature);
			}catch(Exception e) {
				throw new RuntimeException(e);
			}
		}

		public static String getStringFromKey(Key key) {
			return Base64.getEncoder().encodeToString(key.getEncoded());
	}
}
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;

public class PublicPrivateKeyPair {

	/*Public and Private key
	 * will be integrate as/into wallet class later on*/
	public PrivateKey privateKey;
	public PublicKey publicKey;
	
	
	public PublicPrivateKeyPair() {
		generateKeyPair();
	}
		
	//generate public and private key pair
		public void generateKeyPair() {
			try {
				KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
				SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
				ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
				// Initialize the key generator and generate a KeyPair
				keyGen.initialize(ecSpec, random);   //256 bytes provides an acceptable security level
		        	KeyPair keyPair = keyGen.generateKeyPair();
		        	// Set the public and private keys from the keyPair
		        	privateKey = keyPair.getPrivate();
		        	publicKey = keyPair.getPublic();
			}catch(Exception e) {
				throw new RuntimeException(e);
			}
		}
}
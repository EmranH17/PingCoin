import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

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
		        	backupWallet(privateKey, publicKey);
			}catch(Exception e) {
				throw new RuntimeException(e);
			}
		}
		
	/**
	 * Backup PrivateKey and PublicKey into a database file.
	 * @param privateKey The Private Key
	 * @param publicKey The Public Key
	 * @throws Exception Exception thrown
	 */
		public void backupWallet(PrivateKey privateKey, PublicKey publicKey) throws Exception {
			System.out.println("Backup wallet to file...");
			
			// SQLite connection string
			String url = "jdbc:sqlite:wallet.db";
	        
	        // SQL statement for creating a new table
	        String sql = "CREATE TABLE IF NOT EXISTS Block (\n"
	                + "	privateKey STRING,\n"
	                + "	publicKey STRING\n"
	                + ");";
	        
	        try (Connection conn = DriverManager.getConnection(url);
	                Statement stmt = conn.createStatement()) {
	            // create a new table
	            stmt.execute(sql);
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	 
			// SQLite connection string
	        String url1 = "jdbc:sqlite:wallet.db";
	        Connection conn = null;
	        
	        try {
	            conn = DriverManager.getConnection(url1);
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	        
	        String sql1 = "INSERT INTO Block(privateKey,publicKey) VALUES(?,?)";
	        
	        try (PreparedStatement pstmt = conn.prepareStatement(sql1)) {
	            pstmt.setBytes(1, privateKey.getEncoded());
	            pstmt.setBytes(2, publicKey.getEncoded());
	            pstmt.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
		}
		
		
	/**
	 * Generate a Address used to receive coin derived from public key.
	 * Not Complete!
	 * @throws Exception Instance of SHA-256 cannot be retrieved!
	 */
		public void generateAddress() throws Exception {
				int i = 0;
				
			//Print PingCoin in byte
				i = 0;
				byte[] pingCoinInByte = new byte["PingCoin".getBytes().length];
				for(byte bb : "PingCoin".getBytes())
					pingCoinInByte[i++] = bb;
				
				StringBuffer hexString = new StringBuffer();
				for (i = 0; i < pingCoinInByte.length; i++) {
					String hex = Integer.toHexString(0xff & pingCoinInByte[i]);
					if(hex.length() == 1) hexString.append('0');
					hexString.append(hex);
				}
				
				System.out.println("Step 1: Concatenate Byte of 'PingCoin' with public key\nPingCoin in bytes: " + hexString);
			
				
			//Print public key
				byte[] publicKey1 = publicKey.getEncoded();
				
				hexString.delete(0, hexString.length());
				for (i = 0; i < publicKey1.length; i++) {
					String hex = Integer.toHexString(0xff & publicKey1[i]);
					if(hex.length() == 1) hexString.append('0');
					hexString.append(hex);
				}
				
				System.out.println("Public key: " + hexString);
				
			//Print PingCoin + Public Key
				byte[] pingCoinWithPublicKey = new byte[pingCoinInByte.length + publicKey1.length];
				System.arraycopy(pingCoinInByte, 0, pingCoinWithPublicKey, 0, pingCoinInByte.length);
				System.arraycopy(publicKey1, 0, pingCoinWithPublicKey, pingCoinInByte.length, publicKey1.length);
	
				hexString.delete(0, hexString.length());
				for (i = 0; i < pingCoinWithPublicKey.length; i++) {
					String hex = Integer.toHexString(0xff & pingCoinWithPublicKey[i]);
					if(hex.length() == 1) hexString.append('0');
					hexString.append(hex);
				}
				
				System.out.println("PingCoin + Public Key: " + hexString);
			
			
			//Print SHA256 of PingCoin + Public key
				Security.addProvider(new BouncyCastleProvider());
				MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
	
				byte[] pingCoinWithPublicKeySHA256 = messageDigest.digest(pingCoinWithPublicKey);
				
				hexString.delete(0, hexString.length());
				for (i = 0; i < pingCoinWithPublicKeySHA256.length; i++) {
					String hex = Integer.toHexString(0xff & pingCoinWithPublicKeySHA256[i]);
					if(hex.length() == 1) hexString.append('0');
					hexString.append(hex);
				}
				
				System.out.println("SHA-256 of PingCoin + Public key: " + hexString);

				
			//Concatenate bytes of PingCoin to SHA256 of publicKey
				
			
			//Apply RIPEMD-160 hashing algorithm to SHA256 hashed PingCoin + public key
				byte[] RIPEMD160Hash = Ripemd160.getHash(pingCoinWithPublicKey);
				
				hexString.delete(0, hexString.length());
				for (i = 0; i < RIPEMD160Hash.length; i++) {
					String hex = Integer.toHexString(0xff & RIPEMD160Hash[i]);
					if(hex.length() == 1) hexString.append('0');
					hexString.append(hex);
				}
				
				System.out.println("After RIPEMD-160: " + hexString);
			
			//Apply X2 SHA256 hashing algorithm to version byte + RIPEMD-160 hashed SHA256 hashed PingCoin + public key
				byte[] SHA256Hash_of_RIPEMD160Hash = Ripemd160.getHash(RIPEMD160Hash);
				byte[] _2SHA256Hash_of_RIPEMD160Hash = Ripemd160.getHash(SHA256Hash_of_RIPEMD160Hash);
				
				hexString.delete(0, hexString.length());
				for (i = 0; i < _2SHA256Hash_of_RIPEMD160Hash.length; i++) {
					String hex = Integer.toHexString(0xff & _2SHA256Hash_of_RIPEMD160Hash[i]);
					if(hex.length() == 1) hexString.append('0');
					hexString.append(hex);
				}
				
				System.out.println("2SHA of RIPEMD160: " + hexString);
				
				
				byte[] Address = new byte[RIPEMD160Hash.length + 4];
				System.arraycopy(RIPEMD160Hash, 0, Address, 0, RIPEMD160Hash.length);
				System.arraycopy(_2SHA256Hash_of_RIPEMD160Hash, 0, Address, RIPEMD160Hash.length, 4);
				
				hexString.delete(0, hexString.length());
				for (i = 0; i < Address.length; i++) {
					String hex = Integer.toHexString(0xff & Address[i]);
					if(hex.length() == 1) hexString.append('0');
					hexString.append(hex);
				}
				
				System.out.println("Before Base58: " + hexString);
			
			String Base58Address = Base58.encode(Address);
			
			System.out.println("After Base58: " + Base58Address);
		}
}
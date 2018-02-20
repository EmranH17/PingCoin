import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
	 * Backup PrivateKey and PublicKey into a database file every time PrivateKey and PublicKey are generated.
	 * @param privateKey The Private Key
	 * @param publicKey The Public Key
	 */
		public void backupWallet(PrivateKey privateKey, PublicKey publicKey) {
			//System.out.println("Backup wallet to file...");
			
			
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
	 * Generate an Address used to receive coin derived from public key.
	 * @throws NoSuchAlgorithmException Instance of SHA-256 algorithm cannot be retrieved!
	 */
		public String generateAddress() throws NoSuchAlgorithmException {
				int i = 0;
				
				
			//Convert String "PingCoin" into byte counterpart
				i = 0;
				byte[] pingCoinInByte = new byte["PingCoin".getBytes().length];
				for(byte bb : "PingCoin".getBytes())
					pingCoinInByte[i++] = bb;
				
				//System.out.println("Step 1: Concatenate Byte of 'PingCoin' with public key\nPingCoin in bytes: " + HashFunctionUtility.byteToString(pingCoinInByte));
			
				
			//Get public key
				byte[] publicKey1 = publicKey.getEncoded();
				
				//System.out.println("Public key: " + HashFunctionUtility.byteToString(publicKey1));
				
				
			//Concatenate PingCoin + Public Key
				byte[] pingCoinWithPublicKey = new byte[pingCoinInByte.length + publicKey1.length];
				System.arraycopy(pingCoinInByte, 0, pingCoinWithPublicKey, 0, pingCoinInByte.length);
				System.arraycopy(publicKey1, 0, pingCoinWithPublicKey, pingCoinInByte.length, publicKey1.length);
				
				//System.out.println("PingCoin + Public Key: " + HashFunctionUtility.byteToString(pingCoinWithPublicKey));
			
			
			//Apply SHA256 hash algorithm to concatenated PingCoin + Public key bytes
				Security.addProvider(new BouncyCastleProvider());
				MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
				
				byte[] pingCoinWithPublicKeySHA256 = messageDigest.digest(pingCoinWithPublicKey);
				
				//System.out.println("SHA-256 of PingCoin + Public key: " + HashFunctionUtility.byteToString(pingCoinWithPublicKeySHA256));

			
			//Apply RIPEMD-160 hashing algorithm to SHA256 hashed PingCoin + public key & add byte version 0x01 for testnet to the front of the RIPEMD160 Hash
				byte[] RIPEMD160Hash = Ripemd160.getHash(pingCoinWithPublicKeySHA256);
				byte[] RIPEMD160HashWithByteVersion = new byte[RIPEMD160Hash.length + 1];
				System.arraycopy(new byte[]{0}, 0, RIPEMD160HashWithByteVersion, 0, 1);
				System.arraycopy(RIPEMD160Hash, 0, RIPEMD160HashWithByteVersion, 1, RIPEMD160Hash.length);
				
				//System.out.println("After RIPEMD-160: " + HashFunctionUtility.byteToString(RIPEMD160HashWithByteVersion));
				//System.out.println("RIPEMD-160 with byte version: " + HashFunctionUtility.byteToString(RIPEMD160HashWithByteVersion));
			
				
			//Apply two times SHA256 hashing algorithm to version byte + RIPEMD-160 hashed SHA256 hashed PingCoin + public key
				byte[] SHA256Hash_of_RIPEMD160Hash = Ripemd160.getHash(RIPEMD160HashWithByteVersion);
				byte[] _2SHA256Hash_of_RIPEMD160Hash = Ripemd160.getHash(SHA256Hash_of_RIPEMD160Hash);
				
				//System.out.println("X2 SHA of RIPEMD160: " + HashFunctionUtility.byteToString(_2SHA256Hash_of_RIPEMD160Hash));
				
				byte[] Address = new byte[RIPEMD160HashWithByteVersion.length + 4];
				System.arraycopy(RIPEMD160HashWithByteVersion, 0, Address, 0, RIPEMD160HashWithByteVersion.length);
				System.arraycopy(_2SHA256Hash_of_RIPEMD160Hash, 0, Address, RIPEMD160HashWithByteVersion.length, 4);
				
				//System.out.println("Before Base58: " + HashFunctionUtility.byteToString(Address));
			
				String Base58Address = Base58.encode(Address);
			
				//System.out.println("Address Generated!: " + Base58Address);
				return Base58Address;
		}
}
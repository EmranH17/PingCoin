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
}
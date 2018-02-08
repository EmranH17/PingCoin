/**
 * Simple Database class template.
 * addBlock
 * More TODO methods...
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	public Blockchain blockchain;
	
	public Database() {
		this.blockchain = new Blockchain();
		createInitialBlockchainDatabase();
	}
	
	/**
     * Create the initial blockchain database if does not exist.
     */
	private void createInitialBlockchainDatabase() {
		// SQLite connection string
		String url = "jdbc:sqlite:blockchainDb.db";
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS Block (\n"
        		+ "	blockHeight INTEGER,\n"
                + "	timeStamp INTEGER,\n"
                + "	previousHash STRING,\n"
                + "	data STRING,\n"
                + "	blockHash STRING PRIMARY KEY\n"
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}

	/**
     * Attempts to add a block to the blockchain; passthrough to Blockchain.addBlock(block).
     * 
     * @param block Block to add
     */
	public void addBlock(Block block) {
		blockchain.writeBlockToDatabaseFile(block);
	}
	
	/**
     * Attempts to read blocks from the blockchain database file; passthrough to Blockchain.readBlocksFromDatabaseFile().
     * 
     * @param block Block to add
     */
	public void readBlock() {
		blockchain.readBlocksFromDatabaseFile();
	}
}

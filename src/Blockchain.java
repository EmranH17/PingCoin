import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

/**
 * Simple Blockchain class template.
 * writeBlockToDatabaseFile
 * readBlocksFromDatabaseFile
 * More TODO methods...
 */
public class Blockchain {
	static LinkedList<Block> blockchain = new LinkedList<Block>();

	//Empty Constructor
	public Blockchain(){
    }
	
/*	*//**
     * Writes a block to the blockchain database file
     *//*
	public void writeBlockToDatabaseFile(Block block) {
		// SQLite connection string
        String url = "jdbc:sqlite:blockchainDb.db";
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        String sql = "INSERT INTO Block(blockHeight,timeStamp,previousHash,data,blockHash) VALUES(?,?,?,?,?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	pstmt.setInt(1, block.blockHeight);
            pstmt.setLong(2, block.timeStamp);
            pstmt.setString(3, block.previousHash);
            pstmt.setString(4, block.data);
            pstmt.setString(5, block.blockHash);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}*/
	
	/**
     * Read blocks from the blockchain database file
     */
	public void readBlocksFromDatabaseFile(){
		// SQLite connection string
        String url = "jdbc:sqlite:blockchainDb.db";
        String sql = "SELECT blockHeight,timeStamp,previousHash,data,blockHash FROM block";
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        try (Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("blockHeight") +  "\t" + 
                                   rs.getLong("timeStamp") + "\t" +
                                   rs.getString("previousHash") + "\t" +
                                   rs.getString("data") + "\t" +
                                   rs.getString("blockHash"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

	public int size() {
		return blockchain.size();
	}

	public void add(Block block) {
		blockchain.add(block);
	}

	public Block get(int index) {
		return blockchain.get(index);
	}
}

import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class ServerThread extends Thread {
    private Socket socket = null;
    private ArrayList<Block> tempBlock = new ArrayList<Block>();

    public ServerThread(Socket socket, Block block) {
        super("ServerThread");
        this.socket = socket;
        this.tempBlock.add(block);
    }
    
    public void run() {

        try (
    		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ) {
            
            //Initial
        	//tempBlock.add(new Block("Test1","1"));
        	//out.writeObject(new Block("Test1","Test1"));
            
            for(Block b : tempBlock)
            	out.writeObject(b);
            
        } catch (IOException e) {
            System.err.println("IO error: " + e.getMessage());
            System.exit(1);
        }
        
        try {
        	System.out.println("Connection closed");
			socket.close();
			System.exit(0);
		} catch (IOException e) {
			System.err.println("IO error: " + e.getMessage());
            System.exit(1);
		}
    }
}
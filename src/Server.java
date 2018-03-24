import java.net.*;
import java.io.*;

public class Server {
	static ServerThread ST = null;

	public Server(int number,Block b) {
    	int portNumber = number;
    	Block block = b;
        boolean sending = true;
        
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) { 
            while (sending) {
	            ST = new ServerThread(serverSocket.accept(),block);
	            ST.start();
	        }
	    } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}
import java.io.*;
import java.net.*;

public class Client {

	public Block newClient(String hostName, String portNumber) {
    	try (
                Socket socket = new Socket(hostName, Integer.parseInt(portNumber));
                
            	ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            	ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ) {
            	
                Block fromServer = null;

                while (!(fromServer = (Block)in.readObject()).equals(null)) {
                	if (fromServer.data == "Close")
                        break;
                	return fromServer;
                }
            } catch (UnknownHostException e) {
                System.err.println("Don't know about host " + hostName);
                System.exit(1);
            } catch (IOException e) {
                System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
                System.exit(1);
            } catch (ClassNotFoundException e) {
            	System.err.println("Class conversion error " + e.getMessage());
                System.exit(1);
			}
    	return null;
    }
}
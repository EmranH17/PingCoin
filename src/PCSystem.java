
public class PCSystem {
	Node nodes;
	Connection<Node, Node> connections;
	Account accounts;
	private static int systemBalance = 1000000000;
	
	static public int getSystemBalance() {
		return systemBalance;
	}
}

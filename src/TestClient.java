
public class TestClient {
	public static void main(String[] args) {
		Client C = new Client();
		System.out.println(C.newClient("127.0.0.1", "50696").data);
	}
}

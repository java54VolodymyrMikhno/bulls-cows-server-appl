package telran.games;
import telran.net.*;
public class BullsCowsServerAppl {

	private static final int PORT = 5000;

	public static void main(String[] args) {
		BullsCowsService bullsCows = new BullsCowsMapImpl();
		Protocol protocol = new BullsCowsProtocol(bullsCows);
		TcpServer tcpServer = new TcpServer(protocol, PORT);
		tcpServer.run();

	}

}

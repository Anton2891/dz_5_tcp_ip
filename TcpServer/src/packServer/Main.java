package packServer;

public class Main {
    public static void main(String[] args) {

        new TcpServer(3030).startServer();
    }
}

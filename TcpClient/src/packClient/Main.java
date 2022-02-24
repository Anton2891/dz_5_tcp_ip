package packClient;

import java.io.File;

public class Main {
    public static void main(String[] args) {

        File file = new File("ffff.txt");

        new TcpClient("localhost", 3030).startClient();
    }
}

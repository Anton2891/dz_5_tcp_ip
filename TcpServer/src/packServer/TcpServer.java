package packServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {
    public int port;
    public File dir = new File("C:\\Users\\Anton\\IdeaProjects\\dz_5_tcp_ip\\TcpServer\\dir");

    public TcpServer(int port) {
        this.port = port;
    }

    public void startServer(){
        try {
            System.out.println(dir.getAbsolutePath());
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Ждём подключение клиета....");
            Socket socket = serverSocket.accept();
            System.out.println("Подключение установленно");


            DataInputStream is = new DataInputStream(socket.getInputStream());
            DataOutputStream os = new DataOutputStream(socket.getOutputStream());
            InputStream in = socket.getInputStream();

            System.out.println(dir.getAbsolutePath());

            String line;
            while (true){
            line = is.readUTF();
                if (line.equalsIgnoreCase("/fs")){
                    os.writeUTF(String.valueOf(dir));
                    os.flush();
                    System.out.println("комманда выполнена");

                } else {
                    FileOutputStream fos = new FileOutputStream(dir + "\\" + line);
                    while (is.available() > 0){
                        fos.write(is.read());
                    }
                    fos.flush();
                    fos.close();
                    System.out.println("server> : файл добавлен");
                    os.writeUTF("Файл успешно добавлен");
                    os.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

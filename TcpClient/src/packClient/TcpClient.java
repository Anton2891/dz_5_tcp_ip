package packClient;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class TcpClient {
    public String address;
    public int serverPort;
    public InetAddress inetAddress;
    public File dirOut = new File("C:\\Users\\Anton\\IdeaProjects\\dz_5_tcp_ip\\TcpClient\\dirOut");

    public TcpClient(String address, int serverPort) {
        this.address = address;
        this.serverPort = serverPort;
    }

    public void startClient(){
        try {
            inetAddress = InetAddress.getByName(address);
            Socket socket = new Socket(inetAddress, serverPort);

            DataInputStream is = new DataInputStream(socket.getInputStream());
            DataOutputStream os = new DataOutputStream(socket.getOutputStream());

            Scanner scanner = new Scanner(System.in);

            String lineOut;
            String lineIn;

            while (true){
                System.out.println("Введите имя файла, для отправки его на сервер \n или \n комманду \"/fs\" для получения списка файлов");
                lineOut = scanner.nextLine();

                if (lineOut.equalsIgnoreCase("/fs")){
                    os.writeUTF(lineOut);
                    lineIn = is.readUTF();
                    File file = new File(lineIn);
                    File [] files = file.listFiles();
                    assert files != null;
                    for (File f : files) {
                        System.out.println(f.getAbsolutePath());

                    }
                    os.flush();
                } else {
                    File file = new File(dirOut + "\\" + lineOut);
                    file.createNewFile();

                    System.out.println(file.getAbsolutePath());
                    os.writeUTF(file.getName());
                    os.flush();

                    FileInputStream fis = new FileInputStream(file);
                    byte [] bytes = new byte[1024];
                    int i;
                    while ((i = fis.read(bytes)) != -1){
                        os.write(bytes, 0, i);
                    }
                    os.flush();
                    fis.close();
                    lineIn = is.readUTF();
                    System.out.println("client> : " + lineIn);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

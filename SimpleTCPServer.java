import java.io.*;
import java.net.*;

public class SimpleTCPServer {
    public static void main(String[] args) {
        int serverPort = 8080;
        try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
            System.out.println("Server avviato sulla porta " + serverPort);
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    String requestLine ;
                    while (true) {
                        requestLine = reader.readLine();
                        if (requestLine.isEmpty()) break;
                        System.out.println(requestLine);
                    }

                    writer.println("HTTP/1.1 200 OK");
                    writer.println("Content-Type: text/plain; charset=UTF-8");
                    writer.println("Content-Length: 2");
                    writer.println();
                    writer.println("OK");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

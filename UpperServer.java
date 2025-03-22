import java.io.*;
import java.net.*;

public class UpperServer {
    public static void main(String[] args) {
        int serverPort = 8080;
        try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
            System.out.println("Server avviato sulla porta " + serverPort);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nuovo client connesso: " + clientSocket.getInetAddress().getHostAddress());
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String input;
            while ((input = reader.readLine()) != null) {
                System.out.println("Ricevuto: " + input);
                writer.println(input.toUpperCase());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Connessione chiusa con il client.");
        }
    }
}

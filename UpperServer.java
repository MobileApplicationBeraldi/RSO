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
    private static void handleClient2(Socket clientSocket) {
        try (

                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)
        )
        {

            String requestLine = reader.readLine();
            String line;
            System.out.println("Richiesta ricevuta: " + requestLine);
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            if (requestLine != null && requestLine.startsWith("GET")) {
                // Risposta HTML
                String responseHtml = "<html><body><h1>Hello da</h1><h2>SISTEMI OPERATIVI</h2></body></html>";

                // Risposta HTTP corretta
                writer.println("HTTP/1.1 200 OK");
                writer.println("Content-Type: text/HTML; charset=UTF-8");
                writer.println("Content-Length: " + responseHtml.length());
                writer.println("Connection: close");
                writer.println(); // Linea vuota per separare header e body
                writer.println(responseHtml);
                }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("CHIUDO");
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Connessione chiusa.");
        }
    }
}

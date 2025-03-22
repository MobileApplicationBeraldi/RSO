import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadServer {
    private static final int PORT = 8080;
    private static final int THREAD_POOL_SIZE = 1; // Numero massimo di thread nel pool

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server avviato sulla porta " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nuovo client connesso: " + clientSocket.getInetAddress().getHostAddress());
                executor.execute(() -> handleClient(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

            // Leggi tutte le righe della richiesta e stampale
            readAndPrintRequest(reader);

            // Rispondere con "200 OK"
            sendHttpResponse(writer);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnection(clientSocket);
        }
    }

    // Metodo per leggere e stampare tutte le righe ricevute dal client
    private static void readAndPrintRequest(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println("Ricevuto: " + line);
            // Se la riga è vuota, significa che la richiesta è terminata
            if (line.isEmpty()) {
                break;
            }
        }
    }

    // Metodo per inviare la risposta HTTP 200 OK al client
    private static void sendHttpResponse(PrintWriter writer) {
        String response = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/plain\r\n" +
                "Content-Length: 2\r\n" +
                "\r\n" +  // Linea vuota per separare header e body
                "OK";
        writer.println(response);  // Invia la risposta al client
    }

    // Metodo per chiudere la connessione con il client
    private static void closeConnection(Socket clientSocket) {
        try {
            clientSocket.close();
            System.out.println("Connessione chiusa con il client.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

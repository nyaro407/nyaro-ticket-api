import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static TicketQueue ticketQueue = new TicketQueue();
    private static final AtomicInteger ticketCounter = new AtomicInteger(1); // Pour générer des numéros uniques

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/api/tickets", new TicketHandler());
        server.setExecutor(null); // Utilise le thread par défaut
        server.start();
        System.out.println("Serveur démarré sur le port 8080");
    }

    static class TicketHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            String response = "";
            int statusCode = 200;

            try {
                if (method.equals("POST")) {
                    int ticketNumber = ticketCounter.getAndIncrement();
                    ticketQueue.enqueue(ticketNumber);
                    response = "{\"status\":\"success\",\"ticket\":" + ticketNumber + "}";
                    statusCode = 201; // Created
                } else if (method.equals("GET")) {
                    if (ticketQueue.isEmpty()) {
                        response = "{\"status\":\"error\",\"message\":\"Aucun ticket en attente\"}";
                        statusCode = 404;
                    } else {
                        response = "{\"status\":\"success\",\"nextTicket\":" + ticketQueue.peek() + "}";
                    }
                } else if (method.equals("DELETE")) {
                    if (ticketQueue.isEmpty()) {
                        response = "{\"status\":\"error\",\"message\":\"Aucun ticket à traiter\"}";
                        statusCode = 404;
                    } else {
                        int ticket = ticketQueue.dequeue();
                        response = "{\"status\":\"success\",\"ticket\":" + ticket + ",\"message\":\"Ticket traité\"}";
                    }
                } else {
                    response = "{\"status\":\"error\",\"message\":\"Méthode non supportée\"}";
                    statusCode = 405; // Method Not Allowed
                }
            } catch (Exception e) {
                response = "{\"status\":\"error\",\"message\":\"Erreur interne\"}";
                statusCode = 500;
            }

            exchange.getResponseHeaders().set("Content-Type", "application/json");
            byte[] responseBytes = response.getBytes();
            exchange.sendResponseHeaders(statusCode, responseBytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(responseBytes);
            }
        }
    }
}
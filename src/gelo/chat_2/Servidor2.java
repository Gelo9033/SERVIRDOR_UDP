package gelo.chat_2;

import java.io.*;
import java.net.*;
import java.util.*;

public class Servidor2 {
    private static final int PORT = 12345;
    private static Set<PrintWriter> clientWriters = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("Servidor en funcionamiento...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String username;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                synchronized (clientWriters) {
                    clientWriters.add(out);
                }

                // Autenticación
                out.println("Introduce tu nombre de usuario:");
                username = in.readLine();
                out.println("¡Bienvenido, " + username + "!");

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println(username + ": " + message);
                    broadcast(username + ": " + message);
                }
            } catch (IOException e) {
                System.err.println("Error en la conexión con " + username + ": " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Error al cerrar el socket: " + e.getMessage());
                }
                synchronized (clientWriters) {
                    clientWriters.remove(out);
                }
            }
        }

        private void broadcast(String message) {
            synchronized (clientWriters) {
                for (PrintWriter writer : clientWriters) {
                    writer.println(message);
                }
            }
        }
    }
}

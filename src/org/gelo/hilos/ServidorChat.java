package org.gelo.hilos;

import java.io.*;
import java.net.*;
import java.util.HashSet;
import java.util.Set;

public class ServidorChat {
    private static final int SERVER_PORT = 5000;
    private static final String AUTH_KEY = "clave123"; // Ejemplo de clave de acceso
    private static Set<String> nombresUsuarios = new HashSet<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("Servidor iniciado en el puerto " + SERVER_PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new HiloCliente(clientSocket).start();
            }
        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }

    private static class HiloCliente extends Thread {
        private Socket clientSocket;
        private PrintWriter salida;
        private BufferedReader entrada;

        public HiloCliente(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                entrada = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                salida = new PrintWriter(clientSocket.getOutputStream(), true);

                // Solicitar acceso
                String solicitud = entrada.readLine(); // Esperar solicitud de acceso
                if ("SOLICITUD_ACCESO".equals(solicitud)) {
                    salida.println("Ingrese la clave de acceso:");
                }

                // Manejar autenticación
                String clave;
                while ((clave = entrada.readLine()) != null) {
                    if (AUTH_KEY.equals(clave)) {
                        salida.println("Clave correcta. Ingrese su nombre de usuario:");
                        break; // Salir del bucle si la clave es correcta
                    } else {
                        salida.println("Clave incorrecta. Acceso denegado. Intente nuevamente:");
                    }
                }

                // Manejar el nombre de usuario
                String nombreUsuario;
                while ((nombreUsuario = entrada.readLine()) != null) {
                    if (nombresUsuarios.contains(nombreUsuario)) {
                        salida.println("Nombre en uso. Ingrese otro nombre:");
                    } else {
                        nombresUsuarios.add(nombreUsuario);
                        salida.println("Bienvenido, " + nombreUsuario + "!");
                        break; // Salir del bucle si el nombre es aceptado
                    }
                }

                // Aquí puedes agregar más lógica para manejar mensajes del chat

            } catch (IOException e) {
                System.out.println("Error en el hilo del cliente: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.out.println("Error al cerrar el socket del cliente: " + e.getMessage());
                }
            }
        }
    }
}

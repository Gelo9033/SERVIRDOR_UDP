package gelo.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {
    public static void main(String[] args) {
        ServerSocket servidor = null;
        Socket socket = null;
        DataInputStream entrada;
        DataOutputStream salida;


        final int PUERTO = 5000;

        try {
            servidor = new ServerSocket(PUERTO);
            System.out.println("Servidor Iniciado...");

            while (true) {
                socket = servidor.accept();
                System.out.println("El cliente se conecto");
                entrada = new DataInputStream(socket.getInputStream());
                salida = new DataOutputStream(socket.getOutputStream());
                String mensaje = entrada.readUTF();
                System.out.println(mensaje);
                salida.writeUTF("hola el servidor te saluda");
                socket.close();
                System.out.println("el cliente se desconecto!");
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
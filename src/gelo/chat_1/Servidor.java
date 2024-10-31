package gelo.chat_1;

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
        DataInputStream in;
        DataOutputStream out;
        final int PUERTO=5000;

        try {
            servidor=new ServerSocket(PUERTO);
            System.out.println("Servidor conectado");
            while(true){
                socket=servidor.accept();//socket de cliente
                System.out.println("cliente conectado");
                in=new DataInputStream(socket.getInputStream());
                out=new DataOutputStream(socket.getOutputStream());
                String mensaje=in.readUTF();
                System.out.println(mensaje);
                out.writeUTF("hola desde el servidor");
                socket.close();
                System.out.println("cliente desconectado");
            }

        }catch (IOException e){
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, e);
        }


    }

}

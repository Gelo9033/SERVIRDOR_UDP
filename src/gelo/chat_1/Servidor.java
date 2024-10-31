package gelo.chat_1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor extends Observable implements Runnable {

    private int puerto;
    public Servidor(int puerto) {
        this.puerto = puerto;
    }






    @Override
    public  void run() {
        ServerSocket servidor = null;
        Socket socket = null;
        DataInputStream in;
        DataOutputStream out;
        //puerto de nuestro servidor
        //final int PUERTO=5000;

        try {
            //creamos el socket del servidor
            servidor=new ServerSocket(puerto);
            System.out.println("Servidor conectado");
            //siempre estara escuchando peticiones
            while(true){
                //espero a que el cliente se conecte
                socket=servidor.accept();//socket de cliente
                System.out.println("cliente conectado");
                in=new DataInputStream(socket.getInputStream());
                //out=new DataOutputStream(socket.getOutputStream());
                String mensaje=in.readUTF();
                //System.out.println(mensaje);
                this.setChanged();
                this.notifyObservers(mensaje);
                this.clearChanged();
                //out.writeUTF("hola desde el servidor");
                socket.close();
                System.out.println("cliente desconectado");
            }

        }catch (IOException e){
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, e);
        }


    }

}

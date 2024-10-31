package gelo.chat_1;

import javax.swing.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente implements Runnable {

private int puerto;
private String mensaje;
    private JButton enviarButton;
    private JPanel panel1;
    private JTextArea textArea1;

    public Cliente(int puerto, String mensaje) {
    this.puerto = puerto;
    this.mensaje = mensaje;
}


    @Override
    public void run() {
        final String Host = "127.0.0.1";//ip de maquina
        //puerto de servidor
        //final int PUERTO=5000;
        //DataInputStream in;
        DataOutputStream out;

        try {
            Socket socket = new Socket(Host, puerto);
            //in=new DataInputStream(socket.getInputStream());
            out=new DataOutputStream(socket.getOutputStream());
            out.writeUTF(mensaje);
            //String mensaje=in.readUTF();
            //System.out.println(mensaje);
            socket.close();


            }catch (IOException e){
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, e);
        }
        }}






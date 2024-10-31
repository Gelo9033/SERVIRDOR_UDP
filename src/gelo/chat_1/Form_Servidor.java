package gelo.chat_1;

import java.util.Observable;
import java.util.Observer;

public class Form_Sevidor extends javax.swing.JFrame implements Observer {
    public Frm1(){

        this.getRootPane().setDefaultButton(this.btnEnviar);
        Servidor servidor=new Servidor(5000);
        servidor.addObserver(this);
        Thread thread=new Thread(servidor);
        thread.start();
    }



    @SuppressWarnings("unchecked")

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {


    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form_Cliente().setVisible(true);
            }
        });

    }
    private javax.swing.JButton btnEnviar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    @Override
    public void update(Observable o, Object arg) {

    }


}

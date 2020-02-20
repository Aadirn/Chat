/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import static servidor.ServidorGrafico.usuarios;

/**
 *
 * @author Castealo
 */
public class HiloServer extends Thread {

    public static ServerSocket escuchador;
    public static Socket conexion;
    private static HiloServerChat hilo;
    private static boolean finalizar = false;
    private String puerto;

    public HiloServer(String puerto) {
        this.puerto = puerto;
    }

    public HiloServer() {
    }

    @Override
    public void run() {
        conectar();
    }

    private void conectar() {

        int puertoI;

        //puerto = txtPuerto.getText();
        if (puerto.isEmpty()) {
            //System.out.println("Puerto vacio");
        } else {
            puertoI = Integer.valueOf(puerto);
            try {

                System.out.println("Entro en arrancar");
                escuchador = new ServerSocket(puertoI);

                do {

                    if (!escuchador.isClosed()) {

                        //System.out.println("Escuchador no esta cerrado");

                        conexion = escuchador.accept();

                        if (conexion.isConnected()) {

                            //System.out.println("conexion está conectada");

                            hilo = new HiloServerChat(conexion, new CommsListenerServer() {
                                @Override
                                public void llegoMsg(String msg) {
                                    PrintWriter s;
                                    //System.out.println("Ostia, me han dicho:" + msg);
                                    for (int i = 0; i < usuarios.size(); i++) {
                                        if (finalizar == true) {
                                            usuarios.get(i).getEntrada().close();
                                            usuarios.get(i).getSalida().close();
                                        } else {
                                            s = usuarios.get(i).getSalida();
                                            s.print(msg);
                                            s.flush();
                                        }

                                    }
                                }
                            });
                            usuarios.add(hilo);
                            hilo.start();

                        } else {
                            //System.err.println("No se ha podido conectar");
                        }
                    } else {
                        //System.err.println("Escuchador Cerrado");
                    }
                } while (true);

            } catch (IOException ex) {
            }
        }
    }

    public void setFinalizar() {
        try {
            //System.out.println("Salgo del bucle");
            //conexion.close();
            escuchador.close();

        } catch (IOException ex) {
            Logger.getLogger(HiloServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Castealo
 */
public class HiloServerChat extends Thread {

    private Socket conexion;
    private Scanner entrada;
    private PrintWriter salida;
    private String nickName;
    private String mensajeCompleto;
    private String mensajeEntrada;
    private String mensajeSalida;
    private String mensajeEnvio;
    private boolean salir = true;
    private CommsListenerServer listener;

    public HiloServerChat(Socket conexion, CommsListenerServer commsListener) {
        this.listener = commsListener;
        this.conexion = conexion;
    }

    @Override
    public void run() {
        do {
            try {
                entrada = new Scanner(conexion.getInputStream());
                salida = new PrintWriter(conexion.getOutputStream());

                //System.out.println("Antes de entrar al metodo captar mensaje");
                salida.flush();

                captarMensaje();
            } catch (IOException ex) {
                System.err.println("Fallo al obtener puntos de E/S: " + ex.getMessage());
            }

        } while (salir);
        //System.out.println("Cierro cositas");
        entrada.close();
        salida.close();
        try {
            conexion.close();
        } catch (IOException ex) {
            System.err.println("Error al cerrar la conexion");
        }
    }

    private void captarMensaje() {

        //System.out.println("Entro en captar mensaje");
        String[] trozo = new String[2];
        String accion;
        //System.out.println("antes de nexline");
        if (conexion.isConnected()) {
            mensajeCompleto = entrada.nextLine();
            //System.out.println(mensajeCompleto);

            //Tendria que llegarme algo tipo = Accion#Nickname#mensaje (si hay mensaje)
            //Siendo accion: conectar, salir o el mensaje
            trozo = mensajeCompleto.split("#");
            accion = trozo[0];
            switch (accion) {
                case "conectar":
                    conectar(trozo[1]);
                    break;
                case "salir":
                    salir(trozo[1]);
                    break;
                case "mensaje":
                    mensaje(trozo[1], trozo[2]);
                    break;
                default:
                    break;
            }
        } else {

        }
    }

    private void conectar(String nick) {

        nickName = nick;
        mensajeEntrada = "El usuario " + nick + " acaba de entrar al chat. Bienvenido.\r\n";
        if (listener != null) {
            listener.llegoMsg(mensajeEntrada);
        }

        //salida.print(mensajeEntrada);
        //salida.flush();
    }

    private void salir(String nick) {
        nickName = nick;
        mensajeSalida = "El usuario " + nick + " acaba de salir. Bye bye.\r\n";
        if (listener != null) {
            listener.salidaMsg(mensajeSalida, this);
        }

        salir = false;
    }

    private void mensaje(String nick, String msg) {
        nickName = nick;
        mensajeEnvio = nick + ": " + msg + "\r\n";
        if (listener != null) {
            listener.llegoMsg(mensajeEnvio);
        }
    }

    public String getNickName() {
        return nickName;
    }

    public String getMensajeEntrada() {
        return mensajeEntrada;
    }

    public String getMensajeSalida() {
        return mensajeSalida;
    }

    public String getMensajeEnvio() {
        return mensajeEnvio;
    }

    public Scanner getEntrada() {
        return entrada;
    }

    public PrintWriter getSalida() {
        return salida;
    }

}

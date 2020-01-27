/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Castealo
 */
public class HiloClienteChatBienvenida extends Thread {

    private ServerSocket escuchador;
    private JTextField txtNick;
    private Socket conexion;
    private Scanner entrada;
    private PrintWriter salida;
    private int puerto;
    private boolean salir = true;
    private String nickname;

    public HiloClienteChatBienvenida(int puerto) {
        this.puerto = puerto;
    }

    @Override
    public void run() {
        try {
            conexion = new Socket("localhost", puerto);
        } catch (IOException ex) {
            Logger.getLogger(HiloClienteChatBienvenida.class.getName()).log(Level.SEVERE, null, ex);
        }
        do {
            try {
                if (!conexion.isConnected()) {
                    System.err.println("conexion cerrada");
                    return;
                }

                entrada = new Scanner(conexion.getInputStream());
                salida = new PrintWriter(conexion.getOutputStream());

            } catch (IOException ex) {
                System.err.println("Fallo al obtener los puntos de E/S");
            }
        } while (salir);
    }

    public Scanner getEntrada() {
        return entrada;
    }

    public void setEntrada(Scanner entrada) {
        this.entrada = entrada;
    }

    public PrintWriter getSalida() {
        return salida;
    }

    public void setSalida(PrintWriter salida) {
        this.salida = salida;
    }

    public boolean isSalir() {
        return salir;
    }

    public void setSalir(boolean salir) {
        this.salir = salir;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.swing.JTextArea;

/**
 *
 * @author Castealo
 */
public class HiloClienteConectar extends Thread {

    private static final HiloClienteConectar instancia = new HiloClienteConectar();

    private HiloClienteConectar() {
    }

    public static HiloClienteConectar init() {
        return instancia;
    }

    public static HiloClienteConectar init(Scanner entrada, JTextArea txtArea, ArrayList<String> nicks) {
        instancia.entrada = entrada;
        instancia.nicks = nicks;
        instancia.txtArea = txtArea;
        return instancia;
    }

    public static ServerSocket escuchador;
    public static Socket conexion;
    private static boolean finalizar = false;
    private Scanner entrada;
    private ArrayList<String> nicks;
    private JTextArea txtArea;

    @Override
    public void run() {
        do {
            try {
                String algo = entrada.nextLine();
                txtArea.append("\n" + algo);
            } catch (NoSuchElementException ex) {
            }

        } while (!finalizar);
    }

    public void setFinalizar(boolean finalizar) {
        this.finalizar = finalizar;
    }

}

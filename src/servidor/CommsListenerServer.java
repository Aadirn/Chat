/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

/**
 *
 * @author Castealo
 */
public interface CommsListenerServer {
    
    void llegoMsg(String msg);

    void salidaMsg(String mensajeSalida, HiloServerChat hilo);
    
}

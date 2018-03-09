/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloexame2av;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author xavi
 */
public class TicketConsumer {

    /** Procesa o Ticket engadindo o ticket a HistoricoVentas.dat e actualizando 
     * Ventas.dat
     * EXERCICIO b) 2.
     * @param t
     * @throws java.io.IOException
    */
    public void procesaTicket(Ticket t) throws IOException {
        rexistraExistencias(t);
        rexistraTicket(t);
    }
    
    /** Método privado que engade o Ticket a HistoricoVentas.dat
     *  O nome do método é pouco afortunado, debería chamarse por exemplo
     * actualizaHistorico
    */
    private void rexistraExistencias(Ticket t) throws FileNotFoundException, IOException {
        // Try with resources
        //
        try (ObjectOutputStream dos = MyObjectOutputStream.getInstance("/home/xavi/HistoricoVentas.dat")) {
            dos.writeObject(t);
        }
        /*ObjectOutputStream dos=null;
        
        try {
            dos=MyObjectOutputStream.getInstance("/home/xavi/HistoricoVentas.dat");
            dos.writeObject(t);
        } finally {
            if (dos!=null) dos.close();
        }*/
    }

    /** Método privado que actualiza Ventas.dat a partir dos datos do Ticket
     * 
     * EXERCICIO b) 3
     * 
     * O nome do método é pouco afortunado, debería chamarse rexistraVenta
    */
    private void rexistraTicket(Ticket t) throws FileNotFoundException, IOException {
        // Collemos os productos do ticket
        // Mentras Teñamos Productos
        // - Buscar a "Venta"
        // - incrementamos o número de ventas e actualizar importe total
        // - Garda
        // FIN
        ArrayList <Producto> lista;
        Venta v;
        int ventas;
        float total;
        
        try {
            Venta.start();  // Para abrir o ficheiro
            lista=t.getProducts();
            for(Producto p: lista) {
                // Nos situadomos no producto, xunto en posicion de modificación
                Venta.rexistra(p);
            }
        } finally {
            Venta.end(); // Para que Venta peche o ficheiro
        }
    }
    
}

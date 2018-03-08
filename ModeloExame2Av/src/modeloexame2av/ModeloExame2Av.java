/*
 * EXERCICIO a).- HistoricoVentas.dat será un ObjectOutputStream, xa que únicamente
 * teño que ir engadindo Tickets. Teño que ter coidado con que os Ticket
 * sexan serializables e coas cabeceiras que escribe ObjectOutputStream ao
 * abrir para engadir
 *
 * Ventas.dat será un RandomAccessFile, xa que necesito modificar o ficheiro
 * continuamente. Alternativamente, podería utilizar un Stream e facer o 
 * tratamento en memoria cunha Collection, pero presenta risco de perdas de datos.
 *
 * Si usara unha collection, podería utilizar un HashMap <int,Producto> onde a
 * chave é o código de producto, para localizalos rápidamente.
*/
package modeloexame2av;

/**
 *
 * @author xavi
 */
public class ModeloExame2Av {

    /**
     * EXERCICIO b) 1.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Server srv=new Server();
        TicketConsumer consumer=new TicketConsumer();
        srv.setListener(consumer);
        srv.run();
    }
    
}

/*
 * Esta clase representa unha venta de productos por caixa (Ticket)
 */
package caja;

import java.io.IOException;

/**
 *
 * @author xavi
 */
class Ticket  {
    float total=0;      // Total acumulado no ticket
    String ticket="";   // Representación do ticket ata o momento
    private DataAccess data=DataAccess.getInstance();   // Acceso aos datos para gardar as ventas

    public void addVenta(Venta v) throws IOException, NotExistsException {
        // Ao coller o importe se forza a carga do producto e a verificación da súa existencia1
        total+=v.getImporte();  // Sumamos o precio do producto ao total
        data.saveVenta(v);      // Gardamos a venta
        ticket+=v+"\n";         // Modificamos a representación do ticket
    }

    /**
     * Devolve o importe do ticket
     * @return float: importe do Ticket
     */
    public float getImporte() {
        return total;
    }
   
    /**
     * Representación do Ticket
     * @return 
     */
    public String toString() {
        return ticket;
    }

    /**
     * Finaliza o Ticket
     */
    void end() {
        ticket+="\n\nTOTAL: "+total+"\n";
    }
}

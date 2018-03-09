/*
 * Esta clase representa unha venta de productos por caixa (Ticket)
 */
package caja;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author xavi
 */
class Ticket  {
    private Calendar f;         // Data da venta
    private float total=0;      // Total acumulado no ticket
    private ArrayList <Producto> lista;
    
    public Ticket() {
        lista=new ArrayList <> ();
        f=GregorianCalendar.getInstance();
    }

    public ArrayList <Producto> getProductos() {
        return lista;
    }
    
    public void addProducto(Producto p) throws IOException, NotExistsException {
        lista.add(p);
        total+=p.getPVP();
    }
    
    public Calendar getCalendar() {
        return f;
    }

    /**
     * Devolve o importe do ticket
     * @return float: importe do Ticket
     */
    public float getImporte() {
        return total;
    }
    
    public void save() throws IOException, ClassNotFoundException {
        DataAccess data=DataAccess.getInstance();
        data.saveTicket(this);
    }
    /**
     * Representación do Ticket
     * @return 
     */
    @Override
    public String toString() {
        StringBuilder str=new StringBuilder("\n\nTicket de Caixa: ");
        SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = formatDate.format(f.getTime());
        str.append(strDate).append("\n=============================\n");
        for(Producto p: lista) {
            str.append(p).append("\n");
        }
        str.append("\n").append("TOTAL: ").append(total).append("\n\n");
        return str.toString();
    }
}

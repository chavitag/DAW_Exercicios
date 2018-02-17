/*
 * Esta clase representa unha Liña de Venta no Ticket
 */
package caja;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author xavi
 */
public class Venta implements Serializable {
    private int code;           // Codigo do producto
    private Producto pr=null;   // Producto (Non o cargamos ata que non é necesario)
    private Calendar f;         // Data da venta
    
    /**
     * Constructor a partir dunha data e do código.
     * Serve para recuperar ventas xa realizadas anteriormente
     * 
     * @param dia
     * @param mes
     * @param ano
     * @param code 
     */
    public Venta(int dia,int mes,int ano,int code) {
        f=new GregorianCalendar(ano,mes,dia);
        this.code=code;
    }
    
    /**
     * Constructor a partir do código do producto
     * 
     * @param code 
     */
    public Venta(int code)  {
        this.code=code;
        f=GregorianCalendar.getInstance();
    }
    
    /**
     * Devolve a data da venta
     * @return Calendar
     */
    public Calendar getDate() {
        return f;
    }
    
    /**
     * Devolve o código do producto vendido
     * @return 
     */
    public int getCode() {
        return code;
    }
    
    /**
     * Devolve a representación en String da venta
     * OLLO: Si o Producto non está "cargado" a representación so incluirá a data...
     * @return 
     */
    @Override
    public String toString() {
        SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
        String formatted = formatDate.format(f.getTime());
        return formatted+": "+pr;
    }
    
    /**
     * Carga o Producto representado no atributo code si non está xa cargado
     * E de uso interno para os métodos da clase
     * 
     * @return
     * @throws IOException
     * @throws NotExistsException 
     */
    private Producto loadProducto() throws IOException, NotExistsException {
        if (pr==null) {
            DataAccess data=DataAccess.getInstance();    
            pr=data.getProducto(code);
        }
        return pr;
    }

    /**
     * Devolve o producto vendido, cargándoo si é necesario
     * 
     * @return
     * @throws IOException
     * @throws NotExistsException 
     */
    Producto getProducto() throws IOException, NotExistsException {
        return loadProducto();
    }
    
    /**
     *  Devolve o importe do producto vendido 
     */
    float getImporte() throws IOException, NotExistsException {
        return loadProducto().getPVP();
    }
}

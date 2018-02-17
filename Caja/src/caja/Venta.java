/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    private int code;
    private Producto pr=null;
    private Calendar f;
    
    public Venta(int dia,int mes,int ano,int code) {
        f=new GregorianCalendar(ano,mes,dia);
        this.code=code;
    }
    
    public Venta(int code) throws IOException, NotExistsException {
        this.code=code;
        f=GregorianCalendar.getInstance();
    }
    
    public Calendar getDate() {
        return f;
    }
    
    public int getCode() {
        return code;
    }
    
    @Override
    public String toString() {
        SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
        String formatted = formatDate.format(f.getTime());
        return formatted+": "+pr;
    }
    
    private Producto loadProducto() throws IOException, NotExistsException {
        if (pr==null) {
            DataAccess data=DataAccess.getInstance();    
            pr=data.getProducto(code);
        }
        return pr;
    }

    Producto getProducto() throws IOException, NotExistsException {
        return loadProducto();
    }
    
    float getImporte() throws IOException, NotExistsException {
        return loadProducto().getPVP();
    }
}

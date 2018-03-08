/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloexame2av;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author xavi
 */
class Ticket  implements Serializable {
    private ArrayList <Producto> pr;
    private Calendar c;
    private int numCaixa;
    
    public ArrayList <Producto> getProducts() {
        return pr;
    }
    
    public Calendar getCalendar() {
        return c;
    }
    
    public int getCaixa() {
        return numCaixa;
    }
    
    /**
     * EXERCICIO b) 5
     * @return 
     */
    public  float getImporte() {
        float total=0;
        for(Producto p: pr) {
            total+=p.getPrecio();
        }
        return total;
    }
    
}

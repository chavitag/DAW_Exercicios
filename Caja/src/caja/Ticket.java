/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caja;

import java.io.IOException;

/**
 *
 * @author xavi
 */
class Ticket  {
    float total=0;
    String ticket="";
    private DataAccess data=DataAccess.getInstance();

    public void addVenta(Venta v) throws IOException, NotExistsException {
        // Ao coller o importe se forza a carga do producto e a verificación da súa existencia1
        total+=v.getImporte(); 
        data.saveVenta(v);
        ticket+=v+"\n";
    }

   public float getImporte() {
        return total;
   }
   
   public String toString() {
      return ticket;
   }

    void end() {
        ticket+="\n\nTOTAL: "+total+"\n";
    }
}

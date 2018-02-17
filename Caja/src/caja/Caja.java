/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caja;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author xavi
 */
public class Caja {
    private final Scanner scn=new Scanner(System.in);
    private final DataAccess data=DataAccess.getInstance();
    
    private enum Opts {
        VENTA,
        CAJA,
        HISTORICO,
        SALIR;
    };
    
    private Opts menu() {
        System.out.println("Menú Principal");
        System.out.println("==============");
        System.out.println("1.- Venta");
        System.out.println("2.- Caja");
        System.out.println("3.- Historico");
        System.out.println("4.- Salir");
        return Opts.values()[Integer.parseInt(scn.nextLine())-1];
    }
    
    private void runApplication()  {
        Opts op=null;
   
        do {
            try {
                op=menu();
                switch(op) {
                    case VENTA:
                        doVenta();
                        break;
                    case HISTORICO:
                        data.listaHistorico();
                        break;
                    case CAJA:
                        doCaja();
                        break;
                }
            } catch (IOException e) {
                System.out.println("ERROR: "+e.getMessage());
            } catch (NotExistsException ex) {
                System.out.println("O Producto non existe...");
            }
        } while (op!=Opts.SALIR);
    }
        
    private void doVenta() {
        Ticket tk;
        Venta v;
        int code=0;
        
        System.out.println("Benvido! Gracias pola súa compra.");
        tk=new Ticket();
        do {
            try {
                System.out.println("Código Producto: ");
                String l=scn.nextLine();
                code=Integer.parseInt(l);
                if (code>=0) {
                    v=new Venta(code);
                    tk.addVenta(v);
                    System.out.println(tk);
                }
            } catch (NotExistsException e) {
                System.out.println("Producto Erróneo");
            } catch(Exception e) {
                System.out.println("ERROR: "+e.getMessage());
            }
        } while(code!=-1);
        tk.end();
        System.out.println(tk);
        System.out.println("Gracias pola sua visita. Pulsa Enter para Seguir");
        scn.nextLine();
    }
    
    private void doCaja() throws IOException {
        Venta v;
        int idx=0;
        
        System.out.println("Procesando Caja..... un momento");
        while((v=data.getVenta())!=null) {
            System.out.println("Incrementando ventas do código "+v.getCode());
            data.updateHistorico(v);  // Actualizamos o histórico de ventas co producto
        }
        System.out.println("OK");
    }
        
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new Caja().runApplication();
    }
    
}

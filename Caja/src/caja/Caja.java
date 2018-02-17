/**
 * Clase Principal.
 * 
 * O main de esta clase é o principal da aplicación.
 * 
 */
package caja;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase Principal
 * @author xavi
 */
public class Caja {
    private final Scanner scn=new Scanner(System.in);           // Entrada por teclado
    private final DataAccess data=DataAccess.getInstance();     // Acceso a datos
    
    private enum Opts {
        VENTA,
        CAJA,
        HISTORICO,
        SALIR;
    };
    
    /**
     * Menú principal
     * 
     * @return Opción elexida
     */
    private Opts menu() {
        System.out.println("Menú Principal");
        System.out.println("==============");
        System.out.println("1.- Venta");
        System.out.println("2.- Caja");
        System.out.println("3.- Historico");
        System.out.println("4.- Salir");
        return Opts.values()[Integer.parseInt(scn.nextLine())-1];
    }
    
    /**
     * Arranque da aplicación. 
     * Según a opción elexida, leva a cabo a acción
     */
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
                // Erro no acceso ao ficheiro ou o teclado
                System.out.println("ERROR: "+e.getMessage());
            } catch (NotExistsException ex) {
                // O producto non se atopa. Non debe ocurrir, xa
                // que so pode pasar listando o Histórico....
                System.out.println("INCONSISTENCIA NO HISTÓRICO: O Producto non existe...");
            }
        } while (op!=Opts.SALIR);
    }
    
    /**
     * Proceso de venta (ticket de caixa)
     */   
    private void doVenta() {
        Ticket tk;
        Venta v;
        int code=0;
        
        System.out.println("Benvido! Gracias pola súa compra.");
        tk=new Ticket();    // Creamos NOVO ticket
        do {
            try {
                System.out.println("Código Producto (-1 saír): ");
                String l=scn.nextLine();
                code=Integer.parseInt(l);
                if (code>=0) {
                    v=new Venta(code);  // Creamos Venta
                    tk.addVenta(v);     // A engadimos ao Ticket
                    System.out.println(tk);  // Amosamos o estado parcial do ticket
                }
            } catch (NotExistsException e) {
                System.out.println("Producto Erróneo"); 
            } catch(Exception e) {
                System.out.println("ERROR: "+e.getMessage()); // Outros erros..
            }
        } while(code!=-1);
        tk.end(); // Rematamos o TICKET
        System.out.println(tk); // Visualizamos o resultado final
        System.out.println("Gracias pola sua visita. Pulsa Enter para Seguir");
        scn.nextLine();
    }
    
    /**
     * Procesa as ventas do día incorporándoas ao histórico
     * @throws IOException 
     */
    private void doCaja() throws IOException {
        Venta v;
        int idx=0;
        
        System.out.println("Procesando Caja..... un momento");
        while((v=data.getVenta())!=null) { // Procesamos cada venta no histórico
            System.out.println("Incrementando ventas do código "+v.getCode());
            data.updateHistorico(v);  // Actualizamos o histórico de ventas co producto
        }
        System.out.println("OK");
    }
        
    /**
     * Programa Principal
     * @param non se usan
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new Caja().runApplication();
    }
    
}

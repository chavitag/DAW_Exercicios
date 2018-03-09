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
    private Scanner scn=null;           // Entrada por teclado
    private DataAccess data=null;       // Acceso a datos

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
        
        try {
            data=DataAccess.getInstance();
            scn=new Scanner(System.in);
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
                } catch (NumberFormatException e) {
                    op=null;
                } catch (IOException e) {
                    // Erro no acceso ao ficheiro ou o teclado
                    System.out.println("ERROR: "+e.getMessage());
                } catch (NotExistsException ex) {
                    System.out.println("O Producto non existe...");
                }
            } while (op!=Opts.SALIR);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Caja.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    /**
     * Proceso de venta (ticket de caixa)
     */   
    private void doVenta() throws IOException, ClassNotFoundException {
        Ticket tk;
        Producto p;
        int code=0;
        
        System.out.println("Benvido! Gracias pola súa compra.");
        tk=new Ticket();    // Creamos NOVO ticket
        do {
            try {
                System.out.println("Código Producto (-1 saír): ");
                String l=scn.nextLine();
                code=Integer.parseInt(l);
                if (code>=0) {
                    p=Producto.getInstance(code);  // Creamos Venta
                    tk.addProducto(p);     // A engadimos ao Ticket
                    System.out.println(p);
                }
            } catch (NotExistsException e) {
                System.out.println("Producto Erróneo"); 
            } catch(NumberFormatException | IOException e) {
                System.out.println("ERROR: "+e.getMessage()); // Outros erros..
            }
        } while(code!=-1);
        System.out.println(tk); // Visualizamos o resultado final
        tk.save();
        System.out.println("Gracias pola sua visita. Pulsa Enter para Seguir");
        scn.nextLine();
    }
    
    /**
     * Procesa as ventas do día incorporándoas ao histórico
     * @throws IOException 
     */
    private void doCaja() throws IOException,FileNotFoundException, NotExistsException, ClassNotFoundException {
        System.out.println("Procesando Caja..... un momento");
        data.updateHistorico();
        System.out.println("OK");
    }
 
    /**
     * Programa Principal
     * @param args
     */
    public static void main(String[] args) {
        Caja c=new Caja();
        c.runApplication();
    }
    
}

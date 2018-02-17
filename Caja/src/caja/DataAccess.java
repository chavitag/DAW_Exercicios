/*
 * Esta clase proporciona o acceso real aos datos, de xeito que para cambiar o
 * modo en que se almacenan e recuperan, únicamente é necesario escribir unha nova
 * versión de esta clase sen tocar o resto do programa.
 * 
 * A implementación actual intenta non facer uso das estructuras de almacenamento
 * que se explican na unidade 8, e utiliza STREAMS polo que o modo de almacenar 
 * e recuperar datos é moi pouco eficiente. Respecto a primeira versión 
 * sustituiuse o uso de STREAMS por RandomAccessFile
 */
package caja;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author xavi
 */
public class DataAccess {
    // Ficheiros que se utilizarán
    private final String path="/home/xavi/";
    private final String fventas="ventas.dat";
    private final String fproductos="productos.dat";
    private final String fhistorico="historico.dat";
    
    // So queremos un so obxecto DataAccess para a aplicación, esto se coñece
    // como "Patrón Singleton", e se fai facendo privado o constructor da clase
    // e facilitando un método para recuperar ese obxecto único.
    private static DataAccess da=null;  
    
    /**
     * Constructor
     * 
     * O Implementamos para facelo privado
     */
    private DataAccess() {   }
    
    /**
     * Permite obter o obxecto DataAccess para acceder aos datos. 
     * Si non existe, se crea.
     * @return 
     */
    public static DataAccess getInstance() {
        if (da==null) da=new DataAccess();
        return da;
    }
        
    /**
     * Garda unha venta no ficheiro de ventas.
     * @param v
     * @throws IOException 
     */
    public void saveVenta(Venta v) throws IOException {

    }

    /** 
     * Se utiliza para leer ventas secuencialmente ata chegar ao final.
     * 
     * Si o ficheiro non está aberto, o abre e lee deixandoo aberto
     * si xa está aberto, simplemente lee si ten datos devolvendo a venta
     * leída. Si non quedan datos, pecha o ficheiro e devolve unha venta nula
     * 
     * @return 
     * @throws java.io.IOException 
     */
    public Venta getVenta() throws IOException {
 
    }

    /**
     * Visualiza o contido do histórico
     * 
     * @throws FileNotFoundException
     * @throws IOException
     * @throws NotExistsException 
     */
    void listaHistorico() throws FileNotFoundException, IOException, NotExistsException {

    }
    
    /**
     * Actualiza o historico incorporando a venta indicada
     * 
     * 
     * Esto é moi pouco eficiente, pero si non queremos usar Collections
     * (ArrayList e demais) é a única solución
     * 
     * @param v : Venta a incorporar ao histórico
     */
    void updateHistorico(Venta v) throws IOException {

    }
    
    /**
     * Recupera do ficheiro os datos do producto indicado por code
     * e devolve o Producto correspondente.
     * 
     * @param code
     * @return obxecto Producto obtido
     * @throws IOException
     * @throws NotExistsException 
     */
    Producto getProducto(int code) throws IOException, NotExistsException {
    
    }

    /**
     * Garda o Producto indicado no ficheiro de productos
     * 
     * @param pr
     * @throws IOException 
     */
    void saveProducto(Producto pr) throws IOException {
     
    }
    
    /**
     * Lee un producto do ficheiro de productos. 
     * 
     * É unha función auxiliar
     * 
     * @param dis
     * @return
     * @throws IOException 
     */
    private Producto readProducto(DataInputStream dis) throws IOException {
    
    }
    
    /**
     * Garda un Producto no ficheiro de Productos.
     * 
     * É unha funcion auxiliar.
     * 
     * @param dos
     * @param pr
     * @throws IOException 
     */
    private void writeProducto(DataOutputStream dos, Producto pr) throws IOException {
     
    }
    
    /**
     * Lee unha venta do ficheiro de ventas.
     * 
     * É unha función auxiliar
     * 
     * @param dis
     * @return
     * @throws IOException 
     */
    private Venta readVenta(DataInputStream dis) throws IOException {
    
    }
    
    /**
     * Escribe unha venta no ficheiro de ventas.
     * 
     * É unha función auxiliar
     * 
     * @param dos
     * @param v
     * @throws IOException 
     */
    private void writeVenta(DataOutputStream dos, Venta v) throws IOException {
    
    }
    
    
    /**
     * Este programa serve para crear o ficheiro de productos.
     * 
     * Dende Netbeans debedes indicar que execute este ficheiro
     * 
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        Scanner scn=new Scanner(System.in);
        int code;
        String name;
        float pvp;
        Producto p;
        
        DataAccess da=DataAccess.getInstance();
        do {
            System.out.println("Codigo (-1 finalizar):");
            code=Integer.parseInt(scn.nextLine());
            if (code>=0) {
                System.out.println("Nombre:");
                name=scn.nextLine();
                System.out.println("PVP:");
                pvp=Float.parseFloat(scn.nextLine());
                p=new Producto(code,name,pvp);
                da.saveProducto(p);
            }
        } while (code>=0);
    }
}

/*
 * Esta clase proporciona o acceso real aos datos, de xeito que para cambiar o
 * modo en que se almacenan e recuperan, únicamente é necesario escribir unha nova
 * versión de esta clase sen tocar o resto do programa.
 * 
 * A implementación actual intenta non facer uso das estructuras de almacenamento
 * que se explican na unidade 8, e utiliza STREAMS polo que o modo de almacenar 
 * e recuperar datos é moi pouco eficiente. Respecto a primeira versión 
 * sustituiuse o uso de STREAMS por RandomAccessFile para actualizar o Histórico
 * o que resulta moito máis eficiente.
 */
package caja;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author xavi
 */
public class DataAccess {
    // Ficheiros que se utilizarán
    private static final String path="/home/xavi/";
    private static final String fventas="ventas.dat";
    private static final String fproductos="productos.dat";
    private static final String fhistorico="historico.dat";
    
    // So queremos un so obxecto DataAccess para a aplicación, esto se coñece
    // como "Patrón Singleton", e se fai facendo privado o constructor da clase
    // e facilitando un método para recuperar ese obxecto único.
    private static DataAccess da=null;  
    private HashMap <Integer,Producto> productos=null;
    
    /**
     * Constructor
     * 
     * O Implementamos para facelo privado e cargar os productos no HashMap
     */
    private DataAccess() throws IOException, ClassNotFoundException { 
        ObjectInputStream frProducto=null;
        Producto p;
        productos=new HashMap <>();
        try {
            frProducto=new ObjectInputStream(new FileInputStream(path+fproductos));
            while(true) {
                p=(Producto) frProducto.readObject();
                productos.put(p.getCode(),p);
            }
        } catch(EOFException e) {
        } catch(FileNotFoundException e) {
            // Non temos productos... non poderei facer ventas...
            // Aviso..
            System.out.println("Non dispoñemos de productos na base datos...");
        } finally {
            if (frProducto!=null) frProducto.close();
        }
    }
    
    /**
     * Permite obter o obxecto DataAccess para acceder aos datos. 
     * Si non existe, se crea.
     * @return 
     * @throws java.io.IOException 
     * @throws java.lang.ClassNotFoundException 
     */
    public static DataAccess getInstance() throws IOException, ClassNotFoundException {
        if (da==null) da=new DataAccess();
        return da;
    }
    
    /**
     * Recupera un producto a partir dun código. 
     * E inmediato, porque o temos nun HashMap
     * 
     * @param code
     * @return obxecto Producto obtido
     * @throws caja.NotExistsException
     */
    public Producto getProducto(int code) throws NotExistsException  {
        Producto p=productos.get(code);
        if (p==null) throw new NotExistsException();
        return p;
    }
    
    /**
     * Garda o Ticket en ventas.dat
     * @param aThis
     * @throws FileNotFoundException
     * @throws IOException 
     */
    void saveTicket(Ticket aThis) throws FileNotFoundException, IOException {
        DataOutputStream fwVenta=null;
        ArrayList <Producto> list=aThis.getProductos();
        Calendar c;
        
        try {
            fwVenta=new DataOutputStream(new FileOutputStream(path+fventas,true));
        
            // Recorremos os prodcutos do ticket e gardamos a venta
            c=aThis.getCalendar();
            for(Producto p: list) {
                writeVenta(c,p,fwVenta);
            }
        } finally {
            if (fwVenta!=null) fwVenta.close();
        }
    }
    
    /**
     * Actualiza o histórico a partir de ventas.dat
     */
    void updateHistorico() throws  IOException, FileNotFoundException, NotExistsException, ClassNotFoundException {
        ArrayList <Producto> ventas=readVentas();
        
        //TODO: Aquí poderíamos facer un backup do ficheiro e rodear todo esto nun try catch
        for(Producto p:ventas) {
            System.out.println("Procesando "+p);
            incrementaVenta(p.getCode());
        }
        // Procesado, o borramos.
        new File(path+fventas).delete();
        //TODO: Aqui no catch poderíamos restaurar o backup do ficheiro, o ficheiro de ventas non se borrou...
    }
         
    /**
     * Visualiza o contido do histórico
     * 
     * @throws FileNotFoundException
     * @throws IOException
     * @throws NotExistsException 
     */
    void listaHistorico() throws FileNotFoundException, IOException, NotExistsException {
        RandomAccessFile frwhistorico=null;
        int code,num;
        try {
            frwhistorico=new RandomAccessFile(path+fhistorico,"rw");
        
            while(true) {
                code=frwhistorico.readInt(); 
                num=frwhistorico.readInt(); 
                System.out.println(getProducto(code)+" ("+num+" unidades)");
            }
        } catch(EOFException ex) {
        } finally {
            if (frwhistorico!=null) frwhistorico.close();
        }
    }
    
   
    //  ============  Métodos Privados
         
  
    /**
     * Lee todo o ficheiro de ventas (ventas do día) e nos devolve un ArrayList con todas as ventas
     * 
     * @return
     * @throws FileNotFoundException
     * @throws OpenFileException
     * @throws IOException
     * @throws NotExistsException
     * @throws ClassNotFoundException 
     */
    private ArrayList <Producto> readVentas() 
            throws FileNotFoundException, IOException, NotExistsException, ClassNotFoundException {
        DataInputStream frVenta=null;
        int code;
        ArrayList <Producto> lp=new ArrayList <>();
        
        try {
            frVenta=new DataInputStream(new FileInputStream(path+fventas));
            while (frVenta.available()>0) {
                // Saltamos a data, que non interesa
                // Dia
                frVenta.readInt();
                //Mes
                frVenta.readInt();
                //Ano
                frVenta.readInt();
                      
                // Lemos o código e creamos o Producto. Non costa nada porque o collemos do HashMap...
                code=frVenta.readInt();
                lp.add(Producto.getInstance(code));
            }
        } finally {
            if (frVenta!=null) frVenta.close();
        }
        return lp;
    }
    
    /**
     * Escribe unha venta no ficheiro de ventas.
     * 
     * É unha función auxiliar
     * 
     * @param c - Data da venta
     * @param p - Producto vendido
     * @throws IOException 
     */
    private void writeVenta(Calendar c,Producto p,DataOutputStream fwVenta) throws IOException {
        int code=p.getCode();
        int dia,mes,ano;
        
        dia=c.get(Calendar.DAY_OF_MONTH);
        mes=c.get(Calendar.MONTH)+1;
        ano=c.get(Calendar.YEAR);
        // Gardamos data+codigo
        fwVenta.writeInt(dia);
        fwVenta.writeInt(mes);
        fwVenta.writeInt(ano);
        fwVenta.writeInt(code);
    }
    
    /**
     * incrementa o número de unidades vendidas do producto s_code
     * @param s_code 
     */
    private void incrementaVenta(int s_code) throws FileNotFoundException, IOException {
        RandomAccessFile frwhistorico=null;
        int code,num;
        try {
            frwhistorico=new RandomAccessFile(path+fhistorico,"rw");
            while(true) {
                code=frwhistorico.readInt(); 
                num=frwhistorico.readInt();
               
                if (code==s_code) {
                    // Nos posicionamos encima de num
                    frwhistorico.seek(frwhistorico.getFilePointer()-(Integer.SIZE/8));
                    frwhistorico.writeInt((num+1));
                    break;
                }
            }
        } catch(EOFException ex) {
            // Non existe o producto, o engadimos ao histórico
            frwhistorico.writeInt(s_code);
            frwhistorico.writeInt(1);
        } finally {
            if (frwhistorico!=null) frwhistorico.close(); 
        }
    }
  
    
    /**
     * Auxiliar para engadir novos productos co main de esta clase
     * @param p 
     */
    private void addProducto(Producto p) {
        productos.put(p.getCode(),p);
    }
    
    /**
     * Salva os productos ao ficheiro productos.dat
     */
    private void saveProductList() throws IOException {
        // Try-With Resources https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path+fproductos))) {
            for(Map.Entry<Integer,Producto> pr: productos.entrySet() ) {
                oos.writeObject(pr.getValue());
            }
        }
        /*
        ObjectOutputStream oos=null;
        try {
            oos=new ObjectOutputStream(new FileOutputStream(path+fproductos));
            for(Map.Entry<Integer,Producto> pr: productos.entrySet() ) {
                oos.writeObject(pr.getValue());
            }
        } finally {
            if (oos!=null) oos.close();
        }
        */
    }
    
    
    /**
     * Este programa serve para crear o ficheiro de productos.
     * 
     * Dende Netbeans debedes indicar que execute este ficheiro
     * 
     * @param args
     * @throws IOException 
     * @throws java.lang.ClassNotFoundException 
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner scn=new Scanner(System.in);
        int code;
        String name;
        float pvp;
        Producto p;

        DataAccess.getInstance();
        do {
            System.out.println("Codigo (-1 finalizar):");
            code=Integer.parseInt(scn.nextLine());
            if (code>=0) {
                System.out.println("Nombre:");
                name=scn.nextLine();
                System.out.println("PVP:");
                pvp=Float.parseFloat(scn.nextLine());
                p=new Producto(code,name,pvp);
                da.addProducto(p);
            }
        } while (code>=0);
        da.saveProductList();
    }
}

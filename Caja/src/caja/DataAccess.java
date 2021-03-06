/*
 * Esta clase proporciona o acceso real aos datos, de xeito que para cambiar o
 * modo en que se almacenan e recuperan, únicamente é necesario escribir unha nova
 * versión de esta clase sen tocar o resto do programa.
 * 
 * A implementación actual intenta non facer uso das estructuras de almacenamento
 * que se explican na unidade 8, e utiliza STREAMS polo que o modo de almacenar 
 * e recuperar datos é moi pouco eficiente. O uso de RandomAccessFile optimizaría
 * moito o funcionamento. E o uso de estructuras de almacenamento, aínda máis.
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
    private DataInputStream frVenta=null; // Para leer o ficheiro de ventas secuencialmente
    
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
        DataOutputStream fVentas=null;
        try {
            fVentas=new DataOutputStream(new FileOutputStream(path+fventas,true));
            writeVenta(fVentas,v);
        } finally {
            if (fVentas!=null) fVentas.close();
        }
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
        Venta v=null;
              
        if (frVenta==null) frVenta=new DataInputStream(new FileInputStream(path+fventas));
        if (frVenta.available()>0) v=readVenta(frVenta);
        else {
            frVenta.close();
            frVenta=null;
        }
        return v;
    }

    /**
     * Método interno para facer un backup do histórico
     * @throws IOException 
     */
    private void makeBackupFile() throws IOException {
        File orixe=new File(path+fhistorico);
        File dest=new File(path+"."+fhistorico);
        Path bkup=Files.move(orixe.toPath(),dest.toPath());
    }
       
    /**
     * Método interno que restaura o backup por si falla algo no procesamento
     */
    private void restoreBackupFile() {
        File dest=new File(path+fhistorico);
        File orixe=new File(path+"."+fhistorico);
        try {
            Files.move(orixe.toPath(),dest.toPath());
        } catch (IOException ex) {
            System.out.println("ERRO GRAVE PROCESANDO VENTAS");
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }
    
    /**
     * Finaliza o procesamento do histórico, movendo e borrando os ficheiros necesarios
     * @throws IOException 
     */
    private void endProcess() throws IOException {
        System.out.print("Finalizando proceso....");
        File orixe=new File(path+"n_"+fhistorico);
        File dest=new File(path+fhistorico);
        File ventas=new File(path+fventas);
        File bkup=new File(path+"."+fhistorico);
        
        Files.move(orixe.toPath(),dest.toPath()); // O novo histórico pasa a ser o actual
        if (bkup.exists()) bkup.delete(); // Borramos o backup si existe
        ventas.delete();    // Borramos as ventas procesadas
        System.out.println("OK");
    }
    
    /**
     * Visualiza o contido do histórico
     * 
     * @throws FileNotFoundException
     * @throws IOException
     * @throws NotExistsException 
     */
    void listaHistorico() throws FileNotFoundException, IOException, NotExistsException {
        DataInputStream ois=null;
        int code,num;
        File Hi=new File(path+fhistorico);
        try {
            ois=new DataInputStream(new FileInputStream(Hi));
            while(ois.available()>0) {
                code=ois.readInt();
                num=ois.readInt();
                System.out.println(getProducto(code)+" ("+num+" unidades)");
            }
        } finally {
            if (ois!=null) ois.close();
        }
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
        DataOutputStream oos=null;
        DataInputStream ois=null;
        File Hi;
        File nHi;
        int code_p;
        int code, num;
        boolean exists=true;
        
        try {
            code_p=v.getCode();             // Collemos o código do producto
            Hi=new File(path+fhistorico);   // Ficheiro histórico vello
            nHi=new File(path+"n_"+fhistorico); // Novo ficheiro histórico
            try {
                oos=new DataOutputStream(new FileOutputStream(nHi)); // Escribimos no novo
                ois=new DataInputStream(new FileInputStream(Hi));    // Leemos no vello
                while(ois.available()>0) {
                    code=ois.readInt(); // Leemos entrada
                    num=ois.readInt();
                    System.out.print("Procesando código "+code+" ("+num+")...");
                    if (code==code_p) { // Si coincide incrementamos o número
                        num++;
                        code_p=-1; // Indicamos que a venta está procesada
                    }
                    oos.writeInt(code); // Escribimos entrada
                    oos.writeInt(num);
                    System.out.println("total "+num+". OK");
                }
                // Non estaba no histórico, necesitamos procesar a venta
                if (code_p>=0) {
                    System.out.print("Procesando código "+code_p+" (1)...");
                    oos.writeInt(code_p);
                    oos.writeInt(1);
                    System.out.println("total 1. OK");
                }
                oos.close(); oos=null;
                ois.close(); ois=null;
                try { // Finalizamos
                    System.out.print("Facendo Backup....");
                    makeBackupFile(); 
                    System.out.println("OK");
                    endProcess();
                } catch (IOException ex) {
                    restoreBackupFile(); // Fallo no proceso, restauramos Backup
                    System.out.println("Produciuse un erro, non se procesou o arquivo.");
                }
            } catch(FileNotFoundException ex) {
                // Non existe o histórico, incorporamos a venta ao novo histórico.
                System.out.print("Gardando Venta...");
                oos.writeInt(code_p);
                oos.writeInt(1);
                oos.close(); oos=null;
                System.out.println("OK");
                endProcess();
            }
        } finally {
            if (oos!=null) oos.close();
            if (ois!=null) ois.close();
        }
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
        DataInputStream frProductos=null;
        Producto pr=null;
        
        try {
            frProductos=new DataInputStream(new FileInputStream("/home/xavi/productos.dat"));
            while (frProductos.available()>0) {
                pr=readProducto(frProductos);
                if (pr.getCode()==code) return pr;
            }
            throw new NotExistsException();
        } finally {
            if (frProductos!=null) frProductos.close();
        }
    }

    /**
     * Garda o Producto indicado no ficheiro de productos
     * 
     * @param pr
     * @throws IOException 
     */
    void saveProducto(Producto pr) throws IOException {
        DataOutputStream fProductos=null;
        try {
            fProductos=new DataOutputStream(new FileOutputStream("/home/xavi/productos.dat",true));
            writeProducto(fProductos,pr);
        } finally {
            if (fProductos!=null) fProductos.close();
        }   
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
        int codigo;
        String name;
        float pvp;
        
        codigo=dis.readInt();
        name=dis.readUTF();
        pvp=dis.readFloat();
        return new Producto(codigo,name,pvp);
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
        dos.writeInt(pr.getCode());
        dos.writeUTF(pr.getName());
        dos.writeFloat(pr.getPVP());
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
        int dia,mes,ano;
        int code;
        
        dia=dis.readInt();
        mes=dis.readInt();
        ano=dis.readInt();
        code=dis.readInt();
        return new Venta(dia,mes,ano,code);
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
        Calendar c=v.getDate();
        int code=v.getCode();
        int dia,mes,ano;
        
        dia=c.get(Calendar.DAY_OF_MONTH);
        mes=c.get(Calendar.MONTH)+1;
        ano=c.get(Calendar.YEAR);
        dos.writeInt(dia);
        dos.writeInt(mes);
        dos.writeInt(ano);
        dos.writeInt(code);
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

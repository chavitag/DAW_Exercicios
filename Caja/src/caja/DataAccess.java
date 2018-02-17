/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    private final String path="/home/xavi/";
    private final String fventas="ventas.dat";
    private final String fproductos="productos.dat";
    private final String fhistorico="historico.dat";
    
    // So queremos un so obxecto DataAccess para a aplicación, esto se coñece
    // como "Patrón Singleton", e se fai facendo privado o constructor da clase
    // e facilitando un método para recuperar ese obxecto único.
    private static DataAccess da=null;
    private DataInputStream frVenta=null;
    
    private DataAccess() {   }
    
    public static DataAccess getInstance() {
        if (da==null) da=new DataAccess();
        return da;
    }
        
    public void saveVenta(Venta v) throws IOException {
        DataOutputStream fVentas=null;
        try {
            fVentas=new DataOutputStream(new FileOutputStream(path+fventas,true));
            writeVenta(fVentas,v);
        } finally {
            if (fVentas!=null) fVentas.close();
        }
    }

    /** Se utiliza para leer ventas secuencialmente ata chegar ao final
     * @return 
     * @throws java.io.IOException 
     * @throws java.lang.ClassNotFoundException 
     * @throws caja.NotExistsException 
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

    
    private void makeBackupFile() throws IOException {
        File orixe=new File(path+fhistorico);
        File dest=new File(path+"."+fhistorico);
        Path bkup=Files.move(orixe.toPath(),dest.toPath());
    }
    
   
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
    
    private void endProcess() throws IOException {
        System.out.print("Finalizando proceso....");
        File orixe=new File(path+"n_"+fhistorico);
        File dest=new File(path+fhistorico);
        File ventas=new File(path+fventas);
        File bkup=new File(path+"."+fhistorico);
        
        Files.move(orixe.toPath(),dest.toPath());
        if (bkup.exists()) bkup.delete();
        ventas.delete();
        System.out.println("OK");
    }
    
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
     * Esto é moi pouco eficiente, pero si non queremos usar Collections
     * (ArrayList e demais) é a única solución
     * @param v 
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
            code_p=v.getCode();
            Hi=new File(path+fhistorico);
            nHi=new File(path+"n_"+fhistorico);
            try {
                oos=new DataOutputStream(new FileOutputStream(nHi));
                ois=new DataInputStream(new FileInputStream(Hi));
                while(ois.available()>0) {
                    code=ois.readInt();
                    num=ois.readInt();
                    System.out.print("Procesando código "+code+" ("+num+")...");
                    if (code==code_p) {
                        num++;
                        code_p=-1;
                    }
                    oos.writeInt(code);
                    oos.writeInt(num);
                    System.out.println("total "+num+". OK");
                }
                // Non estaba no histórico
                if (code_p>=0) {
                    System.out.print("Procesando código "+code_p+" (1)...");
                    oos.writeInt(code_p);
                    oos.writeInt(1);
                    System.out.println("total 1. OK");
                }
                oos.close(); oos=null;
                ois.close(); ois=null;
                try {
                    System.out.print("Facendo Backup....");
                    makeBackupFile();
                    System.out.println("OK");
                    endProcess();
                } catch (IOException ex) {
                    restoreBackupFile();
                    System.out.println("Produciuse un erro, non se procesou o arquivo.");
                }
            } catch(FileNotFoundException ex) {
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

    void saveProducto(Producto pr) throws IOException {
        DataOutputStream fProductos=null;
        try {
            fProductos=new DataOutputStream(new FileOutputStream("/home/xavi/productos.dat",true));
            writeProducto(fProductos,pr);
        } finally {
            if (fProductos!=null) fProductos.close();
        }   
    }
    
    private Producto readProducto(DataInputStream dis) throws IOException {
        int codigo;
        String name;
        float pvp;
        
        codigo=dis.readInt();
        name=dis.readUTF();
        pvp=dis.readFloat();
        return new Producto(codigo,name,pvp);
    }
    
    private void writeProducto(DataOutputStream dos, Producto pr) throws IOException {
        dos.writeInt(pr.getCode());
        dos.writeUTF(pr.getName());
        dos.writeFloat(pr.getPVP());
    }
    
    private Venta readVenta(DataInputStream dis) throws IOException {
        int dia,mes,ano;
        int code;
        
        dia=dis.readInt();
        mes=dis.readInt();
        ano=dis.readInt();
        code=dis.readInt();
        return new Venta(dia,mes,ano,code);
    }
    
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

/*
 *  SOLUCION b) 3.
 */
package modeloexame2av;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author xavi
 */
public class Venta {
    private static RandomAccessFile f=null;
    private static int code;
    private static float importeTotal;
    private static int numVentas;

    public static void rexistra(Producto p) throws IOException {
        int p_code=p.getCodigo();
        
        f.seek(0);
        try {
            readItem();
            while(true) {
                if (p_code==code) break;
                readItem();
            }
        } catch(EOFException e) {
            numVentas=0;
            importeTotal=0;
            code=p_code;
        }
        incVenta();
        addTotal(p.getPrecio());
        save();
    }
    
    private static void incVenta() {
        numVentas++;
    }
    
    private static void addTotal(float n) {
        importeTotal+=n;
    }
        
    private static void save() throws IOException {
        writeItem();
    }
    
    public static void start() throws FileNotFoundException {
        if (f==null) f=new RandomAccessFile("ventas.dat","rws");
    }
    
    
    public static void end() throws IOException {
        if (f!=null) {
            f.close();
            f=null;
        }
    }
    
    private static void readItem() throws IOException,EOFException {
        code=f.readInt();
        numVentas=f.readInt();
        importeTotal=f.readFloat();
    }
    
    private static void writeItem() throws IOException {
        f.writeInt(code);
        f.writeInt(numVentas);
        f.writeFloat(importeTotal);
    }
}

/*
 * Esta clase representa un Producto
 */
package caja;

import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author xavi
 */
public class Producto implements Serializable {
    private int code;       // Código do producto
    private String name;    // Nome do producto
    private float pvp;      // PVP do producto
    
    /**
     * Constructor (Para dar de alta novos productos)
     * 
     * @param code
     * @param name
     * @param pvp 
     */
    public Producto(int code,String name,float pvp) {
        this.code=code;
        this.name=name;
        this.pvp=pvp;
    }

    /**
     * Recupera un producto xa existente)
     * @param code 
     * @return  
     * @throws java.io.IOException 
     * @throws caja.NotExistsException 
     * @throws java.lang.ClassNotFoundException 
     */
    public static Producto getInstance(int code) throws IOException, NotExistsException, ClassNotFoundException {
        DataAccess da=DataAccess.getInstance();
        return da.getProducto(code);
    }
       
    /**
     * Devolve o nome do producto
     * 
     * @return 
     */
    public String getName() {
        return name;
    }

    /**
     * Devolve o PVP do producto
     * 
     * @return 
     */
    public float getPVP() {
        return pvp;
    }
    
    /**
     * Devolve o código do producto
     * 
     * @return 
     */
    public int getCode() {
        return code;
    }
    
    /**
     * Devolve a representación do producto en String
     * 
     * @return 
     */
    @Override
    public String toString() {
        return code+" - "+name+"\t\t\t"+pvp;
    }
    
}

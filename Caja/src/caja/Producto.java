/*
 * Esta clase representa un Producto
 */
package caja;

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
     * Constructor
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

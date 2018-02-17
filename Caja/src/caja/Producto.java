/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caja;

import java.io.Serializable;

/**
 *
 * @author xavi
 */
public class Producto implements Serializable {
    private int code;
    private String name;
    private float pvp;
    
    public Producto(int code,String name,float pvp) {
        this.code=code;
        this.name=name;
        this.pvp=pvp;
    }
            
    public String getName() {
        return name;
    }

    public float getPVP() {
        return pvp;
    }
    
    public int getCode() {
        return code;
    }
    
    @Override
    public String toString() {
        return code+" - "+name+"\t\t\t"+pvp;
    }
    
}

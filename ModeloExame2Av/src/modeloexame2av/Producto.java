/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloexame2av;

import java.io.Serializable;

/**
 *
 * @author xavi
 */
class Producto implements Serializable {
    private int codigo;
    private  String nombre;
    private float precio;
    
    /**
     * Constructor para o exercicio b) 3
     * @param codigo
     * @param nombre
     * @param precio 
     */
    public Producto(int codigo,String nombre,float precio) {
        this.codigo=codigo;
        this.nombre=nombre;
        this.precio=precio;
    }
    
    public int getCodigo() {
        return codigo;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    
    public float getPrecio() {
        return precio;
    }
}

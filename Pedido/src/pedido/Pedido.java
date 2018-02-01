/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pedido;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author xavi
 */
public class Pedido {
    Cliente cliente;
    ArrayList <LineaPedido> pedidos=new ArrayList <LineaPedido>();


    public Pedido(String pedido) {
    }

    public Pedido(File pedido) {
    }

    public Cliente getCliente() {
    	return cliente;
    }

    public ArrayList <LineaPedido> getPedidos() {
	return pedidos;
    }

    @Override
    public String toString() {
	String str="Cliente: "+cliente+" Pedido: ";
	for(LineaPedido p: pedidos) {
            str+=p+"\n";
	}
	return str;
    }
    
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}

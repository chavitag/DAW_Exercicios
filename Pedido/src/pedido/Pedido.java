/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pedido;

import java.io.File;
import java.util.ArrayList;

/*##    PEDIDO      ##
Número de pedido: { 20304 }
Cliente: { Muebles Bonitos S.A. }
Código del cliente: { 00293 }
Dirección de factura: { C/ De en frente, 11 }
} Dirección de entrega: { C/ De al lado, 22 }
Nombre del contacto: { Elias }
Teléfono del contacto: { 987654321 }
Correo electrónico del contacto: { mail@mail1234.com }
Fecha preferente de entrega: { 19/11/2012 }
Forma de pago: { Transferencia }
## ARTICULOS ##
{ Código Artículo | Descripción | Cantidad }
{ 0001231 | Tuercas tipo 1 | 200 }
{ 0001200 | Tornillos tipo 1 | 200 }
{ 0002200 | Arandelas tipo 2 | 200 }
## FIN ARTICULOS ##
## FIN PEDIDO ##*/

/**
 *
 * @author xavi
 */
public class Pedido {
    Cliente cliente;
    ArrayList <LineaPedido> pedidos=new ArrayList <LineaPedido>();


    public Pedido(String pedido) {
        String regex="##[ ]*ARTICULOS[ ]*##";
        String[] partes=pedido.split(regex);
        String title="Cliente\n";
        for(String s: partes) {
            System.out.println(title+s);
            title="Lineas:\n";
        }
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
        String ejemplo="##    PEDIDO      ##\n" +
"Número de pedido: { 20304 }\n" +
"Cliente: { Muebles Bonitos S.A. }\n" +
"Código del cliente: { 00293 }\n" +
"Dirección de factura: { C/ De en frente, 11 }\n" +
"Dirección de entrega: { C/ De al lado, 22 }\n" +
"Nombre del contacto: { Elias }\n" +
"Teléfono del contacto: { 987654321 }\n" +
"Correo electrónico del contacto: { mail@mail1234.com }\n" +
"Fecha preferente de entrega: { 19/11/2012 }\n" +
"Forma de pago: { Transferencia }\n" +
"## ARTICULOS ##\n" +
"{ Código Artículo | Descripción | Cantidad }\n" +
"{ 0001231 | Tuercas tipo 1 | 200 }\n" +
"{ 0001200 | Tornillos tipo 1 | 200 }\n" +
"{ 0002200 | Arandelas tipo 2 | 200 }\n" +
"## FIN ARTICULOS ##\n" +
"## FIN PEDIDO ##";
        Pedido p=new Pedido(ejemplo);
        System.out.println(p);
    }
    
}

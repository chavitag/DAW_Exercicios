/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myscanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author xavi
 */
public class MyScanner {
    
    
    public String readString() throws IOException {
        // Leer con System.in directamente
    }
    
    private boolean verifyEmailString(String str) {
        char[] strLetras=str.toCharArray();
        int c=0;
        
        if ((strLetras[0]=='.')||
            (strLetras[0]=='-')||
            (strLetras[strLetras.length-1]=='.') ||
            (strLetras[strLetras.length-1]=='-')) return false;
        
        for(char l: strLetras) {
            if (((l<'a')||(l>'z'))&&(l!='-')&&(l!='.')) return false;
            if (l=='.') c++;
            else        c=0;
            if (c==2) return false;
        }
        
        return true;
    }
    
    public String readEmail() throws IOException,KeyboardDataInputException {
        String mail=readString();
        String[] text=mail.toLowerCase().split("@");
        if (text.length!=2) 
            throw new KeyboardDataInputException(KBError.KB_EMAILFORMAT_EXCEPTION);
        if (!verifyEmailString(text[0]) || !verifyEmailString(text[1]))
            throw new KeyboardDataInputException(KBError.KB_EMAILFORMAT_EXCEPTION);
        if (!URL.verifyDomain(text[1]))
            throw new KeyboardDataInputException(KBError.KB_EMAILFORMAT_EXCEPTION);
        return mail;
    }
  
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        MyScanner scn=new MyScanner();
        
        try {
            System.out.print("EMAIL: "); scn.readEmail();
        } catch (KeyboardDataInputException e) {
            System.out.println("Error en la entrada: "+e.getMessage());
        }
        
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myscanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

/**
 *
 * @author xavi
 */
public class MyScanner {
    private BufferedReader in;
    private static String dateformat=null;

    public MyScanner() {
        this.in = new BufferedReader(new InputStreamReader(System.in));
    }

    public String readString() throws IOException {
        return in.readLine();
    }
    
    public float readFloat() throws IOException,KeyboardDataInputException {
        float f;
        try {
            f=Float.parseFloat(readString());
        } catch(NumberFormatException e) {
            throw new KeyboardDataInputException(KBError.KB_FLOATFORMAT_EXCEPTION);
        }
        return f;
    }
  
     public float readInt() throws IOException,KeyboardDataInputException {
        int i;
        try {
            i=Integer.parseInt(readString());
        } catch(NumberFormatException e) {
            throw new KeyboardDataInputException(KBError.KB_INTFORMAT_EXCEPTION);
        }
        return i;
    }
    
    private static String verifyFormat(String format)  throws KeyboardDataInputException {
        return format;
    }
     
    public static void setDateFormat(String format) throws KeyboardDataInputException {
        MyScanner.dateformat=verifyFormat(format);
    }
     
    private Calendar verifyDateFormat(String str) throws KeyboardDataInputException {
        int ano,mes,dia;
        
        
        
        return Calendar.getInstance().set(ano,mes,dia);
    }
    
    public Calendar readDate() throws IOException,KeyboardDataInputException {
        return verifyDateFormat(readString());
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
        Calendar c;
        
        try {
            System.out.print("EMAIL: "); scn.readEmail();
            System.out.print("DATA: "); c=scn.readDate();
            System.out.println("A data é :"+c);
        } catch (KeyboardDataInputException e) {
            System.out.println("Error en la entrada: "+e.getMessage());
        }
        
    }
    
}

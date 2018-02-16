/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myscanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author xavi
 */
public class MyScanner {
    private BufferedReader in;
    private static DateFormat dateformat=null;

    /**
     *  Constructor: Crea o BufferedReader para as lecturas de teclado
     */
    public MyScanner() {
        this.in = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Lee un String do Teclado
     * 
     * @return String : O String leído do teclado
     * @throws IOException (Erro na entrada)
     */
    public String readString() throws IOException {
        return in.readLine();
    }
    
    /**
     * Lee un float do Teclado
     * 
     * @return float: O Float leído do teclado
     * @throws IOException (Erro na entrada)
     * @throws KeyboardDataInputException (Os datos introducidos non son un float)
     */
    public float readFloat() throws IOException,KeyboardDataInputException {
        float f;
        try {
            f=Float.parseFloat(readString());
        } catch(NumberFormatException e) {
            throw new KeyboardDataInputException(KBError.KB_FLOATFORMAT_EXCEPTION);
        }
        return f;
    }
  
    /**
     * Lee un int do Teclado
     * 
     * @return int: Número enteiro leído do Teclado
     * @throws IOException (Erro na entrada) 
     * @throws KeyboardDataInputException (Os datos non son un int)
     */
    public float readInt() throws IOException,KeyboardDataInputException {
        int i;
        try {
            i=Integer.parseInt(readString());
        } catch(NumberFormatException e) {
            throw new KeyboardDataInputException(KBError.KB_INTFORMAT_EXCEPTION);
        }
        return i;
    }
    
    /**
     * Indica o formato para Verificar que a data especificada é  correcta
     * Tamén o analiza para que sexa máis fácil de aplicar sobre a data.
     * O formato pode ser:
     *  DD/MM/AAAA, D/M/AAAA, AAAA*MM*DD ... etc
     * é decir D (dia) M (mes) ou A (ano). e un separador. 
     * DD e D significan o mesmo, únicamente podemos ter ou dúas ou catro A
     * Si temos dúas A, indica que debemos completar o ano cun 20 antes..., si 
     * temos 4 A, que é o ano completo.
     * 
     * @param format
     * @throws KeyboardDataInputException
     */
    public static void setDateFormat(String format) throws DateFormatException {
        MyScanner.dateformat=new DateFormat(format);
    }
     
    /**
     *
     * @return
     * @throws IOException
     * @throws KeyboardDataInputException
     */
    public Calendar readDate() throws IOException,KeyboardDataInputException {
        Calendar c;
        try {
            if (dateformat==null) throw
                new KeyboardDataInputException(KBError.KB_DATEFORMAT_EXCEPTION,"Formato de Fecha no Especificado");
            int[] fecha=dateformat.verify(readString());
            c=new GregorianCalendar(fecha[0],fecha[1]-1,fecha[2]);
        } catch (DateFormatException err) {
            throw new KeyboardDataInputException(KBError.KB_DATEFORMAT_EXCEPTION,dateformat.toString());
        }
        return c;
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
    
    /**
     *
     * @return
     * @throws IOException
     * @throws KeyboardDataInputException
     */
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
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        MyScanner scn=new MyScanner();
        Calendar c;
        
        try {
            System.out.print("EMAIL: "); scn.readEmail();
            // Formato "Raro" Mes*Ano dous díxitos*Dia
            MyScanner.setDateFormat("M*AA*D");
            System.out.print("DATA: "); c=scn.readDate();
            SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
            String formatted = formatDate.format(c.getTime());
            System.out.println("A data é :"+formatted);
        } catch (KeyboardDataInputException | DateFormatException e) {
            System.out.println("Error en la entrada: "+e.getMessage());
        }
        
    }
    
}

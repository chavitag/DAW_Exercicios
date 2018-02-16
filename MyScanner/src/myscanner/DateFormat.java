/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myscanner;


import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author xavi
 */
public class DateFormat {
    int lAno=0; // Lonxitude do ano: 2 ou 4
    char[] fmt=new char[3];  // formato {'D','M','A'} ou {'A','M','D'}.. etc. 
    String separador; // caracter separador entre día mes e ano
    String format;
    
    /**
     * Constructor. 
     * @param format - Formato da data. A analiza e xenera o patron almacenado en fmt, separador e lAno
     * @throws DateFormatException 
     */
    DateFormat(String format) throws DateFormatException {
        String[] aux;
        int ndano;      // Díxitos do ano... 2 ou 4
        char last=0,s=0; 
        int cta=0; int ctam=0;

        // Dividimos o String en letras para analizar
        char[] f=format.toCharArray();
        
        // Verificación do separador
        //
        // Recorremos as letras
        for(char c:f) {
            // Buscamos separador. non é nin A nin M nin D
            if ((c!='A') && (c!='M') && (c!='D')) {
                cta++;
                
                //¿Temos máis de 2 separadores?
                if (cta>2)
                    throw new DateFormatException();
                
                // ¿Temos unha letra que non é o separador nin A nin M nin D ?
                if ((s!=0) && (s!=c))
                    throw new DateFormatException();
                
                // ¿Temos dous separadores seguidos?
                if (last==0) last=c;
                else throw new DateFormatException();
                
                s=c;
            } else {
                last=0;
            }
        }
        
        // Escapamos os caracteres especiais Regex
        separador=procesaSeparador(s);
        // Partimos o patrón usando o separador
        aux=format.split(separador);
        
        // Ten que ter 3 partes
        if (aux.length!=3)
            throw new DateFormatException();
        
        // Verificamos cada parte e comprobamos que se especifica dia mes e ano
        cta=0; ctam=0;
        for(int idx=0;idx<3;idx++) {
            // Análise do Ano, Mes e Día
            switch (aux[idx]) {
                case "AA":
                    if (lAno!=0) throw new DateFormatException();   // Xa se especificou AA ou AAAA
                    lAno=2;
                    fmt[idx]='A';
                    break;
                case "AAAA":
                    if (lAno!=0) throw new DateFormatException();   // Xa se especificou AA ou AAAA
                    lAno=4;
                    fmt[idx]='A';
                    break;
                case "D":
                case "DD":
                    if (cta!=0) throw new DateFormatException();    // Xa se especificou D ou DD
                    fmt[idx]='D';
                    cta=1;
                    break; 
                case "M":
                case "MM":
                    if (ctam!=0) throw new DateFormatException();   // Xa se especificou M ou MM
                    fmt[idx]='M';
                    ctam=1;
                    break;
                default:
                    // NON é unha letra válida
                    throw new DateFormatException();
            }
        }
        this.format=format;
    }
    
    /**
     * Algúns separadores poden ter un significado especial nas expresións regulares
     * e é necesario escapalos
     * @param separador
     * @return separador escapado
     */
    private String procesaSeparador(char separador) {
        char[] reserved={'\\','^','$','.',',','|','?','*','+','(',')','[',']','{','}'};
        String s=null;
        for(char c: reserved) {
            if (c==separador) {
                s="\\"+c;
                break;
            } 
        }
        if (s==null) s=((Character)separador).toString();
        return s;
    }
    
    /**
     * Verifica que date representa unha data segun o formato especificado
     * e devolve os campos ano,mes e día en ese orden si é correcta.
     * @param fecha - Fecha en formato String a verificar
     * @return - Array de ints indicando ano mes e día
     * @throws myscanner.DateFormatException : Data errónea segun o patrón.
     */
    public int[] verify(String fecha) throws DateFormatException {
        int[] date=new int[3];
        try {
            String[] f=fecha.split(separador);
            if (f.length!=3) throw new DateFormatException();
            for(int idx=0;idx<3;idx++) {
                switch(fmt[idx]) {
                    case 'A':
                        if (lAno!=f[idx].length()) throw new DateFormatException();
                        date[0]=Integer.parseInt(f[idx]);
                        if (lAno==2) date[0]=2000+date[0];
                        break;
                    case 'D':
                        date[2]=Integer.parseInt(f[idx]);
                        break;
                    case 'M':
                        date[1]=Integer.parseInt(f[idx]);
                        if ((date[1]<1)||(date[1]>12)) throw new DateFormatException();
                        break;
                }
            }
            // Comprobamos que o día é correcto
            Calendar cal = new GregorianCalendar(date[0],date[1]-1, 1); 
            if ((date[2]<1) || (date[2] > cal.getActualMaximum(Calendar.DAY_OF_MONTH)))
                    throw new DateFormatException();
        } catch (Exception e) {
            throw new DateFormatException();
        }
        return date;
    }
    
    @Override
    public String toString() {
        return format;
    }
}

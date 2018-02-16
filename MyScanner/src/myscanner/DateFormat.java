/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myscanner;

/**
 *
 * @author xavi
 */
public class DateFormat {
    int lAno=0; // Lonxitude do ano: 2 ou 4
    char[] fmt={'\0','\0','\0'};  // formato {'D','M','A'} ou {'A','M','D'}.. etc. 
    char separador=0; // caracter separador entre día mes e ano
    
    DateFormat(String format) throws DateFormatException {
        String[] aux;
        int ndano;      // Díxitos do ano... 2 ou 4
        char last=0; int cta=0; int ctam=0;

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
                if ((separador!=0) && (separador!=c))
                    throw new DateFormatException();
                
                // ¿Temos dous separadores seguidos?
                if (last==0) last=c;
                else throw new DateFormatException();
                
                separador=c;
            } else {
                last=0;
            }
        }
        
        // Partimos o patrón usando o separador
        aux=format.split(((Character)separador).toString());
        
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
    }
    
}

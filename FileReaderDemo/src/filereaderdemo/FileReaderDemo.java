/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filereaderdemo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author xavi
 */
public class FileReaderDemo {
    /*
    * Este atributo sirve para detectar o final do ficheiro no método readLine
    * e poder evitar así intentos de lectura innecesarios.
    */
    private static boolean isEof=false;

    /**
     * readLine para FileReader
     * Descripción: Coma FileReader so ten as operacións mínimas de lectura
     * necesito implementar un método que me permita ler liñas. Leo caracter a
     * caracter ata que atopo un caracter de fin de liña ou remata o ficheiro.
     * Os caracteres leidos os vou engadindo a un StringBuilder que me permite
     * crear un String de xeito dinámico
     * 
     * @param f: FileReader do que leemos
     * @return String: unha liña leida, ou null si non teño nada leido.
     */
    private static String readLine(FileReader f) throws IOException {
        StringBuilder bfs=new StringBuilder();
        char c=0;
                
        while((c!='\n')&&(c!=-1)) bfs.append(f.read());
        if (c==-1) isEof=true;
        if (bfs.length() > 0) return bfs.toString();
        return null;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList <String> buffer;
        String line;
        FileReader f;
        
        buffer=new ArrayList <>();
        try {
            f = new FileReader(args[1]);
            while(!isEof) {
                line=readLine(f);
                if (line!=null) buffer.add(line);
            }
            f.close();
            System.out.println("Ficheiro "+args[1]+" Leído:");
            System.out.println("O ficheiro ten "+buffer.size()+" liñas.");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileReaderDemo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileReaderDemo.class.getName()).log(Level.SEVERE, null, ex);           
        }
  
    }
    
}

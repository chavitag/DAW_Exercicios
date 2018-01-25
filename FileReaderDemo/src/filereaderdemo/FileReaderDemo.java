/*
 * Lee un arquivo liña a liña usando a clase FileReader
 * Como FileReader proporciona pouca funcionalidade, para facer
 * o programa se implementa un método auxiliar que lee as liñas
 */
package filereaderdemo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
        StringBuilder bfs=new StringBuilder(700000);
        int c=0;
        
        // Código ineficiente para reparar en Contornos
        while((c!='\n')&&(c!=-1)) {
            c=f.read();
            if ((c!='\n')&&(c!=-1)) {
                bfs.append((char)c);
            }
        }
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
        Scanner scn;
        int nl;
        
        buffer=new ArrayList <>();
        scn = new Scanner(System.in);
        try {
            System.out.println("Cargando "+args[0]);
            // Código ineficiente para reparar en Contornos
            f = new FileReader(args[0]);
            while(!isEof) {
                line=readLine(f);
                if (line!=null) buffer.add(line);
            }
            f.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Non se atopa o ficheiro "+args[0]);
        } catch (IOException ex) {
            System.out.println("Erro lendo "+args[0]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Debes especificar o nome do ficheiro como argumento.");
        }
        System.out.println("Ficheiro "+args[0]+" Leído:");
        System.out.println("O ficheiro ten "+buffer.size()+" liñas.");
        System.out.println("Que Liña queres ver? ");
        nl=Integer.valueOf(scn.nextLine());
        try {
            System.out.println(buffer.get(nl-1));
        } catch(IndexOutOfBoundsException ex) {
            System.out.println("Esa liña non existe");
        }   
    }
    
}

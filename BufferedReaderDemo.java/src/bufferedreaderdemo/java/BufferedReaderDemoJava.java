/*
 * Este programa fai o mesmo que FileReaderDemo, pero empregando
 * un BufferedReader que xa nos proporciona a posibilidade de ler
 * liña a liña
 */
package bufferedreaderdemo.java;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author xavi
 */
public class BufferedReaderDemoJava {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList <String> buffer;
        String line;
        BufferedReader f;
        Scanner scn;
        int nl;
        
        buffer=new ArrayList <>();
        scn = new Scanner(System.in);
        try {
            System.out.println("Cargando "+args[0]);
            f = new BufferedReader(new FileReader(args[0]));
            line=f.readLine();
            while(line!=null) {
                buffer.add(line);
                line=f.readLine();
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

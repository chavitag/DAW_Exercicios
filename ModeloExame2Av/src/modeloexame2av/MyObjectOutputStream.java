/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeloexame2av;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 *
 * @author xavi
 */
class MyObjectOutputStream extends ObjectOutputStream {
    private static ObjectOutputStream oos=null;
    
    ObjectOutputStream getInstance(String filename) throws IOException {
        File f=new File(filename);
        if (f.exists()) 
                oos=new MyObjectOutputStream(new FileOutputStream(filename,true));
        else
                oos=new ObjectOutputStream(new FileOutputStream(filename,true));
        return oos;
    }
    
    private MyObjectOutputStream(FileOutputStream fos) throws IOException {
       super(fos);
    }
   
    protected void writeStreamHeader() throws IOException {
    }
    
}

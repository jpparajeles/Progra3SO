/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progra3so;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import progra3so.FileSytem.DiskManager;

/**
 *
 * @author José Pablo
 */
public class Progra3SO {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        String url = "C:\\Users\\José Pablo\\Desktop\\test\\disco.txt";
        
        System.out.println("Hola");
        DiskManager diskManager = new DiskManager();
        try {
            diskManager.Create(url, 32, 10);
            diskManager.WriteFile("Hola");
            List<Integer> WriteFile = diskManager.WriteFile("Esta es una prueba de mas de 32 bits de largo");
            diskManager.WriteFile("Adieu");
            diskManager.DeleteFile(WriteFile);
            diskManager.WriteFile("En un hermoso dia de verano los pajaros cantan, rien y sonrien a la luz de sol");
            
        } catch (IOException ex) {
            Logger.getLogger(Progra3SO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

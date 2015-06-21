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
import progra3so.FileSytem.FileManager;

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
        String[] split = "/".split("/");
        
        String url = "C:\\Users\\José Pablo\\Desktop\\test\\disco.txt";
        
        System.out.println("Hola");
        
        try {
            FileManager FM = new FileManager();
            FM.Create(url, 32, 10);
            FM.MkDir("Documentos");
            FM.MkDir("Musica");
            FM.MkDir("Imagenes");
            FM.MkDir("Videos");
                FM.CambiarDir("Documentos");
                    FM.MkDir("FolderDocumentos");
                    FM.File("info1", ".txt", "Fabian Lopez Quesada");
                    FM.File("info2", ".txt", "Jose Pablo Parajeles");
                    FM.File("info3", ".txt", "Rudiney Mejias Calvo");
                    FM.File("info4", ".txt", "Kenneth Meza Chaves");
                        FM.CambiarDir("./FolderDocumentos");
                        FM.File("info5", ".txt", "Maria Fernanda Chaves");
                    FM.CambiarDir(".");
            
        } catch (Exception ex) {
            Logger.getLogger(Progra3SO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

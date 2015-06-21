/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progra3so;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import progra3so.FileSytem.DiskManager;
import progra3so.FileSytem.FileManager;
import progra3so.FileSytem.Node;

/**
 *
 * @author José Pablo
 */
public class Progra3SO {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        String[] split = "/".split("/");
        
        String url = "C:\\Users\\Fabian\\Desktop\\disco.txt";
        
        DiskManager diskManager = new DiskManager();
        FileManager FM = new FileManager();
        try {
            FM.Create(url, 32, 10);
            //diskManager.WriteFile("Hola");
            //List<Integer> WriteFile = diskManager.WriteFile("Esta es una prueba de mas de 32 bits de largo");
            //diskManager.WriteFile("Adieu");
            //diskManager.DeleteFile(WriteFile);
            //diskManager.WriteFile("En un hermoso dia de verano los pajaros cantan, rien y sonrien a la luz de sol");
            FM.MkDir("Carpeta1");
            //FM.CambiarDir("Carpeta1");
            FM.MkDir("Carpeta2");
            FM.MkDir("Carpeta3");
            FM.MkDir("Carpeta4");
            //FM.CambiarDir("../");
            //FM.CambiarDir("Carpeta1");
            FM.File("info", ".txt", "Fabian Lopez Quesada");
            FM.File("info1", ".txt", "The one that...");
            
            System.out.println("EL FOLDER ACTUAL ES: ");
            //FM.MkDir("ArchivoCreado1");
            System.out.println(FM.getCurrentFolder().FullName().toString());
            FM.SelectFile("info.txt");
            System.out.println("PROPIEDADES DE ARCHIVO");
            System.out.println(FM.ContFile());
            
            FM.SelectFile("info1.txt");
            System.out.println("PROPIEDADES DE ARCHIVO");
            System.out.println(FM.ContFile());
            
//System.out.println(FM.VerPropiedades().getCreationDate());
            
            List<Node> ListaFolders = new ArrayList<Node>();
            ListaFolders = FM.ListarDir();
            for (int i = 0; i < ListaFolders.size(); i++) {
                System.out.println(ListaFolders.get(i).FullName());
            }
            
            
        } catch (IOException ex) {
            Logger.getLogger(Progra3SO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

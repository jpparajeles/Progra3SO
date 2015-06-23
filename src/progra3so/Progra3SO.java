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
import progra3so.FileSytem.File;
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
            FM.MkDir("Carpeta1",true);
            FM.MkDir("Carpeta2",true);
            FM.MkDir("Carpeta3",true);
            FM.MkDir("Carpeta4",true);
            FM.File("info", ".txt", "Fabian Lopez Quesada",true);
            FM.File("info1", ".txt", "Fabian Lopez Quesada",true);
            FM.File("info2", ".txt", "Fabian Lopez Quesada",true);
            FM.File("info3", ".txt", "Fabian Lopez Quesada",true);
            
            System.out.println(String.valueOf(FM.QSpaceFree()));
            FM.SelectFile("info.txt");
            System.out.println(FM.getRoot().Path());
            //List<File> ListaFile = new ArrayList<File>();
            //ListaFile = FM.Find("Carpeta1");
            //System.out.println("TIENE");
            //System.out.println(ListaFile.get(0).FullName());
            
            List<Node> ListaFolders = new ArrayList<Node>();
            ListaFolders = FM.ListarDir();
            for (int i = 0; i < ListaFolders.size(); i++) {
                //System.out.println(ListaFolders.get(i).FullName());
            }
        } catch (IOException ex) {
            Logger.getLogger(Progra3SO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
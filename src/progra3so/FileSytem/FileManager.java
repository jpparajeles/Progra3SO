/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progra3so.FileSytem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 *
 * @author José Pablo
 */
public class FileManager {
    private final Root root;
    private Node CurrentNode;
    private Folder CurrentFolder;
    private final DiskManager diskManager;

    public FileManager() {
        root = Root.getRoot();
        CurrentNode = null;
        CurrentFolder = root;
        diskManager = new DiskManager();
    }

    public Node getCurrentNode() {
        return CurrentNode;
    }

    public Folder getCurrentFolder() {
        return CurrentFolder;
    }
    
    private void UpdateParent(Properties properties) {
        UpdateParent(CurrentFolder, properties);
    }
    
    private void UpdateParent(Folder folder, Properties properties)
    {
        // Update Parent properties
        Properties parentP = folder.getProperties();
        parentP.setLastModifiedDate(new Date());
        parentP.setSize(parentP.getSize()+properties.getSize());
        parentP.setDiskSize(parentP.getDiskSize()+properties.getDiskSize());
        if (folder.getParent()!=null)
        {
            UpdateParent(folder.getParent(), parentP);
        }
    }
    
    private Node PathParser(String url) throws Exception
    {
        String[] split = url.split("/");
        ArrayList<String> splitlist = new ArrayList<>();
        Collections.addAll(splitlist,split);
        Node start;
        String startS = splitlist.get(0);
        int i = 1;
        switch (startS) {
            case ".":
                start = CurrentFolder;
                break;
            case "..":
                start = CurrentFolder.getParent();
                break;
            default:
                start = root;
                i = 0;
                break;
        }
        for (; i<splitlist.size(); i++)
        {
            switch (startS) {
            case ".":
                break;
            case "..":
                if(start.getParent()==null)
                {
                    throw new Exception(start.FullName()+" no tiene padre");
                }
                start = start.getParent();
                break;
            default:
                boolean found = false;
                if (!(start instanceof Folder))
                {
                    throw new Exception(start.FullName()+" no es un folder");
                }
                for (Node child : ((Folder)start).getChildren())
                {
                    if (child.FullName().equals(splitlist.get(i)))
                    {
                        start = child;
                        found = true;
                        break;
                    }
                }
                if(!found)
                {
                    throw new Exception("No encontrado");
                }
                break;
            }
        }
        return 
    }
    
    public void Create(String url, int SegL, int SegQ) throws IOException
    {
        diskManager.Create(url, SegL, SegQ);
    }
    public void File(String FileName, String FileExt, String Content) throws IOException
    {
        // file logico
        File file = new File(FileName,FileExt, CurrentFolder);
        // añdor al padre
        CurrentFolder.children.add(file);
        // archivo de propiedades
        Properties properties = file.getProperties();
        // File fisico
        List<Integer> WriteFile = diskManager.WriteFile(Content);
        file.getSectorList().addAll(WriteFile);
        // Set properties
        properties.setSize(Content.length());
        properties.setDiskSize(WriteFile.size());
        properties.setLastModifiedDate(new Date());
        CurrentNode = file;
        UpdateParent(properties);        
    }

    public void MkDir(String Name)
    {
        // folder logico
        Folder folder = new Folder(Name, CurrentFolder);
        // añadir al padre
        CurrentFolder.children.add(folder);
        // set propiedades
        Properties properties = folder.getProperties();
        properties.setLastModifiedDate(new Date());
        properties.setSize(0);
        properties.setDiskSize(0);
        CurrentNode = folder;
        UpdateParent(properties);
        
    }
            
    
    
    
    
    
}

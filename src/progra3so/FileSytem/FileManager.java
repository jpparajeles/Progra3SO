/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progra3so.FileSytem;

import com.sun.org.apache.xpath.internal.axes.SelfIteratorNoPredicate;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        if(split.length==0)
        {
            return root;
        }
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
            case "":
                start = root;
                break;
            default:
                start = CurrentFolder;
                i = 0;
                break;
        }
        for (; i<splitlist.size(); i++)
        {
            switch (splitlist.get(i)) {
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
        return start;
    }
    
    public void Create(String url, int SegL, int SegQ) throws IOException
    {
        diskManager.Create(url, SegL, SegQ);
    }
    public void File(String FileName, String FileExt, String Content, Boolean overwrite) throws IOException, Exception
    {
        if(diskManager.FreeSpace()<Content.length())
        {
            throw new Exception("No hay Suficiente espacio libre");
        }
        boolean error = false;
        try
        {
            Node child = getChild(CurrentFolder, FileName+FileExt);
            if(!overwrite)
            {
                error = true;
            }
            else
            {
                ReMove(child);
                error = false;
            }
        }
        catch (Exception e)
        {
            error = false;
        }
        if(error)
        {
            throw new Exception("El archivo existe y no se debe sobreescribir");
        }
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

    public void MkDir(String Name, boolean overwrite) throws Exception
    {
        boolean error = false;
        try
        {
            Node child = getChild(CurrentFolder, Name);
            if(!overwrite)
            {
                error = true;
            }
            else
            {
                ReMove(child);
                error = false;
            }
        }
        catch (Exception e)
        {
            error = false;
        }
        if(error)
        {
            throw new Exception("El Folder existe y no se debe sobreescribir");
        }
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
    
    public void CambiarDir(String url) throws Exception
    {
        Node PathParser = PathParser(url);
        if (!(PathParser instanceof Folder))
        {
            throw new Exception("No es un Directorio");
        }
        CurrentFolder = (Folder) PathParser;
        CurrentNode = null;
    }
    
    public void SelectFile(String url) throws Exception
    {
        CurrentNode = PathParser(url);
    }
    public List<Node> ListarDir()
    {
        return CurrentFolder.getChildren();
    }
    public void ModFile(String Content) throws Exception
    {
        
        File actual = (File)CurrentNode;
        if(diskManager.FreeSpace()+actual.properties.getDiskSize()<Content.length())
        {
            throw new Exception("No hay suficiente espacio en el disco");
        }
        diskManager.DeleteFile(actual.getSectorList());
        List<Integer> WriteFile = diskManager.WriteFile(Content);
        actual.getSectorList().clear();
        actual.getSectorList().addAll(WriteFile);
        Properties properties = actual.getProperties();
        // Set properties
        properties.setSize(Content.length());
        properties.setDiskSize(WriteFile.size());
        properties.setLastModifiedDate(new Date());
        
        UpdateParent(properties);
    }
    public Properties VerPropiedades()
    {
        return CurrentNode.getProperties();
    }
    public String ContFile() throws Exception
    {
        if (!(CurrentNode instanceof File))
        {
            throw new Exception("No es un archivo");
        }
        File file = (File)CurrentNode;
        return diskManager.ReadFile(file.getSectorList()).substring(0, file.getProperties().getSize());
    }
    private Node getChild(Folder parent, String name) throws Exception
    {
        for (Node child : parent.getChildren())
        {
            if (child.FullName().equals(name))
            {
                return child;
            }
        }
        throw new Exception("No encontrado");
    }
    private int indexChild(Folder parent, String name) throws Exception
    {
        for (int i = 0; i<parent.getChildren().size(); i++)
        {        
            if (parent.getChildren().get(i).FullName().equals(name))
            {
                return i;
            }
        }
        throw new Exception("No encontrado");
    }
    private void ReMove(Node node) throws Exception
    {
        if(node instanceof File)
        {
            Properties AntiFile = Properties.AntiFile(node.getProperties());
            Folder parent = node.getParent();
            parent.getChildren().remove(indexChild(parent, node.FullName()));
            UpdateParent(parent,AntiFile);
            diskManager.DeleteFile(((File)node).getSectorList());
        }
        else
        {
            for(Node child : ((Folder)node).getChildren())
            {
                ReMove(child);
            }
            Folder parent = node.getParent();
            parent.getChildren().remove(indexChild(parent, node.FullName()));
        }
    }
    public void ReMove() throws Exception
    {
        if (CurrentNode instanceof Root)
        {
            throw new Exception("Imposible borrar la Raíz del disco");
        }
        ReMove(CurrentNode);
        
    }
    public int QSpaceFree()
    {
        return diskManager.FreeSpace();
    }
    
    private List<File> Find(Folder folder, Pattern p)
    {
        List<File> ret = new ArrayList<>();
        for(Node child : folder.getChildren())
        {
            if(child instanceof Folder)
            {
                ret.addAll(Find((Folder)child, p));
            }
            else
            {
                Matcher m = p.matcher(child.Path());
                if(m.matches())
                {
                    ret.add((File)child);
                }
            }
        }
        return ret;
    }
    
    public List<File> Find(String finder)
    {
        finder = finder.replace("\\", "\\\\");
        finder = finder.replace(".", "\\.");
        finder = finder.replace("*", "\\w*");
        finder = ".*"+finder;
        
        Pattern p = Pattern.compile(finder,Pattern.CASE_INSENSITIVE|Pattern.UNICODE_CASE|Pattern.UNICODE_CHARACTER_CLASS);
        return Find(root,p);
    }
    
    
    public void Rename(File file, String name)
    {
        String[] split = name.split("\\.");
        file.setName(split[0]);
        file.setExtention(split[1]);
    }
    
    public void Rename(Folder folder, String name)
    {
        folder.setName(name);
    }
    
    public void Move(String target, boolean overwrite) throws Exception
    {
        
        int endIndex = target.lastIndexOf("/");
        Node destino = null;
        String last;
        if (endIndex != -1)  
        {
            String parentT = target.substring(0, endIndex);
            
            try {
                destino = PathParser(parentT);
            } catch (Exception exception) {
                throw new Exception("El path es invalido", exception);
            }
            if (!(destino instanceof Folder))
            {
                throw new Exception("El destino no se encuentra dentro de un Folder");
            }
            last = target.substring(endIndex+1);
        }
        else
        {
            destino = root;
            last = target;
        }
        boolean error = false;
        String msj = "";
        try {
            Node child = getChild((Folder) destino, last);
            if (child instanceof File)
            {
                if(CurrentNode instanceof Folder)
                {
                    error = true;
                    msj = "Imposible mover Folder en archivo";
                }
                else
                {
                    if(!overwrite)
                    {
                        error = true;
                    }
                    else
                    {
                        ReMove(child);
                        error = false;
                    }
                }
            }
            else
            {
                try {
                    destino = child;
                    child = getChild((Folder) destino, last);
                    if(!overwrite)
                    {
                        error = true;
                    }
                    else
                    {
                        ReMove(child);
                        error = false;
                    }
                } catch (Exception exception) {
                    error = false;                    
                }
            }

        } catch (Exception exception) {
            error = false;
        }
        if(error)
        {
            throw new Exception(msj);
        }
        
        if (!last.equals(CurrentNode.FullName()))
            {
                if (CurrentNode instanceof File)
                {
                    Rename((File) CurrentNode, last);
                }
                else
                {
                    Rename((File) CurrentNode, last);
                }
            }
            
        Folder  newPadre = ((Folder)destino);
        newPadre.children.add(CurrentNode);
        UpdateParent(newPadre, CurrentNode.getProperties());
        CurrentFolder = newPadre;

        Folder padre = CurrentNode.getParent();
        int indexhijo = indexChild(padre, CurrentNode.FullName());
        padre.getChildren().remove(indexhijo);
        Properties AntiFile = Properties.AntiFile(CurrentNode.getProperties());
        UpdateParent(padre,AntiFile);
        CurrentNode.setParent(newPadre);
            
        
    }

    public Root getRoot() {
        return root;
    }
    
    public List<String> Disk() throws IOException
    {
        return diskManager.Disk();
    }
    
    private void Export(Folder folder, String p) throws IOException
    {
        String local_url = p+"/"+folder.getName();   
        java.io.File dir = new java.io.File(local_url);
        dir.mkdir();
        for (Node child : folder.getChildren())
        {
            if(CurrentNode instanceof File)
            {
                BufferedWriter out = new BufferedWriter(new FileWriter(local_url+"/"+child.FullName()));
            }
            else
            {
                Export((Folder) child,local_url);
            }
        }
    }
    
    
    public void Export(String URL) throws IOException, Exception
    {
        if(CurrentNode instanceof File)
        {
            BufferedWriter out = new BufferedWriter(new FileWriter(URL));
            out.write(ContFile());        
        }
        else
        {
            //Export((Folder)CurrentNode, URL);
            throw new Exception("So se soporta la Exportación de Directorios");
        }
    }
    
    
    private void Import(java.io.File dir1) throws Exception
    {
        
        if(dir1.isDirectory()) {
            /*
            String name = dir1.getName();
            MkDir(name, true);
            CambiarDir(name);
            java.io.File[] content = dir1.listFiles();
            for (java.io.File content1 : content) {
                Import(content1);
            }
                    */
            throw new Exception("So se soporta la Importación de Directorios");
        }
        else
        {
            FileReader in = new FileReader(dir1);
            StringBuilder contents = new StringBuilder();
            char[] buffer = new char[4096];
            int read = 0;
            do {
                contents.append(buffer, 0, read);
                read = in.read(buffer);
            } while (read >= 0);
            String[] split = dir1.getName().split("\\.");
            String fileName = new SimpleDateFormat("yyyyMMddhhmm'.txt'").format(new Date());
            File(split[0]+fileName, split[1], contents.toString(), Boolean.TRUE);
        }
        
    }
    
    public void Import(String URL, String target) throws FileNotFoundException, IOException, Exception
    {
        
        Node tempN = CurrentNode;
        Folder tempF = CurrentFolder;
        java.io.File dir1 = new java.io.File(URL);
        Import(dir1);
        Move(target, true);
        CurrentFolder = tempF;
        CurrentNode = tempN;
    } 
    public void Copy(String target) throws Exception
    {
        if(CurrentNode instanceof File)
        {
            String fileName = new SimpleDateFormat("yyyyMMddhhmm'.txt'").format(new Date());
            File tempF = (File) CurrentNode;
            File(tempF.getName()+fileName,tempF.getExtention(),ContFile(),true);
            Move(target, true);
        }
        else
        {
            throw new Exception("No se soporta la copia de directorios");
        }
    }
            
    
    
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progra3so.FileSytem;

/**
 *
 * @author José Pablo
 */
public abstract class Node {
    protected String name;
    protected final Properties properties;
    protected Folder parent;
    
    public Node(String name, Folder parent) {
        this.name = name;
        this.properties = new Properties();
        this.parent = parent;
    }
    
    public Folder getParent() {
        return parent;
    }

    public void setParent(Folder parent) {
        this.parent = parent;
    }       

    public Properties getProperties() {
        return properties;
    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String Path()
    {
        return parent.Path()+FullName();
    }
    
    public abstract String FullName();

    
    
    
}

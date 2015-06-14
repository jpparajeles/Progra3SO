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
    private String name;
    private final Properties properties;

    public Node(String name) {
        this.name = name;
        this.properties = new Properties();
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
    
}

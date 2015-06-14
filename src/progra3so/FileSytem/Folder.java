/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progra3so.FileSytem;

import java.util.List;

/**
 *
 * @author José Pablo
 */
public class Folder extends Node {
    private List<Node> children;

    public Folder(String name) {
        super(name);
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }
    
}

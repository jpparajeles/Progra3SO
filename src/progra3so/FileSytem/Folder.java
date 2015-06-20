/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progra3so.FileSytem;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author José Pablo
 */
public class Folder extends Node {
    protected final List<Node> children;

    public Folder(String name, Folder parent) {
        super(name, parent);
        children = new ArrayList<>();
    }

    public List<Node> getChildren() {
        return children;
    }

    @Override
    public String FullName() {
        return name;
    }

    
}

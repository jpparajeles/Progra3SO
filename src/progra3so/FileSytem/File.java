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
public class File extends Node {
    private String extention;
    private final List<Integer> sectorList;

    public File(String name, String Ext, Folder parent) {
        super(name, parent);
        sectorList = new ArrayList<>();
        extention = Ext;
    }

    public String getExtention() {
        return extention;
    }

    public void setExtention(String extention) {
        this.extention = extention;
    }

    public List<Integer> getSectorList() {
        return sectorList;
    }

    @Override
    public String FullName() {
        return name+extention;
    }
    
    
    
}

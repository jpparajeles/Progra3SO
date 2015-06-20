/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progra3so.FileSytem;

import java.util.Date;

/**
 *
 * @author José Pablo
 */
public class Properties {
    private int Size;
    private int DiskSize;
    private final Date CreationDate;
    private Date LastModifiedDate;

    public Properties() {
        CreationDate = new Date();
    }  
    
    public static Properties AntiFile(Properties p)
    {
        Properties anti = new Properties();
        anti.setSize(p.Size*-1);
        anti.setSize(p.DiskSize*-1);
        return anti;
    }
    
    public Date getCreationDate() {
        return CreationDate;
    }

    public int getSize() {
        return Size;
    }

    public void setSize(int Size) {
        this.Size = Size;
    }

    public int getDiskSize() {
        return DiskSize;
    }

    public void setDiskSize(int DiskSize) {
        this.DiskSize = DiskSize;
    }

    public Date getLastModifiedDate() {
        return LastModifiedDate;
    }

    public void setLastModifiedDate(Date LastModifiedDate) {
        this.LastModifiedDate = LastModifiedDate;
    }
    
    
}

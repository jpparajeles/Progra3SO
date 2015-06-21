/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progra3so.FileSytem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author José Pablo
 */
public class DiskManager {
    private Disk disk;
    private Stack<Integer> freesectors;
    
    
    public void Create(String url, int SegL, int SegQ) throws IOException
    {
        disk = new Disk(url, SegQ, SegL);
        freesectors = new Stack<>();
        for( int i=(SegQ-1); i>=0;i--)
        {
            freesectors.push(i);
        }
    }
    public List<Integer> WriteFile(String text) throws IOException
    {
        int qsectors = (int) Math.ceil((double)text.length()/(double)disk.getSectorL());
        List<Integer> ret = new ArrayList<>(qsectors);
        String sector;
        for(int i=0; i<qsectors; i++)
        {
            sector = "";
            int val = freesectors.pop();
            disk.errase(val);
            int lengS = disk.getSectorL();
            sector = text.substring(i*lengS, Math.min((i+1) * lengS,text.length()));
            disk.writeSector(val,sector);
            ret.add(val);
        }
        return ret;
    }
    
    public String ReadFile(List<Integer> sectors) throws IOException
    {
        String ret = "";
        for (Integer sector : sectors) {
            ret = ret.concat(disk.readSector(sector));
        }
        return ret;
    }
    public void DeleteFile(List<Integer> sectors) throws IOException
    {
        for (int i=sectors.size()-1; i>=0;i--)
        {
            disk.errase(sectors.get(i));
            freesectors.push(sectors.get(i));
        }
    }
    public int FreeSpace()
    {
        return freesectors.size()*disk.getSectorL();
    }
    
}

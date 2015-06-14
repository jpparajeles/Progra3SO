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
        for( int i =SegQ; i>0;i--)
        {
            freesectors.push(i);
        }
    }
    public List<Integer> WriteFile(String text) throws IOException
    {
        int qsectors = (int) Math.ceil((double)text.length()/(double)disk.getSectorL());
        List<Integer> ret = new ArrayList<>(qsectors);
        for(int i=0; i<qsectors; i++)
        {
            int val = freesectors.pop();
            disk.errase(val);
            disk.writeSector(val,text.substring(i, Math.min(i + disk.getSectorL(),text.length())));
            ret.add(val);
        }
        return ret;
    }
    
    public String ReadFile(List<Integer> sectors) throws IOException
    {
        String ret = "";
        for(int i = 0; i<sectors.size();i++)
        {
            ret = ret.concat(disk.readSector(i));
        }
        return ret;
    }
}

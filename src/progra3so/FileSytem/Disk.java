/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progra3so.FileSytem;

import static java.nio.file.StandardOpenOption.*;
import java.nio.file.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


/**
 *
 * @author José Pablo
 */
public class Disk {
    private final String url;
    private final int SectorQ;
    private final int SectorL;
    FileChannel fc;
    ByteBuffer deleter;
    
    

    public Disk(String url, int SectorQ, int SectorL) throws IOException
    {
        this.url = url;
        this.SectorQ = SectorQ;
        this.SectorL = SectorL;
        Path file = Paths.get(url);
        this.fc = FileChannel.open(file, READ, WRITE);
        
        String data = "";
        for (int j = 0; j<SectorL;j++)
        {
            data = data.concat(" ");
        }
        byte[] bytes = data.getBytes("UTF-8");
        this.deleter = ByteBuffer.wrap(bytes);
                
        format();
    }

    public String getUrl() {
        return url;
    }

    public int getSectorQ() {
        return SectorQ;
    }

    public int getSectorL() {
        return SectorL;
    }
    
    public String readSector(int sectorNum) throws IOException  
    {
        ByteBuffer copy = ByteBuffer.allocate(SectorL);
        fc.position(SectorL*sectorNum);
        int nread;
        do
        {
            nread = fc.read(copy);
        } while (nread != -1 && copy.hasRemaining());
        return new String(copy.array(),"UTF-8");
           
    }
    public void writeSector(int sectorNum, String data) throws IOException 
    {
        while(data.length()<SectorL)
        {
            data = data.concat(" ");
        }
        
        fc.position(sectorNum*SectorL);
        byte[] bytes = data.getBytes("UTF-8");
        ByteBuffer wrap = ByteBuffer.wrap(bytes);
        fc.write(wrap);
    }
    
    private void format() throws UnsupportedEncodingException, IOException 
    {
        fc.position(0);
        for (int i = 0; i<SectorQ;i++)
        {
            fc.write(deleter);
        }
    }
    
    public void errase(int sectorNum) throws IOException
    {
        fc.position(sectorNum*SectorL);
        fc.write(deleter);
    }
}

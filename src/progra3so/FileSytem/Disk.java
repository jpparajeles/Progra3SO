/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progra3so.FileSytem;

import static java.nio.file.StandardOpenOption.*;
import java.nio.file.*;
import java.io.*;


/**
 *
 * @author José Pablo
 */
public class Disk {
    private final String url;
    private final int SectorQ;
    private final int SectorL;
    private final OutputStream out;
    private final InputStream in;
    //private FileStream disk;

    public Disk(String url, int SectorQ, int SectorL) throws IOException {
        this.url = url;
        this.SectorQ = SectorQ;
        this.SectorL = SectorL;
        
        Path p = Paths.get(url);
        this.out = new BufferedOutputStream(Files.newOutputStream(p, CREATE, APPEND));
        this.in = new BufferedInputStream(Files.newInputStream(p, CREATE));
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
        byte b[] = null;
        in.read(b, sectorNum*SectorL, SectorL);
        return new String(b,"UTF-8");
    }
    public void writeSector(int sectorNum, String data) throws UnsupportedEncodingException, IOException
    {
        out.write(data.getBytes("UTF-8"), sectorNum*SectorL, SectorL);
    }
    
    private void format() throws UnsupportedEncodingException, IOException
    {
        String data = "";
        for (int j = 0; j<SectorL;j++)
        {
            data = data.concat(" ");
        }
        
        for(int i = 0; i<SectorQ;i++ )
        {
            out.write(data.getBytes("UTF-8"), i*SectorL, SectorL);
        }
    }
    
    public void errase(int sectorNum) throws IOException
    {
        String data = "";
        for (int j = 0; j<SectorL;j++)
        {
            data = data.concat(" ");
        }
        
        out.write(data.getBytes("UTF-8"), sectorNum*SectorL, SectorL);
    }
}

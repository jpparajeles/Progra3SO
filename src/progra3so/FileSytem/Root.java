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
public class Root extends Folder {
    
    private static boolean isinstance = false;
    private static Root instance;
    
    private Root() {
        super("Root",null);
    }
    
    public static Root getRoot()
    {
        if(!isinstance)
        {
            instance = new Root();
            isinstance = true;
        }
        return instance;
    }
    @Override
    public String Path()
    {
        return "/";
    }
    
    
    
}

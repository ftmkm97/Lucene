/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lucene;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;


public class DirectoryReader {
    
    //List of All Files In Directory
    public static ArrayList<String> files;
   
    public static ArrayList<String> getAllFilesInDirectory() throws IOException {
        
        files = new ArrayList<>();
        
        try(Stream<Path> paths = Files.walk(Paths.get(Configs.DOCUMENTS_DIR))) {
            
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    
                    files.add(filePath.toString());
                    
                }
            });
           
        } 
        
        return files;
   }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lucene;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 *
 * @author Amir Hossein
 */
public class FilesReader {
    
    public static List<String> getAllFilesContent() throws IOException {
        
        List<String> filesContent = new ArrayList<>();
        
        //Get All File Directory
        ArrayList<String> files = DirectoryReader.getAllFilesInDirectory();
        
        //All File Lines into one String
        StringBuilder fileContent = new StringBuilder();
        
        //Read All Files
        //Send Content of each file as List of String to Tokenizer
        for(String file : files)
        {
            List<String> lines = Files.readAllLines(Paths.get(file),Charset.forName("UTF-8"));
            
            
            //Iterate Through All line to Concatinate All lines Togther
            lines.forEach((line) -> {
                fileContent.append(line);
            });
            
            //Add file Content to List of Files Content
            filesContent.add(fileContent.toString());
            
            //Empty String Builder
            fileContent.delete(0, fileContent.length()-1);
        }
        
        return filesContent;
    }
    
    
    public static List<HashMap<String,String>> getAllFileTitleAndContent() throws IOException {
        
        List<HashMap<String,String>> filesTitleAndContent = new ArrayList<>();
        
        //Get All File Directory
        ArrayList<String> files = DirectoryReader.getAllFilesInDirectory();
        
        //All File Lines into one String
        StringBuilder fileContent = new StringBuilder();
        
        //Read All Files
        //Send Content of each file as List of String to Tokenizer
        for(String file : files)
        {
            List<String> lines = Files.readAllLines(Paths.get(file),Charset.forName("UTF-8"));
            
            HashMap<String,String> titleAndContent = new HashMap<>();
            
            //First Add Title Then Remove From Lines
            titleAndContent.put("title", lines.get(0));
            lines.remove(0);
            
            //Iterate Through All line to Concatinate All lines Togther
            lines.forEach((line) -> {
                fileContent.append(line);
            });
            
            titleAndContent.put("content", fileContent.toString());
            
            filesTitleAndContent.add(titleAndContent);
            
            //Empty String Builder
            fileContent.delete(0, fileContent.length()-1);
        }
        
        return filesTitleAndContent;
    }
    
}

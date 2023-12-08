/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lucene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 *
 * @author Amir Hossein
 */
public class Tokenizer {
    
    //Pair of Tonken->Files
    public HashMap<String, ArrayList<Integer>> token_files = new HashMap<>();
    
    //Create New id as int for each file
    //First file ID = 1
    public int fileID = 1;
    
    public Tokenizer() throws IOException {
      
        List<String> filesContent = FilesReader.getAllFilesContent();
        
        filesContent.forEach(fileContent -> {
            this.FileTokenizer(fileContent);
        });
        
    }
      
    
    public void FileTokenizer(String fileContent) {

        //Analyzer with no StopWords
        StandardAnalyzer analyzer = new StandardAnalyzer(CharArraySet.EMPTY_SET);
        
        //One File Tokens
        List<String> tokens = new ArrayList<>();
        
        try {
            TokenStream stream  = analyzer.tokenStream(null, fileContent);
            stream.reset();
            while (stream.incrementToken()) {
                
                tokens.add(stream.getAttribute(CharTermAttribute.class).toString());
            }
        } catch (IOException e) {
          // not thrown b/c we're using a string reader...
          throw new RuntimeException(e);
        }
        
        this.AddFileTokensToAllTokensSet(tokens);
        
       
    }
    
    public void AddFileTokensToAllTokensSet(List<String> tokens) {
        
        tokens.forEach((token) -> {
            //if token NOT found in tokens List
            //Add token to list
            if(!token_files.containsKey(token)) {
                
                ArrayList<Integer> fileList = new ArrayList<>();
                int cnt;
                
                fileList.add(fileID);
                 
                token_files.put(token, fileList);
                   
            }
            //Token is in the List of Token
            //So just Add File ID to it's list of files
            else {
                ArrayList<Integer> fileList = token_files.get(token);
                
                if(fileList.indexOf(fileID) == -1){
                    fileList.add(fileID);
                }
                      
            }
            
        });
        
        //New File ID
        this.fileID++;
    }
    
    
    
    
}

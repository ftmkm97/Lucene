/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lucene;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public final class StopWords {

    Tokenizer tokenizer;
    
    public List<String> stopWords = new ArrayList<>();
    
    public void createStopWords() throws IOException {
        
        this.tokenizer = new Tokenizer();
        this.FindStopWords();
        this.saveStopWordsInFile();
    }
    
    //Find StopWords
    public void FindStopWords() {
        
        //Hash Pair Of each Token and it's file occurence
        HashMap<String, ArrayList<Integer>> token_files = this.tokenizer.token_files;
        
        //Number of Documents
        float docsNumbers = this.tokenizer.fileID - 1;
        
        //Set Of all unique Tokens in All Files
        Set<String> tokens = token_files.keySet();
        
        tokens.forEach(token -> {
            
            //List of Files which token has been found in
            ArrayList<Integer> docList = token_files.get(token);
            
            
            if(docList.size() / docsNumbers >= 0.90) {
                
                stopWords.add(token);
            }
            
        });
        
    }
    
    public void saveStopWordsInFile() throws IOException {
        
        
        try (PrintWriter out = new PrintWriter(Configs.STOPWRODS_DIR , "UTF-8" )) {
            this.stopWords.forEach(stopword -> {
                
                out.println(stopword);
            });
        }
        System.out.println("finished stop words");
    }
    
    public List<String> readStopWords() throws IOException {
        
        return Files.readAllLines(Paths.get(Configs.STOPWRODS_DIR),Charset.forName("UTF-8"));
    }
    
    public static boolean isStopWordFileExists() {
        
        return (new File(Configs.STOPWRODS_DIR).exists());
    }
    
}

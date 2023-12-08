/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lucene;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 *
 * @author Amir Hossein
 */
public class IndexDocuments {
    
    public StandardAnalyzer createAnalyzer() throws IOException {
        
        //First Default StopWords {, ; ! ....}
        CharArraySet stopWords = CharArraySet.copy(StandardAnalyzer.STOP_WORDS_SET);
        stopWords.clear();
        
        //Get Docs StopWords
        StopWords docsStopWords = new StopWords();
        
        //Add Docs StopWords
        docsStopWords.readStopWords().forEach(word -> {
            stopWords.add(word);
        });
        
        //So Create Analyzer
        StandardAnalyzer analyzer = new StandardAnalyzer(stopWords);
        
        return analyzer;
    }
    
    
    
    public static boolean isIndexExists() throws IOException {
        
        Directory directory = FSDirectory.open( Paths.get(Configs.INDEX_DIR));
        
        return org.apache.lucene.index.DirectoryReader.indexExists(directory);
    }
    
    
    public void AddDocuments(IndexWriter iwriter) throws IOException {
        
        //Read All Files Titles And Content
        List<HashMap<String,String>> filesTitleAndContent = FilesReader.getAllFileTitleAndContent();
        
        filesTitleAndContent.forEach((HashMap<String, String> titleAndContent) -> {
            
            try {
                Document doc = new Document();
                doc.add(new Field("title", titleAndContent.get("title"), TextField.TYPE_STORED));
                doc.add(new Field("content", titleAndContent.get("content"), TextField.TYPE_STORED));
                
                iwriter.addDocument(doc);
                
            } catch (IOException ex) {
                Logger.getLogger(IndexDocuments.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        iwriter.close();
        
        
    }
    
    
    public void createDocumentIndecies() throws IOException {
        Path path = Paths.get(Configs.INDEX_DIR);
        Directory directory = FSDirectory.open(path);
        
        IndexWriterConfig config = new IndexWriterConfig(this.createAnalyzer());
        
        try (IndexWriter iwriter = new IndexWriter(directory, config)) {
            this.AddDocuments(iwriter);
        }
        System.out.println("indexing finished");
        directory.close();

    }
    
}

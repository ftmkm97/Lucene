/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lucene;

import java.io.IOException;
import java.nio.file.Paths;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 *
 * @author Amir Hossein
 */
public class GetDocument {
   
    Document doc;
     
    public GetDocument(int docId) throws IOException {
        
        Directory directory = FSDirectory.open( Paths.get(Configs.INDEX_DIR));
        
        org.apache.lucene.index.DirectoryReader ireader = org.apache.lucene.index.DirectoryReader.open(directory);
        
        IndexSearcher isearcher = new IndexSearcher(ireader);
        
        this.doc = isearcher.doc(docId);
    }
    
    public String getTitle() {
        
        return this.doc.getField("title").stringValue();
    }
    
    public String getContent() {
        
        return this.doc.getField("content").stringValue();
    }
}

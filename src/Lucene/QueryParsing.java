/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lucene;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.json.simple.JSONObject;


/**
 *
 * @author Amir Hossein
 */
public final class QueryParsing {
    
    ScoreDoc[] hits;
    
    int pageSize = 6;
    
    //Title Of DocHits
    public List<HashMap<String,Object>> docsInfo = new ArrayList<>();
    
    public int totalResultNumber;
        
    public QueryParsing(String queryStr, int pageNumber) throws IOException, ParseException {
        
        //Read Index Files
        Directory directory = FSDirectory.open( Paths.get(Configs.INDEX_DIR));
        DirectoryReader ireader = DirectoryReader.open(directory);
        IndexSearcher isearcher = new IndexSearcher(ireader);
        
        String[] fields = {"title", "content"};
        
        //Create Query
        MultiFieldQueryParser parser = new MultiFieldQueryParser(fields, this.createAnalyzer());
        
        Query query;
        
        query = parser.parse(queryStr);
        
        //Run Query
        this.hits = isearcher.search(query, 3000).scoreDocs; 
        
        this.totalResultNumber = this.hits.length;
        
        int startIndex = (pageNumber - 1) * pageSize;
        
        for(int i = startIndex; i < startIndex + pageSize; i++) {
            
            if(i == totalResultNumber)
                break;
            
            Document hitDoc = isearcher.doc(hits[i].doc);
            
            //Create DocInfo
            HashMap<String,Object> docInfo = new HashMap<>();
            
            docInfo.put("title", hitDoc.getField("title").stringValue());
            docInfo.put("summary", DocumentSummary.summariser(hitDoc.getField("content").stringValue(), queryStr));
            docInfo.put("content", hitDoc.getField("content").stringValue());
            docInfo.put("id", hits[i].doc);
            
            
            docsInfo.add(docInfo);
        }
        
        directory.close();
                
    
    }
    
    
    public StandardAnalyzer createAnalyzer() throws IOException {
        
        //First Default StopWords {, ; ! ....}
        CharArraySet stopWords = CharArraySet.copy(StandardAnalyzer.STOP_WORDS_SET);
        
        //Get Docs StopWords
        StopWords docsStopWords = new StopWords();
        
        //Add Docs StopWords
        docsStopWords.readStopWords().forEach(word -> {
            stopWords.add(word);
        });
        
        //So Create Analyzer
        StandardAnalyzer analyzer = new StandardAnalyzer();
        
        return analyzer;
    }
    
    
    public int getDocHits() {    
        return this.hits.length;
    }
    
    
    public List<String> getDocsInformation() {
        
        List<String> result = new ArrayList<>();
        
        JSONObject json = new JSONObject();
        
        this.docsInfo.forEach((t) -> {
            
            json.put("title", t.get("title"));
            json.put("summary", t.get("summary"));
            json.put("id", t.get("id"));
            
            result.add(json.toJSONString());
        });
        
        return result;
    }
    
}

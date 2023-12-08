/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lucene;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Amir Hossein
 */
public class DocumentSummary {
    
    public static String summariser(String docStr, String query) {
        
        String[] queryToken = query.split(" ");
        StringBuilder docSummary = new StringBuilder();
        
        String semiPattern = "(";
        String pattern     = "";
        
        for(String token : queryToken) {
            
            semiPattern += (token + "|");
        }
        semiPattern = semiPattern.substring(0, semiPattern.length() - 1) + ")";
        
        for(String token : queryToken) {
            
            pattern += ( "(.{1,30})?" + semiPattern ) ;
        }
        pattern += "(.{1,30})?";
        
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(docStr);
        
        int maxHit = 2, hit = 0;
        
        while(m.find() && hit < maxHit) {
           hit++;
           docSummary.append(m.group(0)).append("...");  
        }
        
        if(hit == 0){
            
            pattern = "(";
            
            for(String token : queryToken) {
            
                pattern += (token + "|");
            }
            
            pattern = pattern.substring(0, pattern.length() - 1) + ")";
            pattern = "(.{1,30})?" + pattern + "(.{1,30})?";
            
            r = Pattern.compile(pattern);
            m = r.matcher(docStr);
            
            while(m.find() && hit < maxHit) {
                hit++;
                docSummary.append(m.group(0)).append("...");  
            }
            
        }
       
        return docSummary.toString();
    }
}

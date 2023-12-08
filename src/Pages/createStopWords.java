/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pages;

import Lucene.StopWords;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;


/**
 *
 * @author Amir Hossein
 */
@WebServlet("/createStopWords")
public class createStopWords extends HttpServlet{
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        
        StopWords stopWords = new StopWords();
        
        //Json Object to Response
        JSONObject json = new JSONObject();
        res.setContentType("application/json; charset=utf-8");
 
           
        if(stopWords.isStopWordFileExists()) {

            json.put("FileExists", true);
            json.put("stopWords", stopWords.readStopWords());
            
        } else {
            
            stopWords.createStopWords();
            json.put("FileExists", false);
            json.put("stopWords", stopWords.readStopWords());
            
        }
        
        res.getWriter().print(json);
        
        
    }
}

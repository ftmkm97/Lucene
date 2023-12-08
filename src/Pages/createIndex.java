/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pages;

import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Lucene.IndexDocuments;
import Lucene.StopWords;
import java.io.IOException;

public class createIndex extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
        IndexDocuments indexDoc = new IndexDocuments();
        
        //Json Object to Response
        JSONObject json = new JSONObject();
        
        if(!StopWords.isStopWordFileExists()) {
            
            json.put("isStopWordsFileExists", false);
        }
        else if(indexDoc.isIndexExists()) {
            
            json.put("isIndexExists", true);
        }else {
            
            json.put("isIndexExists", false);
            indexDoc.createDocumentIndecies();
        }
        
        response.getWriter().print(json);
    }

   
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pages;

import Lucene.QueryParsing;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.lucene.queryparser.classic.ParseException;
import org.json.simple.JSONObject;

/**
 *
 * @author Amir Hossein
 */
@WebServlet("/queryParse")
public class qeuryParse extends HttpServlet {
    
    static String lastQueuryVal = "";


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
        //Get Query From Client
        this.lastQueuryVal = request.getParameter("query");
        
        //Json Object
        JSONObject json = new JSONObject();
        
        try {
        
            QueryParsing qp = new QueryParsing(this.lastQueuryVal, 1);
            
            json.put("result", qp.getDocsInformation());
            json.put("totalNumer", qp.totalResultNumber);
            response.getWriter().print(json);
            
           
        } catch (ParseException ex) {
            Logger.getLogger(qeuryParse.class.getName()).log(Level.SEVERE, null, ex);
        }
          
               
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
        //Get Query From Client
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        
        //Json Object
        JSONObject json = new JSONObject();
        
        try {
        
            QueryParsing qp = new QueryParsing(qeuryParse.lastQueuryVal, pageNumber);
            
            response.getWriter().print("{ \"totalNumber\": " + qp.totalResultNumber + ", \"result\": " +  qp.getDocsInformation() + "}");
            
           
        } catch (ParseException ex) {
            Logger.getLogger(qeuryParse.class.getName()).log(Level.SEVERE, null, ex);
        }
          
               
    }



}

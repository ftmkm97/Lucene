/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pages;

import Lucene.GetDocument;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

/**
 *
 * @author Amir Hossein
 */
@WebServlet("/getDocument")
public class getDocument extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
    }
    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
        int docId = Integer.parseInt(request.getParameter("docId"));
        
        GetDocument doc = new GetDocument(docId);
        
        JSONObject json = new JSONObject();
        
        json.put("title",    doc.getTitle());
        json.put("content",  doc.getContent());

        
        response.getWriter().print(json);
        
        
    }

}

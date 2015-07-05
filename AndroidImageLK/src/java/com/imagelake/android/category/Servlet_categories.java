/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagelake.android.category;

import com.imagelake.control.CategoriesDAOImp;
import com.imagelake.model.Categories;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author RLN
 */
public class Servlet_categories extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        
        PrintWriter out = response.getWriter();
        
        CategoriesDAOImp categoryDAOImp = new CategoriesDAOImp();
        JSONArray ja = new JSONArray();
        ArrayList<Categories> categoriesList = (ArrayList<Categories>) categoryDAOImp.listAllCategories();
        if(!categoriesList.isEmpty()){
            for (Categories cc : categoriesList) {
                JSONObject jo = new JSONObject();
                jo.put("id", cc.getCategory_id());
                jo.put("cat", cc.getCategory());
                ja.add(jo);
                
                
            }
            out.write("json="+ja.toJSONString());
        }else{
            
            JSONObject jo = new JSONObject();
                jo.put("id", 0);
                jo.put("cat", "No category found.");
                ja.add(jo);
                out.write("json="+ja.toJSONString());
            
        }
    }
}

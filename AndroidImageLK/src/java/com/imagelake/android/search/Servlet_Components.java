/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagelake.android.search;

import com.imagelake.control.CategoriesDAOImp;
import com.imagelake.control.CreditsDAOImp;
import com.imagelake.control.KeyWordsDAOImp;
import com.imagelake.control.UserDAOImp;
import com.imagelake.model.Categories;
import com.imagelake.model.Credits;
import com.imagelake.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
public class Servlet_Components extends HttpServlet {

    KeyWordsDAOImp kwdi;
    CategoriesDAOImp cdi;
    UserDAOImp udi;
    CreditsDAOImp crdi;
    JSONArray jja;

    public Servlet_Components() {
        kwdi = new KeyWordsDAOImp();
        cdi = new CategoriesDAOImp();
        udi = new UserDAOImp();
        crdi = new CreditsDAOImp();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        try {

            jja = new JSONArray();

            JSONArray kja = kwdi.getJSONAllKeyWords();
            jja.add(kja);
            
            List<Categories> categories = cdi.listAllCategories();
            JSONArray cja = new JSONArray();
            for (Categories c : categories) {
                JSONObject jo = new JSONObject();
                jo.put("id", c.getCategory_id());
                jo.put("cat", c.getCategory());
                cja.add(jo);
            }
            jja.add(cja);

            JSONArray sja = new JSONArray();
            List<User> li = new UserDAOImp().listAllSellers();
            if (!li.isEmpty()) {

                for (User u : li) {
                    JSONObject jo = new JSONObject();
                    jo.put("id", u.getUser_id());
                    jo.put("nm", u.getUser_name());
                    sja.add(jo);
                }
            }
            jja.add(sja);

            JSONArray ja = new JSONArray();
            List<Credits> clist = new CreditsDAOImp().getCreditList();
            for (Credits c : clist) {
                JSONObject jo = new JSONObject();
                jo.put("width", c.getWidth());
                jo.put("height", c.getHeight());
                ja.add(jo);
            }
            jja.add(ja);
            System.out.println(jja.toJSONString());
            out.write("json="+jja.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

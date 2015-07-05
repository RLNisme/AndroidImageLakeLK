/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagelake.android.purchasemanagement;

import com.imagelake.control.CartDAOImp;
import com.imagelake.control.CategoriesDAOImp;
import com.imagelake.control.DBFactory;
import com.imagelake.control.ImagesDAOImp;
import com.imagelake.control.UserDAOImp;
import com.imagelake.model.Cart;
import com.imagelake.model.CartHasImages;
import com.imagelake.model.Categories;
import com.imagelake.model.Images;
import com.imagelake.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class Servlet_purchaseViseImages extends HttpServlet {

    JSONArray ja;
    UserDAOImp udi;
    CartDAOImp cdi;
    ImagesDAOImp idi;
    CategoriesDAOImp cadi;
    List<CartHasImages> li;

    public Servlet_purchaseViseImages() {
        cdi = new CartDAOImp();
        udi = new UserDAOImp();
        idi = new ImagesDAOImp();
        cadi = new CategoriesDAOImp();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        try {
            String type = request.getParameter("type");
            if (type != null && !type.equals("")) {
                if (type.equals("all")) {
                    ja = new JSONArray();
                    li = new CartDAOImp().listdownloadedImages();

                    Images im;
                    int i = 1;
                    Categories c = null;
                    Cart ct = null;
                    User buyer = null;
                    User Seller = null;

                    if (!li.isEmpty()) {
                        for (CartHasImages cartHasImages : li) {
                            JSONObject jo = new JSONObject();
                            jo.put("cid", cartHasImages.getImg_id());
                            im = idi.getImageDetail(cartHasImages.getImg_id());
                            jo.put("title", im.getTitle());
                            jo.put("credit", cartHasImages.getCredits());
                            c = cadi.getCategory(im.getCategories_category_id());
                            jo.put("category", c.getCategory());
                            jo.put("date", cartHasImages.getDate());
                            ct = cdi.getACart(cartHasImages.getCart_id());
                            buyer = udi.getUser(ct.getUser_id());
                            Seller = udi.getUser(im.getUser_user_id());
                            jo.put("buyer", buyer.getUser_name());
                            jo.put("seller", Seller.getUser_name());
                            ja.add(jo);
                        }
                        System.out.println(ja.toJSONString());
                        out.write("json=" + ja.toJSONString());
                    } else {
                        out.write("msg=No item found.");
                    }
                } else if (type.equals("all_sort")) {
                    ja = new JSONArray();

                    List<Categories> cli = cadi.listAllCategories();
                    JSONArray catAr = new JSONArray();
                    for (Categories categories : cli) {
                        JSONObject jo = new JSONObject();
                        jo.put("id", categories.getCategory_id());
                        jo.put("category", categories.getCategory());
                        catAr.add(jo);
                    }

                    ja.add(catAr);

                    List<User> usli = udi.listSellersBuyers();
                    JSONArray usAr = new JSONArray();
                    for (User user : usli) {
                        JSONObject jo = new JSONObject();
                        jo.put("bid", user.getUser_id());
                        jo.put("bun", user.getUser_name());
                        usAr.add(jo);
                    }

                    ja.add(usAr);

                    List<User> sli = udi.listAllSellers();
                    JSONArray seAr = new JSONArray();
                    for (User u : sli) {
                        JSONObject jo = new JSONObject();
                        jo.put("sid", u.getUser_id());
                        jo.put("sun", u.getUser_name());
                        seAr.add(jo);
                    }

                    ja.add(seAr);

                    System.out.println(ja.toJSONString());
                    out.write("json=" + ja.toJSONString());
                } else if (type.equals("sort")) {

                    ja = new JSONArray();

                    String imid, date, cat, buyer, seller, date2;
                    imid = request.getParameter("imid");
                    date = request.getParameter("date");
                    date2 = request.getParameter("date2");
                    cat = request.getParameter("cat");
                    buyer = request.getParameter("buy");
                    seller = request.getParameter("sell");

                    System.out.println("imgid:"+imid);
                    System.out.println("date:"+date);
                    System.out.println("date2:"+date2);
                    System.out.println("cat:"+cat);
                    System.out.println("buyer:"+buyer);
                    System.out.println("seller:"+seller);
                    
                    String sql = "SELECT SQL_CALC_FOUND_ROWS * FROM cart_has_images";
                    String where = "";
                    String tr = "";
                    
                    if(imid.equals("0")){
                        imid ="";
                    }
                    
                    if(cat.equals("0")){
                        cat ="";
                    }
                    if(buyer.equals("0")){
                        buyer = "";
                    }
                    if(seller.equals("0")){
                        seller = "";
                    }

                    if (!imid.equals("") && !imid.equals(null)) {
                        if (where.equals("")) {
                            where += " WHERE is_purchase!='0' AND  is_purchase!='2' AND ";
                            sql += where;
                        }
                        sql += " img_id='" + Integer.parseInt(imid) + "'";
                        if (!date.equals("") && !date.equals(null) && !date2.equals("") && !date2.equals(null) || !cat.equals("") && !cat.equals(null) || !buyer.equals("") && !buyer.equals(null) || !seller.equals("") && !seller.equals(null)) {
                            sql += " AND ";
                        }
                    }
                    if (!date.equals("") && !date.equals(null) && !date2.equals("") && !date2.equals(null)) {
                        if (where.equals("")) {
                            where += " WHERE is_purchase!='0' AND  is_purchase!='2' AND ";
                            sql += where;
                        }
                        String[] dt = date.split("-");
                        String[] dt2 = date2.split("-");
                        String orDate = dt[2] + "-" + dt[1] + "-" + dt[0];
                        String orDate2 = dt2[2] + "-" + dt2[1] + "-" + dt2[0];
                        System.out.println("date=" + orDate);
                        sql += " date BETWEEN '" + orDate + "' AND '" + orDate2 + "'";
                        if (!cat.equals("") && !cat.equals(null) || !buyer.equals("") && !buyer.equals(null) || !seller.equals("") && !seller.equals(null)) {
                            sql += " AND ";
                        }
                    }
                    if (!cat.equals("") && !cat.equals(null)) {
                        if (where.equals("")) {
                            where += " WHERE is_purchase!='0' AND  is_purchase!='2' AND ";
                            sql += where;
                        }
                        sql += "img_id IN(SELECT images_id FROM images WHERE categories_category_id='" + cat + "')";
                        if (!buyer.equals("") && !buyer.equals(null) || !seller.equals("") && !seller.equals(null)) {
                            sql += " AND ";
                        }
                    }
                    if (!buyer.equals("") && !buyer.equals(null)) {
                        if (where.equals("")) {
                            where += " WHERE is_purchase!='0' AND  is_purchase!='2' AND ";
                            sql += where;
                        }
                        sql += "cart_id IN(SELECT cart_id FROM cart WHERE user_id='" + buyer + "')";
                        if (!seller.equals("") && !seller.equals(null)) {
                            sql += " AND ";
                        }
                    }
                    if (!seller.equals("") && !seller.equals(null)) {
                        if (where.equals("")) {
                            where += " WHERE is_purchase!='0' AND  is_purchase!='2' AND ";
                            sql += where;
                        }
                        sql += "img_id IN(SELECT images_id FROM images WHERE user_user_id='" + seller + "')";

                    }

                    sql += " ORDER BY date DESC";

                    System.out.println("sql:"+sql);
                    
                    Images im;
                    int i = 1;
                    Categories cc = null;
                    Cart ct = null;
                    User buy = null;
                    User Seller = null;

                    try {

                        PreparedStatement ps = DBFactory.getConnection().prepareStatement(sql);
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                           
                            JSONObject jo = new JSONObject();
                            jo.put("cid", rs.getInt(2));
                            im = idi.getImageDetail(rs.getInt(2));
                            jo.put("title", im.getTitle());
                            jo.put("credit", rs.getInt(7));
                            cc = cadi.getCategory(im.getCategories_category_id());
                            jo.put("category", cc.getCategory());
                            jo.put("date", rs.getDate(6));
                            ct = cdi.getACart(rs.getInt(5));
                            buy = udi.getUser(ct.getUser_id());
                            Seller = udi.getUser(im.getUser_user_id());
                            jo.put("buyer", buy.getUser_name());
                            jo.put("seller", Seller.getUser_name());
                            ja.add(jo);
                        }

                        out.write("json="+ja.toJSONString());
                    } catch (Exception e) {
                        e.printStackTrace();
                        out.write("msg=Internal server error,Please try again later.");
                    }

                }
            } else {
                out.write("msg=Internal server error,Please try again later.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.write("msg=Internal server error,Please try again later.");
        }
    }
}

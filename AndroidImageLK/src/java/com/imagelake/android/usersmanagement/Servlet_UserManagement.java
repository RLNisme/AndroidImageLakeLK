/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagelake.android.usersmanagement;

import com.imagelake.control.AdminHasNotificationDAOImp;
import com.imagelake.control.AdminNotificationDAOImp;
import com.imagelake.control.DBFactory;
import com.imagelake.control.UserDAOImp;
import com.imagelake.model.AdminNotification;
import com.imagelake.model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author RLN
 */
public class Servlet_UserManagement extends HttpServlet {

    UserDAOImp udi;

    public Servlet_UserManagement() {
        udi = new UserDAOImp();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        try {

            String type = request.getParameter("type");
            JSONArray ja;
            if (type != null && !type.trim().equals("")) {

                if (type.equals("all")) {
                    ja = new JSONArray();
                    List<User> li = udi.listSellersBuyers();
                    if (!li.isEmpty()) {

                        for (User u : li) {
                            JSONObject jo = new JSONObject();
                            jo.put("id", u.getUser_id());
                            jo.put("user_name", u.getUser_name());
                            jo.put("f_name", u.getFirst_name());
                            jo.put("l_name", u.getLast_name());
                            jo.put("em", u.getEmail());
                            jo.put("phn", u.getPhone());
                            jo.put("state", u.getState());
                            jo.put("type", u.getUser_type());
                            ja.add(jo);
                        }
                        out.write("json=" + ja.toJSONString());
                    } else {
                        out.write("msg=No item found.");
                    }
                } else if (type.equals("sort")) {
                    ja = new JSONArray();

                    String uid, date, buy, sell, name;
                    uid = request.getParameter("uid");
                    date = request.getParameter("date");
                    buy = request.getParameter("buy");
                    sell = request.getParameter("sell");
                    name = request.getParameter("name");

                    System.out.println("uid:" + uid);
                    System.out.println("date:" + date);
                    System.out.println("buy:" + buy);
                    System.out.println("sell:" + sell);

                    String sql = "SELECT * FROM user WHERE ";

                    if (!sell.equals("false") || !buy.equals("false")) {

                        if (!uid.equals("") && !uid.equals(null)) {
                            System.out.println("user id=" + uid);
                            sql += " user_id='" + Integer.parseInt(uid) + "'";
                            if (!date.trim().equals("") && !date.equals(null) || !buy.equals("") && !buy.equals(null) || !sell.equals("") && !sell.equals(null) || !name.equals("") && !name.equals(null)) {
                                sql += " AND ";
                            }
                        }
                        if (!date.equals("") && !date.equals(null)) {
                            String[] dt = date.split("-");
                            String orDate = dt[2] + "-" + dt[1] + "-" + dt[0];
                            System.out.println("date=" + orDate);

                            sql += " date='" + orDate + "'";
                            if (!buy.equals("") && !buy.equals(null) || !sell.equals("") && !sell.equals(null) || !name.equals("") && !name.equals(null)) {
                                sql += " AND ";
                            }

                        }

//                    if (!name.equals("") && !name.equals(null)) {
//                        System.out.println("Name=" + name);
//                        sql += "user_name='" + name + "'";
//                        if (!buy.equals("") && !buy.equals(null) || !sell.equals("") && !sell.equals(null)) {
//                            sql += " AND ";
//                        }
//                    }
                        if (!buy.equals("") && !buy.equals(null) && !buy.equals("false")) {
                            System.out.println("buyers=" + buy);
                            sql += "user_type_user_type_id='" + 3 + "'";
                            if (!sell.equals("") && !sell.equals(null) && !sell.equals("false")) {
                                sql += " OR ";
                            }

                        }
                        if (!sell.equals("") && !sell.equals(null) && !sell.equals("false")) {
                            System.out.println("Sellers=" + sell);
                            sql += "user_type_user_type_id='" + 2 + "'";

                        }
                        System.out.println("sql=" + sql);
                        PreparedStatement ps;

                        try {
                            ps = DBFactory.getConnection().prepareStatement(sql);
                            ResultSet rs = ps.executeQuery();
                            while (rs.next()) {
                                JSONObject jo = new JSONObject();
                                jo.put("id", rs.getInt(1));
                                jo.put("user_name", rs.getString(2));
                                jo.put("f_name", rs.getString(3));
                                jo.put("l_name", rs.getString(4));
                                jo.put("em", rs.getString(5));
                                jo.put("phn", rs.getString(12));
                                jo.put("state", rs.getInt(19));
                                jo.put("type", rs.getInt(21));
                                ja.add(jo);
                            }

                            out.write("json=" + ja.toJSONString());
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("error sql");
                            out.write("msg=Internal server error,Please try again later.");
                        }

                    } else {
                        System.out.println("no item");
                        out.write("msg=No item found.");
                    }
                } else if (type.equals("update")) {
                    String id = request.getParameter("id");
                    String uid = request.getParameter("uid");
                    String state = request.getParameter("state");

                    System.out.println("id:"+id);
                    System.out.println("uid:"+uid);
                    System.out.println("state:"+state);
                    
                    String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

                    
                    User user = (User) udi.getUser(Integer.parseInt(uid));
                    

                    boolean ok = udi.updateUserState(Integer.parseInt(uid), Integer.parseInt(state));
                    String un = udi.getUn(Integer.parseInt(uid));
                    if (ok) {
//                        AdminNotification a = new AdminNotification();
//                        a.setUser_id(user.getUser_id());
//                        a.setDate(timeStamp);
//
//                        a.setShow(1);
//                        if (state.equals("2")) {
//                            a.setType(2);
//                            String not = "Admin " + user.getUser_name() + " has blocked user " + un;
//                            a.setNotification(not);
//                        } else if (state.equals("1")) {
//                            a.setType(4);
//                            String not = "Admin " + user.getUser_name() + " has activated user " + un;
//                            a.setNotification(not);
//                        }
//
//                        int an = new AdminNotificationDAOImp().insertNotificaation(a);
//                        System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPP " + an);
//
//                        boolean kl = new AdminHasNotificationDAOImp().insertNotification(udi.notMeList(user.getUser_id()), an, 1);
//                        if (kl) {
//                            out.write("ok");
//                        } else {
//                            out.write("no");
//                        }
                          out.write("msg=Successful...");
                    } else {
                        out.write("msg=Unable to complete the action,Please try agin later.");
                    }

                }

            } else {
                System.out.println("type");
                out.write("msg=Internal server error,Please try again later.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error");
            out.write("msg=Internal server error,Please try again later.");
        }

    }
}

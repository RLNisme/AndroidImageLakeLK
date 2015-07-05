/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Lakmal
 */
public class UserDAOImp implements UserDAO{

    @Override
    public boolean searchUserName(String un) {
        try {
            String sql="SELECT * FROM user WHERE user_name=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,un);
             System.out.println("class");
            ResultSet rs=ps.executeQuery();
            while(rs.next()) {
                
                if (rs.getString(2).equals(un)) {
                     System.out.println("false");
                    return false;
                   
                } 
                
               
            }
             System.out.println("true");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
             return false;
        }
    }

    @Override
    public boolean insertUser(User u) {
        boolean ok=false;
        try {
            String sql="INSERT INTO "
                    + "user(user_name,first_name,last_name,email,password,street_add_1,street_add_2,"
                    + "city,state_province,zip_postal_code,phone,fax,com_name,website,com_phone,com_fax,date,"
                    + "state_state_id,country_country_id,user_type_user_type_id,current_country_current_country_id) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            
             Connection con=DBFactory.getConnection();
             PreparedStatement ps=con.prepareStatement(sql);
             ps.setString(1,u.getUser_name());
             ps.setString(2, u.getFirst_name());
             ps.setString(3, u.getLast_name());
             ps.setString(4,u.getEmail());
             ps.setString(5,u.getPassword());
             ps.setString(6,u.getStreet_add_1());
             ps.setString(7,u.getStreet_add_2());
             ps.setString(8,u.getCity());
             ps.setString(9,u.getState_province());
             ps.setString(10,u.getZip_postal_code());
             ps.setString(11,u.getPhone());
             ps.setString(12,u.getFax());
             ps.setString(13,u.getCom_name());
             ps.setString(14,u.getWebsite());
             ps.setString(15,u.getCom_phone());
             ps.setString(16,u.getCom_fax());
             ps.setString(17,u.getDate());
             ps.setInt(18,u.getState());
             ps.setInt(19,u.getBilling_country());
             ps.setInt(20,u.getUser_type());
             ps.setInt(21, u.getCurrent_country_id());
             
             System.out.println("in class");
             int i=ps.executeUpdate();
             if (i>0) {
                ok=true;
            } else {
                ok=false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public boolean serachEmail(String em) {
        
        try {
            String sql="SELECT * FROM user WHERE email=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1, em);
            System.out.println("in email method");
            ResultSet rs=ps.executeQuery();
            
            while (rs.next()) {                
                if (rs.getString(5).equals(em)) {
                    System.out.println("false");
                    return false;
                }
                   
                
            }
            System.out.println("True");
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
    }

    @Override
    public User searchEmailWithUserId(String em) {
        User user=null;
        try {
            String sql="SELECT * FROM user WHERE email=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1, em);
            
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {   
                    user=new User();
                    user.setUser_id(rs.getInt(1));
                    user.setUser_name(rs.getString(2));
                    user.setFirst_name(rs.getString(3));
                    user.setLast_name(rs.getString(4));
                    user.setEmail(rs.getString(5));
                    user.setPassword(rs.getString(6));
                    user.setStreet_add_1(rs.getString(7));
                    user.setStreet_add_2(rs.getString(8));
                    user.setCity(rs.getString(9));
                    user.setState_province(rs.getString(10));
                    user.setZip_postal_code(rs.getString(11));
                    user.setPhone(rs.getString(12));
                    user.setFax(rs.getString(13));
                    user.setCom_name(rs.getString(14));
                    user.setWebsite(rs.getString(15));
                    user.setCom_phone(rs.getString(16));
                    user.setCom_fax(rs.getString(17));
                    user.setDate(rs.getString(18));
                    user.setState(rs.getInt(19));
                    user.setBilling_country(rs.getInt(20));
                    user.setUser_type(rs.getInt(21));
                    user.setCurrent_country_id(rs.getInt(22));
                    System.out.println("user");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User searchUserNameWithUserID(String un) {
       User user=null;
        try {
          String sql="SELECT * FROM user WHERE user_name=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1, un);
            
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {   
                    user=new User();
                    user.setUser_id(rs.getInt(1));
                    user.setUser_name(rs.getString(2));
                    user.setFirst_name(rs.getString(3));
                    user.setLast_name(rs.getString(4));
                    user.setEmail(rs.getString(5));
                    user.setPassword(rs.getString(6));
                    user.setStreet_add_1(rs.getString(7));
                    user.setStreet_add_2(rs.getString(8));
                    user.setCity(rs.getString(9));
                    user.setState_province(rs.getString(10));
                    user.setZip_postal_code(rs.getString(11));
                    user.setPhone(rs.getString(12));
                    user.setFax(rs.getString(13));
                    user.setCom_name(rs.getString(14));
                    user.setWebsite(rs.getString(15));
                    user.setCom_phone(rs.getString(16));
                    user.setCom_fax(rs.getString(17));
                    user.setDate(rs.getString(18));
                    user.setState(rs.getInt(19));
                    user.setBilling_country(rs.getInt(20));
                    user.setUser_type(rs.getInt(21));
                    user.setCurrent_country_id(rs.getInt(22));
                    System.out.println("user"); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User searchPassword(String pw, int uid) {
        System.out.println(pw+"  "+uid);
        User user=null;
        try {
            String sql="SELECT * FROM user WHERE user_id=? AND password=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, uid);
            ps.setString(2, pw);
            ResultSet rs=ps.executeQuery();
            
            while (rs.next()) {    
                user=new User();
                System.out.println(rs.getInt(1));
                System.out.println(rs.getString(6));
                    user.setUser_id(rs.getInt(1));
                    user.setUser_name(rs.getString(2));
                    user.setFirst_name(rs.getString(3));
                    user.setLast_name(rs.getString(4));
                    user.setEmail(rs.getString(5));
                    user.setPassword(rs.getString(6));
                    user.setStreet_add_1(rs.getString(7));
                    user.setStreet_add_2(rs.getString(8));
                    user.setCity(rs.getString(9));
                    user.setState_province(rs.getString(10));
                    user.setZip_postal_code(rs.getString(11));
                    user.setPhone(rs.getString(12));
                    user.setFax(rs.getString(13));
                    user.setCom_name(rs.getString(14));
                    user.setWebsite(rs.getString(15));
                    user.setCom_phone(rs.getString(16));
                    user.setCom_fax(rs.getString(17));
                    user.setDate(rs.getString(18));
                    user.setState(rs.getInt(19));
                    user.setBilling_country(rs.getInt(20));
                    user.setUser_type(rs.getInt(21));
                    user.setCurrent_country_id(rs.getInt(22));
                    System.out.println("user"); 
                    System.gc();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    
   
    
    
    
    @Override
    public User searchSignIn(String un, String pw) {
        System.out.println(un+" "+pw);
        User user=null;
        try {
            String sql="SELECT * FROM user WHERE user_name=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1, un);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                    System.out.println("rs.next");
                if (rs.getString(6).equals(pw)) {
                    user = new User();
                    user.setUser_id(rs.getInt(1));
                    user.setUser_name(rs.getString(2));
                    user.setFirst_name(rs.getString(3));
                    user.setLast_name(rs.getString(4));
                    user.setEmail(rs.getString(5));
                    user.setPassword(rs.getString(6));
                    user.setStreet_add_1(rs.getString(7));
                    user.setStreet_add_2(rs.getString(8));
                    user.setCity(rs.getString(9));
                    user.setState_province(rs.getString(10));
                    user.setZip_postal_code(rs.getString(11));
                    user.setPhone(rs.getString(12));
                    user.setFax(rs.getString(13));
                    user.setCom_name(rs.getString(14));
                    user.setWebsite(rs.getString(15));
                    user.setCom_phone(rs.getString(16));
                    user.setCom_fax(rs.getString(17));
                    user.setDate(rs.getString(18));
                    user.setState(rs.getInt(19));
                    user.setBilling_country(rs.getInt(20));
                    user.setUser_type(rs.getInt(21));
                    user.setCurrent_country_id(rs.getInt(22));
                    System.out.println("user");
                    
                }
                else {
                System.out.println("rs.next false");
                return user;
            }
             
            } 
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("exception");
            return user;
        }
        System.out.println("return user");
        return user;
    }

    @Override
    public boolean updatePublicData(User user) {
            boolean ok=false;
        try {
            String sql="UPDATE user SET user_name=?,first_name=?,last_name=?,email=? WHERE user_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,user.getUser_name());
            ps.setString(2,user.getFirst_name());
            ps.setString(3,user.getLast_name());
            ps.setString(4,user.getEmail());
            ps.setInt(5, user.getUser_id());
            
            int i=ps.executeUpdate();
            if (i>0) {
                ok=true;
            } else {
                ok=false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public User getUser(int user_id) {
        User user=new User();
        try {
            String sql="SELECT * FROM user WHERE user_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, user_id);
            ResultSet rs=ps.executeQuery();
            
            while (rs.next()) {                
                user.setUser_id(rs.getInt(1));
                user.setUser_name(rs.getString(2));
                    user.setFirst_name(rs.getString(3));
                    user.setLast_name(rs.getString(4));
                    user.setEmail(rs.getString(5));
                    user.setPassword(rs.getString(6));
                    user.setStreet_add_1(rs.getString(7));
                    user.setStreet_add_2(rs.getString(8));
                    user.setCity(rs.getString(9));
                    user.setState_province(rs.getString(10));
                    user.setZip_postal_code(rs.getString(11));
                    user.setPhone(rs.getString(12));
                    user.setFax(rs.getString(13));
                    user.setCom_name(rs.getString(14));
                    user.setWebsite(rs.getString(15));
                    user.setCom_phone(rs.getString(16));
                    user.setCom_fax(rs.getString(17));
                    user.setDate(rs.getString(18));
                    user.setState(rs.getInt(19));
                    user.setBilling_country(rs.getInt(20));
                    user.setUser_type(rs.getInt(21));
                    user.setCurrent_country_id(rs.getInt(22));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean updatePrivateData(User user) {
        
        boolean ok=false;
        try {
            String sql="UPDATE user SET first_name=?,last_name=?,"
                    + "user_type_user_type_id=? WHERE user_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1, user.getFirst_name());
            ps.setString(2, user.getLast_name());
            ps.setInt(3, user.getUser_type());
            ps.setInt(4, user.getUser_id());
                   
            int i=ps.executeUpdate();
            if (i>0) {
              ok=true;
            } else {
               ok=false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public boolean updatePassword(String pw,int uid) {
        boolean ok=false;
        try {
            String sql="UPDATE user SET password=? WHERE user_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,pw);
            ps.setInt(2, uid);
            
            int i=ps.executeUpdate();
            if(i>0){
                ok=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    public boolean updatePhoneNo(int uid, String phn) {
         boolean ok=false;
        try {
            String sql="UPDATE user SET phone=? WHERE user_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,phn);
            ps.setInt(2, uid);
            
            int i=ps.executeUpdate();
            if(i>0){
                ok=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    
    
    
    @Override
    public boolean searchUpdatinUserName(String un,int uid) {
        boolean ok=false;
        try {
            String sql="SELECT * FROM user WHERE user_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, uid);
            
            
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                if (un.equals(rs.getString(2))) {
                    System.out.println(un+"="+rs.getString(2));
                    ok=true;
                } else {
                    boolean bun=searchUserName(un);
                    if (bun) {
                        ok=true;
                    } else {
                        ok=false;
                    }
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public boolean searchUpateEmail(String em, int uid) {
        boolean ok=false;
        try {
            String sql="SELECT * FROM user WHERE user_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, uid);
            
            
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                if (em.equals(rs.getString(5))) {
                    System.out.println(em+"="+rs.getString(5));
                    ok=true;
                } else {
                    boolean bem=serachEmail(em);
                    if (bem) {
                        ok=true;
                    } else {
                        ok=false;
                    }
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    public boolean updateFnLn(String fn,String ln,int uid){
        boolean ok=false;
        try {
            String sql="UPDATE user SET first_name=?,last_name=? WHERE user_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1, fn);
            ps.setString(2, ln);
            ps.setInt(3, uid);
            int i=ps.executeUpdate();
            if(i>0){
                ok=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public List<User> getUsers(int state) {
        List<User> li=new ArrayList<User>();
        try {
            String sql="SELECT * FROM user WHERE user_type_user_type_id=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, state);
            ResultSet rs=ps.executeQuery();
            
            while (rs.next()) {  
                User user=new User();
                 user.setUser_id(rs.getInt(1));
                user.setUser_name(rs.getString(2));
                    user.setFirst_name(rs.getString(3));
                    user.setLast_name(rs.getString(4));
                    user.setEmail(rs.getString(5));
                    user.setPassword(rs.getString(6));
                    user.setStreet_add_1(rs.getString(7));
                    user.setStreet_add_2(rs.getString(8));
                    user.setCity(rs.getString(9));
                    user.setState_province(rs.getString(10));
                    user.setZip_postal_code(rs.getString(11));
                    user.setPhone(rs.getString(12));
                    user.setFax(rs.getString(13));
                    user.setCom_name(rs.getString(14));
                    user.setWebsite(rs.getString(15));
                    user.setCom_phone(rs.getString(16));
                    user.setCom_fax(rs.getString(17));
                    user.setDate(rs.getString(18));
                    user.setState(rs.getInt(19));
                    user.setBilling_country(rs.getInt(20));
                    user.setUser_type(rs.getInt(21));
                    user.setCurrent_country_id(rs.getInt(22));
                    System.gc();
                    li.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return li;
    }

    @Override
    public List<User> listSellersBuyers() {
       List<User> li=new ArrayList<User>();
        try {
            String sql="SELECT * FROM user WHERE user_type_user_type_id=2 OR user_type_user_type_id=3";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                User user=new User();
                 user.setUser_id(rs.getInt(1));
                user.setUser_name(rs.getString(2));
                    user.setFirst_name(rs.getString(3));
                    user.setLast_name(rs.getString(4));
                    user.setEmail(rs.getString(5));
                    user.setPassword(rs.getString(6));
                    user.setStreet_add_1(rs.getString(7));
                    user.setStreet_add_2(rs.getString(8));
                    user.setCity(rs.getString(9));
                    user.setState_province(rs.getString(10));
                    user.setZip_postal_code(rs.getString(11));
                    user.setPhone(rs.getString(12));
                    user.setFax(rs.getString(13));
                    user.setCom_name(rs.getString(14));
                    user.setWebsite(rs.getString(15));
                    user.setCom_phone(rs.getString(16));
                    user.setCom_fax(rs.getString(17));
                    user.setDate(rs.getString(18));
                    user.setState(rs.getInt(19));
                    user.setBilling_country(rs.getInt(20));
                    user.setUser_type(rs.getInt(21));
                    user.setCurrent_country_id(rs.getInt(22));
                    System.gc();
                    li.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return li;
    }

    @Override
    public List<User> listSellers() {
        List<User> li=new ArrayList<User>();
        try {
            String sql="SELECT * FROM user WHERE user_type_user_type_id=2";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                User user=new User();
                 user.setUser_id(rs.getInt(1));
                user.setUser_name(rs.getString(2));
                    user.setFirst_name(rs.getString(3));
                    user.setLast_name(rs.getString(4));
                    user.setEmail(rs.getString(5));
                    user.setPassword(rs.getString(6));
                    user.setStreet_add_1(rs.getString(7));
                    user.setStreet_add_2(rs.getString(8));
                    user.setCity(rs.getString(9));
                    user.setState_province(rs.getString(10));
                    user.setZip_postal_code(rs.getString(11));
                    user.setPhone(rs.getString(12));
                    user.setFax(rs.getString(13));
                    user.setCom_name(rs.getString(14));
                    user.setWebsite(rs.getString(15));
                    user.setCom_phone(rs.getString(16));
                    user.setCom_fax(rs.getString(17));
                    user.setDate(rs.getString(18));
                    user.setState(rs.getInt(19));
                    user.setBilling_country(rs.getInt(20));
                    user.setUser_type(rs.getInt(21));
                    user.setCurrent_country_id(rs.getInt(22));
                    System.gc();
                    li.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return li;
    }
    public List<User> listAllSellers() {
        List<User> li=new ArrayList<User>();
        try {
            String sql="SELECT * FROM user WHERE user_type_user_type_id!=3";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                User user=new User();
                 user.setUser_id(rs.getInt(1));
                user.setUser_name(rs.getString(2));
                    user.setFirst_name(rs.getString(3));
                    user.setLast_name(rs.getString(4));
                    user.setEmail(rs.getString(5));
                    user.setPassword(rs.getString(6));
                    user.setStreet_add_1(rs.getString(7));
                    user.setStreet_add_2(rs.getString(8));
                    user.setCity(rs.getString(9));
                    user.setState_province(rs.getString(10));
                    user.setZip_postal_code(rs.getString(11));
                    user.setPhone(rs.getString(12));
                    user.setFax(rs.getString(13));
                    user.setCom_name(rs.getString(14));
                    user.setWebsite(rs.getString(15));
                    user.setCom_phone(rs.getString(16));
                    user.setCom_fax(rs.getString(17));
                    user.setDate(rs.getString(18));
                    user.setState(rs.getInt(19));
                    user.setBilling_country(rs.getInt(20));
                    user.setUser_type(rs.getInt(21));
                    user.setCurrent_country_id(rs.getInt(22));
                    System.gc();
                    li.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return li;
    }

    @Override
    public String getUserRate() {
        
        StringBuffer sb=null;
        sb=new StringBuffer("{'userreg':{"); 
           sb.append("'name':'User Rate',");
           sb.append("'rate':[");
        try {
            String sql="select count(user_id),DATE(date) from user group by DATE(date)";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            int max=0;
            while (rs.next()) {       
                max++;
                System.out.println("U1: "+rs.getDouble(1));
                System.out.println("U2: "+rs.getString(2));
                
                if(rs.isLast()){
                    sb.append("{'date':'"+rs.getString(2)+"','value':'"+rs.getDouble(1)+"'}");
                }else{
                    sb.append("{'date':'"+rs.getString(2)+"','value':'"+rs.getDouble(1)+"'},");
                }
                    
            }
            sb.append("],");
            if(max%2==0){
            sb.append("'max':'"+max+"'");
            }else{
            sb.append("'max':'"+max+++"'");    
            }
            sb.append("}");
            sb.append("}");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public List<User> listAll() {
       List<User> li=new ArrayList<User>();
        try {
            String sql="SELECT * FROM user";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                User user=new User();
                 user.setUser_id(rs.getInt(1));
                user.setUser_name(rs.getString(2));
                    user.setFirst_name(rs.getString(3));
                    user.setLast_name(rs.getString(4));
                    user.setEmail(rs.getString(5));
                    user.setPassword(rs.getString(6));
                    user.setStreet_add_1(rs.getString(7));
                    user.setStreet_add_2(rs.getString(8));
                    user.setCity(rs.getString(9));
                    user.setState_province(rs.getString(10));
                    user.setZip_postal_code(rs.getString(11));
                    user.setPhone(rs.getString(12));
                    user.setFax(rs.getString(13));
                    user.setCom_name(rs.getString(14));
                    user.setWebsite(rs.getString(15));
                    user.setCom_phone(rs.getString(16));
                    user.setCom_fax(rs.getString(17));
                    user.setDate(rs.getString(18));
                    user.setState(rs.getInt(19));
                    user.setBilling_country(rs.getInt(20));
                    user.setUser_type(rs.getInt(21));
                    user.setCurrent_country_id(rs.getInt(22));
                    System.gc();
                    li.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return li;
    }

    @Override
    public boolean updateUserState(int uid, int state) {
        boolean ok=false;
        try {
            String sql="UPDATE user SET state_state_id=? WHERE user_id=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, state);
            ps.setInt(2,uid);
            int i=ps.executeUpdate();
            if(i>0){
                ok=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public String listSubAdmins() {
         StringBuffer sb=null;
         sb=new StringBuffer("{'subadmin':{"); 
           sb.append("'name':'SubAdmin',");
           sb.append("'details':[");    
        
        try {
            String sql="SELECT * FROM user WHERE user_type_user_type_id!=2 AND user_type_user_type_id!=3 AND user_type_user_type_id!=1";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                
                    System.gc();
                    if(rs.isLast()){
                        sb.append("{'id':'"+rs.getInt(1)+"','un':'"+rs.getString(2)+"','em':'"+rs.getString(5)+"','state':'"+rs.getInt(19)+"','type':'"+rs.getInt(21)+"'}");
                    }else{
                        sb.append("{'id':'"+rs.getInt(1)+"','un':'"+rs.getString(2)+"','em':'"+rs.getString(5)+"','state':'"+rs.getInt(19)+"','type':'"+rs.getInt(21)+"'},");
                    }
                     
            }
            sb.append("],");
            sb.append("'states':[");
                
                    String sql2="SELECT * FROM state";
                    PreparedStatement ps2=DBFactory.getConnection().prepareStatement(sql2);
                    ResultSet rs2=ps2.executeQuery();
                    while (rs2.next()) {    
                        if(rs2.isLast()){
                            sb.append("{'value':'"+rs2.getString(1)+"','state':'"+rs2.getString(2)+"'}");
                        }else{
                            sb.append("{'value':'"+rs2.getString(1)+"','state':'"+rs2.getString(2)+"'},");
                        }
                        
                    }
                    
            sb.append("],");
            sb.append("'types':[");
                String sql3="SELECT * FROM user_type WHERE user_type_id>3";
                    PreparedStatement ps3=DBFactory.getConnection().prepareStatement(sql3);
                    ResultSet rs3=ps3.executeQuery();
                    while (rs3.next()) {
                        if(rs3.isLast()){
                             sb.append("{'value':'"+rs3.getString(1)+"','type':'"+rs3.getString(2)+"'}");
                        }else{
                             sb.append("{'value':'"+rs3.getString(1)+"','type':'"+rs3.getString(2)+"'},");
                        }
                       
                    }
            sb.append("]");
            sb.append("}");
            sb.append("}");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public boolean updateUserType(int uid, int type) {
       boolean ok=false;
        System.out.println("======"+uid);
        System.out.println("======"+type);
        try {
            String sql="UPDATE user SET user_type_user_type_id=? WHERE user_id=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1,type);
            ps.setInt(2,uid);
            int i=ps.executeUpdate();
            if(i>0){
                ok=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public boolean deleteSubAdmin(int uid) {
        boolean ok=false;
        try {
            String sql="DELETE FROM user WHERE user_id=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, uid);
            int i=ps.executeUpdate();
            if(i>0){
                ok=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public String getUsertype() {
        
        StringBuffer sb=null;
                try {
                sb=new StringBuffer("{'usertype':{"); 
                sb.append("'name':'all',");
                sb.append("'alltype':[");    
            
                 String sql3="SELECT * FROM user_type WHERE user_type_id>3";
                    PreparedStatement ps3=DBFactory.getConnection().prepareStatement(sql3);
                    ResultSet rs3=ps3.executeQuery();
                    while (rs3.next()) {
                        if(rs3.isLast()){
                             sb.append("{'value':'"+rs3.getString(1)+"','type':'"+rs3.getString(2)+"'}");
                        }else{
                             sb.append("{'value':'"+rs3.getString(1)+"','type':'"+rs3.getString(2)+"'},");
                        }
                       
                    }
                sb.append("]");
                sb.append("}");
                sb.append("}");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
 
    //----------------------------------------
    public boolean addUsertType(String ut){
            boolean ok=false;
            try {
            String sql="INSERT INTO user_type(user_type) VALUES(?)";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setString(1, ut);
            int i=ps.executeUpdate();
            if(i>0){
                ok=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
            return ok;
    }

    @Override
    public String getUn(int uid) {
        String un=null;
        try {
            String sql="SELECT user_name FROM user WHERE user_id=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, uid);
            
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {                
               un=rs.getString(1);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return un;
    }

    @Override
    public List<String> listAdminsIDs() {
        List<String> li=new ArrayList<String>();
        
        try {
            String sql="SELECT user_id FROM user WHERE user_type_user_type_id!=2 AND user_type_user_type_id!=3";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                li.add(rs.getInt(1)+"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return li;
    }
    
    public List<String> notMeList(int uid){
        List<String> li=new ArrayList<String>();
        
        try {
            String sql="SELECT user_id FROM user WHERE user_type_user_type_id!=2 AND user_type_user_type_id!=3 AND user_id!=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, uid);
            
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                li.add(rs.getInt(1)+"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return li;
    }

    @Override
    public int allUsersCount() {
        int count=0;
        try {
            String sql="SELECT COUNT(user_id) FROM user";
            Statement s=DBFactory.getConnection().createStatement();
            ResultSet rs=s.executeQuery(sql);
            while (rs.next()) {                
                count+=rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public String getSubadminList() {
        
        JSONArray all = new JSONArray();
       JSONArray user = new JSONArray();
       JSONArray page = new JSONArray();
       String re = "";
        try {
            
            String sql="SELECT * FROM user WHERE user_type_user_type_id > 3 ";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                
                JSONObject jo = new JSONObject();
                jo.put("id", rs.getInt(1));
                            jo.put("user_name", rs.getString(2));                           
                            jo.put("em", rs.getString(5));
                            jo.put("phn", rs.getString(12));
                            jo.put("state", rs.getInt(19));
                            jo.put("type", getAdminUserType(rs.getInt(21)));
                            jo.put("type_id",rs.getInt(21));
                            user.add(jo);
            
            }
            rs.close();
            
            String sql2="SELECT state FROM interfaces WHERE url=?";
            ps=DBFactory.getConnection().prepareStatement(sql2);
            ps.setString(1, "RegAdmin.jsp");
            
            rs=ps.executeQuery();
            JSONObject jo2 = new JSONObject();
            while (rs.next()) { 
                jo2.put("regstate", rs.getInt(1));
                
                
            }
            rs.close();
            String sql3="SELECT state FROM interfaces WHERE url=?";
            ps=DBFactory.getConnection().prepareStatement(sql2);
            
            ps.setString(1, "UpdateAdmin.jsp");
            rs=ps.executeQuery();
            JSONObject jo3 = new JSONObject();
            while (rs.next()) { 
                jo3.put("upstate", rs.getInt(1));
                
                
            }
            rs.close();
            
            all.add(user);
            all.add(jo2);
            all.add(jo3);
            
            re = "json="+all.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            re = "msg=Internal server error,Please try again later.";
        }
        
        return re;
    }

    public String getAdminUserType(int id) {
            String type ="";
         try {
            
            String sql="SELECT * FROM user_type WHERE user_type_id=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                type = rs.getString(2);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            
        }
         return type;
    }

    public String getJSONAllUserTypeList() {
        JSONArray ja = new JSONArray();
       
        try {
            String sql="SELECT * FROM user_type WHERE user_type_id>3";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                JSONObject jo = new JSONObject();
                jo.put("tid", rs.getInt(1));
                jo.put("ttype", rs.getString(2));
                ja.add(jo);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ja.toJSONString();
    }
    
    
    
    
}

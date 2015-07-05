/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.KeyWords;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Lakmal
 */
public class KeyWordsDAOImp implements KeyWordsDAO{

    @Override
    public boolean addKeyWords(int images_images_id, String keyword) {
        System.out.println("Add key words:"+images_images_id);
        boolean ok=false;
        try {
            String sql="INSERT INTO key_words(key_word,images_images_id)VALUES(?,?)";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,keyword);
            ps.setInt(2, images_images_id);
            int i= ps.executeUpdate();
            if(i>0){
                ok=true;
            }else{
                ok=false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("addKeyWords:"+ok);
        return ok;
    }

    @Override
    public boolean getKeyWords(String keyword,int images_images_id) {
       boolean ok=false;
        try {
            String sql="SELECT * FROM key_words WHERE key_word=? AND images_images_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1, keyword);
            ps.setInt(2, images_images_id);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {                
                ok=false;
                
                
            }else{
                ok=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public List<KeyWords> getkeyWordList(int images_images_id) {
        List<KeyWords> list=new ArrayList<KeyWords>();
        try {
            String sql="SELECT * FROM key_words WHERE images_images_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, images_images_id);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                KeyWords kw=new KeyWords();
                kw.setImages_images_id(rs.getInt(3));
                kw.setKey_word(rs.getString(2));
                kw.setKey_words_id(rs.getInt(1));
                list.add(kw);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean removeKeyWord(int key_word_id) {
        boolean ok=false;
        try {
            String sql="DELETE FROM key_words WHERE key_words_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, key_word_id);
            int i=ps.executeUpdate();
            if(i>0){
                ok=true;
            }else{
                ok=false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public List<KeyWords> listKeyWords(String key) {
        List<KeyWords> lis=new ArrayList<KeyWords>();
        
        try {
            String sql="SELECT * FROM key_words WHERE key_word LIKE '%"+key+"%' ORDER BY images_images_id DESC";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {  
                System.gc();
                KeyWords w=new KeyWords();
                w.setKey_words_id(rs.getInt(1));
                w.setKey_word(rs.getString(2));
                w.setImages_images_id(rs.getInt(3));
                System.out.println("image id:"+rs.getInt(3));
                lis.add(w);
            }
        } catch (Exception e) {
            e.printStackTrace();
                    
        }
        return lis;
    }

    @Override
    public String getAllKeyWords() {
        StringBuffer sb = null;
        try {
            sb = new StringBuffer("{'keywords':{");
            sb.append("'words':[");
                
                String sql="SELECT * FROM key_words GROUP BY key_word";
                PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
                ResultSet rs=ps.executeQuery();
                while (rs.next()) {
                if(rs.isLast()){    
                sb.append("{'id':'"+rs.getInt(1)+"','keyword':'"+rs.getString(2)+"','imgid':'"+rs.getInt(3)+"'}");
                }else{
                sb.append("{'id':'"+rs.getInt(1)+"','keyword':'"+rs.getString(2)+"','imgid':'"+rs.getInt(3)+"'},");    
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
    
    
    public JSONArray getJSONAllKeyWords(){
        JSONArray ja = new JSONArray();
        try {
             String sql="SELECT * FROM key_words GROUP BY key_word";
                PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
                ResultSet rs=ps.executeQuery();
                while (rs.next()) {
                    JSONObject jo = new JSONObject();
                    jo.put("id", rs.getInt(1));
                    jo.put("kw", rs.getString(2));
                    ja.add(jo);
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ja;
    }
    
    
}

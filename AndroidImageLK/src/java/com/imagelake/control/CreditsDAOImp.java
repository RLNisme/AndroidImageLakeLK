/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagelake.control;

import com.imagelake.model.Credits;
import com.imagelake.model.SliceImage;
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
public class CreditsDAOImp implements CreditsDAO {

    @Override
    public Credits getCreditDetails(int credit_id) {
        Credits c = new Credits();
        try {
            String sql = "SELECT * FROM credits WHERE credit_id=?";
            Connection con = DBFactory.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, credit_id);
            ResultSet rs = ps.executeQuery();
            System.gc();
            while (rs.next()) {
                c = new Credits();
                c.setCredit_id(rs.getInt(1));
                c.setCredits(rs.getInt(2));
                c.setSize(rs.getString(3));
                c.setWidth(rs.getInt(4));
                c.setHeight(rs.getInt(5));
                c.setState(rs.getInt(6));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    @Override
    public List<Credits> getCreditList() {
        List<Credits> clis = new ArrayList<Credits>();
        try {
            String sql = "SELECT * FROM credits";
            PreparedStatement ps = DBFactory.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Credits c = new Credits();
                c.setCredit_id(rs.getInt(1));
                c.setCredits(rs.getInt(2));
                c.setSize(rs.getString(3));
                c.setWidth(rs.getInt(4));
                c.setHeight(rs.getInt(5));
                c.setState(rs.getInt(6));
                clis.add(c);
                System.gc();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clis;
    }

    @Override
    public List<SliceImage> getTypeSliceList(int type, int state) {
        List<SliceImage> li = new ArrayList<SliceImage>();
        try {
            String sql = "SELECT * FROM slice_image WHERE type_id=? AND status=?";
            PreparedStatement ps = DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, type);
            ps.setInt(2, state);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SliceImage sl = new SliceImage();
                sl.setId(rs.getInt(1));
                sl.setCredit_id(rs.getInt(2));
                sl.setType_id(rs.getInt(3));
                sl.setStatus(rs.getInt(4));
                li.add(sl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return li;
    }

    @Override
    public boolean updateCredits(Credits c) {
        boolean ok = false;
        try {
            String sql = "UPDATE credits SET credits=?,size=?,width=?,height=?,state=? WHERE credit_id=?";
            PreparedStatement ps = DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, c.getCredits());
            ps.setString(2, c.getSize());
            ps.setInt(3, c.getWidth());
            ps.setInt(4, c.getHeight());
            ps.setInt(5, c.getState());
            ps.setInt(6, c.getCredit_id());

            int i = ps.executeUpdate();
            if (i > 0) {
                ok = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public boolean insertCredits(Credits c) {
        boolean ok = false;
        try {
            String sql = "INSERT INTO credits(credits,size,width,height,state) VALUE(?,?,?,?,?)";
            PreparedStatement ps = DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, c.getCredits());
            ps.setString(2, c.getSize());
            ps.setInt(3, c.getWidth());
            ps.setInt(4, c.getHeight());
            ps.setInt(5, c.getState());

            int i = ps.executeUpdate();
            if (i > 0) {
                ok = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    //-----------------------
    public String getAllSliceDetails() {
        StringBuffer sb = null;
        try {
            String sql = "SELECT * FROM slice_type";
            PreparedStatement ps = DBFactory.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            sb = new StringBuffer("{'slicetype':{");
            sb.append("'name':'all',");
            sb.append("'type':[");
            while (rs.next()) {
                if (rs.isLast()) {
                    sb.append("{'id':'" + rs.getInt(1) + "','width':'" + rs.getString(2) + "','height':'" + rs.getString(3) + "'}");
                } else {
                    sb.append("{'id':'" + rs.getInt(1) + "','width':'" + rs.getString(2) + "','height':'" + rs.getString(3) + "'},");
                }
            }
            sb.append("],");
            sb.append("'slices':[");
            String sql2 = "SELECT * FROM credits WHERE state='1'";
            PreparedStatement ps2 = DBFactory.getConnection().prepareStatement(sql2);
            ResultSet rs2 = ps2.executeQuery();

            while (rs2.next()) {
                if (rs2.isLast()) {
                    sb.append("{'id':'" + rs2.getInt(1) + "','credits':'" + rs2.getInt(2) + "','size':'" + rs2.getString(3) + "','width':'" + rs2.getInt(4) + "','height':'" + rs2.getInt(5) + "'}");
                } else {
                    sb.append("{'id':'" + rs2.getInt(1) + "','credits':'" + rs2.getInt(2) + "','size':'" + rs2.getString(3) + "','width':'" + rs2.getInt(4) + "','height':'" + rs2.getInt(5) + "'},");
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

    public String getFullSliceDetails() {
        String sb = "";
        JSONArray ja = new JSONArray();
        try {
            
          
            String sql2 = "SELECT * FROM credits WHERE state='1'";
            PreparedStatement ps2 = DBFactory.getConnection().prepareStatement(sql2);
            ResultSet rs2 = ps2.executeQuery();

           
                while (rs2.next()) {
                       JSONObject jo = new JSONObject();
                       jo.put("id", rs2.getInt(1));
                       jo.put("credits", rs2.getInt(2));
                       jo.put("size", rs2.getString(3));
                       jo.put("width", rs2.getInt(4));
                       jo.put("height", rs2.getInt(5));
                       ja.add(jo);
                }
                sb = "json="+ja.toJSONString();
            
        } catch (Exception e) {
            e.printStackTrace();
            sb ="msg=Internal server error,Please try again later.";
        }
        return sb;
    }
    
    public boolean insertSliceImage(int cid, int typeid, int sta) {
        boolean ok = false;

        try {
            boolean check = checkSliceImage(cid, typeid);
            if (!check) {
                String sql = "INSERT INTO slice_image(credit_id,type_id,status) VALUES(?,?,?)";
                PreparedStatement ps = DBFactory.getConnection().prepareStatement(sql);
                ps.setInt(1, cid);
                ps.setInt(2, typeid);
                ps.setInt(3, sta);

                int i = ps.executeUpdate();
                if (i > 0) {
                    ok = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    public boolean checkSliceImage(int cid, int typeid) {
        boolean ok = false;

        try {
            String sql = "SELECT * FROM slice_image WHERE credit_id=? AND type_id=? AND status='1'";
            PreparedStatement ps = DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, cid);
            ps.setInt(2, typeid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ok = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    
    public boolean removeSliceImage(int id){
        boolean ok=false;
        try {
            String sql="UPDATE slice_image SET status='2' WHERE id=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            int i=ps.executeUpdate();
            if(i>0){
                ok=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }
    
    //------------------------
    @Override
    public int getCredit(int subid) {
        int i=0;
        try {
            String sql="SELECT credits FROM credits WHERE credit_id=?";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, subid);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                i=rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }
    
    
}

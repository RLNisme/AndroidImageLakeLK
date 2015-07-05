/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.Images;
import com.imagelake.model.ImagesSub;
import com.imagelake.uploads.ColorMatching;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public class ImagesDAOImp implements ImagesDAO{

    @Override
    public List<Images> listAllImages() {
        ArrayList<Images> imagesubList=new ArrayList<Images>();
        
        try {
            String sql="SELECT * FROM images";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            
            while (rs.next()) {                
                Images i=new Images();
                i.setCategories_category_id(rs.getInt(19));
                i.setColor_1(rs.getString(5));
                i.setColor_2(rs.getString(6));
                i.setColor_3(rs.getString(7));
                i.setColor_4(rs.getString(8));
                i.setColor_5(rs.getString(9));
                i.setColor_6(rs.getString(10));
                i.setColor_7(rs.getString(11));
                i.setColor_8(rs.getString(12));
                i.setColor_9(rs.getString(13));
                i.setDate(rs.getString(16));
                i.setDominate_color(rs.getString(4));
                i.setImage_state_image_state_id(rs.getInt(20));
                i.setImages_id(rs.getInt(1));
                i.setImg_blob(rs.getBinaryStream(14));
                i.setImg_url(rs.getString(15));
                i.setKey_wrods(rs.getString(3));
                i.setTitle(rs.getString(2));
                i.setUser_user_id(rs.getInt(18));
                i.setView(rs.getInt(17));
                
                imagesubList.add(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imagesubList;
        
    }
    public List<Images> listAllactiveImages() {
        ArrayList<Images> imagesubList=new ArrayList<Images>();
        
        try {
            String sql="SELECT * FROM images WHERE image_state_image_state_id='1'";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            
            ResultSet rs=ps.executeQuery();
            
            while (rs.next()) {                
                Images i=new Images();
                i.setCategories_category_id(rs.getInt(19));
                i.setColor_1(rs.getString(5));
                i.setColor_2(rs.getString(6));
                i.setColor_3(rs.getString(7));
                i.setColor_4(rs.getString(8));
                i.setColor_5(rs.getString(9));
                i.setColor_6(rs.getString(10));
                i.setColor_7(rs.getString(11));
                i.setColor_8(rs.getString(12));
                i.setColor_9(rs.getString(13));
                i.setDate(rs.getString(16));
                i.setDominate_color(rs.getString(4));
                i.setImage_state_image_state_id(rs.getInt(20));
                i.setImages_id(rs.getInt(1));
                i.setImg_blob(rs.getBinaryStream(14));
                i.setImg_url(rs.getString(15));
                i.setKey_wrods(rs.getString(3));
                i.setTitle(rs.getString(2));
                i.setUser_user_id(rs.getInt(18));
                i.setView(rs.getInt(17));
                
                imagesubList.add(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imagesubList;
        
    }

    @Override
    public List<ImagesSub> getImagedescription(int image_id) {
        System.out.println("getImageDescription image id:"+image_id);
        List<ImagesSub> imSub=new ArrayList<ImagesSub>();
        try {
            String sql="SELECT * FROM sub_images WHERE images_images_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, image_id);
            ResultSet rs=ps.executeQuery();
            
            while (rs.next()) {                
                ImagesSub i=new ImagesSub();
                i.setCredits(rs.getInt(6));
                i.setDimention(rs.getString(4));
                i.setImages_image_id(rs.getInt(9));
                i.setImg_blob(rs.getBinaryStream(2));
                i.setImg_url(rs.getString(3));
                i.setPrice(rs.getDouble(5));
                i.setSize_long(rs.getLong(7));
                i.setSize_string(rs.getString(8));
                i.setSub_images_id(rs.getInt(1));
                System.out.println(rs.getString(3)+"ooooo");
                imSub.add(i);
            }
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imSub;
        
    }

    
    
    @Override
    public Images getImageDetail(int image_id) {
        Images i=null;
        try {
            String sql="SELECT * FROM images WHERE images_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, image_id);
            ResultSet rs=ps.executeQuery();
            
            while (rs.next()) {   
                i=new Images();
                i.setCategories_category_id(rs.getInt(19));
                i.setColor_1(rs.getString(5));
                i.setColor_2(rs.getString(6));
                i.setColor_3(rs.getString(7));
                i.setColor_4(rs.getString(8));
                i.setColor_5(rs.getString(9));
                i.setColor_6(rs.getString(10));
                i.setColor_7(rs.getString(11));
                i.setColor_8(rs.getString(12));
                i.setColor_9(rs.getString(13));
                i.setDate(rs.getString(16));
                i.setDominate_color(rs.getString(4));
                i.setImage_state_image_state_id(rs.getInt(20));
                i.setImages_id(rs.getInt(1));//
                i.setImg_blob(rs.getBinaryStream(14));
                i.setImg_url(rs.getString(15));
                i.setKey_wrods(rs.getString(3));
                i.setTitle(rs.getString(2));
                i.setUser_user_id(rs.getInt(18));
                i.setView(rs.getInt(17));
                
                
                System.out.println("query"+i.getUser_user_id());
                System.out.println("query"+rs.getInt(18));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    

    @Override
    public int getLastImageId() {
       int count=0;
        try {
            String sql="SELECT images_id FROM images";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public boolean insertImage(Images image) {
        boolean ok=false;
        try {
            
          String sql="INSERT INTO images(title,key_words,dominate_color,color_1,color_2,color_3,color_4,color_5,color_6,color_7,color_8,color_9"
                  + ",img_blob,img_url,date,view,user_user_id,categories_category_id,image_state_image_state_id)"
                  + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";  
          Connection con=DBFactory.getConnection();
          PreparedStatement ps=con.prepareStatement(sql);
          ps.setString(1, image.getTitle());
          ps.setString(2, image.getKey_wrods());
          ps.setString(3, image.getDominate_color());
          ps.setString(4, image.getColor_1());
          ps.setString(5, image.getColor_2());
          ps.setString(6, image.getColor_3());
          ps.setString(7, image.getColor_4());
          ps.setString(8, image.getColor_5());
          ps.setString(9, image.getColor_6());
          ps.setString(10, image.getColor_7());
          ps.setString(11, image.getColor_8());
          ps.setString(12, image.getColor_9());
          ps.setBinaryStream(13, image.getImg_blob());
          ps.setString(14, image.getImg_url());
          ps.setString(15, image.getDate());
          ps.setInt(16, image.getView());
          ps.setInt(17, image.getUser_user_id());
          ps.setInt(18, image.getCategories_category_id());
          ps.setInt(19, image.getImage_state_image_state_id());
          
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
    public boolean insertImageContent(Images image,ImagesSub sub) {
        
        boolean ok=false;
        try {
            String sql="UPDATE images SET dominate_color=?,color_1=?,color_2=?,color_3=?,color_4=?,color_5=?,color_6=?,color_7=?,color_8=?,color_9=?"
                    + " WHERE images_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1, image.getDominate_color());
            ps.setString(2, image.getColor_1());
            ps.setString(3, image.getColor_2());
            ps.setString(4, image.getColor_3());
            ps.setString(5, image.getColor_4());
            ps.setString(6, image.getColor_5());
            ps.setString(7, image.getColor_6());
            ps.setString(8, image.getColor_7());
            ps.setString(9, image.getColor_8());
            ps.setString(10, image.getColor_9());
            
            ps.setInt(11, image.getImages_id());
            
            String sql2="INSERT INTO sub_images(img_blob,img_url,dimention,price,credits,size_long,size_string,Images_images_id)"
                    + "VALUES(?,?,?,?,?,?,?,?);";
            PreparedStatement ps2=con.prepareStatement(sql2);
            ps2.setBinaryStream(1,sub.getImg_blob());
            ps2.setString(2, sub.getImg_url());
            ps2.setString(3, sub.getDimention());
            ps2.setDouble(4, sub.getPrice());
            ps2.setInt(5, sub.getCredits());
            ps2.setLong(6, sub.getSize_long());
            ps2.setString(7, sub.getSize_string());
            ps2.setInt(8, sub.getImages_image_id());
            
            int i=ps.executeUpdate();
            int j=ps2.executeUpdate();
            if(i>0 && j>0){
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
    public int getImageId(String title) {
       int id=0;
        try {
            String sql="SELECT images_id FROM images WHERE title=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,title);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                id=rs.getInt(1);
                System.out.println(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public List<Images> searchByColor(String rgb) {
        ArrayList<Images> imagesList=new ArrayList<Images>();
        System.out.println("))))))))))))))))))))))))0"+rgb);
        String[] rg=rgb.split(",");
        
                System.gc();
                
                try {
                String sql="SELECT * FROM images WHERE dominate_color=? OR color_1=? OR color_2=? OR color_3=? OR color_4=? OR color_5=? OR "
                        + "color_6=? OR color_7=? OR color_8=? OR color_9=?";
//                String sql1="SELECT * FROM images WHERE dominate_color OR color_1 LIKE '%"+rgb+"%'";
                Connection con=DBFactory.getConnection();
                PreparedStatement ps=con.prepareStatement(sql);
                ps.setString(1, rgb);
                ps.setString(2, rgb);
                ps.setString(3, rgb);
                ps.setString(4, rgb);
                ps.setString(5, rgb);
                ps.setString(6, rgb);
                ps.setString(7, rgb);
                ps.setString(8, rgb);
                ps.setString(9, rgb);
                ps.setString(10, rgb);
                ResultSet rs=ps.executeQuery();
                while(rs.next()){
                    System.gc();
                    Images i=new Images();
                    i.setCategories_category_id(rs.getInt(19));
                    i.setColor_1(rs.getString(5));
                    i.setColor_2(rs.getString(6));
                    i.setColor_3(rs.getString(7));
                    i.setColor_4(rs.getString(8));
                    i.setColor_5(rs.getString(9));
                    i.setColor_6(rs.getString(10));
                    i.setColor_7(rs.getString(11));
                    i.setColor_8(rs.getString(12));
                    i.setColor_9(rs.getString(13));
                    i.setDate(rs.getString(16));
                    i.setDominate_color(rs.getString(4));
                    i.setImage_state_image_state_id(rs.getInt(20));
                    i.setImages_id(rs.getInt(1));
                    i.setImg_blob(rs.getBinaryStream(14));
                    i.setImg_url(rs.getString(15));
                    i.setKey_wrods(rs.getString(3));
                    i.setTitle(rs.getString(2));
                    i.setUser_user_id(rs.getInt(18));
                    System.out.println(rs.getString(15));
                    imagesList.add(i);
                    System.gc();
                }
            } catch (Exception e) {
                e.printStackTrace();
               
            }
        
        
        
        return imagesList;
    }

    @Override
    public List<Images> getLastUplodedImagesDetails(int uid) {
        List<Images> list=new ArrayList<Images>();
        try {
            String sql="SELECT * FROM images WHERE user_user_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, uid);
            ResultSet rs=ps.executeQuery();
            
            while (rs.next()) {                
                if(rs.getInt(20)== 2){
                    Images i=new Images();
                i.setCategories_category_id(rs.getInt(19));
                i.setColor_1(rs.getString(5));
                i.setColor_2(rs.getString(6));
                i.setColor_3(rs.getString(7));
                i.setColor_4(rs.getString(8));
                i.setColor_5(rs.getString(9));
                i.setColor_6(rs.getString(10));
                i.setColor_7(rs.getString(11));
                i.setColor_8(rs.getString(12));
                i.setColor_9(rs.getString(13));
                i.setDate(rs.getString(16));
                i.setDominate_color(rs.getString(4));
                i.setImage_state_image_state_id(rs.getInt(20));
                i.setImages_id(rs.getInt(1));
                i.setImg_blob(rs.getBinaryStream(14));
                i.setImg_url(rs.getString(15));
                i.setKey_wrods(rs.getString(3));
                i.setTitle(rs.getString(2));
                i.setUser_user_id(rs.getInt(18));
                System.out.println(rs.getString(15));
                list.add(i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int getLastUploadImageSize(int image_id) {
        int subimagesCount=0;
        try {
            String sql="SELECT * FROM sub_images WHERE images_images_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, image_id);
            ResultSet rs=ps.executeQuery();
            
            while (rs.next()) {                
                subimagesCount++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subimagesCount;
    }

    @Override
    public int getLastUplodedImagesId(int uid) {
        int images_id=0;
        try {
            String sql="SELECT images_id,image_state_image_state_id FROM images WHERE user_user_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, uid);
            ResultSet rs=ps.executeQuery();
            
            while (rs.next()) {  
                if(rs.getInt(2)== 2){
                    images_id=rs.getInt(1);
                }
                System.out.println("iiiiiiii  "+images_id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return images_id;
    }

    @Override
    public boolean checkColorCombination(int images_id,String dominationColor, String color1, String color2, String color3, String color4, String color5, String color6, String color7, String color8, String color9) {
       boolean ok=false;
        try {
            String sql="SELECT * FROM images WHERE images_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, images_id);
            
            ResultSet rs=ps.executeQuery();
            System.out.println(dominationColor+"/"+color1+"/"+color2+"/"+color3+"/"+color4+"/"+color5+"/"+color6+"/"+color7+"/"+color8+"/"+color9+"/");
            while (rs.next()) { 
                System.out.println(rs.getString(4)+"/"+rs.getString(5)+"/"+rs.getString(6)+"/"+rs.getString(7)+"/"+rs.getString(8)+"/"+rs.getString(9)+"/"+rs.getString(10)+"/"+rs.getString(11)+"/"+rs.getString(12)+"/"+rs.getString(13)+"/");
                if(rs.getString(4).equals("")&&rs.getString(5).equals("")&&rs.getString(6).equals("")&&
                        rs.getString(7).equals("")&&rs.getString(8).equals("")&&rs.getString(9).equals("")&&
                        rs.getString(10).equals("")&&rs.getString(11).equals("")&&rs.getString(12).equals("")&&rs.getString(13).equals("")){
                    ok=true;
                }
                else if(new ColorMatching().matchColor(rs.getString(4), dominationColor)){
                         System.out.println(rs.getString(10).equals(color6)+"nnnnnnnn");
                    ok=true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       
       return ok;
    }

    @Override
    public String checkDimentionDuplicate(int images_id, String dimention) {
        String ok="";
        System.out.println("the dimention:"+dimention);
        String newDimension[]=dimention.split(" x ");
        int newDim=Integer.parseInt(newDimension[0].toString());
        int newhight=Integer.parseInt(newDimension[1].toString());
        
        System.out.println(newhight);
        
        System.out.println(newDim);
        if (newDim >= 400 && newhight>=300) {
            
            if(newDim==400 && newhight>300){
            }
            System.out.println("if >400");
            System.out.println(images_id);
            System.out.println(dimention);
                    try {
                    String sql="SELECT dimention FROM sub_images WHERE images_images_id=?";
                    Connection con=DBFactory.getConnection();
                    PreparedStatement ps=con.prepareStatement(sql);
                    ps.setInt(1, images_id);
                    ResultSet rs=ps.executeQuery();
                        System.out.println(rs.next()+" next");
                        
                    if(rs.next()){
                           rs=ps.executeQuery();
                           
                            while (rs.next()) {
                                System.out.println(rs.getString(1)+"///"+dimention);
                                if (rs.getString(1).equals(dimention)) {
                                    ok="duplicate";
                                    System.out.println(ok);
                                    System.out.println(rs.getString(1)+"///"+dimention);
                                    System.out.println("duplicate");
                                    break;
                                }else{
                                    System.out.println("no duplicate");
                                    ok="no duplicate";

                                }
                            }
                    

                    }else{
                        ok="new";
                    }
                    System.out.println(ok);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            
        } else {
            System.out.println("else <400");
            ok="<400";
        }
        
        
        return ok;
    }

    @Override
    public boolean cancelUpload(int images_id) {
        boolean ok=false;
        try {
            String sql="DELETE FROM images WHERE images_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, images_id);
            int i=ps.executeUpdate();
            
            String sql2="DELETE FROM sub_images WHERE images_images_id=?";
            PreparedStatement ps2=con.prepareStatement(sql2);
            ps2.setInt(1, images_id);
            int j=ps2.executeUpdate();
            System.out.println("cancel upload j="+j);
            if(i>0 && j>0 || i>0 && j==0){
                System.out.println(ok);
                ok=true;
            }else{
                ok=false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(ok);
        return ok;
    }

    @Override
    public ImagesSub getSubImage(int sub_images_id) {
        ImagesSub sub=new ImagesSub();
        try {
            String sql="SELECT * FROM sub_images WHERE sub_images_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, sub_images_id);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                sub.setCredits(rs.getInt(6));
                sub.setDimention(rs.getString(4));
                sub.setImages_image_id(rs.getInt(9));
                sub.setImg_blob(rs.getBinaryStream(2));
                sub.setImg_url(rs.getString(3));
                sub.setPrice(rs.getDouble(5));
                sub.setSize_long(rs.getLong(7));
                sub.setSize_string(rs.getString(8));
                sub.setSub_images_id(rs.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sub;
    }

    @Override
    public boolean updatePriceUpload(int sub_images_id, double price,String dimention) {
        boolean ok=false;
        System.out.println(dimention);
        String checkDimension=checkUploadingImagePrice(dimention, price);
        if(checkDimension.equals("ok")){
                        try {
                   String sql="UPDATE sub_images SET price=? WHERE sub_images_id=?";
                   Connection con=DBFactory.getConnection();
                   PreparedStatement ps=con.prepareStatement(sql);
                   ps.setDouble(1, price);
                   ps.setInt(2, sub_images_id);
                   int i=ps.executeUpdate();
                   if(i>0){
                       ok=true;
                   }else{
                       ok=false;
                   }
               } catch (Exception e) {
                   e.printStackTrace();
               }
        }else{
        
            ok=false;
        }
       
        return ok;
    }

   

    
    
    @Override
    public String removeSubImage(int sub_images_id,int images_id) {
        String ok="Error";
        try {
            String sql="DELETE FROM sub_images WHERE sub_images_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, sub_images_id);
            int i=ps.executeUpdate();
            
            if(i>0){
                int isLeft=countOfSubImagesLeft(images_id);
                if(isLeft==0){
                    boolean clearColor=updateColorWhenRemovingAnImage(images_id);
                    if(clearColor){
                        ok="OK&"+isLeft;
                    }
                }else{
                    ok="OK&"+isLeft;
                }
               
            }else{
                ok="Error";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public int countOfSubImagesLeft(int images_id) {
        int count=0;
        try {
            String sql="SELECT * FROM sub_images WHERE images_images_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, images_id);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public boolean updateColorWhenRemovingAnImage(int images_id) {
        boolean ok=false;
        try {
            String sql="UPDATE images SET dominate_color=?,color_1=?,color_2=?,color_3=?,color_4=?,color_5=?,color_6=?,color_7=?,color_8=?,color_9=? WHERE images_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1, "");
            ps.setString(2, "");
            ps.setString(3, "");
            ps.setString(4, "");
            ps.setString(5, "");
            ps.setString(6, "");
            ps.setString(7, "");
            ps.setString(8, "");
            ps.setString(9, "");
            ps.setString(10,"");
            ps.setInt(11, images_id);
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
    public String updateImageState(int images_id) {
        String ok="";
        try {
            String sql="UPDATE images SET image_state_image_state_id=? WHERE images_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, 3);
            ps.setInt(2, images_id);
            
            int count=countOfSubImagesLeft(images_id);
            
            int state=ImagesDAOImp.this.checkImageState(images_id);
            if(state==2){
            boolean minDimention=new ImagesDAOImp().checkMinDimention(images_id);
            
            if(count>=3){

                if(minDimention){
                        int i=ps.executeUpdate();
                       if(i>0){
                           ok="done";

                       }else{
                           ok="error";
                       }
                }else{
                    ok="nomindimention";
                }
            }else{
                ok="<3";
            }
            }else if(state==4){
                if(count>=2){
                    boolean minDimention=new ImagesDAOImp().checkMinDimention(images_id);
                    if(!minDimention){
                        int i=ps.executeUpdate();
                       if(i>0){
                           ok="done";

                       }else{
                           ok="error";
                       }
                    
                    }else{
                        ok="<400";
                    }
                 
                }else{
                    ok="<2";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public boolean checkMinDimention(int images_id) {
        boolean ok=false;
        try {
            String sql="SELECT * FROM sub_images WHERE images_images_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, images_id);
            ResultSet rs=ps.executeQuery();
            
            while (rs.next()) {
                if(rs.getString(4).equals("400 x 300")){
                    System.out.println("min dimention is ok");
                    ok=true;
                    break;
                }else{
                    ok=false;
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        return ok;
    }

    @Override
    public boolean checkDimensionLessThanMinDimension(String add_dimension) {
        boolean ok=false;
        String newDimension[]=add_dimension.split(" x ");
        int newDim=Integer.parseInt(newDimension[0].toString());
        if (newDim > 400) {
            System.out.println("if <400");
            
        } else {
            System.out.println("else");
        }
        return ok;       
    }

    @Override
    public String checkUploadingImagePrice(String dimension, double price) {
        
        String ok="";
        String dimensionArray[]=dimension.split(" x ");
        int dimensionWidth=Integer.parseInt(dimensionArray[0]);
        System.out.println(dimensionWidth);
        
        int fullPrice=(int) price;
        System.out.println(fullPrice+"int value");
        
        if(0< dimensionWidth && dimensionWidth < 540 ){
             
            if(fullPrice==0){
                System.out.println("0< dimensionWidth && dimensionWidth < 540 ");
                ok="ok";
            }else{
                ok="error";
            }
        }
        else if(540< dimensionWidth && dimensionWidth < 960 ){
            
            if( 10<=fullPrice && fullPrice<=20 ){
               System.out.println("540< dimensionWidth && dimensionWidth < 960 ");
                ok="ok";
            }else{
                ok="error";
            }
        }
        else if(576 <dimensionWidth && dimensionWidth<1024){
           if( 25<=fullPrice && fullPrice<=35 ){
               System.out.println("576 <dimensionWidth && dimensionWidth<1024");
                ok="ok";
            }else{
                ok="error";
            }
        }
        else if(720 <dimensionWidth && dimensionWidth<1280){
            if(  40<=fullPrice && fullPrice<=50){
                System.out.println("720 <dimensionWidth && dimensionWidth<1280");
                ok="ok";
            }else{
                ok="error";
            }
        }
        else if(768 <dimensionWidth && dimensionWidth<1366){
            if(  55<=fullPrice && fullPrice<=65  ){
                System.out.println("768 <dimensionWidth && dimensionWidth<1366");
                ok="ok";
            }else{
                ok="error";
            }
        }
        else if(900 <dimensionWidth && dimensionWidth<1600){
           if( 70<=fullPrice && fullPrice<=80  ){
               System.out.println("900 <dimensionWidth && dimensionWidth<1600");
                ok="ok";
            }else{
                ok="error";
            } 
        }
        else if(1080 <dimensionWidth && dimensionWidth<1920){
            if( 85<=fullPrice && fullPrice<=95  ){
                System.out.println("1080 <dimensionWidth && dimensionWidth<1920");
                ok="ok";
            }else{
                ok="error";
            } 
        }
        else if(1152 <dimensionWidth && dimensionWidth<2048){
            if(  100<=fullPrice && fullPrice<=110  ){
                System.out.println("1152 <dimensionWidth && dimensionWidth<2048");
                ok="ok";
            }else{
                ok="error";
            } 
        }
        else if(1350 <dimensionWidth && dimensionWidth<2400){
            if( 115<=fullPrice && fullPrice<=120 ){
                System.out.println("1350 <dimensionWidth && dimensionWidth<2400");
                ok="ok";
            }else{
                ok="error";
            }  
        }
        else if(1440 <dimensionWidth && dimensionWidth<2400){
            if(125<=fullPrice && fullPrice<=130 ){
                System.out.println("1440 <dimensionWidth && dimensionWidth<2400");
                ok="ok";
            }else{
                ok="error";
            }  
        }
        else if(1620 <dimensionWidth && dimensionWidth<2880){
            if(135<=fullPrice && fullPrice<=140  ){
                System.out.println("1620 <dimensionWidth && dimensionWidth<2880");
                ok="ok";
            }else{
                ok="error";
            }  
        }
        else if(1999 <dimensionWidth && dimensionWidth<3554){
             if( 145<=fullPrice && fullPrice<=150  ){
                 System.out.println("1999 <dimensionWidth && dimensionWidth<3554");
                ok="ok";
            }else{
                ok="error";
            } 
        }
        else if(2160 <dimensionWidth && dimensionWidth<3840){
             if( 160<=fullPrice && fullPrice<=165 ){
                 System.out.println("2160 <dimensionWidth && dimensionWidth<3840");
                ok="ok";
            }else{
                ok="error";
            } 
        }
        System.out.println(ok);
        return ok;
    }

    @Override
    public String getCategoryName(int category_id) {
        String cat="";
        try {
            String sql="SELECT category FROM categories WHERE category_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, category_id);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                cat=rs.getString(1);
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cat;
    }

    @Override
    public String getSellerName(int user_id) {
        String user="";
        System.out.println("User ID:"+user_id);
        try {
            String sql="SELECT user_name FROM user WHERE user_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, user_id);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                user=rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<Images> searchSimilarImages(String rgb,int images_id) {
        ArrayList<Images> imagesList=new ArrayList<Images>();
        System.out.println(rgb);
        try {
            String sql="SELECT * FROM images WHERE dominate_color=? OR color_1=? OR color_2=? OR color_3=? OR color_4=? OR color_5=? OR "
                    + "color_6=? OR color_7=? OR color_8=? OR color_9=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1, rgb);
            ps.setString(2, rgb);
            ps.setString(3, rgb);
            ps.setString(4, rgb);
            ps.setString(5, rgb);
            ps.setString(6, rgb);
            ps.setString(7, rgb);
            ps.setString(8, rgb);
            ps.setString(9, rgb);
            ps.setString(10, rgb);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                if(rs.getInt(20)==3 && rs.getInt(1)!= images_id){
                    Images i=new Images();
                i.setCategories_category_id(rs.getInt(19));
                i.setColor_1(rs.getString(5));
                i.setColor_2(rs.getString(6));
                i.setColor_3(rs.getString(7));
                i.setColor_4(rs.getString(8));
                i.setColor_5(rs.getString(9));
                i.setColor_6(rs.getString(10));
                i.setColor_7(rs.getString(11));
                i.setColor_8(rs.getString(12));
                i.setColor_9(rs.getString(13));
                i.setDate(rs.getString(16));
                i.setDominate_color(rs.getString(4));
                i.setImage_state_image_state_id(rs.getInt(20));
                i.setImages_id(rs.getInt(1));
                i.setImg_blob(rs.getBinaryStream(14));
                i.setImg_url(rs.getString(15));
                i.setKey_wrods(rs.getString(3));
                i.setTitle(rs.getString(2));
                i.setUser_user_id(rs.getInt(18));
                System.out.println(rs.getString(15));
                imagesList.add(i);
                
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return imagesList;
    }

    @Override
    public boolean removeAdminImage(int images_id) {
        boolean ok=false;
        try {
            String sql="DELETE FROM sub_images WHERE images_images_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, images_id);
            
            String sql2="DELETE FROM images WHERE images_id=?";
            PreparedStatement ps2=con.prepareStatement(sql2);
            ps2.setInt(1, images_id);
            
            String sql3="DELETE FROM key_words WHERE images_images_id=?";
            
            PreparedStatement ps3=con.prepareStatement(sql3);
            ps3.setInt(1, images_id);
            
            
            int i=ps.executeUpdate();
            int j=ps2.executeUpdate();
            int y=ps3.executeUpdate();
            System.out.println("i:"+i+"j:"+j+"y:"+y);
            if(i>0 && j>0 && y>0 || y==0){
            ok=true;
            }else{
            ok=false;
            }
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public boolean adminUpdateImages(int images_id,int state) {
        System.out.println(images_id+"----"+state);
        boolean ok=false;
        try {
            
            String sql="UPDATE images SET image_state_image_state_id=? WHERE images_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1,state);
            ps.setInt(2, images_id);
            
            int i=ps.executeUpdate();
            
            
            if(i>0){
                ok=true;
            }
            System.out.println(i+"==");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public boolean checkImageOwner(int uid, int imgid) {
        boolean ok=false;
        try {
            String sql="SELECT * FROM images WHERE images_id=? AND user_user_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, imgid);
            ps.setInt(2, uid);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                if(rs.getInt(20)==2){
                    System.out.println(rs.next());
                    ok=true;
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public boolean isHaveUpdate(int uid) {
        boolean ok=false;
        try {
            String sql="SELECT * FROM images WHERE user_user_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, uid);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                if(rs.getInt(20)==4){
                    ok=true;
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public int listTobeUpdate(int uid) {
        int i=0;
        System.out.println("uid:"+uid);
        try {
            String sql="SELECT * FROM images WHERE user_user_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, uid);
            ResultSet rs=ps.executeQuery();
            
            while(rs.next()){
                System.out.println(rs.getInt(20));
                 
                    
                    if(rs.getInt(20)== 4){
                    i=rs.getInt(1);
                    System.out.println("rs id="+rs.getInt(1));
                    break;
                    }
                
                
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public int checkImageState(int imgid) {
        int i=0;
        try {
            String sql="SELECT image_state_image_state_id FROM images WHERE images_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, imgid);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                i=rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public List<Images> getMyUpload(int uid) {
        List<Images> list=new ArrayList<Images>();
        try {
            String sql="SELECT * FROM images WHERE user_user_id=? AND image_state_image_state_id=? OR image_state_image_state_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, uid);
            ps.setInt(2, 1);
            ps.setInt(3, 5);
            ResultSet rs=ps.executeQuery();
            
            while (rs.next()) {                
                Images i=new Images();
                i.setCategories_category_id(rs.getInt(19));
                i.setColor_1(rs.getString(5));
                i.setColor_2(rs.getString(6));
                i.setColor_3(rs.getString(7));
                i.setColor_4(rs.getString(8));
                i.setColor_5(rs.getString(9));
                i.setColor_6(rs.getString(10));
                i.setColor_7(rs.getString(11));
                i.setColor_8(rs.getString(12));
                i.setColor_9(rs.getString(13));
                i.setDate(rs.getString(16));
                i.setDominate_color(rs.getString(4));
                i.setImage_state_image_state_id(rs.getInt(20));
                i.setImages_id(rs.getInt(1));
                i.setImg_blob(rs.getBinaryStream(14));
                i.setImg_url(rs.getString(15));
                i.setKey_wrods(rs.getString(3));
                i.setTitle(rs.getString(2));
                i.setUser_user_id(rs.getInt(18));
                System.out.println(rs.getString(15));
                list.add(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Images checkUserToImage(int uid, int imgid) {
        Images i=null;
        try {
            String sql="SELECT * FROM images where images_id=? AND user_user_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, imgid);
            ps.setInt(2, uid);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                i=new Images();
                i.setCategories_category_id(rs.getInt(19));
                i.setColor_1(rs.getString(5));
                i.setColor_2(rs.getString(6));
                i.setColor_3(rs.getString(7));
                i.setColor_4(rs.getString(8));
                i.setColor_5(rs.getString(9));
                i.setColor_6(rs.getString(10));
                i.setColor_7(rs.getString(11));
                i.setColor_8(rs.getString(12));
                i.setColor_9(rs.getString(13));
                i.setDate(rs.getString(16));
                i.setDominate_color(rs.getString(4));
                i.setImage_state_image_state_id(rs.getInt(20));
                i.setImages_id(rs.getInt(1));
                i.setImg_blob(rs.getBinaryStream(14));
                i.setImg_url(rs.getString(15));
                i.setKey_wrods(rs.getString(3));
                i.setTitle(rs.getString(2));
                i.setUser_user_id(rs.getInt(18));
                System.out.println(rs.getString(15));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public boolean deleteMyImages(int imgid) {
        System.out.println("in side the deletemyimage");
        boolean ok=false;
        try {
            String sql="UPDATE images SET image_state_image_state_id=? WHERE images_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, 7);
            ps.setInt(2, imgid);
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
    public int checkImagesId(String dominationColor, String color1, String color2, String color3, String color4, String color5, String color6, String color7, String color8, String color9) {
        int i=0;
        try {
            String sql="SELECT images_id FROM images WHERE dominate_color=? AND color_1=? AND color_2=? AND color_3=? AND color_4=? AND color_5=? AND color_6=? AND color_7=? AND color_8=? AND color_9=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1, dominationColor);
            ps.setString(2, color1);
            ps.setString(3, color2);
            ps.setString(4, color3);
            ps.setString(5, color4);
            ps.setString(6, color5);
            ps.setString(7, color6);
            ps.setString(8, color7);
            ps.setString(9, color8);
            ps.setString(10, color9);
            ResultSet rs=ps.executeQuery();
            System.out.println(i);
            while (rs.next()) {                
                i=rs.getInt(1);
            }
            System.out.println(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public boolean inserSubImages(ImagesSub sub) {
        boolean ok=false;
        try {
            String sql="INSERT INTO sub_images(img_blob,img_url,dimention,price,credits,size_long,size_string,Images_images_id)"
                    + "VALUES(?,?,?,?,?,?,?,?)";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setBinaryStream(1,sub.getImg_blob());
            ps.setString(2, sub.getImg_url());
            ps.setString(3, sub.getDimention());
            ps.setDouble(4, sub.getPrice());
            ps.setInt(5, sub.getCredits());
            ps.setLong(6, sub.getSize_long());
            ps.setString(7, sub.getSize_string());
            ps.setInt(8, sub.getImages_image_id());
            
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
    public int getSubImageID(int imgId, int si) {
        int i=0;
        try {
            String sql="SELECT * FROM sub_images WHERE images_images_id=? AND credits=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, imgId);
            ps.setInt(2, si);
            
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                i=rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public List<Images> searchByTitle(String title) {
        List<Images> li=new ArrayList<Images>();
        try {
            String sql="SELECT * FROM images WHERE title LIKE '%"+title+"%'";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
               Images i=new Images();
                i.setCategories_category_id(rs.getInt(19));
                i.setColor_1(rs.getString(5));
                i.setColor_2(rs.getString(6));
                i.setColor_3(rs.getString(7));
                i.setColor_4(rs.getString(8));
                i.setColor_5(rs.getString(9));
                i.setColor_6(rs.getString(10));
                i.setColor_7(rs.getString(11));
                i.setColor_8(rs.getString(12));
                i.setColor_9(rs.getString(13));
                i.setDate(rs.getString(16));
                i.setDominate_color(rs.getString(4));
                i.setImage_state_image_state_id(rs.getInt(20));
                i.setImages_id(rs.getInt(1));
                i.setImg_blob(rs.getBinaryStream(14));
                i.setImg_url(rs.getString(15));
                i.setKey_wrods(rs.getString(3));
                i.setTitle(rs.getString(2));
                i.setUser_user_id(rs.getInt(18));
                li.add(i);
                System.gc();
                System.out.println(rs.getString(15));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return li;
    }

    
    public boolean checkOwner(int uid, int imgid) {
        boolean ok=false;
        try {
            String sql="SELECT * FROM images WHERE images_id=? AND user_user_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, imgid);
            ps.setInt(2, uid);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                if(rs.getInt(20)==1){
                    System.out.println(rs.next());
                    ok=true;
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public List<Images> listByCategory(int cat_id) {
        List<Images> li=new ArrayList<Images>();
        
        try {
            String sql="SELECT * FROM images WHERE categories_category_id=? ORDER BY images_id DESC";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            
            ps.setInt(1, cat_id);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                Images i=new Images();
                i.setCategories_category_id(rs.getInt(19));
                i.setColor_1(rs.getString(5));
                i.setColor_2(rs.getString(6));
                i.setColor_3(rs.getString(7));
                i.setColor_4(rs.getString(8));
                i.setColor_5(rs.getString(9));
                i.setColor_6(rs.getString(10));
                i.setColor_7(rs.getString(11));
                i.setColor_8(rs.getString(12));
                i.setColor_9(rs.getString(13));
                i.setDate(rs.getString(16));
                i.setDominate_color(rs.getString(4));
                i.setImage_state_image_state_id(rs.getInt(20));
                i.setImages_id(rs.getInt(1));
                i.setImg_blob(rs.getBinaryStream(14));
                i.setImg_url(rs.getString(15));
                i.setKey_wrods(rs.getString(3));
                i.setTitle(rs.getString(2));
                i.setUser_user_id(rs.getInt(18));
                li.add(i);
                System.gc();
                System.out.println(rs.getString(15));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return li;
    }

    @Override
    public boolean updateImageViewCount(int imgid, int view) {
        boolean ok=false;
        try {
            String sql="UPDATE images SET view=? WHERE images_id=?";
            PreparedStatement ps=DBFactory.Connection.prepareStatement(sql);
            ps.setInt(1, view);
            ps.setInt(2, imgid);
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
    public List<Images> getNewUploadList() {
        List<Images> li=new ArrayList<Images>();
        
        try {
            String sql="SELECT * FROM images WHERE image_state_image_state_id='3'";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            
            
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                Images i=new Images();
                i.setCategories_category_id(rs.getInt(19));
                i.setColor_1(rs.getString(5));
                i.setColor_2(rs.getString(6));
                i.setColor_3(rs.getString(7));
                i.setColor_4(rs.getString(8));
                i.setColor_5(rs.getString(9));
                i.setColor_6(rs.getString(10));
                i.setColor_7(rs.getString(11));
                i.setColor_8(rs.getString(12));
                i.setColor_9(rs.getString(13));
                i.setDate(rs.getString(16));
                i.setDominate_color(rs.getString(4));
                i.setImage_state_image_state_id(rs.getInt(20));
                i.setImages_id(rs.getInt(1));
                i.setImg_blob(rs.getBinaryStream(14));
                i.setImg_url(rs.getString(15));
                i.setKey_wrods(rs.getString(3));
                i.setTitle(rs.getString(2));
                i.setUser_user_id(rs.getInt(18));
                li.add(i);
                System.gc();
                System.out.println(rs.getString(15));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return li;
    }
    
    
    public boolean updateState(int imgid,int state) {
        boolean ok=false;
        try {
            String sql="UPDATE images SET image_state_image_state_id=? WHERE images_id=?";
            Connection con=DBFactory.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1, state);
            ps.setInt(2, imgid);
            
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
    public ImagesSub getLowestQuality(int imgid) {
        ImagesSub sub=null;
        try {
            String sql="SELECT * FROM sub_images WHERE images_images_id=? ORDER BY sub_images_id ASC";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ps.setInt(1, imgid);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                sub=new ImagesSub();
                sub.setCredits(rs.getInt(6));
                sub.setDimention(rs.getString(4));
                sub.setImages_image_id(rs.getInt(9));
                sub.setImg_blob(rs.getBinaryStream(2));
                sub.setImg_url(rs.getString(3));
                sub.setPrice(rs.getDouble(5));
                sub.setSize_long(rs.getLong(7));
                sub.setSize_string(rs.getString(8));
                sub.setSub_images_id(rs.getInt(1));
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sub;
    }

    @Override
    public String getImageRate() {
       StringBuffer sb=null;
           sb=new StringBuffer("{'imgvd':{"); 
           sb.append("'name':'Image Rate',");
           sb.append("'rate':[");
           try {
           String sql="SELECT YEAR(date),sum(image_state_image_state_id) FROM images WHERE image_state_image_state_id='1' AND YEAR(date) >= 2014 ";    
           PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            int max=0;
            while (rs.next()) {  
                System.out.println("Year:"+rs.getString(1));
                System.out.println("value:"+rs.getString(2));
                if(rs.isLast()){
                    sb.append("{'year':'"+rs.getString(1)+"','uploads':'"+rs.getInt(2)+"','downloads':'"+getImageDownloadRate(rs.getString(1))+"'}");
                }else{
                    sb.append("{'year':'"+rs.getString(1)+"','uploads':'"+rs.getInt(2)+"','downloads':'"+getImageDownloadRate(rs.getString(1))+"'},");
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
    public int getImageDownloadRate(String year) {
        int i=0;
        try {
         String sql="SELECT count(cart_has_images_id) FROM cart_has_images WHERE is_purchase='1' OR is_purchase='3' AND YEAR(date) >= ? ";  
   
         PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
         ps.setString(1, year);
            ResultSet rs=ps.executeQuery();   
            while (rs.next()) {                
                System.out.println("download count:"+rs.getInt(1));
                i=rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }
    
    public String getCategoryWiseUploads(){
        CategoriesDAOImp cdi=new CategoriesDAOImp();
       StringBuffer sb=null;
           sb=new StringBuffer("{'catup':{"); 
           sb.append("'name':'category wise uploads Rate',");
           sb.append("'rate':[");
           try {
               String sql="SELECT categories_category_id,COUNT(images_id) FROM images GROUP BY categories_category_id";
               PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
               ResultSet rs=ps.executeQuery();
               while (rs.next()) {                   
                   System.out.println("imfgid:"+rs.getInt(1));
                   System.out.println("count:"+rs.getInt(2));
                   if(rs.isLast()){
                    sb.append("{'category':'"+cdi.getImageCategory(rs.getInt(1))+"','value':'"+rs.getInt(2)+"'}");
                }else{
                    sb.append("{'category':'"+cdi.getImageCategory(rs.getInt(1))+"','value':'"+rs.getInt(2)+"'},");
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
    public Images getMaxViewed() {
        Images i=null;
        try {
            String sql="SELECT * FROM images WHERE view='"+getMax()+"'";
            System.out.println("in");             
            
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
               ResultSet rs=ps.executeQuery();
            if(rs.next()){
                i=new Images();
                i.setCategories_category_id(rs.getInt(19));
                i.setColor_1(rs.getString(5));
                i.setColor_2(rs.getString(6));
                i.setColor_3(rs.getString(7));
                i.setColor_4(rs.getString(8));
                i.setColor_5(rs.getString(9));
                i.setColor_6(rs.getString(10));
                i.setColor_7(rs.getString(11));
                i.setColor_8(rs.getString(12));
                i.setColor_9(rs.getString(13));
                i.setDate(rs.getString(16));
                i.setDominate_color(rs.getString(4));
                i.setImage_state_image_state_id(rs.getInt(20));
                i.setImages_id(rs.getInt(1));
                i.setImg_blob(rs.getBinaryStream(14));
                i.setImg_url(rs.getString(15));
                i.setKey_wrods(rs.getString(3));
                i.setTitle(rs.getString(2));
                i.setUser_user_id(rs.getInt(18));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }
    
    public int getMax(){
        int i=0;
        try {
            String sql="SELECT MAX(view) FROM images";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {                
              i=rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return i;
    }
    
   
    
    public List<Images> getMaxValueCategory(){
        List<Images> li=new ArrayList<Images>();
        try {
            String sql="SELECT *,MAX(view) FROM images GROUP BY categories_category_id ORDER BY MAX(view) DESC LIMIT 1,5";
            PreparedStatement ps=DBFactory.getConnection().prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {                
                System.out.println("ID:"+rs.getInt(1));
                Images i=new Images();
                i.setCategories_category_id(rs.getInt(19));
                i.setColor_1(rs.getString(5));
                i.setColor_2(rs.getString(6));
                i.setColor_3(rs.getString(7));
                i.setColor_4(rs.getString(8));
                i.setColor_5(rs.getString(9));
                i.setColor_6(rs.getString(10));
                i.setColor_7(rs.getString(11));
                i.setColor_8(rs.getString(12));
                i.setColor_9(rs.getString(13));
                i.setDate(rs.getString(16));
                i.setDominate_color(rs.getString(4));
                i.setImage_state_image_state_id(rs.getInt(20));
                i.setImages_id(rs.getInt(1));
                i.setImg_blob(rs.getBinaryStream(14));
                i.setImg_url(rs.getString(15));
                i.setKey_wrods(rs.getString(3));
                i.setTitle(rs.getString(2));
                i.setUser_user_id(rs.getInt(18));
                li.add(i);
                System.gc();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return li;
    }
 
    
    @Override
    public int getImagesCount() {
        int i = 0;
        try {
            String sql ="SELECT COUNT(images_id) FROM images";
            PreparedStatement ps = DBFactory.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {                
                i = rs.getInt(1);
                System.out.println(i+"PPPPPPPPPPPPPPPPPPP");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return i;
    }
    
}

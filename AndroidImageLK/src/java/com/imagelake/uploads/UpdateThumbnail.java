/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.uploads;

import com.imagelake.control.ImagesDAOImp;
import com.imagelake.model.ImagesSub;

/**
 *
 * @author Lakmal
 */
public class UpdateThumbnail {
    
    public String getUpdateImage(int subimgid){
        ImagesSub sub=new ImagesDAOImp().getSubImage(subimgid);
        String minPrice="00.00";
        String maxPrice="00.00";
        String value="00.00";
        String Dimention=sub.getDimention();
        
        System.out.println(Dimention);
        String ArrayDimention[]=Dimention.split(" x ");
        
        String subArray=ArrayDimention[0].toString();
        
        int maxDimention=Integer.parseInt(subArray);
        System.out.println(maxDimention);
        
        String img="";
        img+="<h1>Update</h1>";
        img+="<div class='uk-margin' style='text-align: center;'>";
        img+="<div class='uk-thumbnail uk-thumbnail-large'>";
        img+="<img src='uploads/"+sub.getImg_url()+"' width='600' height='400' alt='"+sub.getImg_url()+"'>";
        img+="<div class='uk-thumbnail-caption'><code>.uk-thumbnail-large</code></div>";
        img+="</div>";
        img+="</div>";
        img+="<ul style='list-style: none;'>";
        img+="<li style='margin-top: 5px;'>Dimention :-<code>"+sub.getDimention()+"</code>"
                + "<input id='updateDimension' type='hidden' name='updateDimension' value="+sub.getDimention()+"/></li>";
        img+="<li style='margin-top: 5px;'>Size :-<code>"+sub.getSize_string()+"</code></li>";
        img+="<li>Price :-<code>$</code>";
        if(540< maxDimention && maxDimention < 960 ){
            minPrice="10";
            maxPrice="20";
            value="10.00";
        }
        else if(576 <maxDimention && maxDimention<1024){
            minPrice="25";
            maxPrice="35";
            value="25.00";
        }
        else if(720 <maxDimention && maxDimention<1280){
            minPrice="40";
            maxPrice="50";
            value="40.00";
        }
        else if(768 <maxDimention && maxDimention<1366){
            minPrice="55";
            maxPrice="65";
            value="55.00";
        }
        else if(900 <maxDimention && maxDimention<1600){
            minPrice="70";
            maxPrice="80";
            value="70.00";
        }
        else if(1080 <maxDimention && maxDimention<1920){
            minPrice="85";
            maxPrice="95";
            value="85.00";
        }
        else if(1152 <maxDimention && maxDimention<2048){
            minPrice="100";
            maxPrice="110";
            value="100.00";
        }
        else if(1350 <maxDimention && maxDimention<2400){
            minPrice="115";
            maxPrice="120";
            value="115.00";
        }
        else if(1440 <maxDimention && maxDimention<2400){
            minPrice="125";
            maxPrice="130";
            value="125.00";
        }
        else if(1620 <maxDimention && maxDimention<2880){
            minPrice="135";
            maxPrice="140";
            value="135.00";
        }
        else if(1999 <maxDimention && maxDimention<3554){
            minPrice="145";
            maxPrice="150";
            value="145.00";
        }
        else if(2160 <maxDimention && maxDimention<3840){
            minPrice="160";
            maxPrice="165";
            value="160.00";
        }
        img+="<input id='updatePri' type='number' name='img_update_price'  style='margin-left: 5px;height: 30px;width: 70px;margin-top: 5px;' value='"+sub.getPrice()+"' min='"+minPrice+"' max='"+maxPrice+"'/>";
        img+="<input id='sub-img-id' type='hidden' name='sub_img' value='"+sub.getSub_images_id()+"'/>";
        img+="</li>";
        img+="</ul>";
        img+="<div style='position: absolute;bottom: 6px;right: 5px;'>";
        img+="<a href='javascript:void(0)' class='btn btn-danger' onclick='updateModalCancel()' style='margin-left:5px;'>Cancel</a>";
        img+="<a href='javascript:void(0)' class='btn btn-info' onclick='updateModalUpdate()' style='margin-left:5px;'>Update</a>";
        img+="</div>";
        
        return img;
        
    }
    
}

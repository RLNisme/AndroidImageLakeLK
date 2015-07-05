/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.uploads;

import com.imagelake.control.ImagesDAOImp;
import com.imagelake.model.Images;
import com.imagelake.model.ImagesSub;
import java.util.ArrayList;

/**
 *
 * @author Lakmal
 */
public class CreateUploadThumbnail {
    
    public String createUploadThubnail(int imgid){
            System.out.println("iiiiiiiiiiiiiiiii"+imgid);
                 ArrayList<ImagesSub> subimagesList=(ArrayList<ImagesSub>) new ImagesDAOImp().getImagedescription(imgid);
                Images img=new ImagesDAOImp().getImageDetail(imgid);
                String figure=imgid+"&"+img.getTitle()+"|";
               
                
                for (ImagesSub imagesSub : subimagesList) {
                    figure+="<figure class='uk-thumbnail' style='margin: 10px;width: 240px;padding-bottom: 0px;border-bottom: 0px;'>";
                    figure+="<img src='uploads/"+imagesSub.getImg_url()+"' alt='"+imagesSub.getImg_url()+"' style='height: 185px;'>";
                    figure+="<ul style='list-style: none;margin-left: 0px;margin-bottom: 0px;'>";
                    figure+="<li style='margin-bottom: 5px;margin-top: 5px;'>Dimention :-<code class='codeinfo'>"+imagesSub.getDimention()+"</code></li>";
                    figure+="<li style='margin-bottom: 5px;'>Size :-<code><span>"+imagesSub.getSize_string()+"</span></code></li>";
                    figure+="<li style='margin-bottom: 5px;'>Price :-<code>$<span>"+imagesSub.getPrice()+"</span></code></li>";
                    figure+="<li style='margin-bottom: 5px;'><a href='javascript:void(0)' class='btn btn-block btn-info' onclick='updateUploadedThumbnail("+imagesSub.getSub_images_id()+")'>Update</a></li>";
                    figure+="<li style='margin-bottom: 5px;'><a href='javascript:void(0)' class='btn btn-block btn-danger' onclick='removeUploadedThumbnail("+imagesSub.getSub_images_id()+")'>Remove</a></li>";
                    figure+="</ul>";
                    
                    figure+="</figure>";
                    
                }
                return figure;
    }
}

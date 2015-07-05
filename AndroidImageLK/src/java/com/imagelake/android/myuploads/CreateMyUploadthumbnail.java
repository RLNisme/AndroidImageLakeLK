/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.android.myuploads;

import com.imagelake.control.ImagesDAOImp;
import com.imagelake.model.Images;
import com.imagelake.model.ImagesSub;
import com.sun.imageio.plugins.common.SubImageInputStream;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author RLN
 */
class CreateMyUploadthumbnail {

    ImagesDAOImp idi;
    ImagesSub is;
    JSONArray ja;
    public CreateMyUploadthumbnail() {
         ja= new JSONArray();
        
        idi = new ImagesDAOImp();
    }
    
    public String createThumb(int uid){
            System.out.println("uid="+uid);
            String img="";
            List<Images> imlist=idi.getMyUpload(uid);
            if(!imlist.isEmpty()){
                
                    for (Images images : imlist) {
                        if(images.getUser_user_id()==uid){
                        if(images.getImage_state_image_state_id()!=7){
                                    
                            
                            JSONObject jo = new JSONObject();
                            jo.put("img_id", images.getImages_id());
                            jo.put("title", images.getTitle());
                            jo.put("state", images.getImage_state_image_state_id());
                            jo.put("url", images.getImg_url());
                            ja.add(jo);
                        }
                        }
                }
                    if(!ja.isEmpty()){
                    img = "json="+ja.toJSONString();
                    }else{
                        img="msg=No item found.";
                    }
            }else{
                img="msg=No item found.";
            }
           System.gc();
            return img;
    }
}

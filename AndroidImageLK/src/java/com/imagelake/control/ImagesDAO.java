/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.Images;
import com.imagelake.model.ImagesSub;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public interface ImagesDAO {
    public List<Images> listAllImages();
    public List<ImagesSub> getImagedescription(int image_id);//
    public Images getImageDetail(int image_id);//
    public int getLastImageId();
    public boolean insertImage(Images image);
    public int getImageId(String title);
    public boolean insertImageContent(Images image,ImagesSub subimage);
    
    public List<Images> searchByColor(String rgb);
    
    public List<Images> getLastUplodedImagesDetails(int uid);
    public int getLastUploadImageSize(int image_id);
    public int getLastUplodedImagesId(int uid);
    public boolean checkColorCombination(int images_id,String dominationColor,String color1,String color2,String color3,
            String color4,String color5,String color6,String color7,String color8,String color9);
    
    public String checkDimentionDuplicate(int images_id,String dimention);
    public boolean cancelUpload(int images_id);
    public ImagesSub getSubImage(int sub_images_id);//
    public boolean updatePriceUpload(int sub_images_id,double price,String dimention);
    
    public String removeSubImage(int sub_images_id,int images_id);
    public int countOfSubImagesLeft(int images_id);
    public boolean updateColorWhenRemovingAnImage(int images_id);
    public String updateImageState(int images_id);
    public boolean checkMinDimention(int images_id);
    public boolean checkDimensionLessThanMinDimension(String add_dimension);
    public String checkUploadingImagePrice(String dimension,double price);
    public String getCategoryName(int category_id);//
    public String getSellerName(int user_id);//
    
    public List<Images> searchSimilarImages(String rgb,int images_id);//
    public boolean removeAdminImage(int images_id);
    
    public boolean adminUpdateImages(int images_id,int state);
    public boolean checkImageOwner(int uid,int imgid);
    public boolean isHaveUpdate(int uid);
    public int listTobeUpdate(int uid);
    public int checkImageState(int imgid);
    
    //myupload
    public List<Images> getMyUpload(int uid);
    public Images checkUserToImage(int uid,int imgid);//
    public boolean deleteMyImages(int imgid);//
    
    //upload
    public int checkImagesId(String dominationColor,String color1,String color2,String color3,
            String color4,String color5,String color6,String color7,String color8,String color9);
    public boolean inserSubImages(ImagesSub subimages);
    
    public int getSubImageID(int imgId,int si);//
    public List<Images> searchByTitle(String title);//
    
    //search
    public List<Images> listByCategory(int cat_id);
    public boolean updateImageViewCount(int imgid,int view);
    
    
    //adminupload
    public List<Images> getNewUploadList();
    public boolean updateState(int imgid,int state);
    
    //
    public ImagesSub getLowestQuality(int imgid);
    public String getImageRate();
    public int getImageDownloadRate(String year);
    public String getCategoryWiseUploads();
    
    public Images getMaxViewed(); 
     public int getImagesCount();
   
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.android.uploadmanagement;

import com.imagelake.control.ImagesDAOImp;
import com.imagelake.model.Images;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author Lakmal
 */
public class ImageRec {
    private List<Images> diffList=null;

    public ImageRec() {
        diffList=new ArrayList<Images>();
    }
    
    public List<Images> imgDiff(Images img){
       
      
        
    BufferedImage img1 = null;
    BufferedImage img2 = null;
    try {
      
      img1 = ImageIO.read(new File("D:\\Windows-Use\\Android\\JetBrainWorkStation\\AndroidSyncDB\\AndroidImageLK\\build\\web\\"+img.getImg_url()));
      
    } catch (IOException e) {
      e.printStackTrace();
    }
    List<Images> list=new ImagesDAOImp().listAllImages();
    for(Images im:list){
        if(im.getImages_id()!=img.getImages_id()){
     try {
         System.out.println("img ID: "+im.getImages_id());
         System.out.println("title: "+im.getTitle());
          img2 = ImageIO.read(new File("D:\\Windows-Use\\Android\\JetBrainWorkStation\\AndroidSyncDB\\AndroidImageLK\\build\\web\\"+im.getImg_url()));
         
     } catch (IOException ex) {
            ex.printStackTrace();
     }
     
    int width1 = img1.getWidth(null);
    int width2 = img2.getWidth(null);
    int height1 = img1.getHeight(null);
    int height2 = img2.getHeight(null);
    if ((width1 != width2) || (height1 != height2)) {
      System.err.println("Error: Images dimensions mismatch");
      System.exit(1);
    }
    long diff = 0;
    for (int y = 0; y < height1; y++) {
      for (int x = 0; x < width1; x++) {
        int rgb1 = img1.getRGB(x, y);
        int rgb2 = img2.getRGB(x, y);
        int r1 = (rgb1 >> 16) & 0xff;
        int g1 = (rgb1 >>  8) & 0xff;
        int b1 = (rgb1      ) & 0xff;
        int r2 = (rgb2 >> 16) & 0xff;
        int g2 = (rgb2 >>  8) & 0xff;
        int b2 = (rgb2      ) & 0xff;
        diff += Math.abs(r1 - r2);
        diff += Math.abs(g1 - g2);
        diff += Math.abs(b1 - b2);
      }
    }
    double n = width1 * height1 * 3;
    double p = diff / n / 255.0;
    System.out.println("diff percent: " + (p * 100.0));
        System.out.println("less :"+((p * 100.0)<30));
        
        if((p * 100.0)<20){
            diffList.add(im);
        }
    }
    }    
        
        return diffList;
    }
    
    
    public boolean isDuplicate(Images img){
        boolean ok=true;
        
        BufferedImage img1 = null;
    BufferedImage img2 = null;
    try {
      
      img1 = ImageIO.read(new File("D:\\Windows-Use\\Android\\JetBrainWorkStation\\AndroidSyncDB\\AndroidImageLK\\build\\web\\"+img.getImg_url()));
      
    } catch (IOException e) {
      e.printStackTrace();
    }
    List<Images> list=new ImagesDAOImp().listAllImages();
    for(Images im:list){
        if(im.getImages_id()!=img.getImages_id()){
     try {
         System.out.println("img ID: "+im.getImages_id());
         System.out.println("title: "+im.getTitle());
          img2 = ImageIO.read(new File("D:\\Windows-Use\\Android\\JetBrainWorkStation\\AndroidSyncDB\\AndroidImageLK\\build\\web\\"+im.getImg_url()));
         
     } catch (IOException ex) {
            ex.printStackTrace();
     }
     
    int width1 = img1.getWidth(null);
    int width2 = img2.getWidth(null);
    int height1 = img1.getHeight(null);
    int height2 = img2.getHeight(null);
    if ((width1 != width2) || (height1 != height2)) {
      System.err.println("Error: Images dimensions mismatch");
      System.exit(1);
    }
    long diff = 0;
    for (int y = 0; y < height1; y++) {
      for (int x = 0; x < width1; x++) {
        int rgb1 = img1.getRGB(x, y);
        int rgb2 = img2.getRGB(x, y);
        int r1 = (rgb1 >> 16) & 0xff;
        int g1 = (rgb1 >>  8) & 0xff;
        int b1 = (rgb1      ) & 0xff;
        int r2 = (rgb2 >> 16) & 0xff;
        int g2 = (rgb2 >>  8) & 0xff;
        int b2 = (rgb2      ) & 0xff;
        diff += Math.abs(r1 - r2);
        diff += Math.abs(g1 - g2);
        diff += Math.abs(b1 - b2);
      }
    }
    double n = width1 * height1 * 3;
    double p = diff / n / 255.0;
    System.out.println("diff percent: " + (p * 100.0));
        System.out.println("less :"+((p * 100.0)<30));
        
        if((p * 100.0)==0.0){
            ok=false;
            break;
        }
    }
    }    
        
       
        
        return ok;
    }
    
}

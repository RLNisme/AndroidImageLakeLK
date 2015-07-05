/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagelake.android.upload;

import com.imagelake.control.CreditsDAOImp;
import com.imagelake.control.DBFactory;
import com.imagelake.control.ImagesDAOImp;
import com.imagelake.model.Credits;
import com.imagelake.model.Images;
import com.imagelake.model.ImagesSub;
import com.imagelake.model.SliceImage;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author Lakmal
 */
public class CreateImages {

    private static int IMG_WIDTH = 0;
    private static int IMG_HEIGHT = 0;

    public String col1;
    public String col2;
    public String col3;
    public String col4;
    public String col5;
    public String col6;
    public String col7;
    public String col8;
    public String col9;
    public String dominate;
    public String imgname;
    public String dimension;
    public String imgtit;
    public String cat;
    public String uid;
    public String date;
    public int imgid;

    public boolean h = false;
        //public int creditId=0;

    public boolean sliceImages(String color1, String color2, String color3, String color4, String color5,
            String color6, String color7, String color8, String color9, String dominate, String imgname, String dimension, String imgtit, String cat, String uid, String date) throws IOException {

        col1 = color1;
        col2 = color2;
        col3 = color3;
        col4 = color4;
        col5 = color5;
        col6 = color6;
        col7 = color7;
        col8 = color8;
        col9 = color9;
        this.dominate = dominate;
        this.imgname = imgname;
        this.dimension = dimension;
        this.imgtit = imgtit;
        this.cat = cat;
        this.uid = uid;
        this.date = date;

        String img[] = dimension.split(" x ");
        int width = Integer.parseInt(img[0]);
        int height = Integer.parseInt(img[1]);

        String name[] = imgname.split(".jpg");
        System.out.println("image Name:" + name[0]);

        System.out.println("Width:" + img[0]);
        System.out.println("Height:" + img[1]);
//          
        CreditsDAOImp cdi = new CreditsDAOImp();
//            if(width >= 3840 && height >=2160){
//                  System.out.println("width >= 3840 && height >=2160");
//                   System.out.println("Width:"+img[0]);
//                 System.out.println("Height:"+img[1]);
//                 
//                 //Original size
//                
//                //400 x 300
//                minSlice(400, 300, name[0]);
//                //slice(960, 540, name[0]);
//                //slice(1024, 576, name[0]);
//                //slice(1280, 720, name[0]);
//                //slice(1366, 768, name[0]);
//                slice(1600, 900, name[0]);
//                slice(1920, 1080, name[0]);
//                slice(2048, 1152, name[0]);
//                slice(2400, 1350, name[0]);
//                slice(2560, 1440, name[0]);
//                slice(2880, 1620, name[0]);
//                slice(3554, 1999, name[0]);
//                slice(width, height, name[0]);
//            }
//            
//            else if(width >= 2880 && height >=1620){//XXX-Large
//                System.out.println("width >= 2880 && height >=1620");
//                System.out.println("Width:"+img[0]);
//            System.out.println("Height:"+img[1]);
//            
//          
//                minSlice(400, 300, name[0]);
//                //slice(960, 540, name[0]);
//                //slice(1024, 576, name[0]);
//               // slice(1280, 720, name[0]);
//                slice(1366, 768, name[0]);
//                slice(1600, 900, name[0]);
//                slice(1920, 1080, name[0]);
//                slice(2048, 1152, name[0]);
//                slice(2400, 1350, name[0]);
//                slice(2560, 1440, name[0]);
//                slice(width, height, name[0]);
//            }
//            else if(width >= 2400 && height >=1350){
//                System.out.println("width >= 2400 && height >=1350");
//            System.out.println("Width:"+img[0]);
//            System.out.println("Height:"+img[1]);
//            
//     
//                //400x300
//                minSlice(400, 300, name[0]);
//                //slice(960, 540, name[0]);
//                //slice(1024, 576, name[0]);
//                slice(1280, 720, name[0]);
//                slice(1366, 768, name[0]);
//                slice(1600,900, name[0]);
//                slice(1920, 1080, name[0]);
//                slice(2048, 1152, name[0]);
//                slice(width, height, name[0]);
//            }
//            else if(width >= 2048 && height >=1152){
//                System.out.println("width >= 2048 && height >=1152");
//            System.out.println("Width:"+img[0]);
//            System.out.println("Height:"+img[1]);
//            
//          
//                //400x300
//                minSlice(400, 300, name[0]);
//               // slice(960, 590, name[0]);
//                slice(1024, 576, name[0]);
//                slice(1280,720, name[0]);
//                slice(1366, 768, name[0]);
//                slice(1920, 1080, name[0]);
//                slice(width, height, name[0]);
//                
//            }
//            else if(width >= 1920 && height >=1080){
//                System.out.println("width >= 1920 && height >=1080");
//            System.out.println("Width:"+img[0]);
//            System.out.println("Height:"+img[1]);
//            
//            
//                //400x300
//                minSlice(400, 300, name[0]);
//                //slice(960,590, name[0]);
//                //slice(1024,576, name[0]);
//                slice(1280, 720, name[0]);
//                slice(1366,768, name[0]);
//                slice(1600, 900, name[0]);
//                slice(width, height, name[0]);
//                
//            }
//            else if(width >= 1366 && height >=768){
//                System.out.println("width >= 1366 && height >=768");
//            System.out.println("Width:"+img[0]);
//            System.out.println("Height:"+img[1]);
//            
//            
//           
//                //400x300
//                minSlice(400, 300, name[0]);
//                slice(960, 540, name[0]);
//                slice(1280,720, name[0]);
//                slice(width, height, name[0]);
//            }
//         
//            else if(width >= 1024 && height >=576){
//                System.out.println("width >= 1024 && height >=576");
//            System.out.println("Width:"+img[0]);
//            System.out.println("Height:"+img[1]);
//            
//            //400x300
//                minSlice(400, 300, name[0]);
//                slice(960, 540, name[0]);
//                slice(width, height, name[0]);
//                
//            }
//            else if(width >= 960 && height >=540){
//                System.out.println("width >= 960 && height >=540");
//            System.out.println("Width:"+img[0]);
//            System.out.println("Height:"+img[1]);
//            h=false;
//            }
//            else if(width < 960 && height < 540){
//                System.out.println("width < 960 && height < 540");
//            System.out.println("Width:"+img[0]);
//            System.out.println("Height:"+img[1]);
//            h=false;
//            }else{
//                System.out.println("else");
//            System.out.println("Width:"+img[0]);
//            System.out.println("Height:"+img[1]);
//            h=false;
//            }
//        
//        
        if (width >= 3840 && height >= 2160) {

            minSlice(400, 300, name[0]);

            List<SliceImage> li = cdi.getTypeSliceList(1, 1);
            for (SliceImage sliceImage : li) {
                Credits cd = cdi.getCreditDetails(sliceImage.getCredit_id());
                slice(cd.getWidth(), cd.getHeight(), name[0], cd.getCredit_id());
            }
        } else if (width >= 2880 && height >= 1620) {

            minSlice(400, 300, name[0]);

            List<SliceImage> li = cdi.getTypeSliceList(2, 1);
            for (SliceImage sliceImage : li) {
                Credits cd = cdi.getCreditDetails(sliceImage.getCredit_id());
                slice(cd.getWidth(), cd.getHeight(), name[0], cd.getCredit_id());
            }

        } else if (width >= 2400 && height >= 1350) {
            minSlice(400, 300, name[0]);

            List<SliceImage> li = cdi.getTypeSliceList(3, 1);
            for (SliceImage sliceImage : li) {
                Credits cd = cdi.getCreditDetails(sliceImage.getCredit_id());
                slice(cd.getWidth(), cd.getHeight(), name[0], cd.getCredit_id());
            }

        } else if (width >= 2048 && height >= 1152) {
            minSlice(400, 300, name[0]);

            List<SliceImage> li = cdi.getTypeSliceList(4, 1);
            for (SliceImage sliceImage : li) {
                Credits cd = cdi.getCreditDetails(sliceImage.getCredit_id());
                slice(cd.getWidth(), cd.getHeight(), name[0], cd.getCredit_id());
            }

        } else if (width >= 1920 && height >= 1080) {
            minSlice(400, 300, name[0]);

            List<SliceImage> li = cdi.getTypeSliceList(5, 1);
            for (SliceImage sliceImage : li) {
                Credits cd = cdi.getCreditDetails(sliceImage.getCredit_id());
                slice(cd.getWidth(), cd.getHeight(), name[0], cd.getCredit_id());
            }

        } else if (width >= 1366 && height >= 768) {
            minSlice(400, 300, name[0]);

            List<SliceImage> li = cdi.getTypeSliceList(6, 1);
            for (SliceImage sliceImage : li) {
                Credits cd = cdi.getCreditDetails(sliceImage.getCredit_id());
                slice(cd.getWidth(), cd.getHeight(), name[0], cd.getCredit_id());
            }

        } else if (width >= 1024 && height >= 576) {
            minSlice(400, 300, name[0]);

            List<SliceImage> li = cdi.getTypeSliceList(7, 1);
            for (SliceImage sliceImage : li) {
                Credits cd = cdi.getCreditDetails(sliceImage.getCredit_id());
                slice(cd.getWidth(), cd.getHeight(), name[0], cd.getCredit_id());
            }

        } else if (width >= 960 && height >= 540) {
            System.out.println("width >= 960 && height >=540");
            System.out.println("Width:" + img[0]);
            System.out.println("Height:" + img[1]);
            h = false;
        } else if (width < 960 && height < 540) {
            System.out.println("width < 960 && height < 540");
            System.out.println("Width:" + img[0]);
            System.out.println("Height:" + img[1]);
            h = false;
        } else {
            System.out.println("else");
            System.out.println("Width:" + img[0]);
            System.out.println("Height:" + img[1]);
            h = false;
        }

        return h;
    }

    public void slice(int wid, int hig, String imgname, int creditid) throws IOException {

        IMG_WIDTH = wid;
        IMG_HEIGHT = hig;

        String newpath = "C:\\temp\\uploads\\" + imgname + "(" + wid + "x" + hig + ").jpg";
        String newpath2 = "D:\\Windows-Use\\Android\\JetBrainWorkStation\\AndroidSyncDB\\AndroidImageLK\\build\\web\\uploads\\"+imgname+ "(" + wid + "x" + hig + ").jpg";
        BufferedImage originalImage = ImageIO.read(new File("D:\\Windows-Use\\Android\\JetBrainWorkStation\\AndroidSyncDB\\AndroidImageLK\\build\\web\\uploads\\" + imgname + ".jpg"));
        int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

        BufferedImage resizeImageHintJpg = resizeImageWithHint(originalImage, type);
        ImageIO.write(resizeImageHintJpg, "jpg", new File(newpath));
        ImageIO.write(resizeImageHintJpg, "jpg", new File(newpath2));    
        if (imgid != 0) {

            ImagesSub subImage = new ImagesSub();
            subImage.setCredits(creditid);
            subImage.setDimention(wid + " x " + hig);
            subImage.setImages_image_id(imgid);
            subImage.setImg_blob(null);
            subImage.setImg_url(newpath);
            subImage.setPrice(Double.valueOf(0));
            subImage.setSize_long(0);
            subImage.setSize_string(null);

            boolean ok = new ImagesDAOImp().inserSubImages(subImage);
            if (ok) {
                h = true;
            } else {
                h = false;
            }
        } else {
            h = false;
        }

    }

    public void minSlice(int wid, int hig, String imgname) throws IOException {

        IMG_WIDTH = wid;
        IMG_HEIGHT = hig;
        String originalpath = "D:\\Windows-Use\\Android\\JetBrainWorkStation\\AndroidSyncDB\\AndroidImageLK\\build\\web\\uploads\\" + imgname + ".jpg";
        String newPath = "D:\\Windows-Use\\Android\\JetBrainWorkStation\\AndroidSyncDB\\AndroidImageLK\\build\\web\\uploads\\" + imgname + "1.jpg";

        BufferedImage originalImage = ImageIO.read(new File(originalpath));
        int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

        BufferedImage resizeImageHintJpg = resizeImageWithHint(originalImage, type);
        ImageIO.write(resizeImageHintJpg, "jpg", new File(newPath));

        new WaterMark().addMark(newPath);
        //creditId=0;
        Images im = new Images();
        im.setCategories_category_id(Integer.parseInt(cat));
        im.setColor_1(col1);
        im.setColor_2(col2);
        im.setColor_3(col3);
        im.setColor_4(col4);
        im.setColor_5(col5);
        im.setColor_6(col6);
        im.setColor_7(col7);
        im.setColor_8(col8);
        im.setColor_9(col9);
        im.setDate(date);
        im.setDominate_color(dominate);
        im.setImage_state_image_state_id(3);
        im.setImg_blob(null);
        im.setImg_url("uploads/" + imgname + "1.jpg");
        im.setKey_wrods(null);
        im.setTitle(imgtit);
        im.setUser_user_id(Integer.parseInt(uid));
        im.setView(0);

        boolean add = new ImagesDAOImp().insertImage(im);
        System.out.println(add);
        if (add) {
            imgid = new ImagesDAOImp().checkImagesId(dominate, col1, col2, col3, col4, col5, col6, col7, col8, col9);
        } else {
        }

    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int type) {
        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.dispose();

        return resizedImage;
    }

    private static BufferedImage resizeImageWithHint(BufferedImage originalImage, int type) {

        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.dispose();
        g.setComposite(AlphaComposite.Src);

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        return resizedImage;
    }

}

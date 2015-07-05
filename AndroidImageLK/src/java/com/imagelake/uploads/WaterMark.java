/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.uploads;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Lakmal
 */
public class WaterMark {
        public void addMark(String minImagePath){
            try {
                
        BufferedImage image = ImageIO.read(new File(minImagePath));
        BufferedImage overlay = ImageIO.read(new File("C:\\Users\\Nalin\\Documents\\NetBeansProjects\\ImageLK\\web\\img\\watermark2.png"));

        // create the new image, canvas size is the max. of both image sizes
        int w = Math.max(image.getWidth(), overlay.getWidth());
        int h = Math.max(image.getHeight(), overlay.getHeight());
        BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        // paint both images, preserving the alpha channels
        Graphics g = combined.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.drawImage(overlay, 10, 0, null);

        ImageIO.write(combined, "PNG", new File(minImagePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}

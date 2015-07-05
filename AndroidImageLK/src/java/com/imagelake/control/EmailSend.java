/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.CartHasImages;
import com.imagelake.model.Credits;
import com.imagelake.model.CreditsPackage;
import com.imagelake.model.DownloadCount;
import com.imagelake.model.Images;
import com.imagelake.model.ImagesSub;
import com.imagelake.model.SubscriptionPackage;
import com.imagelake.model.User;
import com.imagelake.model.UserHasPackage;
import java.security.Security;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 *
 * @author Lakmal
 */
public class EmailSend {
    
        Date d=new Date();
        SimpleDateFormat sd1=new SimpleDateFormat("yyyy-MM-dd");
        String date=sd1.format(d);
        
        
        
        public void sendSignUpEmail(String to,String un){
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        Properties prop = new Properties();
        
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.debug", "true");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.socketFactory.fallback", "false");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.setProperty("mail.smtp.starttls.enable", "true");
        
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("vivajavaee@gmail.com", "vivajavaEE123");
            }
        };
        Session session = Session.getDefaultInstance(prop, auth);

        try {
            Message mail = new MimeMessage(session);
            mail.setFrom(new InternetAddress("vivajavaee@gmail.com"));
            
            InternetAddress sTo = new InternetAddress(to);
            mail.setRecipient(Message.RecipientType.TO, sTo);
            
            InternetAddress sCC = new InternetAddress("navarathnelakmalravindra@gmail.com");
            mail.setRecipient(Message.RecipientType.CC, sCC);
//            
//            InternetAddress sBCC = new InternetAddress(txtBCC.getText());
//            mail.setRecipient(Message.RecipientType.BCC, sBCC);
            
            mail.setSubject("Congratulation!");
           
            String msg=" <div style=\"font-family:HelveticaNeue-Light,Arial,sans-serif;background-color:#eeeeee\">\n" +
"\n" +
"<table align=\"center\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#eeeeee\">\n" +
"	<tbody><tr>\n" +
"		<td>\n" +
"			<table align=\"center\" width=\"750px\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#eeeeee\" style=\"width:750px!important\">\n" +
"			<tbody><tr>\n" +
"			<td>\n" +
"			<table width=\"690\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#eeeeee\">\n" +
"\n" +
"\n" +
"				\n" +
"				<tbody><tr>\n" +
"					<td colspan=\"3\" height=\"80\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#eeeeee\" style=\"padding:0;margin:0;font-size:0;line-height:0\">\n" +
"						<table width=\"690\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"							<tbody><tr>\n" +
"								<td width=\"0\"></td>\n" +
"                                                                <style type=\"text/css\">\n" +
"                                                                        #message-logo{\n" +
"                                                                            \n" +
"                                                                            height:50px;\n" +
"                                                                            \n" +
"                                                                            background: rgba(51,51,51,0.85);\n" +
"                                                                            padding: 14px 35px 12px;\n" +
"                                                                            line-height: 50px;\n" +
"                                                                            color:#FFF;\n" +
"                                                                            font-size: 30px;\n" +
"                                                                            font-weight: bolder;\n" +
"                                                                            text-align:center;\n" +
"                                                                            }\n" +
"                                                                    </style> \n" +
"                                                                    \n" +
"								<td align=\"left\" valign=\"middle\" style=\"padding:0;margin:0;font-size:0;line-height:0\">\n" +
"                                                                    <div id=\"message-logo\">\n" +
"                                                                    ImageLakelk\n" +
"                                                                     </div></td>\n" +
"								<td width=\"0\"></td>\n" +
"							</tr>\n" +
"						</tbody></table>\n" +
"					</td>\n" +
"				</tr>\n" +
"\n" +
"								\n" +
"\n" +
"\n" +
"								\n" +
"				<tr bgcolor=\"#ffffff\">\n" +
"					<td colspan=\"3\" align=\"center\">\n" +
"						<table width=\"630\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"							<tbody><tr><td colspan=\"3\" height=\"60\"></td></tr> \n" +
"							<tr>\n" +
"								<td width=\"50\"></td>\n" +
"								<td align=\"center\">\n" +
"									<h1 style=\"font-family:HelveticaNeue-Light,arial,sans-serif;font-size:32px;color:#404040;line-height:40px;font-weight:lighter;margin:0;padding:0\">Congratulation!</h1>\n" +
"								</td>\n" +
"								<td width=\"50\"></td>\n" +
"							</tr>\n" +
"							<tr><td colspan=\"3\" height=\"60\"></td></tr> \n" +
"							<tr>\n" +
"                                                            <td colspan=\"5\"><img src=\"https://ci4.googleusercontent.com/proxy/8XqbDUc-4P01SWUYw8TKqB6pbi_3cYj4JLsp7eV7eM3KLOJYxLom_pz3Ik7n2YVDCpAPuEVRFyTsDFmIQZJMjfo3Uyu_3S86ZlB-AnChm4EVTIy6XWndwg5zc7jXkwfN-8vdgq5gYCe14nlaPg1cCQrteh1pD1oZJA=s0-d-e1-ft#https://dtqn2osro0nhk.cloudfront.net/static/images/email/build/d3eb6c577ab2f08546e4949a77604a0a.png\" alt=\"\" class=\"CToWUd a6T\" tabindex=\"0\"><div class=\"a6S\" dir=\"ltr\" style=\"opacity: 0.01;\"><div id=\":19u\" class=\"T-I J-J5-Ji aQv T-I-ax7 L3 a5q\" title=\"Download\" role=\"button\" tabindex=\"0\" aria-label=\"Download attachment \" data-tooltip-class=\"a1V\"><div class=\"aSK J-J5-Ji aYr\"></div></div></div></td>\n" +
"							</tr>\n" +
"							<tr><td colspan=\"3\" height=\"60\"></td></tr> \n" +
"						</tbody></table>\n" +
"					</td>\n" +
"				</tr>\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"								\n" +
"				<tr bgcolor=\"#ffffff\">\n" +
"					<td width=\"30\"></td>\n" +
"					<td>\n" +
"						<table width=\"630\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"							<tbody><tr>\n" +
"								<td colspan=\"4\">\n" +
"									<h2 style=\"color:#404040;font-size:22px;font-weight:bold;line-height:26px;padding:0;margin:0\">Hi "+un+",</h2>\n" +
"									<div style=\"line-height:22px;padding:0;margin:0\">&nbsp;</div>\n" +
"									<div style=\"color:#404040;font-size:16px;line-height:22px;padding:0;margin:0\">\n" +
"									You have successfully registered.Explore millions of images you can only get from IamgeLake.\n" +
"									</div>\n" +
"									<div style=\"line-height:22px;padding:0;margin:0\">&nbsp;</div>\n" +
"									<div style=\"color:#404040;font-size:16px;line-height:22px;padding:0;margin:0\">\n" +
"									<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align=\"center\"> \n" +
"													<tbody><tr> \n" +
"														<td align=\"center\" height=\"36\" bgcolor=\"#00a3df\" style=\"border-radius:4px;padding-left:20px;padding-right:20px;font-weight:bold;font-family:Helvetica,Arial,sans-serif;color:rgb(255,255,255);background-color:rgb(0,163,223)\">\n" +
"															<span style=\"font-family:'proxima_nova_softmedium',Helvetica;font-weight:bold\">\n" +
"																<a href=\"http://localhost:8084/ImageLK/index.jsp\" style=\"color:#ffffff;font-size:16px;text-decoration:none;line-height:40px;width:100%\" target=\"_blank\">Start swim!</a>\n" +
"															</span>\n" +
"														</td> \n" +
"													</tr> \n" +
"												</tbody></table> \n" +
"									</div>\n" +
"								</td>\n" +
"							</tr>\n" +
"							\n" +
"							<tr><td colspan=\"4\" height=\"30\" style=\"border-bottom:1px solid #e5e5e5\"></td></tr>\n" +
"							\n" +
"							<tr><td colspan=\"4\" height=\"60\" style=\"padding:0;margin:0;font-size:0;line-height:0\"></td></tr>\n" +
"						</tbody></table>\n" +
"					</td>\n" +
"					<td width=\"30\" bgcolor=\"#ffffff\"></td>\n" +
"				</tr>\n" +
"\n" +
"\n" +
"				\n" +
"			</tbody></table>\n" +
"		</td>\n" +
"	</tr>	\n" +
"    \n" +
"\n" +
"   <tr>\n" +
"        <td>\n" +
"            <table width=\"690\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#eeeeee\">\n" +
"                <tbody><tr><td colspan=\"2\" height=\"30\"></td></tr>\n" +
"                <tr>\n" +
"                  <td width=\"360\" valign=\"top\">\n" +
"                    <div style=\"color:#a3a3a3;font-size:12px;line-height:12px;padding:0;margin:0\">© 2015 ImageLakeLK Pty Limited. All Rights Reserved.</div>\n" +
"                    <div style=\"line-height:5px;padding:0;margin:0\">&nbsp;</div>\n" +
"		           \n" +
"                    \n" +
"                  </td>\n" +
"                  <td align=\"right\" valign=\"top\">\n" +
"                    \n" +
"                  </td>\n" +
"                </tr>\n" +
"                <tr><td colspan=\"2\" height=\"5\"></td></tr>\n" +
"                <tr>\n" +
"                        <td>\n" +
"	                  \n" +
"	                </td>\n" +
"	                <td align=\"right\">\n" +
"               		\n" +
"               		</td>\n" +
"                </tr>\n" +
"\n" +
"                <tr>\n" +
"                  <td colspan=\"2\" height=\"80\"></td>\n" +
"                </tr>\n" +
"		            </tbody></table>\n" +
"		        </td>\n" +
"		    </tr>\n" +
"		</tbody></table>\n" +
"		</td>\n" +
"	</tr>\n" +
"</tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
"</div></div>";
            
            mail.setContent(msg, "text/html");
            //Transport transport = session.getTransport("smtp");
            Transport.send(mail);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        }
        
        
        public void sendDownloadList(HttpSession ses){
        
        User us=(User)ses.getAttribute("user");
        List<CartHasImages> clist=(ArrayList<CartHasImages>)ses.getAttribute("cartimages");
        ImagesDAOImp idi=new ImagesDAOImp();
        CreditsDAOImp cdi=new CreditsDAOImp();
            Images im=null;
            ImagesSub is=null;
            Credits cr=null;
            
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        Properties prop = new Properties();
        
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.debug", "true");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.socketFactory.fallback", "false");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.setProperty("mail.smtp.starttls.enable", "true");
        
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("vivajavaee@gmail.com", "vivajavaEE123");
            }
        };
        Session session = Session.getDefaultInstance(prop, auth);

        try {
            Message mail = new MimeMessage(session);
            mail.setFrom(new InternetAddress("vivajavaee@gmail.com"));
            
            InternetAddress sTo = new InternetAddress(us.getEmail());
            mail.setRecipient(Message.RecipientType.TO, sTo);
            
            InternetAddress sCC = new InternetAddress("navarathnelakmalravindra@gmail.com");
            mail.setRecipient(Message.RecipientType.CC, sCC);
//            
//            InternetAddress sBCC = new InternetAddress(txtBCC.getText());
//            mail.setRecipient(Message.RecipientType.BCC, sBCC);
            
            mail.setSubject("Download List("+date+")");
            
            
            String report="<div id=\":mi\" class=\"ii gt m14b715ffa3dda497 adP adO\"><div id=\":mj\" class=\"a3s\" style=\"overflow: hidden;\"><u></u>\n" +
"\n" +
"  \n" +
"\n" +
"                <div style=\"font-family:HelveticaNeue-Light,Arial,sans-serif;\">\n" +
"    <table width=\"100%\" height=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"\">\n" +
"    <tbody><tr>\n" +
"      <td>\n" +
"        <table width=\"650\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"#ffffff\" style=\"border:10px solid #f2dede\">\n" +
"          <tbody><tr>\n" +
"            <td align=\"left\" valign=\"top\">\n" +
"              <table width=\"650\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-bottom:1px solid #cccccc\">\n" +
"                <tbody><tr>\n" +
"                  <td width=\"275\" align=\"left\" valign=\"middle\" style=\"padding:30px;background: rgba(51,51,51,0.85);color:#FFF;\">\n" +
"                    \n" +
"                     <div id=\"message-logo\" style=\" height:50px;\n" +
"                                                                            \n" +
"                                                                            \n" +
"                                                                            padding: 14px 35px 12px;\n" +
"                                                                            line-height: 50px;\n" +
"                                                                            color:#FFF;\n" +
"                                                                            font-size: 30px;\n" +
"                                                                            font-weight: bolder;\n" +
"                                                                            text-align:center;\">\n" +
"                                                                    ImageLakelk\n" +
"                                                                     </div>                                               \n" +
"                  </td>\n" +
"                  <td width=\"255\" align=\"right\" valign=\"middle\" style=\"font-family:Arial;font-size:14px;background: rgba(51,51,51,0.85);color:#FFF;padding:30px\">\n" +
"                    <strong>Notification</strong><br>"+date+"\n" +
"                  </td>\n" +
"                </tr>\n" +
"              </tbody></table>\n" +
"            </td>\n" +
"          </tr>\n" +
"          <tr>\n" +
"            <td align=\"left\" valign=\"top\">\n" +
"              <table width=\"650\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"                <tbody><tr>\n" +
"                  <td colspan=\"3\" width=\"100%\" height=\"20\">\n" +
"                  </td>\n" +
"                </tr>\n" +
"                <tr>\n" +
"                  <td width=\"20\">\n" +
"                  </td>\n" +
"                  <td width=\"610\">\n" +
"                    <p style=\"font-family:Arial;font-size:20px;margin-bottom:0.5em;margin-top:0\">Hi "+us.getUser_name()+",</p>\n" +
"                  </td>\n" +
"                  <td width=\"20\">\n" +
"                  </td>\n" +
"                </tr>\n" +
"                <tr>\n" +
"                  <td width=\"20\">\n" +
"                  </td>\n" +
"                  <td width=\"610\" style=\"font-family:Arial;font-size:14px;padding-bottom:20px\">\n" +
"\n" +
"\n" +
"<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"font-family:Arial,Helvetica,Sans-Serif\">\n" +
"<tbody>\n" +
"    <tr bgcolor=\"#b94a48\">\n" +
"    <td width=\"10\" bgcolor=\"#b94a48\" style=\"border-top-left-radius:3px;line-height:0;font-size:1px\">\n" +
"    </td>\n" +
"    <td width=\"460\" bgcolor=\"#b94a48\" style=\"margin:0;padding:0\">\n" +
"        <h3 style=\"color:#fff;font-size:14px;line-height:1em;font-weight:normal;margin:10px 0\">Image Title</h3>\n" +
"    </td>\n" +
"    <td width=\"460\" bgcolor=\"#b94a48\" style=\"margin:0;padding:0\" align=\"center\">\n" +
"        <h3 style=\"color:#fff;font-size:14px;line-height:1em;font-weight:normal;margin:10px 0\">Size</h3>\n" +
"    </td>\n" +
"    <td width=\"110\" bgcolor=\"#b94a48\" align=\"center\">\n" +
"        <h3 style=\"color:#fff;font-size:14px;line-height:1em;font-weight:normal;margin:10px 0\">Credit</h3>\n" +
"    </td>\n" +
"    <td width=\"10\" bgcolor=\"#b94a48\" style=\"border-top-right-radius:3px;line-height:0;font-size:1px\">\n" +
"    </td>\n" +
"</tr>\n" +
"\n" +
"<tr bgcolor=\"#005D88\">\n" +
"    <td width=\"10\" style=\"line-height:0;font-size:1px;border-left:1px solid #ccc\">\n" +
"    <td width=\"460\" style=\"margin:0;padding:0;font-size:1px;line-height:0\"></td>\n" +
"    <td width=\"110\" style=\"margin:0;padding:0;font-size:1px;line-height:0\"></td>\n" +
"    <td width=\"10\" style=\"line-height:0;font-size:1px;border-right:1px solid #ccc\">\n" +
"    </td>\n" +
"</tr>\n";

      int rows=0;
      int total=0;
            for (CartHasImages cartHasImages : clist) {
                im=idi.getImageDetail(cartHasImages.getImg_id());
                is=idi.getSubImage(cartHasImages.getSubimg_id());
                cr=cdi.getCreditDetails(is.getCredits());
                total+=cr.getCredits();
                if(rows%2==0){
                    report+="<tr>\n" +
"    <td style=\"line-height:0;font-size:1px;border-left:1px solid #ccc\">\n" +
"    </td>\n" +
"    <td style=\"padding:10px 0 15px 0;margin:0\">\n" +
"        <div style=\"overflow:hidden;width:460px;word-wrap:break-word\">\n" +
"\n" +
"            <p style=\"color:#333;font-size:12px;line-height:1.5em;margin:2px 0;padding:0\">"+im.getTitle()+"</p>\n" +
"            \n" +
"            \n" +
"        </div>\n" +
"    </td>\n" +
"    <td align=\"center\" valign=\"top\" style=\"padding:14px 10px;margin:0\">\n" +
"        <p style=\"font-size:14px;line-height:1.2em;margin:0 0 10px 0\">"+is.getDimention()+"</p>\n" +
"        \n" +
"    </td>\n" +
"    <td align=\"center\" valign=\"top\" style=\"padding:14px 10px;margin:0\">\n" +
"        <p style=\"font-size:14px;line-height:1.2em;margin:0 0 10px 0\">"+cr.getCredits()+"</p>\n" +
"        \n" +
"    </td>\n" +
"    <td style=\"line-height:0;font-size:1px;border-right:1px solid #ccc\">\n" +
"    </td>\n" +
"</tr>\n";

                }else{
                    
                    report+="<tr bgcolor=\"#f3f8fb\">\n" +
"    <td style=\"line-height:0;font-size:1px;border-left:1px solid #ccc\">\n" +
"    </td>\n" +
"    <td style=\"padding:10px 0 15px 0;margin:0\">\n" +
"        <div style=\"overflow:hidden;width:460px;word-wrap:break-word\">\n" +
"            <p style=\"color:#333;font-size:12px;line-height:1.5em;margin:2px 0;padding:0\">"+im.getTitle()+"</p>\n" +
"            \n" +
"        </div>\n" +
"    </td>\n" +
"    <td align=\"center\" valign=\"top\" style=\"padding:14px 10px;margin:0\">\n" +
"        <p style=\"font-size:14px;line-height:1.2em;margin:0 0 10px 0\">"+is.getDimention()+"</p>\n" +
"        \n" +
"    </td>\n" +
"    <td align=\"center\" valign=\"top\" style=\"padding:14px 10px;margin:0\">\n" +
"        <p style=\"font-size:14px;line-height:1.2em;margin:0 0 10px 0\">"+cr.getCredits()+"</p>\n" +
"        \n" +
"    </td>\n" +
"    <td style=\"line-height:0;font-size:1px;border-right:1px solid #ccc\">\n" +
"    </td>\n" +
"</tr>\n";
                    
                }
                System.gc();
                rows++;
            }
    
 

 report+="</tbody>\n" +
"</table>\n" +
"\n" +
"\n" +
"<br><br>\n" +
"\n" +
"\n" +
"<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"font-family:Arial,Helvetica,Sans-Serif\">\n" +
"<tbody><tr bgcolor=\"#b94a48\">\n" +
"    <td width=\"10\" bgcolor=\"#b94a48\" style=\"border-top-left-radius:3px;line-height:0;font-size:1px\">\n" +
"    </td>\n" +
"    <td width=\"460\" bgcolor=\"#b94a48\" style=\"margin:0;padding:0\">\n" +
"        <h3 style=\"color:#fff;font-size:14px;line-height:1em;font-weight:normal;margin:10px 0\"></h3>\n" +
"    </td>\n" +
"    <td width=\"110\" bgcolor=\"#b94a48\" align=\"center\">\n" +
"        <h3 style=\"color:#fff;font-size:14px;line-height:1em;font-weight:normal;margin:10px 0\">Total Credits</h3>\n" +
"    </td>\n" +
"    <td width=\"10\" bgcolor=\"#b94a48\" style=\"border-top-right-radius:3px;line-height:0;font-size:1px\">\n" +
"    </td>\n" +
"</tr>\n" +
"\n" +
"<tr bgcolor=\"#005D88\">\n" +
"    <td width=\"10\" style=\"line-height:0;font-size:1px;border-left:1px solid #ccc\">\n" +
"    <td width=\"460\" style=\"margin:0;padding:0;font-size:1px;line-height:0\"></td>\n" +
"    <td width=\"110\" style=\"margin:0;padding:0;font-size:1px;line-height:0\"></td>\n" +
"    <td width=\"10\" style=\"line-height:0;font-size:1px;border-right:1px solid #ccc\">\n" +
"    </td>\n" +
"</tr>\n" +
"\n" +
"\n" +
"<tr bgcolor=\"#f3f8fb\">\n" +
"    <td style=\"line-height:0;font-size:1px;border-left:1px solid #ccc;border-bottom:1px solid #ccc\">\n" +
"    </td>\n" +
"    <td style=\"padding:10px 0 15px 0;margin:0;border-bottom:1px solid #ccc\">\n" +
"        <div style=\"overflow:hidden;width:460px;word-wrap:break-word\">\n" +
"            \n" +
"           \n" +
"        </div>\n" +
"    </td>\n" +
"    <td align=\"center\" valign=\"top\" style=\"padding:14px 10px;margin:0;border-bottom:1px solid #ccc\">\n" +
"        <p style=\"font-size:14px;line-height:1.2em;margin:0 0 10px 0\">"+total+"</p>\n" +
"        \n" +
"    </td>\n" +
"    <td style=\"line-height:0;font-size:1px;border-right:1px solid #ccc;border-bottom:1px solid #ccc\">\n" +
"    </td>\n" +
"</tr>\n" +
"\n" +
"\n" +
"</tbody></table>\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"<br>\n" +
"\n" +
"\n" +
"                  </td>\n" +
"                  <td width=\"20\"></td>\n" +
"                </tr>\n" +
"              </tbody></table>\n" +
"            </td>\n" +
"          </tr>\n" +
"        </tbody></table>\n" +
"      </td>\n" +
"    </tr>\n" +
"  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
"</div></div><div class=\"adL\">\n" +
"\n" +
"\n" +
"\n" +
"</div></div></div>";
           
            mail.setContent(report, "text/html");
            //Transport transport = session.getTransport("smtp");
            Transport.send(mail);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        }
        
        
        public void sendSubscriptionPurchasePackage(HttpSession ses,int packageid,double total,UserHasPackage uhp){
            User us=(User)ses.getAttribute("user");
            DecimalFormat df = new DecimalFormat("#.00"); 
            SubscriptionPackage sp=new SubscriptionPackageDAOImp().getSubscription(packageid);
            DownloadCount dc=new DownloadCountDAOImp().getCount(sp.getCount_id());
            
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        Properties prop = new Properties();
        
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.debug", "true");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.socketFactory.fallback", "false");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.setProperty("mail.smtp.starttls.enable", "true");
        
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("vivajavaee@gmail.com", "vivajavaEE123");
            }
        };
        Session session = Session.getDefaultInstance(prop, auth);

        try {
            Message mail = new MimeMessage(session);
            mail.setFrom(new InternetAddress("vivajavaee@gmail.com"));
            
            InternetAddress sTo = new InternetAddress(us.getEmail());
            mail.setRecipient(Message.RecipientType.TO, sTo);
            
            InternetAddress sCC = new InternetAddress("navarathnelakmalravindra@gmail.com");
            mail.setRecipient(Message.RecipientType.CC, sCC);
//            
//            InternetAddress sBCC = new InternetAddress(txtBCC.getText());
//            mail.setRecipient(Message.RecipientType.BCC, sBCC);
            
            mail.setSubject("New Subscription package");
            
            String pack="  <div id=\":105\" class=\"ii gt m14b5aeedd633726b adP adO\"><div id=\":101\" class=\"a3s\" style=\"overflow: hidden;\"><u></u>\n" +
"\n" +
"<div style=\"font-family:HelveticaNeue-Light,Arial,sans-serif;background-color:#eeeeee\">\n" +
"<table align=\"center\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#eeeeee\">\n" +
"	<tbody><tr>\n" +
"		<td>\n" +
"			<table align=\"center\" width=\"750px\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#eeeeee\" style=\"width:750px!important\">\n" +
"			<tbody><tr>\n" +
"			<td>\n" +
"			<table width=\"690\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#eeeeee\">\n" +
"\n" +
"\n" +
"				\n" +
"				<tbody><tr>\n" +
"					<td colspan=\"3\" height=\"80\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#eeeeee\" style=\"padding:0;margin:0;font-size:0;line-height:0\">\n" +
"						<table width=\"690\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"							<tbody><tr>\n" +
"								<td width=\"0\"></td>\n" +
"                                                                <td align=\"left\" valign=\"middle\" style=\"padding:0;margin:0;font-size:0;line-height:0\">\n" +
"                                                                     <div style=\"height:50px;background: rgba(51,51,51,0.85);padding: 14px 35px 12px;\n" +
"                                                                            line-height: 50px;\n" +
"                                                                            color:#FFF;\n" +
"                                                                            font-size: 30px;\n" +
"                                                                            font-weight: bolder;\n" +
"                                                                            text-align:center;\">\n" +
"                                                                    ImageLakelk\n" +
"                                                                     </div>\n" +
"                                                                </td>\n" +
"								<td width=\"0\"></td>\n" +
"							</tr>\n" +
"						</tbody></table>\n" +
"					</td>\n" +
"				</tr>\n" +
"\n" +
"								\n" +
"\n" +
"\n" +
"								\n" +
"				<tr bgcolor=\"#ffffff\">\n" +
"					<td colspan=\"3\" align=\"center\">\n" +
"						<table width=\"630\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"							<tbody><tr><td colspan=\"3\" height=\"60\"></td></tr> \n" +
"							<tr>\n" +
"								<td width=\"80\"></td>\n" +
"								<td align=\"center\">\n" +
"									<h1 style=\"font-family:HelveticaNeue-Light,arial,sans-serif;font-size:32px;color:#404040;line-height:40px;font-weight:lighter;margin:0;padding:0\">You have successfully purchase a image plan!</h1>\n" +
"								</td>\n" +
"								<td width=\"80\"></td>\n" +
"							</tr>\n" +
"							<tr><td colspan=\"3\" height=\"40\"></td></tr> \n" +
"							<tr>\n" +
"								<td colspan=\"5\"><img src=\"https://ci5.googleusercontent.com/proxy/xaaEauYvLO9meEG3mjPBRxWZqaqcupZwig8cROSe-8FMt7MpUEchSyDHgt4lgRHB_9VBZ6yRXf486Zx4srk9IN9mXjLxWai_IntmOrb55qTW51nTirJO_CxrAs9fNUx8K2V9g8OSL16WuJeXT39wSMAPUJnOtgRxLw=s0-d-e1-ft#https://dtqn2osro0nhk.cloudfront.net/static/images/email/build/d1e996e405c2d63d912bf0d286fe2c3c.png\" alt=\"\" class=\"CToWUd\"></td>\n" +
"							</tr>\n" +
"							<tr><td colspan=\"3\" height=\"60\"></td></tr> \n" +
"						</tbody></table>\n" +
"					</td>\n" +
"				</tr>\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"								\n" +
"				<tr bgcolor=\"#ffffff\">\n" +
"					<td width=\"30\"></td>\n" +
"					<td>\n" +
"						<table width=\"630\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"							<tbody><tr>\n" +
"								<td colspan=\"4\" style=\"padding:0;margin:0;font-size:0;line-height:0\">\n" +
"									<h2 style=\"color:#404040;font-size:22px;font-weight:bold;line-height:26px;padding:0;margin:0\">Hi "+us.getUser_name()+",</h2>\n" +
"								</td>\n" +
"							</tr>\n" +
"							<tr><td colspan=\"4\" height=\"10\"></td></tr>\n" +
"							<tr>\n" +
"								<td colspan=\"5\">\n" +
"									<div style=\"color:#404040;font-size:16px;line-height:22px;padding:0;margin:0\">\n" +
"                                                                            We have now activated a subscription image plan for you.It will valid from <span style=\"color: #468847\">"+uhp.getPurchase_date()+"</span> to <span style=\"color: rgb(185, 74, 72);\">"+uhp.getExpire_date()+"</span>.\n" +
"									</div>\n" +
"									<div style=\"line-height:22px;padding:0;margin:0\">&nbsp;</div>\n" +
"									<div style=\"color:#404040;font-size:16px;line-height:22px;padding:0;margin:0\">\n" +
"									You can download "+dc.getCount()+" images ";
                                                                        if(sp.getSubscription_type_id()==1){
                                                                        pack+="daily";
                                                                        }else{
                                                                         pack+="monthly";
                                                                        }
                                                                        pack+=" for "+sp.getDuration()+" months.cost for one image will be $"+df.format(sp.getPer_image())+" and total cost for the package was <u>$"+df.format(total)+"</u>\n";
									if(sp.getDiscount()!=0){
                                                                            pack+=" with "+sp.getDiscount()+"% discount";
                                                                        }
                                                                        pack+="</div>\n" +
									
                                                                      
"								</td>\n" +
"							</tr>\n" +
"							\n" +
"							<tr><td colspan=\"3\" height=\"60\"></td></tr>\n" +
"							\n" +
"							<tr>\n" +
"								<td align=\"center\" colspan=\"5\">\n" +
"									<table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"font-family:HelveticaNeue-Light,Arial,sans-serif;margin:0;padding:0\">\n" +
"										<tbody><tr>\n" +
"											<td align=\"center\" style=\"margin:0;text-align:center\">\n" +
"												<a href=\"localhost:8084/ImageLK/ImagePlans.jsp\" style=\"font-size:18px;line-height:22px;text-decoration:none;color:#ffffff;font-weight:bold;border-radius:2px;background-color:#0096d3;padding:14px 40px;display:block\" target=\"_blank\">More Plans</a>\n" +
"											</td>\n" +
"										</tr>\n" +
"									</tbody></table>\n" +
"								</td>\n" +
"							</tr>\n" +
"							\n" +
"							<tr><td colspan=\"3\" height=\"30\"></td></tr>\n" +
"							\n" +
"							\n" +
"							\n" +
"							<tr><td colspan=\"4\" height=\"30\"></td></tr>\n" +
"							\n" +
"							<tr>\n" +
"								<td colspan=\"5\">\n" +
"									<div style=\"color:#404040;font-size:18px;line-height:24px;padding:0;margin:0\">Regards,</div>\n" +
"									<div style=\"color:#404040;font-size:18px;line-height:24px;font-weight:bold;padding:0;margin:0\">The ImageLakeLk Team.</div>\n" +
"								</td>\n" +
"							</tr>\n" +
"							<tr><td colspan=\"4\" height=\"60\" style=\"padding:0;margin:0;font-size:0;line-height:0\"></td></tr>\n" +
"						</tbody></table>\n" +
"					</td>\n" +
"					<td width=\"30\" bgcolor=\"#ffffff\"></td>\n" +
"				</tr>\n" +
"\n" +
"\n" +
"				\n" +
"			</tbody></table>\n" +
"		</td>\n" +
"	</tr>	\n" +
"    \n" +
"\n" +
"   \n" +
"\n" +
"                <tr>\n" +
"                  <td colspan=\"2\" height=\"80\"></td>\n" +
"                </tr>\n" +
"		            </tbody></table>\n" +
"		        </td>\n" +
"		    </tr>\n" +
"		</tbody></table>\n" +
"		</td>\n" +
"	</tr>\n" +
"</tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
"</div></div><div class=\"adL\">\n" +
"\n" +
"</div></div></div>";
            
              mail.setContent(pack, "text/html");
            //Transport transport = session.getTransport("smtp");
            Transport.send(mail);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
        
        
        
        public void sendCreditPurchasePackage(HttpSession ses,int pckid,double total,UserHasPackage uhp){
            
             User us=(User)ses.getAttribute("user");
             CreditsPackage cp=new CreditPackageDAOImp().getCreditPackage(pckid);
            DecimalFormat df = new DecimalFormat("#.00"); 
            
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        Properties prop = new Properties();
        
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.debug", "true");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.socketFactory.fallback", "false");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.setProperty("mail.smtp.starttls.enable", "true");
        
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("vivajavaee@gmail.com", "vivajavaEE123");
            }
        };
        Session session = Session.getDefaultInstance(prop, auth);

        try {
            Message mail = new MimeMessage(session);
            mail.setFrom(new InternetAddress("vivajavaee@gmail.com"));
            
            InternetAddress sTo = new InternetAddress(us.getEmail());
            mail.setRecipient(Message.RecipientType.TO, sTo);
            
            InternetAddress sCC = new InternetAddress("navarathnelakmalravindra@gmail.com");
            mail.setRecipient(Message.RecipientType.CC, sCC);
//            
//            InternetAddress sBCC = new InternetAddress(txtBCC.getText());
//            mail.setRecipient(Message.RecipientType.BCC, sBCC);
            
            mail.setSubject("New credit package");
            
            String msg="  <div id=\":105\" class=\"ii gt m14b5aeedd633726b adP adO\"><div id=\":101\" class=\"a3s\" style=\"overflow: hidden;\"><u></u>\n" +
"\n" +
"<div style=\"font-family:HelveticaNeue-Light,Arial,sans-serif;background-color:#eeeeee\">\n" +
"<table align=\"center\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#eeeeee\">\n" +
"	<tbody><tr>\n" +
"		<td>\n" +
"			<table align=\"center\" width=\"750px\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#eeeeee\" style=\"width:750px!important\">\n" +
"			<tbody><tr>\n" +
"			<td>\n" +
"			<table width=\"690\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#eeeeee\">\n" +
"\n" +
"\n" +
"				\n" +
"				<tbody><tr>\n" +
"					<td colspan=\"3\" height=\"80\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#eeeeee\" style=\"padding:0;margin:0;font-size:0;line-height:0\">\n" +
"						<table width=\"690\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"							<tbody><tr>\n" +
"								<td width=\"0\"></td>\n" +
"                                                                <td align=\"left\" valign=\"middle\" style=\"padding:0;margin:0;font-size:0;line-height:0\">\n" +
"                                                                     <div style=\"height:50px;background: rgba(51,51,51,0.85);padding: 14px 35px 12px;\n" +
"                                                                            line-height: 50px;\n" +
"                                                                            color:#FFF;\n" +
"                                                                            font-size: 30px;\n" +
"                                                                            font-weight: bolder;\n" +
"                                                                            text-align:center;\">\n" +
"                                                                    ImageLakelk\n" +
"                                                                     </div>\n" +
"                                                                </td>\n" +
"								<td width=\"0\"></td>\n" +
"							</tr>\n" +
"						</tbody></table>\n" +
"					</td>\n" +
"				</tr>\n" +
"\n" +
"								\n" +
"\n" +
"\n" +
"								\n" +
"				<tr bgcolor=\"#ffffff\">\n" +
"					<td colspan=\"3\" align=\"center\">\n" +
"						<table width=\"630\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"							<tbody><tr><td colspan=\"3\" height=\"60\"></td></tr> \n" +
"							<tr>\n" +
"								<td width=\"80\"></td>\n" +
"								<td align=\"center\">\n" +
"									<h1 style=\"font-family:HelveticaNeue-Light,arial,sans-serif;font-size:32px;color:#404040;line-height:40px;font-weight:lighter;margin:0;padding:0\">You have successfully purchase a image plan!</h1>\n" +
"								</td>\n" +
"								<td width=\"80\"></td>\n" +
"							</tr>\n" +
"							<tr><td colspan=\"3\" height=\"40\"></td></tr> \n" +
"							<tr>\n" +
"								<td colspan=\"5\"><img src=\"https://ci5.googleusercontent.com/proxy/xaaEauYvLO9meEG3mjPBRxWZqaqcupZwig8cROSe-8FMt7MpUEchSyDHgt4lgRHB_9VBZ6yRXf486Zx4srk9IN9mXjLxWai_IntmOrb55qTW51nTirJO_CxrAs9fNUx8K2V9g8OSL16WuJeXT39wSMAPUJnOtgRxLw=s0-d-e1-ft#https://dtqn2osro0nhk.cloudfront.net/static/images/email/build/d1e996e405c2d63d912bf0d286fe2c3c.png\" alt=\"\" class=\"CToWUd\"></td>\n" +
"							</tr>\n" +
"							<tr><td colspan=\"3\" height=\"60\"></td></tr> \n" +
"						</tbody></table>\n" +
"					</td>\n" +
"				</tr>\n" +
"\n" +
"\n" +
"\n" +
"\n" +
"								\n" +
"				<tr bgcolor=\"#ffffff\">\n" +
"					<td width=\"30\"></td>\n" +
"					<td>\n" +
"						<table width=\"630\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
"							<tbody><tr>\n" +
"								<td colspan=\"4\" style=\"padding:0;margin:0;font-size:0;line-height:0\">\n" +
"									<h2 style=\"color:#404040;font-size:22px;font-weight:bold;line-height:26px;padding:0;margin:0\">Hi "+us.getUser_name()+",</h2>\n" +
"								</td>\n" +
"							</tr>\n" +
"							<tr><td colspan=\"4\" height=\"10\"></td></tr>\n" +
"							<tr>\n" +
"								<td colspan=\"5\">\n" +
									
"                                                                        <div style=\"color:#404040;font-size:16px;line-height:22px;padding:0;margin:0\">\n" +
"                                                                            We have now activated a credit image plan for you.It will valid from <span style=\"color: #468847\">"+uhp.getPurchase_date()+"</span> to <span style=\"color: rgb(185, 74, 72);\">"+uhp.getExpire_date()+"</span>.\n" +
"									</div>\n" +
"									<div style=\"line-height:22px;padding:0;margin:0\">&nbsp;</div>\n" +
"									<div style=\"color:#404040;font-size:16px;line-height:22px;padding:0;margin:0\">\n" +
"                                                                            You have "+cp.getCredits()+" credits for "+cp.getDuration()+" months.cost for credit will be $"+df.format(cp.getPer_image())+" and total cost for the package was <u>$"+df.format(total)+"</u>\n";
                                                                             System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ "+(cp.getDiscount()!=0));
                                                                              if(cp.getDiscount()!=0){
                                                                                 msg+=" with "+cp.getDiscount()+"% discount.";
                                                                             }
                                                                    msg+="</div>\n" +
"								</td>\n" +
"							</tr>\n" +
"							\n" +
"							<tr><td colspan=\"3\" height=\"60\"></td></tr>\n" +
"							\n" +
"							<tr>\n" +
"								<td align=\"center\" colspan=\"5\">\n" +
"									<table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" style=\"font-family:HelveticaNeue-Light,Arial,sans-serif;margin:0;padding:0\">\n" +
"										<tbody><tr>\n" +
"											<td align=\"center\" style=\"margin:0;text-align:center\">\n" +
"												<a href=\"localhost:8084/ImageLK/ImagePlans.jsp\" style=\"font-size:18px;line-height:22px;text-decoration:none;color:#ffffff;font-weight:bold;border-radius:2px;background-color:#0096d3;padding:14px 40px;display:block\" target=\"_blank\">More Plans</a>\n" +
"											</td>\n" +
"										</tr>\n" +
"									</tbody></table>\n" +
"								</td>\n" +
"							</tr>\n" +
"							\n" +
"							<tr><td colspan=\"3\" height=\"30\"></td></tr>\n" +
"							\n" +
"							\n" +
"							\n" +
"							<tr><td colspan=\"4\" height=\"30\"></td></tr>\n" +
"							\n" +
"							<tr>\n" +
"								<td colspan=\"5\">\n" +
"									<div style=\"color:#404040;font-size:18px;line-height:24px;padding:0;margin:0\">Regards,</div>\n" +
"									<div style=\"color:#404040;font-size:18px;line-height:24px;font-weight:bold;padding:0;margin:0\">The ImageLakeLk Team.</div>\n" +
"								</td>\n" +
"							</tr>\n" +
"							<tr><td colspan=\"4\" height=\"60\" style=\"padding:0;margin:0;font-size:0;line-height:0\"></td></tr>\n" +
"						</tbody></table>\n" +
"					</td>\n" +
"					<td width=\"30\" bgcolor=\"#ffffff\"></td>\n" +
"				</tr>\n" +
"\n" +
"\n" +
"				\n" +
"			</tbody></table>\n" +
"		</td>\n" +
"	</tr>	\n" +
"    \n" +
"\n" +
"   \n" +
"\n" +
"                <tr>\n" +
"                  <td colspan=\"2\" height=\"80\"></td>\n" +
"                </tr>\n" +
"		            </tbody></table>\n" +
"		        </td>\n" +
"		    </tr>\n" +
"		</tbody></table>\n" +
"		</td>\n" +
"	</tr>\n" +
"</tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
"</div></div><div class=\"adL\">\n" +
"\n" +
"</div></div></div>";
            
              mail.setContent(msg, "text/html");
            //Transport transport = session.getTransport("smtp");
            Transport.send(mail);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
        
        
        public boolean requestForgetPassword(User us){
                 Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        Properties prop = new Properties();
        boolean ok=false;
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.debug", "true");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.socketFactory.fallback", "false");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.setProperty("mail.smtp.starttls.enable", "true");
        
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("vivajavaee@gmail.com", "vivajavaEE123");
            }
        };
        Session session = Session.getDefaultInstance(prop, auth);

        try {
            Message mail = new MimeMessage(session);
            mail.setFrom(new InternetAddress("vivajavaee@gmail.com"));
            
            InternetAddress sTo = new InternetAddress(us.getEmail());
            mail.setRecipient(Message.RecipientType.TO, sTo);
            
            InternetAddress sCC = new InternetAddress("navarathnelakmalravindra@gmail.com");
            mail.setRecipient(Message.RecipientType.CC, sCC);
//            
//            InternetAddress sBCC = new InternetAddress(txtBCC.getText());
//            mail.setRecipient(Message.RecipientType.BCC, sBCC);
            String uid=us.getUser_id()+"";
            byte[] bytesEncoded = Base64.encodeBase64(uid.getBytes());//encoding part
            mail.setSubject("Forget Password Request");
            String msg="<a href='http://localhost:8084/ImageLK/forgetPassword.jsp?fpreq="+bytesEncoded+"forget"+us.getUser_id()+"' >Change password</a>";
              
              mail.setContent(msg, "text/html");
            //Transport transport = session.getTransport("smtp");
            Transport.send(mail);
           ok=true;
            
        } catch (Exception e) {
            e.printStackTrace();
            ok=false;
        }
        return ok;
        }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagelake.android.packages;

import com.imagelake.control.AdminPackageIncomeDAOImp;
import com.imagelake.control.CreditPackageDAOImp;
import com.imagelake.control.DownloadCountDAOImp;
import com.imagelake.control.EmailSend;
import com.imagelake.control.SubscriptionPackageDAOImp;
import com.imagelake.control.UserDAOImp;
import com.imagelake.control.UserHasPackageDAOImp;
import com.imagelake.model.CreditsPackage;
import com.imagelake.model.DownloadCount;
import com.imagelake.model.SubscriptionPackage;
import com.imagelake.model.UserHasPackage;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author RLN
 */
public class Servlet_Purch extends HttpServlet {

    public final int INVALID = -1;
    public final int VISA = 0;
    public final int MASTERCARD = 1;
    public final int AMERICAN_EXPRESS = 2;
    public final int EN_ROUTE = 3;
    public final int DINERS_CLUB = 4;

    private static final String[] cardNames
            = {"Visa",
                "Mastercard",
                "American Express",
                "En Route",
                "Diner's CLub/Carte Blanche",};

    /**
     * Valid a Credit Card number
     */
    public boolean validCC(String number)
            throws Exception {
        int CardID;
        if ((CardID = getCardID(number)) != -1) {
            return validCCNumber(number);
        }
        return false;
    }

    /**
     * Get the Card type returns the credit card type INVALID = -1; VISA = 0;
     * MASTERCARD = 1; AMERICAN_EXPRESS = 2; EN_ROUTE = 3; DINERS_CLUB = 4;
     */
    public int getCardID(String number) {
        int valid = INVALID;

        String digit1 = number.substring(0, 1);
        String digit2 = number.substring(0, 2);
        String digit3 = number.substring(0, 3);
        String digit4 = number.substring(0, 4);

        if (isNumber(number)) {
            /* ----
             ** VISA  prefix=4
             ** ----  length=13 or 16  (can be 15 too!?! maybe)
             */
            if (digit1.equals("4")) {
                if (number.length() == 13 || number.length() == 16) {
                    valid = VISA;
                }
            } /* ----------
             ** MASTERCARD  prefix= 51 ... 55
             ** ----------  length= 16
             */ else if (digit2.compareTo("51") >= 0 && digit2.compareTo("55") <= 0) {
                if (number.length() == 16) {
                    valid = MASTERCARD;
                }
            } /* ----
             ** AMEX  prefix=34 or 37
             ** ----  length=15
             */ else if (digit2.equals("34") || digit2.equals("37")) {
                if (number.length() == 15) {
                    valid = AMERICAN_EXPRESS;
                }
            } /* -----
             ** ENROU prefix=2014 or 2149
             ** ----- length=15
             */ else if (digit4.equals("2014") || digit4.equals("2149")) {
                if (number.length() == 15) {
                    valid = EN_ROUTE;
                }
            } /* -----
             ** DCLUB prefix=300 ... 305 or 36 or 38
             ** ----- length=14
             */ else if (digit2.equals("36") || digit2.equals("38")
                    || (digit3.compareTo("300") >= 0 && digit3.compareTo("305") <= 0)) {
                if (number.length() == 14) {
                    valid = DINERS_CLUB;
                }
            }
        }
        return valid;

        /* ----
         ** DISCOVER card prefix = 60
         ** --------      lenght = 16
         **      left as an exercise ...
         */
    }

    public boolean isNumber(String n) {
        try {
            double d = Double.valueOf(n).doubleValue();
            return true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getCardName(int id) {
        return (id > -1 && id < cardNames.length ? cardNames[id] : "");
    }

    public boolean validCCNumber(String n) {
        try {
            /*
             ** known as the LUHN Formula (mod10)
             */
            int j = n.length();

            String[] s1 = new String[j];
            for (int i = 0; i < n.length(); i++) {
                s1[i] = "" + n.charAt(i);
            }

            int checksum = 0;

            for (int i = s1.length - 1; i >= 0; i -= 2) {
                int k = 0;

                if (i > 0) {
                    k = Integer.valueOf(s1[i - 1]).intValue() * 2;
                    if (k > 9) {
                        String s = "" + k;
                        k = Integer.valueOf(s.substring(0, 1)).intValue()
                                + Integer.valueOf(s.substring(1)).intValue();
                    }
                    checksum += Integer.valueOf(s1[i]).intValue() + k;
                } else {
                    checksum += Integer.valueOf(s1[0]).intValue();
                }
            }
            return ((checksum % 10) == 0);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkCVV(int CardIDthrow, String cvv) {
        boolean ok = false;

        if (CardIDthrow == 2) {
            if (cvv.length() == 4) {
                ok = true;
            }
        } else {
            if (cvv.length() == 3) {
                ok = true;
            }
        }

        return ok;
    }

    public boolean checkExpireDate(String expMonth, String expYear, String currMonth, String currYear) {
        boolean ok = false;

        int expmon = Integer.parseInt(expMonth);
        int expyer = Integer.parseInt(expYear);
        int curmon = Integer.parseInt(currMonth);
        int curyer = Integer.parseInt(currYear);

        if (expmon > 0 && expmon <= 12) {
            if (expyer == curyer) {
                if (expmon >= curmon) {
                    ok = true;
                }
            } else {
                if (expyer > curyer) {
                    if (expmon >= curmon) {
                        ok = true;
                    }
                }
            }
        }
        return ok;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        PrintWriter out = response.getWriter();
        System.out.println("OOO");
        //376657047440675   1234
        try {

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
            DateFormat currentMonth = new SimpleDateFormat("MM");
            DateFormat currentYear = new SimpleDateFormat("yyyy");

            Calendar calMonth = Calendar.getInstance();
            Calendar calYear = Calendar.getInstance();

            Calendar cal = Calendar.getInstance();
            Calendar calReturn = Calendar.getInstance();
            //--------------------------------------------------------------------------
            String fn = request.getParameter("fn");
            String ln = request.getParameter("ln");

            String type = request.getParameter("type");

            String aCard = request.getParameter("card");

            String Cvv = request.getParameter("cv");

            String month = request.getParameter("em");
            String year = request.getParameter("ey");

            String country = request.getParameter("ec");

            String totalDays = request.getParameter("totalDays");
            String pckId = request.getParameter("pkId");
            String userId = request.getParameter("usid");
            String dwnCount = request.getParameter("dwnCount");

            String total = request.getParameter("total");

            UserHasPackageDAOImp uhpdi = new UserHasPackageDAOImp();
            SubscriptionPackageDAOImp subi = new SubscriptionPackageDAOImp();
            //System.out.println("-----"+fn+" "+ln+" "+aCard+" "+cvv+" "+year+" "+month+" "+country+" "+totalDays+" "+pckId+" "+userId+" "+dwnCount+" "+type);
            System.out.println("first Name:" + fn);
            System.out.println("Last Name:" + ln);
            System.out.println("card No:" + aCard);
            System.out.println("CVV:" + Cvv);
            System.out.println("expire year:" + year);
            System.out.println("expire Month:" + month);
            System.out.println("Total Price:" + total);
            System.out.println("Current Yeat:" + currentYear.format(calYear.getTime()));
            System.out.println("Current Month:" + currentMonth.format(calMonth.getTime()));
            System.out.println("Country:" + country);
            System.out.println("total Days:" + totalDays);
            System.out.println("Package ID:" + pckId);
            System.out.println("User ID:" + userId);
            System.out.println("Download Count:" + dwnCount);
            System.out.println("Type:" + type);

            if (getCardID(aCard) > -1 && getCardID(aCard) <= 2 && checkCVV(getCardID(aCard), Cvv) && checkExpireDate(month, year, currentMonth.format(calMonth.getTime()), currentYear.format(calYear.getTime()))) {

                System.out.println("This card is supported.");
                System.out.println("This a " + getCardName(getCardID(aCard)));

                if (type.equals("3")) {
                    if (fn.equals(null) || fn.equals("") && ln.equals(null) || ln.equals("")) {

                        out.write("msg=This card is invalid or unsupported!");

                    } else {
                        boolean ok = new UserDAOImp().updateFnLn(fn, ln, Integer.parseInt(userId));
                        SubscriptionPackage sub = subi.getSubscription(Integer.parseInt(pckId));//get subscription package
                        //SubscriptionUnitPrice su=new SubscriptionUnitPriceDAOImp().getSubscriptionUnitPrice(sub.getSubscription_unit_price_id());//getting unit price
                        DownloadCount dc = new DownloadCountDAOImp().getCount(sub.getCount_id());//get download count

                        calReturn.add(Calendar.DATE, Integer.parseInt(totalDays));

                        UserHasPackage uh = new UserHasPackage();
                        uh.setCredit_count(0);
                        uh.setDownload_count(Integer.parseInt(dwnCount));
                        uh.setExpire_date(dateFormat.format(calReturn.getTime()));
                        uh.setPackage_id(Integer.parseInt(pckId));

                        uh.setPackage_type(3);

                        uh.setPurchase_date(dateFormat.format(cal.getTime()));
                        uh.setState(1);
                        uh.setUser_id(Integer.parseInt(userId));
                        uh.setLast_date(dateFormat.format(cal.getTime()));
                        uh.setOrg_downloads(dc.getCount());
                        uh.setUnit_price(sub.getPer_image());
                        uh.setDuration(Integer.parseInt(totalDays));
                        System.gc();

                        UserHasPackage uhpck = uhpdi.getUserActivePackage(Integer.parseInt(userId), 1);
                        if (uhpck != null) {

                            boolean oo = uhpdi.updateState(uhpck.getUhp_id());
                            System.out.println("OOOO" + oo);
                            if (oo) {
                                boolean add = uhpdi.addAPackage(uh);
                                System.out.println("Now User has new Subscription Package");
                                if (add) {
                                    UserHasPackage upck = uhpdi.getUserActivePackage(Integer.parseInt(userId), 1);

                                    boolean addToAdmin = new AdminPackageIncomeDAOImp().setAdminIncome(upck.getUhp_id(), Double.valueOf(total));
                                    new EmailSend().sendSubscriptionPurchasePackage(request.getSession(), Integer.parseInt(pckId), Double.parseDouble(total), upck);
                                    if (addToAdmin) {
                                        System.out.println("Add to Admin Income");
                                        out.write("msg=Successfuly_added");
                                    } else {
                                        out.write("msg=Unable to complete the action,Please try again later.");
                                    }
                                } else {
                                    out.write("msg=Unable to complete the action,Please try again later.");
                                }

                                System.gc();

                                System.gc();
                            }
                        } else {
                            boolean add = uhpdi.addAPackage(uh);
                            System.out.println("Now User has brandnew Subscription Package");
                            if (add) {
                                UserHasPackage upck = uhpdi.getUserActivePackage(Integer.parseInt(userId), 1);

                                boolean addToAdmin = new AdminPackageIncomeDAOImp().setAdminIncome(upck.getUhp_id(), Double.valueOf(total));
                                new EmailSend().sendSubscriptionPurchasePackage(request.getSession(), Integer.parseInt(pckId), Double.parseDouble(total), upck);
                                if (addToAdmin) {
                                    System.out.println("Add to Admin Income");
                                    out.write("msg=Successfuly_added");
                                } else {
                                    out.write("msg=Unable to complete the action,Please try again later.");
                                }
                            } else {
                                out.write("msg=Unable to complete the action,Please try again later.");
                            }
                            System.gc();
                        }
                    }
                } else {

                    if (fn.equals(null) || fn.equals("") && ln.equals(null) || ln.equals("")) {

                        out.write("msg=This card is invalid or unsupported!");

                    } else {
                        boolean ok = new UserDAOImp().updateFnLn(fn, ln, Integer.parseInt(userId));
                        CreditsPackage cp = new CreditPackageDAOImp().getCreditPackage(Integer.parseInt(pckId));
                        //CreditUnitPrice cup=new CreditUnitPriceDAOImp().getUnitPriceForCredit(cp.getCredit_unit_price_id());

                        calReturn.add(Calendar.DATE, Integer.parseInt(totalDays));

                        UserHasPackage uh = new UserHasPackage();
                        uh.setCredit_count(Integer.parseInt(dwnCount));
                        uh.setDownload_count(0);
                        uh.setExpire_date(dateFormat.format(calReturn.getTime()));
                        uh.setPackage_id(Integer.parseInt(pckId));

                        uh.setPackage_type(4);

                        uh.setPurchase_date(dateFormat.format(cal.getTime()));
                        uh.setState(1);
                        uh.setUser_id(Integer.parseInt(userId));
                        uh.setLast_date(dateFormat.format(cal.getTime()));
                        uh.setUnit_price(cp.getPer_image());
                        uh.setDuration(Integer.parseInt(totalDays));

                        System.gc();
                        UserHasPackage uhpck = uhpdi.getUserActivePackage(Integer.parseInt(userId), 1);
                        if (uhpck != null) {

                            boolean oo = uhpdi.updateState(uhpck.getUhp_id());
                            System.out.println("OOOO" + oo);
                            if (oo) {
                                boolean add = uhpdi.addAPackage(uh);
                                System.gc();
                                if (add) {
                                    UserHasPackage upck = uhpdi.getUserActivePackage(Integer.parseInt(userId), 1);
                                    boolean addToAdmin = new AdminPackageIncomeDAOImp().setAdminIncome(upck.getUhp_id(), Double.valueOf(total));
                                    new EmailSend().sendCreditPurchasePackage(request.getSession(), Integer.parseInt(pckId), Double.parseDouble(total), upck);
                                    if (addToAdmin) {
                                        System.out.println("Add to Admin Income");
                                        out.write("msg=Successfuly_added");
                                    } else {
                                        out.write("msg=Unable to complete the action,Please try again later.");
                                    }
                                } else {
                                    out.write("msg=Unable to complete the action,Please try again later.");
                                }

                                System.gc();
                            }
                        } else {
                            boolean add = uhpdi.addAPackage(uh);
                            System.gc();
                            if (add) {
                                UserHasPackage upck = uhpdi.getUserActivePackage(Integer.parseInt(userId), 1);

                                boolean addToAdmin = new AdminPackageIncomeDAOImp().setAdminIncome(upck.getUhp_id(), Double.valueOf(total));
                                new EmailSend().sendCreditPurchasePackage(request.getSession(), Integer.parseInt(pckId), Double.parseDouble(total), upck);
                                if (addToAdmin) {
                                    System.out.println("Add to Admin Income");
                                    out.write("msg=Successfuly_added");
                                } else {
                                    out.write("msg=Unable to complete the action,Please try again later.");
                                }
                            } else {
                                out.write("msg=Unable to complete the action,Please try again later.");
                            }
                        }
                    }

                }
                try {
                    System.out.println("The card number " + aCard + " is "
                            + (validCC(aCard) ? " good." : " bad."));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            } else {
                System.out.println("This card is invalid or unsupported!");
                out.write("msg=This card is invalid or unsupported!");

            }

        } catch (Exception e) {
            e.printStackTrace();
            out.write("msg=Internal server error,Please try again later.");
        }
    }

}

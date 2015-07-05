/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.UserHasPackage;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public interface UserHasPackageDAO {
    public List<UserHasPackage> getUserPackages(int uid);
    public boolean addAPackage(UserHasPackage u);
    public boolean updateState(int uid);
    public boolean updatePackage(UserHasPackage uhp);
    public UserHasPackage getUserPackage(int uid,int state,int type);
    
    public String getPackageType(int pckID);
    public UserHasPackage getUserActivePackage(int uid,int state);
    public boolean updateExpireDate(UserHasPackage up);
    public String getPackageSellingRate();
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.Packages;
import com.imagelake.model.SubscriptionPackage;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public interface SubscriptionPackageDAO {
    public List<SubscriptionPackage> listAll();
    public List<SubscriptionPackage> listByDownCount(int coid);
    public SubscriptionPackage getSubscriptionPackage(int coid);
    
    public SubscriptionPackage getSubscription(int sub_id);
    public String getSubscriptionType(int type_id);
    //this is for adminsettings
    public List<Packages> getPackageList();
    
    public boolean duplicatePackage(SubscriptionPackage sp);
    public boolean updatePackage(SubscriptionPackage sp);
    public boolean insertPackage(SubscriptionPackage sp);
    
}

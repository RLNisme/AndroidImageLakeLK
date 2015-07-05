/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.AdminPackageIncome;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public interface AdminPackageIncomeDAO {
 public boolean setAdminIncome(int uhi_id,double total);
 public AdminPackageIncome getAdminPackageIncome(int uhp_id);
 public boolean updateAdminincomeTotal(AdminPackageIncome ainc);
 public String getIncome();
 public String getMonth();
}

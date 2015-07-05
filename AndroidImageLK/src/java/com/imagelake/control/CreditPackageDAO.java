/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.CreditsPackage;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public interface CreditPackageDAO {
    public List<CreditsPackage> listAll();
    public CreditsPackage getCreditPackage(int cre_id);
    public boolean updatePackage(CreditsPackage cp);
    public boolean duplicatePackage(CreditsPackage cp);
    public boolean insertPackage(CreditsPackage cp);
}

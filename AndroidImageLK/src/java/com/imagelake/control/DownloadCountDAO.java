/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.imagelake.control;

import com.imagelake.model.DownloadCount;
import java.util.List;

/**
 *
 * @author Lakmal
 */
public interface DownloadCountDAO {
    public List<DownloadCount> listAll();
    public DownloadCount getCount(int coid);
    public boolean insertDownloadCount(DownloadCount dc);
    public boolean duplication(DownloadCount dc);
}

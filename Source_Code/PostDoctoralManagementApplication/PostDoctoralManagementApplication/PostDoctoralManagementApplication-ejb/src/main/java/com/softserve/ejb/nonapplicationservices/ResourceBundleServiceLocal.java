/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softserve.ejb.nonapplicationservices;

import com.softserve.persistence.DBEntities.ResourceEntity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author kgothatso
 */
@Local
public interface ResourceBundleServiceLocal {
    public List<ResourceEntity> loadAllResourceEntities() throws Exception;
    public List<ResourceEntity> loadResourceEntitiesForLocale(String locale) throws Exception;
}

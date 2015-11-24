/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.labadm.persistence;

import edu.eci.pdsw.labadm.entities.Software;

/**
 *
 * @author Zacehiro
 */
public interface DaoSoftware {
    public void save(Software soft);
    public void load(int id);
}

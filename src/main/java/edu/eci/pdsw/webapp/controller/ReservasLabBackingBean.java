/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.webapp.controller;

import edu.eci.pdsw.webapp.model.ServicesFacade;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Zacehiro
 */
@ManagedBean
public class ReservasLabBackingBean {
    private String[] so;
    
    public String[] getSO(){
        return ServicesFacade.getInstance().getSo();
    }
}

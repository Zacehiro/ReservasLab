/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.webapp.controller;

import edu.eci.pdsw.samples.entities.Solicitud;
import edu.eci.pdsw.webapp.model.ServicesFacade;
import java.util.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author tatoo
 */
@ManagedBean
@SessionScoped
public class AdmSolicitudesBackingBean {    
    private Solicitud solselc ; 
    //trae todas las solicitudes que no han sido atendidas
    public List<Solicitud> getSolicitudes(){
        return ServicesFacade.getInstance("config.properties").loadSolicitudSinResp();
    }
    
    //solicitud seleccionada 

    public Solicitud getSolselc() {
        return solselc;
    }

    public void setSolselc(Solicitud solselc) {
        this.solselc = solselc;
    }
    
}

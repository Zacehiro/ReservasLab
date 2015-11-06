/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.webapp.controller;

import edu.eci.pdsw.labadm.entities.Solicitud;
import edu.eci.pdsw.labadm.persistence.PersistenceException;
import edu.eci.pdsw.labadm.services.ServicesFacade;
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
    private boolean resp;
    private Date fechaRealiz;
    private String justificacion;

 

    
    //trae todas las solicitudes que no han sido atendidas
    public List<Solicitud> getSolicitudes(){
        return ServicesFacade.getInstance("config.properties").loadSolicitudSinResp();
    }
    
    public void nuevaRespuesta(){
       
    }
    
    public Solicitud getSolselc() {
        return solselc;
    }

    public void setSolselc(Solicitud solselc) {
        this.solselc = solselc;
    }
    
    public Date getFechaRealiz() {
        return fechaRealiz;
    }

    public void setFechaRealiz(Date fechaRealiz) {
        this.fechaRealiz = fechaRealiz;
    }

    public boolean isResp() {
        return resp;
    }

    public void setResp(boolean resp) {
        this.resp = resp;
    }
    
    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }
}

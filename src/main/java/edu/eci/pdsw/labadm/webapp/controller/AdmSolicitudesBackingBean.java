/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.labadm.webapp.controller;

import edu.eci.pdsw.labadm.entities.Solicitud;
import edu.eci.pdsw.labadm.services.ServicesFacade;
import edu.eci.pdsw.labadm.services.ServicesFacadeException;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author tatoo
 */
@ManagedBean (name = "beanAdmin")
@SessionScoped
public class AdmSolicitudesBackingBean implements Serializable{    
    private Solicitud solselc ; 
    private boolean resp;
    private Date fechaRealiz;
    private String justificacion;
    //Este atributo es para avisar al usuario que no es posible dar respuesta a la solicitud con los datos dados
    private String popUp;

    public AdmSolicitudesBackingBean() {
        justificacion="";
        fechaRealiz=new Date(0, 0, 0);
        resp= false;
        solselc = new Solicitud();
        popUp = "";
    }

 

    
    //trae todas las solicitudes que no han sido atendidas
    public List<Solicitud> getSolicitudes() throws ServicesFacadeException{
        return ServicesFacade.getInstance("config.properties").loadSolicitudSinResp();
    }
    
    public void nuevaRespuesta(){
        System.out.println("duvan de  mierdqa");
        try {
            System.out.println("ENTRO AQUI ASFDFGWERGDa");
            if(resp){
                solselc.setEstado("aprobada");
                
                 System.out.println("Hola+aprobada");
            }else{
                solselc.setEstado("negada");
            }
            solselc.setJustificacion(justificacion);
            System.out.println("ñoña"+justificacion);
            solselc.setFecha_posible(fechaRealiz);
            System.out.println("pipecanson "+fechaRealiz);
            solselc.setFecha_resp(new Date());
            ServicesFacade.getInstance("config.properties").deleteSolicitud(solselc.getId());
            ServicesFacade.getInstance("config.properties").saveSolicitud(solselc);
        } catch (ServicesFacadeException ex) {
            popUp=ex.getMessage();
        }
    }

    public String getPopUp() {
        return popUp;
    }

    public void setPopUp(String popUp) {
        this.popUp = popUp;
    }
    
    public Solicitud getSolselc() {
        return solselc;
    }

    public void setSolselc(Solicitud solselc) {
        this.solselc = solselc;
    }
    
    public Date getFechaRealiz() {
        System.out.println("get");
        return fechaRealiz;
    }

    public void setFechaRealiz(Date fechaRealiz) {
        System.out.println("set");
        this.fechaRealiz = fechaRealiz;
    }

    public boolean isResp() {
        
        System.out.println("holaaaaaaa");
        return resp;
    }

    public void setResp(boolean resp) {
        
        System.out.println("holapipe");
        this.resp = resp;
    }
    
    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }
}

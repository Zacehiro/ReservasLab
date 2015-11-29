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
import java.util.Date;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 * Componenetes que se asiciaran con la interface del usuario monitor en las cuales ayudara para la 
 * administracion de estos, para poder crear, configurar y utilizar el respaldo de la aplicación
 * @author ZACEHIRO
 */
@ManagedBean (name = "beanAdmin")
@ApplicationScoped
public class AdmSolicitudesBackingBean implements Serializable{    
    private Solicitud solselc ; 
    private Solicitud solselc2; 
    private boolean resp;
    private Date fechaRealiz;
    private String justificacion;
    
    
    public AdmSolicitudesBackingBean() {
        justificacion=null;
        fechaRealiz=null;
        resp= false;
        solselc =null;
    }
    
    /**
     * Informa si hay alguna solicitud seleccionada
     * @return boolean true en caso que ya alguna seleción, false en caso contrario
     */
    public boolean isSelected(){
        return solselc!=null;
    }
    
    /**
     * Informa que hay alguna solicitud seleccionada en el momento de hacer un cambio de solicitud 
     * @return boolean true en caso que ya alguna no haya seleción, false en caso contrario
     */
    public boolean selectedCambio(){
        return solselc==null;
    }
    
    /**
     * Consulta todas aquellas solicitudes que no han sido atendidas anteriormente pero que no 
     * han sido aceptadas
     * @return listado de todas las solicitudes que no han sido atendidas y su solicitud aun no 
     * ha sido atendida
     * @throws ServicesFacadeException Problema al realizar una cosulta en la base de datos
     */
    public List<Solicitud> getSolicitudes() throws ServicesFacadeException{
        return ServicesFacade.getInstance("config.properties").loadSolicitudSinResp();
    }
    
    /**
     * Consulta todas aquellas solicitudes que ya han sido atendidas anteriormente pero que no 
     * han sido aceptadas
     * @return listado de todas las solicitudes que han sido atendidas y su solicitud ya fue atendida
     * @throws ServicesFacadeException Problema al realizar una cosulta en la base de datos
     */
    public List<Solicitud> getSolicitudesAtendidas() throws ServicesFacadeException{
        return ServicesFacade.getInstance("config.properties").loadSolicitudResp();
    }
    
    /**
     * Consulta todas aquellas solicitudes que ya han sido atendidas anteriormente y que han 
     * sido aceptadas
     * @return listado de todas las solicitudes que han sido atendidas y su solicitud a sido aceptadas
     * @throws ServicesFacadeException Problema al realizar una cosulta en la base de datos
     */
    public List<Solicitud> getSolicitudesAceptadas() throws ServicesFacadeException{
        return ServicesFacade.getInstance("config.properties").loadSolicitudSinInstalar();
    }
    
    
    /**
     * Se da respuesta de una solicitud, en caso de ser aprobada se exige la fecha de posible 
     * realización y en caso de ser negada se exige la justificacion de está.
     * @throws ServicesFacadeException Problema al realizar una actualizacion en la base de datos
     */
    public void nuevaRespuesta() throws ServicesFacadeException{
        if(resp){
            solselc.setEstado("aprobada");
        }else{
            solselc.setEstado("negada");
        }
        solselc.setJustificacion(justificacion);
        solselc.setFecha_posible(fechaRealiz);
        solselc.setFecha_resp(new Date());
        ServicesFacade.getInstance("config.properties").updateSolicitud(solselc);
        clearData();
        solselc=null;
    }
    
    /*
    *Instalacion aprobada de una solicitud que ya ha sido atendida y aceptada por el usuario
    *@throws ServicesFacadeException Problema con la actualizacion de la base de datos
    */
    public void seleccionadaInstalada() throws ServicesFacadeException{
        this.solselc2.setSoftware_instalado(true);
        ServicesFacade.getInstance("config.properties").updateSolicitud(solselc2);
    }
    
    /*
    *Solicitud que ya ha sido atendida y aun no tiene una respuesta
    *return Una solicitud que ya han sido atendida y aun no tiene respuesta.
    */
    public Solicitud getSolselc() {
        return solselc;
    }
    
    /*
    *Asignacion de la solicitud seleccionada de un listado de solicitudes que ya han sido atendida
    *y aun no tiene respuesta
    */ 
    public void setSolselc(Solicitud solselc) {
        this.solselc = solselc;
    }
    
    /*
    *return Una solicitud que ya han sido atendida y aprobadas
    */
    public Solicitud getSolselc2() {
        return solselc2;
    }

    /*
    *Asignacion de la solicitud seleccionada de un listado de solicitudes que ya han sido atendida
    *y aprobadas
    */    
    public void setSolselc2(Solicitud solselc2) {
        this.solselc2 = solselc2;
    }
    
    /*
    *Fecha en la que se piensa podra ser posible la realizacion de dicha solicitud en caso de ser aprobada
    *@return Fecha en que se estima sera posible la realizacion de la solicitud a la que se le esta 
    *dando una respuesta y que es aprobada.
    */
    public Date getFechaRealiz() {
        return fechaRealiz;
    }
    
    /*
    *Sobre escribe la fecha en que sera posible la realizacion de la solicitud
    */
    public void setFechaRealiz(Date fechaRealiz) {
        this.fechaRealiz = fechaRealiz;
    }

    /*
    *Respuesta a la solicitud, en caso de ser aprobada true caso contrario false
    *@return Respuesta de la solicitud.
    */
    public boolean isResp() {
        return resp;
    }

    /*
    *Respuesta a la solicitud, en caso de ser aprobada true caso contrario false
    */
    public void setResp(boolean resp) {
        this.resp = resp;
    }
    
    /*
    *Justificacion en por la que se piensa ser negada la realizacion de dicha solicitud
    *@return Justificacion por la que se estima sera negada la realizacion de la solicitud a la que 
    *se le esta dando una respuesta.
    */
    public String getJustificacion() {
        return justificacion;
    }

    /*
    *Justificacion en por la que se piensa ser negada la realizacion de dicha solicitud 
    *en el caso que la respuesta sea negada.
    */
    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }
    
    /*
    *Inicializacion de la justificacion y de la fecha en la que se estima podra ser resuelta la solicitud
    */
    public void clearData() {
        this.justificacion=null;
        this.fechaRealiz=null;
    }
}

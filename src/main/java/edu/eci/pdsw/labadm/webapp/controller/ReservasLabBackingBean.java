/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.labadm.webapp.controller;

import edu.eci.pdsw.labadm.entities.Laboratorio;
import edu.eci.pdsw.labadm.entities.SistemaOperativo;
import edu.eci.pdsw.labadm.entities.Solicitud;
import edu.eci.pdsw.labadm.services.ServicesFacade;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Zacehiro
 */
@ManagedBean
public class ReservasLabBackingBean {
    private ArrayList<String> sos = new ArrayList<String>();
    private ArrayList<Solicitud> sol;
    private ArrayList<String> labs = new ArrayList<String>();
    private String linkDescarga;
    private String linkSoftware;
    private String sistemaoperativo;
    private SistemaOperativo so;
    private ServicesFacade sf;
    
    
    public ArrayList<String> getLabs() {
        return labs;
    }

    public void setLabs(SistemaOperativo so) {
        sf = ServicesFacade.getInstance("config.properties");
        List<Laboratorio> lab = new ArrayList<Laboratorio>();
        //lab = sf.loadLaboratorioPosible(so);
        for(int i =0; i<lab.size();i++){
            labs.add(lab.get(i).getNombre());
        }
    }

    public ArrayList<String> getSos() {
        sf =ServicesFacade.getInstance("config.properties");
        ArrayList<SistemaOperativo> sito;
        sito = sf.getSos();
        for (int i=0; i<sito.size();i++) {
            sos.add(sito.get(i).getNombre());
        }
        return sos;
    }

    public void setSos(ArrayList<String> sos) {
        this.sos = sos;
    }

    public ArrayList<Solicitud> getSol() {
        return sol;
    }

    public void setSol(ArrayList<Solicitud> sol) {
        this.sol = sol;
    }

    public String getLinkDescarga() {
        return linkDescarga;
    }

    public void setLinkDescarga(String linkDescarga) {
        this.linkDescarga = linkDescarga;
    }

    public String getLinkSoftware() {
        return linkSoftware;
    }

    public void setLinkSoftware(String linkSoftware) {
        this.linkSoftware = linkSoftware;
    }

    public SistemaOperativo getSo() {
        return so;
    }

    public void setSo(SistemaOperativo so) {
        this.so = so;
    }

    public String getSistemaoperativo() {
        return sistemaoperativo;
    }

    public void setSistemaoperativo(String sistemaoperativo) {
        this.sistemaoperativo = sistemaoperativo;
        //obtener sistema operativo por el nombre y cambiarlo llamado al metodo setSo
    }
    
    public void onSoChange() {    
        if(sistemaoperativo!=null){
            setLabs(so);
        }
        
    }
  
    
}

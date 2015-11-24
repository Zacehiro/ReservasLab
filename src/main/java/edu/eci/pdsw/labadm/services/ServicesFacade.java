/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.labadm.services;

import edu.eci.pdsw.labadm.entities.Laboratorio;
import edu.eci.pdsw.labadm.entities.SistemaOperativo;
import edu.eci.pdsw.labadm.entities.Software;
import edu.eci.pdsw.labadm.entities.Solicitud;
import edu.eci.pdsw.labadm.persistence.DaoFactory;
import edu.eci.pdsw.labadm.persistence.DaoLaboratorio;
import edu.eci.pdsw.labadm.persistence.DaoSistemaOperativo;
import edu.eci.pdsw.labadm.persistence.DaoSoftware;
import edu.eci.pdsw.labadm.persistence.DaoSolicitud;
import edu.eci.pdsw.labadm.persistence.PersistenceException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Zacehiro
 */
public class ServicesFacade {
    private DaoFactory df = null;
    private List<SistemaOperativo> sos ;
    private static ServicesFacade instance=null;
    private final Properties properties=new Properties();
    
    
    private ServicesFacade(String propFileName) throws IOException{        
	InputStream input = null;
        input = this.getClass().getClassLoader().getResourceAsStream(propFileName);
        properties.load(input);
    }
    
    public static ServicesFacade getInstance(String propertiesFileName) throws RuntimeException{
        if (instance==null){
            try {
                instance=new ServicesFacade(propertiesFileName);
            } catch (IOException ex) {
                throw new RuntimeException("Error on application configuration:",ex);
            }
        }
        return instance;
    }

    public List<SistemaOperativo> getSos() {
        df = DaoFactory.getInstance(properties);
        DaoSistemaOperativo dso;
        try {
            dso = df.getDaoSistemaOperativo();
            sos = dso.loadAll();
            
        } catch (PersistenceException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sos;
    }
    
    /**
     * Guarda la soliditud parametro en la base de datos.
     * @param s Solicitud a guardar
     * @throws ServicesFacadeException Problema al leer en la base de datos.
     */
    public void saveSolicitud(Solicitud s) throws ServicesFacadeException{
        df=DaoFactory.getInstance(properties);
        DaoSolicitud ds;
        try {
            ds = df.getDaoSolicitud();
            if(s.getLink_licencia().contains(".") && s.getLink_descarga().contains(".")){
                ds.save(s);
            }else{
                throw new ServicesFacadeException(ServicesFacadeException.WRONG_LINK_TYPED);
            }
        }catch (PersistenceException ex) {
            throw new ServicesFacadeException(ServicesFacadeException.PROBLEMA_BASE_DATOS, ex);
        }
    }
    
    /**
     * Actualiza una solicitud de acuerdo a la existente en ls base de datos.
     * @param s Solicitud a actualizar.
     * @throws ServicesFacadeException Problemas al actualizar en la base de datos.
     */
    public void updateSolicitud(Solicitud s) throws ServicesFacadeException{
        try {
            df=DaoFactory.getInstance(properties);
            DaoSolicitud ds=df.getDaoSolicitud();
            ds.update(s);
        } catch (PersistenceException ex) {
            throw new ServicesFacadeException(ServicesFacadeException.PROBLEMA_BASE_DATOS, ex);
        }
    }
    /**
     * Carga todos los laboratorios que cuenten con el sistema operativo especificado.
     * @param SistemaOperativo
     * @return ArrayList con laboratorios.
     */

    public List<Laboratorio> loadLaboratorioPosible(SistemaOperativo so) throws ServicesFacadeException{
        df= DaoFactory.getInstance(properties);
        DaoLaboratorio dl;
        List posibles;
        try {
            dl = df.getDaoLaboratorio();
            posibles = dl.loadBySo(so);
        } catch (PersistenceException ex) {
            throw new ServicesFacadeException(ServicesFacadeException.PROBLEMA_BASE_DATOS);
        }
        return posibles;
    }
    
    /**
     * Carga todas las solicitudes realizadas anteriormente que ya hallan sido respondidas.
     * @return ArrayList con las solicitudes respondidas.
     */
    public List<Solicitud> loadAllSolicitud() throws ServicesFacadeException{
        df=DaoFactory.getInstance(properties);
        DaoSolicitud ds;
        try {
            ds = df.getDaoSolicitud();
            return ds.loadAll();
        } catch (PersistenceException ex) {
            if(ex.getMessage().equals("No requests found.")){
                throw new ServicesFacadeException(ServicesFacadeException.NO_CRITERIA_OR_EMPTY, ex);
            }else{
                throw new ServicesFacadeException(ServicesFacadeException.PROBLEMA_BASE_DATOS, ex);
            }
        }
    }
    
    /**
     * Retorna Solicitudes con Respuesta
     * @return Lista con Solicitudes Respondidas
     * @throws ServicesFacadeException Error de lectura en Base de Datos
     */
    public List<Solicitud> loadSolicitudResp() throws ServicesFacadeException{
        df= DaoFactory.getInstance(properties);
        List<Solicitud> sol = new ArrayList<Solicitud>();
        DaoSolicitud ds;
        try {
            ds = df.getDaoSolicitud();
            sol=ds.loadWithAnswer();
        } catch (PersistenceException ex) {
            if(ex.getMessage().equals("No requests found.")){
                throw new ServicesFacadeException(ServicesFacadeException.NO_CRITERIA_OR_EMPTY, ex);
            }else{
                throw new ServicesFacadeException(ServicesFacadeException.PROBLEMA_BASE_DATOS, ex);
            }
        }
        return sol;
    }

    /**
     * Retorna solicitudes no respondidas aun
     * @return Lista con Solicitudes no Respondidas
     * @throws ServicesFacadeException Error de Lectura en la Base de Datos
     */
    public List<Solicitud> loadSolicitudSinResp() throws ServicesFacadeException{
        df= DaoFactory.getInstance(properties);
        DaoSolicitud ds;
        try {
            ds = df.getDaoSolicitud();
            return ds.loadWithoutAnswer();
        } catch (PersistenceException ex) {
            if(ex.getMessage().equals("No requests found.")){
                throw new ServicesFacadeException(ServicesFacadeException.NO_CRITERIA_OR_EMPTY, ex);
            }else{
                throw new ServicesFacadeException(ServicesFacadeException.PROBLEMA_BASE_DATOS, ex);
            }
        }
    }
    
    /**
     * Retorna una solicitud de acuerdo al identificador del parametro
     * @param id Identificador de la Solicitud requerida
     * @return Soliditud Requierida
     * @throws ServicesFacadeException Problemas en la base de datos.
     */
    public Solicitud loadSolicitud(int id) throws ServicesFacadeException{
        df= DaoFactory.getInstance(properties);
        DaoSolicitud ds;
        try {
            ds = df.getDaoSolicitud();
            return ds.loadSolicitud(id);
        } catch (PersistenceException ex) {
            if(ex.getMessage().equals("No requests found.")){
                throw new ServicesFacadeException(ServicesFacadeException.NO_CRITERIA_OR_EMPTY, ex);
            }else{
                throw new ServicesFacadeException(ServicesFacadeException.PROBLEMA_BASE_DATOS, ex);
            }
        }
    }
    
    /**
     * Carga todos los sistemas operativos que estan vigentes en los laboratorios.
     * @return ArrayList<SistemaOperativo> lista con los sistemas operativos 
     */
    public ArrayList<SistemaOperativo> loadAllSo(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void deleteSolicitud(int sol) throws ServicesFacadeException{
        df=DaoFactory.getInstance(properties);
        try {
            DaoSolicitud ds=df.getDaoSolicitud();
            ds.delete(sol);
        } catch (PersistenceException ex) {
            throw new ServicesFacadeException(ServicesFacadeException.PROBLEMA_BASE_DATOS, ex);
        }
    }
    
    public SistemaOperativo loadSistemaOperativo(String nombre, String version){
        df=DaoFactory.getInstance(properties);
        SistemaOperativo so = null;
        try {
            DaoSistemaOperativo dso;
            dso = df.getDaoSistemaOperativo();
            so=dso.loadSo(nombre, version);
        } catch (PersistenceException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return so;
    }
    
    public void saveSoftware(Software sof){
        df = DaoFactory.getInstance(properties);
        try {
            DaoSoftware dsf;
            dsf = df.getDaoSoftware();
            dsf.save(sof);
        } catch (PersistenceException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
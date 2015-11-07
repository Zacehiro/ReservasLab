/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.labadm.services;

import edu.eci.pdsw.labadm.entities.Laboratorio;
import edu.eci.pdsw.labadm.entities.SistemaOperativo;
import edu.eci.pdsw.labadm.entities.Solicitud;
import edu.eci.pdsw.labadm.entities.Usuario;
import edu.eci.pdsw.labadm.persistence.DaoFactory;
import edu.eci.pdsw.labadm.persistence.DaoSolicitud;
import edu.eci.pdsw.labadm.persistence.DaoUsuario;
import edu.eci.pdsw.labadm.persistence.PersistenceException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
    private String[] so = {"Windows","Mac OS X","Linux"};
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

    public String[] getSo() {
        return so;
    }
    
    /**
     * Guarda una Solicitud realizada por un usuario.
     * @param Solicitud
     */
    public void saveSolicitud(Solicitud s) throws ServicesFacadeException{
        df=DaoFactory.getInstance(properties);
        DaoSolicitud ds=df.getDaoSolicitud();
        if(s!=null){
            if(((s.getEstado().equals("aprobada"))&&(!s.getFecha_resp().equals(new Date(0,0,0)))&&(s.getJustificacion().equals("")))||((s.getEstado().equals("negada"))&&(s.getFecha_posible().equals(new Date(0,0,0)))&&(!s.getJustificacion().equals("")))){
                try {
                    ds.save(s);
                } catch (PersistenceException ex) {
                    throw  new ServicesFacadeException(ServicesFacadeException.PROBLEMA_BASE_DATOS);
                }
            }else{
                throw new ServicesFacadeException(ServicesFacadeException.PROBLEMA_GUARDAR_SOLICITUD);
            }
        }else{
            throw new ServicesFacadeException(ServicesFacadeException.PROBLEMA_SOLICITUD_NO_SELECCIONADA);
        }
    }
    /**
     * Carga todos los laboratorios que cuenten con el sistema operativo especificado.
     * @param SistemaOperativo
     * @return ArrayList con laboratorios.
     */
    public ArrayList<Laboratorio> loadLaboratorioPosible(SistemaOperativo so){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Carga todas las solicitudes realizadas anteriormente que ya hallan sido respondidas.
     * @return ArrayList con las solicitudes respondidas.
     */
    public ArrayList<Solicitud> loadAllSolicitud(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Solicitud> loadSolicitudResp() throws ServicesFacadeException{
        df= DaoFactory.getInstance(properties);
        DaoSolicitud ds= df.getDaoSolicitud();
        List<Solicitud> ans=new ArrayList<>();
        try {
            for (Solicitud sol : ds.loadAll()) {
                if(sol.getEstado()!=null){
                    ans.add(sol);
                }
            }
        } catch (PersistenceException ex) {
            throw new ServicesFacadeException(ServicesFacadeException.PROBLEMA_BASE_DATOS);
        }
        return ans;
    }


    public List<Solicitud> loadSolicitudSinResp(){
        df= DaoFactory.getInstance(properties);
        /*DaoSolicitud ds= df.getDaoSolicitud();*/
        List<Solicitud> ans=new ArrayList<>();
        /*
        try {
            for (Solicitud sol : ds.loadAll()) {
                if(sol.getEstado()==null){
                    ans.add(sol);
                }
            }
        } catch (PersistenceException ex) {
            Logger.getLogger(ServicesFacade.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        Laboratorio templab= new Laboratorio("Redes", 1,8, true);
        Solicitud temp= new Solicitud(1, "Sotware", "www.licencia", "www.descargas", null,new Date(2015, 07, 24), null, null, null, templab, null);
        ans.add(temp);
        return ans;
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
        DaoSolicitud ds=df.getDaoSolicitud();
        try {
            ds.delete(sol);
        } catch (PersistenceException ex) {
            throw new ServicesFacadeException(ServicesFacadeException.PROBLEMA_BASE_DATOS);
        }
    }
}

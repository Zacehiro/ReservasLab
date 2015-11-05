/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.webapp.model;

import edu.eci.pdsw.samples.entities.Laboratorio;
import edu.eci.pdsw.samples.entities.SistemaOperativo;
import edu.eci.pdsw.samples.entities.Solicitud;
import edu.eci.pdsw.samples.entities.Usuario;
import edu.eci.pdsw.samples.persistence.DaoFactory;
import edu.eci.pdsw.samples.persistence.DaoUsuario;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
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
    public void saveSolicitud(Solicitud s){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    public List<Solicitud> loadSolicitudResp() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Solicitud> loadSolicitudSinResp() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Carga todos los sistemas operativos que estan vigentes en los laboratorios.
     * @return ArrayList<SistemaOperativo> lista con los sistemas operativos 
     */
    public ArrayList<SistemaOperativo> loadAllSo(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Zacehiro
 */
public class ServicesFacade {
    private final static ServicesFacade instance=new ServicesFacade();
    private DaoFactory df=DaoFactory.getInstance();
    
    private ServicesFacade(){
        
    }
    
    public static ServicesFacade getInstance(){
        return instance;
    }
    
    public void saveSolicitud(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public ArrayList<Laboratorio> loadLaboratorioPosible(SistemaOperativo so){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public ArrayList<Solicitud> loadAllSolicitud(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

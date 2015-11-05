/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.persistence.jdbc;

import edu.eci.pdsw.samples.entities.Solicitud;
import edu.eci.pdsw.samples.persistence.DaoSolicitud;
import edu.eci.pdsw.samples.persistence.PersistenceException;
import java.util.List;

/**
 *
 * @author Zacehiro
 */
public class JDBCDaoSolicitud implements DaoSolicitud{

    @Override
    public void save(Solicitud s) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Solicitud loadSolicitud(int id) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Solicitud> loadAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

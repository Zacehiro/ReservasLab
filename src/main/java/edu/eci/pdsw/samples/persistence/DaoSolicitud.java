/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.persistence;

import edu.eci.pdsw.samples.entities.Solicitud;
import java.util.List;

/**
 *
 * @author Zacehiro
 */
public interface DaoSolicitud {
    public void save(Solicitud s) throws PersistenceException;
    public Solicitud loadSolicitud(int id) throws PersistenceException;
    public List<Solicitud> loadAll();
}

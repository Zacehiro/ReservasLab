/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.labadm.persistence;

import edu.eci.pdsw.labadm.entities.Solicitud;
import java.util.List;

/**
 *
 * @author Zacehiro
 */
public interface DaoSolicitud {
    public void save(Solicitud s) throws PersistenceException;
    public Solicitud loadSolicitud(int id) throws PersistenceException;
    public List<Solicitud> loadAll() throws PersistenceException;
    public void delete(int s) throws PersistenceException;
    public List<Solicitud> loadWithoutAnswer() throws PersistenceException;
    public List<Solicitud> loadWithAnswer() throws PersistenceException;
    public void update(Solicitud s) throws PersistenceException;
    public List<Solicitud> loadAppproved() throws PersistenceException;
}

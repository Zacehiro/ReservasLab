/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.labadm.persistence;


import edu.eci.pdsw.labadm.entities.SistemaOperativo;
import java.util.List;

/**
 *
 * @author usuario
 */
public interface DaoSistemaOperativo {
        public void save(SistemaOperativo s) throws PersistenceException;
        public void Update(SistemaOperativo s) throws PersistenceException;
        public List<SistemaOperativo> loadAll() throws PersistenceException;
        public SistemaOperativo loadSo(String nombre, String version) throws PersistenceException;
}

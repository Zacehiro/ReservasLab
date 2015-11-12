package edu.eci.pdsw.labadm.persistence;

import edu.eci.pdsw.labadm.entities.Laboratorio;
import edu.eci.pdsw.labadm.entities.SistemaOperativo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Zacehiro
 */
public interface DaoLaboratorio {
   
    public void save(Laboratorio l) throws PersistenceException;
    public List<Laboratorio> loadBySo(SistemaOperativo so) throws PersistenceException;
    public void Update(Laboratorio l) throws PersistenceException;
    public List<Laboratorio> loadAll() throws PersistenceException;
    
}

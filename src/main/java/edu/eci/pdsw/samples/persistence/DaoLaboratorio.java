package edu.eci.pdsw.samples.persistence;

import edu.eci.pdsw.samples.entities.Laboratorio;
import edu.eci.pdsw.samples.entities.SistemaOperativo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Zacehiro
 */
public interface DaoLaboratorio {
   
    public void save(Laboratorio l) throws PersistenceException;
    public void loadBySo(SistemaOperativo so) throws PersistenceException;
    //public Laboratorio load(int id) throws PersistenceException; //no se como hacer esto o si sea necesario
    public void Update(Laboratorio l) throws PersistenceException;
    public ArrayList<Laboratorio> loadAll() throws PersistenceException;
    
}

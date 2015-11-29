/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.labadm.persistence.jdbc;

import edu.eci.pdsw.labadm.entities.SistemaOperativo;
import edu.eci.pdsw.labadm.entities.Usuario;
import edu.eci.pdsw.labadm.persistence.DaoSistemaOperativo;
import edu.eci.pdsw.labadm.persistence.PersistenceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Zacehiro
 */
public class JDBCDaosistemaoperativo implements DaoSistemaOperativo{

    Connection con;
    
    /**
     * Creacion del JDBCDaoSistemaoperativo
     * @param con conexion del Dao.
     */
    public JDBCDaosistemaoperativo(Connection con) {
        this.con = con;
    }
    
    /**
     * Guardar un sistema operativo en la base de datos.
     * @param s sistema operativo que se desea guardar.
     * @throws PersistenceException 
     */
    @Override
    public void save(SistemaOperativo s) throws PersistenceException {
        try {
            PreparedStatement ps;
            ps=con.prepareStatement("INSERT INTO `SISTEMA_OPERATIVO`(`ID_sistema_operativo`, `nombre`, `version`) VALUES (?,?,?)");
            ps.setInt(1, s.getId());
            ps.setString(2, s.getNombre());
            ps.setString(3, s.getVersion());
            con.commit();
        } catch (SQLException ex) {
            Logger.getLogger(JDBCDaosistemaoperativo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Actualizar la informacion de un sistema opertivo existente en la base de datos.
     * @param s sistema operativo que se va a actualizar.
     * @throws PersistenceException 
     */
    @Override
    public void Update(SistemaOperativo s) throws PersistenceException {
       PreparedStatement ps;
        try {
            ps=con.prepareStatement("DELETE FROM `SISTEMA_OPERATIVO` WHERE ID_sitema_operativo=?");
            ps.setInt(1, s.getId());
            ps.execute();
            con.commit();
            this.save(s);
        } catch (SQLException ex) {
            Logger.getLogger(JDBCDaoSolicitud.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }

    /**
     * Consultar todos los sistemas operativos existentes en la base de datos.
     * @return una lista con todas los sistemas operativos existentes, si no hay retorna una lista vacia.
     * @throws PersistenceException 
     */
    @Override
    public List<SistemaOperativo> loadAll() throws PersistenceException {
       List<SistemaOperativo> ans= new ArrayList<>();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("SELECT ID_sistema_operativo, nombre, version FROM `SISTEMA_OPERATIVO`");
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                ans.add(new SistemaOperativo(rs.getString(2), rs.getString(3), rs.getInt(1)));
            }
                
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while loading a product.",ex);
        }
        return ans;
    }
    
    /**
     * Consultar un sistema operativo especifico de la base de datos.
     * @param nombre nombre del sistema opertivo que se desea consultar.
     * @param version version del sistema opertivo que se desea consultar.
     * @return sistema operativo existente, un sistema operativo nulo si no se encuentra en la base de datos. 
     * @throws PersistenceException 
     */
    @Override
    public SistemaOperativo loadSo(String nombre, String version) throws PersistenceException{
        SistemaOperativo so;
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("SELECT * FROM `SISTEMA_OPERATIVO` WHERE nombre = ? AND version = ?");
            ps.setString(1, nombre);
            ps.setString(2, version);
            ResultSet rs=ps.executeQuery();
            rs.next();
            so=new SistemaOperativo(rs.getString(2), rs.getString(3), rs.getInt(1));
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while loading a product.",ex);
        }
        return so;
    }
}

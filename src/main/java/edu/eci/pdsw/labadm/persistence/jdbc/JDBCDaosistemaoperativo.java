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
 * @author usuario
 */
public class JDBCDaosistemaoperativo implements DaoSistemaOperativo{

    Connection con;

    public JDBCDaosistemaoperativo(Connection con) {
        this.con = con;
    }
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

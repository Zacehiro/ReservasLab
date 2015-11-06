/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.labadm.persistence.jdbc;

import edu.eci.pdsw.labadm.entities.Usuario;
import edu.eci.pdsw.labadm.persistence.DaoUsuario;
import edu.eci.pdsw.labadm.persistence.PersistenceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Zacehiro
 */
public class JDBCDaoUsuario implements DaoUsuario{
    Connection con;

    public JDBCDaoUsuario(Connection con) {
        this.con = con;
    }
    
    @Override
    public void save(Usuario u) throws PersistenceException {
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("insert into USUARIO(ID_usuario,nombre,email,tipo_usuario) values(?,?,?,?)");
            ps.setInt(1, u.getId());
            ps.setString(2, u.getNombre());
            ps.setString(3, u.getEmail());
            ps.setInt(4, u.getTipo_usuario());
            
            ps.execute();
            
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while saving an user.",ex);
        }
    }

    @Override
    public Usuario load(int id) throws PersistenceException {
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("select nombre, email, tipo_usuario from USUARIO where ID_usuario=?");
            ps.setInt(1, id);
            ResultSet rs=ps.executeQuery();
            
            if (rs.next()){
                return new Usuario(id, rs.getString(1), rs.getString(2), rs.getInt(3));
            }
            else{
                throw new PersistenceException("No row with the given id:"+id);
            }
            
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while loading an user.",ex);
        }
    }

    @Override
    public void Update(Usuario u) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public List<Usuario> loadAll() throws PersistenceException {
        List<Usuario> ans= new ArrayList<>();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("select ID_usuario, nombre, email,tipo_usuario from USUARIO");
            ResultSet rs=ps.executeQuery();
            if (!rs.next()){
                throw new PersistenceException("No users found.");
            }else{
                ans.add(new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3),rs.getInt(4)));
            }
            while (rs.next()){
                ans.add(new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3),rs.getInt(4)));
            }
                
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while loading a product.",ex);
        }
        return ans;
    }
    
}

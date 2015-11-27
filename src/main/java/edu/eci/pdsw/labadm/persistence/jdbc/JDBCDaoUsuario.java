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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Zacehiro
 */
public class JDBCDaoUsuario implements DaoUsuario{
    Connection con;
    
    /**
     * Creación del JDBCDaoUsuario.
     * @param con conexión del Dao.
     */
    public JDBCDaoUsuario(Connection con) {
        this.con = con;
    }
    
    /**
     * Guardar un usuario en la base de datos.
     * @param u usuario que se va a guardar en la base de datos.
     * @throws PersistenceException 
     */
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
            con.commit();
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while saving an user.",ex);
        }
    }

    /**
     * Consultar un usuario específico.
     * @param id numero de id del usuario que se desea consultar.
     * @return el usuario si este es encontrado, un usuario nulo si este no existe.
     * @throws PersistenceException 
     */
    @Override
    public Usuario load(int id) throws PersistenceException {
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("select nombre, email, tipo_usuario from USUARIO where ID_usuario=?");
            ps.setInt(1, id);
            ResultSet rs=ps.executeQuery();
            rs.next();
            return new Usuario(id, rs.getString(1), rs.getString(2), rs.getInt(3));
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while loading an user.",ex);
        }
    }

    /**
     * Actualizar la información de un usuario existente.
     * @param u usuario que va a ser actualizado.
     * @throws PersistenceException 
     */
    @Override
    public void Update(Usuario u) throws PersistenceException {
        PreparedStatement ps;
        try {
            ps=con.prepareStatement("DELETE FROM USUARIO WHERE USUARIO.ID_usuario=?");
            ps.setInt(1, u.getId());
            ps.execute();
            con.commit();
            this.save(u);
        } catch (SQLException ex) {
            Logger.getLogger(JDBCDaoSolicitud.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }

    /**
     * Consultar todos los usuarios existentes de la base de datos.
     * @return una lista con todas los usuarios existentes, si no hay retorna una lista vacia.
     * @throws PersistenceException 
     */
    @Override
    public List<Usuario> loadAll() throws PersistenceException {
        List<Usuario> ans= new ArrayList<>();
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("select ID_usuario, nombre, email,tipo_usuario from USUARIO");
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                ans.add(new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3),rs.getInt(4)));
            }
                
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while loading a product.",ex);
        }
        return ans;
    }
    
}

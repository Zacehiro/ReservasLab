/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.labadm.persistence.jdbc;

import edu.eci.pdsw.labadm.entities.Software;
import edu.eci.pdsw.labadm.persistence.DaoSoftware;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Zacehiro
 */
public class JDBCDaoSoftware implements DaoSoftware {
    
    Connection con;
    
    /**
     * Creacion del JDBCDaoSoftware.
     * @param con conexion del Dao.
     */
    public JDBCDaoSoftware(Connection con){
        this.con=con;
    }
    
    /**
     * Guardar un software en la base de datos.
     * @param soft software que se desea guardar.
     */
    @Override
    public void save(Software soft) {
        PreparedStatement ps;
        try {
            ps =con.prepareStatement("INSERT INTO `SOFTWARE`(`nombre`, `version`) VALUES (?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, soft.getNombre());
            ps.setString(2, soft.getVersion());
            ps.execute();
            con.commit();
             ResultSet i = ps.getGeneratedKeys();
            int clave=0;
            while(i.next()){
                clave=i.getInt(1);
            }
            soft.setId(clave);
        } catch (SQLException ex) {
            Logger.getLogger(JDBCDaoSoftware.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

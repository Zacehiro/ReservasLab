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
 * @author usuario
 */
public class JDBCDaoSoftware implements DaoSoftware {
    
    Connection con;
    
    public JDBCDaoSoftware(Connection con){
        this.con=con;
    }
    
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

    @Override
    public void load(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.labadm.persistence.jdbc;

import static com.mysql.jdbc.Messages.getString;
import edu.eci.pdsw.labadm.entities.Laboratorio;
import edu.eci.pdsw.labadm.entities.SistemaOperativo;
import edu.eci.pdsw.labadm.entities.Solicitud;
import edu.eci.pdsw.labadm.persistence.DaoSolicitud;
import edu.eci.pdsw.labadm.persistence.PersistenceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;

/**
 *
 * @author Zacehiro
 */
public class JDBCDaoSolicitud implements DaoSolicitud{
    
    Connection con;

    public JDBCDaoSolicitud(Connection con){
        this.con = con;
    }

    @Override
    public void save(Solicitud s) throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Solicitud loadSolicitud(int id) throws PersistenceException {
        Solicitud sol;
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("SELECT solicitud.ID_solicitud AS id_solicitud, solicitud.Software AS software,"+
                                        " solicitud.Link_licencia AS licencia, solicitud.Link_descarga AS descarga, solicitud.Estado AS estado,"+
                                        " solicitud.Fecha_radicacion AS fecha_rad, solicitud.Fecha_posible_instalacion AS fecha_instalacion,"+
                                        " solicitud.Fecha_respuesta AS fecha_resp, solicitud.Justificacion AS justificacion,"+
                                        " usuario_T.ID_Usuario, usuario_T.nombre, usuario_T.email, usuario_T.tipo_usuario, "+
                                        " laboratorio.ID_laboratorio AS labid, laboratorio.nombre AS labn, laboratorio.cantidad_equipos AS can_equ, laboratorio.videobeam AS labv,"+
                                        " FROM SOLICITUD AS solicitud JOIN USUARIO AS usuario_T ON solicitud.Usuario_id=usuario_T.ID_usuario"+
                                        " JOIN LABORATORIO AS laboratorio ON solicitud.Laboratorio_id=laboratoiro.ID_laboratorio"+
                                        " WHERE solicitud.ID_solicitud=? ORDER BY solicitud.Fecha_Radicacion");
            ps.setInt(1, id);
            ResultSet rs=ps.executeQuery();
            if (rs.next()){
                //sol=new Solicitud(rs.getInt("id_colicitud"),rs.getString("software"),rs.getString("licencia"),rs.getString("descarga"),rs.getString("estado"),rs.getTimestamp("fecha_rad"),rs.getTimestamp("fecha_instalacion"),rs.getTimestamp("fecha_resp"), rs.getString("justificacion"),rs.getInt(new Laboratorio(rs.getInt("labid"),rs.getString("labn"),rs.getInt( "can_equ"),rs.getBoolean("labv"))),rs,getString("software"));
            }
            else{
                throw new PersistenceException("No row with the given id:"+id);
            }
            
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while loading a request.",ex);
        }
        return null;
    }

    /**
     *
     * @return
     * @throws PersistenceException
     */
    @Override
    public List<Solicitud> loadAll() throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}

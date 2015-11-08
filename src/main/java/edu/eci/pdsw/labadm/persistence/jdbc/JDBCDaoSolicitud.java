/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.labadm.persistence.jdbc;

import static com.mysql.jdbc.Messages.getString;
import edu.eci.pdsw.labadm.entities.Laboratorio;
import edu.eci.pdsw.labadm.entities.SistemaOperativo;
import edu.eci.pdsw.labadm.entities.Software;
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
import java.util.logging.Level;
import java.util.logging.Logger;

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
       PreparedStatement ps;
        try {
            ps=con.prepareStatement("INSERT INTO `SOLICITUD`(`ID_solicitud`, `Laboratorio_id`, `Software`, `Link_licencia`, `Link_descarga`,"+
                                    " `Estado`, `Fecha_radicacion`, `Fecha_posible_instalacion`, `Fecha_respuesta`, `Justificacion`,"+
                                    " `Usuario_id`) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1,s.getId());
            ps.setInt(2, s.getLaboratorio().getId());
            ps.setString(3, s.getSoftware());
            ps.setString(4, s.getLink_licencia());
            ps.setString(5, s.getLink_descarga());
            ps.setString(6, s.getEstado());
            ps.setDate(7, (java.sql.Date) s.getFecha_rad());
            ps.setDate(8, (java.sql.Date) s.getFecha_posible());
            ps.setDate(9, (java.sql.Date) s.getFecha_resp());
            ps.setString(10, s.getJustificacion());
            //ps.setInt(11, 0);
            con.commit();
        } catch (SQLException ex) {
            Logger.getLogger(JDBCDaoSolicitud.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                                        " usuario.ID_Usuario AS id_usuario, usuario.nombre AS usuario_nombre, usuario.email AS email, usuario.tipo_usuario AS tipo_us, "+
                                        " laboratorio.ID_laboratorio AS labid, laboratorio.nombre AS labn, laboratorio.cantidad_equipos AS can_equ,"+
                                        " laboratorio.videobeam AS labv, sistemaop.ID_sistema_operativo AS id_so, sistemaop.nombre AS so_nombre, sistemaop.version AS so_version"+
                                        " FROM SOLICITUD AS solicitud JOIN USUARIO AS usuario ON solicitud.Usuario_id=usuario.ID_usuario"+
                                        " JOIN LABORATORIO AS laboratorio ON solicitud.Laboratorio_id=laboratorio.ID_laboratorio"+
                                        " JOIN SISTEMA_OPERATIVO AS sistemaop ON sistemaop.Solicitud_id=solicitud.ID_solicitud"+
                                        " WHERE solicitud.ID_solicitud=? ORDER BY solicitud.Fecha_Radicacion");
            ps.setInt(1, id);
            ResultSet rs=ps.executeQuery();
            int labid=rs.getInt("labid");
            Laboratorio lab=new Laboratorio(rs.getString("labn"),rs.getInt("labid"),rs.getInt( "can_equ"),rs.getBoolean("labv"));
            if (rs.next()){
                sol=new Solicitud(rs.getInt("id_colicitud"),rs.getString("software"),rs.getString("licencia"),rs.getString("descarga"),rs.getString("estado"),rs.getTimestamp("fecha_rad"),rs.getTimestamp("fecha_instalacion"),rs.getTimestamp("fecha_resp"), rs.getString("justificacion"),lab,new SistemaOperativo(rs.getString("so_nombre"),rs.getString("so_version")));
                ps=con.prepareStatement("SELECT tablaR.SISTEMA_OPERATIVO_ID_sistema_operativo AS so_id, sistemao.nombre AS so_nombre, sistemao.version AS so_version"+
                                        " FROM LABORATORIO_SISTEMA_OPERATIVO AS tablaR JOIN SISTEMA_OPERATIVO AS sistemao ON tablaR.SISTEMA_OPERATIVO_ID_sistema_operativo=sistemao.ID_sistema_operativo WHERE tablaR.LABORATORIO_ID_laboratorio=?");
                ps.setInt(1, lab.getId());
                ArrayList<SistemaOperativo> so= new ArrayList<>();
                rs=ps.executeQuery();
                if(rs.next()){
                    so.add(new SistemaOperativo(rs.getString("so_nombre"), rs.getString("so_version")));
                    while (rs.next()){
                        so.add(new SistemaOperativo(rs.getString("so_nombre"), rs.getString("so_version")));
                    }
                }lab.setSos(so);
                ps=con.prepareStatement("SELECT tablaR.SOFTWARE_ID_software AS so_id, software.nombre AS so_nombre, software.version AS so_version"+
                                        " FROM SOFTWARE_LABORATORIO AS tablaR JOIN SOFTWARE AS software ON tablaR.SOFTWARE_ID_software=software.ID_software WHERE tablaR.LABORATORIO_ID_laboratorio=?");
                ps.setInt(1, lab.getId());
                ArrayList<Software> sof= new ArrayList<>();
                rs=ps.executeQuery();
                if(rs.next()){
                    sof.add(new Software(rs.getString("so_nombre"), rs.getString("so_version")));
                    while (rs.next()){
                        sof.add(new Software(rs.getString("so_nombre"), rs.getString("so_version")));
                    }
                }lab.setSoftware(sof);
            }
            else{
                throw new PersistenceException("No row with the given id:"+id);
            }
            
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while loading a request.",ex);
        }
        return sol;
    }

    /**
     *
     * @return
     * @throws PersistenceException
     */
    @Override
    public List<Solicitud> loadAll() throws PersistenceException {
        Solicitud sol;
        PreparedStatement ps;
        List<Solicitud> ans=new ArrayList<>();
        try {
             ps = con.prepareStatement("SELECT solicitud.ID_solicitud AS id_solicitud, solicitud.Software AS software,"+
                                        " solicitud.Link_licencia AS licencia, solicitud.Link_descarga AS descarga, solicitud.Estado AS estado,"+
                                        " solicitud.Fecha_radicacion AS fecha_rad, solicitud.Fecha_posible_instalacion AS fecha_instalacion,"+
                                        " solicitud.Fecha_respuesta AS fecha_resp, solicitud.Justificacion AS justificacion,"+
                                        " usuario.ID_Usuario AS id_usuario, usuario.nombre AS usuario_nombre, usuario.email AS email, usuario.tipo_usuario AS tipo_us, "+
                                        " laboratorio.ID_laboratorio AS labid, laboratorio.nombre AS labn, laboratorio.cantidad_equipos AS can_equ,"+
                                        " laboratorio.videobeam AS labv, sistemaop.ID_sistema_operativo AS id_so, sistemaop.nombre AS so_nombre, sistemaop.version AS so_version"+
                                        " FROM SOLICITUD AS solicitud JOIN USUARIO AS usuario ON solicitud.Usuario_id=usuario.ID_usuario"+
                                        " JOIN LABORATORIO AS laboratorio ON solicitud.Laboratorio_id=laboratorio.ID_laboratorio"+
                                        " JOIN SISTEMA_OPERATIVO AS sistemaop ON sistemaop.Solicitud_id=solicitud.ID_solicitud"+
                                        " ORDER BY solicitud.Fecha_Radicacion");
            ResultSet rs=ps.executeQuery();
            int labid=rs.getInt("labid");
            Laboratorio lab=new Laboratorio(rs.getString("labn"),rs.getInt("labid"),rs.getInt( "can_equ"),rs.getBoolean("labv"));
            if (!rs.next()){
                throw new PersistenceException("No requests found.");
            }else{
                sol=new Solicitud(rs.getInt("id_colicitud"),rs.getString("software"),rs.getString("licencia"),rs.getString("descarga"),rs.getString("estado"),rs.getTimestamp("fecha_rad"),rs.getTimestamp("fecha_instalacion"),rs.getTimestamp("fecha_resp"), rs.getString("justificacion"),lab,new SistemaOperativo(rs.getString("so_nombre"),rs.getString("so_version")));
                ps=con.prepareStatement("SELECT tablaR.SISTEMA_OPERATIVO_ID_sistema_operativo AS so_id, sistemao.nombre AS so_nombre, sistemao.version AS so_version"+
                                        " FROM LABORATORIO_SISTEMA_OPERATIVO AS tablaR JOIN SISTEMA_OPERATIVO AS sistemao ON tablaR.SISTEMA_OPERATIVO_ID_sistema_operativo=sistemao.ID_sistema_operativo WHERE tablaR.LABORATORIO_ID_laboratorio=?");
                ps.setInt(1, lab.getId());
                ArrayList<SistemaOperativo> so= new ArrayList<>();
                rs=ps.executeQuery();
                if(rs.next()){
                    so.add(new SistemaOperativo(rs.getString("so_nombre"), rs.getString("so_version")));
                    while (rs.next()){
                        so.add(new SistemaOperativo(rs.getString("so_nombre"), rs.getString("so_version")));
                    }
                }lab.setSos(so);
                ps=con.prepareStatement("SELECT tablaR.SOFTWARE_ID_software AS so_id, software.nombre AS so_nombre, software.version AS so_version"+
                                        " FROM SOFTWARE_LABORATORIO AS tablaR JOIN SOFTWARE AS software ON tablaR.SOFTWARE_ID_software=software.ID_software WHERE tablaR.LABORATORIO_ID_laboratorio=?");
                ps.setInt(1, lab.getId());
                ArrayList<Software> sof= new ArrayList<>();
                rs=ps.executeQuery();
                if(rs.next()){
                    sof.add(new Software(rs.getString("so_nombre"), rs.getString("so_version")));
                    while (rs.next()){
                        sof.add(new Software(rs.getString("so_nombre"), rs.getString("so_version")));
                    }
                }lab.setSoftware(sof);
                ans.add(sol);
            }
            while (rs.next()){
                sol=new Solicitud(rs.getInt("id_colicitud"),rs.getString("software"),rs.getString("licencia"),rs.getString("descarga"),rs.getString("estado"),rs.getTimestamp("fecha_rad"),rs.getTimestamp("fecha_instalacion"),rs.getTimestamp("fecha_resp"), rs.getString("justificacion"),lab,new SistemaOperativo(rs.getString("so_nombre"),rs.getString("so_version")));
                ps=con.prepareStatement("SELECT tablaR.SISTEMA_OPERATIVO_ID_sistema_operativo AS so_id, sistemao.nombre AS so_nombre, sistemao.version AS so_version"+
                                        " FROM LABORATORIO_SISTEMA_OPERATIVO AS tablaR JOIN SISTEMA_OPERATIVO AS sistemao ON tablaR.SISTEMA_OPERATIVO_ID_sistema_operativo=sistemao.ID_sistema_operativo WHERE tablaR.LABORATORIO_ID_laboratorio=?");
                ps.setInt(1, lab.getId());
                ArrayList<SistemaOperativo> so= new ArrayList<>();
                rs=ps.executeQuery();
                if(rs.next()){
                    so.add(new SistemaOperativo(rs.getString("so_nombre"), rs.getString("so_version")));
                    while (rs.next()){
                        so.add(new SistemaOperativo(rs.getString("so_nombre"), rs.getString("so_version")));
                    }
                }lab.setSos(so);
                ps=con.prepareStatement("SELECT tablaR.SOFTWARE_ID_software AS so_id, software.nombre AS so_nombre, software.version AS so_version"+
                                        " FROM SOFTWARE_LABORATORIO AS tablaR JOIN SOFTWARE AS software ON tablaR.SOFTWARE_ID_software=software.ID_software WHERE tablaR.LABORATORIO_ID_laboratorio=?");
                ps.setInt(1, lab.getId());
                ArrayList<Software> sof= new ArrayList<>();
                rs=ps.executeQuery();
                if(rs.next()){
                    sof.add(new Software(rs.getString("so_nombre"), rs.getString("so_version")));
                    while (rs.next()){
                        sof.add(new Software(rs.getString("so_nombre"), rs.getString("so_version")));
                    }
                }lab.setSoftware(sof);
                ans.add(sol);
            }
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while loading a request.",ex);
        }
        return ans;
    }

    @Override
    public void delete(int s) throws PersistenceException {
        PreparedStatement ps;
        try {
            ps=con.prepareStatement("DELETE FROM SOLICITUD WHERE SOLICITUD.ID_solicitud=?");
            ps.setInt(1, s);
            ps.execute();
            con.commit();
        } catch (SQLException ex) {
            Logger.getLogger(JDBCDaoSolicitud.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }

    
}
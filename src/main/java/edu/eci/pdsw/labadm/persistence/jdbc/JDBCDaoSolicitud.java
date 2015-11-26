/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.labadm.persistence.jdbc;

import edu.eci.pdsw.labadm.entities.Laboratorio;
import edu.eci.pdsw.labadm.entities.SistemaOperativo;
import edu.eci.pdsw.labadm.entities.Software;
import edu.eci.pdsw.labadm.entities.Solicitud;
import edu.eci.pdsw.labadm.entities.Usuario;
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
        PreparedStatement ps;
        try {
            //Autogenerar ID
            ps=con.prepareStatement("SELECT COUNT(*) AS cantidad FROM SOLICITUD");
            ResultSet rs=ps.executeQuery();
            rs.next();
            int id=rs.getInt("cantidad")+1;
            s.setId(id);
            //......
            ps=con.prepareStatement("INSERT INTO `SOLICITUD`(`ID_solicitud`, `ID_software`, `Link_licencia`, "
                    + "`Link_descarga`, `Estado`, `Fecha_radicacion`, `Fecha_posible_instalacion`, `Fecha_respuesta`, `Justificacion`, `Usuario_id`, `ID_sistema_operativo`, `Software_instalado`) "
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, s.getId());
            ps.setInt(2, s.getSoftware().getId());
            ps.setString(3, s.getLink_licencia());
            ps.setString(4, s.getLink_descarga());
            ps.setString(5, s.getEstado());
            ps.setDate(6, new java.sql.Date(s.getFecha_rad().getTime()));
            ps.setDate(7,(s.getFecha_posible()==null)? null:new java.sql.Date(s.getFecha_posible().getTime()));
            ps.setDate(8,(s.getFecha_resp()==null)? null:new java.sql.Date(s.getFecha_resp().getTime()));
            ps.setString(9, s.getJustificacion());
            ps.setInt(10, s.getUsuario().getId());
            ps.setInt(11, s.getSo().getId());
            ps.setBoolean(12, s.getSoftware_instalado());
            ps.execute();
            con.commit();
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while saving.", ex);
        }
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
            throw new PersistenceException("An error ocurred while loadin a request.", ex);
        }
    }
    
    @Override
    public void update(Solicitud s) throws PersistenceException {
        PreparedStatement ps;
        try {
            ps=con.prepareStatement("DELETE FROM SOLICITUD WHERE SOLICITUD.ID_solicitud=?");
            ps.setInt(1, s.getId());
            ps.execute();
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred deleting.", ex);
        }
        try {
            ps=con.prepareStatement("INSERT INTO `SOLICITUD`(`ID_solicitud`, `ID_software`, `Link_licencia`, `Link_descarga`,"+
                                    " `Estado`, `Fecha_radicacion`, `Fecha_posible_instalacion`, `Fecha_respuesta`, `Justificacion`,"+
                                    " `Usuario_id`,`ID_sistema_operativo`,`Software_instalado`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1,s.getId());
            ps.setInt(2, s.getSoftware().getId());
            ps.setString(3, s.getLink_licencia());
            ps.setString(4, s.getLink_descarga());
            ps.setString(5, s.getEstado());
            ps.setDate(6, new java.sql.Date(s.getFecha_rad().getTime()));
            ps.setDate(7,(s.getFecha_posible()==null)? null:new java.sql.Date(s.getFecha_posible().getTime()));
            ps.setDate(8,(s.getFecha_resp()==null)? null:new java.sql.Date(s.getFecha_resp().getTime()));
            ps.setString(9, s.getJustificacion());
            ps.setInt(10, s.getUsuario().getId());
            ps.setInt(11, s.getSo().getId());
            ps.setBoolean(12, s.getSoftware_instalado());
            ps.execute();
            con.commit();
        } catch (SQLException ex) {
            throw new PersistenceException("An error occured saving", ex);
        }
    }

    @Override
    public Solicitud loadSolicitud(int id) throws PersistenceException {
        Solicitud sol;
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("SELECT solicitud.ID_solicitud AS id_solicitud, solicitud.Link_licencia AS licencia, solicitud.Link_descarga AS descarga, solicitud.Estado AS estado,solicitud.Fecha_radicacion AS fecha_rad, solicitud.Fecha_posible_instalacion AS fecha_instalacion,solicitud.Fecha_respuesta AS fecha_resp, solicitud.Justificacion AS justificacion, solicitud.Software_instalado AS software_ins,usuario.ID_Usuario AS id_usuario, usuario.nombre AS usuario_nombre, usuario.email AS email, usuario.tipo_usuario AS tipo_us, sistemaop.ID_sistema_operativo AS id_so, sistemaop.nombre AS so_nombre, sistemaop.version AS so_version,software.ID_software AS software_id, software.nombre AS soft_nombre, software.version AS soft_version FROM SOLICITUD AS solicitud JOIN USUARIO AS usuario ON solicitud.Usuario_id=usuario.ID_usuario JOIN SISTEMA_OPERATIVO AS sistemaop ON sistemaop.ID_sistema_operativo=solicitud.ID_sistema_operativo JOIN SOFTWARE AS software ON solicitud.ID_software = software.ID_software  WHERE solicitud.ID_solicitud=? ORDER BY solicitud.Fecha_Radicacion");
            ps.setInt(1, id); 
            ResultSet rs=ps.executeQuery();
            rs.next();
            Usuario u= new Usuario(rs.getInt("id_usuario"), rs.getString("usuario_nombre") , rs.getString ("email"), rs.getInt("tipo_us"));
            SistemaOperativo sos=new SistemaOperativo(rs.getString("so_nombre"),rs.getString("so_version"),rs.getInt("id_so"));
            Software s=new Software(rs.getString("soft_nombre"),rs.getString("soft_version"),rs.getInt("software_id"));
            sol=new Solicitud(rs.getInt("id_solicitud"),s,rs.getString("licencia"),rs.getString("descarga"),rs.getString("estado"),rs.getTimestamp("fecha_rad"),rs.getTimestamp("fecha_instalacion"),rs.getTimestamp("fecha_resp"), rs.getString("justificacion"),sos,u,rs.getBoolean("software_ins"));
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
            ps = con.prepareStatement("SELECT solicitud.ID_solicitud AS id_solicitud, solicitud.Link_licencia AS licencia, solicitud.Link_descarga AS descarga, solicitud.Estado AS estado,solicitud.Fecha_radicacion AS fecha_rad, solicitud.Fecha_posible_instalacion AS fecha_instalacion,solicitud.Fecha_respuesta AS fecha_resp, solicitud.Justificacion AS justificacion,solicitud.Software_instalado AS software_ins,usuario.ID_Usuario AS id_usuario, usuario.nombre AS usuario_nombre, usuario.email AS email, usuario.tipo_usuario AS tipo_us, sistemaop.ID_sistema_operativo AS id_so, sistemaop.nombre AS so_nombre, sistemaop.version AS so_version,software.ID_software AS software_id, software.nombre AS soft_nombre, software.version AS soft_version FROM SOLICITUD AS solicitud JOIN USUARIO AS usuario ON solicitud.Usuario_id=usuario.ID_usuario JOIN SISTEMA_OPERATIVO AS sistemaop ON sistemaop.ID_sistema_operativo=solicitud.ID_sistema_operativo JOIN SOFTWARE AS software ON solicitud.ID_software = software.ID_software ORDER BY solicitud.Fecha_Radicacion");
            ResultSet rs=ps.executeQuery();
            rs.next();
            Usuario u= new Usuario(rs.getInt("id_usuario"), rs.getString("usuario_nombre") , rs.getString ("email"), rs.getInt("tipo_us"));
            SistemaOperativo sos=new SistemaOperativo(rs.getString("so_nombre"),rs.getString("so_version"),rs.getInt("id_so"));
            Software s=new Software(rs.getString("soft_nombre"),rs.getString("soft_version"),rs.getInt("software_id"));
            sol=new Solicitud(rs.getInt("id_solicitud"),s,rs.getString("licencia"),rs.getString("descarga"),rs.getString("estado"),rs.getTimestamp("fecha_rad"),rs.getTimestamp("fecha_instalacion"),rs.getTimestamp("fecha_resp"), rs.getString("justificacion"),sos,u,rs.getBoolean("software_ins"));
            ans.add(sol);
            while (rs.next()){
                u= new Usuario(rs.getInt("id_usuario"), rs.getString("usuario_nombre") , rs.getString ("email"), rs.getInt("tipo_us"));
                sos=new SistemaOperativo(rs.getString("so_nombre"),rs.getString("so_version"),rs.getInt("id_so"));
                s=new Software(rs.getString("soft_nombre"),rs.getString("soft_version"),rs.getInt("software_id"));
                sol=new Solicitud(rs.getInt("id_solicitud"),s,rs.getString("licencia"),rs.getString("descarga"),rs.getString("estado"),rs.getTimestamp("fecha_rad"),rs.getTimestamp("fecha_instalacion"),rs.getTimestamp("fecha_resp"), rs.getString("justificacion"),sos,u,rs.getBoolean("software_ins"));
                ans.add(sol);
            }
          
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while loading a request.",ex);
        }
        return ans;
    }    
    
    @Override
    public List<Solicitud> loadWithoutAnswer() throws PersistenceException {
        Solicitud sol;
        PreparedStatement ps;
        List<Solicitud> ans=new ArrayList<>();
        try {
            ps = con.prepareStatement("SELECT solicitud.ID_solicitud AS id_solicitud, solicitud.Link_licencia AS licencia, solicitud.Link_descarga AS descarga, solicitud.Estado AS estado,solicitud.Fecha_radicacion AS fecha_rad, solicitud.Fecha_posible_instalacion AS fecha_instalacion,solicitud.Fecha_respuesta AS fecha_resp, solicitud.Justificacion AS justificacion,solicitud.Software_instalado AS software_ins,usuario.ID_Usuario AS id_usuario, usuario.nombre AS usuario_nombre, usuario.email AS email, usuario.tipo_usuario AS tipo_us, sistemaop.ID_sistema_operativo AS id_so, sistemaop.nombre AS so_nombre, sistemaop.version AS so_version,software.ID_software AS software_id, software.nombre AS soft_nombre, software.version AS soft_version FROM SOLICITUD AS solicitud JOIN USUARIO AS usuario ON solicitud.Usuario_id=usuario.ID_usuario JOIN SISTEMA_OPERATIVO AS sistemaop ON sistemaop.ID_sistema_operativo=solicitud.ID_sistema_operativo JOIN SOFTWARE AS software ON solicitud.ID_software = software.ID_software WHERE solicitud.Estado is ? ORDER BY solicitud.Fecha_Radicacion");
            ps.setString(1, null);
            ResultSet rs=ps.executeQuery();
            rs.next();
            Usuario u= new Usuario(rs.getInt("id_usuario"), rs.getString("usuario_nombre") , rs.getString ("email"), rs.getInt("tipo_us"));
            SistemaOperativo sos=new SistemaOperativo(rs.getString("so_nombre"),rs.getString("so_version"),rs.getInt("id_so"));
            Software s=new Software(rs.getString("soft_nombre"),rs.getString("soft_version"),rs.getInt("software_id"));
            sol=new Solicitud(rs.getInt("id_solicitud"),s,rs.getString("licencia"),rs.getString("descarga"),rs.getString("estado"),rs.getTimestamp("fecha_rad"),rs.getTimestamp("fecha_instalacion"),rs.getTimestamp("fecha_resp"), rs.getString("justificacion"),sos,u,rs.getBoolean("software_ins"));
            ans.add(sol);
            while (rs.next()){
                u= new Usuario(rs.getInt("id_usuario"), rs.getString("usuario_nombre") , rs.getString ("email"), rs.getInt("tipo_us"));
                sos=new SistemaOperativo(rs.getString("so_nombre"),rs.getString("so_version"),rs.getInt("id_so"));
                s=new Software(rs.getString("soft_nombre"),rs.getString("soft_version"),rs.getInt("software_id"));
                sol=new Solicitud(rs.getInt("id_solicitud"),s,rs.getString("licencia"),rs.getString("descarga"),rs.getString("estado"),rs.getTimestamp("fecha_rad"),rs.getTimestamp("fecha_instalacion"),rs.getTimestamp("fecha_resp"), rs.getString("justificacion"),sos,u,rs.getBoolean("software_ins"));
                ans.add(sol);
            }
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while loading a request.",ex);
        }
        return ans;
    }

    @Override
    public List<Solicitud> loadWithAnswer() throws PersistenceException {
        Solicitud sol;
        PreparedStatement ps;
        List<Solicitud> ans=new ArrayList<>();
        try {
            ps = con.prepareStatement("SELECT solicitud.ID_solicitud AS id_solicitud, solicitud.Link_licencia AS licencia, solicitud.Link_descarga AS descarga, solicitud.Estado AS estado,solicitud.Fecha_radicacion AS fecha_rad, solicitud.Fecha_posible_instalacion AS fecha_instalacion,solicitud.Fecha_respuesta AS fecha_resp, solicitud.Justificacion AS justificacion,solicitud.Software_instalado AS software_ins,usuario.ID_Usuario AS id_usuario, usuario.nombre AS usuario_nombre, usuario.email AS email, usuario.tipo_usuario AS tipo_us, sistemaop.ID_sistema_operativo AS id_so, sistemaop.nombre AS so_nombre, sistemaop.version AS so_version,software.ID_software AS software_id, software.nombre AS soft_nombre, software.version AS soft_version FROM SOLICITUD AS solicitud JOIN USUARIO AS usuario ON solicitud.Usuario_id=usuario.ID_usuario JOIN SISTEMA_OPERATIVO AS sistemaop ON sistemaop.ID_sistema_operativo=solicitud.ID_sistema_operativo JOIN SOFTWARE AS software ON solicitud.ID_software = software.ID_software WHERE solicitud.Estado is not ? AND solicitud.Software_instalado=? ORDER BY solicitud.Fecha_Radicacion");
            ps.setString(1, null);
            ps.setBoolean(2, false);
            ResultSet rs=ps.executeQuery();
            rs.next();
            Usuario u= new Usuario(rs.getInt("id_usuario"), rs.getString("usuario_nombre") , rs.getString ("email"), rs.getInt("tipo_us"));
            SistemaOperativo sos=new SistemaOperativo(rs.getString("so_nombre"),rs.getString("so_version"),rs.getInt("id_so"));
            Software s=new Software(rs.getString("soft_nombre"),rs.getString("soft_version"),rs.getInt("software_id"));
            sol=new Solicitud(rs.getInt("id_solicitud"),s,rs.getString("licencia"),rs.getString("descarga"),rs.getString("estado"),rs.getTimestamp("fecha_rad"),rs.getTimestamp("fecha_instalacion"),rs.getTimestamp("fecha_resp"), rs.getString("justificacion"),sos,u,rs.getBoolean("software_ins"));
            ans.add(sol);
            while (rs.next()){
                u= new Usuario(rs.getInt("id_usuario"), rs.getString("usuario_nombre") , rs.getString ("email"), rs.getInt("tipo_us"));
                sos=new SistemaOperativo(rs.getString("so_nombre"),rs.getString("so_version"),rs.getInt("id_so"));
                s=new Software(rs.getString("soft_nombre"),rs.getString("soft_version"),rs.getInt("software_id"));
                sol=new Solicitud(rs.getInt("id_solicitud"),s,rs.getString("licencia"),rs.getString("descarga"),rs.getString("estado"),rs.getTimestamp("fecha_rad"),rs.getTimestamp("fecha_instalacion"),rs.getTimestamp("fecha_resp"), rs.getString("justificacion"),sos,u,rs.getBoolean("software_ins"));
                ans.add(sol);
            }
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while loading a request.",ex);
        }
        return ans;
    }

    @Override
    public List<Solicitud> loadAppproved() throws PersistenceException {
        Solicitud sol;
        PreparedStatement ps;
        List<Solicitud> ans=new ArrayList<>();
        try {
            ps = con.prepareStatement("SELECT solicitud.ID_solicitud AS id_solicitud, solicitud.Link_licencia AS licencia, solicitud.Link_descarga AS descarga, solicitud.Estado AS estado,solicitud.Fecha_radicacion AS fecha_rad, solicitud.Fecha_posible_instalacion AS fecha_instalacion,solicitud.Fecha_respuesta AS fecha_resp, solicitud.Justificacion AS justificacion,solicitud.Software_instalado AS software_ins,usuario.ID_Usuario AS id_usuario, usuario.nombre AS usuario_nombre, usuario.email AS email, usuario.tipo_usuario AS tipo_us, sistemaop.ID_sistema_operativo AS id_so, sistemaop.nombre AS so_nombre, sistemaop.version AS so_version,software.ID_software AS software_id, software.nombre AS soft_nombre, software.version AS soft_version FROM SOLICITUD AS solicitud JOIN USUARIO AS usuario ON solicitud.Usuario_id=usuario.ID_usuario JOIN SISTEMA_OPERATIVO AS sistemaop ON sistemaop.ID_sistema_operativo=solicitud.ID_sistema_operativo JOIN SOFTWARE AS software ON solicitud.ID_software = software.ID_software WHERE solicitud.Software_instalado=? AND solicitud.Estado= ? ORDER BY solicitud.Fecha_Radicacion ASC");
            ps.setInt(1, 0);
            ps.setString(2, "aprobada");
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                Usuario u= new Usuario(rs.getInt("id_usuario"), rs.getString("usuario_nombre") , rs.getString ("email"), rs.getInt("tipo_us"));
                SistemaOperativo sos=new SistemaOperativo(rs.getString("so_nombre"),rs.getString("so_version"),rs.getInt("id_so"));
                Software s=new Software(rs.getString("soft_nombre"),rs.getString("soft_version"),rs.getInt("software_id"));
                sol=new Solicitud(rs.getInt("id_solicitud"),s,rs.getString("licencia"),rs.getString("descarga"),rs.getString("estado"),rs.getTimestamp("fecha_rad"),rs.getTimestamp("fecha_instalacion"),rs.getTimestamp("fecha_resp"), rs.getString("justificacion"),sos,u,rs.getBoolean("software_ins"));
                ans.add(sol);
            }
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while loading a request.",ex);
        }
        return ans;
    }
 
}

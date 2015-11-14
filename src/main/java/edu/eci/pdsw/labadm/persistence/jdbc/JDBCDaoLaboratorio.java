/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.labadm.persistence.jdbc;

import edu.eci.pdsw.labadm.entities.Laboratorio;
import edu.eci.pdsw.labadm.entities.SistemaOperativo;
import edu.eci.pdsw.labadm.entities.Software;
import edu.eci.pdsw.labadm.persistence.DaoLaboratorio;
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
 * @author 2101047
 */
public class JDBCDaoLaboratorio implements DaoLaboratorio {
    Connection con;

    public JDBCDaoLaboratorio(Connection con) {
        this.con = con;
    }

    @Override
    public void save(Laboratorio l) throws PersistenceException {
          PreparedStatement ps;
        try {
            ps=con.prepareStatement("INSERT INTO `LABORATORIO`(`ID_laboratorio`, `nombre`, `cantidad_equipos`, `videobeam`) VALUES (?,?,?,?)");
            ps.setInt(1, l.getId());
            ps.setString(2, l.getNombre());
            ps.setInt(3, l.getCant_equipos());
            ps.setBoolean(4, l.isVideobeam());
            
            for(Software s: l.getSoftware()){
                ps=con.prepareStatement("INSERT INTO `SOFTWARE_LABORATORIO`(`SOFTWARE_ID_software`, `LABORATORIO_ID_laboratorio`) VALUES (?,?)");
                ps.setInt(1, s.getId());
                ps.setInt(2, l.getId());
            }
            
            for(Software so: l.getSoftware()){
                ps=con.prepareStatement("INSERT INTO `LABORATORIO_SISTEMA_OPERATIVO`(`LABORATORIO_ID_laboratorio`, `SISTEMA_OPERATIVO_ID_sistema_operativo`) VALUES (?,?)");
                ps.setInt(1, so.getId());
                ps.setInt(2, l.getId());
            }
            con.commit();
        } catch (SQLException ex) {
            Logger.getLogger(JDBCDaoLaboratorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Laboratorio> loadBySo(SistemaOperativo so) throws PersistenceException {
        Laboratorio lab;
        PreparedStatement ps;
        List<Laboratorio> ans=new ArrayList<>();
        try {
            ps = con.prepareStatement("SELECT laboratorio.ID_laboratorio AS labid, laboratorio.nombre AS labn, laboratorio.cantidad_equipos AS can_equ,laboratorio.videobeam AS labv FROM LABORATORIO_SISTEMA_OPERATIVO AS tablaR JOIN LABORATORIO AS laboratorio ON tablaR.LABORATORIO_ID_laboratorio=laboratorio.ID_laboratorio WHERE tablaR.SISTEMA_OPERATIVO_ID_sistema_operativo=?");
            ps.setInt(1, so.getId());
            ResultSet rs=ps.executeQuery();
            if (!rs.next()){
                throw new PersistenceException("No requests found.");
            }else{
                lab=new Laboratorio(rs.getString("labn"),rs.getInt("labid"),rs.getInt( "can_equ"),rs.getBoolean("labv"));
                ps=con.prepareStatement("SELECT tablaR.SISTEMA_OPERATIVO_ID_sistema_operativo AS so_id, sistemao.nombre AS so_nombre, sistemao.version AS so_version"+
                                        " FROM LABORATORIO_SISTEMA_OPERATIVO AS tablaR JOIN SISTEMA_OPERATIVO AS sistemao ON tablaR.SISTEMA_OPERATIVO_ID_sistema_operativo=sistemao.ID_sistema_operativo WHERE tablaR.LABORATORIO_ID_laboratorio=?");
                ps.setInt(1, lab.getId());
                ArrayList<SistemaOperativo> sos= new ArrayList<>();
                ResultSet rss = ps.executeQuery();
                if(rss.next()){
                    sos.add(new SistemaOperativo(rss.getString("so_nombre"),rss.getString("so_version"),rss.getInt("so_id")));
                    while (rss.next()){
                        sos.add(new SistemaOperativo(rss.getString("so_nombre"),rss.getString("so_version"),rss.getInt("so_id")));
                    }
                }
                lab.setSos(sos);
                ps=con.prepareStatement("SELECT tablaR.SOFTWARE_ID_software AS so_id, software.nombre AS so_nombre, software.version AS so_version"+
                                        " FROM SOFTWARE_LABORATORIO AS tablaR JOIN SOFTWARE AS software ON tablaR.SOFTWARE_ID_software=software.ID_software WHERE tablaR.LABORATORIO_ID_laboratorio=?");
                ps.setInt(1, lab.getId());
                ArrayList<Software> sof= new ArrayList<>();
                ResultSet rss2=ps.executeQuery();
                if(rss2.next()){
                    sof.add(new Software(rss2.getString("so_nombre"), rss2.getString("so_version"),rss2.getInt("so_id")));
                    while (rss2.next()){
                        sof.add(new Software(rss2.getString("so_nombre"), rss2.getString("so_version"),rss2.getInt("so_id")));
                    }
                }lab.setSoftware(sof);
                ans.add(lab);
            }
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while loading a request.",ex);
        }
        return ans;
    }

    @Override
    public void Update(Laboratorio l) throws PersistenceException {
        PreparedStatement ps;
        try {
            ps=con.prepareStatement("DELETE FROM LABORATORIO WHERE LABORATORIO.ID_laboratorio=?");
            ps.setInt(1, l.getId());
            ps.execute();
            con.commit();
            this.save(l);
        } catch (SQLException ex) {
            Logger.getLogger(JDBCDaoSolicitud.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }

    @Override
    public List<Laboratorio> loadAll() throws PersistenceException {
        Laboratorio lab;
        PreparedStatement ps;
        List<Laboratorio> ans=new ArrayList<>();
        try {
            ps = con.prepareStatement("SELECT laboratorio.ID_laboratorio AS labid, laboratorio.nombre AS labn, laboratorio.cantidad_equipos AS can_equ,"+
                                        " laboratorio.videobeam AS labv, FROM  LABORATORIO AS laboratorio");
            ResultSet rs=ps.executeQuery();
            if (!rs.next()){
                throw new PersistenceException("No requests found.");
            }else{
                lab=new Laboratorio(rs.getString("labn"),rs.getInt("labid"),rs.getInt( "can_equ"),rs.getBoolean("labv"));
                ps=con.prepareStatement("SELECT tablaR.SISTEMA_OPERATIVO_ID_sistema_operativo AS so_id, sistemao.nombre AS so_nombre, sistemao.version AS so_version"+
                                        " FROM LABORATORIO_SISTEMA_OPERATIVO AS tablaR JOIN SISTEMA_OPERATIVO AS sistemao ON tablaR.SISTEMA_OPERATIVO_ID_sistema_operativo=sistemao.ID_sistema_operativo WHERE tablaR.LABORATORIO_ID_laboratorio=?");
                ps.setInt(1, lab.getId());
                ArrayList<SistemaOperativo> so= new ArrayList<>();
                    rs=ps.executeQuery();
                if(rs.next()){
                    so.add(new SistemaOperativo(rs.getString("so_nombre"),rs.getString("so_version"),rs.getInt("so_id")));
                    while (rs.next()){
                        so.add(new SistemaOperativo(rs.getString("so_nombre"),rs.getString("so_version"),rs.getInt("so_id")));
                    }
                }lab.setSos(so);
                ps=con.prepareStatement("SELECT tablaR.SOFTWARE_ID_software AS so_id, software.nombre AS so_nombre, software.version AS so_version"+
                                        " FROM SOFTWARE_LABORATORIO AS tablaR JOIN SOFTWARE AS software ON tablaR.SOFTWARE_ID_software=software.ID_software WHERE tablaR.LABORATORIO_ID_laboratorio=?");
                ps.setInt(1, lab.getId());
                ArrayList<Software> sof= new ArrayList<>();
                rs=ps.executeQuery();
                if(rs.next()){
                    sof.add(new Software(rs.getString("so_nombre"), rs.getString("so_version"),rs.getInt("so_id")));
                    while (rs.next()){
                        sof.add(new Software(rs.getString("so_nombre"), rs.getString("so_version"),rs.getInt("so_id")));
                    }
                }lab.setSoftware(sof);
                ans.add(lab);
                while (rs.next()){
                   lab=new Laboratorio(rs.getString("labn"),rs.getInt("labid"),rs.getInt( "can_equ"),rs.getBoolean("labv"));
                    ps=con.prepareStatement("SELECT tablaR.SISTEMA_OPERATIVO_ID_sistema_operativo AS so_id, sistemao.nombre AS so_nombre, sistemao.version AS so_version"+
                                            " FROM LABORATORIO_SISTEMA_OPERATIVO AS tablaR JOIN SISTEMA_OPERATIVO AS sistemao ON tablaR.SISTEMA_OPERATIVO_ID_sistema_operativo=sistemao.ID_sistema_operativo WHERE tablaR.LABORATORIO_ID_laboratorio=?");
                    ps.setInt(1, lab.getId());
                    ArrayList<SistemaOperativo> so1= new ArrayList<>();
                    rs=ps.executeQuery();
                    if(rs.next()){
                        so1.add(new SistemaOperativo(rs.getString("so_nombre"),rs.getString("so_version"),rs.getInt("so_id")));
                        while (rs.next()){
                            so1.add(new SistemaOperativo(rs.getString("so_nombre"),rs.getString("so_version"),rs.getInt("so_id")));
                        }
                    }lab.setSos(so);
                    ps=con.prepareStatement("SELECT tablaR.SOFTWARE_ID_software AS so_id, software.nombre AS so_nombre, software.version AS so_version"+
                                            " FROM SOFTWARE_LABORATORIO AS tablaR JOIN SOFTWARE AS software ON tablaR.SOFTWARE_ID_software=software.ID_software WHERE tablaR.LABORATORIO_ID_laboratorio=?");
                    ps.setInt(1, lab.getId());
                    ArrayList<Software> sof1= new ArrayList<>();
                    rs=ps.executeQuery();
                    if(rs.next()){
                        sof1.add(new Software(rs.getString("so_nombre"), rs.getString("so_version"),rs.getInt("so_id")));
                        while (rs.next()){
                            sof1.add(new Software(rs.getString("so_nombre"), rs.getString("so_version"),rs.getInt("so_id")));
                        }
                    }lab.setSoftware(sof);
                    ans.add(lab);
                }
            }
        } catch (SQLException ex) {
            throw new PersistenceException("An error ocurred while loading a request.",ex);
        }
        return ans;
    }
}

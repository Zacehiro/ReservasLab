/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.labadm.entities;

import java.util.Date;

/**
 *
 * @author usuario
 */
public class Solicitud {
    private int id;
    private Software software;
    private String link_licencia;
    private String link_descarga;
    private String estado;
    private Date fecha_rad;
    private Date fecha_posible;
    private Date fecha_resp;
    private String justificacion;
    private Laboratorio laboratorio;
    private SistemaOperativo so;
    private Usuario usuario;


    public Solicitud(int id, Software software, String link_licencia, String link_descarga, String estado, Date fecha_rad, Date fecha_posible, Date fecha_resp, String justificacion, Laboratorio laboratorio, SistemaOperativo so, Usuario u){
        this.id=id;
        this.estado=estado;
        this.fecha_posible=fecha_posible;
        this.fecha_rad=fecha_rad;
        this.fecha_resp=fecha_resp;
        this.justificacion=justificacion;
        this.link_descarga=link_descarga;
        this.link_licencia=link_licencia;
        this.software=software;
        this.laboratorio=laboratorio;
        this.so=so;
        this.usuario=u;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public int getId() {
        return id;
    }
    
    public Solicitud(){
        
    }
    
    public Laboratorio getLaboratorio() {
        return laboratorio;
    }
    
    public SistemaOperativo getSo() {
        return so;
    }

    public void setSo(SistemaOperativo so) {
        this.so = so;
    }

    public void setLaboratorio(Laboratorio laboratorio) {
        this.laboratorio = laboratorio;
    }

    public Software getSoftware() {
        return software;
    }

    public void setSoftware(Software software) {
        this.software = software;
    }

    public String getLink_licencia() {
        return link_licencia;
    }

    public void setLink_licencia(String link_licencia) {
        this.link_licencia = link_licencia;
    }

    public String getLink_descarga() {
        return link_descarga;
    }

    public void setLink_descarga(String link_descarga) {
        this.link_descarga = link_descarga;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
    }

    public Date getFecha_rad() {
        return fecha_rad;
    }

    public void setFecha_rad(Date fecha_rad) {
        this.fecha_rad = fecha_rad;
    }

    public Date getFecha_posible() {
        return fecha_posible;
    }

    public void setFecha_posible(Date fecha_posible) {
        this.fecha_posible = fecha_posible;
    }

    public Date getFecha_resp() {
        return fecha_resp;
    }

    public void setFecha_resp(Date fecha_resp) {
        this.fecha_resp = fecha_resp;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.labadm.entities;

import java.util.Date;

/**
 *
 * @author Zacehiro
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
    private SistemaOperativo so;
    private Usuario usuario;
    private Boolean Software_instalado;

    /**
     * Creador de una solicitud
     * @param id identificador de la solicitud.
     * @param software software que se requiere.
     * @param link_licencia link de licencia del software.
     * @param link_descarga link para descargar el software.
     * @param estado si la solicitud ha sido aprobada o negada.
     * @param fecha_rad fecha en la que se radico la solicitud.
     * @param fecha_posible fecha de posible instalacion, si fue aprobada.
     * @param fecha_resp fecha en la que fue aprobada o negada.
     * @param justificacion por que no se puede realizar, si fue negada.
     * @param so sistema operativo que se requiere.
     * @param u usuario que realizo la solicitud
     * @param Software_instalado si el software ya fue o no instalado, si fue aprobada.
     */
    public Solicitud(int id, Software software, String link_licencia, String link_descarga, String estado, Date fecha_rad, Date fecha_posible, Date fecha_resp, String justificacion, SistemaOperativo so, Usuario u, Boolean Software_instalado){
        this.id=id;
        this.estado=estado;
        this.fecha_posible=fecha_posible;
        this.fecha_rad=fecha_rad;
        this.fecha_resp=fecha_resp;
        this.justificacion=justificacion;
        this.link_descarga=link_descarga;
        this.link_licencia=link_licencia;
        this.software=software;
        this.so=so;
        this.usuario=u;
        this.Software_instalado=Software_instalado;
    }
    
    /**
     * Creador de una solicitud nula.
     */
    public Solicitud(){
        estado=null;
        fecha_posible=null;
        fecha_rad=null;
        fecha_resp=null;
        justificacion=null;
        link_descarga=null;
        link_licencia=null;
        software=new Software();
        so=null;
        usuario=null;
    }
    
    /**
     * Consultar si el software ya fue instalado.
     * @return si el software ya fue instalado o no.
     */
    public Boolean getSoftware_instalado() {
        return Software_instalado;
    }

    /**
     * Asignar propiedad de software instalado.
     * @param Software_instalado si el software ya fue instalado o no.
     */
    public void setSoftware_instalado(Boolean Software_instalado) {
        this.Software_instalado = Software_instalado;
    }
    
    /**
     * Consultar el usuario de la solicitud.
     * @return usuario de la solicitud.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Asignar un usuario a la solicitud.
     * @param usuario usuario que se asignara.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    /**
     * Consultar identificador de la solicitud.
     * @return identificador de la solicitud.
     */
    public int getId() {
        return id;
    }
    
    /**
     * Asignar un identificador a la solicitud.
     * @param id identificador de la solicitud que se asignara.
     */
    public void setId(int id) {
        this.id = id;
    }
        
    /**
     * Consulatr el sistema operativo de la solicitud.
     * @return sistema operativo de la solicitud.
     */
    public SistemaOperativo getSo() {
        return so;
    }

    /**
     * Asignar un sistema operativo a la solicitud.
     * @param so sistema operativo que se asignara a la solicitud.
     */
    public void setSo(SistemaOperativo so) {
        this.so = so;
    }

    /**
     * Consultar el software de la solicitud.
     * @return software de la solicitud.
     */
    public Software getSoftware() {
        return software;
    }

    /**
     * Asignar un software a la solicitud.
     * @param software software que se va a asignar a la solicitud.
     */
    public void setSoftware(Software software) {
        this.software = software;
    }

    /**
     * Consultar el link de la licencia del software de la solicitud.
     * @return link de la licencia del software.
     */
    public String getLink_licencia() {
        return link_licencia;
    }

    /**
     * Asignar un link de la licencia del software a la solicitud.
     * @param link_licencia link de la licencia del software que se asignara.
     */
    public void setLink_licencia(String link_licencia) {
        this.link_licencia = link_licencia;
    }

    /**
     * Consultar el link de descarga del software de la solicitud.
     * @return link de descarga del software.
     */
    public String getLink_descarga() {
        return link_descarga;
    }

    /**
     * Asignar un link de descarga del software a la solicitud.
     * @param link_descarga link de descarga del software que sera asignado.
     */
    public void setLink_descarga(String link_descarga) {
        this.link_descarga = link_descarga;
    }

    /**
     * Consultar el estado de la solicitud.
     * @return estado de la solicitud.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Asignar un estado a la solicitud.
     * @param estado estado que sera asignado a la solicitud.
     */
    public void setEstado(String estado) {
        this.estado=estado;
    }

    /**
     * Consultar la fecha de radicacion de la solicitud.
     * @return fecha de radicacion de la solicitud.
     */
    public Date getFecha_rad() {
        return fecha_rad;
    }

    /**
     * Asignar una fecha de radicacion a la solicitud.
     * @param fecha_rad fecha de radicacion que se asignara a la solicitud.
     */
    public void setFecha_rad(Date fecha_rad) {
        this.fecha_rad = fecha_rad;
    }

    /**
     * Consultar la fecha de posible instalacion del software de la solicitud.
     * @return fecha de posible instalacion del software
     */
    public Date getFecha_posible() {
        return fecha_posible;
    }

    /**
     * Asignar una fecha de posible instalacion del software a la solicitud.
     * @param fecha_posible fecha de posible instalacion del software que se asignara.
     */
    public void setFecha_posible(Date fecha_posible) {
        this.fecha_posible = fecha_posible;
    }

    /**
     * Consultar la fecha en la que se dio respuesta a la solicitud.
     * @return fecha en la que se dio respuesta a la solicitud.
     */
    public Date getFecha_resp() {
        return fecha_resp;
    }

    /**
     * Asignar una fecha en la que se dio respuesta a la solicitud.
     * @param fecha_resp fecha en la que se dio respuesta a la solicitud que se asignara.
     */
    public void setFecha_resp(Date fecha_resp) {
        this.fecha_resp = fecha_resp;
    }

    /**
     * Consultar el porque no fue aceptada la solicitud.
     * @return justificacion de negacion de la solicitud.
     */
    public String getJustificacion() {
        return justificacion;
    }

    /**
     * Asignar una justificacion a la solicitud.
     * @param justificacion justificacion que sera asignada a la solicitud.
     */
    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }
}

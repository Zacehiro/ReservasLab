/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.labadm.entities;

import java.util.ArrayList;

/**
 *
 * @author Zacehiro
 */
public class Usuario {
    private int id;
    private String nombre;
    private String email;
    /*
    *Usuario tipo 1: PROFESORES
    *Usuario tipo 2: Asistente Laboratorio
    *Usuario tipo 3: cualquiera 
    */
    private int tipo_usuario;
    private ArrayList<Solicitud> solicitud;
    
    /**
     * Creador de un usuario.
     * @param id numero de identificacion del usuario.
     * @param nombre nombre del usuario.
     * @param email correo electronico del usuario.
     * @param tipo_usuario tipo de usuario.
     */
    public Usuario(int id, String nombre, String email, int tipo_usuario){
        this.id=id;
        this.nombre=nombre;
        this.email=email;
        this.tipo_usuario=tipo_usuario;
        solicitud = new ArrayList<Solicitud>();
    }

    /**
     * Consultar el numero de identificaion del usuario.
     * @return numero de identificaion del usuario.
     */
    public int getId() {
        return id;
    }

    /**
     * Asignar un numero de identificacion al usuario.
     * @param id numero de identificacion que se asignara.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Consultar las solicitudes del usuario.
     * @return lista de solicitudes realizadas por el usuario.
     */
    public ArrayList<Solicitud> getSolicitud() {
        return solicitud;
    }

    /**
     * Asignar una lista de solicitudes al usuario.
     * @param solicitud lista de solicitudes que se asignara.
     */
    public void setSolicitud(ArrayList<Solicitud> solicitud) {
        this.solicitud = solicitud;
    }

    /**
     * Consultar el nombre del usuario.
     * @return nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asignar un nombre al usuario.
     * @param nombre nombre que sera asignado.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Consultar el correo electronico del usuario.
     * @return correo electronico del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Asignar un correo electronico al usuario.
     * @param email correo electronico que sera asignado.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Consultar el tipo de usuario.
     * @return tipo de usuario.
     */
    public int getTipo_usuario() {
        return tipo_usuario;
    }

    /**
     * Asignar un tipo de usuario.
     * @param tipo_usuario tipo de usuario que se asignara.
     */
    public void setTipo_usuario(int tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }
    
}

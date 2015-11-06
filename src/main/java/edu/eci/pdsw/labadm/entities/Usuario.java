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
    
    public Usuario(int id, String nombre, String email, int tipo_usuario){
        this.id=id;
        this.nombre=nombre;
        this.email=email;
        this.tipo_usuario=tipo_usuario;
        solicitud = new ArrayList<Solicitud>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Solicitud> getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(ArrayList<Solicitud> solicitud) {
        this.solicitud = solicitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(int tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }
    
}

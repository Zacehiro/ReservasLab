/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.entities;

import java.util.ArrayList;

/**
 *
 * @author Zacehiro
 */
public class Laboratorio {
    
    private String nombre;
    private int id;
    private int cant_equipos;
    private boolean videobeam;
    private ArrayList<SistemaOperativo> sos;
    private ArrayList<Software> software;
    
    public Laboratorio(String nombre, int id, int cant_equipos, boolean videobeam){
        this.nombre = nombre;
        this.id = id;
        this.cant_equipos = cant_equipos;
        this.videobeam = videobeam;
        sos=new ArrayList<SistemaOperativo>();
        software= new ArrayList<Software>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCant_equipos() {
        return cant_equipos;
    }

    public void setCant_equipos(int cant_equipos) {
        this.cant_equipos = cant_equipos;
    }

    public boolean isVideobeam() {
        return videobeam;
    }

    public void setVideobeam(boolean videobeam) {
        this.videobeam = videobeam;
    }

    public ArrayList<SistemaOperativo> getSos() {
        return sos;
    }

    public void setSos(ArrayList<SistemaOperativo> sos) {
        this.sos = sos;
    }

    public ArrayList<Software> getSoftware() {
        return software;
    }

    public void setSoftware(ArrayList<Software> software) {
        this.software = software;
    }
    
    
}

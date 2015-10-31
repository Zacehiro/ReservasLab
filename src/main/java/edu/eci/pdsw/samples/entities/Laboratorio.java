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
    private String version;
    private ArrayList<SistemaOperativo> sos;
    private ArrayList<Software> software;
    
    public Laboratorio(String nombre, String version){
        this.nombre=nombre;
        this.version=version;
        sos=new ArrayList<SistemaOperativo>();
        software= new ArrayList<Software>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

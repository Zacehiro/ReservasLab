/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.labadm.entities;

/**
 *
 * @author Zacehiro
 */
public class SistemaOperativo {
    private String nombre;
    private String version;
    
    public SistemaOperativo(String nombre, String version){
        this.nombre=nombre;
        this.version=version;
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
    
}

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
public class Software {
    private String nombre;
    private String version;
    private int id;
    
    /**
     * Creador de un software.
     * @param nombre nombre del software.
     * @param version version del software.
     * @param id numero de identificacion del software.
     */
    public Software(String nombre, String version,int id){
        this.nombre=nombre;
        this.version=version;
        this.id=id;
    }
    
    /**
     * Creador de un software.
     * @param nombre nombre del software.
     * @param version version del software.
     */
    public Software(String nombre, String version){
        this.nombre=nombre;
        this.version=version;
    }
    
    /**
     * Creador de un software nulo.
     */
    public Software(){}
    
    /**
     * Consultar el id del software.
     * @return id del software.
     */
    public int getId() {
        return id;
    }

    /**
     * Asignar un numero de identificacion al software.
     * @param id numero de identificacion que se asignara.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Consultar el nombre del software.
     * @return nombre del software.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asignar un nombre al software.
     * @param nombre nombre que sera asignado.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Consultar la version del software.
     * @return version del software.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Asignar una version al software.
     * @param version version que se asignara.
     */
    public void setVersion(String version) {
        this.version = version;
    }
}

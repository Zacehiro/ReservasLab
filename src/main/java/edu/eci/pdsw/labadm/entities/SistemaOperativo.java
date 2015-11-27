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
    private int id;

    /**
     * Creador del sistema operativo.
     * @param nombre nombre del sistema operativo.
     * @param version version del sistema operativo.
     * @param id numero de identificacion del sistema operativo.
     */
    public SistemaOperativo(String nombre, String version,int id){
        this.nombre=nombre;
        this.version=version;
        this.id=id;
    }

    /**
     * Consultar el id del sistema operativo.
     * @return id del sistema operativo.
     */
    public int getId() {
        return id;
    }

    /**
     * Asignar un numero de identificacion al sistema operativo.
     * @param id numero de identificacion que se asignara.
     */
    public void setId(int id) {
        this.id = id;
    }
     
    /**
     * Consultar el nombre del sistema operativo.
     * @return nombre del sistema operativo.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asignar un nombre al sistema operativo.
     * @param nombre nombre que sera asignado.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Consultar la version del sistema operativo.
     * @return version del sistema operativo.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Asignar una version al sistema operativo.
     * @param version version que se asignara.
     */
    public void setVersion(String version) {
        this.version = version;
    }
    
}

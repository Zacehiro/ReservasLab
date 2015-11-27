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
public class Laboratorio {
    
    private String nombre;
    private int id;
    private int cant_equipos;
    private boolean videobeam;
    private ArrayList<SistemaOperativo> sos;
    private ArrayList<Software> software;

    /**
     * Creador de Laboratorio.
     * @param nombre nombre del laboratorio.
     * @param id numero de identificacion del laboratorio.
     * @param cant_equipos cantidad de equipos con los que cuenta el laboratorio.
     * @param videobeam si el laboratorio cuenta o no con videobeam.
     */
    public Laboratorio(String nombre, int id, int cant_equipos, boolean videobeam){
        this.nombre = nombre;
        this.id = id;
        this.cant_equipos = cant_equipos;
        this.videobeam = videobeam;
        sos=new ArrayList<SistemaOperativo>();
        software= new ArrayList<Software>();
    }
    
    /**
     * Creador generico del laboratorio.
     */
    public Laboratorio (){
        sos=new ArrayList<SistemaOperativo>();
        software= new ArrayList<Software>();
    }

    /**
     * Consultar el nombre del laboratorio.
     * @return nombre del laboratorio.
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Asignar el nombre al laboratorio.
     * @param nombre nombre que será asignado.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Consultar el id del laboratorio.
     * @return id del laboratorio.
     */
    public int getId() {
        return id;
    }

    /**
     * Asignar numero de identificacion al laboratorio.
     * @param id numero identificacion que se asignara.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Consultar la cantidad de equipos del laboratorio.
     * @return cantidad de equipos del laboratorio.
     */
    public int getCant_equipos() {
        return cant_equipos;
    }

    /**
     * Asignar la cantidad de equipos del laboratorio.
     * @param cant_equipos cantidad de equipos del laboratorio.
     */
    public void setCant_equipos(int cant_equipos) {
        this.cant_equipos = cant_equipos;
    }

    /**
     * Consultar si el laboratorio cuenta o no con videobeam.
     * @return si cuenta o no con videobeam.
     */
    public boolean isVideobeam() {
        return videobeam;
    }

    /**
     * Asignar propiedad de videobeam al laboratorio.
     * @param videobeam propiedad de videobeam al laboratorio.
     */
    public void setVideobeam(boolean videobeam) {
        this.videobeam = videobeam;
    }

    /**
     * Consultar los sistemas operativos del laboratorio.
     * @return lista de los sistemas operativos del laboratorio.
     */
    public ArrayList<SistemaOperativo> getSos() {
        return sos;
    }

    /**
     * Asignar los sistemas operativos al laboratorio.
     * @param sos sistemas operativos que se asignaran.
     */
    public void setSos(ArrayList<SistemaOperativo> sos) {
        this.sos = sos;
    }

    /**
     * Consultar los software existentes en el laboratorio.
     * @return lista de los software existentes en el laboratorio.
     */
    public ArrayList<Software> getSoftware() {
        return software;
    }

    /**
     * Asignar los software instalados en el laboratorio.
     * @param software lista de software instalados en el laboratorio.
     */
    public void setSoftware(ArrayList<Software> software) {
        this.software = software;
    }
}

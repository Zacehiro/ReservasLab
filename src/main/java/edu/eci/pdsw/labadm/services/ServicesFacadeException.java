/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.labadm.services;

/**
 *
 * @author andres
 */
public class ServicesFacadeException extends Exception{
    public static final String PROBLEMA_BASE_DATOS="Ha ocurrido un error al leer en la base de datos";
    public ServicesFacadeException() {
    }

    public ServicesFacadeException(String message) {
        super(message);
    }
    
}

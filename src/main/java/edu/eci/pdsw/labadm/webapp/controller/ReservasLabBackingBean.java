package edu.eci.pdsw.labadm.webapp.controller;

import edu.eci.pdsw.labadm.entities.Laboratorio;
import edu.eci.pdsw.labadm.entities.SistemaOperativo;
import edu.eci.pdsw.labadm.entities.Software;
import edu.eci.pdsw.labadm.entities.Solicitud;
import edu.eci.pdsw.labadm.entities.Usuario;
import edu.eci.pdsw.labadm.services.ServicesFacade;
import edu.eci.pdsw.labadm.services.ServicesFacadeException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Zacehiro
 */
@ManagedBean
public class ReservasLabBackingBean {
    private ArrayList<String> sos = new ArrayList<String>();
    private ArrayList<Solicitud> sol;
    private Solicitud solicitud;
    private List<Laboratorio> labs = new ArrayList<Laboratorio>();
    private String linkDescarga;
    private String linkSoftware;
    private String sistemaoperativo;
    private String nombre;
    private String version;
    private SistemaOperativo so;
    private ServicesFacade sf;
    private final Usuario usr = new Usuario(1, "Edwin", "edwin.ceron@mail", 1);

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
    
    /**
     * Guarda una solicitud y sus componentes (Software, usuario, sistema operativo, ) 
     */
    public void saveSolicitud(){
        sf = ServicesFacade.getInstance("config.properties");
        solicitud = new Solicitud();
        Software soft =new Software(nombre, version);
        sf.saveSoftware(soft);
        solicitud.setFecha_rad(new Date());
        solicitud.setLink_descarga(linkDescarga);
        solicitud.setUsuario(usr);
        solicitud.setLink_licencia(linkSoftware);
        String [] siso = sistemaoperativo.split(" ");
        so=sf.loadSistemaOperativo(siso[0],siso[1]);
        solicitud.setSo(so);
        solicitud.setSoftware(soft);
        solicitud.setSoftware_instalado(false);
        try {
            sf.saveSolicitud(solicitud);
        } catch (ServicesFacadeException ex) {
            Logger.getLogger(ReservasLabBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Laboratorio> getLabs() {
        return labs;
    }

    public void setLabs(SistemaOperativo so) {
        sf = ServicesFacade.getInstance("config.properties");
        try {
            labs = sf.loadLaboratorioPosible(so);
        } catch (ServicesFacadeException ex) {
            Logger.getLogger(ReservasLabBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<String> getSos() {
        sf =ServicesFacade.getInstance("config.properties");
        List<SistemaOperativo> sito;
        sito = sf.getSos();
        for (int i=0; i<sito.size();i++) {
            sos.add(sito.get(i).getNombre()+" "+sito.get(i).getVersion());
        }
        return sos;
    }

    public void setSos(ArrayList<String> sos) {
        this.sos = sos;
    }

    public ArrayList<Solicitud> getSol() {
        sf= ServicesFacade.getInstance("config.properties");
        try {
            sol= (ArrayList<Solicitud>) sf.loadSolicitudResp();
        } catch (ServicesFacadeException ex) {
            Logger.getLogger(ReservasLabBackingBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sol;
    }

    public void setSol(ArrayList<Solicitud> sol) {
        this.sol = sol;
    }

    public String getLinkDescarga() {
        return linkDescarga;
    }

    public void setLinkDescarga(String linkDescarga) {
        this.linkDescarga = linkDescarga;
    }

    public String getLinkSoftware() {
        return linkSoftware;
    }

    public void setLinkSoftware(String linkSoftware) {
        this.linkSoftware = linkSoftware;
    }

    public SistemaOperativo getSo() {
        return so;
    }

    public void setSo(SistemaOperativo so) {
        this.so = so;
    }

    public String getSistemaoperativo() {
        return sistemaoperativo;
    }

    public void setSistemaoperativo(String sistemaoperativo) {
        this.sistemaoperativo = sistemaoperativo;
    }
    
    /**
     * Al cambiar el SistemaOperativo en la UI carga el sistema operativo desde la persistencia
     */
    public void onSoChange() {    
        if(sistemaoperativo!=null){
            sf = ServicesFacade.getInstance("config.properties");
            String [] siso = sistemaoperativo.split(" ");
            so=sf.loadSistemaOperativo(siso[0], siso[1]);
            setLabs(so);
        }
    }
}
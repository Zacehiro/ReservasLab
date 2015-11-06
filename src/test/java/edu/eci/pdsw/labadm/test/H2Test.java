package edu.eci.pdsw.labadm.test;


import edu.eci.pdsw.labadm.entities.SistemaOperativo;
import edu.eci.pdsw.labadm.entities.Solicitud;
import edu.eci.pdsw.labadm.persistence.PersistenceException;
import edu.eci.pdsw.labadm.services.ServicesFacade;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Assert;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;

import org.junit.Test;
/*
*CLASES DE EQUIVALENCIA:
*C1: Todas las solicitudes que tengan respuesta, esta debe ser 'aprobada' o 'negada'.
*C2: Todas las solicitudes aprobadas deben tener la fecha de instalacion.
*C3: Todas las solicitudes negadas deben tener la justificacion.
*C4: Si las solicitudes fueron negadas, no deben tener fecha de instalacion.
*C5: Las solicitudes sin revisar no pueden tener fecha de respuesta.
*/
public class H2Test {
@Before
    public void setUp() {
    }

    @After
    public void clearDB() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:file:./target/db/testdb;MODE=MYSQL", "sa", "");
        Statement stmt = conn.createStatement();
        stmt.execute("delete from SISTEMA_OPERATIVO");
        stmt.execute("delete from SOFTWARE");
        stmt.execute("delete from SOLICITUD");
        stmt.execute("delete from USUARIO");
        stmt.execute("delete from SOLICITUD");
        stmt.execute("delete from LABORATORIO");
        conn.commit();
        conn.close();
    }
    @Test
    public void c1Test() {
        ServicesFacade sf = ServicesFacade.getInstance("h2-applicationconfig.properties");
        List<Solicitud> solicitudesRespondidas;
        
    try {
        solicitudesRespondidas = sf.loadSolicitudResp();
        
        for (Solicitud s : solicitudesRespondidas) {
            assertTrue(s.getEstado().equals("aprobada")||s.getEstado().equals("negada"));
        }
    } catch (PersistenceException ex) {
        Logger.getLogger(H2Test.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    
    @Test
    public void c2Test() {
        ServicesFacade sf = ServicesFacade.getInstance("h2-applicationconfig.properties");
        List<Solicitud> solicitudesRespondidas;
        
    try {
        solicitudesRespondidas = sf.loadSolicitudResp();
        for (Solicitud s : solicitudesRespondidas) {
            if(s.getEstado().equals("aprobada")){
                assertTrue(s.getFecha_posible()!=null);
            }
        }
    } catch (PersistenceException ex) {
        Logger.getLogger(H2Test.class.getName()).log(Level.SEVERE, null, ex);
    }
        
    }
    
    @Test
    public void c3Test() {
        ServicesFacade sf = ServicesFacade.getInstance("h2-applicationconfig.properties");
        List<Solicitud> solicitudesRespondidas;
    try {
        solicitudesRespondidas = sf.loadSolicitudResp();
        for (Solicitud s : solicitudesRespondidas) {
            if(s.getEstado().equals("negada")){
                assertTrue(s.getJustificacion()!=null);
            }
        }
    } catch (PersistenceException ex) {
        Logger.getLogger(H2Test.class.getName()).log(Level.SEVERE, null, ex);
    }
        
    }
    
    @Test
    public void c4Test() {
        ServicesFacade sf = ServicesFacade.getInstance("h2-applicationconfig.properties");
        List<Solicitud> solicitudesRespondidas;
    try {
        solicitudesRespondidas = sf.loadSolicitudResp();
        for (Solicitud s : solicitudesRespondidas) {
            if(s.getEstado().equals("negada")){
                assertTrue(s.getFecha_posible()==null);
            }
        }
    } catch (PersistenceException ex) {
        Logger.getLogger(H2Test.class.getName()).log(Level.SEVERE, null, ex);
    }
        
    }
    
    @Test
    public void c5Test() {
        ServicesFacade sf = ServicesFacade.getInstance("h2-applicationconfig.properties");
        List<Solicitud> solicitudesSinRespuesta=sf.loadSolicitudSinResp();
        for (Solicitud s : solicitudesSinRespuesta) {
            assertTrue(s.getFecha_resp()==null);
        }
    }
} 


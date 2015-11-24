package edu.eci.pdsw.labadm.test;


import edu.eci.pdsw.labadm.entities.SistemaOperativo;
import edu.eci.pdsw.labadm.entities.Solicitud;
import edu.eci.pdsw.labadm.persistence.PersistenceException;
import edu.eci.pdsw.labadm.services.ServicesFacade;
import edu.eci.pdsw.labadm.services.ServicesFacadeException;
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
        stmt.execute("delete from SOLICITUD");
        stmt.execute("delete from SOFTWARE_LABORATORIO");
        stmt.execute("delete from SOFTWARE");
        stmt.execute("delete from USUARIO");
        stmt.execute("delete from LABORATORIO_SISTEMA_OPERATIVO");
        stmt.execute("delete from SISTEMA_OPERATIVO");
        stmt.execute("delete from LABORATORIO");
        conn.commit();
        conn.close();
    }
    
    @Before
    public void insertData() throws SQLException{
        Connection conn = DriverManager.getConnection("jdbc:h2:file:./target/db/testdb;MODE=MYSQL", "sa", "");
        Statement stmt = conn.createStatement();
        stmt.execute("INSERT INTO LABORATORIO (ID_laboratorio, nombre, cantidad_equipos, videobeam) VALUES (1, 'Redes', 8, 1)");
        stmt.execute("INSERT INTO LABORATORIO (ID_laboratorio, nombre, cantidad_equipos, videobeam) VALUES (2, 'Plataformas', 12, 1)");
        stmt.execute("INSERT INTO LABORATORIO (ID_laboratorio, nombre, cantidad_equipos, videobeam) VALUES (3, 'Quimica', 13, 1)");
        stmt.execute("INSERT INTO LABORATORIO (ID_laboratorio, nombre, cantidad_equipos, videobeam) VALUES (4, 'B0', 20, 0)");
        stmt.execute("INSERT INTO SISTEMA_OPERATIVO (ID_sistema_operativo, nombre, version) VALUES (1, 'Windows', '8.1')");
        stmt.execute("INSERT INTO SISTEMA_OPERATIVO (ID_sistema_operativo, nombre, version) VALUES (2, 'Windows', '10')");
        stmt.execute("INSERT INTO SISTEMA_OPERATIVO (ID_sistema_operativo, nombre, version) VALUES (3, 'Ubuntu', '14.4')");
        stmt.execute("INSERT INTO SISTEMA_OPERATIVO (ID_sistema_operativo, nombre, version) VALUES (4, 'Android', '1.2')");
        stmt.execute("INSERT INTO LABORATORIO_SISTEMA_OPERATIVO VALUES (1, 1)");
        stmt.execute("INSERT INTO LABORATORIO_SISTEMA_OPERATIVO VALUES (2, 1)");
        stmt.execute("INSERT INTO LABORATORIO_SISTEMA_OPERATIVO VALUES (1, 2)");
        stmt.execute("INSERT INTO LABORATORIO_SISTEMA_OPERATIVO VALUES (2, 3)");        
        stmt.execute("INSERT INTO USUARIO VALUES (1, 'Pipe', 'Pipe@hotmail.com', 2)");
        stmt.execute("INSERT INTO USUARIO VALUES (2, 'Andrés', 'andres@hotmail.com', 1)");
        stmt.execute("INSERT INTO USUARIO VALUES (3, 'Felipe', 'felipe@adas.com', 1)");
        stmt.execute("INSERT INTO USUARIO VALUES (4, 'juan', 'juan@sdfsdm.do', 3)");
        stmt.execute("INSERT INTO SOFTWARE VALUES (1, 'Mathematica', '2.00')");
        stmt.execute("INSERT INTO SOFTWARE VALUES (2, 'TeamViewer', '11')");
        stmt.execute("INSERT INTO SOFTWARE VALUES (3, 'Spotify', '6')");
        stmt.execute("INSERT INTO SOFTWARE VALUES (4, 'Netbeans', '8')");
        stmt.execute("INSERT INTO SOFTWARE_LABORATORIO VALUES (2, 1)");
        stmt.execute("INSERT INTO SOFTWARE_LABORATORIO VALUES (3, 1)");
        stmt.execute("INSERT INTO SOFTWARE_LABORATORIO VALUES (3, 2)");
        stmt.execute("INSERT INTO SOFTWARE_LABORATORIO VALUES (3, 4)");
        stmt.execute("INSERT INTO SOLICITUD (ID_solicitud, ID_software, Link_licencia, Link_descarga, Estado, Fecha_radicacion, Fecha_posible_instalacion, Fecha_respuesta, Justificacion, Usuario_id, ID_sistema_operativo, Software_instalado) VALUES (1, 1, 'www.lic.com', 'www.des.com', null, '2015-11-12', null, null, null, 1, 2, 0)");
        stmt.execute("INSERT INTO SOLICITUD (ID_solicitud, ID_software, Link_licencia, Link_descarga, Estado, Fecha_radicacion, Fecha_posible_instalacion, Fecha_respuesta, Justificacion, Usuario_id, ID_sistema_operativo, Software_instalado) VALUES (2, 2, 'www.lic.com', 'www.des.com', null, '2015-11-12', null, null, null, 3, 1, 0)");
        stmt.execute("INSERT INTO SOLICITUD (ID_solicitud, ID_software, Link_licencia, Link_descarga, Estado, Fecha_radicacion, Fecha_posible_instalacion, Fecha_respuesta, Justificacion, Usuario_id, ID_sistema_operativo, Software_instalado) VALUES (3, 3, 'www.lic.com', 'www.des.com', 'aprobada', '2015-11-12', '2015-11-24', null, null, 2, 1, 0)");
        stmt.execute("INSERT INTO SOLICITUD (ID_solicitud, ID_software, Link_licencia, Link_descarga, Estado, Fecha_radicacion, Fecha_posible_instalacion, Fecha_respuesta, Justificacion, Usuario_id, ID_sistema_operativo, Software_instalado) VALUES (4, 4, 'www.lic.com', 'www.des.com', 'negada', '2015-11-12', null, null, 'Imposible', 4, 3, 0)");
        conn.commit();
        conn.close();
    }
    
    @Test
    public void c1Test() throws ServicesFacadeException {
        ServicesFacade sf = ServicesFacade.getInstance("h2-applicationconfig.properties");
        List<Solicitud> solicitudesRespondidas;
        solicitudesRespondidas = sf.loadSolicitudResp();
        for (Solicitud s : solicitudesRespondidas) {
            assertTrue("Las Solicitudes Respondidas Tienen Un Estado", s.getEstado().equals("aprobada")||s.getEstado().equals("negada"));
        }
    }
    
    @Test
    public void c2Test() throws ServicesFacadeException {
        ServicesFacade sf = ServicesFacade.getInstance("h2-applicationconfig.properties");
        List<Solicitud> solicitudesRespondidas;
        solicitudesRespondidas = sf.loadSolicitudResp();
        for (Solicitud s : solicitudesRespondidas) {
            if(s.getEstado().equals("aprobada")){
                assertTrue("Si se agrego una fecha de posible instalación", s.getFecha_posible()!=null);
            }
        }
    }
    
    @Test
    public void c3Test() throws ServicesFacadeException {
        ServicesFacade sf = ServicesFacade.getInstance("h2-applicationconfig.properties");
        List<Solicitud> solicitudesRespondidas;
        solicitudesRespondidas = sf.loadSolicitudResp();
        for (Solicitud s : solicitudesRespondidas) {
            if(s.getEstado().equals("negada")){
                assertTrue("Si se agrego una justificacion por negar la solicitud", s.getJustificacion()!=null);
            }
        }   
    }
    
    @Test
    public void c4Test() throws ServicesFacadeException {
        ServicesFacade sf = ServicesFacade.getInstance("h2-applicationconfig.properties");
        List<Solicitud> solicitudesRespondidas;
        solicitudesRespondidas = sf.loadSolicitudResp();
        for (Solicitud s : solicitudesRespondidas) {
            if(s.getEstado().equals("negada")){
                assertTrue("No se agrego fecha de instalacion por negar la solicitud", s.getFecha_posible()==null);
            }
        }  
    }
    
    @Test
    public void c5Test() throws ServicesFacadeException {
        ServicesFacade sf = ServicesFacade.getInstance("h2-applicationconfig.properties");
        List<Solicitud> solicitudesSinRespuesta;
        solicitudesSinRespuesta = sf.loadSolicitudSinResp();
        for (Solicitud s : solicitudesSinRespuesta) {
            assertTrue("Las solicitudes sin respuesta no tienen fecha de respuesta", s.getFecha_resp()==null);
        }
    }
} 


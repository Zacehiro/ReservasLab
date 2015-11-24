package edu.eci.pdsw.labadm.test;

import edu.eci.pdsw.labadm.entities.Laboratorio;
import edu.eci.pdsw.labadm.entities.SistemaOperativo;
import edu.eci.pdsw.labadm.entities.Software;
import edu.eci.pdsw.labadm.entities.Solicitud;
import edu.eci.pdsw.labadm.services.ServicesFacade;
import edu.eci.pdsw.labadm.services.ServicesFacadeException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import org.junit.Test;

/**
 * CLASES DE EQUIVALENCIA:
 * Todas las solicitudes realicidas deben tener como respuesta null.
 * Todas las solicitudes deben contener el email y las dos paginas web con un formato correcto.
 * Las solicitudes deben hacerse a un laboratorio con el sistema operativo indicado.
 * Todas las solicitudes deben tener fecha de radicación.
 **/

public class AppTest {

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
  
  @Test
  public void emailOPaginaIncorrectosTest() {
      
      Connection conn;
    try {
        conn = DriverManager.getConnection("jdbc:h2:file:./target/db/testdb;MODE=MYSQL", "sa", "");
        Statement stmt = conn.createStatement();
        stmt.execute("INSERT INTO LABORATORIO (ID_laboratorio, nombre, cantidad_equipos, videobeam) values(1,'Ingenieria De Software',20, true)");
        stmt.execute("INSERT INTO SISTEMA_OPERATIVO (ID_sistema_operativo, nombre, version, Solicitud_id) values(1,'Windows','8.1', 1)");
        conn.commit();
    } catch (SQLException ex) {
        Logger.getLogger(AppTest.class.getName()).log(Level.SEVERE, null, ex);
    }
      
      ServicesFacade sf = ServicesFacade.getInstance("h2-applicationconfig.properties");
      boolean posible=true;
      Solicitud s = new Solicitud();
      Software sof=new Software("Wolfram", "10",1);
      s.setSoftware(sof);
      s.setLink_descarga("http://dev-c.softonic.com/");
      s.setLink_licencia("licencia");
      SistemaOperativo so = new SistemaOperativo("Windows", "8.1",7);
      s.setSo(so);
      try{
        sf.saveSolicitud(s);
      }catch(ServicesFacadeException bs){
         if(bs.getMessage().equals(ServicesFacadeException.WRONG_LINK_TYPED)){
            posible =false;
         }
       }
      Assert.assertFalse("El mail o la pagina no son correctos",posible);
      
  }
  
  @Test
  public void autoGeneraFechaRadicacionTest() throws ServicesFacadeException{
       Connection conn;
    try {
        conn = DriverManager.getConnection("jdbc:h2:file:./target/db/testdb;MODE=MYSQL", "sa", "");
        Statement stmt = conn.createStatement();
        stmt.execute("INSERT INTO LABORATORIO (ID_laboratorio, nombre, cantidad_equipos, videobeam) values(1,'Ingenieria De Software',20, true)");
        stmt.execute("INSERT INTO USUARIO (ID_usuario, nombre, email, tipo_usuario) values (1,'camilo', 'camilo@hotmail.com', 1)");
        stmt.execute("INSERT INTO SOFTWARE(ID_software, nombre, version) VALUES (1,'openmaint','15')");
        stmt.execute("INSERT INTO SISTEMA_OPERATIVO (ID_sistema_operativo, nombre, version) values(1,'Windows','8.1')");
        stmt.execute("INSERT INTO SOLICITUD (ID_solicitud,ID_software, Link_licencia, Link_descarga, Estado, Fecha_respuesta, Fecha_posible_instalacion, Justificacion, Usuario_id, ID_sistema_operativo, Software_instalado) values (1,1,'http//:www.openmaint.com','http//:www.openmaint.com/descargas',null,null,null,null,1,1, 0)");
        conn.commit();
    } catch (SQLException ex) {
        Logger.getLogger(AppTest.class.getName()).log(Level.SEVERE, null, ex);
    }
        ServicesFacade sf = ServicesFacade.getInstance("h2-applicationconfig.properties");
        Date d = new Date();
        boolean fine= true;
        List<Solicitud> solicitudes = sf.loadAllSolicitud();
        System.out.println("asdasd "+solicitudes);
        for (Solicitud s : solicitudes) {
          if(!(s.getFecha_rad().getDay()==d.getDay() && s.getFecha_rad().getMonth()==d.getMonth() && s.getFecha_rad().getYear() == d.getYear()) && fine){
              fine= false;
          }
      }
        Assert.assertTrue("No se autogenera la fecha de radicacion",fine);
        
  }
  
  @Test
  public void laboratoriosConSistemaOperativoTest(){
      Connection conn;
      boolean fine = true;
        try {
            conn = DriverManager.getConnection("jdbc:h2:file:./target/db/testdb;MODE=MYSQL", "sa", "");
            Statement stmt = conn.createStatement();
            stmt.execute("INSERT INTO LABORATORIO (ID_laboratorio, nombre, cantidad_equipos, videobeam) values(1,'Ingenieria De Software',20, true)");
            stmt.execute("INSERT INTO USUARIO (ID_usuario, nombre, email, tipo_usuario) values(1,'Tatiana','tatiana@mail.com', 1)");
            stmt.execute("INSERT INTO SOFTWARE(ID_software, nombre, version) VALUES (1,'opas','15')");
            stmt.execute("INSERT INTO SISTEMA_OPERATIVO (ID_sistema_operativo, nombre, version) values(1,'Windows','8.1')");
            stmt.execute("INSERT INTO `LABORATORIO_SISTEMA_OPERATIVO`(`LABORATORIO_ID_laboratorio`, `SISTEMA_OPERATIVO_ID_sistema_operativo`) VALUES (1,1)");
            stmt.execute("INSERT INTO SOLICITUD (ID_solicitud, Software, Link_licencia, Link_descarga, Estado, Fecha_posible_instalacion, Fecha_respuesta, Justificacion, Usuario_id, ID_sistema_operativo, Software_instalado)"
                    + " values(1,'Dulces', 'http//:www.Dulces.co', 'http//:www.Dulces.co/download', null, null, null, null, 1,1,0)");
      
            conn.commit();
            ServicesFacade sf = ServicesFacade.getInstance("h2-applicationconfig.properties");
          try {
              SistemaOperativo sof = sf.loadSistemaOperativo("Windows","8.1");
              ArrayList<Laboratorio> labs = new ArrayList<Laboratorio>();
              labs = (ArrayList<Laboratorio>) sf.loadLaboratorioPosible(sof);
              for (Laboratorio lab : labs) {
                  if(!(lab.getNombre().equalsIgnoreCase("Windows"))){
                      fine=false;
                  }
              }
          } catch (ServicesFacadeException ex) {
              Logger.getLogger(AppTest.class.getName()).log(Level.SEVERE, null, ex);
          }
       } catch (SQLException ex) {
           fine=false;
        }
      
      Assert.assertFalse("La solicitud no posee un laboratorio con el sistema operativo",fine);
  }
  @Test
  public void solicitudesSinRespuestaTest() throws ServicesFacadeException{
      Connection conn;
    try {
      conn = DriverManager.getConnection("jdbc:h2:file:./target/db/testdb;MODE=MYSQL", "sa", "");
      Statement stmt = conn.createStatement();
      stmt.execute("INSERT INTO LABORATORIO (ID_laboratorio, nombre, cantidad_equipos, videobeam) values(1,'Ingenieria De Software',20, true)");
      stmt.execute("INSERT INTO USUARIO (ID_usuario, nombre, email, tipo_usuario) values(1,'Tatiana','tatiana@mail.com', 1)");      
      stmt.execute("INSERT INTO SISTEMA_OPERATIVO (ID_sistema_operativo, nombre, version, Solicitud_id) values(1,'Windows','8.1', 1)");
      stmt.execute("INSERT INTO SOFTWARE (ID_software, nombre, version) values(1,'glass','1')");
      stmt.execute("INSERT INTO SOLICITUD (ID_solicitud, ID_software, Link_licencia, Link_descarga, Estado, Fecha_posible_instalacion, Fecha_respuesta, Justificacion, Usuario_id, ID_sistema_operativo, Software_instalado)"
              + " values(1,1, 'http//:www.Dulces.co', 'http//:www.Dulces.co/download', null, null, null, null, 1,1,0)");
      
      
      conn.commit();
      } catch (SQLException ex) {
        Logger.getLogger(AppTest.class.getName()).log(Level.SEVERE, null, ex);
    }
     
      ServicesFacade sf = ServicesFacade.getInstance("h2-applicationconfig.properties");
      List<Solicitud> s =sf.loadAllSolicitud();
      boolean fine = true;
      for (Solicitud s1 : s) {
              if(!(s1.getJustificacion() == null) && fine){
                  fine = false;
              }
          }
      Assert.assertTrue("Las solicitudes recien insertadas poseen respuesta",fine);
  }
} 
    

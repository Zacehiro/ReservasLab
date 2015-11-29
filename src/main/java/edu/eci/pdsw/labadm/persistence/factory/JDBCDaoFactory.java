/*
 * Copyright (C) 2015 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.pdsw.labadm.persistence.factory;

import edu.eci.pdsw.labadm.persistence.DaoFactory;
import edu.eci.pdsw.labadm.persistence.DaoUsuario;
import edu.eci.pdsw.labadm.persistence.DaoLaboratorio;
import edu.eci.pdsw.labadm.persistence.DaoSistemaOperativo;
import edu.eci.pdsw.labadm.persistence.DaoSoftware;
import edu.eci.pdsw.labadm.persistence.DaoSolicitud;
import edu.eci.pdsw.labadm.persistence.PersistenceException;
import edu.eci.pdsw.labadm.persistence.jdbc.JDBCDaoLaboratorio;
import edu.eci.pdsw.labadm.persistence.jdbc.JDBCDaoSoftware;
import edu.eci.pdsw.labadm.persistence.jdbc.JDBCDaoSolicitud;
import edu.eci.pdsw.labadm.persistence.jdbc.JDBCDaoUsuario;
import edu.eci.pdsw.labadm.persistence.jdbc.JDBCDaosistemaoperativo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Zacehiro
 */
public class JDBCDaoFactory extends DaoFactory {

    private static final ThreadLocal<Connection> connectionInstance = new ThreadLocal<Connection>() {
    };

    private static Properties appProperties = null;

    public JDBCDaoFactory(Properties appProperties) {
        this.appProperties = appProperties;
    }

    private static Connection openConnection() throws PersistenceException {
        String url = appProperties.getProperty("url");
        String driver = appProperties.getProperty("driver");
        String user = appProperties.getProperty("user");
        String pwd = appProperties.getProperty("pwd");

        try {
            Class.forName(driver);
            Connection _con = DriverManager.getConnection(url, user, pwd);
            _con.setAutoCommit(false);
            return _con;
        } catch (ClassNotFoundException | SQLException ex) {
            throw new PersistenceException("Error on connection opening.", ex);
        }

    }

    @Override
    public void beginSession() throws PersistenceException {
        connectionInstance.set(openConnection());

    }

    @Override
    public void endSession() throws PersistenceException {
        try {
            if (connectionInstance.get() == null || connectionInstance.get().isClosed()) {
                throw new PersistenceException("Conection is null or is already closed.");
            } else {
                connectionInstance.get().close();
            }
        } catch (SQLException ex) {
            throw new PersistenceException("Error on connection closing.", ex);
        }
    }

    @Override
    public void commitTransaction() throws PersistenceException {
        try {
            if (connectionInstance.get() == null || connectionInstance.get().isClosed()) {
                throw new PersistenceException("Conection is null or is already closed.");
            } else {
                connectionInstance.get().commit();
            }
        } catch (SQLException ex) {
            throw new PersistenceException("Error on connection closing.", ex);
        }
    }

    @Override
    public void rollbackTransaction() throws PersistenceException {
        try {
            if (connectionInstance.get() == null || connectionInstance.get().isClosed()) {
                throw new PersistenceException("Conection is null or is already closed.");
            } else {
                connectionInstance.get().rollback();
            }
        } catch (SQLException ex) {
            throw new PersistenceException("Error on connection closing.", ex);
        }
    }

    @Override
    public DaoLaboratorio getDaoLaboratorio() throws PersistenceException {
        return new JDBCDaoLaboratorio(connectionInstance.get());
    }

    @Override
    public DaoUsuario getDaoUsuario() throws PersistenceException {
        return new JDBCDaoUsuario(connectionInstance.get());
    }

    @Override
    public DaoSolicitud getDaoSolicitud() throws PersistenceException {
        return new JDBCDaoSolicitud(connectionInstance.get());
    }

    @Override
    public DaoSistemaOperativo getDaoSistemaOperativo() throws PersistenceException {
        return new JDBCDaosistemaoperativo(connectionInstance.get());
    }

    @Override
    public DaoSoftware getDaoSoftware() throws PersistenceException {
        return new JDBCDaoSoftware(connectionInstance.get());
    }

}
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
package edu.eci.pdsw.labadm.persistence;

import edu.eci.pdsw.labadm.persistence.factory.JDBCDaoFactory;
import java.util.Properties;
/**
 *
 * @author Zacehiro
 */
public abstract class DaoFactory {
    
    protected DaoFactory(){}
    private static DaoFactory instance=null;
    
    public static DaoFactory getInstance(Properties appProperties) {
        if (instance == null) {
            synchronized (DaoFactory.class) {
                if (instance == null) {
                    if (appProperties.get("persistence").equals("jdbc")) {
                        instance = new JDBCDaoFactory(appProperties);
                    } else {
                        throw new RuntimeException("Wrong configuration: Unsupported DAO:" + appProperties.get("dao"));
                    }
                }
            }
        }
        return instance;
    }
    
    public abstract void beginSession() throws PersistenceException;
    
    public abstract DaoLaboratorio getDaoLaboratorio() throws PersistenceException;
    
    public abstract DaoSistemaOperativo getDaoSistemaOperativo() throws PersistenceException;
    
    public abstract DaoUsuario getDaoUsuario() throws PersistenceException;
    
    public abstract DaoSoftware getDaoSoftware() throws PersistenceException;
    
    public abstract DaoSolicitud getDaoSolicitud() throws PersistenceException;
    
    public abstract void commitTransaction() throws PersistenceException;
    
    public abstract void rollbackTransaction() throws PersistenceException;
    
    public abstract void endSession() throws PersistenceException;
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.labadm.persistence.factory;

import edu.eci.pdsw.labadm.persistence.DaoFactory;
import edu.eci.pdsw.labadm.persistence.DaoUsuario;
import edu.eci.pdsw.labadm.persistence.DaoLaboratorio;
import edu.eci.pdsw.labadm.persistence.DaoSistemaOperativo;
import edu.eci.pdsw.labadm.persistence.DaoSoftware;
import edu.eci.pdsw.labadm.persistence.DaoSolicitud;
import edu.eci.pdsw.labadm.persistence.PersistenceException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 *
 * @author Zacehiro
 */
public class MyBatisMapperFactory extends DaoFactory {
    private static SqlSession sqlss;
    
    public static SqlSessionFactory getSqlSessionFactory() {
        SqlSessionFactory sqlSessionFactory = null;
        if (sqlSessionFactory == null) {
            InputStream inputStream;
            try {
                inputStream = Resources.getResourceAsStream("mybatis-config.xml");
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e.getCause());
            }
        }
        return sqlSessionFactory;
    }
    
    @Override
    public void beginSession() throws PersistenceException {
        SqlSessionFactory sessionfact = getSqlSessionFactory();
        sqlss = sessionfact.openSession();
    }

    @Override
    public void endSession() throws PersistenceException {
        sqlss.close();
    }

    @Override
    public void commitTransaction() throws PersistenceException {
        sqlss.commit();     
    }

    @Override
    public void rollbackTransaction() throws PersistenceException {
        sqlss.rollback();
    }
   
    @Override
    public DaoLaboratorio getDaoLaboratorio() {
        //ProductoMapper prodm=sqlss.getMapper(ProductoMapper.class);
        return null;//new MybatisProducto(prodm);
    }

    @Override
    public DaoUsuario getDaoUsuario() {
        //PedidoMapper pm=sqlss.getMapper(PedidoMapper.class);
        return null;//new MybatisPedido(pm);
    }

    @Override
    public DaoSolicitud getDaoSolicitud() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DaoSistemaOperativo getDaoSistemaOperativo() throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DaoSoftware getDaoSoftware() throws PersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}



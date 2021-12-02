package com.wz.dao;

import com.wz.utils.JDBCUtilsByDruid;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BasicDAO<T> {
    //泛型指定具体类型
    private QueryRunner qr = new QueryRunner();
    public int update(String sql, Object... parameters) {
        Connection connection = null;
        try {
            connection = JDBCUtilsByDruid.getConn();
            int update = qr.update(connection, sql, parameters);
             return update;
        } catch (SQLException e) {
            throw new RuntimeException(e); //将编译异常->运行异常 ,抛出
        } finally {
            JDBCUtilsByDruid.clouse(null, null, connection);
        }

    }

//返回多个对象(即查询的结果是多行), 针对任意表
    /**

     *

     * @param sql sql 语句，可以有 ?

     * @param clazz 传入一个类的 Class 对象 比如 Actor.class

     * @param parameters 传入 ? 的具体的值，可以是多个

     * @return 根据 Actor.class 返回对应的 ArrayList 集合

     */
    public List<T> queryMulti(String sql, Class<T> clazz, Object... parameters) {

        Connection connection = null;

        try {

            connection = JDBCUtilsByDruid.getConn();

            return qr.query(connection, sql, new BeanListHandler<T>(clazz), parameters);

        } catch (SQLException e) {

            throw new RuntimeException(e); //将编译异常->运行异常 ,抛出

        } finally {

            JDBCUtilsByDruid.clouse(null, null, connection);

        }

    }

//查询单行结果 的通用方法

    public T queryOne(String sql, Class<T> clazz, Object... parameters) {

        Connection connection = null;

        try {

            connection = JDBCUtilsByDruid.getConn();

            return qr.query(connection, sql, new BeanHandler<T>(clazz), parameters);

        } catch (SQLException e) {

            throw new RuntimeException(e); //将编译异常->运行异常 ,抛出

        } finally {

            JDBCUtilsByDruid.clouse(null, null, connection);

        }

    }

//查询单行单列的方法,即返回单值的方法

    public Object queryScalar(String sql, Object... parameters) {

        Connection connection = null;
      try {

            connection = JDBCUtilsByDruid.getConn();
            return qr.query(connection, sql, new ScalarHandler(), parameters);

      } catch (SQLException e) {
           throw new RuntimeException(e); //将编译异常->运行异常 ,抛出
       } 
      finally {
            JDBCUtilsByDruid.clouse(null, null, connection); 
      }

    }

}
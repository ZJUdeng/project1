package Day1;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import java.sql.Connection;

/**
 * 演示DBUtils
 * 封装了和数据库存取相关的一些方法
 * 通用的增删改查
 *  QueryRunner类：
 *      update(connection,sql,params):执行任何增删改查语句
 *      query(connection,sql,ResultSetHandler,params):执行任何查询语句
 *  ResultSetHandler接口：
 *      BeanHandler:将结果集的第一行，封装成对象，并返回   new BeanHandler<>(XX.class)
 *      BeanListHandler:将结果集中所有的行，封装成对象的集合，并返回   new BeanListHandler<>(XX.class)
 *      ScalarHandler:将结果集中的第一行第一列，以Object形式返回，    new ScalarHandler()
 * 使用步骤：
 * 导入jar包commons-dbutils-1.3.jar
 * 看帮助
 * 使用
 *
 */
//修改表
public class TestDBUtils {
    @Test
    public void TestUpdate() throws Exception {
        //1.获取连接
        Connection connection = JDBCUtilsByDruid.getConnection();
        //2.执行增删改
        QueryRunner qr = new QueryRunner();
        int update = qr.update(connection, "update boys set boyname = ? where id = 4", "邓世伟");
        System.out.println(update>0?"success":"failure");
        //3.关闭连接
        JDBCUtilsByDruid.close(null,null,connection);
    }
    //查询多个值
    @Test
    public void TestQuery() throws Exception {
        //1.获取连接
        Connection connection = JDBCUtilsByDruid.getConnection();
        //2.执行增删改
        QueryRunner qr = new QueryRunner();
        //首先要创建表名对应的.java
        Admin admin = qr.query(connection, "select * from  admin where id=?", new BeanHandler<>(Admin.class), 3);
        System.out.println(admin);
        //3.关闭连接
        JDBCUtilsByDruid.close(null,null,connection);
    }
    //查询单个值
    @Test
    public void TestScalar() throws Exception {
        //1.获取连接
        Connection connection = JDBCUtilsByDruid.getConnection();
        //2.执行增删改
        QueryRunner qr = new QueryRunner();
        //无需创建对象
        Object query = qr.query(connection, "select count(*) from beauty", new ScalarHandler());
        System.out.println(query);
        //3.关闭连接
        JDBCUtilsByDruid.close(null,null,connection);
    }
}

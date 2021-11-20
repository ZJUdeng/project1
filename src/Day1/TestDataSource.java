package Day1;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 演示德鲁伊数据库连接池的使用
 */
public class TestDataSource {
    @Test
    public void TestDataSource() throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src\\druid.properties"));
        //1.创建了一个指定参数的连接池
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        //2.从数据库连接池中获取可用的连接对象
        Connection connection = dataSource.getConnection();
        System.out.println("连接成功");
        //3.关闭连接
        connection.close();
    }
    //使用C3P0数据库连接池
    @Test
    public void TestC3P0DataResource() throws PropertyVetoException, SQLException {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass("com.mysql.jdbc.Driver");
        cpds.setJdbcUrl("jdbc:mysql://localhost:3306/girls");
        cpds.setUser("root");
        cpds.setPassword("123456");
        cpds.setInitialPoolSize(10);
        cpds.setMaxPoolSize(50);

        Connection connection = cpds.getConnection();
        System.out.println("连接成功");
        connection.close();

    }
}

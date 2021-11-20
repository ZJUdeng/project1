package Day1;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 通过德鲁伊数据库连接池获取连接对象
 */
public class JDBCUtilsByDruid {
    static DataSource dataSource;
    static {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src\\druid.properties"));
            //1.创建了一个指定参数的连接池
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static Connection getConnection() throws Exception {
        //2.从数据库连接池中获取可用的连接对象
        return dataSource.getConnection();
    }
    //释放资源
    public static void close(ResultSet set, Statement statement, Connection connection) {
        try {
            if(set!= null){
                set.close();
            }
            if(statement != null){
                statement.close();
            }
            if(connection != null){
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


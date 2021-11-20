package Day1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * 此类是封装JDBC的工具类
 * 功能：
 * 1.获取连接
 * 2.释放资源
 */
public class JDBCUtils {
    static String user;
    static String password;
    static String driver;
    static String url;
    static{
        try {
            Properties info = new Properties();
            info.load(new FileInputStream("src\\jdbc.properties"));
            user = info.getProperty("user");
            password = info.getProperty("password");
            driver = info.getProperty("driver");
            url = info.getProperty("url");
            //1.注册驱动
            Class.forName(driver);
        } catch (Exception e) {
            throw new RuntimeException(e);//编译异常转换成运行异常
        }
    }
    //获取连接
    public static Connection getConnection()  {

        //2.获取连接
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //释放资源
    public static void close(ResultSet set, Statement statement,Connection connection) {
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

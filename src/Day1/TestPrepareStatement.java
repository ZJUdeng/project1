package Day1;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

/**
 * 演示PreparedStatement和Statement区别
 * 案例：登录验证
 * PreparedStatement继承了Statement
 * 使用PreparedStatement好处：
 * 1.不再使用 + 来拼接sql语句，减少了语法错误，语义性强
 * 2.将模板sql（固定的部分）和参数的部分进行了分离，提高维护性
 * 3.有效解决了sql注入问题
 * 4.提高效率
 */
public class TestPrepareStatement {
   //使用Statement实现登录
   @Test
   public void testStatement() throws IOException, ClassNotFoundException, SQLException {
      Scanner input  = new Scanner(System.in);
      System.out.println("Please input username");
       String username = input.next();
       System.out.println("Please input password");
       String psd = input.next();
       //-------------连接数据库--------------
       Properties info = new Properties();
       info.load(new FileInputStream("src\\jdbc.properties"));
       String user = info.getProperty("user");
       String password = info.getProperty("password");
       String driver = info.getProperty("driver");
       String url = info.getProperty("url");
       //1.注册驱动
       Class.forName(driver);
       //2.获取连接
       Connection connection = DriverManager.getConnection(url, user, password);
       //3.执行查询
       String sql = "SELECT COUNT(*) FROM admin WHERE username = '"+username+"' AND PASSWORD = '"+psd+"'" ;//用＋号拼接对象，易出错
       Statement statement = connection.createStatement();
       ResultSet set = statement.executeQuery(sql);
       if(set.next()){
           int count = set.getInt(1);
           System.out.println(count > 0?"success":"failure");
       }
       //4.关闭
       set.close();
       statement.close();
       connection.close();
   }
    //使用PreparedStatement实现登录
    @Test
    public void testPreparedStatement() throws IOException, ClassNotFoundException, SQLException {
        Scanner input  = new Scanner(System.in);
        System.out.println("Please input username");
        String username = input.next();
        System.out.println("Please input password");
        String psd = input.next();
        //-------------连接数据库--------------
        Properties info = new Properties();
        info.load(new FileInputStream("src\\jdbc.properties"));
        String user = info.getProperty("user");
        String password = info.getProperty("password");
        String driver = info.getProperty("driver");
        String url = info.getProperty("url");
        //1.注册驱动
        Class.forName(driver);
        //2.获取连接
        Connection connection = DriverManager.getConnection(url, user, password);
        //3.执行查询
        //3-1编写sql语句
        String sql = "SELECT COUNT(*) FROM admin WHERE username = ? AND PASSWORD = ? ";
        //3-1获取PreparedStatement命令对象
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        //3-2设置占位符的值
        preparedStatement.setString(1,username);//第一个？处
        preparedStatement.setString(2,psd);//第二个？处
        //3-3执行sql命令
        ResultSet set = preparedStatement.executeQuery();
        if(set.next()){
            int count = set.getInt(1);
            System.out.println(count > 0?"success":"failure");
        }
        //4.关闭
        set.close();
        preparedStatement.close();
        connection.close();
    }

}

package Day1;

import com.mysql.jdbc.Driver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * 演示JDBC的使用步骤1：加载驱动
 * 演示JDBC的使用步骤2:获取连接
 * 类的加载时机：
 * 1.new对象
 * 2.加载子类
 * 3.调用类中的静态成员
 * 4.通过反射
 *
 * 使用new对象的加载方式不足：
 * 1.属于编译期加载，如果编译期间该类不存在，则直接编译报错，依赖性太强
 * 2.导致Driver对象创建两遍，效率较低
 *
 * 反射的方式加载类
 */
public class ConnectionTest2 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        Properties info = new Properties();
        info.load(new FileInputStream("JDBCstudy\\src\\jdbc.properties"));
        info.list(System.out);//打印jdbc文件
        String user = info.getProperty("user");
        String password = info.getProperty("password");
        String driver = info.getProperty("driver");
        String url = info.getProperty("url");
        //不推荐
//        DriverManager.registerDriver(new Driver());
        //通过反射加载
        Class.forName(driver);
        //2.获取连接
//        DriverManager.getConnection("jdbc:mysql://localhost:3306/girls","root","123456");
//        DriverManager.getConnection("jdbc:mysql://localhost:3306/girls?user=root&password=123456");
        //使用properties获取连接
        Connection connection = DriverManager.getConnection(url,user,password);
        System.out.println("连接成功");
        //3.执行增删改查
        //查询
        String sql = "select id,name,sex,borndate from beauty";
        //获取执行sql命令的对象
        Statement statement = connection.createStatement();
        //执行sql语句
//        boolean execute = statement.execute();执行任何sql语句
//        statement.executeUpdate(sql)执行增删改语句，返回受影响的行数
        ResultSet set = statement.executeQuery(sql);//executeQuery执行查询语句，返回一个结果集ResultSet
        while(set.next()) {
            int id = set.getInt(1);
            String name = set.getString(2);
            String sex = set.getString(3);
            Date date = set.getDate(4);
            System.out.println(id + "\t" + name + "\t" + sex + "\t" + date);//1	柳岩	女	1988-02-03
        }
            //4.关闭连接
            set.close();
            statement.close();
            connection.close();



    }
}

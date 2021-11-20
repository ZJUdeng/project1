package Day1;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 演示JDBC
 * 步骤：
 * 1.加载驱动
 * 2.获取连接
 * 3.执行增删改查操作（重点）
 * 4.关闭连接
 */
public class ConnectionTest1 {
    public static void main(String[] args) throws SQLException {
        //1.加载驱动
        DriverManager.registerDriver(new Driver());
        //2.获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/girls","root","123456");
        System.out.println("连接成功");
        //3.执行增删改查
        //3-1编写sql语句
        //删除
//        String sql = "delete from beauty where id = 9";
        //修改
//        String sql = "UPDATE beauty set name = 'dsw' where id = 8";
        //增加
        String sql = "insert into beauty values(null,'wsd','男','2000-3-3','110','null',3)";
        //3-2执行sql语句
        Statement statement = connection.createStatement();
        //3-3使用命令对象指向sql语句
        int update = statement.executeUpdate(sql);
        //3-4处理执行结果
        System.out.println(update > 0 ? "success":"failure");
        //4.关闭连接(后用先关)
        statement.close();
        connection.close();

    }
}

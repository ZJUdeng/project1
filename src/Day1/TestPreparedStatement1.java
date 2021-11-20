package Day1;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

/**
 *
 */
public class TestPreparedStatement1 {
    @Test
    public void test1() throws IOException, SQLException, ClassNotFoundException {
        Scanner input = new Scanner(System.in);
        System.out.println("请输入待修改的客户编号：");
        int id = input.nextInt();
        System.out.println("请输入新的客户姓名");
        String name = input.next();
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
        String sql = "update customers set name = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,name);
        preparedStatement.setInt(2,id);
        int update = preparedStatement.executeUpdate();
        System.out.println(update > 0?"修改成功":"修改失败");
        //4.关闭
        preparedStatement.close();
        connection.close();
    }
}

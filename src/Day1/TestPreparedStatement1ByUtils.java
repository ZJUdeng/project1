package Day1;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

/**
 *
 */
public class TestPreparedStatement1ByUtils {
    @Test
    public void test1() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("请输入待修改的客户编号：");
        int id = input.nextInt();
        System.out.println("请输入新的客户姓名");
        String name = input.next();
        //-------------连接数据库--------------
        //1.获取连接
        Connection connection = JDBCUtils.getConnection();
        //3.执行查询
        String sql = "update customers set name = ? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,name);
        preparedStatement.setInt(2,id);
        int update = preparedStatement.executeUpdate();
        System.out.println(update > 0?"修改成功":"修改失败");
       //3.关闭
        JDBCUtils.close(null,preparedStatement,connection);
    }
}

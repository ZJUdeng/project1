package Day1;

import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 演示批处理
 * 向admin插入50000条数据
 * 相关API：
 * addBatch
 * executeBatch
 * clearBatch
 *说明；批处理往往和PreparedStatement搭配使用，既减少编译次数，又减少运行次数，效率提高
 */
public class TestBatch {
    //不使用批处理
    @Test
    public void testNoBatch() throws SQLException {
        //1.获取连接
        Connection connection = JDBCUtils.getConnection();
        //2.执行插入
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT into admin values(null,?,?)");
        for (int i = 1; i < 5000; i++) {
            preparedStatement.setString(1,"john"+i);
            preparedStatement.setString(2,"0000");
            preparedStatement.executeUpdate();
        }
        //3.释放资源
        JDBCUtils.close(null,preparedStatement,connection);
    }
    //使用批处理
    @Test
    public void testBatch() throws SQLException {
        //1.获取连接
        Connection connection = JDBCUtils.getConnection();
        //2.执行插入
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT into admin values(null,?,?)");
        for (int i = 1; i <= 50000; i++) {
            preparedStatement.setString(1,"john"+i);
            preparedStatement.setString(2,"0000");
            preparedStatement.addBatch();//添加sql语句到批处理包中
            if(i %1000 == 0){//到极限执行一次
                preparedStatement.executeBatch();//执行批处理包中的sql语句
                preparedStatement.clearBatch();//清空批处理包中的sql语句
            }
        }
        //3.释放资源
        JDBCUtils.close(null,preparedStatement,connection);
    }
}

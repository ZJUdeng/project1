package Day1;

import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 演示JDBC中的事务
 * 使用步骤：
 * 1.开启新事务
 *      取消事务自动提交的功能
 *      setAutoCommit(false)
 * 2.编写组成事务的一组sql语句
 * 3.结束事务
 *      commit();提交
 *      rollback();回滚
 * 细节：
 *  要求开启事务的连接对象和获取命令的连接对象必须是同一个，否则无效
 * 案例：dsw给wsd转5000元
 */
public class TestTransaction {
    //不用事务
    @Test
    public void testNoTransaction() throws SQLException {
        //1.获取连接
        Connection connection = JDBCUtils.getConnection();
        //2.执行sql语句
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE account set balance = ? where stuname = ?");
        //操作1：dsw-5000
        preparedStatement.setDouble(1,5000);
        preparedStatement.setString(2,"dsw");
        preparedStatement.executeUpdate();
        //操作2.wsd+5000
        preparedStatement.setDouble(1,15000);
        preparedStatement.setString(2,"wsd");
        preparedStatement.executeUpdate();
        JDBCUtils.close(null,preparedStatement,connection);
    }
    //使用事务
    @Test
    public void testTransaction()  {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            //1.获取连接
            connection = JDBCUtils.getConnection();
            //事务1：开启事务
            connection.setAutoCommit(false);//取消事务自动提交的功能
            //事务2：编写sql语句
            preparedStatement = connection.prepareStatement("UPDATE account set balance = ? where stuname = ?");
            //操作1：dsw-5000
            preparedStatement.setDouble(1,5000);
            preparedStatement.setString(2,"dsw");
            preparedStatement.executeUpdate();
            //int i = 1/0;//模拟异常
            //操作2.wsd+5000
            preparedStatement.setDouble(1,15000);
            preparedStatement.setString(2,"wsd");
            preparedStatement.executeUpdate();
            //事务3:结束事务，判断有无异常
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();//回滚事务
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally {
            JDBCUtils.close(null,preparedStatement,connection);
        }
    }
}

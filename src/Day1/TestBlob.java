package Day1;

import jdk.nashorn.internal.scripts.JD;
import org.junit.Test;

import java.io.*;
import java.sql.*;

/**
 * 演示Blob类型数据的存取
 */
public class TestBlob {
    //存照片
    @Test
    public void testSave() throws SQLException, FileNotFoundException {
        //1.获取连接
        Connection connection = JDBCUtils.getConnection();
        //2.执行修改语句
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE beauty set photo = ? where id = 1");
        preparedStatement.setBlob(1,new FileInputStream("3.jpg"));
        int update = preparedStatement.executeUpdate();
        //3.关闭连接
        JDBCUtils.close(null,preparedStatement,connection);
    }
    //读照片
    @Test
    public void testRead() throws SQLException, IOException {
        //1.获取连接
        Connection connection = JDBCUtils.getConnection();
        //2.执行修改语句
        PreparedStatement preparedStatement = connection.prepareStatement("select photo from beauty where id = 1");
        ResultSet set = preparedStatement.executeQuery();
        if (set.next()){
            //方式1：
//            Blob blob = set.getBlob("photo");
//            InputStream binaryStream = blob.getBinaryStream();
            //方式2：
            InputStream inputStream = set.getBinaryStream("photo");
            FileOutputStream fos = new FileOutputStream("src\\copy.jpg");
            int len;
            byte[] b = new byte[256];
            while ((len = inputStream.read(b)) != -1){
                fos.write(b,0,len);
            }
            fos.close();
            inputStream.close();
        }
        //3.关闭连接
        JDBCUtils.close(null,preparedStatement,connection);
    }
}

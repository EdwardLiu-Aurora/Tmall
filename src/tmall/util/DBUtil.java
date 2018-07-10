package tmall.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Edward on 2018/7/3
 */

public class DBUtil {

    private static String ip = "127.0.0.1";
    private static int port = 3306;
    private static String database = "tmall";
    private static String encoding = "UTF-8";
    private static String loginName = "root";
    private static String password = "123456";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        String url = String.format("jdbc:mysql://%s:%d/%s?characterEncoding=%s&&serverTimezone=UTC", ip, port, database, encoding);
        return DriverManager.getConnection(url, loginName, password);
    }

    public static void main(String[] args) throws SQLException {
        System.out.println(getConnection());
    }

}

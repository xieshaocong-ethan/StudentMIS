import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
/**
 * StuMisDatabase class
 *
 * @author Ethantse
 * @date 2018/12/20
 */

public class StuMisDatabase {
    public Connection con1() {
        Properties pr = new Properties();
        //String c3p0Properties = Objects.requireNonNull(this.getClass().getClassLoader().getResource("c3p0.properties")).getPath();
        InputStream c3p0Properties = this.getClass().getResourceAsStream("c3p0.properties");
/*        try {
            c3p0Properties = URLDecoder.decode(c3p0Properties, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        /*java.io.File conc3p0file = new java.io.File(c3p0Properties);*/
        try {
            pr.load(c3p0Properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        try {
            cpds.setDriverClass(pr.getProperty("c3p0DriverClass"));
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        cpds.setJdbcUrl(pr.getProperty("c3p0.JDBC.url"));
        cpds.setUser(pr.getProperty("c3p0.user"));
        cpds.setPassword(pr.getProperty("c3p0.password"));
        Connection conn = null;
        try {
            conn = cpds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}

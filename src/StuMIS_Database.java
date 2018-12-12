import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class StuMIS_Database {
    public Connection con1() throws IOException, PropertyVetoException, SQLException {
        Properties pr = new Properties();
        String c3p0Properties = this.getClass().getClassLoader().getResource("c3p0.properties").getPath();
        c3p0Properties = URLDecoder.decode(c3p0Properties, "utf-8");
        java.io.File conc3p0file = new java.io.File(c3p0Properties);
        pr.load(new FileInputStream(conc3p0file));
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass(pr.getProperty("c3p0DriverClass"));
        cpds.setJdbcUrl(pr.getProperty("c3p0.JDBC.url"));
        cpds.setUser(pr.getProperty("c3p0.user"));
        cpds.setPassword(pr.getProperty("c3p0.password"));
        Connection conn =cpds.getConnection();
        return conn;
    }
}


import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.*;
import java.util.Vector;

public class StuMIS_StuInfo {
     Vector rsrow;
     Vector rshead;

    public void getdata() throws PropertyVetoException, IOException, SQLException {
        StuMIS_Database db = new StuMIS_Database();
        Connection conn = db.con1();
        PreparedStatement ps = conn.prepareStatement("select * from stu");
        ResultSet rs = ps.executeQuery();
        rshead = new Vector();
        rsrow = new Vector();
        ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                rsrow.addElement(getrow(rs,rsmd));
            }
        for (int i = 1; i <= rsmd.getColumnCount(); i++)
            rshead.addElement(rsmd.getColumnName(i));
    }

    public Vector getrow(ResultSet rs, ResultSetMetaData rsmd) throws SQLException {
        Vector rscolumn = new Vector();
            for(int o = 1; o <= rsmd.getColumnCount();o++)
                rscolumn.addElement(rs.getString(o));
        return rscolumn;
    }
}

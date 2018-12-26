
import javax.swing.*;
import javax.swing.table.TableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Pattern;

public class StuMIS_StuInfo {
     Vector rsrow;
     Vector rshead;
     ArrayList<String> userrow;
     StuMIS_Database db;
     Connection conn;
     PreparedStatement ps;
     PreparedStatement ups;
     PreparedStatement pps;
     ResultSet rs;
     ResultSet urs;
     ResultSet prs;
     boolean isexe = false;
     boolean isedit = false;
     String esql;
public StuMIS_StuInfo() {
    db = new StuMIS_Database();
    conn = db.con1();
}

    public void getdata(String sql) {
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            rshead = new Vector();
            rsrow = new Vector();
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                rsrow.addElement(getrow(rs,rsmd));
            }
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                rshead.addElement(rsmd.getColumnName(i));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String keyWord(String key){
        //String key = this.text1.getText().trim();
        String type = null;
        if(Pattern.matches("\\d{8}",key)){
            type = "Sno";
        }else if(Pattern.matches("([男]|[女])+$",key)){
            type = "Sgender";
        }else if(Pattern.matches("^[0-9]*$",key)){
            type = "Sage";
        }else if(Pattern.matches("^[\u4E00-\u9FA5]+$",key)){
            type = "Sname";
        }
        return type;

    }


    public Vector getrow(ResultSet rs, ResultSetMetaData rsmd) throws SQLException {
        Vector rscolumn = new Vector();
            for(int o = 1; o <= rsmd.getColumnCount();o++) {
                rscolumn.addElement(rs.getString(o));
            }
        return rscolumn;
    }
    public ArrayList passwordgetrow(ResultSet rs, ResultSetMetaData rsmd) throws SQLException {
        ArrayList<String> rscolumn = new ArrayList<String>();
        for(int o = 1; o <= rsmd.getColumnCount();o++) {
            rscolumn.add(rs.getString(o));
        }
        return rscolumn;
    }

    public void exedata(String sql) {
        try {
            ps = conn.prepareStatement(sql);
            int line = ps.executeUpdate();
            System.out.println(line+"行受影响");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void selectexedata(String sql) {
        try {
            ups = conn.prepareStatement(sql);
            urs = ups.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletedata(JTable tb) {
        Object dvalue = tb.getValueAt(tb.getSelectedRow(), 0);
        String sql = "delete from Stu where Sno = '" + dvalue + "'";
        try {
            exedata(sql);
        }catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void seleditdata(String regex,String sqlitem,String sqllocaitem,Object tarv,Object locav,JLabel lb) {
        if (Pattern.matches(regex,(String)tarv)) {
            esql ="update Stu set "+sqlitem+"='" + tarv + "'where "+sqllocaitem+" = '" + locav + "'";
            isexe =true;
            lb.setVisible(false);
            isedit = false;
        } else {
            if (!isedit) {
                System.out.println("请输入正确的内容");
                lb.setVisible(true);
                isedit = true;
            }
        }
    }

    public void editdata(JTable tb, int r, int c, JLabel stunolb,JLabel stunamelb,JLabel stuagelb,JLabel stugenderlb) {
     Object dvalue = tb.getValueAt(r, c);
        Object divalue = tb.getValueAt(r, 0);
        String regex1 = "\\d{8}";
        String regex2 = "[\\u4E00-\\u9FA5·\\u4E00-\\u9FA5]{2,20}";
        String regex3 = "\\d{2}";
        String regex4 = "[男女]{1}";
        String sqlitem;
        String sqllocaitem;
        if(c == 0) {
            Object davalue = tb.getValueAt(r, 1);
            sqlitem = "Sno";
            sqllocaitem = "Sname";
            seleditdata(regex1,sqlitem,sqllocaitem,dvalue,davalue,stunolb);
        } else if(c == 1) {
            sqlitem = "Sname";
            sqllocaitem = "Sno";
            seleditdata(regex2,sqlitem,sqllocaitem,dvalue,divalue,stunamelb);

        } else if(c == 2) {
            sqlitem = "Sage";
            sqllocaitem = "Sno";
            seleditdata(regex3,sqlitem,sqllocaitem,dvalue,divalue,stuagelb);
        } else if(c == 3) {
            sqlitem = "Sgender";
            sqllocaitem = "Sno";
            seleditdata(regex4,sqlitem,sqllocaitem,dvalue,divalue,stugenderlb);
        }
        if(!isedit && isexe) {
            try {
                exedata(esql);
                isexe =false;

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
    public void adddata(){
        String sql = "insert into Stu values('null','null,18,'null')";
        exedata(sql);
    }
    public boolean Userlogin(Object user) {
        String sql = "select User from user where User = '"+user+"'";
        selectexedata(sql);
        boolean flag1 = false;
        try {
            flag1 = urs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(flag1) {
            return true;
        }else{
            return false;
        }

    }
    public boolean isUserin(Object user,Object password) {
        String sql = "select userpassword from user where User = '"+user+"'";
        try {
            pps = conn.prepareStatement(sql);
            prs = pps.executeQuery();
            userrow  = new ArrayList();
            ResultSetMetaData rsmd = prs.getMetaData();
            while (prs.next()) {
                userrow.add(prs.getString("userpassword"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(userrow.contains(password)) {
            return true;
        } else {
            return false;
        }
    }

}

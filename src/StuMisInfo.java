
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Pattern;
/**
 * StuMisInfo class
 *
 * @author Ethantse
 * @date 2018/12/26
 */
public class StuMisInfo {
     Vector rsrow;
     Vector rshead;
     private ArrayList userrow;
     private Connection conn;
     private PreparedStatement ps;
     private ResultSet urs;
     private boolean isexe = false;
     private boolean isedit = false;
     private String esql;
public StuMisInfo() {
    StuMisDatabase db = new StuMisDatabase();
    conn = db.con1();
}

    public void getdata(String sql) {
        try {
            ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
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
        String type = null;
        String kregex1 = "\\d{8}";
        String kregex2 = "([男]|[女])+$";
        String kregex3 = "^[0-9]*$";
        String kregex4 = "^[\u4E00-\u9FA5]+$";
        if(Pattern.matches(kregex1,key)){
            type = "Sno";
        }else if(Pattern.matches(kregex2,key)){
            type = "Sgender";
        }else if(Pattern.matches(kregex3,key)){
            type = "Sage";
        }else if(Pattern.matches(kregex4,key)){
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
        ArrayList<String> rscolumn = new ArrayList<>();
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
            PreparedStatement ups = conn.prepareStatement(sql);
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
        int sno = 0;
        int sname = 1;
        int sage = 2;
        int sgender = 3;
        if(c == sno) {
            Object davalue = tb.getValueAt(r, 1);
            sqlitem = "Sno";
            sqllocaitem = "Sname";
            seleditdata(regex1,sqlitem,sqllocaitem,dvalue,davalue,stunolb);
        } else if(c == sname) {
            sqlitem = "Sname";
            sqllocaitem = "Sno";
            seleditdata(regex2,sqlitem,sqllocaitem,dvalue,divalue,stunamelb);

        } else if(c == sage) {
            sqlitem = "Sage";
            sqllocaitem = "Sno";
            seleditdata(regex3,sqlitem,sqllocaitem,dvalue,divalue,stuagelb);
        } else if(c == sgender) {
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
        return flag1;

    }
    public boolean isUserin(Object user,Object password) {
        String sql = "select userpassword from user where User = '"+user+"'";
        try {
            PreparedStatement pps = conn.prepareStatement(sql);
            ResultSet prs = pps.executeQuery();
            userrow  = new ArrayList();
            while (prs.next()) {
                userrow.add(prs.getString("userpassword"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userrow.contains(password);
    }

}

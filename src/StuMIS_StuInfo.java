
import javax.swing.*;
import javax.swing.table.TableModel;
import java.sql.*;
import java.util.Vector;
import java.util.regex.Pattern;

public class StuMIS_StuInfo {
     Vector rsrow;
     Vector rshead;
     StuMIS_Database db;
     Connection conn;
     PreparedStatement ps;
     ResultSet rs;
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

    public void exedata(String sql) {
        try {
            ps = conn.prepareStatement(sql);
            int line = ps.executeUpdate();
            System.out.println(line+"行受影响");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
/*    public void bufferexedata() {
        try {
            ps = conn.prepareStatement(esql.toString());
            int line = ps.executeUpdate();
            System.out.println(line+"行受影响");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
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
        /*boolean isexe = false;
        boolean isedit = false;*/
        //String sql = null;
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
            /*if (Pattern.matches(regex1,(String)dvalue)) {
                Object davalue = tb.getValueAt(r, 1);
                sql = "update Stu set Sno='" + dvalue + "'where Sname = '" + davalue + "'";
                isexe =true;
                stunolb.setVisible(false);
                isedit = false;
            } else {
                    if(!isedit) {
                        System.out.println("请输入正确的学号");
                        stunolb.setVisible(true);
                        isedit = true; }
            }*/
        } else if(c == 1) {
            sqlitem = "Sname";
            sqllocaitem = "Sno";
            seleditdata(regex2,sqlitem,sqllocaitem,dvalue,divalue,stunamelb);
                /*if(Pattern.matches(regex2,(String)dvalue)) {
                    sql = "update Stu set Sname='" + dvalue + "'where Sno = '" + divalue + "'";
                    isexe = true;
                    stunamelb.setVisible(false);
                    isedit =false;
            } else {
                if(!isedit){
                    System.out.println("请输入正确的姓名");
                    stunamelb.setVisible(true);
                    isedit =true; }
            }*/
        } else if(c == 2) {
            sqlitem = "Sage";
            sqllocaitem = "Sno";
            seleditdata(regex3,sqlitem,sqllocaitem,dvalue,divalue,stuagelb);
                /*if(Pattern.matches(regex3,(String)dvalue)) {
                    sql = "update Stu set Sage='" + dvalue + "'where Sno = '" + divalue + "'";
                    isexe = true;
                    stunamelb.setVisible(false);
                    isedit =false;
            } else {
                if(!isedit) {
                        System.out.println("请输入正确的年龄");
                        stuaeglb.setVisible(true);
                        isedit = false; }
            }*/
        } else if(c == 3) {
            sqlitem = "Sgender";
            sqllocaitem = "Sno";
            seleditdata(regex4,sqlitem,sqllocaitem,dvalue,divalue,stugenderlb);
                /*if(Pattern.matches(regex4,(String)dvalue)) {
                    esql = "update Stu set Sgender='" + dvalue + "'where Sno = '" + divalue + "'";
                    isexe = true;
                    stunamelb.setVisible(false);
                    isedit =false;
            } else {
                    if(!isedit) {
                        System.out.println("请输入正确的性别");
                        stugenderlb.setVisible(true);
                        isedit = false; }
                }*/
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
}

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StuMIS_Main {//extends JFrame implements ActionListener {
    /*StuMIS_StuInfo stuinfo;
    JPanel pan1,pan2;
    JLabel lb1,lb2,lb3;
    JTextField tf1;
    JButton button1,button2,button3,button4,button5;
    JTable table1;
    JScrollPane scrp1;
    DefaultTableModel tm;

//声明查询模块
    public StuMIS_Main() {
        //1
        pan1 = new JPanel();
        lb1 = new JLabel("请输入名字");
        tf1 = new JTextField(10);
        button1 = new JButton("查询");
        button1.addActionListener(this);button1.setActionCommand("select");
        pan1.add(lb1);pan1.add(tf1);pan1.add(button1);
        //2
        pan2 = new JPanel();
        button2 = new JButton("添加");
        button2.addActionListener(this);button2.setActionCommand("ado");
        button3 = new JButton("修改");
        button3.addActionListener(this);button3.setActionCommand("edit");
        button4 = new JButton("删除");
        button4.addActionListener(this);button4.setActionCommand("delete");
        button5 = new JButton("显示全部");
        button5.addActionListener(this);button5.setActionCommand("home");
        pan2.add(button2);pan2.add(button3);pan2.add(button4);pan2.add(button5);
        //学生信息
        stuinfo = new StuMIS_StuInfo();
        try {
            stuinfo.getdata("select * from stu");
        } catch (Exception e) {
            e.printStackTrace();
        }
        tm = new DefaultTableModel(stuinfo.rsrow,stuinfo.rshead);
        table1 = new JTable(tm);
        scrp1 = new JScrollPane(table1);

        //this.add(table1);
        this.add(scrp1);
        this.add(pan1,"North");
        this.add(pan2,"South");
        this.setTitle("学生信息管理系统");
        this.setSize(800,600);
        this.setLocation(200,180);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void backtohome() {
        stuinfo.getdata("select * from Stu");
        tm.setDataVector(stuinfo.rsrow,stuinfo.rshead);
        tm.fireTableDataChanged();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("select")) {
            String taText = tf1.getText().trim();
            String sql = "select * from Stu where Sname = '"+taText+"'";
            try {
                stuinfo.getdata(sql);
                tm.setDataVector(stuinfo.rsrow,stuinfo.rshead);
                tm.fireTableDataChanged();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            // stuinfo =new StuInfo("sql")
            // table1.setModel(stuinfo);

        } else if(e.getActionCommand().equals("add")) {
         //调用增加模块

        } else if(e.getActionCommand().equals("edit")) {
            //调用修改模块

        } else if(e.getActionCommand().equals("delete")) {
            //调用删除模块
            //tm.removeRow(table1.getSelectedRow()); //测试用


        } else if(e.getActionCommand().equals("home")) {
            backtohome();
        }
    }

    public static void main(String[] args) {

        new StuMIS_Main();
    }*/

    public static void main(String[] args) {

        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
        try {
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        new StuMIS_Mainfunc();
    }
}

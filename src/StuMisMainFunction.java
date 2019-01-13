import com.jgoodies.forms.factories.*;
import org.apache.commons.lang3.StringUtils;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jb2011.lnf.beautyeye.*;
import org.jb2011.lnf.beautyeye.ch20_filechooser.BEFileChooserUICross;



import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.event.TableModelEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.table.DefaultTableModel;
/*
 * Created by JFormDesigner on Wed Dec 19 15:26:14 CST 2018
 */

/**
 * StuMisMainFunction  class
 *
 * @author Ethantse
 * @date 2018/12/28
 */
public class StuMisMainFunction extends JFrame {
    private DefaultTableModel tm;
    private StuMisInfo stuinfo;
    private boolean iscancel = false;
    public void refresh() {
        iscancel = true;
        stuinfo.getdata("select * from Stu");
        tm.setDataVector(stuinfo.rsrow,stuinfo.rshead);
        tm.fireTableDataChanged();
        iscancel = false;
    }

    public void useralleditrole(boolean status) {
        boolean irstatus =!status;
        iscancel = irstatus;
        cancel.setEnabled(status);
        delete.setEnabled(status);
        resetedit.setEnabled(status);
        add.setEnabled(status);
        button3.setEnabled(status);
        fileinbtm.setEnabled(status);
        role.setVisible(irstatus);
    }
    public void loginstatus(boolean status){
        boolean irstatus =!status;
            tabp1.setEnabledAt(1, true);
            tabp1.setEnabledAt(2, true);
            if(status) {
                tabp1.setSelectedIndex(1);
            } else {
                tabp1.setSelectedIndex(0);
            }
            username1.setVisible(irstatus);
            ulb.setVisible(irstatus);
            password1.setVisible(irstatus);
            plb.setVisible(irstatus);
        }

    public StuMisMainFunction() {
        initComponents();
    }

    public void setIscancel() {
        iscancel = false;
    }



    private void addActionPerformed(ActionEvent e) {
        // TODO add your code here
        Vector vv = new Vector();
        vv.addElement("null");
        vv.addElement("null");
        vv.addElement(18);
        vv.addElement("男");
        tm.addRow(vv);
        stuinfo.adddata();
    }

    private void createUIComponents() {
        // none
    }

    private void cancelButtonActionPerformed(ActionEvent e) {
        //取消编辑
        iscancel = true;
        refresh();
        editlabel.setVisible(true);
    }

    private void selectActionPerformed(ActionEvent e) {
        //查询
        iscancel = true;
        String taText = tf1.getText().trim();
        String targets = stuinfo.keyWord(taText);
        String sql = "select * from Stu where "+targets+" = '"+taText+"'";
        try {
            stuinfo.getdata(sql);
            tm.setDataVector(stuinfo.rsrow,stuinfo.rshead);
            tm.fireTableDataChanged();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    private void tmlistener(TableModelEvent e) {
        //表格监听
        if(e.getType()==TableModelEvent.UPDATE && !iscancel){
            stuinfo.editdata(table1,e.getFirstRow(),e.getColumn(),label1,label3,label4,label5);
        }

    }

    private void deleteActionPerformed(ActionEvent e) {
        // 删除
        iscancel = true;
        stuinfo.deletedata(table1);
        tm.fireTableDataChanged();
        stuinfo.getdata("select * from Stu");
        tm.setDataVector(stuinfo.rsrow,stuinfo.rshead);
        refresh();
        iscancel = false;
    }

    private void reseteditActionPerformed(ActionEvent e) {
        // 恢复编辑
        setIscancel();
        editlabel.setVisible(false);
    }

    private void loginKeyTyped(KeyEvent e) {
        // 登入
        int enter = 10;
        if (e.getKeyChar()== enter) {
            String username = username1.getText();
            if (stuinfo.Userlogin(username) && StringUtils.isNotBlank(username)) {
                String password = new String(password1.getPassword());
                if(stuinfo.isUserin(username,password) && StringUtils.isNotBlank(password)) {
                    loginstatus(true);
                    if(stuinfo.userrole(username).equals("1")){
                        useralleditrole(false);
                    } else{
                        useralleditrole(true);
                    }

                } else {
                    Panelshake.panelshake(this);
                }
            } else {
               Panelshake.panelshake(this);
            }
        }
    }

    private void logoutActionPerformed(ActionEvent e) {
        //切换账号
        loginstatus(false);
        password1.setText("");
        username1.setText("");
    }

    private void fileInActionPerformed(ActionEvent e) {
        int result = fileinchooser.showOpenDialog(this);
        if(result == JFileChooser.APPROVE_OPTION) {
            //iscancel = true;
            File infile = fileinchooser.getSelectedFile();
            List<Student> students = null;
            Student student = null;
            try {
                students = new StuMisPoi().readexcel(infile);
            } catch(IOException e1){
                e1.printStackTrace();
            }
                for (int i = 0; i < students.size(); i++) {
                    student = students.get(i);
                stuinfo.addindata(student);
            }
            tabp1.setSelectedIndex(1);
            refresh();
            //iscancel = false;
        }
    }

    private void fileoutActionPerformed(ActionEvent e) {
        int result = fileoutchooser.showSaveDialog(this);
        new BEFileChooserUICross(fileinchooser);
        if(result == JFileChooser.APPROVE_OPTION) {
            List<Student> students = stuinfo.getlistdata();
        File file = fileoutchooser.getSelectedFile();
        String filename = fileoutchooser.getName(file);
        if(filename == null || filename.trim().length() == 0){
        JOptionPane.showMessageDialog(panel5,"文件名为空");
        }
        if(file.isFile()) {
            filename =file.getName();
            if(file.exists()) {
                int i = JOptionPane.showConfirmDialog(panel5, "该文件已经存在，确定要覆盖吗？");
                    if(i == JOptionPane.YES_OPTION){
                        try {
                            StuMisPoi.xlsDto2Excel(students,file);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                    else{ return;}
            }
        } else{
            if(filename.indexOf(".xlsx")==-1) {
                file = new File(fileoutchooser.getCurrentDirectory(), filename + ".xlsx");
                try {
                    file.createNewFile();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                System.out.println("renamed");
                System.out.println(file.getName());
                try {
                    StuMisPoi.xlsDto2Excel(students,file);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            } else {
                file = new File(fileoutchooser.getCurrentDirectory(), filename);
                try {
                    file.createNewFile();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                System.out.println("renamed");
                System.out.println(file.getName());
                try {
                    StuMisPoi.xlsDto2Excel(students,file);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
        }
    }

    private void refreshActionPerformed(ActionEvent e) {
        refresh();
    }
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        try {
                            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;
                            BeautyEyeLNFHelper.translucencyAtFrameInactive = true;
                            UIManager.setLookAndFeel(new BeautyEyeLookAndFeelCross());
                            UIManager.put("RootPane.setupButtonVisible", false);
                            UIManager.put("TabbedPane.tabAreaInsets", new InsetsUIResource(0,0,0,0));
                            UIManager.put("TabbedPane.contentBorderInsets", new InsetsUIResource(0,0,2,0));
                            UIManager.put("TabbedPane.tabInsets", new InsetsUIResource(3,10,9,10));
                            Font frameTitleFont = (Font)UIManager.get("InternalFrame.titleFont");
                            frameTitleFont = frameTitleFont.deriveFont(Font.PLAIN);
                            UIManager.put("InternalFrame.titleFont", frameTitleFont);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
        tabp1 = new JTabbedPane();
        homep = new JPanel() {
                    @Override
                    public void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        ImageIcon icon = new ImageIcon("C:\\javaP\\StudentMIS\\img\\3.jpg");
                        g.drawImage(icon.getImage(),0,0,getWidth(),getHeight(),this);
                    }
                };
        password1 = new JPasswordField();
        username1 = new JFormattedTextField();
        ulb = new JLabel();
        plb = new JLabel();
        datap = new JPanel();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        panel2 = new JPanel();
        select = new JButton();
        tf1 = new JTextField();
        panel3 = new JPanel();
        cancel = new JButton();
        label1 = new JLabel();
        editlabel = new JLabel();
        delete = new JButton();
        resetedit = new JButton();
        add = new JButton();
        label3 = new JLabel();
        label4 = new JLabel();
        label5 = new JLabel();
        refresh = new JButton();
        role = new JLabel();
        optionp = new JPanel();
        panel4 = new JPanel();
        scrollPane2 = new JScrollPane();
        logout = new JButton();
        panel5 = new JPanel();
        button3 = new JButton();
        fileinbtm = new JButton();
        fileinchooser = new JFileChooser();
        fileoutchooser = new JFileChooser();

        //======== this ========
        setFont(new Font("\u9ed1\u4f53", Font.PLAIN, 15));
        setTitle("\u5b66\u751f\u4fe1\u606f\u7ba1\u7406\u7cfb\u7edf");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        setMinimumSize(new Dimension(140, 360));
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== tabp1 ========
        {
            tabp1.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 12));
            tabp1.setBorder(null);

            //======== homep ========
            {
                homep.setBorder(null);
                homep.setVerifyInputWhenFocusTarget(false);
                homep.setAutoscrolls(true);
                homep.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        loginKeyTyped(e);
                    }
                });

                //---- password1 ----
                password1.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        loginKeyTyped(e);
                    }
                });

                //---- username1 ----
                username1.setToolTipText("\u8f93\u5165\u60a8\u7684\u8d26\u53f7");
                username1.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        loginKeyTyped(e);
                    }
                });

                //---- ulb ----
                ulb.setText("\u7528\u6237\u540d");
                ulb.setHorizontalAlignment(SwingConstants.CENTER);
                ulb.setLabelFor(username1);
                ulb.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 12));

                //---- plb ----
                plb.setText("\u5bc6   \u7801");
                plb.setLabelFor(password1);
                plb.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 12));
                plb.setHorizontalAlignment(SwingConstants.CENTER);

                GroupLayout homepLayout = new GroupLayout(homep);
                homep.setLayout(homepLayout);
                homepLayout.setHorizontalGroup(
                    homepLayout.createParallelGroup()
                        .addGroup(homepLayout.createSequentialGroup()
                            .addGap(181, 181, 181)
                            .addGroup(homepLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(ulb, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(plb, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(homepLayout.createParallelGroup()
                                .addComponent(password1, GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                                .addComponent(username1, GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE))
                            .addGap(217, 217, 217))
                );
                homepLayout.setVerticalGroup(
                    homepLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, homepLayout.createSequentialGroup()
                            .addGap(361, 361, 361)
                            .addGroup(homepLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(ulb)
                                .addComponent(username1))
                            .addGap(26, 26, 26)
                            .addGroup(homepLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(plb)
                                .addComponent(password1))
                            .addGap(105, 105, 105))
                );
            }
            tabp1.addTab("\u9996\u9875", homep);

            //======== datap ========
            {
                datap.setBorder(null);

                //======== scrollPane1 ========
                {
                    scrollPane1.setAutoscrolls(true);
                    scrollPane1.setViewportBorder(null);
                    scrollPane1.setPreferredSize(new Dimension(452, 419));
                    scrollPane1.setBorder(null);
                    scrollPane1.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 12));

                    //---- table1 ----
                    stuinfo = new StuMisInfo();
                            try {
                                stuinfo.getdata("select * from stu");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            tm = new DefaultTableModel(stuinfo.rsrow,stuinfo.rshead);
                            table1 = new JTable(tm);
                    table1.setFocusCycleRoot(true);
                    table1.setFocusTraversalPolicyProvider(true);
                    table1.setAutoCreateRowSorter(true);
                    table1.setFillsViewportHeight(true);
                    table1.setFont(new Font("\u9ed1\u4f53", Font.PLAIN, 12));
                    table1.setBorder(null);
                    table1.setSurrendersFocusOnKeystroke(true);
                    tm.addTableModelListener(e -> tmlistener(e));
                    scrollPane1.setViewportView(table1);
                }

                //======== panel2 ========
                {
                    panel2.setBorder(null);
                    panel2.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 12));
                    panel2.setAutoscrolls(true);

                    //---- select ----
                    select.setText("\u67e5\u8be2");
                    select.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 12));
                    select.setActionCommand("select");
                    select.setPreferredSize(new Dimension(57, 25));
                    select.addActionListener(e -> selectActionPerformed(e));

                    //---- tf1 ----
                    tf1.setToolTipText("\u9700\u8981\u663e\u793a\u7684\u5b66\u751f\u59d3\u540d");
                    tf1.setText("\u8bf7\u8f93\u5165\u5b66\u751f\u59d3\u540d");
                    tf1.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 12));

                    GroupLayout panel2Layout = new GroupLayout(panel2);
                    panel2.setLayout(panel2Layout);
                    panel2Layout.setHorizontalGroup(
                        panel2Layout.createParallelGroup()
                            .addGroup(panel2Layout.createSequentialGroup()
                                .addComponent(tf1, GroupLayout.PREFERRED_SIZE, 671, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(select, GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE))
                    );
                    panel2Layout.setVerticalGroup(
                        panel2Layout.createParallelGroup()
                            .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(tf1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(select, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                    );
                }

                //======== panel3 ========
                {
                    panel3.setBorder(null);

                    //---- cancel ----
                    cancel.setText("\u53d6\u6d88\u4fee\u6539");
                    cancel.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 12));
                    cancel.addActionListener(e -> cancelButtonActionPerformed(e));

                    //---- label1 ----
                    label1.setText("\u8bf7\u8f93\u5165\u6b63\u786e\u5b66\u53f7");
                    label1.setForeground(new Color(255, 51, 51));
                    label1.setVisible(false);
                    label1.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 12));

                    //---- editlabel ----
                    editlabel.setText("\u4e0d\u5728\u7f16\u8f91\u72b6\u6001");
                    editlabel.setForeground(new Color(255, 51, 51));
                    editlabel.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 12));
                    editlabel.setVisible(false);

                    //---- delete ----
                    delete.setText("\u5220\u9664");
                    delete.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 12));
                    delete.addActionListener(e -> deleteActionPerformed(e));

                    //---- resetedit ----
                    resetedit.setText("\u6062\u590d\u4fee\u6539");
                    resetedit.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 12));
                    resetedit.addActionListener(e -> reseteditActionPerformed(e));

                    //---- add ----
                    add.setText("\u6dfb\u52a0");
                    add.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 12));
                    add.addActionListener(e -> addActionPerformed(e));

                    //---- label3 ----
                    label3.setText("\u8bf7\u8f93\u5165\u6b63\u786e\u7684\u59d3\u540d");
                    label3.setVisible(false);
                    label3.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 12));
                    label3.setForeground(new Color(255, 0, 51));

                    //---- label4 ----
                    label4.setText("\u8bf7\u8f93\u5165\u6b63\u786e\u7684\u5e74\u9f84");
                    label4.setVisible(false);
                    label4.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 12));
                    label4.setForeground(new Color(255, 51, 51));

                    //---- label5 ----
                    label5.setText("\u8bf7\u8f93\u5165\u6b63\u786e\u7684\u6027\u522b(\u7537or\u5973)");
                    label5.setVisible(false);
                    label5.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 12));
                    label5.setForeground(new Color(255, 51, 51));

                    //---- refresh ----
                    refresh.setIcon(new ImageIcon("C:\\javaP\\StudentMIS\\img\\Refresh_16px.png"));
                    refresh.addActionListener(e -> refreshActionPerformed(e));

                    GroupLayout panel3Layout = new GroupLayout(panel3);
                    panel3.setLayout(panel3Layout);
                    panel3Layout.setHorizontalGroup(
                        panel3Layout.createParallelGroup()
                            .addGroup(panel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(label5, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label4, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label3, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label1, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(editlabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(refresh, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(add, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(delete, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(resetedit, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cancel, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                    );
                    panel3Layout.setVerticalGroup(
                        panel3Layout.createParallelGroup()
                            .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label3, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label4, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label5, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addComponent(editlabel)
                                .addComponent(cancel)
                                .addComponent(resetedit, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(delete, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(add))
                            .addGroup(panel3Layout.createSequentialGroup()
                                .addComponent(refresh)
                                .addGap(0, 0, Short.MAX_VALUE))
                    );
                }

                //---- role ----
                role.setText("\u4f60\u6ca1\u6709\u6743\u9650\uff01");
                role.setForeground(new Color(255, 51, 51));
                role.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 12));
                role.setVisible(false);

                GroupLayout datapLayout = new GroupLayout(datap);
                datap.setLayout(datapLayout);
                datapLayout.setHorizontalGroup(
                    datapLayout.createParallelGroup()
                        .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(datapLayout.createSequentialGroup()
                            .addGroup(datapLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                .addGroup(datapLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(role, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(panel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(panel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                datapLayout.setVerticalGroup(
                    datapLayout.createParallelGroup()
                        .addGroup(datapLayout.createSequentialGroup()
                            .addComponent(panel2, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(datapLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(panel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(role, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)))
                );
            }
            tabp1.addTab("\u6570\u636e", datap);
            tabp1.setEnabledAt(1, false);

            //======== optionp ========
            {

                //======== panel4 ========
                {

                    //---- logout ----
                    logout.setText("\u5207\u6362\u8d26\u53f7");
                    logout.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
                    logout.addActionListener(e -> logoutActionPerformed(e));

                    GroupLayout panel4Layout = new GroupLayout(panel4);
                    panel4.setLayout(panel4Layout);
                    panel4Layout.setHorizontalGroup(
                        panel4Layout.createParallelGroup()
                            .addGroup(panel4Layout.createSequentialGroup()
                                .addGap(172, 172, 172)
                                .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(471, Short.MAX_VALUE))
                            .addComponent(logout, GroupLayout.DEFAULT_SIZE, 743, Short.MAX_VALUE)
                    );
                    panel4Layout.setVerticalGroup(
                        panel4Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addGroup(panel4Layout.createSequentialGroup()
                                .addComponent(logout, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    );
                }

                //======== panel5 ========
                {

                    //---- button3 ----
                    button3.setText("\u5bfc\u51fa");
                    button3.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
                    button3.setBorder(Borders.createEmptyBorder("0dlu, 0dlu, 0dlu, 0dlu"));
                    button3.addActionListener(e -> fileoutActionPerformed(e));

                    //---- fileinbtm ----
                    fileinbtm.setText("\u5bfc\u5165");
                    fileinbtm.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 14));
                    fileinbtm.setBorder(BorderFactory.createEmptyBorder());
                    fileinbtm.addActionListener(e -> fileInActionPerformed(e));

                    GroupLayout panel5Layout = new GroupLayout(panel5);
                    panel5.setLayout(panel5Layout);
                    panel5Layout.setHorizontalGroup(
                        panel5Layout.createParallelGroup()
                            .addComponent(button3, GroupLayout.DEFAULT_SIZE, 743, Short.MAX_VALUE)
                            .addComponent(fileinbtm, GroupLayout.DEFAULT_SIZE, 743, Short.MAX_VALUE)
                    );
                    panel5Layout.setVerticalGroup(
                        panel5Layout.createParallelGroup()
                            .addGroup(panel5Layout.createSequentialGroup()
                                .addComponent(button3, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fileinbtm, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(31, Short.MAX_VALUE))
                    );
                }

                GroupLayout optionpLayout = new GroupLayout(optionp);
                optionp.setLayout(optionpLayout);
                optionpLayout.setHorizontalGroup(
                    optionpLayout.createParallelGroup()
                        .addComponent(panel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panel5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
                optionpLayout.setVerticalGroup(
                    optionpLayout.createParallelGroup()
                        .addGroup(optionpLayout.createSequentialGroup()
                            .addComponent(panel4, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(panel5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(401, Short.MAX_VALUE))
                );
            }
            tabp1.addTab("\u9009\u9879", optionp);
            tabp1.setEnabledAt(2, false);
        }
        contentPane.add(tabp1, BorderLayout.NORTH);
        pack();
        setLocationRelativeTo(getOwner());

        //---- fileinchooser ----
        FileNameExtensionFilter filter = new FileNameExtensionFilter("xls,xlsx    ExcelFile","xls","xlsx");
            fileinchooser.setFileFilter(filter);
        fileinchooser.setMinimumSize(null);
            fileinchooser.setPreferredSize(new Dimension(718, 428));
            fileinchooser.setAcceptAllFileFilterUsed(false);
            fileinchooser.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 12));

        //---- fileoutchooser ----
        fileoutchooser.setFileFilter(filter);
        fileoutchooser.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 12));
        fileoutchooser.setPreferredSize(new Dimension(718, 428));
        fileoutchooser.setAcceptAllFileFilterUsed(false);
        fileoutchooser.setSelectedFile(new File(".xlsx"));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JTabbedPane tabp1;
    private JPanel homep;
    private JPasswordField password1;
    private JFormattedTextField username1;
    private JLabel ulb;
    private JLabel plb;
    private JPanel datap;
    private JScrollPane scrollPane1;
    private JTable table1;
    private JPanel panel2;
    private JButton select;
    private JTextField tf1;
    private JPanel panel3;
    private JButton cancel;
    private JLabel label1;
    private JLabel editlabel;
    private JButton delete;
    private JButton resetedit;
    private JButton add;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JButton refresh;
    private JLabel role;
    private JPanel optionp;
    private JPanel panel4;
    private JScrollPane scrollPane2;
    private JButton logout;
    private JPanel panel5;
    private JButton button3;
    private JButton fileinbtm;
    private JFileChooser fileinchooser;
    private JFileChooser fileoutchooser;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}

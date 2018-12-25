import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
/*
 * Created by JFormDesigner on Wed Dec 19 15:26:14 CST 2018
 */



/**
 * @author Ethantse
 */
public class StuMIS_Mainfunc extends JFrame {
    DefaultTableModel tm;
    StuMIS_StuInfo stuinfo;
    boolean iscancel= false;

    public StuMIS_Mainfunc() {
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
        vv.addElement("null");
        tm.addRow(vv);
        stuinfo.adddata();
    }

    private void createUIComponents() {
        // TODO: add custom component creation code here
    }

    private void cancelButtonActionPerformed(ActionEvent e) {
        iscancel = true;
        stuinfo.getdata("select * from Stu");
        tm.setDataVector(stuinfo.rsrow,stuinfo.rshead);
        tm.fireTableDataChanged();
        editlabel.setVisible(true);
    }

    private void selectActionPerformed(ActionEvent e) {
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
        if(e.getType()==TableModelEvent.UPDATE && !iscancel){
            stuinfo.editdata(table1,e.getFirstRow(),e.getColumn(),label1,label3,label4,label5);
        }

    }

    private void deleteActionPerformed(ActionEvent e) {
        // TODO add your code here
        stuinfo.deletedata(table1);
        tm.fireTableDataChanged();
        stuinfo.getdata("select * from Stu");
        tm.setDataVector(stuinfo.rsrow,stuinfo.rshead);
        tm.fireTableDataChanged();
    }

    private void reseteditActionPerformed(ActionEvent e) {
        // TODO add your code here
        setIscancel();
        editlabel.setVisible(false);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        tabbedPane1 = new JTabbedPane();
        dialogPane = new JPanel();
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
        panel1 = new JPanel();
        panel4 = new JPanel();
        textField2 = new JTextField();
        textField3 = new JTextField();
        comboBox1 = new JComboBox<>();
        button2 = new JButton();
        comboBox2 = new JComboBox<>();
        panel5 = new JPanel();
        button3 = new JButton();

        //======== this ========
        setFont(new Font("\u9ed1\u4f53", Font.PLAIN, 15));
        setTitle("\u5b66\u751f\u4fe1\u606f\u7ba1\u7406\u7cfb\u7edf");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== tabbedPane1 ========
        {
            tabbedPane1.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 12));

            //======== dialogPane ========
            {
                dialogPane.setBorder(null);

                //======== scrollPane1 ========
                {
                    scrollPane1.setAutoscrolls(true);
                    scrollPane1.setViewportBorder(null);
                    scrollPane1.setPreferredSize(new Dimension(452, 419));
                    scrollPane1.setBorder(null);
                    scrollPane1.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 12));

                    //---- table1 ----
                    stuinfo = new StuMIS_StuInfo();
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
                                .addComponent(select, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    );
                    panel2Layout.setVerticalGroup(
                        panel2Layout.createParallelGroup()
                            .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(tf1)
                                .addComponent(select, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
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
                                .addComponent(add, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(delete, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(resetedit, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cancel, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24))
                    );
                    panel3Layout.setVerticalGroup(
                        panel3Layout.createParallelGroup()
                            .addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(cancel)
                                .addComponent(resetedit, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(delete, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(add)
                                .addComponent(label1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label3, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label4, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label5, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                .addComponent(editlabel))
                    );
                }

                GroupLayout dialogPaneLayout = new GroupLayout(dialogPane);
                dialogPane.setLayout(dialogPaneLayout);
                dialogPaneLayout.setHorizontalGroup(
                    dialogPaneLayout.createParallelGroup()
                        .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(dialogPaneLayout.createSequentialGroup()
                            .addGroup(dialogPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                .addComponent(panel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(panel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                dialogPaneLayout.setVerticalGroup(
                    dialogPaneLayout.createParallelGroup()
                        .addGroup(dialogPaneLayout.createSequentialGroup()
                            .addComponent(panel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(panel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                );
            }
            tabbedPane1.addTab("\u5b66\u751f\u6570\u636e", dialogPane);

            //======== panel1 ========
            {

                //======== panel4 ========
                {

                    //---- comboBox1 ----
                    comboBox1.setModel(new DefaultComboBoxModel<>(new String[] {
                        "\u7537",
                        "\u5973"
                    }));

                    //---- button2 ----
                    button2.setText("text");

                    //---- comboBox2 ----
                    comboBox2.setModel(new DefaultComboBoxModel<>(new String[] {
                        "5",
                        "6",
                        "7",
                        "8",
                        "9",
                        "10",
                        "11",
                        "12",
                        "13",
                        "14",
                        "15",
                        "16",
                        "17",
                        "18",
                        "19",
                        "20",
                        "21",
                        "22",
                        "23",
                        "24",
                        "25",
                        "26",
                        "27",
                        "28",
                        "29",
                        "30",
                        "31",
                        "32",
                        "33",
                        "34",
                        "35",
                        "36",
                        "37",
                        "38",
                        "39",
                        "40",
                        "41",
                        "42",
                        "43",
                        "44",
                        "45",
                        "46",
                        "47",
                        "48",
                        "49",
                        "50",
                        "51",
                        "52",
                        "53",
                        "54",
                        "55",
                        "56",
                        "57",
                        "58",
                        "59",
                        "60",
                        "61",
                        "62",
                        "63",
                        "64",
                        "65",
                        "66",
                        "67",
                        "68",
                        "69",
                        "70",
                        "71",
                        "72",
                        "73",
                        "74",
                        "75",
                        "76",
                        "77",
                        "78",
                        "79",
                        "80",
                        "81",
                        "82",
                        "83",
                        "84",
                        "85",
                        "86",
                        "87",
                        "88",
                        "89",
                        "90",
                        "91",
                        "92",
                        "93",
                        "94",
                        "95",
                        "96",
                        "97",
                        "98",
                        "99",
                        "100"
                    }));

                    GroupLayout panel4Layout = new GroupLayout(panel4);
                    panel4.setLayout(panel4Layout);
                    panel4Layout.setHorizontalGroup(
                        panel4Layout.createParallelGroup()
                            .addGroup(panel4Layout.createSequentialGroup()
                                .addComponent(button2, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(textField2, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(textField3, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(comboBox2, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                                .addComponent(comboBox1, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                    );
                    panel4Layout.setVerticalGroup(
                        panel4Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addGroup(panel4Layout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addGroup(panel4Layout.createParallelGroup()
                                    .addGroup(panel4Layout.createSequentialGroup()
                                        .addComponent(comboBox1)
                                        .addContainerGap())
                                    .addGroup(panel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(button2)
                                        .addComponent(textField3, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(comboBox2)
                                        .addComponent(textField2, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))))
                    );
                }

                //======== panel5 ========
                {

                    //---- button3 ----
                    button3.setText("text");

                    GroupLayout panel5Layout = new GroupLayout(panel5);
                    panel5.setLayout(panel5Layout);
                    panel5Layout.setHorizontalGroup(
                        panel5Layout.createParallelGroup()
                            .addGroup(panel5Layout.createSequentialGroup()
                                .addComponent(button3, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 655, Short.MAX_VALUE))
                    );
                    panel5Layout.setVerticalGroup(
                        panel5Layout.createParallelGroup()
                            .addGroup(panel5Layout.createSequentialGroup()
                                .addComponent(button3, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 163, Short.MAX_VALUE))
                    );
                }

                GroupLayout panel1Layout = new GroupLayout(panel1);
                panel1.setLayout(panel1Layout);
                panel1Layout.setHorizontalGroup(
                    panel1Layout.createParallelGroup()
                        .addComponent(panel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panel5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
                panel1Layout.setVerticalGroup(
                    panel1Layout.createParallelGroup()
                        .addGroup(panel1Layout.createSequentialGroup()
                            .addComponent(panel4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(panel5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 309, Short.MAX_VALUE))
                );
            }
            tabbedPane1.addTab("\u6570\u636e\u64cd\u4f5c", panel1);
        }
        contentPane.add(tabbedPane1, BorderLayout.NORTH);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JTabbedPane tabbedPane1;
    private JPanel dialogPane;
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
    private JPanel panel1;
    private JPanel panel4;
    private JTextField textField2;
    private JTextField textField3;
    private JComboBox<String> comboBox1;
    private JButton button2;
    private JComboBox<String> comboBox2;
    private JPanel panel5;
    private JButton button3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}

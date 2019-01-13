import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FilenameFilter;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.filechooser.FileNameExtensionFilter;
/*
 * Created by JFormDesigner on Tue Jan 01 22:50:53 CST 2019
 */



/**
 * @author Ethantse
 */
public class FileInFrame extends JFrame {
    File file;
    int result;
    public FileInFrame() {
        initComponents();
    }

    private void fileinActionPerformed(ActionEvent e) {
        // TODO add your code here
        if(result == JFileChooser.APPROVE_OPTION){
            file = fileinch.getSelectedFile();
            System.out.println("1");
            dispose();
        } else if(result == JFileChooser.CANCEL_OPTION) {
            //this.setVisible(false);
            System.out.println("0");

        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        fileinch = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("xls,xlsx    ExcelFile","xls","xlsx");
        fileinch.setFileFilter(filter);

        //======== this ========
        setAlwaysOnTop(true);
        setVisible(true);
        Container contentPane = getContentPane();

        //---- fileinch ----
        fileinch.setMinimumSize(null);
        fileinch.setPreferredSize(new Dimension(718, 428));
        fileinch.setAcceptAllFileFilterUsed(false);
        fileinch.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 12));
        fileinch.addActionListener(e -> fileinActionPerformed(e));

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(fileinch, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addComponent(fileinch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JFileChooser fileinch;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.GroupLayout;
/*
 * Created by JFormDesigner on Tue Jan 01 22:36:05 CST 2019
 */



/**
 * @author Ethantse
 */
public class FileSelectFrame {
    String filepath;
    public FileSelectFrame() {
        initComponents();
    }

    private void fileselectActionPerformed(ActionEvent e) {
        int result = 0;
        String filename;
        if(result == fileChooser1.APPROVE_OPTION){
            File file = fileChooser1.getSelectedFile();
            filename = fileChooser1.getName(file);
            if(filename==null || filename.trim().length()==0){
                Panelshake.panelshake(fileout);
            }
            filepath = file.getAbsolutePath();
            //fileout.setVisible(false);
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        fileout = new JFrame();
        fileChooser1 = new JFileChooser();

        //======== fileout ========
        {
            fileout.setAlwaysOnTop(true);
            fileout.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            fileout.setName("fileinframe");
            fileout.setIconImage(new ImageIcon("C:\\javaP\\StudentMIS\\img\\fileoutput.png").getImage());
            fileout.setVisible(true);
            Container fileoutContentPane = fileout.getContentPane();

            //---- fileChooser1 ----
            fileChooser1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser1.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 12));
            fileChooser1.setDialogType(JFileChooser.SAVE_DIALOG);
            fileChooser1.addActionListener(e -> fileselectActionPerformed(e));

            GroupLayout fileoutContentPaneLayout = new GroupLayout(fileoutContentPane);
            fileoutContentPane.setLayout(fileoutContentPaneLayout);
            fileoutContentPaneLayout.setHorizontalGroup(
                fileoutContentPaneLayout.createParallelGroup()
                    .addComponent(fileChooser1, GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
            );
            fileoutContentPaneLayout.setVerticalGroup(
                fileoutContentPaneLayout.createParallelGroup()
                    .addComponent(fileChooser1, GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
            );
            fileout.pack();
            fileout.setLocationRelativeTo(fileout.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JFrame fileout;
    private JFileChooser fileChooser1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}

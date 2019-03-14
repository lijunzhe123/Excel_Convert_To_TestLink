package com.jyall.utils.email.springmail.dom4j;

import java.awt.Container;
import java.awt.Font;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StartMenu
        extends JFrame
{
    private static DropTarget dropTarget;
    private JPanel panel;
    private JLabel filename;
    private JButton btnNewButton;
    private DropDragSupportTextArea filepath;
    private JLabel lblSheet;
    private JTextField SheetName;
    private JButton button;
    private JButton button_1;
    private JLabel lblqq;

    public StartMenu()
    {
        getContentPane().setLayout(null);
        setDefaultCloseOperation(3);
        this.panel = new JPanel();
        this.panel.setBounds(-2, 0, 502, 292);
        getContentPane().add(this.panel);
        this.panel.setLayout(null);

        this.filename = new JLabel("文件路径");
        this.filename.setBounds(10, 32, 65, 15);
        this.panel.add(this.filename);

        this.btnNewButton = new JButton("转换");
        this.btnNewButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    CreatXML.writeDocument(CreatXML.createDocument(
                            StartMenu.this.filepath.getText(), StartMenu.this.SheetName.getText()), "a.xml");
                    JOptionPane.showMessageDialog(null, "转换成功，即将退出", "消息",
                            1);
                    System.exit(0);
                }
                catch (Exception e1)
                {
                    JOptionPane.showMessageDialog(null, "转换失败，即将退出", "消息",
                            0);
                    e1.printStackTrace();
                }
            }
        });
        this.btnNewButton.setBounds(425, 110, 80, 23);
        this.panel.add(this.btnNewButton);

        this.filepath = new DropDragSupportTextArea();
        this.filename.setAutoscrolls(true);
        this.filepath.setDragEnabled(true);
        this.filepath.setText("用例模板.xlsx");
        this.filepath.setBounds(85, 28, 348, 21);
        this.panel.add(this.filepath);
        this.filepath.setColumns(10);

        this.lblSheet = new JLabel("Sheet名字");

        this.lblSheet.setBounds(10, 74, 65, 15);
        this.panel.add(this.lblSheet);

        this.SheetName = new JTextField();
        this.SheetName.setBounds(85, 71, 348, 21);
        this.SheetName.setText("TestCase");
        this.panel.add(this.SheetName);
        this.SheetName.setColumns(10);

        this.button = new JButton("清空");
        this.button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                StartMenu.this.filepath.setText("");
            }
        });
        this.button.setBounds(435, 27, 70, 23);
        this.panel.add(this.button);

        this.button_1 = new JButton("清空");
        this.button_1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                StartMenu.this.SheetName.setText("");
            }
        });
        this.button_1.setBounds(435, 70, 70, 23);
        this.panel.add(this.button_1);

        this.lblqq = new JLabel("Author：刘文彬→→→→QQ:370143056");
        this.lblqq.setFont(new Font("幼圆", 0, 13));
        this.lblqq.setBounds(8, 115, 250, 15);
        this.panel.add(this.lblqq);

        dropTarget = new DropTarget();
    }

    public static void main(String[] args)
    {
        StartMenu st = new StartMenu();
        st.setLocation(380, 200);
        st.setSize(550, 180);
        st.setResizable(false);
        st.setVisible(true);
    }
}

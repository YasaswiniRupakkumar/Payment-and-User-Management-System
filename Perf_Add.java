import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

class Perf_Add extends JFrame implements KeyListener, ActionListener
{
    JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12;
    JTextField t1, t2, t3, t4;
    JButton b1;

    Connection con;
    PreparedStatement ps;

    Statement st;
    ResultSet rs1, rs2;

    String stno, grade;
    int m1, m2, m3, total;
    double avg;

    Perf_Add()
    {
    super("Performance Table Addition Program");
    setSize(320,600);
    setResizable(false);
    setLayout(null);
    Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
    Dimension windowsize= getSize();

    this.setBounds((screensize.width - windowsize.width)/2,
        (screensize.height - windowsize.height)/2,windowsize.width,windowsize.height);


    l1=new JLabel("Number");
    add(l1);
    l1.setBounds(30, 50, 100, 25);

    l2=new JLabel("Name");
    add(l2);
    l2.setBounds(30, 100, 100, 25);

    l3=new JLabel("Marks1");
    add(l3);
    l3.setBounds(30, 150, 100, 25);

    l4=new JLabel("Marks2");
    add(l4);
    l4.setBounds(30, 200, 100, 25);

    l5=new JLabel("Marks3");
    add(l5);
    l5.setBounds(30, 250, 100, 25);

    l6=new JLabel("Total");
    add(l6);
    l6.setBounds(30, 300, 100, 25);

    l7=new JLabel("Average");
    add(l7);
    l7.setBounds(30, 350, 100, 25);

    l8=new JLabel("Grade");
    add(l8);
    l8.setBounds(30, 400, 100, 25);

    t1=new JTextField();
    add(t1);
    t1.setBounds(130, 50, 150, 25);
    t1.addKeyListener(this);

    l9=new JLabel();
    add(l9);
    l9.setBounds(130, 100, 150, 25);

    t2=new JTextField();
    add(t2);
    t2.setBounds(130, 150, 150, 25);
    t2.setEditable(false);
    t2.addKeyListener(this);

    t3=new JTextField();
    add(t3);
    t3.setBounds(130, 200, 150, 25);
    t3.setEditable(false);
    t3.addKeyListener(this);

    t4=new JTextField();
    add(t4);
    t4.setBounds(130, 250, 150, 25);
    t4.setEditable(false);
    t4.addKeyListener(this);

    l10=new JLabel();
    add(l10);
    l10.setBounds(130, 300, 150, 25);

    l11=new JLabel();
    add(l11);
    l11.setBounds(130, 350, 150, 25);

    l12=new JLabel();
    add(l12);
    l12.setBounds(130, 400, 150, 25);

    b1=new JButton("Add");
    add(b1);
    b1.setBounds(130, 450, 100, 25);
    b1.addActionListener(this);

    try
    {
        Class.forName("com.mysql.jdbc.Driver");//error here
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Student7", "root", "");
        JOptionPane.showMessageDialog(null, "Database connection Suceeded", "Connection Message", JOptionPane.INFORMATION_MESSAGE);
    }
    catch(Exception e)
    {
        JOptionPane.showMessageDialog(null,  e, "Error", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
        // this.hide();//Only closes teh current Frame
    }
    }
    public void keyPressed(KeyEvent e)
    {
        int code=e.getKeyChar();
        if((e.getSource()==t1) &&(code==10))
        {
            onNumVal();
        }
        else
        if((e.getSource()==t2) &&(code==10))
        {
            onMarks1Val();
        }
        else
        if((e.getSource()==t3) &&(code==10))
        {
            onMarks2Val();
        }
        else
        if((e.getSource()==t4) &&(code==10))
        {
            onMarks3Val();
        }
    }
    public void keyTyped(KeyEvent e)
    {
    }
    public void keyReleased(KeyEvent e)
    {
    }
    public void actionPerformed(ActionEvent e)
    {
        Object obj=e.getSource();
        if(obj==b1)
        {
            onRecordAdd();
        }        
    }

    public void onNumVal()
    {
        stno=t1.getText();
        if(stno.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Number is Empty", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            stno=stno.trim();
            if(stno.length()!=5)
            {
                JOptionPane.showMessageDialog(null, "Number should be 5 digits", "Error", JOptionPane.ERROR_MESSAGE);
                t1.setText("");
            }
            else
            {
                try
                {
                    st=con.createStatement();
                    rs1=st.executeQuery("select * from Personal where Number='"+stno+"'");
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null,  "Error 1", "Error", JOptionPane.ERROR_MESSAGE);
                }

                try
                {
                    if(!(rs1.next()))
                    {
                        JOptionPane.showMessageDialog(null, "Please register yourself", "Error", JOptionPane.ERROR_MESSAGE);
                        t1.setText("");
                        t1.requestFocus();
                    }
                    else
                    {
                        st=con.createStatement();
                        rs2=st.executeQuery("select * from Performance where Number='"+stno+"'");
                        
                        if(rs2.next())
                        {
                            JOptionPane.showMessageDialog(null, "Record already exists in Performanmce Table", "Error", JOptionPane.ERROR_MESSAGE);
                            t1.setText("");
                            t1.requestFocus();
                        }
                        else
                        {
                            l9.setText(rs1.getString(2));
                            t1.setEditable(false);
                            t2.setEditable(true);
                            t2.requestFocus();
                        }
                    }
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null,  "Error 2", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void onMarks1Val()
    {
        String sm1;
        sm1=t2.getText();
        if(sm1.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Marks1 is Empty", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            try
            {
                m1=Integer.parseInt(sm1);
                if((m1<0)||(m1>100))
                {
                    JOptionPane.showMessageDialog(null, "Marks1 should be between 0 and 100", "Error", JOptionPane.ERROR_MESSAGE);
                    t3.setText("");
                }
                else
                {
                    t3.setEditable(true);
                    t3.requestFocus();
                    t2.setEditable(false);
                }
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null, "Marks1 should be numeric", "Error", JOptionPane.ERROR_MESSAGE);
                t2.setText("");
                t2.requestFocus();
            }
        }
    }

    public void onMarks2Val()
    {
        String sm2;
        sm2=t3.getText();
        if(sm2.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Marks2 is Empty", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            try
            {
                m2=Integer.parseInt(sm2);
                if((m2<0)||(m2>100))
                {
                    JOptionPane.showMessageDialog(null, "Marks2 should be between 0 and 100", "Error", JOptionPane.ERROR_MESSAGE);
                    t3.setText("");
                }
                else
                {
                    t4.setEditable(true);
                    t4.requestFocus();
                    t3.setEditable(false);
                }
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null, "Marks2 should be numeric", "Error", JOptionPane.ERROR_MESSAGE);
                t3.setText("");
                t3.requestFocus();
            }
        }
    }

    public void onMarks3Val()
    {
        String sm3;
        sm3=t4.getText();
        if(sm3.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Marks3 is Empty", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            try
            {
                m3=Integer.parseInt(sm3);
                if((m3<0)||(m3>100))
                {
                    JOptionPane.showMessageDialog(null, "Marks3 should be between 0 and 100", "Error", JOptionPane.ERROR_MESSAGE);
                    t4.setText("");
                }
                else
                {
                    total=m1+m2+m3;
                    avg=total/3;
                    if(avg>80)
                    grade="Distinction";
                    else if(avg>70)
                    grade="Credit";
                    else if(avg>60)
                    grade="Merit Pass";
                    else
                    grade="Fail";

                    l10.setText(String.valueOf(total));
                    l11.setText(String.valueOf(avg));
                    l12.setText(grade);
                    t4.setEditable(false);
                }
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null, "Marks3 should be numeric", "Error", JOptionPane.ERROR_MESSAGE);
                t4.setText("");
                t4.requestFocus();
            }
        }
    }

    public void onRecordAdd()
    {
        int confirm=JOptionPane.showConfirmDialog(null, "Do you want to add the record", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(confirm==JOptionPane.YES_OPTION)
        {
            try
            {
                ps=con.prepareStatement("insert into Performance values(?, ?, ?, ?, ?, ?, ?, ?)");
                ps.setString(1, stno);
                ps.setString(2, l9.getText());
                ps.setInt(3, m1);
                ps.setInt(4, m2);
                ps.setInt(5, m3);
                ps.setInt(6, total);
                ps.setDouble(7, avg);
                ps.setString(8, grade);

                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Record added", "Addition Message", JOptionPane.INFORMATION_MESSAGE);
                onUserConfirm();
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null,  e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else
        {
            onUserConfirm();
        }
    }

    public void onUserConfirm()
    {
        int confirm=JOptionPane.showConfirmDialog(null, "Do you want to continue", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(confirm==JOptionPane.YES_OPTION)
        {
            onClearAndStart();
        }
        else
        {
            System.exit(0);
            //this.hide();
        }
    }

    public void onClearAndStart()
    {
        t1.setText("");
        t2.setText("");
        t3.setText("");
        t4.setText("");
        l9.setText("");
        l10.setText("");
        l11.setText("");
        l12.setText("");
        t1.setEditable(true);
        t1.requestFocus();
    }

    public static void main(String args[])
    {
        Perf_Add add1 = new Perf_Add();
        add1.setVisible(true);
    }
}
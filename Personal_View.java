import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

class Personal_View extends JFrame implements KeyListener, ActionListener
{
    JLabel l1, l2, l3, l4, l5;
    JTextField t1, t2, t3, t4, t5;
    JButton b1;
    Connection con;
    Statement st;
    ResultSet rs;
    String stno;

    Personal_View()
    {
        super("Personal Table Viewing Program");
        setSize(320, 320);
        setResizable(false);
        setLayout(null);
        Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
        Dimension windowsize=getSize();

        this.setBounds((screensize.width-windowsize.width)/2,(screensize.height-windowsize.height)/2, windowsize.width,windowsize.height);

        l1=new JLabel("Number");
        add(l1);
        l1.setBounds(30, 30, 100, 25);

        l2=new JLabel("Name");
        add(l2);
        l2.setBounds(30, 70, 100, 25);

        l3=new JLabel("Address");
        add(l3);
        l3.setBounds(30, 110, 100, 25);

        l4=new JLabel("Tel No");
        add(l4);
        l4.setBounds(30, 150, 100, 25);

        l5=new JLabel("Email");
        add(l5);
        l5.setBounds(30, 190, 100, 25);

        t1=new JTextField();
        add(t1);
        t1.setBounds(190, 30, 150, 25);
        t1.addKeyListener(this);

        t2=new JTextField();
        add(t2);
        t2.setBounds(190, 70, 150, 25);

        t3=new JTextField();
        add(t3);
        t3.setBounds(130, 150, 150, 25);

        t4=new JTextField();
        add(t4);
        t4.setBounds(130, 200, 150, 25);

        t5=new JTextField();
        add(t5);
        t5.setBounds(130, 250, 150, 25);

        b1=new JButton("View More");
        add(b1);
        b1.setBounds(80, 250, 100, 25);
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

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==b1)
        {
            onViewMore();
        }
    }

    public void keyPressed(KeyEvent e)
    {
        int code=e.getKeyChar();
        if((e.getSource()==t1) &&(code==10))
        {
            onNumVal();
        }
    }

    public void keyTyped(KeyEvent e)
    {
    }
    public void keyReleased(KeyEvent e)
    {
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
                    rs=st.executeQuery("select * from Personal where Number='"+stno+"'");

                    if(!(rs.next()))
                    {
                        JOptionPane.showMessageDialog(null,  "Does not exist", "Error", JOptionPane.ERROR_MESSAGE);

                        t1.setText("");
                        t1.requestFocus();
                    }
                    else
                    {
                        t2.setText(rs.getString(2));
                        t3.setText(rs.getString(3));
                        t4.setText(rs.getString(4));
                        t5.setText(rs.getString(5));
                    }
                }
                catch(Exception ee)
                {
                    JOptionPane.showMessageDialog(null,  "An error in execution", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void onViewMore()
    {
        int confirm=JOptionPane.showConfirmDialog(null, "Do you want to continue", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(confirm==JOptionPane.YES_OPTION)
        {
            onClearAndStart();
        }
        else
        {
            // System.exit(0);
            this.hide();
        }
    }

    public void onClearAndStart()
    {
        t1.setText("");
        t2.setText("");
        t3.setText("");
        t4.setText("");
        t5.setText("");
        t1.setEditable(true);
        t1.requestFocus();
    }


    public static void main (String args[])
    {
        Personal_View view=new Personal_View();
        view.setVisible(true);
    }
}

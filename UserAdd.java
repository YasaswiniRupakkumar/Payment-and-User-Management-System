import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

class UserAdd extends JFrame implements KeyListener
{
    JLabel l1, l2, l3, l4, l5, l6, l7;
    JTextField t1;
    JPasswordField p1, p2;

    Connection con;

    PreparedStatement ps;

    Statement st;
    ResultSet rs;

    String uname, pword, conpword;

    UserAdd()//constructor
    {
        super("User Add");

        setSize(322, 300);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(Color.CYAN);

        Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
        Dimension windowsize=getSize();

        this.setBounds((screensize.width-windowsize.width)/2, (screensize.height-windowsize.height)/2, windowsize.width, windowsize.height);

        l1 = new JLabel("User Name");
        add(l1);
        l1.setBounds(60, 80, 100, 25);

        l2 = new JLabel("Password");
        add(l2);
        l1.setBounds(60, 140, 100, 25);

        l3 = new JLabel("Confirm Password");
        add(l3);
        l1.setBounds(60, 200, 100, 25);

        t1 = new JTextField();
        add(t1);
        t1.setBounds(180, 80, 100, 25);
        t1.addKeyListener(this);

        p1 = new JPasswordField();
        add(p1);
        p1.setEditable(false);
        p1.setBounds(180, 140, 100, 25);
        p1.addKeyListener(this);

        p2 = new JPasswordField();
        add(p2);
        p2.setEditable(false);
        p2.setBounds(180, 140, 100, 25);
        p2.addKeyListener(this);

        try
        {
            Class.forName("com.mysql.jdbc.Driver");//error here
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/UserList", "root", "");
            JOptionPane.showMessageDialog(null, "Database connection Suceeded", "Connection Message", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,  e, "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

    }

    public void keyPressed(KeyEvent e)
    {
        int code=e.getKeyChar();
        if(code==10)
        if(e.getSource()==t1)
        {
            onUserNameVal();
        }
        else if(e.getSource()==p1)
        {
            onPasswordVal();
        }
        else if(e.getSource()==p2)
        {
            onConPwordVal();
        }
    }

    public void keyTyped(KeyEvent e)
    {
    }
    public void keyReleased(KeyEvent e)
    {
    }

    public void onUserNameVal()
    {
        uname=t1.getText();
        if(uname.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Please enter the User Name", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            uname=uname.trim();
            if((uname.length()<10)||(uname.length()>20))
            {
                JOptionPane.showMessageDialog(null, "User Name should be between 10 and 20 characters", "Error", JOptionPane.ERROR_MESSAGE);
                t1.setText("");
            }
            else
            {
                try
                {
                    st=con.createStatement();
                    rs=st.executeQuery("select * from Users where UserName='"+uname+"'");
                    if(rs.next())//if found
                    {
                        JOptionPane.showMessageDialog(null, "User Name already exists", "Error", JOptionPane.ERROR_MESSAGE);
                        t1.setText("");
                    }
                    else
                    {
                        p1.setEditable(true);
                        p1.requestFocus();
                        t1.setEditable(false);
                    }
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void onPasswordVal()
    {
        pword=p1.getText();
        if(pword.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Please enter the Password", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            pword=pword.trim();
            if((pword.length()<10)||(pword.length()>20))
            {
                JOptionPane.showMessageDialog(null, "Password should be between 10 and 20 characters", "Error", JOptionPane.ERROR_MESSAGE);
                p1.setText("");
            }
            else
            {
                p2.setEditable(true);
                p2.requestFocus();
                p1.setEditable(false);
            }
        }
    }

    public void onConPwordVal()
    {
        conpword=p2.getText();
        if(conpword.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Please enter the Confirm Password", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            conpword=conpword.trim();
            if(!conpword.equals(pword))
            {
                JOptionPane.showMessageDialog(null, "Password and Confirm Password do not match", "Error", JOptionPane.ERROR_MESSAGE);
                p2.setText("");
            }
            else
            {
                onRecordAdd();
            }
        }
    }

    public void onRecordAdd()
    {
        int confirm=JOptionPane.showConfirmDialog(null, "Do you want to add this record?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if(confirm==JOptionPane.YES_OPTION)
        {
            try
            {
                ps=con.prepareStatement("insert into UserList values(?, ?)");
                ps.setString(1, uname);
                ps.setString(2, pword);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Record added successfully", "Record Message", JOptionPane.INFORMATION_MESSAGE);
                onUserConfirm();
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else
        {
            onUserConfirm();
        }
    }

    public void onUserConfirm()
    {
        int confirm=JOptionPane.showConfirmDialog(null, "Do you want Continue?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if(confirm==JOptionPane.YES_OPTION)
        {
            onClearAndStart();
        }
        else
        {
            System.exit(0);
        }
    }

    public void onClearAndStart()
    {
        t1.setText("");
        p1.setText("");
        p2.setText("");
        t1.setEditable(true);
        t1.requestFocus();
        p1.setEditable(false);
        p2.setEditable(false);
    }

    public static void main(String args[])
    {
        UserAdd obj=new UserAdd();
        obj.setVisible(true);
    }
}
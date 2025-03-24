import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

class LogIn extends JFrame implements KeyListener
{
    JLabel l1, l2;
    JTextField t1;
    JPasswordField p1;

    Connection con;

    Statement st;
    ResultSet rs;

    String uname, pword, filepword;

    LogIn()//constructor
    {
        super("Log In");

        setSize(322, 300);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(Color.CYAN);

        Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
        Dimension windowsize=getSize();

        this.setBounds((screensize.width-windowsize.width)/2, (screensize.height-windowsize.height)/2, windowsize.width, windowsize.height);

        l1 = new JLabel("User Name");
        add(l1);
        l1.setBounds(60, 100, 100, 30);

        l2 = new JLabel("Password");
        add(l2);
        l1.setBounds(60, 175, 100, 30);

        t1 = new JTextField();
        add(t1);
        t1.setBounds(180, 100, 100, 30);
        t1.addKeyListener(this);

        p1 = new JPasswordField();
        add(p1);
        p1.setEditable(false);
        p1.setBounds(180, 175, 100, 30);
        p1.addKeyListener(this);

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
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
                    if(!(rs.next()))//if not found
                    {
                        JOptionPane.showMessageDialog(null, "User Name does not exist", "Error", JOptionPane.ERROR_MESSAGE);
                        t1.setText("");
                    }
                    else
                    {
                        filepword=rs.getString(2);//Gets the value of the password
                        t1.setEditable(false);
                        p1.setEditable(true);
                        p1.requestFocus();
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
                if(!(filepword.equals(pword)))
                {
                    JOptionPane.showMessageDialog(null, "Password does not match", "Error", JOptionPane.ERROR_MESSAGE);
                    p1.setText("");
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Log In Successful", "Log In Message", JOptionPane.INFORMATION_MESSAGE);

                    // MenuSystem1 mf1=new MenuSystem1();
                    // mf1.setVisible(true);
                    // this.hide();
                }
            }
        }
    }

    public static void main(String args[])
    {
        LogIn log=new LogIn();
        log.setVisible(true);
    }
}
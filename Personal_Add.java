import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

class Personal_Add extends JFrame implements KeyListener
{
    JLabel l1, l2, l3, l4, l5;
    JTextField t1, t2, t3, t4, t5;
    Connection con;
    PreparedStatement ps;
    Statement st;
    ResultSet rs;
    String stno, stname, staddr, sttelno, stemail;

    Personal_Add()
    {
        super("Personal Table Addition Program");
        setSize(320, 320);
        setResizable(false);
        setLayout(null);
        Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
        Dimension windowsize=getSize();

        this.setBounds((screensize.width-windowsize.width)/2,(screensize.height-windowsize.height)/2, windowsize.width,windowsize.height);

        l1=new JLabel("Number");
        add(l1);
        l1.setBounds(30, 50, 100, 25);

        l2=new JLabel("Name");
        add(l2);
        l2.setBounds(30, 100, 100, 25);

        l3=new JLabel("Address");
        add(l3);
        l3.setBounds(30, 150, 100, 25);

        l4=new JLabel("Tel No");
        add(l4);
        l4.setBounds(30, 200, 100, 25);

        l5=new JLabel("Email");
        add(l5);
        l5.setBounds(30, 250, 100, 25);

        t1=new JTextField();
        add(t1);
        t1.setBounds(130, 50, 150, 25);
        t1.addKeyListener(this);

        t2=new JTextField();
        add(t2);
        t2.setBounds(130, 100, 150, 25);
        t2.addKeyListener(this);

        t3=new JTextField();
        add(t3);
        t3.setBounds(130, 150, 150, 25);
        t3.addKeyListener(this);

        t4=new JTextField();
        add(t4);
        t4.setBounds(130, 200, 150, 25);
        t4.addKeyListener(this);

        t5=new JTextField();
        add(t5);
        t5.setBounds(130, 250, 150, 25);
        t5.addKeyListener(this);

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
            onNameVal();
        }
        else
        if((e.getSource()==t3) &&(code==10))
        {
            onAddrVal();
        }
        else
        if((e.getSource()==t4) &&(code==10))
        {
            onTelVal();
        }
        else
        if((e.getSource()==t5) &&(code==10))
        {
            onEmailVal();
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
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null,  e, "Error", JOptionPane.ERROR_MESSAGE);
                }

                try
                {
                    if(rs.next())
                    {
                        JOptionPane.showMessageDialog(null, "Number already exists", "Error", JOptionPane.ERROR_MESSAGE);
                        t1.setText("");
                        t1.requestFocus();
                    }
                    else
                    {
                        t2.setEditable(true);
                        t2.requestFocus();
                        t1.setEditable(false);
                    }
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null,  e, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    public void onNameVal()
    {
        stname=t2.getText();
        if(stname.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Name is Empty", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            t3.setEditable(true);
            t3.requestFocus();
            t2.setEditable(false);
        }
    }

    public void onAddrVal()
    {
        staddr=t3.getText();
        if(staddr.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Address is Empty", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            t4.setEditable(true);
            t4.requestFocus();
            t3.setEditable(false);
        }
    }

    public void onTelVal()//no space allowed, only numbers, 10 digits, 0th index should not be 0
    {
        sttelno=t4.getText();
        int tno;
        if(sttelno.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Tel No is Empty", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            try
            {
                sttelno=sttelno.trim();
                tno=Integer.parseInt(sttelno);
                if(sttelno.length()!=10)
                {
                    JOptionPane.showMessageDialog(null, "Tel No should be 10 digits", "Error", JOptionPane.ERROR_MESSAGE);
                    t4.setText("");
                    t4.requestFocus();
                }
                else
                {
                    if(sttelno.charAt(0)=='0')
                    {
                        JOptionPane.showMessageDialog(null, "Tel No should not start with 0", "Error", JOptionPane.ERROR_MESSAGE);
                        t4.setText("");
                        t4.requestFocus();
                    }
                    else
                    {
                        t5.setEditable(true);
                        t5.requestFocus();
                        t4.setEditable(false);
                    }
                }
            }
            catch(NumberFormatException e)
            {
                JOptionPane.showMessageDialog(null, "Tel No should be numeric", "Error", JOptionPane.ERROR_MESSAGE);
                t4.setText("");
                t4.requestFocus();
            }
        }   
    }

    public void onEmailVal()//no space allowed, @ should be there, @>0, . should be there, .>0
    {
        stemail=t5.getText();
        if(stemail.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Email is Empty", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else if(stemail.indexOf('@')<0||stemail.indexOf('.')<0)
        {
            JOptionPane.showMessageDialog(null, "Email should contain @ and .", "Error", JOptionPane.ERROR_MESSAGE);
            t5.setText("");
            t5.requestFocus();
        }
        else
        {
            int x, y;
            y=0;
            for(x=0; x<stemail.length();x++)
            {
                if(stemail.charAt(x)=='@')
                {
                    y++;
                }
            }
            if(y>1)
            {
                JOptionPane.showMessageDialog(null, "Email should contain only one @", "Error", JOptionPane.ERROR_MESSAGE);
                t5.setText("");
                t5.requestFocus();
            }
            else
            {
                onRecordAdd();
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
                ps=con.prepareStatement("insert into Personal values(?, ?, ?, ?, ?)");
                ps.setString(1, stno);
                ps.setString(2, stname);
                ps.setString(3, staddr);
                ps.setString(4, sttelno);
                ps.setString(5, stemail);

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
        t5.setText("");
        t1.setEditable(true);
        t1.requestFocus();
        t5.setEditable(false);
    }
    public static void main (String args[])
    {
        Personal_Add add=new Personal_Add();
        add.setVisible(true);
    }
}
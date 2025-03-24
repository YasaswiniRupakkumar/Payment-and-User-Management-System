import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

class Personal_Edit extends JFrame implements KeyListener, ActionListener
{
    JLabel l1, l2, l3, l4, l5;
    JTextField t1, t2, t3, t4, t5;
    Connection con;
    PreparedStatement ps;
    Statement st;
    ResultSet rs;
    String stno, stname, staddr, sttelno, stemail;
    JButton b1, b2;
    boolean r_val1, r_val2, r_val3, r_val4;

    Personal_Edit()
    {
        super("Personal Table Editing Program");
        setSize(320, 440);
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

        b1=new JButton("Edit");
        add(b1);
        b1.setBounds(80, 300, 100, 25);
        b1.addActionListener(this);

        b2=new JButton("Exit");
        add(b2);
        b2.setBounds(200, 300, 100, 25);
        b2.addActionListener(this);

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
            onValidTest();
        }
        else
        if(e.getSource()==b2)
        {
            onExit();
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
                    if(!(rs.next()))
                    {
                        JOptionPane.showMessageDialog(null, "Number already exists", "Error", JOptionPane.ERROR_MESSAGE);
                        t1.setText("");
                        t1.requestFocus();
                    }
                    else
                    {
                        t2.setText(rs.getString(2));
                        t3.setText(rs.getString(3));
                        t4.setText(rs.getString(4));
                        t5.setText(rs.getString(5));
                        t1.setEditable(false);
                        t2.setEditable(true);
                        t3.setEditable(true);
                        t4.setEditable(true);
                        t5.setEditable(true);
                    }
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null,  e, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    
    public boolean onNameVal()
    {
        stname=t2.getText();
        if(stname.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Name is Empty", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else
        {
            t3.requestFocus(true);
            return true;
        }
    }

    public boolean onAddrVal()
    {
        staddr=t3.getText();
        if(staddr.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Address is Empty", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else
        {
            t4.setEditable(true);
            return true;
        }
    }

    public boolean onTelVal()//no space allowed, only numbers, 10 digits, 0th index should not be 0
    {
        sttelno=t4.getText();
        int tno;
        if(sttelno.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Tel No is Empty", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
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
                    return false;
                }
                else
                {
                    if(sttelno.charAt(0)=='0')
                    {
                        JOptionPane.showMessageDialog(null, "Tel No should not start with 0", "Error", JOptionPane.ERROR_MESSAGE);
                        t4.setText("");
                        t4.requestFocus();
                        return false;
                    }
                    else
                    {
                        t5.setEditable(true);
                        t5.requestFocus();
                        t4.setEditable(false);
                        return true;
                    }
                }
            }
            catch(NumberFormatException e)
            {
                JOptionPane.showMessageDialog(null, "Tel No should be numeric", "Error", JOptionPane.ERROR_MESSAGE);
                t4.setText("");
                return false;
            }
        }   
    }

    public boolean onEmailVal()//no space allowed, @ should be there, @>0, . should be there, .>0
    {
        stemail=t5.getText();
        if(stemail.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Email is Empty", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if(stemail.indexOf('@')<0||stemail.indexOf('.')<0)
        {
            JOptionPane.showMessageDialog(null, "Email should contain @ and .", "Error", JOptionPane.ERROR_MESSAGE);
            t5.setText("");
            t5.requestFocus();
            return false;
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
                return false;
            }
            else
            {
                onEditRecord();
                return true;
            }
        }
    }

    public void onValidTest()
    {
        r_val1=onNameVal();
        if(r_val1==true)
        {
            r_val2=onAddrVal();
        }
        else if (r_val1==false)
        {
            t2.requestFocus();
        }

        r_val2=onAddrVal();
        if(r_val2==true)
        {
            r_val3=onTelVal();
        }
        else if (r_val2==false)
        {
            t3.requestFocus();
        }

        r_val3=onTelVal();
        if(r_val3==true)
        {
            r_val4=onEmailVal();
        }
        else if (r_val3==false)
        {
            t4.requestFocus();
        }

        r_val4=onEmailVal();
        if(r_val4==true)
        {
            onEditRecord();
        }
        else if (r_val4==false)
        {
            t5.requestFocus();
        }
    }

    public void onEditRecord()
    {
        int confirm=JOptionPane.showConfirmDialog(null, "Do you want to edit this record?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(confirm==JOptionPane.YES_OPTION)
        {
            try
            {
                ps=con.prepareStatement("update Personal set Name="+t2.getText()+", Address="+t3.getText()+", TelNo="+t4.getText()+", Email="+t5.getText()+" where Number="+t1.getText()+"");
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Record Updated", "Record Update", JOptionPane.INFORMATION_MESSAGE);
                confirm=JOptionPane.showConfirmDialog(null, "Do you want to continue", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(confirm==JOptionPane.YES_OPTION)
                {
                    t1.setText("");
                    t2.setText("");
                    t3.setText("");
                    t4.setText("");
                    t5.setText("");

                    t1.setEditable(true);
                    t2.setEditable(false);
                    t3.setEditable(false);
                    t4.setEditable(false);
                    t5.setEditable(false);
                    t1.requestFocus();
                }
                else
                {
                    onExit();
                }
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else
        {
            onExit();
        }
    }

    public void onExit()
    {
        this.hide();
    }
    public static void main (String args[])
    {
        Personal_Edit edit=new Personal_Edit();
        edit.setVisible(true);
    }
}
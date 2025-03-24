import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class Payment_Add extends JFrame implements KeyListener, ActionListener
{
    JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9;
    JTextField t1, t2, t3;
    JButton b1;

    Connection con;
    PreparedStatement ps;

    Statement st;
    ResultSet rs;

    String stno, subject;
    double fees;

    Payment_Add()
    {
        super("Payment Table Addition Program");
        setSize(320, 480);
        setLayout(null);
        setResizable(false);
        Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
        Dimension windowsize=getSize();

        this.setBounds((screensize.width-windowsize.width)/2,(screensize.height-windowsize.height)/2, windowsize.width,windowsize.height);
        
        l1=new JLabel("Number");
        add(l1);
        l1.setBounds(30, 50, 100, 25);

        l2=new JLabel("Name");
        add(l2);
        l2.setBounds(30, 100, 100, 25);

        l3=new JLabel("Subject");
        add(l3);
        l3.setBounds(30, 150, 100, 25);

        l4=new JLabel("Fees");
        add(l4);
        l4.setBounds(30, 200, 100, 25);

        l5=new JLabel("Paid Amount");
        add(l5);
        l5.setBounds(30, 250, 100, 25);

        l6=new JLabel("Due Amount");
        add(l6);
        l6.setBounds(30, 300, 100, 25);

        t1=new JTextField();
        add(t1);
        t1.setBounds(190, 50, 100, 25);
        t1.addKeyListener(this);

        l7=new JLabel();
        add(l7);
        l7.setBounds(190, 100, 100, 25);

        t2=new JTextField();
        add(t2);
        t2.setBounds(190, 150, 100, 25);
        t2.setEditable(false);
        t2.addKeyListener(this);

        t3=new JTextField();
        add(t3);
        t3.setBounds(190, 200, 100, 25);
        t3.setEditable(false);
        t3.addKeyListener(this);

        l8=new JLabel();
        add(l8);
        l8.setBounds(190, 250, 100, 25);

        l9=new JLabel();
        add(l9);
        l9.setBounds(190, 300, 100, 25);

        b1=new JButton("Add Record");
        add(b1);
        b1.setBounds(40, 400, 340, 20);
        b1.addActionListener(this);

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Student7", "root", "");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e, "Database Connection Error", JOptionPane.ERROR_MESSAGE);
            this.hide();
        }
    
    }

    public void keyPressed(KeyEvent e)
    {
        int code=e.getKeyChar();
        if((e.getSource()==t1)&&(code==10))
        {
            onNumVal();
        }
        else
        if((e.getSource()==t2)&&(code==10))
        {
            onSubjectVal();
        }
        else
        if((e.getSource()==t3)&&(code==10))
        {
            onFeesVal();
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
            JOptionPane.showMessageDialog(null, "Please enter the number", "Error", JOptionPane.ERROR_MESSAGE);
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
                    rs=st.executeQuery("select * from personal where Number="+stno);
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
                }
            

                try
                {
                    if(!(rs.next()))
                    {
                        JOptionPane.showMessageDialog(null, "Record not found", "Error", JOptionPane.ERROR_MESSAGE);
                        t1.setText("");
                        t1.requestFocus();
                    }
                    else
                    {
                        l7.setText(rs.getString(2));
                        t2.setEditable(true);
                        t1.setEditable(false);
                        t2.requestFocus();
                    }
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void onSubjectVal()
    {
        subject=t2.getText();
        if(subject.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Please enter the subject", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            t3.setEditable(true);
            t2.setEditable(false);
            t3.requestFocus();
        }
    }

    public void onFeesVal()
    {
        String sfees;
        sfees=t3.getText();
        if(sfees.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Please enter the fees", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            try
            {
                fees=Double.parseDouble(sfees);
                if(fees<0)
                {
                    JOptionPane.showMessageDialog(null, "Fees should be greater than 0", "Error", JOptionPane.ERROR_MESSAGE);
                    t3.setText("");
                    t3.requestFocus();
                }
                else
                {
                    l8.setText("0");
                    l9.setText(String.valueOf(fees));
                }
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
                t3.setText("");
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
                ps=con.prepareStatement("insert into Payments values(?, ?, ?, ?, ?, ?)");
                ps.setString(1, stno);
                ps.setString(2, l7.getText());
                ps.setString(3, subject);
                ps.setDouble(4, fees);
                ps.setDouble(5, Double.parseDouble(l8.getText()));
                ps.setDouble(6, Double.parseDouble(l9.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Record added", "Addition Message", JOptionPane.INFORMATION_MESSAGE);
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
        int confirm=JOptionPane.showConfirmDialog(null, "Do you want to continue", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
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
        t2.setText("");
        t3.setText("");
        l7.setText("");
        l8.setText("");
        l9.setText("");
        t1.setEditable(true);
        t1.requestFocus();
        t2.setEditable(false);
        t3.setEditable(false);
    }

    public static void main(String args[])
    {
        Payment_Add add1=new Payment_Add();
        add1.setVisible(true);
    }
}//There are huge errors
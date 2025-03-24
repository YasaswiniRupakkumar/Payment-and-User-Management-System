import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class Payment_View extends JFrame implements KeyListener, ActionListener
{
    JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11;
    JTextField t1;
    JButton b1;

    Connection con;

    Statement st;
    ResultSet rs;

    String stno;

    Payment_View()
    {
        super("Payment View");
        setSize(380, 500);
        setLayout(null);
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

        l3=new JLabel("Subject");
        add(l3);
        l3.setBounds(30, 110, 100, 25);

        l4=new JLabel("Fees");
        add(l4);
        l4.setBounds(30, 150, 100, 25);

        l5=new JLabel("Paid Amount");
        add(l5);
        l5.setBounds(30, 190, 100, 25);

        l6=new JLabel("Due Amount");
        add(l6);
        l6.setBounds(30, 230, 100, 25);

        t1=new JTextField();
        add(t1);
        t1.setBounds(190, 30, 100, 25);
        t1.addKeyListener(this);

        l7=new JLabel();
        add(l7);
        l7.setBounds(190, 70, 100, 25);

        l8=new JLabel();
        add(l8);
        l8.setBounds(190, 110, 100, 25);

        l9=new JLabel();
        add(l9);
        l9.setBounds(190, 150, 100, 25);

        l10=new JLabel();
        add(l10);
        l10.setBounds(190, 190, 100, 25);

        l11=new JLabel();
        add(l11);
        l11.setBounds(190, 230, 100, 25);

        b1=new JButton("View More");
        add(b1);
        b1.setBounds(80, 280, 100, 25);
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
        if ((e.getSource()==t1)&&(code==10))
        {
            onNumVal();
        }
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if((e.getSource()==b1))
        {
            onViewMore();
        }
    }

    public void keyReleased(KeyEvent e)
    {

    }

    public void keyTyped(KeyEvent e)
    {

    }

    public void onNumVal()
    {
        stno=t1.getText();
        if(stno.equals(""))
        JOptionPane.showMessageDialog(null, "Number is not entered", "Error", JOptionPane.ERROR_MESSAGE);
        else
        {
            stno=stno.trim();
            if(stno.equals(""))
            {
                JOptionPane.showMessageDialog(null, "Number is not entered", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                stno=stno.trim();
                if(stno.length()!=5)
                {
                    JOptionPane.showMessageDialog(null, "The number length should be 5", "Error Message", JOptionPane.ERROR_MESSAGE);
                    t1.setText("");
                }
                else
                {
                    try
                    {
                        st=con.createStatement();
                        rs=st.executeQuery("SELECT * FROM Payments WHERE Number='"+stno+"'");
                        
                        if(!(rs.next()))
                        {
                            JOptionPane.showMessageDialog(null, "The number does not exist", "Error Message", JOptionPane.ERROR_MESSAGE);
                            t1.setText("");
                            t1.requestFocus();
                        }
                        else
                        {
                            l7.setText(rs.getString(2));
                            l8.setText(rs.getString(3));
                            l9.setText(rs.getString(4));
                            l10.setText(rs.getString(5));
                            l11.setText(rs.getString(6));
                        }
                    }
                    catch(Exception ee)
                    {
                        JOptionPane.showMessageDialog(null, ee, "Error Message", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        }

    }

    public void onViewMore()
    {
        int confirm=JOptionPane.showConfirmDialog(null, "Do you want to view more", "Confirmation", JOptionPane.YES_NO_OPTION);

        if(confirm==JOptionPane.YES_OPTION)
        {
            t1.setText("");
            t1.requestFocus();
            l7.setText("");
            l8.setText("");
            l9.setText("");
            l10.setText("");
            l11.setText("");
        }
        else
        {
            this.hide();
        }
    }




public static void main(String args[])
{
    Payment_View view1=new Payment_View();
    view1.setVisible(true);
}
}
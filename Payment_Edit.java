import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

class Payment_Edit extends JFrame implements KeyListener, ActionListener
{
    JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12;
    JTextField t1, t2;
    JButton b1;

    Connection con;
    PreparedStatement ps1, ps2;

    Statement st;
    ResultSet rs;

    String stno;
    double fees, paid_amt, paying_amt, due_amt;


    Payment_Edit()
    {
        super("Payment Edit");
        setSize(380, 500);
        setLayout(null);
        setResizable(false);
        setLayout(null);
        Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
        Dimension windowsize=getSize();

        this.setBounds((screensize.width-windowsize.width)/2,(screensize.height-windowsize.height)/2, windowsize.width,windowsize.height);

        l1=new JLabel("Number");
        add(l1);
        l1.setBounds(80, 50, 100, 25);

        l2=new JLabel("Name");
        add(l2);
        l2.setBounds(80, 100, 100, 25);

        l3=new JLabel("Subject");
        add(l3);
        l3.setBounds(80, 150, 100, 25);

        l4=new JLabel("Fees");
        add(l4);
        l4.setBounds(80, 200, 100, 25);

        l5=new JLabel("Paying Amount");
        add(l5);
        l5.setBounds(80, 250, 100, 25);

        l6=new JLabel("Paid Amount");
        add(l6);
        l6.setBounds(80, 300, 100, 25);

        l7=new JLabel("Due Amount");
        add(l7);
        l7.setBounds(80, 350, 100, 25);

        t1=new JTextField();
        add(t1);
        t1.setBounds(200, 50, 100, 25);
        t1.addKeyListener(this);

        l8=new JLabel();
        add(l8);
        l8.setBounds(200, 100, 100, 25);

        l9=new JLabel();
        add(l9);
        l9.setBounds(200, 150, 100, 25);

        l10=new JLabel();
        add(l10);
        l10.setBounds(200, 200, 100, 25);

        t2=new JTextField();
        add(t2);
        t2.setBounds(200, 250, 100, 25);
        t2.addKeyListener(this);

        l11=new JLabel();
        add(l11);
        l11.setBounds(200, 300, 100, 25);

        l12=new JLabel();
        add(l12);
        l12.setBounds(200, 350, 100, 25);

        b1=new JButton("Edit Record");
        add(b1);
        b1.setBounds(70, 390, 115, 25);
        b1.addActionListener(this);

        try{
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Student7","root","");
            JOptionPane.showMessageDialog(null, "Database connection succeeded", "Connection Message", JOptionPane.ERROR_MESSAGE);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource()==b1){
            onEdit();
        }
    }

    public void keyPressed(KeyEvent e){
    int code=e.getKeyChar();

    if((code==10)&&(e.getSource()==t1)){
        onNumVal();
    }
    else
    if((code==10)&&(e.getSource()==t2)){
        onPayingAmtVal();
    }
    }
    

    public void keyTyped(KeyEvent e){

    }

    public void keyReleased(KeyEvent e){

    }

    public void onNumVal(){
        stno=t1.getText();
        if(stno.equals("")){
            JOptionPane.showMessageDialog(null, "Number is not entered", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else{
            stno=stno.trim();
            if(stno.length()!=5)
            {
                JOptionPane.showMessageDialog(null, "Number should be 5 digits long", "Error message", JOptionPane.ERROR_MESSAGE);
                t1.setText("");
                t1.requestFocus();
            }
            else
            {
                try
                {
                    st=con.createStatement();
                    rs=st.executeQuery("SELECT * FROM payments WHERE Number="+stno+"");

                    if(!(rs.next()))
                    {
                        JOptionPane.showMessageDialog(null, "Number doesn't exist", "Error message", JOptionPane.ERROR_MESSAGE);
                        t1.setText("");
                        t1.requestFocus();
                    }
                    else
                    {
                        l8.setText(rs.getString(2));//name
                        l9.setText(rs.getString(3));//subject
                        l10.setText(rs.getString(4));//fees                             100000.00
                        l11.setText(rs.getString(5));//paid amount                      0.00        ----->25000.00

                        fees=Double.parseDouble(l10.getText());//fees                   100000.00
                        double file_paid_amount=Double.parseDouble(rs.getString(5));//  0.00


                        file_paid_amount=Double.parseDouble(l11.getText());//            0.00
                        due_amt=fees-file_paid_amount;//                                100000-0.00
                        l12.setText(String.valueOf(due_amt));

                        t2.setEditable(true);
                        t2.requestFocus();
                        t1.setEditable(false);
                    }
                }
                catch(Exception ee)
                {
                    JOptionPane.showMessageDialog(null, ee, "Error Message", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void onPayingAmtVal()
    {
        String s_paying_amt;
        s_paying_amt=t2.getText();      //25000
        if(s_paying_amt.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Please Enter Paying Amount", "Error", JOptionPane.ERROR_MESSAGE);
            t2.setText("");
        }
        else
        {
            try{
                paying_amt=Double.parseDouble(s_paying_amt);
                if(paying_amt<=0)
                {
                    JOptionPane.showMessageDialog(null, "Payment amount cannot be a zero or negative value", "Error", JOptionPane.ERROR_MESSAGE);
                    t2.setText("");
                }
                else
                {
                    double due_fee=Double.parseDouble(l12.getText());
                    if(paying_amt>due_fee)
                    {
                        JOptionPane.showMessageDialog(null, "Paymenty amount cannot exceed the due amount"+due_amt+"", "Error", JOptionPane.ERROR_MESSAGE);
                        t2.setText("");
                    }
                    else
                    {
                        due_amt=due_amt-paying_amt;         //100000-25000=75000
                        paid_amt=paid_amt+paying_amt;       //0.00+25000=25000
                        l11.setText(String.valueOf(paid_amt));
                        l12.setText(String.valueOf(due_amt));
                    }
                }
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void onEdit()
    {
        int confirm=JOptionPane.showConfirmDialog(null, "Do you want to update this payment", "Edit Message", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(confirm==JOptionPane.YES_OPTION)
        {
            try
            {
                ps1=con.prepareStatement("insert into payment_transaction values(?,?,?,?,?,?,?)");
                ps1.setString(1, stno);
                ps1.setString(2, l8.getText());//name
                ps1.setString(3, l9.getText());//subject
                ps1.setString(4, l10.getText());//fees
                ps1.setDouble(5, paying_amt);
                ps1.setDouble(6, paid_amt);
                ps1.setDouble(7, due_amt);
                ps1.executeUpdate();

                ps2=con.prepareStatement("UPDATE Payments SET Paid_Amount="+l11.getText()+", Due_Amount="+l12.getText()+"WHERE Number="+t1.getText()+"");
                ps2.executeUpdate();

                JOptionPane.showMessageDialog(null, "Record Updated", "Edit Message", JOptionPane.INFORMATION_MESSAGE);

                confirm=JOptionPane.showConfirmDialog(null, "Do you have any payments", "Edit Message", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if(confirm==JOptionPane.YES_OPTION)
                {
                    t1.setText("");
                    t2.setText("");
                    l8.setText("");
                    l9.setText("");
                    l10.setText("");
                    l11.setText("");
                    l12.setText("");

                    t1.setEditable(true);
                    t2.setEditable(false);
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

    public static void main(String args[])
    {
        Payment_Edit edit1=new Payment_Edit();
        edit1.setVisible(true);
    }
}
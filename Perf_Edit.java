import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

class Perf_Edit extends JFrame implements KeyListener, ActionListener
{
	JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12;
    JTextField t1, t2, t3, t4;
	JButton b1, b2;

    Connection con;
    PreparedStatement ps;

    Statement st;
    ResultSet rs;

    String stno, grade;
	int mar1, mar2, mar3, total;
	double average;

    boolean r_val1, r_val2, r_val3;

	Perf_Edit()
	{
		super("Performance Table Editing Program");
		setSize(380,500);
		setResizable(false);
		setLayout(null);
		Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
		Dimension windowsize= getSize();

		this.setBounds((screensize.width - windowsize.width)/2,
			(screensize.height - windowsize.height)/2,windowsize.width,windowsize.height);

		l1=new JLabel("Number");
        add(l1);
        l1.setBounds(60, 50, 100, 25);

        l2=new JLabel("Name");
        add(l2);
        l2.setBounds(60, 100, 100, 25);

        l3=new JLabel("Marks 1");
        add(l3);
        l3.setBounds(60, 150, 100, 25);

        l4=new JLabel("Marks 2");
        add(l4);
        l4.setBounds(60, 200, 100, 25);

        l5=new JLabel("Marks 3");
        add(l5);
        l5.setBounds(60, 250, 100, 25);

		l6=new JLabel("Total");
		add(l6);
		l5.setBounds(60, 300, 100, 25);

		l7=new JLabel("Average");
		add(l6);
		l5.setBounds(60, 350, 100, 25);

		l8=new JLabel("Grade");
		add(l6);
		l5.setBounds(60, 400, 100, 25);

        t1=new JTextField();
        add(t1);
        t1.setBounds(190, 50, 100, 25);
        t1.addKeyListener(this);

		l9=new JLabel();
		add(l9);
		l9.setBounds(190, 100, 100, 25);

        t2=new JTextField();
        add(t2);
        t2.setBounds(190, 150, 100, 25);
        t2.addKeyListener(this);

        t3=new JTextField();
        add(t3);
        t3.setBounds(190, 200, 100, 25);
        t3.addKeyListener(this);

        t4=new JTextField();
        add(t4);
        t4.setBounds(190, 250, 100, 25);
        t4.addKeyListener(this);

		l10=new JLabel();
		add(l10);
		l10.setBounds(190, 300, 100, 25);

		l11=new JLabel();
		add(l11);
		l11.setBounds(190, 350, 100, 25);

		l12=new JLabel();
		add(l12);
		l12.setBounds(190, 400, 100, 25);

		b1=new JButton("Edit Record");
        add(b1);
        b1.setBounds(80, 450, 115, 25);
        b1.addActionListener(this);

        b2=new JButton("Exit");
        add(b2);
        b2.setBounds(205, 450, 115, 25);
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
        if(e.getSource()==t1)
        {
            onNumVal();
        }
        
        if(e.getSource()==t2)
		if(code==10)
        {
            onMarks1Val();
        }
        
        if(e.getSource()==t3)
		if(code==10)
        {
            onMarks2Val();
        }
        
        if(e.getSource()==t4)
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
                    rs=st.executeQuery("select * from Performance where Number='"+stno+"'");
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
                        l9.setText(rs.getString(2));
						t2.setText(rs.getString(3));
						t3.setText(rs.getString(4));
						t4.setText(rs.getString(5));
						l10.setText(rs.getString(6));
						l11.setText(rs.getString(7));
						l12.setText(rs.getString(8));

						t1.setEditable(false);
						t2.setEditable(true);
						t3.setEditable(true);
						t4.setEditable(true);
						t2.requestFocus();
                    }
                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null,  e, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

	public boolean onMarks1Val()
    {
        String sm1;
        sm1=t2.getText();
        if(sm1.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Marks1 is Empty", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
        }
        else
        {
            try
            {
                mar1=Integer.parseInt(sm1);
                if((mar1<0)||(mar1>100))
                {
                    JOptionPane.showMessageDialog(null, "Marks1 should be between 0 and 100", "Error", JOptionPane.ERROR_MESSAGE);
                    t3.setText("");
					return false;
                }
                else
                {
                    t3.setEditable(true);
                    t3.requestFocus();
                    t2.setEditable(false);
					return true;
                }
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null, "Marks1 should be numeric", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
    }

    public boolean onMarks2Val()
    {
        String sm2;
        sm2=t3.getText();
        if(sm2.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Marks2 is Empty", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
        }
        else
        {
            try
            {
                mar2=Integer.parseInt(sm2);
                if((mar2<0)||(mar2>100))
                {
                    JOptionPane.showMessageDialog(null, "Marks2 should be between 0 and 100", "Error", JOptionPane.ERROR_MESSAGE);
                    t3.setText("");
					return false;
                }
                else
                {
                    t4.setEditable(true);
                    t4.requestFocus();
                    t3.setEditable(false);
					return true;
                }
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null, "Marks2 should be numeric", "Error", JOptionPane.ERROR_MESSAGE);
                t3.setText("");
                t3.requestFocus();
				return false;
            }
        }
    }

    public boolean onMarks3Val()
    {
        String sm3;
        sm3=t4.getText();
        if(sm3.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Marks3 is Empty", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
        }
        else
        {
            try
            {
                mar3=Integer.parseInt(sm3);
                if((mar3<0)||(mar3>100))
                {
                    JOptionPane.showMessageDialog(null, "Marks3 should be between 0 and 100", "Error", JOptionPane.ERROR_MESSAGE);
                    t4.setText("");
					return false;
                }
                else
                {
                    total=mar1+mar2+mar3;
                    average=total/3;
                    if(average>80)
                    grade="Distinction";
                    else if(average>70)
                    grade="Credit";
                    else if(average>60)
                    grade="Merit Pass";
                    else
                    grade="Fail";

                    l10.setText(String.valueOf(total));
                    l11.setText(String.valueOf(average));
                    l12.setText(grade);
                    t4.setEditable(false);
					return true;
                }
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null, "Marks3 should be numeric", "Error", JOptionPane.ERROR_MESSAGE);
                t4.setText("");
                t4.requestFocus();
				return false;
            }
        }
    }

	public void onValidTest()
	{
		r_val1=onMarks1Val();
		if(r_val1==true)
		{
			r_val2=onMarks2Val();
		}
		else if (r_val1==false)
		{
			t2.requestFocus();
		}

		if(r_val2==true)
		{
			r_val3=onMarks3Val();
		}
		else if (r_val2==false)
		{
			t3.requestFocus();
		}

		if(r_val3==false)
		{
			t4.requestFocus();
		}
		else if ((r_val1==true)&&(r_val2==true)&&(r_val3==true))
		{
			onEditRecord();
		}
	}

    public void onEditRecord()
    {
        int confirm=JOptionPane.showConfirmDialog(null, "Do you want to edit this record?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(confirm==JOptionPane.YES_OPTION)
        {
            try
            {
                ps=con.prepareStatement("update Performance set Marks1="+t2.getText()+", Marks2="+t3.getText()+", Marks3="+t4.getText()+", Total="+l10.getText()+", Average="+l11.getText()+" where Number="+t1.getText()+"");
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Record Updated", "Record Update", JOptionPane.INFORMATION_MESSAGE);
                confirm=JOptionPane.showConfirmDialog(null, "Do you want to continue", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(confirm==JOptionPane.YES_OPTION)
                {
                    t1.setText("");
                    t2.setText("");
                    t3.setText("");
                    t4.setText("");
                    l10.setText("");
					l11.setText("");
					l12.setText("");

                    t1.setEditable(true);
                    t2.setEditable(false);
                    t3.setEditable(false);
                    t4.setEditable(false);
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
	Perf_Edit inq1 = new Perf_Edit();
	inq1.setVisible(true);
	}
}
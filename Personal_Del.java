import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

class Personal_Del extends JFrame //implements KeyListener
{
    Personal_Del()
    {
        super("Personal Table Deletion Program");
        setSize(380, 380);
        setResizable(false);
        setLayout(null);
        Dimension screensize=Toolkit.getDefaultToolkit().getScreenSize();
        Dimension windowsize=getSize();

        this.setBounds((screensize.width-windowsize.width)/2,(screensize.height-windowsize.height)/2, windowsize.width,windowsize.height);
    }

    public static void main (String args[])
    {
        Personal_Del del=new Personal_Del();
        del.setVisible(true);
    }
}
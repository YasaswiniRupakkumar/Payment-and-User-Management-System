import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class MenuSystem extends JFrame implements ActionListener
{
    JMenuBar mb;
    JMenu mnupers, mnuperf, mnuexit, mnupay;
    JMenuItem mnupers_add, mnupers_edit, mnupers_del, mnupers_inq, mnuquit, mnuperf_add, mnuperf_edit, mnuperf_view, mnupay_add, mnupay_edit, mnupay_view;


    MenuSystem()//constructor
    {
        super("Menu");

        setSize(322, 300);
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screensize);
        setResizable(false);

        mb = new JMenuBar();
        setJMenuBar(mb);

        mnupers = new JMenu("Personal");
        mb.add(mnupers);

        mnupers_add = new JMenuItem("Add");
        mnupers.add(mnupers_add);
        mnupers_add.addActionListener(this);

        mnupers_edit = new JMenuItem("Edit");
        mnupers.add(mnupers_edit);
        mnupers_edit.addActionListener(this);

        mnupers_del = new JMenuItem("Delete");
        mnupers.add(mnupers_del);
        mnupers_del.addActionListener(this);

        mnupers_inq = new JMenuItem("View");
        mnupers.add(mnupers_inq);
        mnupers_inq.addActionListener(this);

        mnuperf = new JMenu("Performance");
        mb.add(mnuperf);

        mnuperf_add = new JMenuItem("Add");
        mnuperf.add(mnuperf_add);
        mnuperf_add.addActionListener(this);

        mnuperf_edit = new JMenuItem("Edit");
        mnuperf.add(mnuperf_edit);
        mnuperf_edit.addActionListener(this);

        mnuperf_view = new JMenuItem("View");
        mnuperf.add(mnuperf_view);
        mnuperf_view.addActionListener(this);

        mnupay = new JMenu("Payment");
        mb.add(mnupay);
        
        mnupay_add = new JMenuItem("Add");
        mnupay.add(mnupay_add);
        mnupay_add.addActionListener(this);

        mnupay_edit = new JMenuItem("Edit");
        mnupay.add(mnupay_edit);
        mnupay_edit.addActionListener(this);

        mnupay_view = new JMenuItem("View");
        mnupay.add(mnupay_view);
        mnupay_view.addActionListener(this);

        mnuexit= new JMenu("Exit");
        mb.add(mnuexit);

        mnuquit = new JMenuItem("Quit");
        mnuexit.add(mnuquit);
        mnuquit.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e)
    {
        Object obj= e.getSource();

        if(obj==mnupers_add)
        {
            Personal_Add add=new Personal_Add();
            add.setVisible(true);
        }
        else
        if(obj==mnupers_edit)
        {
            Personal_Edit edit=new Personal_Edit();
            edit.setVisible(true);
        }
        else
        if(obj==mnupers_del)
        {
            Personal_Del del=new Personal_Del();
            del.setVisible(true);
        }
        else
        if(obj==mnupers_inq)
        {
            Personal_View inq=new Personal_View();
            inq.setVisible(true);
        }
        else
        if(obj==mnuperf_add)
        {
            Perf_Add add=new Perf_Add();
            add.setVisible(true);
        }
        else
        if(obj==mnuperf_edit)
        {
            Perf_Edit edit=new Perf_Edit();
            edit.setVisible(true);
        }
        else 
        if(obj==mnuperf_view)
        {
            Perf_View view=new Perf_View();
            view.setVisible(true);
        }
        else
        if(obj==mnupay_add)
        {
            Payment_Add add=new Payment_Add();
            add.setVisible(true);
        }
        else
        if(obj==mnupay_edit)
        {
            Payment_Edit edit=new Payment_Edit();
            edit.setVisible(true);
        }
        else
        if(obj==mnupay_view)
        {
            Payment_View view=new Payment_View();
            view.setVisible(true);
        }
        else
        if(obj==mnuquit)
        {
            Exit xxt= new Exit();
        }
    }

    public static void main (String args[])
    {
        MenuSystem menu1= new MenuSystem();
        menu1.setVisible(true);
    }

}

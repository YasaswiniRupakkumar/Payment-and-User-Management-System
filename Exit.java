import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

class Exit extends JFrame {
    Exit() {
      int confirm=JOptionPane.showConfirmDialog(null, "Do you want to Exit", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

      if(confirm==JOptionPane.YES_OPTION)
      {
        System.exit(0);
      }
   }

   public static void main(String[] var0) {
    Exit exit = new Exit();
   }
}

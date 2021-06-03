import java.awt.Dimension;
 
import javax.swing.JFrame;
 
public class Window {
 
    public Window(int width, int height, String title, Main main) {
       
        JFrame frame = new JFrame(title);
        frame.setPreferredSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
       
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
       // frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        //frame.setUndecorated(true);
        frame.setVisible(true);
        
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(main);
    }
}
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{

	public boolean keys[] = new boolean[10];

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        if(key == KeyEvent.VK_D) { keys[0] = true; }
        if(key == KeyEvent.VK_A) { keys[1] = true; }
        if(key == KeyEvent.VK_W) { keys[2] = true; }
        if(key == KeyEvent.VK_S) { keys[3] = true; }
        
        if(key == KeyEvent.VK_SPACE) {keys[4]  = true; }
        if(key == KeyEvent.VK_E) {keys[5] = true; }
    }
	
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
       
        if(key == KeyEvent.VK_D) { keys[0] = false; }
        if(key == KeyEvent.VK_A) { keys[1] = false; }
        if(key == KeyEvent.VK_W) { keys[2] = false; }
        if(key == KeyEvent.VK_S) { keys[3] = false; }
   
        if(key == KeyEvent.VK_SPACE) {keys[4]  = false; }
        if(key == KeyEvent.VK_E) {keys[5] = false; }
    }
    
}

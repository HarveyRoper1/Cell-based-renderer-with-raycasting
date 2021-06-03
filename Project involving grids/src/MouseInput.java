import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class MouseInput extends MouseMotionAdapter{
	
	public int mx;
	public int my;

	public boolean mousePressed=false;

    public void mouseDragged(MouseEvent e) {
		mx = e.getX();
		my = e.getY();
		
	}

	public void mouseMoved(MouseEvent e) {
		mx = e.getX();
		my = e.getY();
		
	}

}
//
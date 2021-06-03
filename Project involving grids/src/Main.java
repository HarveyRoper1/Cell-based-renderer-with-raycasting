import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
 
public class Main extends Canvas implements Runnable, KeyListener, MouseListener{
 
    public static int WIDTH = 800;//1920;
    public static int HEIGHT = 800;//1080;
    public String title = "...";
   
    private Thread thread;
   
    private boolean isRunning = false;
   
    //Instances

   
    private KeyInput input;
    private MouseInput minput;
    private World world;
  
    public Main() {
       
        init();
        new Window(WIDTH-32, HEIGHT-9, title, this);
        start();
        
        
    }
   
    public void init() {
    	input = new KeyInput();
    	this.addKeyListener(input);
    	
    	minput = new MouseInput();
    	this.addMouseMotionListener(minput);
    	
    	world = new World(minput);
    }
   
    private synchronized void start() {
    	
        if(isRunning) return;
       
        thread = new Thread(this);
        thread.start();
        isRunning = true;
    }
   
    private synchronized void stop() {
        if(!isRunning) return;
       
        try {
            thread.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        isRunning = false;
    }
   
    //gameloop
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        while(isRunning){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;
                   
            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                //System.out.println("FPS: " + frames + " TICKS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
        stop();
    }
 
    public void tick() {
    
    	world.tick();
    }
   
    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
       
        ///
        
	    g.setColor(Color.black);
	    g.fillRect(0, 0, WIDTH, HEIGHT);
	   
	    
	    //Color daylight = new Color(Math.min(800-world.circleY, 50), Math.min(800-world.circleY, 50), Math.min(800-(world.circleY), 255));
	   // g.setColor(daylight);
	    //g.fillRect(0, 0, WIDTH, HEIGHT);
	    
	    world.render(g);
	    
	   // g.setColor(new Color(0, 50, 0));
	   // g.fillRect(0, 550, 800, 400);
	    
        
	    
        ///
        bs.show();
        g.dispose();
       
    }
   
    public static void main(String[] args) {
        new Main();
        
    }
 
    public void keyPressed(KeyEvent arg0) {
       
    }
 
    public void keyReleased(KeyEvent e) {
       
    }
 
    public void keyTyped(KeyEvent e) {
       
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
   
}
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.Random;

public class World {

	private int rows = 100;
	private int cols = 100;
	
	public int circleX=400, circleY=800;
	
	private int numLines = 4;
	private LinkedList<Line2D.Float> lines;
	
	private Random r = new Random();
	
	private Rectangle2D.Float[][] grid = new Rectangle2D.Float[rows][cols];
	private MouseInput minput;
	private int cellWidth,cellHeight;
	private Ellipse2D mouseCircle;

	
	public World(MouseInput minput) {
		
		lines = buildLines();
		
		for(int i = 0 ; i < rows ; i++) {
			for(int j = 0 ; j < cols ; j++) {
				grid[i][j] = new Rectangle2D.Float(i*Main.WIDTH/rows, j*Main.HEIGHT/cols, Main.WIDTH/rows, Main.HEIGHT/cols);
			}
		}
		
		this.minput = minput;
		mouseCircle = new Ellipse2D.Float(minput.mx, minput.my, 400, 400);
	}
	
	public void tick() {
		circleY-=10;
		if(circleY == -400) circleY = 800;
		
	}
	
	public void render(Graphics g) {
		
		g.setColor(new Color(255, 255, 255, 10));
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setStroke(new BasicStroke(2));
		
		for(int i = 0 ; i < rows ; i++) {
			    //g.drawLine(i*Main.WIDTH/rows, 0, i*Main.WIDTH/rows, Main.HEIGHT);
			   // g.drawLine(0, i*Main.HEIGHT/cols,  Main.WIDTH, i*Main.HEIGHT/cols);
		}
		
		
		for(int i = 0 ; i < rows ; i++) {
			for(int j = 0 ; j < cols ; j++) {
				
				//if(getDist((int)grid[i][j].getCenterX(), minput.mx, (int)grid[i][j].getCenterY(), minput.my) < 125) {
				//	g.setColor(new Color(255, 255, 255, 180));
					
				//	g2d.fill(grid[i][j]);
					
			   //}
				
				
				for(Line2D.Float line : lines) {
					if(grid[i][j].intersectsLine(line)){
						g.setColor(new Color(255, 255, 255, 180));
						
						g2d.fill(grid[i][j]);
					}
					
				
				}
				
				LinkedList<Line2D.Float> rays = calcRays(lines, minput.mx, minput.my, 10, 3000);
				for(Line2D.Float ray : rays) {
					if(grid[i][j].intersectsLine(ray)){
						g.setColor(new Color(255, 0, 255, 180));
						
						g2d.fill(grid[i][j]);
					}
				}
				
				
				/*
				mouseCircle = new Ellipse2D.Float(circleX-200, circleY, 300, 300);
				if(mouseCircle.intersects(grid[i][j])) {
					
					if(j < 35) {
						int dist = (int) getDist((int)grid[i][j].getCenterX(), circleX-50, (int)grid[i][j].getCenterY(), circleY+150);
						g.setColor(new Color(255, Math.min(dist, 255), 0, 180));
						g2d.fill(grid[i][j]);

					}
					
				}
				*/
			}
		}
		
	}
	
	public double getDist(int x1, int x2, int y1, int y2) {
		return Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1)) ;
	}
	
	
	
	public LinkedList<Line2D.Float> buildLines() {
		LinkedList<Line2D.Float>lines = new LinkedList<>();
		for(int i = 0; i < numLines; i++) {
			
			int x1 = r.nextInt(800);
			int y1 = r.nextInt(800);
			int x2 = r.nextInt(800);
			int y2 = r.nextInt(800);
			
			lines.add(new Line2D.Float(r.nextInt(Main.WIDTH),r.nextInt(Main.HEIGHT),r.nextInt(Main.WIDTH),r.nextInt(Main.HEIGHT)));
		}
		return lines;
	}
	
	private LinkedList<Line2D.Float> calcRays(LinkedList<Line2D.Float> lines, int mx, int my, int resolution, int maxDist){
		LinkedList<Line2D.Float> rays = new LinkedList<>();
		
		for(int i = 0 ; i < resolution; i++) {
			double dir = (Math.PI*2) * (double) i / resolution;
			float minDist = maxDist;
			for(Line2D.Float line : lines) {
				float dist = getRayCast(mx, my, mx+(float) Math.cos(dir) * maxDist, my+(float) Math.sin(dir) * maxDist, line.x1, line.y1, line.x2, line.y2);
				if(dist < minDist && dist > 0) {
					minDist = dist;
				}
			}
			rays.add(new Line2D.Float(mx, my, mx+(float) Math.cos(dir) * minDist, my+(float) Math.sin(dir) * minDist));
		}
		return rays;
	}
	
	public static float getRayCast(float p0_x, float p0_y, float p1_x, float p1_y, float p2_x, float p2_y, float p3_x, float p3_y) {
	    float s1_x, s1_y, s2_x, s2_y;
	    s1_x = p1_x - p0_x;
	    s1_y = p1_y - p0_y;
	    s2_x = p3_x - p2_x;
	    s2_y = p3_y - p2_y;

	    float s, t;
	    s = (-s1_y * (p0_x - p2_x) + s1_x * (p0_y - p2_y)) / (-s2_x * s1_y + s1_x * s2_y);
	    t = (s2_x * (p0_y - p2_y) - s2_y * (p0_x - p2_x)) / (-s2_x * s1_y + s1_x * s2_y);

	    if (s >= 0 && s <= 1 && t >= 0 && t <= 1) {
	        // Collision detected
	        float x = p0_x + (t * s1_x);
	        float y = p0_y + (t * s1_y);

	        return dist(p0_x, p0_y, x, y);
	    }

	    return -1; // No collision
	}
	
	public static float dist(float x1, float y1, float x2, float y2) {
	    return (float) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

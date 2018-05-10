import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class CarShape {
	
	private int x;
	private int y;
	private int width;
	private int direction;// 1 is vertical, 0 is horizontal
	private int length;

	public CarShape(int x, int y, int width, int length, int direction) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.direction = direction;
		this.length = length;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public void draw(Graphics2D g2) {
		
		//coordinate x,y,width, hight
		if(direction == 0) {
			RoundRectangle2D.Double body = new RoundRectangle2D.Double(x, y+width, width*length, width, 10, 10);
			g2.draw(body);
		}else {
			RoundRectangle2D.Double body = new RoundRectangle2D.Double(x, y+width, width, width*length,10,10);
			g2.draw(body);
		}
		
	
		
	}
	
	public int getYco() {
		return y;
	}
	public int getXco() {
		return x;
	}
	
	public boolean contains(Point2D p) {
		if(direction == 0) {
			return x <= p.getX() && p.getX() <= x + (width*length) 
				&& y <= p.getY() && p.getY() <= y + width;
		}else {
			return x <= p.getX() && p.getX() <= x + width
				&& y-width <= p.getY() && p.getY() <= y + width*length - width;
		}
	}
	
	public void translate(int dx, int dy){
	      x = x + dx;
	      y = y + dy;
	}
	
}

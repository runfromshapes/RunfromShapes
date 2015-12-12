
import java.awt.*;

class Circle extends Shape
{
	private int radius;
	private Color color;
	
	public Circle( int radius, Color color, Point center)
	{
		this.radius = radius;
		this.color = color;
		
		super.setCenter(center);
	}
	
	public void draw( Graphics g, int life)
	{
		g.setColor( color);
		g.fillOval( getCenter().getX()-radius, getCenter().getY()-radius, radius<<1, radius<<1);
		for ( int i = 1 ; i <= life ; i ++)
			g.fillOval( 1000-i*22-10, 10, 10, 10);
	}
	
	public Point getCenter()
	{
		return super.getCenter();
	}
	
	public void update( Point center)
	{
		super.setCenter(center);
	}
	
	public int getRadius()
	{
		return radius;
	}
	
	private void setRadius(int radius)
	{
		this.radius = radius;
	}
	
	public void changeRadius()
	{
		if ( radius < 50)
			setRadius( radius + 20);
		if ( radius > 150)
			setRadius( radius - 20);
		if ( (int)(Math.random()*1) == 0)
			setRadius( radius + 20);
		else
			setRadius( radius - 20);
	}
}

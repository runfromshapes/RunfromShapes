
import java.lang.Math;
import java.lang.Object;
import java.awt.*;
import javax.swing.*;

class Shape
{
	private Point center;
	private Point speed;
	private double rotatingSpeed;
	private Vector[] vertices;
	private int length, maxVertices;
	
	public Shape( int y, int level)
	{
		maxVertices = (int)( (Math.random()) * 6) + 3;
		length = (int)( (Math.random()) * 80) + 10;
		
		vertices = new Vector[maxVertices];
		
		for ( int i = 0 ; i < maxVertices ; i ++)
			vertices[i] = new Vector( length, (double)( (360.0*i) / maxVertices) );
		
		center = new Point( (int)( (Math.random()) * 800) + 100, y );
		
		speed = new Point( (int)( (Math.random()) * 11) - 5, -3*level);
		
		rotatingSpeed = (Math.random()*40) - 20 ;
	}
	
	public Shape( )
	{
		maxVertices = 10;
		speed = new Point( 0, -15);
		rotatingSpeed = (Math.random()*60) - 30 ;
		center = new Point( (int)( (Math.random()) * 800) + 100, ((int)( (Math.random()) * 3000) + 200) * -10);
		
		vertices = new Vector[maxVertices];
		
		for ( int i = 0 ; i < maxVertices ; i ++)
			vertices[i] = new Vector( (i&1) == 0 ? 50:20 , (double)( (360.0*i) / maxVertices) );
	}
	
	public void updateSpeed( int level)
	{
		speed.setY( level*-3);
	}
	
	public void setCenter( Point center)
	{
		this.center = center;
	}
	
	public Point getCenter()
	{
		return center;
	}
	
	public void update()
	{
		for ( int i = 0 ; i < maxVertices ; i ++)
			vertices[i].updateAngle( rotatingSpeed);
		if ( center.getY() + length > 0 )
			center.setX( center.getX() - speed.getX());
		center.setY( center.getY() - speed.getY());
	}
	
	public int getLength()
	{
		return length;
	}
	
	public void draw( Graphics g1)
	{
		if ( (center.getY() + length) <= 0 )
			return;
		Graphics2D g = (Graphics2D) g1;
		int x = center.getX(), y = center.getY();
		
		g.setStroke( new BasicStroke(5));
		g.setColor( Color.white);
			
		for ( int i = 0 ; i + 1 < maxVertices ; i ++)
			g.drawLine( x + (int)(vertices[i].getLength()*Math.cos(vertices[i].getAngle()*Math.PI/180.0)),
						y + (int)(vertices[i].getLength()*Math.sin(vertices[i].getAngle()*Math.PI/180.0)),
						x + (int)(vertices[i+1].getLength()*Math.cos(vertices[i+1].getAngle()*Math.PI/180.0)),
						y + (int)(vertices[i+1].getLength()*Math.sin(vertices[i+1].getAngle()*Math.PI/180.0)) );
		
		g.drawLine( x + (int)(vertices[maxVertices-1].getLength()*Math.cos(vertices[maxVertices-1].getAngle()*Math.PI/180.0)),
					y + (int)(vertices[maxVertices-1].getLength()*Math.sin(vertices[maxVertices-1].getAngle()*Math.PI/180.0)),
					x + (int)(vertices[0].getLength()*Math.cos(vertices[0].getAngle()*Math.PI/180.0)),
					y + (int)(vertices[0].getLength()*Math.sin(vertices[0].getAngle()*Math.PI/180.0)) );
	}
	
	public boolean checkCollide( Point center, int radius)
	{
		if ( Math.sqrt((center.getX()-getCenter().getX())*(center.getX()-getCenter().getX())+(center.getY()-getCenter().getY())*(center.getY()-getCenter().getY())) > (radius + getLength()))
			return false;
		double area,line;
		int x1,x2,y1,y2;
		double angle = ( Math.atan2( center.getX() - getCenter().getX(), center.getY() - getCenter().getY()) * 180.0 )/Math.PI;
		for ( int i = 0 ; i < maxVertices; i ++ )
			if ( vertices[i].getAngle() <= angle && vertices[(i+1)%maxVertices].getAngle() >= angle)
			{
				x1 = getCenter().getX() + (int)(vertices[i].getLength()*Math.cos(vertices[i].getAngle()*Math.PI/180.0));
				y1 = getCenter().getY() + (int)(vertices[i].getLength()*Math.sin(vertices[i].getAngle()*Math.PI/180.0));
				x2 = getCenter().getX() + (int)(vertices[(i+1)%maxVertices].getLength()*Math.cos(vertices[(i+1)%maxVertices].getAngle()*Math.PI/180.0));
				y2 = getCenter().getY() + (int)(vertices[(i+1)%maxVertices].getLength()*Math.sin(vertices[(i+1)%maxVertices].getAngle()*Math.PI/180.0));
				
				area = x1*y2 +
					   x2*center.getY() +
					   center.getX()*y1 -
					   y1*x2 -
					   y2*center.getX() -
					   center.getY()*x1;
				line = Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
				if( area/line > radius)
					return false;
				return true;
			}
		return false;
	}
}

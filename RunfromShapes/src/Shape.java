
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
		if ( dist(center.getX(),center.getY(),getCenter().getX(),getCenter().getY()) > (radius + getLength()))
			return false;
		double area,line;
		int x1,x2,y1,y2,x3,y3;
		x3 = center.getX();
		y3 = center.getY();
		double angle = Math.atan2( center.getY() - getCenter().getY(), center.getX() - getCenter().getX()) * 180.0/Math.PI;
		if ( angle < 0)
			angle = angle + 360.0;
		for ( int i = 0 ; i + 1< maxVertices; i ++ )
			if ( vertices[i].getAngle() <= angle && angle <= vertices[i+1].getAngle() )
			{
				x1 = getCenter().getX() + (int)(vertices[i].getLength()*Math.cos(vertices[i].getAngle()*Math.PI/180.0));
				y1 = getCenter().getY() + (int)(vertices[i].getLength()*Math.sin(vertices[i].getAngle()*Math.PI/180.0));
				x2 = getCenter().getX() + (int)(vertices[(i+1)%maxVertices].getLength()*Math.cos(vertices[(i+1)%maxVertices].getAngle()*Math.PI/180.0));
				y2 = getCenter().getY() + (int)(vertices[(i+1)%maxVertices].getLength()*Math.sin(vertices[(i+1)%maxVertices].getAngle()*Math.PI/180.0));
				
				area = x1*y2 +
					   x2*y3 +
					   x3*y1 -
					   y1*x2 -
					   y2*x3 -
					   y3*x1;
				
				if ( dist( x3,y3,x1,y1) < radius)
					return true;
				if ( dist( x3,y3,x2,y2) < radius)
					return true;
					
				line = Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
				if( area/line >= radius)
					return false;
				
				if ( angle(x1,y1,x2,y2,x3,y3)>90 || angle(x2,y2,x1,y1,x3,y3)>90)
					return false;
				return true;
			}
		x1 = getCenter().getX() + (int)(vertices[maxVertices-1].getLength()*Math.cos(vertices[maxVertices-1].getAngle()*Math.PI/180.0));
		y1 = getCenter().getY() + (int)(vertices[maxVertices-1].getLength()*Math.sin(vertices[maxVertices-1].getAngle()*Math.PI/180.0));
		x2 = getCenter().getX() + (int)(vertices[0].getLength()*Math.cos(vertices[0].getAngle()*Math.PI/180.0));
		y2 = getCenter().getY() + (int)(vertices[0].getLength()*Math.sin(vertices[0].getAngle()*Math.PI/180.0));
		
		area = x1*y2 +
			   x2*y3 +
			   x3*y1 -
			   y1*x2 -
			   y2*x3 -
			   y3*x1;
		
		if ( dist( x3,y3,x1,y1) < radius)
			return true;
		if ( dist( x3,y3,x2,y2) < radius)
			return true;
			
		line = Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
		if( area/line >= radius)
			return false;
		
		
		if ( angle(x1,y1,x2,y2,x3,y3)>90 || angle(x2,y2,x1,y1,x3,y3)>90)
			return false;
		return true;
	}
	
	private double dist( int x1, int y1, int x2, int y2)
	{
		return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
	}
	
	private double angle( int x1, int y1, int x2, int y2, int x3, int y3)
	{
		double line1,line2,line3;
		line1 = dist( x1,y1,x2,y2);
		line2 = dist( x2,y2,x3,y3);
		line3 = dist( x1,y1,x3,y3);
		
		return Math.acos( (line3*line3 - line1*line1 - line2*line2) / (2*line1*line2))*180/Math.PI;
	}
}

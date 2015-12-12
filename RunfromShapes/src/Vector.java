
class Vector
{
	private int length;
	private double angle;
	
	public Vector( int length, double angle)
	{
		this.length = length;
		this.angle = angle;
	}
	
	public int getLength()
	{
		return length;
	}
	public double getAngle()
	{
		return angle;
	}
	
	public void updateAngle( double rotatingSpeed)
	{
		angle = ( angle + rotatingSpeed) % 360;
	}
}

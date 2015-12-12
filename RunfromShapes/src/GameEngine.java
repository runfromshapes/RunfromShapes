
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

class GameEngine extends JPanel{
	private JFrame frame;
	private MainMenu menuPanel;
	private Point mouseLocation;
    private Timer timer;
    private Image background;
    private int backgroundY;
    
	private final int maxShapes = 20;
	private Shape []polygons;
	private Circle player;
	private Star bonus;
	private Color []colors;
	private int life;
	private int score;
	private int level;
	private int pause;
	
	public GameEngine( JFrame frame, MainMenu menuPanel, int color, int difficulty)
	{
		this.frame = frame;
		this.menuPanel = menuPanel;
		
		setPreferredSize(new Dimension(1000,800));
		setBackground(Color.white);
		
		polygons = new Shape[maxShapes];
		
		JButton button=new JButton("Quit Game");
		
		setLayout(new BorderLayout());
		add(button,BorderLayout.SOUTH);
		button.addActionListener(new ButtonListener());
		addMouseMotionListener(new mouseListener());
		
		mouseLocation = new Point( 500, 600);
		
        timer = new Timer();
        timer.schedule(new timerTask(), 80, 40);
        
        
   		background = Toolkit.getDefaultToolkit().getImage("..\\space.png");
   		backgroundY = -1024;
   		
		colors = new Color[]{ Color.red, Color.yellow, Color.green, Color.blue, Color.white} ;
		
		polygons = new Shape[maxShapes];
		
		level = 1;
		for ( int i = 0 ; i < maxShapes ; i ++ )
			polygons[i] = generateShape(i*-100);
		
		player = new Circle( 50 + difficulty * 25, colors[color], new Point( 500, 600) );
		bonus = generateBonus();
		
		life = 3;
		score = 0;
		pause = 0;
	}
	
	public class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			timer.cancel();
			frame.setContentPane( menuPanel);
			frame.revalidate();
			return;
		}
	}
	
	public class mouseListener extends MouseAdapter
	{
		public void mouseMoved(MouseEvent e)
		{
			int x = e.getX();
			int y = e.getY();
			if ( x<100)
				x=100;
			if ( x>899)
				x=899;
			if ( y<100)
				y=100;
			if ( y>699)
				y=699;
				
			mouseLocation.setX( x);
			mouseLocation.setY( y);
		}
		public void mouseDragged(MouseEvent e)
		{
			int x = e.getX();
			int y = e.getY();
			if ( x<100)
				x=100;
			if ( x>899)
				x=899;
			if ( y<100)
				y=100;
			if ( y>699)
				y=699;
				
			mouseLocation.setX( x);
			mouseLocation.setY( y);
		}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
    	g2.drawImage( background, 0, backgroundY, this);
    	backgroundY += 5;
    	if ( backgroundY > 0)
    		backgroundY -= 1024;
    	g2.finalize();
		drawAll(g);
	}
	
	class timerTask extends TimerTask {
        public void run() {
        	gameUpdateLoop();
        }
    }
    
	private void drawAll(Graphics g)
	{
		for ( int i = 0 ; i < maxShapes ; i ++ )
			polygons[i].draw(g);
		
		bonus.draw(g);
		
    	g.drawString("Score : " + score, 10, 20);
    	g.drawString("Level : " + level, 10, 40);
    	if ( pause > 0)
    		g.drawString("Starting in " + pause + " ...", 10, 60);
			
		player.draw(g, life);
	}
	
	private void updateAll( Point mouseLocation)
	{
		for ( int i = 0 ; i < maxShapes ; i ++ )
		{
			polygons[i].update();
			if ( polygons[i].getCenter().getY() > 900 )
				polygons[i] = generateShape( polygons[i].getCenter().getY() - maxShapes*100);
		}
			
		player.update( mouseLocation);
		
		bonus.update();
		
		if ( bonus.getCenter().getY() > 900 )
				bonus = generateBonus( );
	}
	private void gameUpdateLoop()
	{
		if ( pause == 0)
		{
			if (checkCollisions())
				decreaseLife();
				
			if (checkCollisionsWithBonus())
				giveBonus();
				
			increaseScore();
			
			if ( level != 10 && score/50 > (level+1)*level)
			{
				increaseLevel();
				for ( int i = 0 ; i < maxShapes ; i ++ )
					polygons[i].updateSpeed(level);
			}
				
			updateAll( mouseLocation);
		}
		else
		{
			player.update( mouseLocation);
			if ( pause > 0)
				pause -- ;
		}
			
		
		repaint();
	}
	
	private boolean checkCollisions()
	{
		for ( int i = 0 ; i < maxShapes ; i ++ )
			if ( polygons[i].checkCollide( player.getCenter(), player.getRadius() ) )
				return true;
		return false;
	}
	
	private boolean checkCollisionsWithBonus()
	{
		if ( player.getRadius()*player.getRadius() > ((player.getCenter().getX()-bonus.getCenter().getX())*(player.getCenter().getX()-bonus.getCenter().getX()) +
													  (player.getCenter().getY()-bonus.getCenter().getY())*(player.getCenter().getY()-bonus.getCenter().getY())) )
			return true;
		return false;
	}
	
	private void giveBonus()
	{
		int random = (int) Math.random()*3;
		
		if ( random == 0)
		{
			life = life + 1;
		}
		else if ( random == 1)
		{
			score = score + 500;
		}
		else if ( random == 2)
		{
			player.changeRadius();
		}
		
		bonus = generateBonus();
	}
	
	private void decreaseLife()
	{
		if ( life == 0)
		{
			pause = -1;
		}
		else
		{
			life = life - 1;
			pause = 35;
		}
	}
	
	private void increaseLevel()
	{
		if ( level != 10)
			level = level + 1;
	}
	
	private void increaseScore()
	{
		score = 1 + score + (int) (Math.random() * level);
	}
	
	private Shape generateShape(int y)
	{
		return new Shape( y, level);
	}
	
	private Star generateBonus()
	{
		return new Star();
	}
}
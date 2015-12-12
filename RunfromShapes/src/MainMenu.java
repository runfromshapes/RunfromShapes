
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.System;

class MainMenu extends JPanel{
	private JFrame frame;
	private MainMenu menuPanel;
	
	private JButton newGameButton;
	private JButton difficultyButton;
	private JButton colorButton;
	private JButton helpButton;
	private JButton exitButton;
	
	private JButton clickedButton;
	
	private int difficulty;
	private String[] difficulties;
	private int color;
	private String[] colors;
	
    public static void main(String[] args)
    {
		JFrame frame=new JFrame("Run From Shapes!");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setContentPane(new MainMenu( frame));
		frame.pack();
		frame.setVisible(true);
    }
	
	public MainMenu( JFrame frame)
	{
		this.frame = frame;
		menuPanel = this;
		
		setPreferredSize(new Dimension(1000,800));
		
		newGameButton = new JButton("New Game");
		difficulty = 1;
		difficultyButton=new JButton("Difficulty : Normal");
		color = 1;
		colorButton=new JButton("Color : Yellow");
		helpButton=new JButton("Help !");
		exitButton=new JButton("Exit");
		
		colors = new String[]{"Red", "Yellow", "Green", "Blue", "White"} ;
		difficulties = new String[]{"Easy", "Medium", "Hard"} ;
		
    	JPanel buttonPanel = new JPanel();
    	
    	buttonPanel.setLayout(new GridLayout(9,0));
    	buttonPanel.add(new JPanel());
    	buttonPanel.add(new JPanel());
    	buttonPanel.add(newGameButton);
    	buttonPanel.add(difficultyButton);
    	buttonPanel.add(colorButton);
    	buttonPanel.add(helpButton);
    	buttonPanel.add(exitButton);
    	buttonPanel.add(new JPanel());
    	buttonPanel.add(new JPanel());
    	
    	setLayout(new GridLayout(0,3));
		add(new JPanel());
		add(buttonPanel);
		add(new JPanel());
		
		
		newGameButton.addActionListener(new ButtonListener());
		difficultyButton.addActionListener(new ButtonListener());
		colorButton.addActionListener(new ButtonListener());
		helpButton.addActionListener(new ButtonListener());
		exitButton.addActionListener(new ButtonListener());
	}
	
	public class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			clickedButton = (JButton)e.getSource();
			
			if ( clickedButton == exitButton )
			{
				System.exit(0);
			}
			if ( clickedButton ==  helpButton )
			{
				showHelp();
				return;
			}
			if ( clickedButton ==  colorButton )
			{
				changeColor();
				return;
			}
			if ( clickedButton ==  difficultyButton )
			{
				changeDifficulty();
				return;
			}
			if ( clickedButton ==  newGameButton )
			{
				createGame();
				return;
			}
		}
	}
	
	private void createGame()
	{
		frame.setContentPane( new GameEngine( frame, menuPanel, color, difficulty));
		frame.revalidate();
	}
	
	private void changeColor()
	{
		color = (color + 1)%5;
		colorButton.setText( "Color : " + colors[color]);
	}
	
	private void changeDifficulty()
	{
		difficulty = (difficulty + 1)%3;
		difficultyButton.setText( "Difficulty : " + difficulties[difficulty]);
	}
	
	private void showHelp()
	{
		JOptionPane.showMessageDialog(frame, "The only controller is mouse\nAvoid crushing objects\nCollect stars for bonuses", "How to Play", JOptionPane.WARNING_MESSAGE);
	}
}

import java.awt.event.*;
import java.applet.Applet;

public abstract class GameBase extends Applet implements Runnable, KeyListener
{
	Thread t;

	public static final int SP = KeyEvent.VK_SPACE;
	public static final int EN = KeyEvent.VK_ENTER;

	public static final int _1 = KeyEvent.VK_1;
	public static final int _2 = KeyEvent.VK_2;
	public static final int _3 = KeyEvent.VK_3;
	public static final int _4 = KeyEvent.VK_4;
	
	boolean[] released = new boolean[1024];

	public abstract void initialize();

	public void init()
	{
		initialize();
		
		requestFocus();
		addKeyListener(this);
		
		t = new Thread(this);
		
		t.start();		
	}

	
	public abstract void inTheGameLoop();

	
	public void run()
	{
		while(true)
		{
			inTheGameLoop();
			
 			repaint();
			
			try
			{
		      t.sleep(16);
			}
			catch(Exception x) {};
		}
	}
	
	
	public void keyPressed(KeyEvent e) { }

	public void keyReleased(KeyEvent e)
	{
		released[e.getKeyCode()] = true;
	}
	
	public void keyTyped(KeyEvent e) {}

	
}

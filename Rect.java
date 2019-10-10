import java.awt.*;

public class Rect
{
	int x;
	int y;
	
	int w;
	int h;

	public Rect(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
				
		this.w = w;
		this.h = h;
	}
	
	public boolean overlaps(Rect r)
	{
		return (y+h >= r.y) && (r.y+r.h >= y) && (x+w >= r.x) && (r.x+r.w >= x);
	}

	public void draw(Graphics g)
	{
		g.drawRect(x, y, w, h);
	}

	public void moveLt(int dx){ x -= dx;}

	public void moveUp(int dx){ y -= dx;}

	public void moveDn(int dy){ y += dy;}

}

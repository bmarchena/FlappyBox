import java.awt.*;

public class Rect
{
	int x;
	int y;
	
	int w;
	int h;

	int angle = 0;
	
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
	
	public boolean contains(int mx, int my)
	{
		return (mx >= x)      &&
				 (mx <= x + w)  &&
				 (my >= y)      &&
				 (my <= y + h);
	}

	
	public void draw(Graphics g)
	{
		g.drawRect(x, y, w, h);
	}

	public void rotate(){angle -= 90;}

	public void moveLt(int dx){ x -= dx;}

	public void moveRt(int dx){ x += dx;}

	public void moveUp(int dx){ y -= dx;}

	public void moveDn(int dy){ y += dy;}

}

package Game.Entities.Static;

import java.awt.Graphics;
import java.awt.Rectangle;

import Main.Handler;
import Resources.Images;

public class Tree extends StaticBase {
	
	private Rectangle tree;
	
	public Tree(Handler handler, int xPosition, int yPosition) {
		super(handler);
		// Sets original position to be this one.
        this.setY(yPosition);
        this.setX(xPosition);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Images.object[1], this.getX(), this.getY(), 50, 50,null );
    	tree = new Rectangle(this.getX(), this.getY()+10, 50, 50);

	}
    @Override
    public Rectangle GetCollision() {
    	
    	return tree;
    }
}

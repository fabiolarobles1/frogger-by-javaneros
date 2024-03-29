package Game.Entities.Dynamic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Game.Entities.EntityBase;
import Game.Entities.Static.StaticBase;
import Game.GameStates.State;
import Main.Handler;
import Resources.Images;

/*
 * The Frog.
 */
public class Player extends EntityBase {
	private Handler handler;

	private ArrayList<StaticBase> SpawnedHazards;
	private Rectangle player;
	private String facing = "UP";
	public Boolean moving = false;
	private int moveCoolDown=0;
	private static long score = 0;
	private static long scoreHolder = 0;
	public static long finalScore = 0;
	private int index =0;

	public Player(Handler handler) {
		super(handler);
		this.handler = handler;
		this.handler.getEntityManager().getEntityList().add(this);
		SpawnedHazards = new ArrayList<>();
		player = new Rectangle(); 	// see UpdatePlayerRectangle(Graphics g) for its usage.
		
	}
	
	public Rectangle getPlayerCollision() {
		return player;
	}

	public String getFacing() {
		return facing;
	}

	public static long getScore() {
		return score;
	}

	public static long getScoreHolder() {
		return scoreHolder;
	}


	public static void setScoreHolder(long scoreHolder) {
		Player.scoreHolder = scoreHolder;
	}

	public static void setScore(long score) {
		Player.score = score;
	}

	public void tick(){


		if(moving) {
			animateMovement();
		}

		if(!moving){
			move();
		}

	}

	public void reGrid() {
		if(facing.equals("UP")) {
			if(this.getX() % 64 >= 64 / 2 ) {
				this.setX(this.getX() + (64 - this.getX() % 64));
			}
			else {
				this.setX(this.getX() - this.getX() % 64);
			}
			setY(getY()-64);
		}
	}

	private void move(){
		if(player.getY()>handler.getHeight()-player.getHeight()) {
			State.setState(handler.getGame().gameoverState);
		}
		if(moveCoolDown< 25){
			moveCoolDown++;
		}
		index=0;

		/////////////////MOVE UP///////////////
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_W) && !moving && facing.equals("UP")){
			//When the player get close to the top side prevent the player from using this action 
			
			if(player.getY()>player.getHeight()) {
				moving=true;
				if (moving==true){
					scoreHolder++;
				}
				
			}
		}else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_W) && !moving && !facing.equals("UP")){
			//When the player get close to the top side prevent the player from using this action 
			if(player.getY()>10) {
				if(facing.equals("DOWN")) {
					if(this.getX() % 64 >= 64 / 2 ) {

						this.setX(this.getX() + (64 - this.getX() % 64));
					}
					else {
						this.setX(this.getX() - this.getX() % 64);
					}
					setY(getY() + 64);
				}
				if(facing.equals("LEFT")) {
					setY(getY() + 64);
				}
				if(facing.equals("RIGHT")) {
					setX(getX()-64);
					setY(getY()+64);
				}
				facing = "UP";
			}

			/////////////////MOVE LEFT///////////////
		}else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_A) && !moving && facing.equals("LEFT")){
			//When the player get close to the left side prevent the player from using this action 
			if(player.getX()>0) {
				moving=true;
			}
		}else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_A) && !moving&& !facing.equals("LEFT")){
			//When the player get close to the left side prevent the player from using this action 
			if(player.getX()>0) {
				if(facing.equals("RIGHT")) {
					setX(getX()-64);
				}
				reGrid();
				facing = "LEFT";
			}

		}


		/////////////////MOVE DOWN///////////////
		else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_S) && !moving && facing.equals("DOWN")){
			//When the player get close to the bottom side prevent the player from using this action 
			if(player.getY()<700) {
				moving=true;
				scoreHolder--;
			}else {
				
				State.setState(handler.getGame().gameoverState);
				
			}
		}else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_S) && !moving && !facing.equals("DOWN")){
			//When the player get close to the bottom side prevent the player from using this action 
			if(player.getY()<700) {
				reGrid();
				if(facing.equals("RIGHT")){
					setX(getX()-64);
				}
				facing = "DOWN";
			}

			/////////////////MOVE RIGHT///////////////

		}else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_D) && !moving && facing.equals("RIGHT")){
			//When the player get close to the right side prevent the player from using this action 
			if(player.getX()<450) {
				moving=true;
			}
		}else if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_D) && !moving&& !facing.equals("RIGHT")){
			//When the player get close to the right side prevent the player from using this action 
			if(player.getX()<450) {
				if(facing.equals("LEFT")) {
					setX(getX()+64);
				}
				if(facing.equals("UP")) {
					setX(getX()+64);
					setY(getY()-64);
				}
				if(facing.equals("DOWN")) {
					if(this.getX() % 64 >= 64 / 2 ) {
						this.setX(this.getX() + (64 - this.getX() % 64));
					}
					else {
						this.setX(this.getX() - this.getX() % 64);
					}
					setX(getX()+64);
				}
				facing = "RIGHT";
			}
		}
		if(scoreHolder>score) {
			score++;
		}
	}

	private void animateMovement(){
		if(index==8) {
			moving = false;
			index = 0;
		}
		moveCoolDown = 0;
		index++;
		switch (facing) {
		case "UP":
			if (this.getX() % 64 >= 64 / 2) {
				this.setX(this.getX() + (64 - this.getX() % 64));
			} else {
				this.setX(this.getX() - this.getX() % 64);
			}
			setY(getY() - (8));
			break;

		case "LEFT":
			setX(getX() - (8));
			break;

		case "DOWN":
			if (this.getX() % 64 >= 64 / 2) {
				this.setX(this.getX() + (64 - this.getX() % 64));
			} else {
				this.setX(this.getX() - this.getX() % 64);
			}
			setY(getY() + (8));
			break;

		case "RIGHT":
			setX(getX() + (8));
			break;

		}
	}

	public void render(Graphics g){

		if(index>=8){
			index=0;
			moving = false;
		}

		switch (facing) {
		case "UP":
			g.drawImage(Images.Player[index], getX(), getY(), getWidth(), -1 * getHeight(), null);
			break;
		case "DOWN":
			g.drawImage(Images.Player[index], getX(), getY(), getWidth(), getHeight(), null);
			break;
		case "LEFT":
			g.drawImage(rotateClockwise90(Images.Player[index]), getX(), getY(), getWidth(), getHeight(), null);
			break;
		case "RIGHT":
			g.drawImage(rotateClockwise90(Images.Player[index]), getX(), getY(), -1 * getWidth(), getHeight(), null);
			break;
		}


		UpdatePlayerRectangle(g);
		g.setFont(new Font("SansSerif",Font.BOLD,30));
		g.setColor(Color.BLACK);
		g.drawString(("Score: "+ String.valueOf(score)), 400, 35);

	}

	// Rectangles are what is used as "collisions." 
	// The hazards have Rectangles of their own.
	// This is the Rectangle of the Player.
	// Both come in play inside the WorldManager.
	private void UpdatePlayerRectangle(Graphics g) {

		player = new Rectangle(this.getX(), this.getY(), getWidth(), getHeight());

		if (facing.equals("UP")){
			player = new Rectangle(this.getX(), this.getY() - 64, getWidth(), getHeight());
		}
		else if (facing.equals("RIGHT")) {
			player = new Rectangle(this.getX() - 64, this.getY(), getWidth(), getHeight());
		}
	}

	@SuppressWarnings("SuspiciousNameCombination")
	private static BufferedImage rotateClockwise90(BufferedImage src) {
		int width = src.getWidth();
		int height = src.getHeight();

		BufferedImage dest = new BufferedImage(height, width, src.getType());

		Graphics2D graphics2D = dest.createGraphics();
		graphics2D.translate((height - width) / 2, (height - width) / 2);
		graphics2D.rotate(Math.PI / 2, height / 2, width / 2);
		graphics2D.drawRenderedImage(src, null);

		return dest;
	}

	

}

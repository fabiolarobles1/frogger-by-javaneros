package Game.GameStates;

import Main.Handler;

import Resources.Images;
import UI.ClickListlener;
import UI.UIImageButton;
import UI.UIManager;

import java.awt.*;

import Game.Entities.Dynamic.Player;



/**
 * Created by AlexVR on 7/1/2018.
 */
public class GameOverState extends State {
	
    private int count = 0;
    private UIManager uiManager;

    public GameOverState(Handler handler) {
        super(handler);
        
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);

        /*
         * Adds a button that by being pressed changes the State
         */
        uiManager.addObjects(new UIImageButton(75, handler.getGame().getHeight() - 100, 128, 64, Images.butstart, new ClickListlener() {
            @Override
            public void onClick() {
                handler.getMouseManager().setUimanager(null);
                handler.getGame().reStart();
                State.setState(handler.getGame().gameState);
            }
        }));
      

        uiManager.addObjects(new UIImageButton(33 + 150 * 2,  handler.getGame().getHeight() - 100, 128, 64, Images.BTitle, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().menuState);
        }));
       




    }

    @Override
    public void tick() {
        handler.getMouseManager().setUimanager(uiManager);
        uiManager.tick();
        count++;
        if( count>=30){
            count=30;
        }
        if(handler.getKeyManager().pbutt && count>=30){
            count=0;
            State.setState(handler.getGame().gameState);
        }

    }

    @Override
    public void render(Graphics g) {
    	g.setColor(Color.darkGray);
        g.fillRect(0,0,handler.getWidth(),handler.getHeight());
        g.drawImage(Images.gameoverfrogger,0,0,handler.getGame().getWidth(),handler.getGame().getHeight(),null);
        uiManager.Render(g);
        g.setFont(new Font("SansSerif",Font.BOLD,50));
		g.setColor(Color.BLACK);
		g.drawString(("Score: "+ String.valueOf(Player.getScore())), 190, 80);
        
   
    }
}

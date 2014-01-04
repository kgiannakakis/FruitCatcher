package gr.sullenart.games.fruitcatcher.view;

import com.badlogic.gdx.math.Rectangle;

public class Basket {

    private int SPEED = 200;

    private int width = 48;
    
    private int height = 48;
    
    private int screenWidth;
    
    private Rectangle rect;
    
    public Rectangle getPosition() {
        return rect;
    }
    
    public Basket(int screenWidth, int posX) {
        this.screenWidth = screenWidth;
        
        rect = new Rectangle();
        if (posX < 0) {
        	rect.x = screenWidth / 2 - width / 2;
        }
        else {
        	rect.x = posX;
        }
        rect.y = 20;
        rect.width = width;
        rect.height = height;
    }

    public void setPositionX(float x) {
        rect.x = x - width/2;
        
        keepOnScreen();
    }
    
    public void moveX(float speedRatio, float delta) {
        rect.x += speedRatio * SPEED * delta;
        
        keepOnScreen();
    }
    
    private void keepOnScreen() {
        if (rect.x < 0) {
            rect.x = 0;
        }
        else if (rect.x > screenWidth) {
            rect.x = screenWidth - width;
        }
    }
}


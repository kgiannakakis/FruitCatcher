package gr.sullenart.games.fruitcatcher.view;

import gr.sullenart.games.fruitcatcher.images.ImageProvider;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class NumberBoard {

	private TextureRegion[] numbers;

	private TextureRegion minusSign;
	
	public NumberBoard(ImageProvider imageProvider) {
        numbers = new TextureRegion[10];
        for(int i=0;i<10;i++) {
        	numbers[i] = imageProvider.getNumber(i);
        }
        minusSign = imageProvider.getMinusSign();
	}

	public void draw(SpriteBatch batch, int right, int top, int number) {
		if (number > 99 || number <-99) {
			return;
		}
		if (number < -9) {
        	int lsDigit = (-number)%10;
        	int msDigit = (-number)/10;
        	int posX = right - numbers[lsDigit].getRegionWidth();
        	int posY = top - numbers[lsDigit].getRegionHeight(); 
        	batch.draw(numbers[lsDigit], posX, posY);
        	posX -= numbers[msDigit].getRegionWidth()+5;
        	batch.draw(numbers[msDigit], posX, posY);
        	posX -= minusSign.getRegionWidth() + 5;
        	posY +=  (numbers[msDigit].getRegionHeight() -
        			  minusSign.getRegionHeight())/2;
        	batch.draw(minusSign, posX, posY);
        }
        else if (number < 0) {
        	int digit = -number;
        	int posX = right - numbers[digit].getRegionWidth();
        	int posY = top - numbers[digit].getRegionHeight(); 
        	batch.draw(numbers[digit], posX, posY);
        	posX -= minusSign.getRegionWidth() + 5;
        	posY +=  (numbers[digit].getRegionHeight() -
        			  minusSign.getRegionHeight())/2;
        	batch.draw(minusSign, posX, posY);        	
        }
        else if (number < 10) {
        	int posX = right - numbers[number].getRegionWidth();
        	int posY = top - numbers[number].getRegionHeight();
        	batch.draw(numbers[number], posX, posY);
        }
        else {
        	int lsDigit = number%10;
        	int msDigit = number/10;
        	int posX = right - numbers[lsDigit].getRegionWidth();
        	int posY = top - numbers[lsDigit].getRegionHeight(); 
        	batch.draw(numbers[lsDigit], posX, posY);
        	posX -= numbers[msDigit].getRegionWidth()+5;
        	batch.draw(numbers[msDigit], posX, posY);
        }
		
	}
	
}

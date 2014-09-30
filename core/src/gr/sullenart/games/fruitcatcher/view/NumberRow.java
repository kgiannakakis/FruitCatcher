package gr.sullenart.games.fruitcatcher.view;

import gr.sullenart.games.fruitcatcher.images.ImageProvider;
import gr.sullenart.games.fruitcatcher.layout.LayoutItem;
import gr.sullenart.games.fruitcatcher.layout.LayoutRow;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class NumberRow {
	
	public static LayoutRow getRow(ImageProvider imageProvider, TextureRegion label, int number) {
		int itemCount = 1;
		if (label != null) {
			itemCount++;
		}
		int n = number;
		if (n < 0) {
			n = -n;
			itemCount++;
		}
		if (n > 0) {
			itemCount += (int) Math.log10(n);
		}
		LayoutItem[] items = new LayoutItem[itemCount];
		
		int i = 0;
		if (label != null) {
			items[i++] = new LayoutItem(label);
		}
		
		if (number < 0) {
			items[i] = new LayoutItem(imageProvider.getMinusSign());
		}
		for(i = itemCount-1;;i--) {
			int x = n%10;
			items[i] = new LayoutItem(imageProvider.getNumber(x));
			n = n / 10;
			if (n == 0) {
				break;
			}
		}
		
		LayoutRow row = new LayoutRow(items, 10);
		return row;
	}
}

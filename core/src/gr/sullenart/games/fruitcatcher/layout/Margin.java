package gr.sullenart.games.fruitcatcher.layout;

public class Margin {
	public int left;
	public int top;
	public int right;
	public int bottom;
	
	public Margin(int left, int top, int right, int bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}
	
	public Margin() {
		left = top = right = bottom = 0;
	}
}

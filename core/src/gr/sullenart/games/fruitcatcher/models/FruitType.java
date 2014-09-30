package gr.sullenart.games.fruitcatcher.models;

public class FruitType {
	public static final float SEASONAL_FRUITS_RATIO = 0.25f;
	
    public static String [] fruitNames = {
        "Strawberry", "Cherries", "Blueberry", "PassionFruit",
        "Watermelon", "Grapes", "Peach", "Fig",
        "Apple", "Pear", "Banana", "Coconut",
        "Orange", "Kiwi", "Pineapple", "Papaya"};	
        
    private static int [][] seasonalFruits = new int[][] {
        { 0, 1, 2, 3},
        { 4, 5, 6, 7},
        { 8, 9, 10, 11},
        { 12, 13, 14, 15}
    };
        
    public static int [] getFruitsInSeason(int season) {
    	return seasonalFruits[season];
    }
    
    public static boolean isInSeason(int fruit, int season) {
    	int [] fruits = seasonalFruits[season];
    	for(int f: fruits) {
    		if (f == fruit) {
    			return true;
    		}
    	}
    	return false;
    }
}
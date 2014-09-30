package gr.sullenart.games.fruitcatcher.models;

public enum FallingObjectType {
    Fruit(0),
    SeasonalFruit(1),
    BadFruit(2),
    BonusObject(3);
    
    private final int value;
    
    public int getValue() {
        return value;
    }
    
    private FallingObjectType(int value) {
        this.value = value;
    }
    
    public static FallingObjectType fromValue(int value) {
        switch(value) {
            case 0:
                return Fruit;
            case 1:
                return SeasonalFruit;
            case 2:
                return BadFruit;
            case 3:
                return BonusObject;                
        }
        throw new ArrayIndexOutOfBoundsException("Wrong value");    
    }
}

package gr.sullenart.games.fruitcatcher.models;

public enum Season {
    Spring(0),
    Summer(1),
    Autumn(2),
    Winter(3);
    
    private final int value;
    
    public int getValue() {
        return value;
    }
    
    private Season(int value) {
        this.value = value;
    }
    
    public static Season fromValue(int value) {
        switch(value%4) {
            case 0:
                return Spring;
            case 1:
                return Summer;
            case 2:
                return Autumn;
            case 3:
                return Winter;                
        }
        throw new ArrayIndexOutOfBoundsException("Wrong value");
    }
}


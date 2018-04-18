package in.satya.sareenproperties.Enums;

public enum PropertyUnit {
    squareFeet("Sq-ft"),
	squareYard("Sq-yrd"),
	squareMeter("Sq-m"),
	acre("Acre"),
	bigha("Bigha"),
	hectare("Hectare"),
	marla("Marla"),
	kanal("Kanal");
    private String propertyUnit;

    private PropertyUnit(String propertyUnit){
        this.propertyUnit = propertyUnit;
    }

    @Override public String toString(){
        return propertyUnit;
    }
}

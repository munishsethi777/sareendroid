package in.satya.sareenproperties.Enums;

public enum PropertyUnit {
    selectAny("Select Any"),
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

    public static String getNameByValue(String code){
        if(code.equals(PropertyUnit.selectAny.toString())){
            return "";
        }
        for(PropertyUnit e : PropertyUnit.values()){
            if(code == e.toString()) return e.name();
        }
        return null;
    }
}

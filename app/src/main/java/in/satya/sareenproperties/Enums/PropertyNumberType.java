package in.satya.sareenproperties.Enums;

public enum PropertyNumberType {
    salam("Salam"),
	mushtarka("Mushtarka"),
	taksimPossible("Taksim possible"),
	coshareSignatory("Co-Share Signatory");

    private String propertyNumberType;

    private PropertyNumberType(String propertyNumberType){
        this.propertyNumberType = propertyNumberType;
    }

    @Override public String toString(){
        return propertyNumberType;
    }
}

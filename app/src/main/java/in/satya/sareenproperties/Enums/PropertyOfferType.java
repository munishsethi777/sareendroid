package in.satya.sareenproperties.Enums;

public enum PropertyOfferType {
    sale("Sale"),
	rental("Rental"),
	tolet("To-Let");

    private String propertyOfferType;

    private PropertyOfferType(String propertyOfferType){
        this.propertyOfferType = propertyOfferType;
    }

    @Override public String toString(){
        return propertyOfferType;
    }
}

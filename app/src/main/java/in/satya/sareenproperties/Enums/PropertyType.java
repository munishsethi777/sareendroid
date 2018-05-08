package in.satya.sareenproperties.Enums;
public enum PropertyType {
	   selectAny("Select Any"),
       residentialHouse("RESIDENTIAL - House/Villa"),
       residentialOldHouse("RESIDENTIAL - Old House/Plot"),
       residentialPlot("RESIDENTIAL - Plot/Land"),
       residentialFlat("RESIDENTIAL - Flat"),
       residentialFloot("RESIDENTIAL - Floor"),

	   // group_allToLet("ALL TOLET"),
	    toletHouse("TOLET - House/Villa"),
	    toletGroundFloor("TOLET - Ground Floor"),
	    toletFirstFloor("TOLET - First Floor"),
	    toletOtherFloor("TOLET - Other Floor"),
	    toletFlatGroundFloor("TOLET - Flat Ground Floor"),
	    toletFlatFirstFloor("TOLET - Flat First Gloor"),
	    toletFlatOtherFloor("TOLET - Flat Other Floor"),
	    //group_allCommercial("ALL COMMERCIAL"),
	    officeSpace("COMMERCIAL - Office Space"),
	    shopShowroom("COMMERCIAL - Shop/Showroom"),
	    commercialLand("COMMERCIAL - Commercial Land"),
	    industrialLand("COMMERCIAL - Industrial Land"),
	    warehouse("COMMERCIAL - Warehouse/Godown"),
	    industrialBuilding("COMMERCIAL - Industrial Building"),
	    commercialBuilding("COMMERCIAL - Commercial Building"),
	    industrialShed("COMMERCIAL - Industrial Shed"),
	    mallShop("COMMERCIAL - Mall Shop/Showroom"),
	    mallOfficeSpace("COMMERCIAL - Mall Office Space"),
	    sco("COMMERCIAL - SCO"),
	    scf("COMMERCIAL - SCF"),
	    rentedBanks("COMMERCIAL - Rented Banks"),
	    entedOffices("COMMERCIAL - Rented Offices"),
	    rentedComplexShops("COMMERCIAL - Rented Complex Shops"),
	    rentedFloors("COMMERCIAL - Rented Floors"),
	    rentalCompleteHouse("COMMERCIAL - Rental Complete House"),
	    rentalHouseFloor("COMMERCIAL - Rental House Floor"),
	    rentalFlat("COMMERCIAL - Rental Flat"),
	    rentalResidentialFloor("COMMERCIAL - Rental Residential Floor"),
	    rentalCommercialBuilding("COMMERCIAL - Rental Commercial Building"),
	    rentalCommercialFloor("COMMERCIAL - Rental Commercial Floor"),
	    rentalShop("COMMERCIAL - Rental Shop/Showroom"),
	    rentalShopMall("COMMERCIAL - Rental Shop/Showroom Mall"),
	    rentalPlot("COMMERCIAL - Rental Plot/Land"),
	    //group_allAgricultural = "ALL AGRICULTURAL";
	    industrialAgriculturalLand("AGRICULTURAL - Industrial Agricultural Land"),
	    residentialAgriculturalLand("AGRICULTURAL - Residential Agricultural Land"),
	    commercialAgriculturalLand("AGRICULTURAL - Commercial Agricultural Land"),
	    agriculturalLand("AGRICULTURAL - Agricultural Land"),
	    farmHouse("AGRICULTURAL - Farm House"),
	    marriagePalace("AGRICULTURAL - Marriage Palace/Banquets");

       private String propertyType;

       private PropertyType(String propertyType){
           this.propertyType = propertyType;
       }

       @Override public String toString(){
           return propertyType;
       }

    public static String getNameByValue(String code){
		if(code.equals(PropertyType.selectAny.toString())){
			return "";
		}
        for(PropertyType e : PropertyType.values()){
            if(code == e.toString()) return e.name();
        }
        return null;
    }

}

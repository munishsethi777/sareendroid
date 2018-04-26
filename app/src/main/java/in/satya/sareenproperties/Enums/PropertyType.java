package in.satya.sareenproperties.Enums;
public enum PropertyType {
	   selectAny("Select Any"),
       residentialHouse("House/Villa"),
       residentialOldHouse("Old House/Plot"),
       residentialPlot("Plot/Land"),
       residentialFlat("Flat"),
       residentialFloot("Floor"),

	   // group_allToLet("ALL TOLET"),
	    toletHouse("House/Villa"),
	    toletGroundFloor("Ground Floor"),
	    toletFirstFloor("First Floor"),
	    toletOtherFloor("Other Floor"),
	    toletFlatGroundFloor("Flat Ground Floor"),
	    toletFlatFirstFloor("Flat First Gloor"),
	    toletFlatOtherFloor("Flat Other Floor"),
	    //group_allCommercial("ALL COMMERCIAL"),
	    officeSpace("Office Space"),
	    shopShowroom("Shop/Showroom"),
	    commercialLand("Commercial Land"),
	    industrialLand("Industrial Land"),
	    warehouse("Warehouse/Godown"),
	    industrialBuilding("Industrial Building"),
	    commercialBuilding("Commercial Building"),
	    industrialShed("Industrial Shed"),
	    mallShop("Mall Shop/Showroom"),
	    mallOfficeSpace("Mall Office Space"),
	    sco("SCO"),
	    scf("SCF"),
	    rentedBanks("Rented Banks"),
	    entedOffices("Rented Offices"),
	    rentedComplexShops("Rented Complex Shops"),
	    rentedFloors("Rented Floors"),
	    rentalCompleteHouse("Rental Complete House"),
	    rentalHouseFloor("Rental House Floor"),
	    rentalFlat("Rental Flat"),
	    rentalResidentialFloor("Rental Residential Floor"),
	    rentalCommercialBuilding("Rental Commercial Building"),
	    rentalCommercialFloor("Rental Commercial Floor"),
	    rentalShop("Rental Shop/Showroom"),
	    rentalShopMall("Rental Shop/Showroom Mall"),
	    rentalPlot("Rental Plot/Land"),
	    //group_allAgricultural = "ALL AGRICULTURAL";
	    industrialAgriculturalLand("Industrial Agricultural Land"),
	    residentialAgriculturalLand("Residential Agricultural Land"),
	    commercialAgriculturalLand("Commercial Agricultural Land"),
	    agriculturalLand("Agricultural Land"),
	    farmHouse("Farm House"),
	    marriagePalace("Marriage Palace/Banquets");

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

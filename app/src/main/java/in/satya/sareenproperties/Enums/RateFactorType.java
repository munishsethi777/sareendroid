package in.satya.sareenproperties.Enums;

public enum RateFactorType {
    selectAny("Select Any"),
    negotiable("Negotiable"),
	nonNegotiable("Non Negotiable");

    private String rateFactorType;

    private RateFactorType(String rateFactorType){
        this.rateFactorType = rateFactorType;
    }

    @Override public String toString(){
        return rateFactorType;
    }


    public static String getNameByValue(String code){
        if(code.equals(RateFactorType.selectAny.toString())){
            return "";
        }
        for(RateFactorType e : RateFactorType.values()){
            if(code == e.toString()) return e.name();
        }
        return null;
    }
}

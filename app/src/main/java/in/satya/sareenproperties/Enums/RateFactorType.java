package in.satya.sareenproperties.Enums;

public enum RateFactorType {

    negotiable("Negotiable"),
	nonNegotiable("Non Negotiable");

    private String rateFactorType;

    private RateFactorType(String rateFactorType){
        this.rateFactorType = rateFactorType;
    }

    @Override public String toString(){
        return rateFactorType;
    }
}

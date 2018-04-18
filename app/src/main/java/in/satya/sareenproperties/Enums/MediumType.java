package in.satya.sareenproperties.Enums;

public enum MediumType {
    direct("Direct"),
	broker("Broker"),
	Relative("Relative"),
	relativIncharge("Relative Incharge");

    private String mediumType;

    private MediumType(String mediumType){
        this.mediumType = mediumType;
    }

    @Override public String toString(){
        return mediumType;
    }
}

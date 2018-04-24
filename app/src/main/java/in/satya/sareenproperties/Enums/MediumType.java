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

    public static String getNameByValue(String code){
        for(MediumType e : MediumType.values()){
            if(code == e.toString()) return e.name();
        }
        return null;
    }
}

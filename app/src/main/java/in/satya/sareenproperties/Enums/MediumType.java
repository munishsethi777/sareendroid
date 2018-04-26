package in.satya.sareenproperties.Enums;

public enum MediumType {
    selectAny("Select Any"),
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
        if(code.equals(MediumType.selectAny.toString())){
            return "";
        }
        for(MediumType e : MediumType.values()){
            if(code == e.toString()) return e.name();
        }
        return null;
    }
}

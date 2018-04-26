package in.satya.sareenproperties.Enums;

public enum PurposeType {
    selectAny("Select Any"),
    residential("Residential"),
	commercial("Commercial"),
	industrial("Industrial"),
	agricultural("Agricultural");
    private String purposeType;

    private PurposeType(String purposeType){
        this.purposeType = purposeType;
    }

    @Override public String toString(){
        return purposeType;
    }

    public static String getNameByValue(String code){
        if(code.equals(PurposeType.selectAny.toString())){
            return "";
        }
        for(PurposeType e : PurposeType.values()){
            if(code == e.toString()) return e.name();
        }
        return null;
    }
}

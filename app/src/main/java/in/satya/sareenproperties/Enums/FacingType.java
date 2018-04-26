package in.satya.sareenproperties.Enums;

public enum FacingType {
    selectAny("Select Any"),
    north("North"),
	northeast("North East"),
	northwest("North West"),
	east("East"),
	west("West"),
	south("South"),
	southeast("South East"),
	southwest("South West");

    private String facingType;

    private FacingType(String approvalType){
        this.facingType = approvalType;
    }

    @Override public String toString(){
        return facingType;
    }

    public static String getNameByValue(String code){
        if(code.equals(FacingType.selectAny.toString())){
            return "";
        }
        for(FacingType e : FacingType.values()){
            if(code == e.toString()) return e.name();
        }
        return null;
    }
}

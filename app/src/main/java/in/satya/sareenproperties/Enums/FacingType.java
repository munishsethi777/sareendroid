package in.satya.sareenproperties.Enums;

public enum FacingType {
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
        for(FacingType e : FacingType.values()){
            if(code == e.toString()) return e.name();
        }
        return null;
    }
}

package in.satya.sareenproperties.Enums;

public enum AcquiredType {
    selfCreated("Self Created"),
	inherited("Inherited");
    private String purposeType;
    private AcquiredType(String purposeType){
        this.purposeType = purposeType;
    }

    @Override public String toString(){
        return purposeType;
    }

    public static String getNameByValue(String code){
        for(AcquiredType e : AcquiredType.values()){
            if(code == e.toString()) return e.name();
        }
        return null;
    }
}

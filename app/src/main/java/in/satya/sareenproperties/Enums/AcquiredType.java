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
}

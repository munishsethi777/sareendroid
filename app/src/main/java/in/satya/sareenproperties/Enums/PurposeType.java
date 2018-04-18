package in.satya.sareenproperties.Enums;

public enum PurposeType {

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
}

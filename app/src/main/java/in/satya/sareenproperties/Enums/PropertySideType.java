package in.satya.sareenproperties.Enums;

public enum PropertySideType {

    singleSideOpen("Single Side Open"),
	corner("Corner"),
	twoSideOpen("Two Side Open"),
	threeSideOpen("Three Side Open"),
	fourSideOpen("Four Side Open");
    private String propertySideType;

    private PropertySideType(String propertySideType){
        this.propertySideType = propertySideType;
    }

    @Override public String toString(){
        return propertySideType;
    }
}

package in.satya.sareenproperties.Enums;

public enum PropertySideType {
    selectAny("Select Any"),
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

    public static String getNameByValue(String code){
        if(code.equals(PropertySideType.selectAny.toString())){
            return "";
        }
        for(PropertySideType e : PropertySideType.values()){
            if(code == e.toString()) return e.name();
        }
        return null;
    }
}

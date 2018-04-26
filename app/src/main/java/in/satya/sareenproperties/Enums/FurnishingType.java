package in.satya.sareenproperties.Enums;

public enum FurnishingType {
   selectAny("Select Any"),
   finished("Finished"),
   finishFurnished("Finish Furnished");

    private String furnishingType;

    private FurnishingType(String facingType){
        this.furnishingType = facingType;
    }

    @Override public String toString(){
        return furnishingType;
    }

    public static String getNameByValue(String code){
        if(code.equals(FurnishingType.selectAny.toString())){
            return "";
        }
        for(FurnishingType e : FurnishingType.values()){
            if(code == e.toString()) return e.name();
        }
        return null;
    }
}

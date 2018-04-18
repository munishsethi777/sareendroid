package in.satya.sareenproperties.Enums;

public enum FurnishingType {
   finished("Finished"),
   finishFurnished("Finish Furnished");

    private String furnishingType;

    private FurnishingType(String facingType){
        this.furnishingType = facingType;
    }

    @Override public String toString(){
        return furnishingType;
    }
}

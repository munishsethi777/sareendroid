package in.satya.sareenproperties.Enums;

public enum YesNO {
    selectAny("Select Any"),
    yes("Yes"),
    no("No");

    private String yesNO;

    private YesNO(String yesNO){
        this.yesNO = yesNO;
    }

    @Override public String toString(){
        return yesNO;
    }


    public static String getNameByValue(String code){
        if(code == YesNO.yes.toString()) {
            return "1";
        }else if(code == YesNO.no.toString()) {
            return "0";
        }else{
            return "";
        }
    }
}

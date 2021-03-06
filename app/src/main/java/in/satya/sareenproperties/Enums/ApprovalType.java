package in.satya.sareenproperties.Enums;

public enum ApprovalType {
    selectAny("Select Any"),
    pudaApproved("Puda Approved"),
	regularised("Regularised"),
	unapproved("UnApproved"),
	agriculturalRegistry("Agricultural Registry"),
	cityProperty("City Property");

    private String approvalType;

    private ApprovalType(String approvalType){
        this.approvalType = approvalType;
    }

    @Override public String toString(){
        return approvalType;
    }

    public static String getNameByValue(String code){
        if(code.equals(ApprovalType.selectAny.toString())){
            return "";
        }
        for(ApprovalType e : ApprovalType.values()){
            if(code == e.toString()) return e.name();
        }
        return null;
    }
}

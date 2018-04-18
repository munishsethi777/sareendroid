package in.satya.sareenproperties.Enums;

public enum ApprovalType {

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
}

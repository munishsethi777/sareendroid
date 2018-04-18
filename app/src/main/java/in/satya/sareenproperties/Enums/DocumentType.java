package in.satya.sareenproperties.Enums;

public enum DocumentType {
    registry("Registry"),
	agreement("Agreement"),
	attorney("Attorney"),
	fullFinal("Full Final"),
	allotment("Allotment"),
	leaseHold("Lease Hold"),
	freeHold("Free Hold/Registry"),
	individualTransfer("Individual Transfer"),
	companyTransfer("Company Transfer");

    private String documentType;

    private DocumentType(String approvalType){
        this.documentType = approvalType;
    }

    @Override public String toString(){
        return documentType;
    }
}

package in.satya.sareenproperties.BusinessObjects;

public class Admin {
    public static final String TABLE_NAME = "admins";
    private int id;
    private int adminSeq;
    private String userName;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAdminSeq() {
        return adminSeq;
    }

    public void setAdminSeq(int adminSeq) {
        this.adminSeq = adminSeq;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

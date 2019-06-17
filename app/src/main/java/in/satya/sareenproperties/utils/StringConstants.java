package in.satya.sareenproperties.utils;

/**
 * Created by baljeetgaheer on 02/09/17.
 */

public class StringConstants {
    public static final String NULL = "null";
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String SEQ = "seq";
    public static final String CONNECTION_ERROR = "Connection Error.Please check network connection.";
    public static final String WEB_URL = "http://www.sareenproperties.com/software/";
    public static final String PREFS_NAME = "LoginAuth";
    public static final String LOGGED_IN_ADMIN_SEQ = "loggedInAdminSeq";
    public static final String NOTE_SEQ = "noteSeq";
    //User Actions
    //API URL STRINGS
    public static final String ACTION_API_URL = "http://www.sareenproperties.com/software/Actions/";
    public static final String LOGIN_URL = ACTION_API_URL + "AdminAction.php?call=loginAdmin&username={0}&password={1}";
    public static final String CHANGE_PASSWORD = ACTION_API_URL + "AdminAction.php?call=changePassword&ismobile=1&newPassword={0}&earlierPassword={1}&adminSeq={2}";

    public static final String SAVE_INVENTORY = ACTION_API_URL + "InventoryAction.php?call=saveInventory&ismobile=1&contactperson={0}&contactmobile={1}&referredby={2}&contactaddress={3}&organisation={4}&propertytype={5}&medium={6}&propertyoffer={7}&plotnumber={8}&address1={9}&address2={10}&city={11}&landmark={12}&propertyarea={13}&propertyunit={14}&dimensionlength={15}&dimensionbreadth={16}&facing={17}&documentation={18}&time={19}&approvaltype={20}&propertysides={21}&expectedamount={22}&rate={23}&ratefactor={24}&specifications={25}&latitude={26}&longitude={27}&purpose={28}&adminseq={29}&mediumname={30}&mediumaddress={31}&mediumphone={32}&isavailable={33}&seq={34}";
    public static final String GET_INVENTORIES  = ACTION_API_URL + "InventoryAction.php?call=getInventoriesLight&ismobile=1&sortdatafield=lastmodifiedon&sortorder=desc";
    public static final String GET_INVENTORY_DETAIL  = ACTION_API_URL + "InventoryAction.php?call=getInventoryDetails&ismobile=1&id={0}";
    public static final String DELETE_INVENTORY  = ACTION_API_URL + "InventoryAction.php?call=deleteInventory&ismobile=1&ids={0}";
    //ENQUIRY ACTION
    public static final String SAVE_ENQUIRY = ACTION_API_URL + "EnquiryAction.php?call=saveEnquiry&ismobile=1&propertytype={0}&purpose={1}&address={2}&landmark={3}&propertyarea={4}&propertyunit={5}&dimensionlength={6}&dimensionbreadth={7}&facing={8}&referredby={9}&contactperson={10}&contactmobile={11}&contactaddress={12}&isrental={13}&isfullfilled={14}&expectedamount={15}&specifications={16}&propertyoffer={17}&adminseq={18}&seq={19}";
    public static final String GET_ENQUIRIES  = ACTION_API_URL + "EnquiryAction.php?call=getEnquiries&ismobile=1&sortdatafield=lastmodifiedon&sortorder=desc";
    public static final String GET_ENQUIRY_DETAIL = ACTION_API_URL + "EnquiryAction.php?call=getEnquiryDetails&ismobile=1&id={0}";
    public static final String DELETE_ENQUIRY  = ACTION_API_URL + "EnquiryAction.php?call=deleteEnquiry&ismobile=1&ids={0}";

    //NOTE ACTION
    public static final String GET_ALL_NOTES = ACTION_API_URL + "NoteAction.php?call=getAllNotes&ismobile=1";
    public static final String DELETE_NOTE = ACTION_API_URL + "NoteAction.php?call=deleteNotes&ids={0,number,#}&ismobile=1";
    public static final String GET_NOTES_DETAILS = ACTION_API_URL + "NoteAction.php?call=getNoteDetails&&noteSeq={0,number,#}&ismobile=1";
    public static final String SAVE_NOTES_DETAILS = ACTION_API_URL + "NoteAction.php?call=saveNote&adminseq={0,number,#}&seq={1,number,#}&detail={2}&ismobile=1";

}
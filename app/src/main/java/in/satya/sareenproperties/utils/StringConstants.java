package in.satya.sareenproperties.utils;

/**
 * Created by baljeetgaheer on 02/09/17.
 */

public class StringConstants {
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String CONNECTION_ERROR = "Connection Error.Please check network connection.";
    //User Actions
    //API URL STRINGS
    public static final String ACTION_API_URL = "http://www.sareenproperties.com/software/Actions/";
    public static final String LOGIN_URL = ACTION_API_URL + "AdminAction.php?call=loginAdmin&username={0}&password={1}";

    public static final String SAVE_INVENTORY = ACTION_API_URL + "InventoryAction.php?call=saveInventory&ismobile=1&contactperson={0}&contactmobile={1}&referredby={2}&contactaddress={3}&organisation={4}&propertytype={5}&medium={6}&propertyoffer={7}&plotnumber={8}&address1={9}&address2={10}&city={11}&landmark={12}&propertyarea={13}&propertyunit={14}&dimensionlength={15}&dimensionbreadth={16}&facing={17}&documentation={18}&time={19}&approvaltype={20}&propertysides={21}&expectedamount={22}&rate={23}&ratefactor={24}&specifications={25}&latitude={26}&longitude={27}&purpose={28}&adminseq={29}";

}
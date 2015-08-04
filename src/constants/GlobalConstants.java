package constants;

/**
 * Created by mcalancea on 2015-08-03.
 */
public class GlobalConstants {
    public static final int PARTITION_QTY = 4;

    public static final String DEFAULT_SCRIPT_NAME_SUFFIX = "_db_script";
    public static final String DEFAULT_SCRIPT_DESTINATION_FOLDER = System.getProperty("user.dir"); //"c:\\acquisio";
    public static final String DEFAULT_SCRIPT_ORDER_FILE = System.getProperty("user.dir")+"\\order_file.txt";
    public static final String DEFAULT_SCRIPT_ROLLBACK_ORDER_FILE = System.getProperty("user.dir")+"\\rollback_order_file.txt";

}

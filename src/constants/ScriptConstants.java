package constants;

/**
 * Created by mcalancea on 2015-08-03.
 */
public class ScriptConstants {

    public static final String KEY_WORD = "ЗДЕСЯТУТА";
    public static final String STAT_FILE_NAME= "DEVTEAMSCHEMA_run_all.sql";
    public static final String SCRIPT_NAME_PLACEHOLDER = "~SCRRIPT_NAME~";
    public static final String STAT  =
            "DEFINE APPUSER        = &votre_u\n" +
            "DEFINE TBL_TABLESPACE = DEVELOP_DTA\n" +
            "DEFINE IDX_TABLESPACE = DEVELOP_IDX\n" +
            "DEFINE OWNER          = &APPUSER\n" +
            "set verify off\n" +
            "\n" +
            "SPOOL &OWNER._run_all.log\n" +
            "\n" +
            "alter SESSION SET DDL_LOCK_TIMEOUT = 3600;\n" +
            "ALTER SESSION SET CURRENT_SCHEMA = &OWNER;\n" +
            "\n" +
            "column START_DATE HEADING ''\n" +
            "select 'START_DATE ' || to_char(SYSDATE, 'yyyy-mm-dd hh24:mi:ss') AS START_DATE from dual;\n" +
            "\n" +
            "exec dbobjver.start_ddl_exec('~SCRRIPT_NAME~.zip', '&OWNER');\n"+
            "\n" +
            "ЗДЕСЯТУТА\n" +
            "@@validate_invalid_objects.sql\n" +
            "@@cc_grant_obj_privileges.sql\n" +
            "\n" +
            "exec dbobjver.end_ddl_exec;\n"+
            "\n" +
            "set termout on\n" +
            "column END_DATE HEADING ''\n" +
            "select 'END_DATE   ' || to_char(SYSDATE, 'yyyy-mm-dd hh24:mi:ss') AS END_DATE from dual;\n" +
            "SPOOL OFF";

    public static final String NON_STAT_FILE_NAME = "DEVACQUISIO~n~NONSTAT_run_all.sql";
    public static final String PARTITION_NUMBER_KEY_WORD = "~n~";
    public static final String NON_STAT  =
        "DEFINE APPUSER        = DEVACQUISIO~n~\n" +
        "DEFINE TBL_TABLESPACE = DEVACQUISIO~n~NONSTAT\n" +
        "DEFINE IDX_TABLESPACE = DEVACQUISIO~n~NONSTATIDX\n" +
        "DEFINE OWNER          = DEVACQUISIO~n~NONSTAT\n" +
        "set verify off\n" +
        "\n" +
        "SPOOL &OWNER._run_all.log\n" +
        "\n" +
        "alter SESSION SET DDL_LOCK_TIMEOUT = 3600;\n" +
        "ALTER SESSION SET CURRENT_SCHEMA = &OWNER;\n" +
        "\n" +
        "column START_DATE HEADING ''\n" +
        "select 'START_DATE ' || to_char(SYSDATE, 'yyyy-mm-dd hh24:mi:ss') AS START_DATE from dual;\n" +
        "\n" +
        "exec dbobjver.start_ddl_exec('~SCRRIPT_NAME~.zip', '&OWNER');\n"+
        "\n" +
        "ЗДЕСЯТУТА\n" +
        "@validate_invalid_objects.sql\n" +
        "@cc_grant_obj_privileges.sql\n" +
        "\n" +
        "exec dbobjver.end_ddl_exec;\n"+
        "\n" +
        "set termout on\n" +
        "column END_DATE HEADING ''\n" +
        "select 'END_DATE   ' || to_char(SYSDATE, 'yyyy-mm-dd hh24:mi:ss') AS END_DATE from dual;\n" +
        "SPOOL OFF\n" +
        "exit\n";

}

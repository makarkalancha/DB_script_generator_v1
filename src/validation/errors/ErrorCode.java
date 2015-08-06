package validation.errors;

/**
 * Created by mcalancea on 2015-08-04.
 */
public enum ErrorCode {
    SPACE_IN_QUERY_FILE("There is a space in query file >%s<."),
    SQL_EXTENSION("SQL extension is not in the name of the script >%s<.");

    private String errorMessage;
    ErrorCode(String message){
        this.errorMessage = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

package validation.errors;

/**
 * Created by mcalancea on 2015-08-05.
 */
public class ErrorDescription {
    private ErrorCode errorCode;
    private String fullErrorMessage;

    public ErrorDescription(ErrorCode errorCode, String fullErrorMessage) {
        this.errorCode = errorCode;
        this.fullErrorMessage = fullErrorMessage;
    }
}

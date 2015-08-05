package validation.errors;

import java.util.Arrays;
import java.util.Collection;
import com.google.common.collect.Multimap;

/**
 * Created by mcalancea on 2015-08-05.
 */
public class ErrorConverter {
    public static String convertErrorCodeToString(Multimap<ErrorCode, Object> errorCodes) {
        StringBuilder result = new StringBuilder();
        for (ErrorCode errorCode : errorCodes.keySet()) {
            String errorMessage = errorCode.getErrorMessage();
            Collection<Object> objects = errorCodes.get(errorCode);
            String stringObjects = Arrays.toString(objects.toArray());
            result.append(errorCode);
            result.append(": ");
            result.append(String.format(errorMessage,stringObjects));
            result.append("; ");
        }
        return result.toString();
    }

}

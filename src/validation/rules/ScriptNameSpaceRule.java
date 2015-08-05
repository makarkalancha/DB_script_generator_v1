package validation.rules;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import validation.Rule;
import validation.errors.ErrorCode;

/**
 * Created by mcalancea on 2015-08-04.
 */
public class ScriptNameSpaceRule implements Rule {
    private String scriptName;

    @Override
    public Multimap<ErrorCode, Object> validate() {
        Multimap<ErrorCode, Object> errorCodes = ArrayListMultimap.create();
        if(scriptName.contains(" ")){
            errorCodes.put(ErrorCode.SPACE_IN_QUERY_FILE, scriptName);
        }
        return errorCodes;
    }

    @Override
    public void setObjectToValidate(Object objectToValidate) {
        scriptName = (String) objectToValidate;
    }
}

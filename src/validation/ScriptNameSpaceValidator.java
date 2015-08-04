package validation;

/**
 * Created by mcalancea on 2015-08-04.
 */
public class ScriptNameSpaceValidator implements Validator {
    private final String scriptName;

    public ScriptNameSpaceValidator(String scriptName) {
        this.scriptName = scriptName;
    }

    @Override
    public boolean validate() {

        return false;
    }
}

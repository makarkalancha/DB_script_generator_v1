package validation;

/**
 * Created by mcalancea on 2015-08-04.
 */
public enum ValidationRules {
    SCRIPT_NAME_SPACE_VALIDATOR(new ScriptNameSpaceValidator());

    private Validator validator;
    ValidationRules(Validator validator) {
        this.validator = validator;
    }


}

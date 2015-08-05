package validation;

import validation.rules.ScriptNameSpaceRule;

/**
 * Created by mcalancea on 2015-08-04.
 */
public enum ValidationRules {
    SCRIPT_NAME_SPACE_VALIDATOR(new ScriptNameSpaceRule());

    private Rule rule;
    ValidationRules(Rule rule) {
        this.rule = rule;
    }

    public Rule getRule() {
        return rule;
    }

}

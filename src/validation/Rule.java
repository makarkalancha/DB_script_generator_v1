package validation;

import com.google.common.collect.Multimap;
import validation.errors.ErrorCode;

/**
 * Created by mcalancea on 2015-08-04.
 */
public interface Rule {

    public Multimap<ErrorCode, Object> validate();
    public void setObjectToValidate(Object objectToValidate);

}

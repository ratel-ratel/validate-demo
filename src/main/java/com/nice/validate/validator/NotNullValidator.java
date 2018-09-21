package com.nice.validate.validator;

import com.nice.validate.annotations.NotNull;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;

/**
 * NotAllowedNullValidator
 * Created by johnd on 2/22/17.
 */
@Slf4j
public class NotNullValidator extends AbstractValidator {
    @Override
    public String validate(Object object, Annotation annotation, Object value, String fieldName) {
        if ( null == value ) {
            return object.getClass().getSimpleName()  + "." + fieldName + " " + ((NotNull)annotation).message();
        }
        return null;
    }
}

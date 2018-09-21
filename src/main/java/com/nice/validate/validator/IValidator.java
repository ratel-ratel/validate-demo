package com.nice.validate.validator;

import java.lang.annotation.Annotation;

/**
 * AbstractValidator
 * Created by johnd on 2/22/17.
 */
public interface IValidator {
    public String validate(Object object, Annotation annotation, Object value, String fieldName);
}

package com.nice.validate.validator;

import com.nice.validate.annotations.NotEmpty;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;

/**
 * NotAllowedNullValidator
 * Created by johnd on 2/22/17.
 */
@Slf4j
public class NotEmptyValidator extends AbstractValidator {
    @Override
    public String validate(Object object, Annotation annotation, Object value, String fieldName) {
        if ( null == value || value.toString().isEmpty()) {
            return object.getClass().getSimpleName()  + "." + fieldName + " " + ((NotEmpty)annotation).message();//获取class 名称
        }
        return null;
    }
}

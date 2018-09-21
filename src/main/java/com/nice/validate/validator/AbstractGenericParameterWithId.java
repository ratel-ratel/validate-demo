package com.nice.validate.validator;

import com.nice.validate.annotations.NotNull;
import com.nice.validate.constant.ValidatorConditionType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This is the abstract implementation of an ProSa Parameter object.  This abstract object
 * implements an generic toString method which  generates an output for all private attributes of
 * the class where an corresponding getter exists. This getters have to start with
 * <code>get</code>. Subattributes (in a Collection) are also printed!<br>
 * Also it is posible to check if an attribute is optional. This could be  done by the method
 * isOptionalMethod. This method delivers <code>true</code> if the name of the method is added to
 * the included HashMap via the method  addOptionalMethod. This is needed for the validating process!<br>
 * Also there is the method validatWith where you can send a Validator object  into the parameter
 * and let him do his work. Normally this will be the  GenericClassAttributeValidator. Which
 * validates all attributes including  Collections!
 *
 * @author johnd
 */

@Getter
@Setter
@ToString
public abstract class AbstractGenericParameterWithId<T> extends AbstractGenericParameter {
    @NotNull(when = {ValidatorConditionType.UPDATE, ValidatorConditionType.DELETE})
    private T id;
}

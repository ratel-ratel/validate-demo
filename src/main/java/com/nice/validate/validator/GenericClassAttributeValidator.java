package com.nice.validate.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;


/**
 * This class implements a generic validating process for AbstractParameter objects. All attributes
 * which are not optional and are from the declaring class are checked  if they are
 * <code>null</code>. If they are null and not optional a  AttributeValidatorException is thrown.
 * Subattributes which are handled in a  Collection are also checked!
 *
 * @author johnd
 */
@Slf4j
public class GenericClassAttributeValidator implements IClassAttributeValidator {

  /**
   * The instance of the class
   */
  private static IClassAttributeValidator instance = null;

  /**
   * Creates a new GenericClassAttributeValidator object.
   */
  private GenericClassAttributeValidator() {
  }

  /**
   * The singleton getInstance to create the class
   *
   * @return IClassAttributeValidator
   */
  public static synchronized IClassAttributeValidator getInstance() {
    if (instance == null) {
      instance = new GenericClassAttributeValidator();
    }

    return instance;
  }

  /**
   * The validation process for AbstractParameter objects
   *
   * @param param The AbstractParameter which has to be checked
   * @throws AttributeValidatorException Throws if the validation fails
   */
  public synchronized void validate(BaseAbstractParameter param, String condition)//验证
      throws AttributeValidatorException {
    Class clazz = param.getClass();//得到实体的class
    Method[] methods = clazz.getMethods();//得到实体的所有方法

    Iterator methodIter = Arrays.asList(methods).iterator();//
    Method method = null;
    String methodName = null;
    while (methodIter.hasNext()) {
      method = (Method) methodIter.next();//实体的方法
      methodName = method.getName();//得到实体的方法名
      String fieldName = PropertyUtils.getBeanName(methodName);//得到方法对应的字段 grtName --> name
      Annotation[] annotations = PropertyUtils.getFieldAnnotations(param.getClass(), fieldName);//获取此 字段 上面的所有注解
      if (methodName.startsWith("get") &&
          (null != annotations && 0 != annotations.length)) {
        Object methodResult = null;

        try {
          //执行get 方法得到值
          methodResult = method.invoke(param, null);//对带有指定参数的指定对象调用由此 Method 对象表示的底层方法。
        } catch (IllegalArgumentException e) {
          throw new AttributeValidatorException(e.getMessage());
        } catch (IllegalAccessException e) {
          throw new AttributeValidatorException(e.getMessage());
        } catch (InvocationTargetException e) {
          throw new AttributeValidatorException(e.getMessage());
        }

        String className = clazz.getName();
        className = className.substring(className.lastIndexOf('.') + 1);

        for (Annotation annotation : annotations) {
          Class<? extends AbstractValidator> validatorClazz;
          try {
            validatorClazz = (Class<? extends AbstractValidator>) annotation.getClass()
                                                                            .getDeclaredMethod(
                                                                                "validator")
                                                                            .invoke(annotation);
            AbstractValidator abstractValidator = BeanUtils.instantiate(validatorClazz);
            // check if it's optional field
            boolean optional = (null != condition);
            if (optional) {
              String[] whenConditions = {};
              Method whenMethod = annotation.getClass().getDeclaredMethod("when");//得到注解的 when 方法 @NotEmpty(when = {ValidatorConditionType.READ,ValidatorConditionType.UPDATE,ValidatorConditionType.DELETE})
              whenConditions = (String[]) whenMethod.invoke(annotation);
              if (0 != whenConditions.length) {
                for (String when : whenConditions) {
                  if (when.equals(condition)) {
                    optional = false;
                    break;
                  }
                }
              } else {
                optional = false;
              }
            }
            // when is not optional, then validate the field.
            if (!optional && null != abstractValidator) {
              String message = abstractValidator.validate(param, annotation, methodResult, fieldName);
              if (null != message) {
                throw new AttributeValidatorException(message);
              }
            }
            log.debug("{}", annotation.toString());
          } catch (IllegalAccessException e) {
            log.debug("{}", e.getMessage());
          } catch (InvocationTargetException e) {
            log.debug("{}", e.getMessage());
          } catch (NoSuchMethodException e) {
            log.debug("{}", e.getMessage());
          }
        }

        if (methodResult instanceof Collection) {
          Collection col = (Collection) methodResult;
          Iterator iter = col.iterator();
          Object subParam = null;

          while (iter.hasNext()) {
            subParam = iter.next();

            if (subParam instanceof AbstractParameter) {
              AbstractParameter abstractParam = (AbstractParameter) subParam;
              abstractParam.validateWith(this);
            }
          }
        }
      }
    }
  }
}

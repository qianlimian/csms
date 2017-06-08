package org.smartframework.platform.validate;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.smartframework.platform.dto.annotation.DtoClass;
import org.smartframework.platform.dto.annotation.DtoProperty;
import org.smartframework.platform.dto.util.PropertyHelper;
import org.smartframework.platform.validate.beans.ValidatorBean;
import org.smartframework.platform.validate.beans.ValidatorErrorInfo;
import org.smartframework.platform.validate.beans.Validators;
import org.smartframework.utils.FrameUtil;

public class ValidateUtil {
	
	public static Validator getValidator(Object obj) {
		return ((HibernateValidatorConfiguration) ((HibernateValidatorConfiguration) Validation.byProvider(HibernateValidator.class).configure())
				.messageInterpolator(new ResourceBundleMessageInterpolator()))
				.buildValidatorFactory().getValidator();
	}

	public static void validateBean(Object obj, Validators validators) {
		if (obj == null) {
			return;
		}
		Validator validator = getValidator(obj);
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(obj);
		if (constraintViolations.size() > 0) {
			ValidatorBean validatorBean = new ValidatorBean("form", obj.getClass().getName());
			for (ConstraintViolation<Object> c : constraintViolations) {
				ValidatorErrorInfo info = new ValidatorErrorInfo(c.getPropertyPath().toString(), c.getMessage());
				validatorBean.addValidatorErrorInfo(info);
			}
			validators.addValidatorBean(validatorBean);
		}
	}

	public static void validateList(List<?> list, Validators validators) throws Exception {
		if ((list == null) || (list.size() == 0)) {
			return;
		}
		Validator validator = getValidator(list.get(0));
		ValidatorBean validatorBean = new ValidatorBean("grid", list.get(0).getClass().getName());
		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);
			Set<ConstraintViolation<Object>> constraintViolations = validator.validate(obj);
			if (constraintViolations.size() > 0) {
				Object rowId = PropertyHelper.getProperty(obj, "_row_id");
				if ((rowId == null)) {
					throw new Exception("校验的List的bean中不存在_row_id，或者_row_id内容为空");
				}
				for (ConstraintViolation<Object> c : constraintViolations) {
					ValidatorErrorInfo info = new ValidatorErrorInfo(rowId.toString(), c.getPropertyPath().toString(), c.getMessage());
					validatorBean.addValidatorErrorInfo(info);
				}
			}
		}
		if ((validatorBean.getErrors() != null) && (validatorBean.getErrors().size() > 0)) {
			validators.addValidatorBean(validatorBean);
		}
	}

	public static void validateData(Object obj, Validators validators) throws Exception {
		if (obj == null) {
			return;
		}
		validateBean(obj, validators);

		Field[] fields = FrameUtil.getAllFeilds(obj.getClass());
		for (Field field : fields) {
			field.setAccessible(true);
			DtoProperty pro = (DtoProperty) field.getAnnotation(DtoProperty.class);
			if ((pro == null) || (pro.isValidate())) {
				Object element = field.get(obj);
				if (element != null) {
					if ((element instanceof List)) {
						List<?> list = (List<?>) element;
						if ((list != null) && (list.size() > 0)) {
							Object first = list.get(0);
							DtoClass dto = (DtoClass) first.getClass().getAnnotation(DtoClass.class);
							if (dto != null) {
								validateList(list, validators);
							}
						}
					} else {
						DtoClass dto = (DtoClass) element.getClass().getAnnotation(DtoClass.class);
						if (dto != null) {
							validateBean(element, validators);
						}
					}
				}
			}
		}
	}

	public static Boolean hasValidateErrors(Validators validators) {
		if ((validators == null) || (validators.getValidatorBeans() == null)
				|| (validators.getValidatorBeans().size() == 0)) {
			return false;
		}
		return true;
	}
}

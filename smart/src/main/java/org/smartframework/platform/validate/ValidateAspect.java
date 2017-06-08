package org.smartframework.platform.validate;

import java.util.List;
import org.aspectj.lang.ProceedingJoinPoint;

import org.smartframework.platform.dto.annotation.DtoClass;
import org.smartframework.platform.validate.beans.Validators;
import org.smartframework.platform.exception.ValidateException;
import org.smartframework.utils.helper.LogHelper;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ValidateAspect {
	
	private Object dataValidate(ProceedingJoinPoint joinPoint) throws Throwable, ValidateException {
		Validators validators = new Validators();
		try {
			Object[] args = joinPoint.getArgs();
			for (int i = 0; i < args.length; i++) {
				Object arg = args[i];
				if (arg != null) {
					if ((arg instanceof List)) {
						List<?> arglist = (List<?>) arg;
						if ((arglist != null) && (arglist.size() > 0)) {
							Object first = arglist.get(0);
							DtoClass dto = (DtoClass) first.getClass().getAnnotation(DtoClass.class);
							if (dto != null) {
								ValidateUtil.validateList(arglist, validators);
							}
						}
					} else {
						DtoClass dto = (DtoClass) arg.getClass().getAnnotation(DtoClass.class);
						if (dto != null) {
							ValidateUtil.validateData(arg, validators);
						}
					}
				}
			}
			if (ValidateUtil.hasValidateErrors(validators)) {
				LogHelper.log(ValidateAspect.class, LogHelper.DEBUG, "存在校验异常，框架开始组织校验异常并输出");
				ObjectMapper mapper = new ObjectMapper();
				String result = mapper.writeValueAsString(validators);
				throw new ValidateException(result);
			}
			return joinPoint.proceed();
		} catch (Throwable e) {
			LogHelper.log(ValidateAspect.class, LogHelper.DEBUG, e.getMessage());
			throw e;
		}
	}
}

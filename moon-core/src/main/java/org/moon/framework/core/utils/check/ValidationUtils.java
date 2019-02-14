package org.moon.framework.core.utils.check;

import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by 明月   on 2018-09-19 / 16:01
 *
 * @Description: HibernateValidator校验组件的工具类
 * 
 * 
 * Annotation Doc ->
 * 
 * @AssertTrue 用于boolean字段，该字段只能为true
 * 
 * @AssertFalse 该字段的值只能为false
 * 
 * @CreditCardNumber 对信用卡号进行一个大致的验证
 * 
 * @DecimalMax 只能小于或等于该值
 * 
 * @DecimalMin 只能大于或等于该值
 * 
 * @Digits(integer=,fraction=) 检查是否是一种数字的整数、分数,小数位数的数字
 * 
 * @Email 检查是否是一个有效的email地址
 * 
 * @Future 检查该字段的日期是否是属于将来的日期
 * 
 * @Length(min=,max=) 检查所属的字段的长度是否在min和max之间,只能用于字符串
 * 
 * @Max 该字段的值只能小于或等于该值
 * 
 * @Min 该字段的值只能大于或等于该值
 * 
 * @NotNull 不能为null
 * 
 * @NotBlank 不能为空，检查时会将空格忽略
 * 
 * @NotEmpty 不能为空，这里的空是指空字符串
 * 
 * @Null 检查该字段为空
 * 
 * @Past 检查该字段的日期是在过去
 * 
 * @Pattern(regex=,flag=) 被注释的元素必须符合指定的正则表达式
 * 
 * @Range(min=,max=,message=) 被注释的元素必须在合适的范围内
 * 
 * @Size(min=, max=) 检查该字段的size是否在min和max之间，可以是字符串、数组、集合、Map等
 * 
 * @URL(protocol=,host,port)
 * 检查是否是一个有效的URL，如果提供了protocol，host等，则该URL还需满足提供的条件
 * 
 * @Valid 该注解主要用于字段为一个包含其他对象的集合或map或数组的字段，或该字段直接为一个其他对象的引用，
 * 这样在检查当前对象的同时也会检查该字段所引用的对象
 * 
 * //大于0.01，不包含0.01
 * @NotNull
 * @DecimalMin(value = "0.01", inclusive = false)
 * private Integer greaterThan;
 *	
 * //大于等于0.01
 * @NotNull
 * @DecimalMin(value = "0.01", inclusive = true)
 * private BigDecimal greatOrEqualThan;
 *
 * @Length(min = 1, max = 20, message = "message不能为空")
 * //不能将Length错用成Range
 * //@Range(min = 1, max = 20, message = "message不能为空")
 * private String message;
 */
public final class ValidationUtils {

	private ValidationUtils() {
	}

	public static class ValidationResult {
		// 校验结果是否含有违反约束
		private boolean hasErrors;

		// 违反约束的信息
		private Map<String, String> errorMsg;

		public boolean isHasErrors() {
			return hasErrors;
		}

		public void setHasErrors(boolean hasErrors) {
			this.hasErrors = hasErrors;
		}

		public Map<String, String> getErrorMsg() {
			return errorMsg;
		}

		public void setErrorMsg(Map<String, String> errorMsg) {
			this.errorMsg = errorMsg;
		}

		@Override
		public String toString() {
			return "ValidationResult [hasErrors=" + hasErrors + ", errorMsg=" + errorMsg + "]";
		}
	}

	// 快速失败校验器
	private static Validator failFastValidator;
	// 默认校验器(no fail fast)
	private static Validator defaultValidator;
	// 快速失败标记
	private static final Boolean IS_FAIL_FAST_VALIDATOR = Boolean.TRUE;
	private static final String NO_FAIL_FAST_VALIDATOR = Boolean.FALSE.toString();

	static {
		failFastValidator = Validation.byProvider(HibernateValidator.class).configure()
				.failFast(ValidationUtils.IS_FAIL_FAST_VALIDATOR).buildValidatorFactory().getValidator();

		defaultValidator = Validation.byProvider(HibernateValidator.class).configure()
				.addProperty("hibernate.validator.fail_fast", ValidationUtils.NO_FAIL_FAST_VALIDATOR)
				.buildValidatorFactory().getValidator();
	}

	/**
	 * 获取默认校验器(no fail fast validator)
	 */
	public static Validator getValidator() {
		return getValidator(false);
	}

	/**
	 * 根据参数获取校验器
	 */
	public static Validator getValidator(boolean isFailFast) {
		return isFailFast ? failFastValidator : defaultValidator;
	}

	/**
	 * 采用默认校验器校验Model并返回违反记录(若无违反记录则返回空集合)
	 */
	public static <T> Set<ConstraintViolation<T>> validateModel(T model) {
		return validateModel(model, false);
	}

	/**
	 * 根据参数选择校验器校验Model并返回违反记录(若无违反记录则返回空集合)
	 */
	public static <T> Set<ConstraintViolation<T>> validateModel(T model, boolean isFailFast) {
		return getValidator(isFailFast).validate(model);
	}

	/**
	 * 检验实体的某个字段
	 */
	public static <T> Set<ConstraintViolation<T>> validateModelProperty(T model, String propertyName) {
		return getValidator(false).validateProperty(model, propertyName);
	}

	/**
	 * 采用默认校验器校验Model并返回检验结果
	 */
	public static <T> ValidationResult validateModelAndReturnResults(T model) {
		return validateModelAndReturnResults(model, false);
	}

	/**
	 * 根据参数选择校验器校验Model并返回检验结果
	 */
	public static <T> ValidationResult validateModelAndReturnResults(T model, boolean isFailFast) {
		final ValidationResult result = new ValidationResult();
		// 组装错误信息
		final Map<String, String> resultMessage = new HashMap<>();
		for (Iterator<ConstraintViolation<T>> iterator = getValidator(isFailFast).validate(model).iterator(); iterator
				.hasNext();) {
			ConstraintViolation<T> next = iterator.next();
			resultMessage.put(next.getPropertyPath().toString(), next.getMessage());
		}
		// 组装返回结果
		if (resultMessage.size() > 0) {
			result.setHasErrors(true);
			result.setErrorMsg(resultMessage);
		} else
			result.setHasErrors(false);
		return result;
	}

	/**
	 * 根据参数选择校验器校验Model并返回检验结果
	 */
	@SuppressWarnings("rawtypes")
	public static <T> ValidationResult validateModelAndReturnResultsByStream(T model, boolean isFailFast) {
		final ValidationResult result = new ValidationResult();

		// 采取错误信息
		Map<String, String> resultMessage = getValidator(isFailFast).validate(model).stream()
				.collect(Collectors.toMap((ConstraintViolation cv) -> cv.getPropertyPath().toString(),
						(ConstraintViolation cv) -> cv.getMessage()));
		// 组装返回结果
		if (resultMessage.size() > 0) {
			result.setHasErrors(true);
			result.setErrorMsg(resultMessage);
		} else
			result.setHasErrors(false);
		return result;
	}

	/**
	 * Assert Model By Default Validator
	 */
	public static void assertModel(Object model) {
		if (null != model) {
			// 组装错误信息
			final Map<String, String> resultMessage = new HashMap<>();
			for (Iterator<ConstraintViolation<Object>> iterator = getValidator(false).validate(model)
					.iterator(); iterator.hasNext();) {
				ConstraintViolation<Object> next = iterator.next();
				resultMessage.put(next.getPropertyPath().toString(), next.getMessage());
			}
			if (resultMessage.size() > 0)
				throw new RuntimeException("Model Assert Result -> " + resultMessage);
		} else throw new IllegalArgumentException("parameter model cannot by empty");
	}
}
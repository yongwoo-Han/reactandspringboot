package com.okta.developer.util;

import java.util.Collection;
import java.util.stream.Stream;

import javax.validation.Validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

/**
 * 기본적으로 파라미터로 넘어온 Null Check는 @Validator 하지만 
 * 모든 파라미터(Collection 포함)를 오브젝트로 받기 때문에 문제가 있다.
 * 즉, List와 같은 Collection 타입은 별도로 SpringValidatorAdapter로 구현해야한다.
 *  * @author han
 *
 */
@Component
public class CollectionValidation implements Validator {

	private SpringValidatorAdapter validator;
	
	public CollectionValidation() {
		// TODO Auto-generated constructor stub
		this.validator = new SpringValidatorAdapter(Validation.buildDefaultValidatorFactory().getValidator());
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Collection.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Stream<?> collection = (Stream<?>) target;
		collection.forEach(object->validator.validate(object, errors));
	}
}

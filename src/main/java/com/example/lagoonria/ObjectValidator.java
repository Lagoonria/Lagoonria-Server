package com.example.lagoonria;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ObjectValidator<T> {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    public Map<String, String> validate (T object) {

        Map<String, String> fieldErrors = new HashMap<>();

        Set<ConstraintViolation<T>> violations = validator.validate(object);

        for (ConstraintViolation<T> violation : violations) {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            fieldErrors.putIfAbsent(fieldName, errorMessage);
        }

        return fieldErrors;
    }

}

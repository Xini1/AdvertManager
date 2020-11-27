package by.pakodan.advertmanager.domain;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
class ValidationExecutor<T> {

    private final T instance;
    private Set<ConstraintViolation<T>> constraintViolations;

    ValidationExecutor<T> validate() {
        constraintViolations = Validation.buildDefaultValidatorFactory()
                .getValidator()
                .validate(instance);

        return this;
    }

    ValidationErrorHandler errorHandler() {
        return new ValidationErrorHandler();
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    class ValidationErrorHandler {

        ValidationErrorHandler log() {
            constraintViolations.forEach(constraintViolation -> log.warn("{} - {}",
                    constraintViolation.getPropertyPath(), constraintViolation.getMessage()));

            return this;
        }

        void ifPresentThrowException() {
            if (!constraintViolations.isEmpty()) {
                throw new ConstraintViolationException(constraintViolations);
            }
        }
    }
}

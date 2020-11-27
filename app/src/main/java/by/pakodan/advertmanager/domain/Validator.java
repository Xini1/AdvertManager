package by.pakodan.advertmanager.domain;

import by.pakodan.advertmanager.domain.exception.ValidationException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@RequiredArgsConstructor
class Validator<T> {

    private final T instance;
    private final List<String> errors = new ArrayList<>();

    Validator<T> validate(Predicate<T> constraint, String message) {
        if (!constraint.test(instance)) {
            errors.add(message);
        }

        return this;
    }

    ValidationErrorHandler errorHandler() {
        return new ValidationErrorHandler();
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    class ValidationErrorHandler {

        void ifPresentThrowException() {
            if (!errors.isEmpty()) {
                throw new ValidationException(getFullMessage(errors));
            }
        }

        private String getFullMessage(List<String> errors) {
            return "Constraint violations:\n" + String.join("\n", errors);
        }
    }
}

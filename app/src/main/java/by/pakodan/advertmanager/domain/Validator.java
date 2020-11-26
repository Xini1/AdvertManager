package by.pakodan.advertmanager.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@RequiredArgsConstructor
class Validator<T> {

    private final T instance;
    @Getter
    private final List<String> errors = new ArrayList<>();

    Validator<T> validate(Predicate<T> constraint, String message) {
        if (!constraint.test(instance)) {
            errors.add(message);
        }

        return this;
    }
}

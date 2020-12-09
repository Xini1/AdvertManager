package by.pakodan.advertservice.utils;

import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

@UtilityClass
public class Preconditions {

    public void checkNotNull(Object object, String parameterName) {
        if (Objects.isNull(object)) {
            throw new ValidationException(String.format("%s must be not null", parameterName));
        }
    }

    public void checkSizeGreaterThan(Collection<?> collection, int threshold, String parameterName) {
        if (collection.size() <= threshold) {
            throw new ValidationException(String.format("%s must have size of %d, but has %d",
                    parameterName, threshold, collection.size()));
        }
    }

    public void checkValueGreaterThan(long value, long threshold, String parameterName) {
        if (value <= threshold) {
            throw new ValidationException(String.format("%s must be greater than %d, but is %d",
                    parameterName, threshold, value));
        }
    }

    public void checkNotBlank(String string, String parameterName) {
        if (Objects.isNull(string) || Objects.equals(string.replace(" ", "").length(), 0)) {
            throw new ValidationException(String.format("%s \"%s\" must be not blank", parameterName, string));
        }
    }

    public void checkMatchRegex(String string, String regex, String parameterName) {
        if (!string.matches(regex)) {
            throw new ValidationException(String.format("%s \"%s\" must match regular expression %s",
                    parameterName, string, regex));
        }
    }

    public void checkUUID(String string, String parameterName) {
        try {
            UUID.fromString(string);
        } catch (IllegalArgumentException e) {
            throw new ValidationException(String.format("%s \"%s\" must be valid UUID", parameterName, string));
        }
    }

    public static class ValidationException extends RuntimeException {

        public ValidationException(String message) {
            super(message);
        }
    }
}

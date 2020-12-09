package by.pakodan.advertservice.utils;

import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public class PhoneNumberUtils {

    public String formatPhoneNumber(String rawPhoneNumber) {
        if (Objects.isNull(rawPhoneNumber)) {
            return null;
        }

        String cleanPhoneNumber = rawPhoneNumber.replace("(", "")
                .replace(")", "")
                .replace("-", "")
                .trim()
                .replaceFirst("80", "+375");

        if (Objects.equals(cleanPhoneNumber.length(), 6)) {
            cleanPhoneNumber = "+375232" + cleanPhoneNumber;
        }

        return cleanPhoneNumber;
    }
}

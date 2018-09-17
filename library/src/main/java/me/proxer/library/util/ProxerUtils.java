package me.proxer.library.util;

import com.squareup.moshi.Json;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;

/**
 * Utility class for internal use.
 *
 * @author Ruben Gees
 */
@UtilityClass
public class ProxerUtils {

    /**
     * Returns the name of the passed enum instance (Using that specified in the {@code @Json} annotation).
     */
    @SuppressWarnings("checkstyle:designforextension") // Lombok makes it static.
    @Nullable
    public String getApiEnumName(final Enum<?> it) {
        try {
            return it.getDeclaringClass().getField(it.name()).getAnnotation(Json.class).name();
        } catch (NoSuchFieldException ignored) {
            return null;
        }
    }

    /**
     * Returns the name of the passed enum instance (Using that specified in the {@code @Json} annotation).
     */
    @SuppressWarnings("checkstyle:designforextension") // Lombok makes it static.
    public String getSafeApiEnumName(final Enum<?> it) {
        final String result = getApiEnumName(it);

        if (result != null) {
            return result;
        } else {
            throw new IllegalStateException("Field " + it.name() + " not found in Enum "
                    + it.getDeclaringClass().getName());
        }
    }

    /**
     * Converts the passed {@code value} string to an instance of the passed enum {@code type}.
     * <p>
     * If the conversion is not possible, because the passed string was invalid, null is returned.
     * If an invalid enum type is passed (one that is not in this library), an exception is thrown.
     */
    @SuppressWarnings("checkstyle:designforextension") // Lombok makes it static.
    @Nullable
    public <T extends Enum<T>> T toApiEnum(final Class<T> type, final String value) {
        for (final Field field : type.getFields()) {
            final Json annotation = field.getAnnotation(Json.class);

            if (annotation != null && annotation.name().equalsIgnoreCase(value)) {
                return Enum.valueOf(type, field.getName());
            }
        }

        return null;
    }

    /**
     * Converts the passed {@code value} string to an instance of the passed enum {@code type}.
     * <p>
     * If the conversion is not possible, because the passed string was invalid, null is returned.
     * If an invalid enum type is passed (one that is not in this library), an exception is thrown.
     */
    @SuppressWarnings("checkstyle:designforextension") // Lombok makes it static.
    public <T extends Enum<T>> T toSafeApiEnum(final Class<T> type, final String value) {
        final T result = toApiEnum(type, value);

        if (result != null) {
            return result;
        } else {
            throw new IllegalStateException("Value " + value + " not found for Enum " + type.getName());
        }
    }

    /**
     * Joins an Iterator with a given delimiter to a {@link String} and returns the result.
     * <p>
     * The Iterator can have any type, toString is called on the individual items.
     */
    @SuppressWarnings("checkstyle:designforextension") // Lombok makes it static.
    public String join(final String delimiter, final Iterable<?> iterable) {
        final Iterator<?> iterator = iterable.iterator();

        if (!iterator.hasNext()) {
            return "";
        }

        final StringBuilder builder = new StringBuilder().append(iterator.next());

        while (iterator.hasNext()) {
            builder.append(delimiter).append(iterator.next());
        }

        return builder.toString();
    }

    /**
     * Joins an Iterator of enums with a given delimiter to a {@link String} and returns the result.
     * <p>
     * For String conversion, the {@link #getApiEnumName(Enum)} method is used, so this method will only work with enums
     * from this library.
     */
    @SuppressWarnings("checkstyle:designforextension") // Lombok makes it static.
    public String joinEnums(final String delimiter, final EnumSet<?> enums) {
        final ArrayList<String> convertedEnums = new ArrayList<>();

        for (final Enum<?> value : enums) {
            convertedEnums.add(ProxerUtils.getSafeApiEnumName(value));
        }

        return join(delimiter, convertedEnums);
    }

    /**
     * Checks if a String is blank, as in that it only contains whitespace characters or is empty.
     */
    @SuppressWarnings("checkstyle:designforextension") // Lombok makes it static.
    public boolean isBlank(final String subject) {
        for (int i = 0; i < subject.length(); i++) {
            if (!Character.isWhitespace(subject.charAt(i))) {
                return false;
            }
        }

        return true;
    }
}

package com.choimroc.demo.tool;

import java.util.Collection;

/**
 * The type Validator utils.
 *
 * @author choimroc
 * @since 2019 /3/9
 */
public class ValidatorUtils {

    /**
     * Is null boolean.
     *
     * @param objects the objects
     * @return the boolean
     */
    public static boolean isNull(Object ...objects) {
        for (Object obj : objects) {
            if (obj == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Is not null boolean.
     *
     * @param objects the objects
     * @return the boolean
     */
    public static boolean nonNull(Object ...objects) {
        return !isNull(objects);
    }

    /**
     * Is blank boolean.
     *
     * @param str the str
     * @return the boolean
     */
    public static boolean isBlank(String str) {
        return isNull(str) || str.isEmpty() || str.trim().isEmpty() || "null".equals(str);
    }

    /**
     * Is blank boolean.
     *
     * @param strings the strings
     * @return the boolean
     */
    public static boolean isBlank(String... strings) {
        for (String str : strings) {
            if (isBlank(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Is not blank boolean.
     *
     * @param strings the strings
     * @return the boolean
     */
    public static boolean nonBlank(String... strings) {
        return !isBlank(strings);
    }

    /**
     * Is empty boolean.
     *
     * @param <E>         the type parameter
     * @param collections the collections
     * @return the boolean
     */
    public static <E> boolean isEmpty(Collection<E>... collections) {
        for (Collection collection : collections) {
            if (isNull(collection) || collection.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Is not empty boolean.
     *
     * @param <E>         the type parameter
     * @param collections the collections
     * @return the boolean
     */
    public static <E> boolean nonEmpty(Collection<E> ...collections) {
        return !isEmpty(collections);
    }
}

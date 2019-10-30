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
     * @param obj the obj
     * @return the boolean
     */
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    /**
     * Is not null boolean.
     *
     * @param obj the obj
     * @return the boolean
     */
    public static boolean isNotNull(Object obj) {
        return obj != null;
    }

    /**
     * Is not blank boolean.
     *
     * @param strings the strings
     * @return the boolean
     */
    public static boolean isNotBlank(String... strings) {
        return !isBlank(strings);
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
     * Is empty boolean.
     *
     * @param <E>        the type parameter
     * @param collection the collection
     * @return the boolean
     */
    public static <E> boolean isEmpty(Collection<E> collection) {
        return collection.isEmpty();
    }

    /**
     * Is not empty boolean.
     *
     * @param <E>        the type parameter
     * @param collection the collection
     * @return the boolean
     */
    public static <E> boolean isNotEmpty(Collection<E> collection) {
        return !isEmpty(collection);
    }
}

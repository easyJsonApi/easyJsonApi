package org.easyJsonApi.asserts;

public class Assert {

    public static boolean notEmpty(String value) {
        return !isEmpty(value);
    }

    public static boolean isEmpty(String value) {

        if (value != null && !value.isEmpty()) {
            return false;
        }
        return true;
    }

    public static boolean notNull(Object value) {
        return !isNull(value);
    }

    public static boolean isNull(Object value) {
        if (value == null) {
            return true;
        }

        return false;
    }

    public static boolean isNull(Object... values) {

        boolean anyNotNullable = false;

        int index = 0;

        while (index < values.length && !anyNotNullable) {
            if (notNull(values[index])) {
                anyNotNullable = true;
            }
            index++;
        }

        return !anyNotNullable;
    }

}

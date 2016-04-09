package org.easyJsonApi.asserts;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AssertTest {

    @Test
    public void isNullTest() {

        assertFalse(Assert.isNull("test", null, ""));
        assertFalse(Assert.isNull("test", "5", ""));
        assertTrue(Assert.isNull(null, null, null));
        assertFalse(Assert.isNull("test"));

        Object obj = null;

        assertTrue(Assert.isNull(obj));

    }

    @Test
    public void notNullTest() {

        assertTrue(Assert.notNull("test"));

    }

    @Test
    public void isEmptyTest() {

        assertTrue(Assert.isEmpty(""));
        assertTrue(Assert.isEmpty(null));
        assertFalse(Assert.isEmpty("Test"));

    }

    @Test
    public void notEmptyTest() {

        assertTrue(Assert.notEmpty("Test"));
        assertFalse(Assert.notEmpty(""));

    }

}

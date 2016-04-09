package org.easyJsonApi.tools;

import org.easyJsonApi.exceptions.EasyJsonApiInvalidPackageException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class EasyJsonApiConfigTest {

    @Test
    public void constructionTest() throws EasyJsonApiInvalidPackageException {

        EasyJsonApiConfig configDefaultConstruct = new EasyJsonApiConfig();

        Assert.assertNotNull(configDefaultConstruct);

        EasyJsonApiConfig configConstructWithString = new EasyJsonApiConfig("org.easyJsonApi.entities.test");

        Assert.assertNotNull(configConstructWithString);
        Assert.assertNotEquals(0, configConstructWithString.getPackagesSearched().length);

    }

    // FIXME: Find solution to test this exception
    @Ignore
    @Test(expected = EasyJsonApiInvalidPackageException.class)
    public void setInvalidPackagesToSearchTest() throws EasyJsonApiInvalidPackageException {

        new EasyJsonApiConfig("invalid.test");

    }

}

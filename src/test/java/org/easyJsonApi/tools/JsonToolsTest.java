/*
 * #%L
 * EasyJsonApi
 * %%
 * Copyright (C) 2016 EasyJsonApi
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.easyJsonApi.tools;

import org.easyJsonApi.exceptions.EasyJsonApiRuntimeException;
import org.junit.Assert;
import org.junit.Test;

import com.google.gson.JsonObject;

public class JsonToolsTest {

    @Test
    public void getStringInsideJsonTest() {

        JsonObject objTest = new JsonObject();
        objTest.addProperty("Type", "Books");
        objTest.addProperty("Car", "Mercedes");

        String mercedes = JsonTools.getStringInsideJson("Car", objTest);

        Assert.assertEquals(mercedes, "Mercedes");
        Assert.assertNull(JsonTools.getStringInsideJson("", objTest));
        Assert.assertNull(JsonTools.getStringInsideJson("InvalidTest", objTest));

    }

    @Test(expected = EasyJsonApiRuntimeException.class)
    public void insertObjectExceptionTest() {

        JsonObject objTestActual = new JsonObject();
        objTestActual.addProperty("Type", "Books");

        JsonTools.insertObject(objTestActual, "Transports", new Integer(20));

    }

    @Test
    public void insertObjectTest() {

        JsonObject objTestExpected = new JsonObject();
        objTestExpected.addProperty("Type", "Books");

        JsonObject objTestActual = new JsonObject();
        objTestActual.addProperty("Type", "Books");

        JsonTools.insertObject(null, "Car", new String());
        Assert.assertEquals(objTestExpected, objTestActual);

        JsonTools.insertObject(objTestActual, "Car", null);
        Assert.assertEquals(objTestExpected, objTestActual);

        JsonTools.insertObject(objTestActual, "Car", new String());
        Assert.assertEquals(objTestExpected, objTestActual);

        JsonObject jsonElemActual = new JsonObject();
        jsonElemActual.addProperty("Bicicleta", "Rodas");

        JsonTools.insertObject(objTestActual, "Car", new String());
        Assert.assertEquals(objTestExpected, objTestActual);

        JsonTools.insertObject(objTestActual, "Transports", jsonElemActual);
        Assert.assertNotEquals(objTestExpected, objTestActual);

        JsonTools.insertObject(objTestActual, "Transports", new String("NOT_AVAILABLE"));
        Assert.assertNotEquals(objTestExpected, objTestActual);

    }

}

package org.easyJsonApi.adapters;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.easyJsonApi.tools.EasyJsonApiConfig;

import com.google.gson.reflect.TypeToken;

public class EasyJsonApiMachine implements TypeControl {

    private LinkedList<Class<?>> classesUsedInJson = new LinkedList<>();

    private EasyJsonApiConfig config;

    protected Map<EasyJsonApiTypeToken, Type> tokenTypesToUse = new HashMap<>();

    @Override
    public void setClassesUsed(Class<?>... clazz) {

        if (this.classesUsedInJson.isEmpty()) {
            this.classesUsedInJson.addAll(Arrays.asList(clazz));
        } else {
            this.classesUsedInJson.retainAll(Arrays.asList(clazz));
            if (this.classesUsedInJson.isEmpty()) {
                this.classesUsedInJson.addAll(Arrays.asList(clazz));
            }
        }

        for (Map.Entry<EasyJsonApiTypeToken, List<Class<?>>> clazzMapping : this.config.getClassesParsed().entrySet()) {
            List<Class<?>> classes = clazzMapping.getValue();

            for (Class<?> cla : classes) {
                if (this.classesUsedInJson.contains(cla)) {
                    Type classType = TypeToken.get(cla).getType();
                    this.tokenTypesToUse.put(clazzMapping.getKey(), classType);
                }
            }
        }
    }

    @Override
    public void setConfig(EasyJsonApiConfig config) {
        this.config = config;
    }

}

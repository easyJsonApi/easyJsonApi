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
package org.easyJsonApi.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.easyJsonApi.adapters.EasyJsonApiTypeToken;
import org.easyJsonApi.annotations.Attributes;
import org.easyJsonApi.annotations.Meta;
import org.easyJsonApi.annotations.MetaRelationship;
import org.easyJsonApi.asserts.Assert;
import org.easyJsonApi.exceptions.EasyJsonApiInvalidPackageException;

import com.google.common.reflect.ClassPath;

/**
 * The EasyJsonApiConfig it is necessary to configure the {@link EasyJsonApi}.
 * This class allows to parsing the packages defined into the
 * {@link EasyJsonApiConfig#packagesSearched} inside the class
 * 
 * @author Nuno Bento (nbento.neves@gmail.com)
 * @version %I%, %G%
 */
public class EasyJsonApiConfig {

    private Map<EasyJsonApiTypeToken, List<Class<?>>> classesParsed = new HashMap<>();

    private String[] packagesSearched;

    /**
     * The default constructor
     */
    public EasyJsonApiConfig() {}

    /**
     * The constructor with packages attributes
     * 
     * @param packages
     *            the packages needs to be search and parsing
     * @throws EasyJsonApiInvalidPackageException
     */
    public EasyJsonApiConfig(String... packages) throws EasyJsonApiInvalidPackageException {
        setPackagesToSearch(packages);
    }

    /**
     * Get the array with all classes parsed
     * 
     * @return the classesParsed the classes parsed
     */
    public Map<EasyJsonApiTypeToken, List<Class<?>>> getClassesParsed() {
        return classesParsed;
    }

    /**
     * Get the array with packages searched
     * 
     * @return the packagesSearched the packages searched
     */
    public String[] getPackagesSearched() {
        return packagesSearched;
    }

    /**
     * Set the packages to search
     * 
     * @param packages
     *            the packages needs to be search and parsing
     * @throws EasyJsonApiInvalidPackageException
     */
    public void setPackagesToSearch(String... packages) throws EasyJsonApiInvalidPackageException {

        this.packagesSearched = packages;

        for (String packageToSearch : packages) {
            try {
                ClassPath classpath = ClassPath.from(getClass().getClassLoader());

                List<Class<?>> attrClasses = new ArrayList<>();
                List<Class<?>> metaClasses = new ArrayList<>();
                List<Class<?>> metaRelationshipsClasses = new ArrayList<>();

                for (ClassPath.ClassInfo classInfo : classpath.getTopLevelClasses(packageToSearch)) {

                    Class<?> clazz = classInfo.load();

                    if (Assert.notNull(clazz.getAnnotation(Attributes.class))) {
                        attrClasses.add(clazz);
                    } else if (Assert.notNull(clazz.getAnnotation(Meta.class))) {
                        metaClasses.add(clazz);
                    } else if (Assert.notNull(clazz.getAnnotation(MetaRelationship.class))) {
                        metaRelationshipsClasses.add(clazz);
                    }
                }

                this.classesParsed.put(EasyJsonApiTypeToken.TOKEN_ATTR, attrClasses);
                this.classesParsed.put(EasyJsonApiTypeToken.TOKEN_META, metaClasses);
                this.classesParsed.put(EasyJsonApiTypeToken.TOKEN_META_RELATIONSHIP, metaRelationshipsClasses);

            } catch (IOException ex) {
                throw new EasyJsonApiInvalidPackageException("Invalid packages inserted!", ex);
            }
        }
    }

}

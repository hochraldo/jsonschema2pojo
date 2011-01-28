/**
 * Copyright © 2010 Nokia
 *
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
 */

package com.googlecode.jsonschema2pojo.rules;

import java.util.Iterator;

import org.codehaus.jackson.JsonNode;

import com.googlecode.jsonschema2pojo.SchemaMapper;
import com.sun.codemodel.JDefinedClass;

/**
 * Applies the "properties" schema rule.
 * 
 * @see <a
 *      href="http://tools.ietf.org/html/draft-zyp-json-schema-02#section-5.2">http://tools.ietf.org/html/draft-zyp-json-schema-02#section-5.2</a>
 */
public class PropertiesRule implements SchemaRule<JDefinedClass, JDefinedClass> {
    
    private final SchemaMapper mapper;
    
    public PropertiesRule(SchemaMapper mapper) {
        this.mapper = mapper;
    }
    
    /**
     * Applies this schema rule to take the required code generation steps.
     * <p>
     * For each property present within the properties node, this rule will
     * invoke the 'property' rule provided by the given schema mapper.
     * 
     * @param nodeName
     *            the name of the node for which properties are being added
     * @param node
     *            the properties node, containing property names and their
     *            definition
     * @param jclass
     *            the Java type which will have the given properties added
     * @return the given jclass
     */
    @Override
    public JDefinedClass apply(String nodeName, JsonNode node, JDefinedClass jclass) {
        
        for (Iterator<String> properties = node.getFieldNames(); properties.hasNext();) {
            String property = properties.next();
            
            mapper.getPropertyRule().apply(property, node.get(property), jclass);
        }
        
        return jclass;
    }
}

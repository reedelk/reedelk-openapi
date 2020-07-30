package com.reedelk.openapi.v3.deserializer;

import com.reedelk.openapi.AbstractDeserializer;
import com.reedelk.openapi.v3.DeserializerContext;
import com.reedelk.openapi.v3.model.ParameterLocation;
import com.reedelk.openapi.v3.model.ParameterObject;
import com.reedelk.openapi.v3.model.ParameterStyle;
import com.reedelk.openapi.v3.model.Schema;

import java.util.Map;

public class ParameterObjectDeserializer extends AbstractDeserializer<ParameterObject> {

    @SuppressWarnings("unchecked")
    @Override
    public ParameterObject deserialize(DeserializerContext context, Map<String, Object> serialized) {
        ParameterObject parameterObject = new ParameterObject();

        parameterObject.setName(getString(serialized, "name"));
        parameterObject.setDescription(getString(serialized, "description"));

        // Parameter 'in'
        String inValue = getString(serialized, "in");
        if (inValue != null) {
            parameterObject.setIn(ParameterLocation.valueOf(inValue));
        }

        // Parameter 'style'
        String styleValue = getString(serialized, "style");
        if (styleValue != null) {
            parameterObject.setStyle(ParameterStyle.valueOf(styleValue));
        }

        // Parameter schema
        if (serialized.containsKey("schema")) {
            Map<String, Object> schemaMap = (Map<String, Object>) serialized.get("schema");
            Schema schema = context.deserialize(Schema.class, schemaMap);
            parameterObject.setSchema(schema);
        }

        parameterObject.setExample(getString(serialized, "example"));
        parameterObject.setExplode(getBoolean(serialized, "explode"));
        parameterObject.setDeprecated(getBoolean(serialized, "deprecated"));
        parameterObject.setRequired(getBoolean(serialized, "required"));
        parameterObject.setAllowEmptyValue(getBoolean(serialized, "allowEmptyValue"));
        parameterObject.setAllowReserved(getBoolean(serialized, "allowReserved"));
        return parameterObject;
    }
}
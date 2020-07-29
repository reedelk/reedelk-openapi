package com.reedelk.openapi;

import com.reedelk.openapi.v3.Example;
import com.reedelk.openapi.v3.Schema;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class OpenApiSerializableAbstract implements OpenApiSerializable {

    private static final String JSON_PROPERTY_EXAMPLE = "example";
    private static final String JSON_PROPERTY_SCHEMA = "schema";

    protected void set(Map<String, Object> parent, Schema schema) {
        parent.put(JSON_PROPERTY_SCHEMA, schema.serialize());
    }

    protected void set(Map<String, Object> parent, Example example) {
        if (example != null) {
            parent.put(JSON_PROPERTY_EXAMPLE, new JSONObject(example.data()).toMap());
        }
    }

    protected void setMapSerializable(Map<String,Object> parent, String propertyName, Map<String, ? extends OpenApiSerializable> serializableMap) {
        if (serializableMap != null && !serializableMap.isEmpty()) {
            Map<String, Object> map = new LinkedHashMap<>();
            serializableMap.forEach((key, mapObject) -> set(map, key, mapObject));
            parent.put(propertyName, map);
        }
    }

    protected void set(Map<String,Object> object, String propertyName, List<? extends OpenApiSerializable> serializableList) {
        if (serializableList != null && !serializableList.isEmpty()) {
            List<Map<String,Object>> listOfItems = new ArrayList<>();
            serializableList.forEach(serializable -> listOfItems.add(serializable.serialize()));
            object.put(propertyName, listOfItems);
        }
    }

    protected void set(Map<String,Object> object, String propertyName, OpenApiSerializable serializable) {
        if (serializable != null) {
            object.put(propertyName, serializable.serialize());
        }
    }

    protected void setList(Map<String,Object> object, String propertyName, List<String> items) {
        if (items != null && !items.isEmpty()) object.put(propertyName, items);
    }

    protected void set(Map<String,Object> object, String propertyName, Boolean aBoolean) {
        if (aBoolean != null) object.put(propertyName, aBoolean);
    }

    protected void set(Map<String,Object> object, String propertyName, Map<String,Object> value) {
        if (value != null) object.put(propertyName, value);
    }

    protected void set(Map<String,Object> object, String propertyName, String value) {
        if (value != null) object.put(propertyName, value);
    }

    protected String getString(Map<String,Object> data, String propertyName) {
        return (String) data.get(propertyName);
    }

    protected Boolean getBoolean(Map<String,Object> data, String propertyName) {
        return (Boolean) data.get(propertyName);
    }

    @SuppressWarnings("unchecked")
    protected Map<String,Object> getMap(Map<String,Object> data, String propertyName) {
        return (Map<String,Object>) data.get(propertyName);
    }

    @SuppressWarnings("unchecked")
    protected List<Map<String,Object>> getList(Map<String,Object> data, String propertyName) {
        return (List<Map<String,Object>>) data.get(propertyName);
    }
}

package com.reedelk.openapi.v3.serializer;

import com.reedelk.openapi.commons.AbstractSerializer;
import com.reedelk.openapi.commons.NavigationPath;
import com.reedelk.openapi.v3.SerializerContext;
import com.reedelk.openapi.v3.model.OpenApiObject;
import com.reedelk.openapi.v3.model.TagObject;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class OpenApiObjectSerializer extends AbstractSerializer<OpenApiObject> {

    @Override
    public Map<String, Object> serialize(SerializerContext context, NavigationPath navigationPath, OpenApiObject input) {
        Map<String, Object> map = new LinkedHashMap<>();

        set(map, "openapi", input.getOpenapi()); // REQUIRED

        Map<String, Object> serializedInfo = context.serialize(navigationPath.with("info"), input.getInfo());
        set(map, "info", serializedInfo); // REQUIRED

        List<Map<String, Object>> mappedServers = input
                .getServers()
                .stream()
                .map(serverObject -> {
                    NavigationPath navPath = navigationPath
                            .with("servers")
                            .with("serverUrl", serverObject.getUrl());
                    return context.serialize(navPath, serverObject);
                })
                .collect(toList());
        map.put("servers", mappedServers);

        Map<String, Object> serializedPaths = context.serialize(navigationPath.with("paths"), input.getPaths());
        set(map, "paths", serializedPaths); // REQUIRED

        Map<String, Object> serializedComponents = context.serialize(navigationPath.with("components"), input.getComponents());
        set(map, "components", serializedComponents);


        List<TagObject> tags = input.getTags();
        if (tags != null && !tags.isEmpty()) {
            List<Map<String, Object>> mappedTags = tags
                    .stream()
                    .map(tagObject -> {
                        NavigationPath navPath = navigationPath
                                .with("tags")
                                .with("tagName", tagObject.getName());
                        return context.serialize(navPath, tagObject);
                    })
                    .collect(toList());
            map.put("tags", mappedTags);
        }
        return map;
    }
}

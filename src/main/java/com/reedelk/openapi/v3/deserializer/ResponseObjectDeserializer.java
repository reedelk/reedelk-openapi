package com.reedelk.openapi.v3.deserializer;

import com.reedelk.openapi.commons.AbstractDeserializer;
import com.reedelk.openapi.v3.DeserializerContext;
import com.reedelk.openapi.v3.model.HeaderObject;
import com.reedelk.openapi.v3.model.MediaTypeObject;
import com.reedelk.openapi.v3.model.ResponseObject;

import java.util.LinkedHashMap;
import java.util.Map;

public class ResponseObjectDeserializer extends AbstractDeserializer<ResponseObject> {

    @SuppressWarnings("unchecked")
    @Override
    public ResponseObject deserialize(DeserializerContext context, Map<String, Object> serialized) {
        ResponseObject responseObject = new ResponseObject();

        responseObject.setDescription(getString(serialized, "description"));

        // Content
        if (serialized.containsKey("content")) {
            Map<String, MediaTypeObject> contentMediaType = new LinkedHashMap<>();
            Map<String,Map<String,Object>> contentMap = (Map<String, Map<String, Object>>) serialized.get("content");
            contentMap.forEach((contentType, mediaTypeObjectMap) -> {
                MediaTypeObject mediaTypeObject = context.deserialize(MediaTypeObject.class, mediaTypeObjectMap);
                contentMediaType.put(contentType, mediaTypeObject);
            });
            responseObject.setContent(contentMediaType);
        }

        // Headers
        if (serialized.containsKey("headers")) {
            Map<String, HeaderObject> headerObjectMap = new LinkedHashMap<>();
            Map<String, Map<String,Object>> headersMap = (Map<String, Map<String, Object>>) serialized.get("headers");
            headersMap.forEach((headerName, headerObjectData) -> {
                HeaderObject headerObject = context.deserialize(HeaderObject.class, headerObjectData);
                headerObjectMap.put(headerName, headerObject);
            });
            responseObject.setHeaders(headerObjectMap);
        }

        return responseObject;
    }
}

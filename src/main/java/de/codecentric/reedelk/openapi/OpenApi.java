package de.codecentric.reedelk.openapi;

import de.codecentric.reedelk.openapi.commons.DataFormat;
import de.codecentric.reedelk.openapi.commons.MapToJsonObject;
import de.codecentric.reedelk.openapi.commons.NavigationPath;
import de.codecentric.reedelk.openapi.commons.Utils;
import de.codecentric.reedelk.openapi.v3.SerializerContext;
import de.codecentric.reedelk.openapi.v3.model.OpenApiObject;
import de.codecentric.reedelk.openapi.v3.serializer.Serializers;
import org.json.JSONObject;
import org.yaml.snakeyaml.Yaml;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class OpenApi {

    private OpenApi() {
    }

    public static String toJson(OpenApiModel openApiModel) {
        return new OpenApiSerializer().toJson(openApiModel, new HashMap<>());
    }

    public static String toJson(OpenApiModel openApiModel, Map<Class<?>, Serializer<?>> overridden) {
        return new OpenApiSerializer().toJson(openApiModel, overridden);
    }

    public static String toJson(OpenApiModel openApiModel, Map<Class<?>, Serializer<?>> overridden, NavigationPath navigationPath) {
        return new OpenApiSerializer().toJson(openApiModel, overridden, navigationPath);
    }

    public static String toYaml(OpenApiModel openApiModel) {
        return new OpenApiSerializer().toYaml(openApiModel, new HashMap<>());
    }

    public static String toYaml(OpenApiModel openApiModel, Map<Class<?>, Serializer<?>> overridden) {
        return new OpenApiSerializer().toYaml(openApiModel, overridden);
    }

    public static String toYaml(OpenApiModel openApiModel, Map<Class<?>, Serializer<?>> overridden, NavigationPath navigationPath) {
        return new OpenApiSerializer().toYaml(openApiModel, overridden, navigationPath);
    }

    public static OpenApiObject from(String jsonOrYaml) {
        return new OpenApiDeserializer().from(jsonOrYaml, new HashMap<>());
    }

    public static OpenApiObject from(String jsonOrYaml, Map<Class<?>, Deserializer<?>> overridden) {
        return new OpenApiDeserializer().from(jsonOrYaml, overridden);
    }

    static class OpenApiDeserializer {

        OpenApiObject from(String jsonOrYaml, Map<Class<?>, Deserializer<?>> overridden) {
            DataFormat openApiFormat = DataFormat.JSON.is(jsonOrYaml) ? DataFormat.JSON : DataFormat.YAML;
            Yaml yaml = new Yaml();
            Map<String,Object> openApiMap = yaml.load(jsonOrYaml);

            String openApiVersion = (String) openApiMap.get("openapi");
            if (Utils.isBlank(openApiVersion)) {
                throw new IllegalArgumentException("Missing 'openapi' version property in the JSON or YAML structure");
            }

            OpenApiVersion VERSION = Arrays.stream(OpenApiVersion.values())
                    .filter(version -> version.isSupported(openApiVersion))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Open API version " + openApiVersion + ", not supported. Supported versions: " +
                            Arrays.stream(OpenApiVersion.values())
                                    .map(OpenApiVersion::displayName)
                                    .collect(Collectors.joining(","))));

            return VERSION.deserialize(openApiMap, overridden, openApiFormat);
        }
    }

    static class OpenApiSerializer {

        private static final int JSON_INDENT_FACTOR = 2;

        String toJson(OpenApiModel serializable, Map<Class<?>, Serializer<?>> overridden) {
            Map<String, Object> serialized = serializeAsMap(serializable, overridden, NavigationPath.create());
            // We use the custom object factory to preserve position
            // of serialized properties in the map.
            JSONObject jsonObject = (JSONObject) MapToJsonObject.convert(serialized);
            return jsonObject.toString(JSON_INDENT_FACTOR);
        }

        String toYaml(OpenApiModel serializable, Map<Class<?>, Serializer<?>> overridden) {
            Map<String, Object> serialized = serializeAsMap(serializable, overridden, NavigationPath.create());
            Yaml yaml = new Yaml();
            return yaml.dump(serialized);
        }

        String toJson(OpenApiModel serializable, Map<Class<?>, Serializer<?>> overridden, NavigationPath navigationPath) {
            Map<String, Object> serialized = serializeAsMap(serializable, overridden, navigationPath);
            // We use the custom object factory to preserve position
            // of serialized properties in the map.
            JSONObject jsonObject = (JSONObject) MapToJsonObject.convert(serialized);
            return jsonObject.toString(JSON_INDENT_FACTOR);
        }

        String toYaml(OpenApiModel serializable, Map<Class<?>, Serializer<?>> overridden, NavigationPath navigationPath) {
            Map<String, Object> serialized = serializeAsMap(serializable, overridden, navigationPath);
            Yaml yaml = new Yaml();
            return yaml.dump(serialized);
        }

        private Map<String, Object> serializeAsMap(OpenApiModel serializable, Map<Class<?>, Serializer<?>> overridden, NavigationPath navigationPath) {
            Serializers serializers = new Serializers(overridden);
            SerializerContext context = new SerializerContext(serializers);
            return context.serialize(navigationPath, serializable);
        }
    }
}
